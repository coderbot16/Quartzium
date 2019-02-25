package net.coderbot.projecty.init;

import net.coderbot.projecty.ProjectY;
import net.coderbot.projecty.color.ColorMap;
import net.coderbot.projecty.color.PentaColorMap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ProjectY.MODID)
public class ModItems {
	public static PentaColorMap<Item> ORE;
	public static ColorMap<Item> CRYSTAL;
	public static ColorMap<Item> BRICKS;
	public static ColorMap<Item> BLOCK;
	public static ColorMap<Item> PLATE;
	public static ColorMap<Item> PLATFORM;
	public static ColorMap<Item> SHIELD;
	public static ColorMap<Item> ENGINEERING_BRICKS;
	public static ColorMap<Item> LAMP;
	public static ColorMap<Item> INVERTED_LAMP;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ORE = ModBlocks.ORE.map(block -> register(event, block));
		CRYSTAL = ModBlocks.CRYSTAL.map(block -> register(event, block));
		BRICKS = ModBlocks.BRICKS.map(block -> register(event, block));
		BLOCK = ModBlocks.BLOCK.map(block -> register(event, block));
		PLATE = ModBlocks.PLATE.map(block -> register(event, block));
		PLATFORM = ModBlocks.PLATFORM.map(block -> register(event, block));
		SHIELD = ModBlocks.SHIELD.map(block -> register(event, block));
		ENGINEERING_BRICKS = ModBlocks.ENGINEERING_BRICKS.map(block -> register(event, block));
		LAMP = ModBlocks.LAMP.map(block -> register(event, block));
		INVERTED_LAMP = ModBlocks.INVERTED_LAMP.map(block -> register(event, block));

		CRYSTAL.forEachPenta((color, item) -> ModBlocks.ORE.get(color).setCrystal(item));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if(FMLCommonHandler.instance().getSide() != Side.CLIENT) {
			return;
		}

		OBJLoader.INSTANCE.addDomain(ProjectY.MODID);

		ORE.forEach((color, item) -> registerModel(item));
		CRYSTAL.forEach((color, item) -> registerModel(item));
		BRICKS.forEach((color, item) -> registerModel(item));
		BLOCK.forEach((color, item) -> registerModel(item));
		PLATE.forEach((color, item) -> registerModel(item));
		PLATFORM.forEach((color, item) -> registerModel(item));
		SHIELD.forEach((color, item) -> registerModel(item));
		ENGINEERING_BRICKS.forEach((color, item) -> registerModel(item));
		LAMP.forEach((color, item) -> registerModel(item));
		INVERTED_LAMP.forEach((color, item) -> registerModel(item));
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
}
