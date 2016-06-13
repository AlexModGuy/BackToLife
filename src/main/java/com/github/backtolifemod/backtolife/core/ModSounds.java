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
	public static SoundEvent protoceratops_idle;
	public static SoundEvent protoceratops_hurt;
	public static SoundEvent protoceratops_death;
	public static SoundEvent protoceratops_attack;
	
	public static SoundEvent tarbosaurus_idle;
	public static SoundEvent tarbosaurus_hurt;
	public static SoundEvent tarbosaurus_death;
	public static SoundEvent tarbosaurus_attack;
	
	public static void init(){
		velociraptor_idle = registerSound("velociraptor_idle");
		velociraptor_hurt = registerSound("velociraptor_hurt");
		velociraptor_death = registerSound("velociraptor_death");
		velociraptor_threat = registerSound("velociraptor_threat");
		velociraptor_attack = registerSound("velociraptor_attack");
		protoceratops_idle = registerSound("protoceratops_idle");
		protoceratops_hurt = registerSound("protoceratops_hurt");
		protoceratops_death = registerSound("protoceratops_death");
		protoceratops_attack = registerSound("protoceratops_attack");
		tarbosaurus_idle = registerSound("tarbosaurus_idle");
		tarbosaurus_hurt = registerSound("tarbosaurus_hurt");
		tarbosaurus_death = registerSound("tarbosaurus_death");
		tarbosaurus_attack = registerSound("tarbosaurus_attack");
	}

	private static SoundEvent registerSound(String sound) {
		return GameRegistry.register(new SoundEvent(new ResourceLocation("backtolife", sound)).setRegistryName(new ResourceLocation("backtolife", sound)));
	}
}
