package mczme.lingshi.common.data.recipe;

import mczme.lingshi.common.data.builder.ChoppingBoardRecipeBuilder;
import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.common.tag.NeoforgeTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ChoppingBoardRecipeDatagen {
    public ChoppingBoardRecipeDatagen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
        build(List.of(new ItemStack(ModItems.RICE.get(),3)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.RICE_OF_EAR.get()),output,"rice");
    }

    private void build(List<ItemStack> result, Ingredient tool, Ingredient inputItem,RecipeOutput output,String pid){
        new ChoppingBoardRecipeBuilder(result, tool, inputItem).save(output, pid);
    }

}
