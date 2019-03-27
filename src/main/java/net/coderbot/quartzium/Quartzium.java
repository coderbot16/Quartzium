package net.coderbot.quartzium;

import net.coderbot.quartzium.color.Color;
import net.coderbot.quartzium.init.QuartziumItems;
import net.coderbot.quartzium.world.QuartziumWorldGen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Quartzium.MODID, name = Quartzium.NAME, version = Quartzium.VERSION)
public class Quartzium
{
    public static final String MODID = "quartzium";
    public static final String NAME = "Quartzium";
    public static final String VERSION = "1.0.1";

    public static final String BASE_NAME = "quartzium";

    public static CreativeTabs TAB = new CreativeTabs("quartzium") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(QuartziumItems.BRICKS.get(Color.BLUE));
        }
    };

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        QuartziumItems.ORE.forEach((color, ore) ->
                GameRegistry.addSmelting(ore, new ItemStack(QuartziumItems.CRYSTAL.get(color), 3), 1)
        );

        GameRegistry.registerWorldGenerator(new QuartziumWorldGen(), 0);
    }
}
