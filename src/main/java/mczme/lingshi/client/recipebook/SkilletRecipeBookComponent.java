package mczme.lingshi.client.recipebook;

import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.lingshi;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Iterator;
import java.util.List;

public class SkilletRecipeBookComponent extends RecipeBookComponent {

    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"recipe_book/skillet_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"recipe_book/skillet_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"recipe_book/skillet_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(lingshi.MODID,"recipe_book/skillet_filter_disabled_highlighted")
    );

    @Override
    public void setupGhostRecipe(RecipeHolder<?> pRecipe, List<Slot> pSlots) {
        SkilletRecipe skilletRecipe = (SkilletRecipe) pRecipe.value();
        ItemStack resultitem = pRecipe.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(pRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(resultitem), pSlots.get(6).x, pSlots.get(6).y);
        if(skilletRecipe.getContainer().container() != ItemStack.EMPTY){
            this.ghostRecipe.addIngredient(Ingredient.of(skilletRecipe.getContainer().container()), pSlots.get(5).x, pSlots.get(5).y);
        }

        NonNullList<Ingredient> nonnulllist = pRecipe.value().getIngredients();

        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for (int i = 0; i < 5; i++) {
            if (!iterator.hasNext()) {
                return;
            }
            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = pSlots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

}
