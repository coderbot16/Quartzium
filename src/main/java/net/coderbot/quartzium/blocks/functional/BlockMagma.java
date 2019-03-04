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

public class BlockMagma extends BlockThermal {
	public BlockMagma(float hardness, float resistance) {
		super(Color.ORANGE, hardness, resistance);

		setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, BlockPos selfPos, IBlockState state, Random rand) {
		super.updateTick(world, selfPos, state, rand);

		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);
			IBlockState neighbor = world.getBlockState(pos);

			if(rand.nextBoolean() && neighbor.getBlock().isAir(neighbor, world, pos)) {
				world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 1));
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Turns water into stone and cobblestone");
		tooltip.add("Generates flowing lava passively");
	}

	@Override
	public void activate(World world, BlockPos selfPos, IBlockState state, Random rand) {
		for(EnumFacing facing: EnumFacing.VALUES) {
			BlockPos pos = selfPos.offset(facing);
			IBlockState neighbor = world.getBlockState(pos);

			if(neighbor.getBlock() == Blocks.WATER || neighbor.getBlock() == Blocks.FLOWING_WATER) {
				if(facing == EnumFacing.DOWN) {
					triggerBoil(world, pos);
					world.setBlockState(pos, Blocks.STONE.getDefaultState());
				} else {
					triggerBoil(world, pos);
					world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
				}
			}
		}
	}
}
