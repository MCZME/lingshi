package mczme.lingshi.common.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.data.builder.SkilletRecipeBuilder;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class SkilletRecipeDatagen {
    public SkilletRecipeDatagen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
        build(List.of(Ingredient.of(Items.DIAMOND),Ingredient.of(Items.IRON_BLOCK)), List.of(),
                new ItemStack(Items.APPLE.asItem()), output,"test00", CookingFoodRecipeLabel.PAN_FRY);
        build(List.of(Ingredient.of(Items.GOLD_BLOCK),Ingredient.of(Items.IRON_BLOCK)), List.of(),
                new ItemStack(Items.DIAMOND.asItem()), output,"test01",CookingFoodRecipeLabel.BOIL);
        build(List.of(Ingredient.of(Items.DIAMOND_BLOCK),Ingredient.of(Items.MILK_BUCKET)), List.of(),
                new ItemStack(Items.WHEAT.asItem()), output,"test02", CookingFoodRecipeLabel.HEAT);
    }

    private void build(List<Ingredient> items, List<FluidStack> fluids, ItemStack result, RecipeOutput output, String pid, CookingFoodRecipeLabel label){
        new SkilletRecipeBuilder(items, fluids, result).setLabel(label).group("Skillet").setLabel(label).unlockedBy(result.toString(),Recipes.has(ModItems.SKILLET.get())).save(output,pid);
    }
}
