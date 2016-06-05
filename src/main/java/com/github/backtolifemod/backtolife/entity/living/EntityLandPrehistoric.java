package com.github.backtolifemod.backtolife.entity.living;

import net.minecraft.world.World;

import com.github.backtolifemod.backtolife.enums.EnumPrehistoricType;

public abstract class EntityLandPrehistoric extends EntityPrehistoric {

	public EntityLandPrehistoric(World world, EnumPrehistoricType type, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
		super(world, type, minimumDamage, maximumDamage, minimumHealth, maximumHealth, minimumSpeed, maximumSpeed);
	}

}
