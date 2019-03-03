package net.coderbot.projecty.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLamp extends Block {
	public static PropertyBool LIT = PropertyBool.create("lit");
	private boolean invert;

	public BlockLamp(MapColor color, float hardness, float resistance, boolean invert) {
		super(Material.REDSTONE_LIGHT, color);
		setSoundType(SoundType.GLASS);
		setHardness(hardness);
		setResistance(resistance);

		this.invert = invert;

		this.setDefaultState(this.getDefaultState().withProperty(LIT, false));
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if(world.isRemote) {
			return;
		}

		boolean lit = state.getValue(LIT);

		if (lit && !world.isBlockPowered(pos)) {
			world.setBlockState(pos, state.withProperty(LIT, false), 2);
		} else if (!lit && world.isBlockPowered(pos)) {
			world.setBlockState(pos, state.withProperty(LIT, true), 2);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(world.isRemote) {
			return;
		}

		boolean lit = state.getValue(LIT);

		if (lit && !world.isBlockPowered(pos)) {
			world.scheduleUpdate(pos, this, 4);
		} else if (!lit && world.isBlockPowered(pos)) {
			world.setBlockState(pos, state.withProperty(LIT, true), 2);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		onBlockAdded(world, pos, state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getLightValue(IBlockState state)
	{
		return state.getValue(LIT) ^ invert ? 15 : 0;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LIT) ? 1 : 0;
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return meta > 0 ? this.getDefaultState().withProperty(LIT, true) : this.getDefaultState();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LIT);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
