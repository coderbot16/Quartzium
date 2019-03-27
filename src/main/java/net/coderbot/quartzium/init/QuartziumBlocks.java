package net.coderbot.quartzium.init;

import net.coderbot.quartzium.Quartzium;
import net.coderbot.quartzium.blocks.BlockCrystal;
import net.coderbot.quartzium.blocks.BlockLamp;
import net.coderbot.quartzium.blocks.functional.BlockFlame;
import net.coderbot.quartzium.blocks.functional.BlockIce;
import net.coderbot.quartzium.blocks.functional.BlockInferno;
import net.coderbot.quartzium.blocks.functional.BlockMagma;
import net.coderbot.quartzium.color.Color;
import net.coderbot.quartzium.color.ColorMap;
import net.coderbot.quartzium.blocks.BlockDecoration;
import net.coderbot.quartzium.blocks.BlockOre;
import net.coderbot.quartzium.color.PentaColor;
import net.coderbot.quartzium.color.PentaColorMap;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Quartzium.MODID)
public class QuartziumBlocks {
	public static PentaColorMap<BlockOre> ORE;
	public static ColorMap<Block> CRYSTAL;
	public static ColorMap<Block> BRICKS;
	public static ColorMap<Block> BLOCK;
	public static ColorMap<Block> PLATE;
	public static ColorMap<Block> PLATFORM;
	public static ColorMap<Block> SHIELD;
	public static ColorMap<Block> ENGINEERING_BRICKS;
	public static ColorMap<BlockLamp> LAMP;
	public static ColorMap<BlockLamp> INVERTED_LAMP;

	public static BlockIce ICE;
	public static BlockDecoration MAGMA;
	public static BlockDecoration LEVITATOR;
	public static BlockDecoration AQUIFER;
	public static BlockDecoration SUN;
	public static BlockDecoration SLIME;
	public static BlockDecoration FLAME;
	public static BlockDecoration LIQUID_VOID;
	public static BlockDecoration FLUX_VOID;
	public static BlockDecoration GUARDIAN;
	public static BlockDecoration ANCHOR;
	public static BlockDecoration FLOODGATE;
	public static BlockDecoration SOIL;
	public static BlockDecoration SAND;
	public static BlockDecoration INFERNO;
	public static BlockDecoration ITEM_VOID;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ORE = registerForPentaColors(event, Quartzium.BASE_NAME + "_ore", color ->
				new BlockOre(color.getColor())
		);

		CRYSTAL = registerForColors(event, Quartzium.BASE_NAME + "_crystal", color ->
				new BlockCrystal(color.getColor())
		);

		BRICKS = registerForColors(event, Quartzium.BASE_NAME + "_bricks", color ->
				new BlockDecoration(color.getColor(), 2.0F, 10.0F, false)
		);

		BLOCK = registerForColors(event, Quartzium.BASE_NAME + "_block", color ->
				new BlockDecoration(color.getColor(), 1.5F, 8.0F, true)
		);

		PLATE = registerForColors(event, Quartzium.BASE_NAME + "_plate", color ->
				new BlockDecoration(color.getColor(), 8.0F, 32.0F, true)
		);

		PLATFORM = registerForColors(event, Quartzium.BASE_NAME + "_platform", color ->
				new BlockDecoration(color.getColor(), 0.2F, 10.0F, false)
		);

		SHIELD = registerForColors(event, Quartzium.BASE_NAME + "_shield", color ->
				new BlockDecoration(color.getColor(), 16.0F, 112.0F, true)
		);

		ENGINEERING_BRICKS = registerForColors(event, Quartzium.BASE_NAME + "_engineering_bricks", color ->
				new BlockDecoration(color.getColor(), 2.0F, 10.0F, false)
		);

		LAMP = registerForColors(event, Quartzium.BASE_NAME + "_lamp", color ->
				new BlockLamp(color.getColor(), 0.3F, 0.5F, false)
		);

		INVERTED_LAMP = registerForColors(event, Quartzium.BASE_NAME + "_inverted_lamp", color ->
				new BlockLamp(color.getColor(), 0.3F, 0.5F, true)
		);

