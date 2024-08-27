package mczme.lingshi.common.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.data.builder.CookingPotRecipeBuilder;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.lingshi;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

import static mczme.lingshi.common.data.recipe.Recipes.has;

public class CookingPotRecipeDataGen {
    public CookingPotRecipeDataGen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),8),CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice"));
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),10),CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice_1"));
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),12),CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice_2"));

    }

    private CookingPotRecipeBuilder build(List<Ingredient> items, FluidStack fluid, ItemStack result, CookingFoodRecipeLabel label){
        return new CookingPotRecipeBuilder(items, fluid, result).setLabel(label);
    }

    private ResourceLocation create(String s){
        return ResourceLocation.fromNamespaceAndPath(lingshi.MODID,s);
    }

}
