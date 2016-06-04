package com.github.backtolifemod.backtolife.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.backtolifemod.backtolife.entity.living.EntityVelociraptor;

public enum EnumPrehistoricType {
	VELOCIRAPTOR(EntityVelociraptor.class, EnumPrehistoricFossilType.CARNIVORE_DINOSAUR, EnumPrehistoricEggType.DROMEOSAURID), PROTOCERATOPS(null, EnumPrehistoricFossilType.HERBIVORE_DINOSAUR, EnumPrehistoricEggType.SMALL_CERATOPSIAN), TARBOSAURUS(null, EnumPrehistoricFossilType.CARNIVORE_DINOSAUR, EnumPrehistoricEggType.NORMAL);

	public EnumPrehistoricFossilType fossilType;
	public EnumPrehistoricEggType eggType;
	public Class entityClass;

	private EnumPrehistoricType(Class entityClass, EnumPrehistoricFossilType fossilType, EnumPrehistoricEggType eggType) {
		this.entityClass = entityClass;
		this.fossilType = fossilType;
		this.eggType = eggType;
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

	public enum EnumPrehistoricEggType {
		BIG_CERATOPSIAN, SMALL_CERATOPSIAN, DROMEOSAURID, OVIRAPTORID, SAUROPOD, NORMAL;
	}
}
