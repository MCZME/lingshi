package mczme.lingshi.common.data.recipe;

import mczme.lingshi.common.data.builder.ChoppingBoardRecipeBuilder;
import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.common.tag.NeoforgeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChoppingBoardRecipeDatagen extends RecipeProvider {
    public ChoppingBoardRecipeDatagen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        new ChoppingBoardRecipeBuilder(List.of(new ItemStack(ModItems.RICE.get(),3)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.RICE_OF_EAR.get()))
                .save(output, "rice");
    }

}
