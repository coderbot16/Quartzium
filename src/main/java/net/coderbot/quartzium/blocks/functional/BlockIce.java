package net.coderbot.quartzium.blocks.functional;

import net.coderbot.quartzium.color.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStoneSlab;
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

public class BlockIce extends BlockThermal {
	private Block inferno;

	public BlockIce(float hardness, float resistance) {
		super(Color.WHITE, hardness, resistance);

		setTickRandomly(true);
	}

	public void setInferno(Block inferno) {
		this.inferno = inferno;
	}

	@Override
	public void updateTick(World world, BlockPos selfPos, IBlockState state, Random rand) {
		super.updateTick(world, selfPos, state, rand);

		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);
			IBlockState neighbor = world.getBlockState(pos);

			if(checkForInferno(world, pos)) {
				continue;
			}

			if(rand.nextBoolean() && neighbor.getBlock().isAir(neighbor, world, pos) && Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos)) {
				world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Freezes water into ice and snow");
		tooltip.add("Freezes lava into obsidian and cobblestone slabs");
		tooltip.add("Also passively generates small amounts of snow");
	}

	@Override
	public void activate(World world, BlockPos selfPos, IBlockState state, Random rand) {
		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);

			if(checkForInferno(world, pos)) {
				continue;
			}

			IBlockState neighbor = world.getBlockState(pos);

			if(neighbor.getBlock() == Blocks.WATER || neighbor.getBlock() == Blocks.FLOWING_WATER) {
				int level = neighbor.getValue(BlockLiquid.LEVEL);

				if(level <= 1) {
					world.setBlockState(pos, Blocks.ICE.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.SNOW.getDefaultState());
				}
			} else if(neighbor.getBlock() == Blocks.LAVA || neighbor.getBlock() == Blocks.FLOWING_LAVA) {
				int level = neighbor.getValue(BlockLiquid.LEVEL);

				if(level == 0) {
					world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE));
				}
			}
		}
	}

	private boolean checkForInferno(World world, BlockPos pos) {
		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos neighbor = pos.offset(facing);

			if(world.getBlockState(neighbor).getBlock() == inferno) {
				return true;
			}
		}

		return false;
	}
}
