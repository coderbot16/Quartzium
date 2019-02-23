package net.coderbot.projecty.init;

import net.coderbot.projecty.ProjectY;
import net.coderbot.projecty.blocks.BlockDecoration;
import net.coderbot.projecty.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ProjectY.MODID)
public class ModBlocks {
	public static Flavor ORE;
	public static Flavor BRICKS;
	public static Flavor BLOCK;
	public static Flavor PLATE;
	public static Flavor PLATFORM;
	public static Flavor SHIELD;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ORE = registerFlavorOre(event, ProjectY.BASE_NAME + "_ore");
		BRICKS = registerFlavor(event, ProjectY.BASE_NAME + "_bricks", 2.0F, 10.0F, false);
		BLOCK = registerFlavor(event, ProjectY.BASE_NAME + "_block", 1.5F, 8.0F, true);
		PLATE = registerFlavor(event, ProjectY.BASE_NAME + "_plate", 8.0F, 32.0F, true);
		PLATFORM = registerFlavor(event, ProjectY.BASE_NAME + "_platform", 0.2F, 10.0F, false);
		SHIELD = registerFlavor(event, ProjectY.BASE_NAME + "_shield", 16.0F, 112.0F, true);
	}

	private static Flavor registerFlavor(RegistryEvent.Register<Block> event, String name, float hardness, float resistance, boolean isBeaconBase) {
		Flavor flavor = new Flavor();

		flavor.red = registerBasicBlock(event, name + "_red", new BlockDecoration(MapColor.RED, hardness, resistance, isBeaconBase));
		flavor.blue = registerBasicBlock(event, name + "_blue", new BlockDecoration(MapColor.BLUE, hardness, resistance, isBeaconBase));
		flavor.green = registerBasicBlock(event, name + "_green", new BlockDecoration(MapColor.GREEN, hardness, resistance, isBeaconBase));
		flavor.light = registerBasicBlock(event, name + "_light", new BlockDecoration(MapColor.SNOW, hardness, resistance, isBeaconBase));
		flavor.dark = registerBasicBlock(event, name + "_dark", new BlockDecoration(MapColor.OBSIDIAN, hardness, resistance, isBeaconBase));

		return flavor;
	}

	private static Flavor registerFlavorOre(RegistryEvent.Register<Block> event, String name) {
		Flavor flavor = new Flavor();

		flavor.red = registerBasicBlock(event, name + "_red", new BlockOre(MapColor.RED));
		flavor.blue = registerBasicBlock(event, name + "_blue", new BlockOre(MapColor.BLUE));
		flavor.green = registerBasicBlock(event, name + "_green", new BlockOre(MapColor.GREEN));
		flavor.light = registerBasicBlock(event, name + "_light", new BlockOre(MapColor.SNOW));
		flavor.dark = registerBasicBlock(event, name + "_dark", new BlockOre(MapColor.OBSIDIAN));

		return flavor;
	}

	private static <T extends Block> T registerBasicBlock(RegistryEvent.Register<Block> event, String name, T block) {
		block.setRegistryName(ProjectY.MODID, name);
		block.setUnlocalizedName(ProjectY.MODID + "." + name);
		block.setCreativeTab(ProjectY.TAB);

		event.getRegistry().register(block);

		return block;
	}

	public static class Flavor {
		public Block red;
		public Block blue;
		public Block green;
		public Block light;
		public Block dark;
	}
}
