package com.github.backtolifemod.backtolife.block;

import com.github.backtolifemod.backtolife.BackToLife;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFlamingCliffsFossil extends Block {

    public boolean isAnimal;

    public BlockFlamingCliffsFossil(boolean isAnimal) {
        super(Material.ROCK);
        this.isAnimal = isAnimal;
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(BackToLife.tab);
        this.setUnlocalizedName(isAnimal ? "backtolife.flaming_cliffs_animal_fossil" : "backtolife.flaming_cliffs_plant_fossil");
        GameRegistry.registerBlock(this, isAnimal ? "flaming_cliffs_animal_fossil" : "flaming_cliffs_plant_fossil");
        BackToLife.PROXY.addItemRender(Item.getItemFromBlock(this), isAnimal ? "flaming_cliffs_animal_fossil" : "flaming_cliffs_plant_fossil");
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.CLAY_BALL;
    }

    public int quantityDropped(Random random){
        return 4;
    }
}
