package com.github.backtolifemod.backtolife.entity.living;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public abstract class EntityPrehistoric extends EntityTameable implements IAnimatedEntity {

	public EnumPrehistoricType type;
	private Animation currentAnimation;
	private int animationTicks;

	public EntityPrehistoric(World world, EnumPrehistoricType type) {
		super(world);
		this.type = type;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public int getAnimationTick() {
		return animationTicks;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTicks = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{};
	}
}
