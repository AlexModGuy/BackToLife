package com.github.backtolifemod.backtolife.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.backtolifemod.backtolife.entity.living.EntityVelociraptor;

public enum EnumPrehistoricType {
	VELOCIRAPTOR(EntityVelociraptor.class, EnumPrehistoricFossilType.CARNIVORE_DINOSAUR, EnumPrehistoricEggType.DROMEOSAURID, 0X392A22, 0XDAC9B9),
	PROTOCERATOPS(null, EnumPrehistoricFossilType.HERBIVORE_DINOSAUR, EnumPrehistoricEggType.SMALL_CERATOPSIAN, 0, 0),
	TARBOSAURUS(null, EnumPrehistoricFossilType.CARNIVORE_DINOSAUR, EnumPrehistoricEggType.NORMAL, 0, 0);
	public EnumPrehistoricFossilType fossilType;
	public EnumPrehistoricEggType eggType;
	public Class entityClass;
	public int colorA;
	public int colorB;

	private EnumPrehistoricType(Class entityClass, EnumPrehistoricFossilType fossilType, EnumPrehistoricEggType eggType, int colorA, int colorB) {
		this.entityClass = entityClass;
		this.fossilType = fossilType;
		this.eggType = eggType;
		this.colorA = colorA;
		this.colorB = colorB;
	}

	public static EnumPrehistoricType getOneOfFossilType(EnumPrehistoricFossilType fossilType_0) {
		List<EnumPrehistoricType> list = new ArrayList<EnumPrehistoricType>();
		for (EnumPrehistoricType prehistoricType : EnumPrehistoricType.values()) {
			if (prehistoricType.fossilType == fossilType_0) {
				list.add(prehistoricType);
			}
		}
		return list.get(new Random().nextInt(list.size()));
	}

	public enum EnumPrehistoricFossilType {
		CARNIVORE_DINOSAUR, HERBIVORE_DINOSAUR, PTEROSAUR;
	}

	public enum EnumPrehistoricDietType {
		CARNIVORE, HERBIVORE, OMNIVORE;
	}
	
	public enum EnumPrehistoricEggType {
		BIG_CERATOPSIAN, SMALL_CERATOPSIAN, DROMEOSAURID, OVIRAPTORID, SAUROPOD, NORMAL;
	}
}
