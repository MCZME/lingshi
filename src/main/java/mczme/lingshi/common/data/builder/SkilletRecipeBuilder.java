package mczme.lingshi.common.data.builder;

import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.lingshi;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SkilletRecipeBuilder implements RecipeBuilder {

    private final List<Ingredient> items;
    private final List<FluidStack> fluids;
    private final ItemStack result;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public SkilletRecipeBuilder(List<Ingredient> items, List<FluidStack> fluids, ItemStack result) {
        this.items = items;
        this.fluids = fluids;
        this.result = result;
    }

    @Override
    public RecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"skillet/"+pId.getPath());
        Advancement.Builder advancement = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        SkilletRecipe recipe = new SkilletRecipe(this.items, this.fluids, this.result);
        pRecipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
