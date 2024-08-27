package mczme.lingshi.common.registry;

import mczme.lingshi.common.recipe.ChoppingBoardRecipe;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, lingshi.MODID);

    public static final Supplier<RecipeType<ChoppingBoardRecipe>> CHOPPING_BOARD_RECIPE =
            RECIPE_TYPES.register(
                    "chopping_board_recipe",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "chopping_board"))
            );
    public static final Supplier<RecipeType<SkilletRecipe>> SKILLET_RECIPE =
            RECIPE_TYPES.register(
                    "skillet_recipe",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "skillet_recipe"))
            );
    public static final Supplier<RecipeType<CookingPotRecipe>> COOKING_POT_RECIPE =
            RECIPE_TYPES.register(
                    "cooking_pot_recipe",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_pot_recipe"))
            );

    public static void register(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
    }
}
