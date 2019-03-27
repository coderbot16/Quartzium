package net.coderbot.quartzium.init;

import net.coderbot.quartzium.Quartzium;
import net.coderbot.quartzium.client.SimpleBakedModelBright;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Quartzium.MODID)
public class QuartziumModels {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		if(FMLCommonHandler.instance().getSide() != Side.CLIENT) {
			return;
		}

		OBJLoader.INSTANCE.addDomain(Quartzium.MODID);

		QuartziumItems.ORE.forEach((color, item) -> registerModel(item));
		QuartziumItems.CRYSTAL.forEach((color, item) -> registerModel(item));
		QuartziumItems.BRICKS.forEach((color, item) -> registerModel(item));
		QuartziumItems.BLOCK.forEach((color, item) -> registerModel(item));
		QuartziumItems.PLATE.forEach((color, item) -> registerModel(item));
		QuartziumItems.PLATFORM.forEach((color, item) -> registerModel(item));
		QuartziumItems.SHIELD.forEach((color, item) -> registerModel(item));
		QuartziumItems.ENGINEERING_BRICKS.forEach((color, item) -> registerModel(item));
		QuartziumItems.LAMP.forEach((color, item) -> registerModel(item));
		QuartziumItems.INVERTED_LAMP.forEach((color, item) -> registerModel(item));

		registerModel(QuartziumItems.ICE);
		registerModel(QuartziumItems.MAGMA);
		registerModel(QuartziumItems.LEVITATOR);
		registerModel(QuartziumItems.AQUIFER);
		registerModel(QuartziumItems.SUN);
		registerModel(QuartziumItems.SLIME);
		registerModel(QuartziumItems.FLAME);
		registerModel(QuartziumItems.LIQUID_VOID);
		registerModel(QuartziumItems.FLUX_VOID);
		registerModel(QuartziumItems.GUARDIAN);
		registerModel(QuartziumItems.SAND);
		registerModel(QuartziumItems.SOIL);
		registerModel(QuartziumItems.FLOODGATE);
		registerModel(QuartziumItems.ANCHOR);
		registerModel(QuartziumItems.INFERNO);
		registerModel(QuartziumItems.ITEM_VOID);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void patchBakedModels(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();

		QuartziumItems.ORE.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.CRYSTAL.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.BRICKS.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.BLOCK.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.PLATE.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.PLATFORM.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.SHIELD.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.ENGINEERING_BRICKS.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.LAMP.forEach((color, item) -> patchModel(registry, item));
		QuartziumItems.INVERTED_LAMP.forEach((color, item) -> patchModel(registry, item));

		patchModel(registry, QuartziumItems.ICE);
		patchModel(registry, QuartziumItems.MAGMA);
		patchModel(registry, QuartziumItems.LEVITATOR);
		patchModel(registry, QuartziumItems.AQUIFER);
		patchModel(registry, QuartziumItems.SUN);
		patchModel(registry, QuartziumItems.SLIME);
		patchModel(registry, QuartziumItems.FLAME);
		patchModel(registry, QuartziumItems.LIQUID_VOID);
		patchModel(registry, QuartziumItems.FLUX_VOID);
		patchModel(registry, QuartziumItems.GUARDIAN);
		patchModel(registry, QuartziumItems.SAND);
		patchModel(registry, QuartziumItems.SOIL);
		patchModel(registry, QuartziumItems.FLOODGATE);
		patchModel(registry, QuartziumItems.ANCHOR);
		patchModel(registry, QuartziumItems.INFERNO);
		patchModel(registry, QuartziumItems.ITEM_VOID);

		QuartziumBlocks.ORE.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.CRYSTAL.forEach((color, block) -> patchModel(registry, block, "facing=up", "facing=down", "facing=east", "facing=west", "facing=south", "facing=north"));
		QuartziumBlocks.BRICKS.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.BLOCK.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.PLATE.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.PLATFORM.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.SHIELD.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.ENGINEERING_BRICKS.forEach((color, block) -> patchModel(registry, block));
		QuartziumBlocks.LAMP.forEach((color, block) -> patchModel(registry, block, "lit=true", "lit=false"));
		QuartziumBlocks.INVERTED_LAMP.forEach((color, block) -> patchModel(registry, block, "lit=true", "lit=false"));

		patchModel(registry, QuartziumBlocks.ICE);
		patchModel(registry, QuartziumBlocks.MAGMA);
		patchModel(registry, QuartziumBlocks.LEVITATOR);
		patchModel(registry, QuartziumBlocks.AQUIFER);
		patchModel(registry, QuartziumBlocks.SUN);
		patchModel(registry, QuartziumBlocks.SLIME);
		patchModel(registry, QuartziumBlocks.FLAME);
		patchModel(registry, QuartziumBlocks.LIQUID_VOID);
		patchModel(registry, QuartziumBlocks.FLUX_VOID);
		patchModel(registry, QuartziumBlocks.GUARDIAN);
		patchModel(registry, QuartziumBlocks.SAND);
		patchModel(registry, QuartziumBlocks.SOIL);
		patchModel(registry, QuartziumBlocks.FLOODGATE);
		patchModel(registry, QuartziumBlocks.ANCHOR);
		patchModel(registry, QuartziumBlocks.INFERNO);
		patchModel(registry, QuartziumBlocks.ITEM_VOID);
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
