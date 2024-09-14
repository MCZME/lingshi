package mczme.lingshi.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.data.builder.CookingPotRecipeBuilder;
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

import static mczme.lingshi.data.recipe.Recipes.has;

public class CookingPotRecipeDataGen {
    public CookingPotRecipeDataGen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
//        煮
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),8)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get())).group("rice")
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice"));
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),10)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get())).group("rice")
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice_1"));
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.COOKED_RICE.get(),12)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_rice", has(ModItems.RICE.get())).group("rice")
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("cooked_rice_2"));
        build(List.of(Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(ModItems.NOODLES.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.STEWED_NOODLES.get(),6)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_noodles", has(ModItems.NOODLES.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("stewed_noodles"));
        build(List.of(Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(Items.EGG)),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.EGG_ADDED_STEWED_NOODLES.get(),6)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_noodles", has(ModItems.NOODLES.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("egg_added_stewed_noodles"));
        build(List.of(Ingredient.of(ModItems.BEAN.get()),Ingredient.of(ModItems.BEAN.get()),Ingredient.of(ModItems.BEAN.get()),Ingredient.of(Items.SUGAR)),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.SOYBEAN_MILK.get(),4)
                ,CookingFoodRecipeLabel.BOIL)
                .unlockedBy("has_bean", has(ModItems.BEAN.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.GLASS_BOTTLE),0))
                .save(output,create("soybean_milk"));
//        炖
        build(List.of(Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.RICE.get()),Ingredient.of(ModItems.CABBAGE_LEAF.get()),Ingredient.of(ModItems.PIG_FEET.get())),new FluidStack(Fluids.WATER,1000),new ItemStack(ModItems.PORK_FEET_RICE.get(),2)
                ,CookingFoodRecipeLabel.STEW)
                .unlockedBy("has_pig_feet", has(ModItems.PIG_FEET.get()))
                .setContainer(new CookingPotRecipe.CookingPotContainer(new ItemStack(Items.BOWL),5))
                .save(output,create("pork_feet_rice"));
//        油炸

//        其他

    }

    private CookingPotRecipeBuilder build(List<Ingredient> items, FluidStack fluid, ItemStack result, CookingFoodRecipeLabel label){
        return new CookingPotRecipeBuilder(items, fluid, result).setLabel(label);
    }

    private ResourceLocation create(String s){
        return ResourceLocation.fromNamespaceAndPath(lingshi.MODID,s);
    }

}
