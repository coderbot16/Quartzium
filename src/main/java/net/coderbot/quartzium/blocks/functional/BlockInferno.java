package net.coderbot.quartzium.blocks.functional;

import net.coderbot.quartzium.color.Color;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockInferno extends BlockThermal {
	public BlockInferno(float hardness, float resistance) {
		super(Color.RED, hardness, resistance);

		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, BlockPos selfPos, IBlockState state, Random rand) {
		super.updateTick(world, selfPos, state, rand);

		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);
			IBlockState neighbor = world.getBlockState(pos);

			if(rand.nextBoolean() && neighbor.getBlock().isAir(neighbor, world, pos) && Blocks.FIRE.canPlaceBlockAt(world, pos)) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Melts ice and snow into water");
		tooltip.add("Generates fire passively");
		tooltip.add("Blocks the operation of Quartzium Ice");
	}

	@Override
	public void activate(World world, BlockPos selfPos, IBlockState state, Random rand) {
		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);
			IBlockState neighbor = world.getBlockState(pos);

			if(neighbor.getBlock() == Blocks.ICE) {
				triggerBoil(world, pos);
				world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
			} else if(neighbor.getBlock() == Blocks.SNOW || neighbor.getBlock() == Blocks.SNOW_LAYER) {
				triggerBoil(world, pos);
				world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState().withProperty(BlockLiquid.LEVEL, 1));
			}
		}
	}
}
