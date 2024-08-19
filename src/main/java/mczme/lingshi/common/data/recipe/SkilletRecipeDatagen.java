package mczme.lingshi.common.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.data.builder.SkilletRecipeBuilder;
import mczme.lingshi.common.recipe.SkilletRecipe;
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
        build(List.of(Ingredient.of(ModItems.RICE.get())), null, new ItemStack(Items.APPLE.asItem()))
                .setLabel(CookingFoodRecipeLabel.PAN_FRY)
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),0)).save(output,"rice_apple");
        build(List.of(Ingredient.of(Items.GOLD_BLOCK),Ingredient.of(Items.IRON_BLOCK)), null, new ItemStack(Items.DIAMOND.asItem()))
                .setLabel(CookingFoodRecipeLabel.BOIL).save(output,"test01");
        build(List.of(Ingredient.of(Items.DIAMOND_BLOCK),Ingredient.of(Items.MILK_BUCKET)), null, new ItemStack(Items.WHEAT.asItem()))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,"test02");
    }

    private SkilletRecipeBuilder build(List<Ingredient> items, FluidStack fluid, ItemStack result){
       return new SkilletRecipeBuilder(items, fluid, result).group("Skillet");
    }
}
