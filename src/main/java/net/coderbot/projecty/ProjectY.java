package net.coderbot.projecty;

import net.coderbot.projecty.color.Color;
import net.coderbot.projecty.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

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
            return new ItemStack(ModItems.BRICKS.get(Color.BLUE));
        }
    };
}
