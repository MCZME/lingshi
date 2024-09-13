package mczme.lingshi.data.recipe;

import mczme.lingshi.data.builder.ChoppingBoardRecipeBuilder;
import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.common.tag.NeoforgeTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ChoppingBoardRecipeDatagen {
    public ChoppingBoardRecipeDatagen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
        build(List.of(new ItemStack(ModItems.RICE.get(),3)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.RICE_OF_EAR.get()),output,"rice");
        build(List.of(new ItemStack(ModItems.FLOUR.get())),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(Items.WHEAT),output,"flour");
        build(List.of(new ItemStack(ModItems.DOUGH.get())),Ingredient.of(Items.WATER_BUCKET),Ingredient.of(ModItems.FLOUR.get()),output,"dough");
        build(List.of(new ItemStack(ModItems.NOODLES.get())),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.DOUGH.get()),output,"noodles");
        build(List.of(new ItemStack(ModItems.CABBAGE_LEAF.get(),3)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.CABBAGE.get()),output,"cabbage_leaf");
        build(List.of(new ItemStack(ModItems.SLICED_PORK.get(),2)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(Items.PORKCHOP),output,"sliced_pork");
        build(List.of(new ItemStack(ModItems.SLICED_BEEF.get(),2)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(Items.BEEF),output,"sliced_beef");
        build(List.of(new ItemStack(ModItems.BEAN.get(),3)),Ingredient.of(NeoforgeTags.KNIFE),Ingredient.of(ModItems.POD.get()),output,"bean");

    }

    private void build(List<ItemStack> result, Ingredient tool, Ingredient inputItem,RecipeOutput output,String pid){
        new ChoppingBoardRecipeBuilder(result, tool, inputItem).save(output, pid);
    }

}
