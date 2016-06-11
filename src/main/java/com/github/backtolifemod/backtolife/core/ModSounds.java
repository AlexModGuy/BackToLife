package com.github.backtolifemod.backtolife.core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSounds {

	public static SoundEvent velociraptor_idle;
	public static SoundEvent velociraptor_hurt;
	public static SoundEvent velociraptor_death;
	public static SoundEvent velociraptor_threat;
	public static SoundEvent velociraptor_attack;

	public static void init(){
		velociraptor_idle = registerSound("velociraptor_idle");
		velociraptor_hurt = registerSound("velociraptor_hurt");
		velociraptor_death = registerSound("velociraptor_death");
		velociraptor_threat = registerSound("velociraptor_threat");
		velociraptor_attack = registerSound("velociraptor_attack");
	}

	private static SoundEvent registerSound(String sound) {
		return GameRegistry.register(new SoundEvent(new ResourceLocation("backtolife", sound)).setRegistryName(new ResourceLocation("backtolife", sound)));
	}
}
