package com.naterbobber.darkerdepths.common.entities;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

@SuppressWarnings("deprecation")

//<>

public class ModBoatEntity extends BoatEntity {
	private BoatEntity.Status status;
	private double lastYd;
	
	private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(ModBoatEntity.class, DataSerializers.VARINT);
	
	public ModBoatEntity(EntityType<? extends BoatEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}
	
	protected void registerData() {
		super.registerData();
		this.dataManager.register(BOAT_TYPE, ModBoatEntity.BoatType.OAK.ordinal());
	}
	
	public void setModBoatType(ModBoatEntity.BoatType boatType) {
		this.dataManager.set(BOAT_TYPE, boatType.ordinal());
	}
	
	public ModBoatEntity.BoatType getModBoatType() {
		return ModBoatEntity.BoatType.byId(this.dataManager.get(BOAT_TYPE));
	}
	
	protected void writeAdditional(CompoundNBT compound) {
		compound.putString("Type", this.getModBoatType().getName());
	}
	
	protected void readAdditional(CompoundNBT compound) {
		if (compound.contains("Type", 8)) {
			this.setModBoatType(ModBoatEntity.BoatType.getTypeFromString(compound.getString("Type")));
		}	
	}
	
	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		this.lastYd = this.getMotion().y;
		if (!this.isPassenger()) {
			if (onGroundIn) {
				if (this.fallDistance > 3.0f) {
					if (this.status != BoatEntity.Status.ON_LAND) {
						this.fallDistance = 0.0f;
						return;
					}
					
					this.onLivingFall(this.fallDistance, 1.0f);
					if (!this.world.isRemote && !this.removed) {
						this.remove();
						if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
							this.dropBreakItems();
						}
					}
				}
				this.fallDistance = 0.0f;
			}
			else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0d) {
				this.fallDistance = (float)((double)this.fallDistance - y);
			}
		}
	}
	
	protected void dropBreakItems() {
		for (int i = 0; i < 3; ++i) {
			this.entityDropItem(this.getPlanks());
		}
		for (int j = 0; j < 2; ++j) {
			this.entityDropItem(Items.STICK);
		}
		this.entityDropItem(this.getDisplayTile().getBlock());
	}
	
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		}
		else if (!this.world.isRemote && !this.removed) {
			if (source instanceof IndirectEntityDamageSource && source.getTrueSource() != null && this.isPassenger(source.getTrueSource())) {
				return false;
			}
			else {
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0f);
				this.markVelocityChanged();
				boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode;
				if (flag || this.getDamageTaken() > 40.0f) {
					if (!flag && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
						this.killBoat();
					}
						this.remove();
				}
					return true;
			}
		} else {
			return true;
		}
	}
	
	public void killBoat() {
		this.entityDropItem(this.getItemDropBoat());
	}
	
	public BlockState getDisplayTile() {
		return Blocks.AIR.getDefaultState();
	}
	
	public Item getItemDropBoat() {
		return this.getItemBoat();
	}
	
	public Item getItemBoat() {
		switch(this.getModBoatType()) {
		case OAK:
		default:
			return Items.OAK_BOAT;
		case SPRUCE:
			return Items.SPRUCE_BOAT;
		case BIRCH:
			return Items.BIRCH_BOAT;
		case JUNGLE:
			return Items.JUNGLE_BOAT;
		case ACACIA:
			return Items.ACACIA_BOAT;
		case DARK_OAK:
			return Items.DARK_OAK_BOAT;
		case PETRIFIED:
			return DDItems.PETRIFIED_BOAT.get();
		}
	}
	
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	protected Block getPlanks() {
		switch(this.getModBoatType()) {
		case OAK:
		default:
			return Blocks.OAK_PLANKS;
		case SPRUCE:
			return Blocks.SPRUCE_PLANKS;
		case BIRCH:
			return Blocks.BIRCH_PLANKS;
		case JUNGLE:
			return Blocks.JUNGLE_PLANKS;
		case ACACIA:
			return Blocks.ACACIA_PLANKS;
		case DARK_OAK:
			return Blocks.DARK_OAK_PLANKS;
		case PETRIFIED:
			return DDBlocks.PETRIFIED_PLANKS.get();
		}
	}
	
	public static enum BoatType {
		OAK("oak"),
		SPRUCE("spruce"),
		BIRCH("birch"),
		JUNGLE("jungle"),
		ACACIA("acacia"),
		DARK_OAK("dark_oak"),
		PETRIFIED("petrified");
		
		private final String name;
		
		private BoatType(String nameIn) {
			this.name = nameIn;
		}
		
		public String getName() {
			return this.name;
		}
		
		public String toString() {
			return this.name;
		}
		
		public static ModBoatEntity.BoatType byId(int id){
			ModBoatEntity.BoatType[] type = values();
			if (id < 0 || id >= type.length) {
				id = 0;
			}
			return type[id];
		}
		
		public static ModBoatEntity.BoatType getTypeFromString(String nameIn) {
			ModBoatEntity.BoatType[] type = values();
			
			for (int i = 0; i < type.length; ++i) {
				if (type[i].getName().equals(nameIn)) {
					return type[i];
				}
			}
			return type[0];
		}
	}
}
