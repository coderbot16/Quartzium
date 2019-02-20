package net.coderbot.projecty.init;

import net.coderbot.projecty.ProjectY;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ProjectY.MODID)
public class ModItems {
	public static FlavorItem ORE;
	public static FlavorItem BRICKS;
	public static FlavorItem BLOCK;
	public static FlavorItem PLATE;
	public static FlavorItem PLATFORM;
	public static FlavorItem SHIELD;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ORE = registerFlavor(event, ModBlocks.ORE);
		BRICKS = registerFlavor(event, ModBlocks.BRICKS);
		BLOCK = registerFlavor(event, ModBlocks.BLOCK);
		PLATE = registerFlavor(event, ModBlocks.PLATE);
		PLATFORM = registerFlavor(event, ModBlocks.PLATFORM);
		SHIELD = registerFlavor(event, ModBlocks.SHIELD);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if(FMLCommonHandler.instance().getSide() != Side.CLIENT) {
			return;
		}

		registerFlavorModels(ORE);
		registerFlavorModels(BRICKS);
		registerFlavorModels(BLOCK);
		registerFlavorModels(PLATE);
		registerFlavorModels(PLATFORM);
		registerFlavorModels(SHIELD);
	}

	private static FlavorItem registerFlavor(RegistryEvent.Register<Item> event, ModBlocks.Flavor base) {
		FlavorItem flavor = new FlavorItem();

		flavor.red = register(event, base.red);
		flavor.blue = register(event, base.blue);
		flavor.green = register(event, base.green);
		flavor.light = register(event, base.light);
		flavor.dark = register(event, base.dark);

		return flavor;
	}

	private static void registerFlavorModels(FlavorItem flavor) {
		registerModel(flavor.red);
		registerModel(flavor.blue);
		registerModel(flavor.green);
		registerModel(flavor.light);
		registerModel(flavor.dark);
	}

	private static Item register(RegistryEvent.Register<Item> event, Block base) {
		Item item = new ItemBlock(base);
		item.setRegistryName(base.getRegistryName());
		item.setUnlocalizedName(base.getUnlocalizedName());
		event.getRegistry().register(item);

		return item;
	}

	private static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static class FlavorItem {
		public Item red;
		public Item blue;
		public Item green;
		public Item light;
		public Item dark;
	}
}
