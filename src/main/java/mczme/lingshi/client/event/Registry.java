package mczme.lingshi.client.event;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import mczme.lingshi.client.BlockEntityRenderer.ChoppingBoardBER;
import mczme.lingshi.client.BlockEntityRenderer.CookingPotBER;
import mczme.lingshi.client.BlockEntityRenderer.GlassJarBER;
import mczme.lingshi.client.BlockEntityRenderer.SkilletBER;
import mczme.lingshi.client.ItemRender.lingshiBEWLR;
import mczme.lingshi.client.model.GlassJarBakeModel;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.client.screen.CookingHud;
import mczme.lingshi.client.screen.CookingPotScreen;
import mczme.lingshi.client.screen.SkilletScreen;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import mczme.lingshi.common.recipe.SkilletRecipe;
import mczme.lingshi.common.registry.*;
import mczme.lingshi.lingshi;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Map;

import static mczme.lingshi.client.recipebook.ModRecipeBookCategories.*;
import static mczme.lingshi.client.recipebook.ModRecipeBookType.COOKING_POT;
import static mczme.lingshi.client.recipebook.ModRecipeBookType.SKILLET;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Registry {

    //    block render
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityTypes.CHOPPING_BOARD_BLOCK_ENTITY.get(), ChoppingBoardBER::new);
        event.registerBlockEntityRenderer(BlockEntityTypes.SKILLET_BLOCK_ENTITY.get(), SkilletBER::new);
        event.registerBlockEntityRenderer(BlockEntityTypes.COOKING_POT_BLOCK_ENTITY.get(), CookingPotBER::new);
        event.registerBlockEntityRenderer(BlockEntityTypes.GLASS_JAR_BLOCK_ENTITY.get(), GlassJarBER::new);
    }

    //    menu screen
    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SKILLET_MENU.get(), SkilletScreen::new);
        event.register(ModMenuTypes.COOKING_POT_MENU.get(), CookingPotScreen::new);
    }

    //    recipe book
    @SubscribeEvent
    public static void registerRecipeBook(RegisterRecipeBookCategoriesEvent event) {
//        Skillet
        event.registerBookCategories(SKILLET, ImmutableList.of(SKILLET_SEARCH.get(), SKILLET_HEAT.get(), SKILLET_PAN_FRY.get(), SKILLET_STIR_FRY.get(), SKILLET_BOIL.get()));
        event.registerAggregateCategory(SKILLET_SEARCH.get(), ImmutableList.of(SKILLET_HEAT.get(), SKILLET_PAN_FRY.get(), SKILLET_STIR_FRY.get(), SKILLET_BOIL.get()));
        event.registerRecipeCategoryFinder(ModRecipes.SKILLET_RECIPE.get(), recipe ->
        {
            if (recipe.value() instanceof SkilletRecipe Recipe) {
                CookingFoodRecipeLabel label = Recipe.getLabel();
                return switch (label) {
                    case HEAT -> SKILLET_HEAT.get();
                    case PAN_FRY -> SKILLET_PAN_FRY.get();
                    case STIR_FRY -> SKILLET_STIR_FRY.get();
                    case BOIL -> SKILLET_BOIL.get();
                    default -> SKILLET_MISC.get();
                };
            }
            return SKILLET_MISC.get();
        });
//        Cooking Pot
        event.registerBookCategories(COOKING_POT, ImmutableList.of(COOKING_POT_SEARCH.get(), COOKING_POT_POT_BOIL.get(), COOKING_POT_STEW.get(), COOKING_POT_DEEP_FRY.get(), COOKING_POT_MISC.get()));
        event.registerAggregateCategory(COOKING_POT_SEARCH.get(), ImmutableList.of(COOKING_POT_POT_BOIL.get(), COOKING_POT_STEW.get(), COOKING_POT_DEEP_FRY.get(), COOKING_POT_MISC.get()));
        event.registerRecipeCategoryFinder(ModRecipes.COOKING_POT_RECIPE.get(), recipe ->
        {
            if (recipe.value() instanceof CookingPotRecipe Recipe) {
                CookingFoodRecipeLabel label = Recipe.getLabel();
                return switch (label) {
                    case BOIL -> COOKING_POT_POT_BOIL.get();
                    case STEW -> COOKING_POT_STEW.get();
                    case DEEP_FRY -> COOKING_POT_DEEP_FRY.get();
                    case MISC -> COOKING_POT_MISC.get();
                    default -> COOKING_POT_MISC.get();
                };
            }
            return COOKING_POT_MISC.get();
        });
    }

    //fluid
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_FLOWING.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.OIL_SOURCE.get(), RenderType.translucent());
        });

    }

    //    Render
    @SubscribeEvent
    public static void registerClientExtensionsEvent(RegisterClientExtensionsEvent event) {
//        fluid
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_still");
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_flow");
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture() {
                return ResourceLocation.withDefaultNamespace("block/water_overlay");
            }

            @Override
            public int getTintColor() {
                return 0xA1EAD909;
            }

            // 修改从流体中看雾的颜色
            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level,
                                                    int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(234f / 255f, 217f / 255f, 9f / 255f);
            }

            // 液体中的能见度 或者 说雾的范围
            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick,
                                        float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f); // distance when the fog starts
            }
        }, ModFluids.MOD_FLUID_TYPE.get());

//        item
//        GlassJar
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new lingshiBEWLR();
            }
        }, ModItems.GLASS_JAR.get());
    }

    //HUD
    @SubscribeEvent
    public static void registerGuiLayersEvent(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "cooking_hud"), CookingHud.getInstance());
    }

    //    Model
    @SubscribeEvent
    public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
        // glass jar item model
        Map<ModelResourceLocation, BakedModel> modelRegistry = event.getModels();
        ModelResourceLocation location = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ModItems.GLASS_JAR.get()), "inventory");
        BakedModel bakeModel = modelRegistry.get(location);
        if (bakeModel == null) {
            throw new RuntimeException("Did not find Obsidian Hidden in registry");
        } else if (bakeModel instanceof GlassJarBakeModel) {
            throw new RuntimeException("Tried to replaceObsidian Hidden twice");
        } else {
            GlassJarBakeModel glassJarBakeModel = new GlassJarBakeModel(bakeModel);
            event.getModels().put(location, glassJarBakeModel);
        }
    }
}
