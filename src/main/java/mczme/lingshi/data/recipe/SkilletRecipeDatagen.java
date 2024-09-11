package mczme.lingshi.data.recipe;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.data.builder.SkilletRecipeBuilder;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.lingshi;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

import static mczme.lingshi.data.recipe.Recipes.has;
import static mczme.lingshi.common.tag.NeoforgeTags.CROPS_CABBAGE;

public class SkilletRecipeDatagen {
    public SkilletRecipeDatagen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
//       加热
        build(List.of(Ingredient.of(Items.POTATO)),null,new ItemStack(Items.BAKED_POTATO))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("baked_potato"));
        build(List.of(Ingredient.of(Items.BEEF)),null,new ItemStack(Items.COOKED_BEEF))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_beef"));
        build(List.of(Ingredient.of(Items.PORKCHOP)),null,new ItemStack(Items.COOKED_PORKCHOP))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_porkchop"));
        build(List.of(Ingredient.of(Items.MUTTON)),null,new ItemStack(Items.COOKED_MUTTON))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_mutton"));
        build(List.of(Ingredient.of(Items.CHICKEN)),null,new ItemStack(Items.COOKED_CHICKEN))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_chicken"));
        build(List.of(Ingredient.of(Items.RABBIT)),null,new ItemStack(Items.COOKED_RABBIT))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_rabbit"));
        build(List.of(Ingredient.of(Items.COD)),null,new ItemStack(Items.COOKED_COD))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_cod"));
        build(List.of(Ingredient.of(Items.SALMON)),null,new ItemStack(Items.COOKED_SALMON))
                .setLabel(CookingFoodRecipeLabel.HEAT).save(output,create("cooked_salmon"));
        // 煎
        build(List.of(Ingredient.of(Items.EGG)),new FluidStack(ModFluids.OIL_SOURCE.get(),250),new ItemStack(ModItems.FRIED_EGG.get()))
                .setLabel(CookingFoodRecipeLabel.PAN_FRY).save(output,create("fried_egg"));
        build(List.of(Ingredient.of(Items.COD)),new FluidStack(ModFluids.OIL_SOURCE.get(),250),new ItemStack(ModItems.FRIED_FISH.get()))
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),1))
                .setLabel(CookingFoodRecipeLabel.PAN_FRY).save(output,create("fried_fish"));
        //  炒
        build(List.of(Ingredient.of(Items.EGG),Ingredient.of(ModItems.RICE.get())),new FluidStack(ModFluids.OIL_SOURCE.get(),100),new ItemStack(ModItems.EGG_FRIED_RICE.get()))
                .setLabel(CookingFoodRecipeLabel.STIR_FRY)
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),3))
                .save(output,create("egg_fried_rice"));
        build(List.of(Ingredient.of(CROPS_CABBAGE),Ingredient.of(Items.CARROT),Ingredient.of(Items.BEETROOT),Ingredient.of(Tags.Items.MUSHROOMS)),new FluidStack(ModFluids.OIL_SOURCE.get(),100),new ItemStack(ModItems.SAUTEED_SEASONAL_VEGETABLE.get()))
                .setLabel(CookingFoodRecipeLabel.STIR_FRY)
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),2))
                .save(output,create("sauteed_seasonal_vegetable"));
        //  煮
        build(List.of(Ingredient.of(ModItems.NOODLES.get())),new FluidStack(Fluids.WATER,250),new ItemStack(ModItems.STEWED_NOODLES.get()))
                .setLabel(CookingFoodRecipeLabel.BOIL)
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("stewed_noodles"));
        build(List.of(Ingredient.of(ModItems.NOODLES.get()),Ingredient.of(Items.EGG)),new FluidStack(Fluids.WATER,250),new ItemStack(ModItems.EGG_ADDED_STEWED_NOODLES.get()))
                .setLabel(CookingFoodRecipeLabel.BOIL)
                .setContainer(new SkilletRecipe.SkilletCookingContainer(new ItemStack(Items.BOWL),0))
                .save(output,create("egg_added_stewed_noodles"));
    }

    private SkilletRecipeBuilder build(List<Ingredient> items, FluidStack fluid, ItemStack result){
       return new SkilletRecipeBuilder(items, fluid, result).unlockedBy("has_skillet",has(ModItems.SKILLET.get()));
    }

    private ResourceLocation create(String s){
        return ResourceLocation.fromNamespaceAndPath(lingshi.MODID,s);
    }
}
