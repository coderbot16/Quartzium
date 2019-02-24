package net.coderbot.projecty.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCrystal extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.4, 0.0, 0.4, 0.6, 0.6, 0.6);

	public BlockCrystal(MapColor color) {
		super(Material.GLASS, color);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
			case UP:
			case DOWN: return AABB_DOWN;
			case EAST:
			case WEST:
			case NORTH:
			case SOUTH:
			default: return AABB_DOWN;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return getBoundingBox(state, world, pos);
	}


	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal();
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing;

		switch(meta) {
			case 0:
				facing = EnumFacing.DOWN;
				break;
			case 1:
				facing = EnumFacing.UP;
				break;
			case 2:
				facing = EnumFacing.NORTH;
				break;
			case 3:
				facing = EnumFacing.SOUTH;
				break;
			case 4:
				facing = EnumFacing.WEST;
				break;
			default:
				facing = EnumFacing.EAST;
				break;
		}

		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
}
