package net.coderbot.projecty;

import net.coderbot.projecty.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ProjectY.MODID, name = ProjectY.NAME, version = ProjectY.VERSION)
public class ProjectY
{
    public static final String MODID = "projecty";
    public static final String NAME = "ProjectY";
    public static final String VERSION = "1.0.0";

    public static final String BASE_NAME = "xychronite";

    public static CreativeTabs TAB = new CreativeTabs("projecty") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.BRICKS.blue);
        }
    };

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("REKT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
