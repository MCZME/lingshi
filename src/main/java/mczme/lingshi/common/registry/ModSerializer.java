package mczme.lingshi.common.registry;

import mczme.lingshi.common.recipe.ChoppingBoardRecipe;
import mczme.lingshi.common.recipe.serializer.ChoppingBoardRecipeSerializer;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSerializer {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, lingshi.MODID);

    public static final Supplier<RecipeSerializer<ChoppingBoardRecipe>> CHOPPING_BOARD__SERIALIZER =
            RECIPE_SERIALIZERS.register("chopping_board__serializer", ChoppingBoardRecipeSerializer::new);

    public static void register (IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}