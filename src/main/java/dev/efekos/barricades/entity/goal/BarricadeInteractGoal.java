package dev.efekos.barricades.entity.goal;

import dev.efekos.barricades.entity.BarricadeEntity;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;

import java.util.List;

public class BarricadeInteractGoal extends Goal {
	protected MobEntity mob;
	protected BarricadeEntity barricadeEntity;
	private int cooldown;

	public BarricadeInteractGoal(MobEntity mob) {
		this.mob = mob;
		if (!NavigationConditions.hasMobNavigation(mob)) {
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	@Override
	public boolean canStart() {
		if(this.cooldown-- > 0){
			return false;
		} else if (!NavigationConditions.hasMobNavigation(this.mob)) {
			return false;
		} else if (!this.mob.horizontalCollision) {
			return false;
		} else {
			MobNavigation mobNavigation = (MobNavigation)this.mob.getNavigation();
			Path path = mobNavigation.getCurrentPath();
			if (path != null && !path.isFinished() && mobNavigation.canEnterOpenDoors()) {
				for (int i = 0; i < Math.min(path.getCurrentNodeIndex() + 2, path.getLength()); i++) {
					PathNode pathNode = path.getNode(i);

					List<BarricadeEntity> list = this.mob.getWorld().getEntitiesByClass(BarricadeEntity.class, new Box(pathNode.getBlockPos()).expand(2f), be -> be.getBoundingBox().expand(0.02f).intersects(this.mob.getBoundingBox()));
                    if (!list.isEmpty()) {
                        barricadeEntity = list.get(0);
						return true;
                    }
				}

            }
            return false;
        }
	}

	@Override
	public void start() {
		this.barricadeEntity.damage();
		this.barricadeEntity = null;
		switch (this.mob.getWorld().getDifficulty()){
			case PEACEFUL -> this.cooldown = 100;
			case EASY -> this.cooldown = 20;
			case NORMAL -> this.cooldown = 15;
			case HARD -> this.cooldown = 10;
		}
		this.mob.swingHand(this.mob.getActiveHand());
	}

}
