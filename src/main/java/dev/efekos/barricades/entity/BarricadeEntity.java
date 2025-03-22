package dev.efekos.barricades.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BarricadeEntity extends Entity implements Attackable {

    private LivingEntity lastAttacker = null;
    public static final TrackedData<Boolean> ROTATE_HITBOX = DataTracker.registerData(BarricadeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> DAMAGE = DataTracker.registerData(BarricadeEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Long> LAST_DAMAGE_TICKS = DataTracker.registerData(BarricadeEntity.class, TrackedDataHandlerRegistry.LONG);

    public BarricadeEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public Box calculateBoundingBox() {
        return getDataTracker().get(ROTATE_HITBOX) ? Box.of(getPos(), .125, 0.25, 2.25) : Box.of(getPos(), 2.25, 0.25, .125);
    }

    private void setDamage(int damage){
        getDataTracker().set(DAMAGE, damage);
    }

    public void setLastDamageTicks(long lastDamageTicks){
        getDataTracker().set(LAST_DAMAGE_TICKS, lastDamageTicks);
    }

    public void damage() {
        setDamage(getDamage()+1);
        setLastDamageTicks(getWorld().getTime());
        if (getDamage()>=5) {
            breakBarricade();
        } else playHitSound();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if(data.equals(ROTATE_HITBOX)) setBoundingBox(calculateBoundingBox());
    }

    public void setRotateHitbox(boolean rotate) {
        getDataTracker().set(ROTATE_HITBOX, rotate);
    }

    public boolean doesRotateHitbox() {
        return getDataTracker().get(ROTATE_HITBOX);
    }

    @Override
    public void setYaw(float yaw) {
        super.setYaw(yaw);
        setBoundingBox(calculateBoundingBox());
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DAMAGE, 0);
        this.dataTracker.startTracking(LAST_DAMAGE_TICKS, getWorld().getTime());
        this.dataTracker.startTracking(ROTATE_HITBOX, false);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        getDataTracker().set(ROTATE_HITBOX, nbt.getBoolean("RotateHitbox"));
        getDataTracker().set(DAMAGE, nbt.getInt("Damage"));
        getDataTracker().set(LAST_DAMAGE_TICKS, nbt.getLong("LastDamageTicks"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putLong("LastDamageTicks", getLastDamageTicks());
        nbt.putInt("Damage", getDamage());
        nbt.putBoolean("RotateHitbox", doesRotateHitbox());
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public @Nullable LivingEntity getLastAttacker() {
        return lastAttacker;
    }

    public int getDamage() {
        return getDataTracker().get(DAMAGE);
    }

    public long getLastDamageTicks() {
        return getDataTracker().get(LAST_DAMAGE_TICKS);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return false;
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        if(attacker instanceof LivingEntity) {
            lastAttacker = (LivingEntity) attacker;
            damage();
            return true;
        } else {
            return false;
        }
    }

    private void breakBarricade() {
        playHitSound();
        Vec3d pos = getPos();
        for (int i = 0; i < Math.random() * 10; i++) {
            getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), pos.x,pos.y,pos.z,Math.random()-.5,Math.random()-.5,Math.random()-.5);
        }
        remove(RemovalReason.KILLED);
    }

    private void playHitSound() {
        getWorld().playSound(this,getBlockPos(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.BLOCKS,1f,((float) Math.random()/2f)+.5f);
    }

    @Override
    public boolean collidesWith(Entity other) {
        return other instanceof LivingEntity;
    }

}