		ICE = registerBasicBlock(event, Quartzium.BASE_NAME + "_ice",
				new BlockIce(2.0F, 10.0F)
		);

		MAGMA = registerBasicBlock(event, Quartzium.BASE_NAME + "_magma",
				new BlockMagma(2.0F, 10.0F)
		);

		LEVITATOR = registerBasicBlock(event, Quartzium.BASE_NAME + "_levitator",
				new BlockDecoration(Color.MAGENTA.getColor(), 2.0F, 10.0F, false)
		);

		AQUIFER = registerBasicBlock(event, Quartzium.BASE_NAME + "_aquifer",
				new BlockDecoration(Color.LIGHT_BLUE.getColor(), 2.0F, 10.0F, false)
		);

		SUN = registerBasicBlock(event, Quartzium.BASE_NAME + "_sun",
				new BlockDecoration(Color.YELLOW.getColor(), 2.0F, 10.0F, false)
		);

		SLIME = registerBasicBlock(event, Quartzium.BASE_NAME + "_slime",
				new BlockDecoration(Color.PINK.getColor(), 2.0F, 10.0F, false)
		);

		FLAME = registerBasicBlock(event, Quartzium.BASE_NAME + "_flame",
				new BlockFlame(2.0F, 10.0F)
		);

		LIQUID_VOID = registerBasicBlock(event, Quartzium.BASE_NAME + "_liquid_void",
				new BlockDecoration(Color.GRAY.getColor(), 2.0F, 10.0F, false)
		);

		FLUX_VOID = registerBasicBlock(event, Quartzium.BASE_NAME + "_flux_void",
				new BlockDecoration(Color.LIGHT_GRAY.getColor(), 2.0F, 10.0F, false)
		);

		GUARDIAN = registerBasicBlock(event, Quartzium.BASE_NAME + "_guardian",
				new BlockDecoration(Color.PINK.getColor(), 2.0F, 10.0F, false)
		);

		ANCHOR = registerBasicBlock(event, Quartzium.BASE_NAME + "_anchor",
				new BlockDecoration(Color.PURPLE.getColor(), 2.0F, 10.0F, false)
		);

		FLOODGATE = registerBasicBlock(event, Quartzium.BASE_NAME + "_floodgate",
				new BlockDecoration(Color.PINK.getColor(), 2.0F, 10.0F, false)
		);

		SAND = registerBasicBlock(event, Quartzium.BASE_NAME + "_sand",
				new BlockDecoration(Color.BROWN.getColor(), 2.0F, 10.0F, false)
		);

		SOIL = registerBasicBlock(event, Quartzium.BASE_NAME + "_soil",
				new BlockDecoration(Color.GREEN.getColor(), 2.0F, 10.0F, false)
		);

		INFERNO = registerBasicBlock(event, Quartzium.BASE_NAME + "_inferno",
				new BlockInferno(2.0F, 10.0F)
		);

		ITEM_VOID = registerBasicBlock(event, Quartzium.BASE_NAME + "_item_void",
				new BlockDecoration(Color.BLACK.getColor(), 2.0F, 10.0F, false)
		);

		ICE.setInferno(INFERNO);
	}

	private static <T extends Block> ColorMap<T> registerForColors(RegistryEvent.Register<Block> event, String name, Function<Color, T> factory) {
		ColorMap<T> flavor = new ColorMap<>(factory);

		flavor.forEach((Color color, T block) -> registerBasicBlock(event, color.getName() + '_' + name, block));

		return flavor;
	}

	private static <T extends Block> PentaColorMap<T> registerForPentaColors(RegistryEvent.Register<Block> event, String name, Function<PentaColor, T> factory) {
		PentaColorMap<T> flavor = new PentaColorMap<>(factory);

		flavor.forEach((PentaColor color, T block) -> registerBasicBlock(event, color.getName() + '_' + name, block));

		return flavor;
	}

	private static <T extends Block> T registerBasicBlock(RegistryEvent.Register<Block> event, String name, T block) {
		block.setRegistryName(Quartzium.MODID, name);
		block.setUnlocalizedName(Quartzium.MODID + "." + name);
		block.setCreativeTab(Quartzium.TAB);

		event.getRegistry().register(block);

		return block;
	}
}
