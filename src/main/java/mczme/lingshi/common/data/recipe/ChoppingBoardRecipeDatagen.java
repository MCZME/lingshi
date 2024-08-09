package mczme.lingshi.common.data.recipe;

import mczme.lingshi.common.data.builder.ChoppingBoardRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ChoppingBoardRecipeDatagen extends RecipeProvider {
    public ChoppingBoardRecipeDatagen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        new ChoppingBoardRecipeBuilder(new ItemStack(Items.WOODEN_AXE),Ingredient.of(Items.APPLE))
                .unlockedBy("test",has(Items.APPLE)).save(output, "test_0");
    }

}
