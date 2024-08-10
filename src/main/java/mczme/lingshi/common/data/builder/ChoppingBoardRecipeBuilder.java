package mczme.lingshi.common.data.builder;

import mczme.lingshi.common.recipe.ChoppingBoardRecipe;
import mczme.lingshi.lingshi;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChoppingBoardRecipeBuilder implements RecipeBuilder {

    protected final List<ItemStack>  result;

    private final Ingredient inputItem;
    private final Ingredient tool;

    public ChoppingBoardRecipeBuilder(List<ItemStack> result, Ingredient tool,Ingredient inputItem) {
        this.result = result;
        this.inputItem = inputItem;
        this.tool = tool;
    }

    @Override
    public RecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        return null;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return result.getFirst().getItem();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"chopping_board/"+pId.getPath());
        ChoppingBoardRecipe recipe = new ChoppingBoardRecipe(this.inputItem, tool ,this.result);
        pRecipeOutput.accept(id, recipe, null);
    }
}
