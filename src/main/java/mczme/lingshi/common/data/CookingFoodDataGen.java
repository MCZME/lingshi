package mczme.lingshi.common.data;

import mczme.lingshi.common.datamap.ingredient.CookingFoodData;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static mczme.lingshi.common.datamap.DataMapTypes.COOKING_FOOD;

public class CookingFoodDataGen extends DataMapProvider {

    protected CookingFoodDataGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        addCookingFood(Items.APPLE,5);
        addCookingFood(ModItems.RICE,15);
    }

    protected void addCookingFood(Item item , float time){
        builder(COOKING_FOOD).add(ResourceLocation.parse(item.toString()),new CookingFoodData(time),false);
    }

    protected void addCookingFood(Supplier<Item> item , float time){
        builder(COOKING_FOOD).add(ResourceLocation.parse(item.get().toString()),new CookingFoodData(time),false);
    }

    protected void addCookingFood(TagKey<Item> tag, float time){
        builder(COOKING_FOOD).add(tag,new CookingFoodData(time),false);
    }
}
