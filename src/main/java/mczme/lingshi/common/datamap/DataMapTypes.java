package mczme.lingshi.common.datamap;

import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class DataMapTypes {
    public static final DataMapType<Item, CookingFoodData> COOKING_FOOD = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_food"),
            Registries.ITEM, CookingFoodData.CODEC
    ).build();

}
