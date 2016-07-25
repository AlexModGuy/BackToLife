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

public class BlockFlamingCliffsAnimalFossil extends Block {

    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 5);

    public BlockFlamingCliffsAnimalFossil() {
        super(Material.ROCK);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(BackToLife.tab);
        this.setUnlocalizedName("backtolife.flaming_cliffs_animal_fossil");
        GameRegistry.registerBlock(this, "flaming_cliffs_animal_fossil");
        BackToLife.PROXY.addItemRender(Item.getItemFromBlock(this), "flaming_cliffs_animal_fossil");
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Integer.valueOf(0)));
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(VARIANT, new Random().nextInt(6)));
        return this.getDefaultState().withProperty(VARIANT, new Random().nextInt(6));
    }
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state) {
        return ((Integer)state.getValue(VARIANT)).intValue();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {

        return Items.CLAY_BALL;
    }

    public int quantityDropped(Random random){
        return 4;
    }
}
