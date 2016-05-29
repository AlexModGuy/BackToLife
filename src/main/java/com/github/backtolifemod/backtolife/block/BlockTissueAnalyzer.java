package com.github.backtolifemod.backtolife.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.backtolifemod.backtolife.BackToLife;
import com.github.backtolifemod.backtolife.entity.tile.TileEntityTissueAnalyzer;


public class BlockTissueAnalyzer extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockTissueAnalyzer() {
		super(Material.IRON);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setSoundType(SoundType.METAL);
		this.setCreativeTab(BackToLife.tab);
		this.setUnlocalizedName("backtolife.tissue_analyzer");
		GameRegistry.registerBlock(this, "tissue_analyzer");
		BackToLife.PROXY.addItemRender(Item.getItemFromBlock(this), "tissue_analyzer");
		GameRegistry.registerTileEntity(TileEntityTissueAnalyzer.class, "tissue_analyzer");
	}

	public static final AxisAlignedBB BOUNDINGBOX = new AxisAlignedBB(0F, 0, 0F, 1F, 0.9375F, 1F);

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return BOUNDINGBOX;
	}

	public boolean isOpaqueCube(IBlockState blockstate){
		return false;
	}

	public boolean isFullCube(IBlockState blockstate){
		return false;
	}

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		return iblockstate.getBlock() != Blocks.AIR;
	}

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
		this.checkAndDropBlock(worldIn, pos, worldIn.getBlockState(pos.down()));
	}

	private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state){
		if (!this.canPlaceBlockAt(worldIn, pos)){
			worldIn.destroyBlock(pos, true);
			return false;
		}
		else{
			return true;
		}
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityTissueAnalyzer){
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityTissueAnalyzer)tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
	}

	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer(){
		return BlockRenderLayer.CUTOUT;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(playerIn.isSneaking()){
			return false;
		}else{
			playerIn.openGui(BackToLife.INSTANCE, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTissueAnalyzer();
	}

}
