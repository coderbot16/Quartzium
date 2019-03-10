package net.coderbot.quartzium;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=Quartzium.MODID)
public class QuartziumConfig {
	@Config.Comment("Ore configuration (requires restart)")
	public static final Ores ORES = new Ores();

	public static class Ores {
		@Config.Comment("Size of the white ore vein")
		public int whiteSize = 9;

		@Config.Comment("Size of the blue ore vein")
		public int blueSize = 9;

		@Config.Comment("Size of the green ore vein")
		public int greenSize = 9;

		@Config.Comment("Size of the red ore vein")
		public int redSize = 9;

		@Config.Comment("Size of the black ore vein")
		public int blackSize = 9;

		@Config.Comment("How many white veins per chunk")
		public int whiteCount = 6;

		@Config.Comment("How many blue veins per chunk")
		public int blueCount = 6;

		@Config.Comment("How many green veins per chunk")
		public int greenCount = 6;

		@Config.Comment("How many red veins per chunk")
		public int redCount = 6;

		@Config.Comment("How many black veins per chunk")
		public int blackCount = 6;
	}

	@Mod.EventBusSubscriber(modid = Quartzium.MODID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Quartzium.MODID)) {
				ConfigManager.sync(Quartzium.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
