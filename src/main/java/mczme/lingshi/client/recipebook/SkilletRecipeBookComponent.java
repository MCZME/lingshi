package mczme.lingshi.client.recipebook;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class SkilletRecipeBookComponent extends RecipeBookComponent {

    @Override
    public void slotClicked(@Nullable Slot pSlot) {
        super.slotClicked(pSlot);
        if (pSlot != null && pSlot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> pRecipe, List<Slot> pSlots) {
        ItemStack itemstack = pRecipe.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(pRecipe);
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), pSlots.get(2).x, pSlots.get(2).y);
        NonNullList<Ingredient> nonnulllist = pRecipe.value().getIngredients();

        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for (int i = 0; i < 2; i++) {
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

}
