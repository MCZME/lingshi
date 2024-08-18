package mczme.lingshi.common.data.lang;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import static mczme.lingshi.lingshi.MODID;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add("lingshi.lingshi_tab", "灵食");

        this.add(ModItems.RICE.get(), "稻米");
        this.add(ModItems.RICE_SEEDLING.get(), "稻苗");
        this.add(ModItems.RICE_OF_EAR.get(),"稻穗");

        this.add(ModItems.TEA_TREE.get(), "茶树");
        this.add(ModItems.TEA_LEAF.get(), "茶叶");

        this.add(ModItems.CHOPPING_BOARD.get(), "砧板");
        this.add(ModItems.IRON_KNIFE.get(), "铁菜刀");

        this.add(ModItems.SKILLET.get(), "平底锅");

        this.add(ModItems.OIL_BUCKET.get(),"油桶");

        this.add("gui.lingshi.skillet_menu","平底锅");
    }
}
