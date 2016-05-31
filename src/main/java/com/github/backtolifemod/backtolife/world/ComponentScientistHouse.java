package com.github.backtolifemod.backtolife.world;

import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenVillage.Start;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

public class ComponentScientistHouse extends StructureVillagePieces.Village{

	private int averageGroundLevel = -1;
	 
	public ComponentScientistHouse(StructureVillagePieces.Start startPiece, int p2, Random random, StructureBoundingBox structureBox, EnumFacing facing) {
		super();
		this.setCoordBaseMode(facing);
		this.boundingBox = structureBox;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		if (this.averageGroundLevel < 0) {
			this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
			if (this.averageGroundLevel < 0) {
				return true;
			}
			this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 4, 0);
		}
		BlockPos blockpos = new BlockPos(this.getXWithOffset(0, 0), this.getYWithOffset(0), this.getZWithOffset(0, 0));
		return StructureScientistHouse.generate(world, random, blockpos.getX(), blockpos.getY() + 1, blockpos.getZ(), this.getCoordBaseMode());
	}

	public static ComponentScientistHouse buildComponent(StructureVillagePieces.Start startPiece, List pieces, Random random, int x, int y, int z, EnumFacing facing, int p5) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 20, 7, 20, facing);
		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentScientistHouse(startPiece, p5, random, structureboundingbox, facing) : null;
	}

}
