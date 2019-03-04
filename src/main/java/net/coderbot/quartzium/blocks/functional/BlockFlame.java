package net.coderbot.quartzium.blocks.functional;

import net.coderbot.quartzium.blocks.BlockDecoration;
import net.coderbot.quartzium.color.Color;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockFlame extends BlockDecoration {
	public BlockFlame(float hardness, float resistance) {
		super(Color.PINK.getColor(), hardness, resistance, false);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Acts like netherrack, permits fire indefinitely");
	}

	@Override
	public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
		return true;
	}
}
