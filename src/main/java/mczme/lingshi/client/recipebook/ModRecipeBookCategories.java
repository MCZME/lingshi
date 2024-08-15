package mczme.lingshi.client.recipebook;

import com.google.common.base.Suppliers;
import net.minecraft.client.RecipeBookCategories;

import java.util.function.Supplier;

public class ModRecipeBookCategories {
    public static final Supplier<RecipeBookCategories> SKILLET_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_SKILLET_SEARCH"));
    public static final Supplier<RecipeBookCategories> SKILLET_HEAT = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_SKILLET_HEAT"));
    public static final Supplier<RecipeBookCategories> SKILLET_PAN_FRY = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_PAN_FRY"));
    public static final Supplier<RecipeBookCategories> SKILLET_STIR_FRY = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_STIR_FRY"));
    public static final Supplier<RecipeBookCategories> SKILLET_BOIL = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_BOIL"));
    public static final Supplier<RecipeBookCategories> MISC = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_MISC"));
}