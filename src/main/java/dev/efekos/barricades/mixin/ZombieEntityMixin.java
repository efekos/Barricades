package dev.efekos.barricades.mixin;

import dev.efekos.barricades.entity.goal.BarricadeInteractGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends MobEntity {

    public ZombieEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals",at = @At("TAIL"))
    public void initCustomGoals(CallbackInfo ci) {
        this.targetSelector.add(2,new BarricadeInteractGoal(this));
    }

}