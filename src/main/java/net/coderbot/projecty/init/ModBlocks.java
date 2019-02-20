package net.coderbot.projecty.init;

import net.coderbot.projecty.ProjectY;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
		ORE = registerFlavor(event, ProjectY.BASE_NAME + "_ore");
		BRICKS = registerFlavor(event, ProjectY.BASE_NAME + "_bricks");
		BLOCK = registerFlavor(event, ProjectY.BASE_NAME + "_block");
		PLATE = registerFlavor(event, ProjectY.BASE_NAME + "_plate");
		PLATFORM = registerFlavor(event, ProjectY.BASE_NAME + "_platform");
		SHIELD = registerFlavor(event, ProjectY.BASE_NAME + "_shield");
	}

	private static Flavor registerFlavor(RegistryEvent.Register<Block> event, String name) {
		Flavor flavor = new Flavor();

		flavor.red = registerBasicBlock(event, name + "_red");
		flavor.blue = registerBasicBlock(event, name + "_blue");
		flavor.green = registerBasicBlock(event, name + "_green");
		flavor.light = registerBasicBlock(event, name + "_light");
		flavor.dark = registerBasicBlock(event, name + "_dark");

		return flavor;
	}

	private static Block registerBasicBlock(RegistryEvent.Register<Block> event, String name) {
		Block block = new Block(Material.IRON);
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
