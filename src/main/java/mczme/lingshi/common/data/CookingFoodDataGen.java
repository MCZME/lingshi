package mczme.lingshi.common.data;

import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static mczme.lingshi.common.datamap.DataMapTypes.COOKING_FOOD_FLUID;
import static mczme.lingshi.common.datamap.DataMapTypes.COOKING_FOOD_ITEM;

public class CookingFoodDataGen extends DataMapProvider {

    protected CookingFoodDataGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        addCookingFood(Items.APPLE,5,15);
        addCookingFood(ModItems.RICE,15,25);
        addCookingFood(Items.IRON_BLOCK,5,20);
        addCookingFood(Items.GOLD_BLOCK,10,20);
        addCookingFood(Fluids.WATER,15);
        builder(COOKING_FOOD_FLUID).add(ModFluids.OIL_SOURCE.get().builtInRegistryHolder(), new CookingFoodData(8,30),false);

    }

    protected void addCookingFood(Item item , int cookedTime,int burntTime){
        builder(COOKING_FOOD_ITEM).add(ResourceLocation.parse(item.toString()),new CookingFoodData(cookedTime,burntTime),false);
    }

    protected void addCookingFood(Supplier<Item> item , int cookedTime,int burntTime){
        builder(COOKING_FOOD_ITEM).add(ResourceLocation.parse(item.get().toString()),new CookingFoodData(cookedTime,burntTime),false);
    }

    protected void addCookingFood(TagKey<Item> tag, int cookedTime,int burntTime){
        builder(COOKING_FOOD_ITEM).add(tag,new CookingFoodData(cookedTime,burntTime),false);
    }

    protected void addCookingFood(Fluid fluid , int cookedTime){
        builder(COOKING_FOOD_FLUID).add(fluid.builtInRegistryHolder(),new CookingFoodData(cookedTime,30),false);
    }

}
