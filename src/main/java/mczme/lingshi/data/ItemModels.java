package mczme.lingshi.data;

import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.lingshi;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, lingshi.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.RICE.get());
        basicItem(ModItems.RICE_OF_EAR.get());
        basicItem(ModItems.RICE_SEEDLING.get());
        basicItem(ModItems.CABBAGE_SEED.get());
        basicItem(ModItems.CABBAGE.get());
        basicItem(ModItems.CABBAGE_LEAF.get());
        basicItem(ModItems.BEAN.get());
        basicItem(ModItems.POD.get());
        basicItem(ModItems.PORK_FEET_RICE.get());
        basicItem(ModItems.SAUTEED_SEASONAL_VEGETABLE.get());
        basicItem(ModItems.STEWED_NOODLES.get());
        basicItem(ModItems.EGG_ADDED_STEWED_NOODLES.get());
        basicItem(ModItems.FRIED_FISH.get());

        basicItem(ModItems.FRIED_EGG.get());
        basicItem(ModItems.COOKED_RICE.get());
        basicItem(ModItems.EGG_FRIED_RICE.get());
        basicItem(ModItems.FLOUR.get());
        basicItem(ModItems.DOUGH.get());
        basicItem(ModItems.NOODLES.get());
        basicItem(ModItems.PIG_FEET.get());
        basicItem(ModItems.SLICED_PORK.get());
        basicItem(ModItems.SLICED_BEEF.get());

        basicItem(ModItems.OIL_BUCKET.get());

        withExistingParent(String.valueOf(ModItems.STOVE.get()), modLoc("block/stove"));
        withExistingParent(String.valueOf(ModItems.COOKING_POT.get()), modLoc("block/cooking_pot"));
        withExistingParent(String.valueOf(ModItems.CHOPPING_BOARD.get()), modLoc("block/chopping_board"));
        withExistingParent(String.valueOf(ModItems.SKILLET.get()), modLoc("block/skillet"));
        withExistingParent(String.valueOf(ModItems.IRON_KNIFE.get()),mcLoc("item/handheld")).texture("layer0", modLoc("item/iron_knife"));
        withExistingParent(String.valueOf(ModItems.SPATULA.get()),mcLoc("item/handheld")).texture("layer0", modLoc("item/spatula"));
        withExistingParent(String.valueOf(ModItems.GLASS_JAR.get()),modLoc("block/glass_jar"));
    }
}
