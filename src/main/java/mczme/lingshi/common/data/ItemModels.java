package mczme.lingshi.common.data;

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

        withExistingParent(String.valueOf(ModItems.CHOPPING_BOARD.get()), modLoc("block/chopping_board"));
        withExistingParent(String.valueOf(ModItems.IRON_KNIFE.get()),mcLoc("item/handheld")).texture("layer0", modLoc("item/iron_knife"));


    }
}