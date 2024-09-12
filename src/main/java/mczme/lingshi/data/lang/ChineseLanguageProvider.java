package mczme.lingshi.data.lang;

import mczme.lingshi.common.registry.ModEffects;
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
        this.add("lingshi.lingshi_tab", "零食");

        this.add(ModItems.RICE.get(), "稻米");
        this.add(ModItems.RICE_SEEDLING.get(), "稻苗");
        this.add(ModItems.RICE_OF_EAR.get(),"稻穗");
        this.add(ModItems.CABBAGE_SEED.get(), "白菜种子");
        this.add(ModItems.CABBAGE.get(), "白菜");
        this.add(ModItems.CABBAGE_LEAF.get(), "白菜叶");

        this.add(ModItems.TEA_TREE.get(), "茶树");
        this.add(ModItems.TEA_LEAF.get(), "茶叶");

        this.add(ModItems.CHOPPING_BOARD.get(), "砧板");
        this.add(ModItems.IRON_KNIFE.get(), "铁菜刀");

        this.add("gui.lingshi.skillet_menu","平底锅");
        this.add(ModItems.SKILLET.get(), "平底锅");
        this.add(ModItems.SPATULA.get(),"锅铲");
        this.add("gui.lingshi.cooking_pot_menu","烹饪锅");
        this.add(ModItems.COOKING_POT.get(), "烹饪锅");
        this.add(ModItems.POT_LID.get(),"锅盖");
        this.add(ModItems.GLASS_JAR.get(), "玻璃罐");

        this.add(ModItems.STOVE.get(), "炉灶");
        this.add(ModItems.OIL_BUCKET.get(),"油桶");
//      食物
        this.add(ModItems.FRIED_EGG.get(),"煎鸡蛋");
        this.add(ModItems.COOKED_RICE.get(),"米饭");
        this.add(ModItems.EGG_FRIED_RICE.get(), "蛋炒饭");
        this.add(ModItems.FLOUR.get(), "面粉");
        this.add(ModItems.DOUGH.get(), "面团");
        this.add(ModItems.NOODLES.get(), "面条");
        this.add(ModItems.PIG_FEET.get(), "猪蹄");
        this.add(ModItems.PORK_FEET_RICE.get(), "猪脚饭");
        this.add(ModItems.SAUTEED_SEASONAL_VEGETABLE.get(),"炒时蔬");
        this.add(ModItems.STEWED_NOODLES.get(), "煮面条");
        this.add("item.lingshi.egg_added_stewed_noodles","加蛋煮面条");
        this.add(ModItems.FRIED_FISH.get(), "煎鱼");
        this.add(ModItems.SLICED_PORK.get(), "切好的猪肉");
        this.add(ModItems.SLICED_BEEF.get(), "切好的牛肉");

        this.add(ModEffects.GRATIFICATION_EFFECT.get(),"满足");

    }
}
