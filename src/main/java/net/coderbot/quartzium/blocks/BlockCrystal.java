package net.coderbot.quartzium.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystal extends Block {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.4, 0.0, 0.4, 0.6, 0.6, 0.6);
	private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.4, 0.4, 0.4, 0.6, 1.0, 0.6);
	private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0, 0.4, 0.4, 0.6, 0.6, 0.6);
	private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.4, 0.4, 0.4, 1.0, 0.6, 0.6);
	private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.4, 0.4, 0.0, 0.6, 0.6, 0.6);
	private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.4, 0.4, 0.4, 0.6, 0.6, 1.0);

	public BlockCrystal(MapColor color) {
		super(Material.GLASS, color);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));

		setLightLevel(8.0F / 15.0F);
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch(state.getValue(FACING)) {
			case UP:    return AABB_UP;
			case DOWN:  return AABB_DOWN;
			case EAST:  return AABB_EAST;
			case WEST:  return AABB_WEST;
			case NORTH: return AABB_NORTH;
			case SOUTH: return AABB_SOUTH;
			default:    return AABB_UP;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return getBoundingBox(state, world, pos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		BlockPos potentialPos = pos.offset(side.getOpposite());
		IBlockState state = world.getBlockState(potentialPos);

		if(side==EnumFacing.UP && state.isSideSolid(world, pos, EnumFacing.UP)) {
			return true;
		}

		return !isExceptBlockForAttachWithPiston(state.getBlock()) && state.getBlockFaceShape(world, pos, side) == BlockFaceShape.SOLID;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : FACING.getAllowedValues()) {
			if (this.canPlaceBlockOnSide(worldIn, pos, enumfacing)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		if(!canPlaceBlockOnSide(world, pos, facing)) {
			return this.getDefaultState();
		}

		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!this.canPlaceBlockOnSide(world, pos, state.getValue(FACING))) {
			this.dropBlockAsItem(world, pos, state, 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState withRotation(IBlockState state, Rotation rotation) {
		return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
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
