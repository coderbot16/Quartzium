package net.coderbot.projecty.init;

import net.coderbot.projecty.ProjectY;
import net.coderbot.projecty.color.Color;
import net.coderbot.projecty.color.ColorMap;
import net.coderbot.projecty.blocks.BlockDecoration;
import net.coderbot.projecty.blocks.BlockOre;
import net.coderbot.projecty.color.PentaColor;
import net.coderbot.projecty.color.PentaColorMap;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ProjectY.MODID)
public class ModBlocks {
	public static PentaColorMap<Block> ORE;
	public static ColorMap<Block> BRICKS;
	public static ColorMap<Block> BLOCK;
	public static ColorMap<Block> PLATE;
	public static ColorMap<Block> PLATFORM;
	public static ColorMap<Block> SHIELD;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ORE = registerForPentaColors(event, ProjectY.BASE_NAME + "_ore", color ->
				new BlockOre(color.getColor())
		);

		BRICKS = registerForColors(event, ProjectY.BASE_NAME + "_bricks", color ->
				new BlockDecoration(color.getColor(), 2.0F, 10.0F, false)
		);

		BLOCK = registerForColors(event, ProjectY.BASE_NAME + "_block", color ->
				new BlockDecoration(color.getColor(), 1.5F, 8.0F, true)
		);

		PLATE = registerForColors(event, ProjectY.BASE_NAME + "_plate", color ->
				new BlockDecoration(color.getColor(), 8.0F, 32.0F, true)
		);

		PLATFORM = registerForColors(event, ProjectY.BASE_NAME + "_platform", color ->
				new BlockDecoration(color.getColor(), 0.2F, 10.0F, false)
		);

		SHIELD = registerForColors(event, ProjectY.BASE_NAME + "_shield", color ->
				new BlockDecoration(color.getColor(), 16.0F, 112.0F, true)
		);
	}

	private static ColorMap<Block> registerForColors(RegistryEvent.Register<Block> event, String name, Function<Color, Block> factory) {
		ColorMap<Block> flavor = new ColorMap<>(factory);

		flavor.forEach((Color color, Block block) -> registerBasicBlock(event, color.getName() + '_' + name, block));

		return flavor;
	}

	private static PentaColorMap<Block> registerForPentaColors(RegistryEvent.Register<Block> event, String name, Function<PentaColor, Block> factory) {
		PentaColorMap<Block> flavor = new PentaColorMap<>(factory);

		flavor.forEach((PentaColor color, Block block) -> registerBasicBlock(event, color.getName() + '_' + name, block));

		return flavor;
	}

	private static <T extends Block> T registerBasicBlock(RegistryEvent.Register<Block> event, String name, T block) {
		block.setRegistryName(ProjectY.MODID, name);
		block.setUnlocalizedName(ProjectY.MODID + "." + name);
		block.setCreativeTab(ProjectY.TAB);

		event.getRegistry().register(block);

		return block;
	}
}
