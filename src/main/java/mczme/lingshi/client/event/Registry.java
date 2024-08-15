package mczme.lingshi.client.event;

import com.google.common.collect.ImmutableList;
import mczme.lingshi.client.BlockEntityRenderer.ChoppingBoardBER;
import mczme.lingshi.client.BlockEntityRenderer.SkilletBER;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.client.screen.SkilletScreen;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.common.registry.ModMenuTypes;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.lingshi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import static mczme.lingshi.client.recipebook.ModRecipeBookCategories.*;
import static mczme.lingshi.client.recipebook.ModRecipeBookType.SKILLET;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class Registry {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypes.CHOPPING_BOARD_BLOCKENTITY.get(), ChoppingBoardBER::new);
        event.registerBlockEntityRenderer(BlockEntityTypes.SKILLET_BLOCKENTITY.get(), SkilletBER::new);
    }

//    menu screen
    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SKILLET_MENU.get(), SkilletScreen::new);
    }

//    recipe book
    @SubscribeEvent
    public static void registerRecipeBook(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(SKILLET, ImmutableList.of(SKILLET_SEARCH.get(), SKILLET_HEAT.get(),SKILLET_PAN_FRY.get(),SKILLET_STIR_FRY.get(),SKILLET_BOIL.get()));
        event.registerAggregateCategory(SKILLET_SEARCH.get(), ImmutableList.of(SKILLET_HEAT.get(),SKILLET_PAN_FRY.get(),SKILLET_STIR_FRY.get(),SKILLET_BOIL.get()));
        event.registerRecipeCategoryFinder(ModRecipes.SKILLET_RECIPE.get(), recipe ->
        {
            if (recipe.value() instanceof SkilletRecipe Recipe) {
                CookingFoodRecipeLabel label = Recipe.getLabel();
                return switch (label) {
                    case HEAT -> SKILLET_HEAT.get();
                    case PAN_FRY -> SKILLET_PAN_FRY.get();
                    case STIR_FRY -> SKILLET_STIR_FRY.get();
                    case BOIL -> SKILLET_BOIL.get();
                    default -> MISC.get();
                };
            }
            return MISC.get();
        });
    }

}
