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
    public static final Supplier<RecipeBookCategories> SKILLET_MISC = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_MISC"));

    public static final Supplier<RecipeBookCategories> COOKING_POT_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_COOKING_POT_SEARCH"));
    public static final Supplier<RecipeBookCategories> COOKING_POT_POT_BOIL = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_COOKING_POT_BOIL"));
    public static final Supplier<RecipeBookCategories> COOKING_POT_STEW = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_STEW"));
    public static final Supplier<RecipeBookCategories> COOKING_POT_DEEP_FRY = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_DEEP_FRY"));
    public static final Supplier<RecipeBookCategories> COOKING_POT_MISC = Suppliers.memoize(() -> RecipeBookCategories.valueOf("lingshi_COOKING_POT_MISC"));

}