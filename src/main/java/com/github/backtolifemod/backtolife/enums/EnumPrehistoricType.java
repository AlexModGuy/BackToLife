package com.github.backtolifemod.backtolife.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum EnumPrehistoricType {
	VELOCIRAPTOR(EnumPrehistoricFossilType.CARNIVORE_DINOSAUR),
	PROTOCERATOPS(EnumPrehistoricFossilType.HERBIVORE_DINOSAUR),
	TARBOSAURUS(EnumPrehistoricFossilType.CARNIVORE_DINOSAUR);
	
	EnumPrehistoricFossilType fossilType;
	
	private EnumPrehistoricType(EnumPrehistoricFossilType fossilType){
		this.fossilType = fossilType;
	}
	
	public static EnumPrehistoricType getOneOfFossilType(EnumPrehistoricFossilType fossilType_0){
		List<EnumPrehistoricType> list = new ArrayList<EnumPrehistoricType>();
		for(EnumPrehistoricType prehistoricType : EnumPrehistoricType.values()){
			if(prehistoricType.fossilType == fossilType_0){
				list.add(prehistoricType);
			}
		}
		return list.get(new Random().nextInt(list.size()));
	}
	
	public enum EnumPrehistoricFossilType {
		CARNIVORE_DINOSAUR,
		HERBIVORE_DINOSAUR,
		PTEROSAUR;
	}
}
