package mczme.lingshi.data.builder;

import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.CookingPotRecipe;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mczme.lingshi.client.recipebook.CookingFoodRecipeLabel.MISC;

public class CookingPotRecipeBuilder implements RecipeBuilder {

    private final List<Ingredient> items;
    private final FluidStack fluid;
    private CookingPotRecipe.CookingPotContainer container;
    private final ItemStack result;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;
    protected CookingFoodRecipeLabel label;

    public CookingPotRecipeBuilder(List<Ingredient> items, FluidStack fluid, ItemStack result) {
        this.items = items;
        this.fluid = fluid;
        this.result = result;
    }

    @Override
    public @NotNull CookingPotRecipeBuilder unlockedBy(@NotNull String pName, @NotNull Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    @Override
    public @NotNull CookingPotRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public CookingPotRecipeBuilder setLabel(@Nullable CookingFoodRecipeLabel pLabelName) {
        this.label = pLabelName;
        return this;
    }

    public CookingPotRecipeBuilder setContainer(CookingPotRecipe.CookingPotContainer container) {
        this.container = container;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_pot/" + pId.getPath());
        Advancement.Builder advancement = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        CookingPotRecipe recipe = new CookingPotRecipe(this.items, this.fluid, this.container, this.result
                , Objects.requireNonNullElse(this.group, "")
                , Objects.requireNonNullElse(this.label, MISC));
        pRecipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
