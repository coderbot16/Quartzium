package net.coderbot.quartzium.init;

import net.coderbot.quartzium.Quartzium;
import net.coderbot.quartzium.client.SimpleBakedModelBright;
import net.coderbot.quartzium.color.ColorMap;
import net.coderbot.quartzium.color.PentaColorMap;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Quartzium.MODID)
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

		ICE = register(event, ModBlocks.ICE);
		MAGMA = register(event, ModBlocks.MAGMA);
		LEVITATOR = register(event, ModBlocks.LEVITATOR);
		AQUIFER = register(event, ModBlocks.AQUIFER);
		SUN = register(event, ModBlocks.SUN);
		SLIME = register(event, ModBlocks.SLIME);
		FLAME = register(event, ModBlocks.FLAME);
		LIQUID_VOID = register(event, ModBlocks.LIQUID_VOID);
		FLUX_VOID = register(event, ModBlocks.FLUX_VOID);
		GUARDIAN = register(event, ModBlocks.GUARDIAN);
		ANCHOR = register(event, ModBlocks.ANCHOR);
		FLOODGATE = register(event, ModBlocks.FLOODGATE);
		SAND = register(event, ModBlocks.SAND);
		SOIL = register(event, ModBlocks.SOIL);
		INFERNO = register(event, ModBlocks.INFERNO);
		ITEM_VOID = register(event, ModBlocks.ITEM_VOID);

		CRYSTAL.forEachPenta((color, item) -> ModBlocks.ORE.get(color).setCrystal(item));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if(FMLCommonHandler.instance().getSide() != Side.CLIENT) {
			return;
		}

		OBJLoader.INSTANCE.addDomain(Quartzium.MODID);

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

		registerModel(ICE);
		registerModel(MAGMA);
		registerModel(LEVITATOR);
		registerModel(AQUIFER);
		registerModel(SUN);
		registerModel(SLIME);
		registerModel(FLAME);
		registerModel(LIQUID_VOID);
		registerModel(FLUX_VOID);
		registerModel(GUARDIAN);
		registerModel(SAND);
		registerModel(SOIL);
		registerModel(FLOODGATE);
		registerModel(ANCHOR);
		registerModel(INFERNO);
		registerModel(ITEM_VOID);
	}

	@SubscribeEvent
	public static void patchBakedModels(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();

		ORE.forEach((color, item) -> patchModel(registry, item));
		CRYSTAL.forEach((color, item) -> patchModel(registry, item));
		BRICKS.forEach((color, item) -> patchModel(registry, item));
		BLOCK.forEach((color, item) -> patchModel(registry, item));
		PLATE.forEach((color, item) -> patchModel(registry, item));
		PLATFORM.forEach((color, item) -> patchModel(registry, item));
		SHIELD.forEach((color, item) -> patchModel(registry, item));
		ENGINEERING_BRICKS.forEach((color, item) -> patchModel(registry, item));
		LAMP.forEach((color, item) -> patchModel(registry, item));
		INVERTED_LAMP.forEach((color, item) -> patchModel(registry, item));

		patchModel(registry, ICE);
		patchModel(registry, MAGMA);
		patchModel(registry, LEVITATOR);
		patchModel(registry, AQUIFER);
		patchModel(registry, SUN);
		patchModel(registry, SLIME);
		patchModel(registry, FLAME);
		patchModel(registry, LIQUID_VOID);
		patchModel(registry, FLUX_VOID);
		patchModel(registry, GUARDIAN);
		patchModel(registry, SAND);
		patchModel(registry, SOIL);
		patchModel(registry, FLOODGATE);
		patchModel(registry, ANCHOR);
		patchModel(registry, INFERNO);
		patchModel(registry, ITEM_VOID);

		ModBlocks.ORE.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.CRYSTAL.forEach((color, block) -> patchModel(registry, block, "facing=up", "facing=down", "facing=east", "facing=west", "facing=south", "facing=north"));
		ModBlocks.BRICKS.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.BLOCK.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.PLATE.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.PLATFORM.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.SHIELD.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.ENGINEERING_BRICKS.forEach((color, block) -> patchModel(registry, block));
		ModBlocks.LAMP.forEach((color, block) -> patchModel(registry, block, "lit=true", "lit=false"));
		ModBlocks.INVERTED_LAMP.forEach((color, block) -> patchModel(registry, block, "lit=true", "lit=false"));

		patchModel(registry, ModBlocks.ICE);
		patchModel(registry, ModBlocks.MAGMA);
		patchModel(registry, ModBlocks.LEVITATOR);
		patchModel(registry, ModBlocks.AQUIFER);
		patchModel(registry, ModBlocks.SUN);
		patchModel(registry, ModBlocks.SLIME);
		patchModel(registry, ModBlocks.FLAME);
		patchModel(registry, ModBlocks.LIQUID_VOID);
		patchModel(registry, ModBlocks.FLUX_VOID);
		patchModel(registry, ModBlocks.GUARDIAN);
		patchModel(registry, ModBlocks.SAND);
		patchModel(registry, ModBlocks.SOIL);
		patchModel(registry, ModBlocks.FLOODGATE);
		patchModel(registry, ModBlocks.ANCHOR);
		patchModel(registry, ModBlocks.INFERNO);
		patchModel(registry, ModBlocks.ITEM_VOID);
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

	private static void patchModel(IRegistry<ModelResourceLocation, IBakedModel> registry, Item item) {
		ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), "inventory");

		IBakedModel model = registry.getObject(location);
		registry.putObject(location, new SimpleBakedModelBright(model, new HashSet<>()));
	}

	private static final String[] NORMAL = new String[] { "normal" };
	private static void patchModel(IRegistry<ModelResourceLocation, IBakedModel> registry, Block block, String... variants) {
		if(variants.length == 0) {
			variants = NORMAL;
		}

		for(String variant: variants) {
			ModelResourceLocation location = new ModelResourceLocation(block.getRegistryName(), variant);

			IBakedModel model = registry.getObject(location);

			if(model==null) {
				System.out.println("Couldn't get block model for: "+location);
				continue;
			}

			registry.putObject(location, new SimpleBakedModelBright(model, new HashSet<>()));
		}
	}
}
