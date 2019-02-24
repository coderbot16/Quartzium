package net.coderbot.projecty.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOre extends Block {
	private Item crystal;

	public BlockOre(MapColor color) {
		super(Material.ROCK, color);

		setHardness(1.5F);
		setResistance(8.0F);
	}

	public void setCrystal(Item crystal) {
		this.crystal = crystal;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return crystal;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		int multiplier = random.nextInt(fortune + 2);

		if(multiplier < 1) {
			multiplier = 1;
		}

		return (3 + random.nextInt(2)) * multiplier;
	}

	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		Random random = world instanceof World ? ((World)world).rand : new Random();

		return 2 + random.nextInt(3);
	}
}
