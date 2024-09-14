package mczme.lingshi.data;

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
        addCookingFood(ModItems.RICE,8,25);
        addCookingFood(Items.POTATO,5,25);
        addCookingFood(Items.BEEF,5,20);
        addCookingFood(Items.PORKCHOP,5,20);
        addCookingFood(Items.MUTTON,5,20);
        addCookingFood(Items.CHICKEN,5,20);
        addCookingFood(Items.RABBIT,5,20);
        addCookingFood(Items.COD,5,20);
        addCookingFood(Items.SALMON,5,20);
        addCookingFood(Items.EGG,3,12);
        addCookingFood(ModItems.PIG_FEET,8,20);
        addCookingFood(ModItems.CABBAGE_LEAF.get(),3,12);
        addCookingFood(Items.CARROT,3,12);
        addCookingFood(Items.RED_MUSHROOM,3,12);
        addCookingFood(Items.BROWN_MUSHROOM,3,12);
        addCookingFood(Items.BEETROOT,3,12);
        addCookingFood(ModItems.NOODLES.get(),5,20);
        addCookingFood(ModItems.SLICED_PORK.get(),4,15);
        addCookingFood(ModItems.SLICED_BEEF.get(),4,15);
        addCookingFood(ModItems.BEAN.get(),8,22);
        addCookingFood(Items.SUGAR,2,20);

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
