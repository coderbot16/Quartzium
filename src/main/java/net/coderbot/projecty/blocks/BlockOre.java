package net.coderbot.projecty.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockOre extends Block {
	public BlockOre(MapColor color) {
		super(Material.ROCK, color);

		setHardness(1.5F);
		setResistance(8.0F);
	}

	// TODO: Drops
	// 3-4 items
}
