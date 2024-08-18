package mczme.lingshi.common.datamap;

import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class DataMapTypes {
    public static final DataMapType<Item, CookingFoodData> COOKING_FOOD_ITEM = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_food_item"),
            Registries.ITEM, CookingFoodData.CODEC
    ).build();

    public static final DataMapType<Fluid, CookingFoodData> COOKING_FOOD_FLUID = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_food_fliud"),
            Registries.FLUID, CookingFoodData.CODEC
    ).build();

}
