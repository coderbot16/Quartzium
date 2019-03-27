package net.coderbot.quartzium.init;

import net.coderbot.quartzium.Quartzium;
import net.coderbot.quartzium.color.ColorMap;
import net.coderbot.quartzium.color.PentaColorMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Quartzium.MODID)
public class QuartziumItems {
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

	public static Item ICE;
	public static Item MAGMA;
	public static Item LEVITATOR;
	public static Item AQUIFER;
	public static Item SUN;
	public static Item SLIME;
	public static Item FLAME;
	public static Item LIQUID_VOID;
	public static Item FLUX_VOID;
	public static Item GUARDIAN;
	public static Item ANCHOR;
	public static Item FLOODGATE;
	public static Item SAND;
	public static Item SOIL;
	public static Item INFERNO;
	public static Item ITEM_VOID;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ORE = QuartziumBlocks.ORE.map(block -> register(event, block));
		CRYSTAL = QuartziumBlocks.CRYSTAL.map(block -> register(event, block));
		BRICKS = QuartziumBlocks.BRICKS.map(block -> register(event, block));
		BLOCK = QuartziumBlocks.BLOCK.map(block -> register(event, block));
		PLATE = QuartziumBlocks.PLATE.map(block -> register(event, block));
		PLATFORM = QuartziumBlocks.PLATFORM.map(block -> register(event, block));
		SHIELD = QuartziumBlocks.SHIELD.map(block -> register(event, block));
		ENGINEERING_BRICKS = QuartziumBlocks.ENGINEERING_BRICKS.map(block -> register(event, block));
		LAMP = QuartziumBlocks.LAMP.map(block -> register(event, block));
		INVERTED_LAMP = QuartziumBlocks.INVERTED_LAMP.map(block -> register(event, block));

		ICE = register(event, QuartziumBlocks.ICE);
		MAGMA = register(event, QuartziumBlocks.MAGMA);
		LEVITATOR = register(event, QuartziumBlocks.LEVITATOR);
		AQUIFER = register(event, QuartziumBlocks.AQUIFER);
		SUN = register(event, QuartziumBlocks.SUN);
		SLIME = register(event, QuartziumBlocks.SLIME);
		FLAME = register(event, QuartziumBlocks.FLAME);
		LIQUID_VOID = register(event, QuartziumBlocks.LIQUID_VOID);
		FLUX_VOID = register(event, QuartziumBlocks.FLUX_VOID);
		GUARDIAN = register(event, QuartziumBlocks.GUARDIAN);
		ANCHOR = register(event, QuartziumBlocks.ANCHOR);
		FLOODGATE = register(event, QuartziumBlocks.FLOODGATE);
		SAND = register(event, QuartziumBlocks.SAND);
		SOIL = register(event, QuartziumBlocks.SOIL);
		INFERNO = register(event, QuartziumBlocks.INFERNO);
		ITEM_VOID = register(event, QuartziumBlocks.ITEM_VOID);

		CRYSTAL.forEachPenta((color, item) -> QuartziumBlocks.ORE.get(color).setCrystal(item));
	}

	private static Item register(RegistryEvent.Register<Item> event, Block base) {
		Item item = new ItemBlock(base);
		item.setRegistryName(base.getRegistryName());
		item.setUnlocalizedName(base.getUnlocalizedName());
		event.getRegistry().register(item);

		return item;
	}
}
