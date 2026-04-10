package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.blockentities.ParanoiaAltarBlockEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ParanoiaAltarBlock extends BaseEntityBlock  {
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public ParanoiaAltarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LOCKED, false)
                .setValue(ENABLED, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ParanoiaAltarBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.or(
                Block.box(0, 0, 0, 16, 4, 16),
                Block.box(1, 4, 1, 15, 13, 15));

        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LOCKED);
        builder.add(ENABLED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LOCKED, false).setValue(ENABLED, true);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(DDItems.VOID_SOUL_REQUIEM.get()) && state.getValue(LOCKED)) {
            level.setBlock(blockPos, state.setValue(LOCKED, false), 2);
            level.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1F, 2.0F);
            if(level instanceof ServerLevel serverLevel) {
                for(int i = 0; i < 10; i++) {
                    var rand = level.getRandom();
                    var x = blockPos.getX() + rand.nextDouble();
                    var y = blockPos.getY() + rand.nextDouble();
                    var z = blockPos.getZ() + rand.nextDouble();
                    serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, 5, 0, 1, 0, 0.05);
                }
            }
            if (!player.getAbilities().instabuild) itemStack.shrink(1);

            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, blockPos, player, hand, result);
    }

    @Override
    public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
        if (pState.getValue(LOCKED)) {
            return 0.0F;
        }
        return super.getDestroyProgress(pState, pPlayer, pLevel, pPos);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        if (state.getValue(LOCKED)) {
            return 1200.0F;
        }
        return super.getExplosionResistance(state, level, pos, explosion);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return createTickerHelper(type, DDBlockEntityTypes.PARANOIA_ALTAR.get(), (lvl, pos, st, be) -> be.tick(lvl, pos, st));
    }
}
