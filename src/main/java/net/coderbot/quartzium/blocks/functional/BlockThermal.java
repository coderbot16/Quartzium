package net.coderbot.quartzium.blocks.functional;

import net.coderbot.quartzium.blocks.BlockDecoration;
import net.coderbot.quartzium.color.Color;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockThermal extends BlockDecoration {
	// If true, neighbor updates will be ignored.
	private boolean working = false;

	public BlockThermal(Color color, float hardness, float resistance) {
		super(color.getColor(), hardness, resistance, false);

		setTickRandomly(true);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if(world.isRemote) {
			return;
		}

		working = true;
		activate(world, pos, state, world.rand);
		working = false;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(world.isRemote) {
			return;
		}

		working = true;
		activate(world, pos, state, rand);
		working = false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(world.isRemote || working) {
			return;
		}

		working = true;
		activate(world, pos, state, world.rand);
		working = false;
	}

	public void triggerBoil(World world, BlockPos pos) {
		double x = (double)pos.getX();
		double y = (double)pos.getY();
		double z = (double)pos.getZ();
		world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

		for (int i = 0; i < 8; ++i) {
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + Math.random(), y + 1.2D, z + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}

	public abstract void activate(World world, BlockPos pos, IBlockState state, Random rand);
}
