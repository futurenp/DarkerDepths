package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.api.DeathAnchorLocation;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDPoiTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class DeathAnchorBlock extends Block {
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public DeathAnchorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(DDItems.VOID_SOUL_REQUIEM.get()) && !state.getValue(POWERED)) {
            level.setBlock(blockPos, state.setValue(POWERED, true), 2);
            level.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!player.getAbilities().instabuild) itemStack.shrink(1);

            if (player instanceof DeathAnchorLocation deathAnchorLocation) {
                this.handleExistingDeathAnchor(level, blockPos, deathAnchorLocation);
                deathAnchorLocation.setDeathAnchorLocation(Optional.of(GlobalPos.of(level.dimension(), blockPos)));
            }

            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, blockPos, player, hand, result);
    }

    private void handleExistingDeathAnchor(Level level, BlockPos current, DeathAnchorLocation deathAnchorLocation) {
        Optional<GlobalPos> deathAnchorLocation1 = deathAnchorLocation.getDeathAnchorLocation();

        if (deathAnchorLocation1.isPresent() && level instanceof ServerLevel serverLevel) {
            GlobalPos globalPos = deathAnchorLocation1.get();
            ResourceKey<Level> resourcekey = globalPos.dimension();
            ResourceKey<PoiType> key = DDPoiTypes.DEATH_ANCHOR.getKey();

            BlockPos pos = globalPos.pos();

            if (pos.equals(current)) return;

            ServerLevel newServer;

            if (key != null && (newServer = serverLevel.getServer().getLevel(resourcekey)) != null && newServer.getPoiManager().existsAtPosition(key, pos)) {
                newServer.scheduleTick(pos, DDBlocks.DEATH_ANCHOR.get(), 2);
                newServer.setBlock(pos, DDBlocks.DEATH_ANCHOR.get().defaultBlockState().setValue(POWERED, false), 2);
            }
        }

    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        serverLevel.setBlock(blockPos, blockState.setValue(POWERED, false), 2);
        serverLevel.playSound(null, blockPos, SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}
