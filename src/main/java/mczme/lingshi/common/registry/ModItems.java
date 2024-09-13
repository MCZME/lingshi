package mczme.lingshi.common.registry;

import mczme.lingshi.common.item.*;
import mczme.lingshi.lingshi;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.core.component.DataComponents.ITEM_NAME;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(lingshi.MODID);
    public static final List<Supplier<Item>> ITEMS_LIST = new ArrayList<>();

    public static final Supplier<Item> RICE = registerWithCreateTab("rice", () -> new BlockItem(ModBlocks.RICE.get(), new Item.Properties()));
    public static final Supplier<Item> RICE_SEEDLING = registerWithCreateTab("rice_seedling", () -> new BlockItem(ModBlocks.RICE_SEEDLING.get(), new Item.Properties()));
    public static final Supplier<Item> RICE_OF_EAR = registerWithCreateTab("rice_of_ear", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CABBAGE_SEED = registerWithCreateTab("cabbage_seed", () -> new BlockItem(ModBlocks.CABBAGE.get(), new Item.Properties()));
    public static final Supplier<Item> BEAN = registerWithCreateTab("bean", () -> new BlockItem(ModBlocks.BEAN.get(), new Item.Properties()));
    public static final Supplier<Item> POD = registerWithCreateTab("pod", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CABBAGE = registerWithCreateTab("cabbage", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3).saturationModifier(2.4f).build())));
    public static final Supplier<Item> CABBAGE_LEAF = registerWithCreateTab("cabbage_leaf", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.8f).build())));

    public static final Supplier<Item> TEA_TREE = registerWithCreateTab("tea_tree", () -> new BlockItem(ModBlocks.TEA_TREE.get(), new Item.Properties()));
    public static final Supplier<Item> TEA_LEAF = registerWithCreateTab("tea_leaf", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> SKILLET = registerWithCreateTab("skillet", () -> new BlockItem(ModBlocks.SKILLET.get(), new Item.Properties()));
    public static final Supplier<Item> COOKING_POT = registerWithCreateTab("cooking_pot", () -> new BlockItem(ModBlocks.COOKING_POT.get(), new Item.Properties()));
    public static final Supplier<Item> POT_LID = registerWithCreateTab("pot_lid", () -> new PotLid(new Item.Properties()));
    public static final Supplier<Item> CHOPPING_BOARD = registerWithCreateTab("chopping_board", () -> new BlockItem(ModBlocks.CHOPPING_BOARD.get(), new Item.Properties()));

    public static final Supplier<Item> SPATULA = registerWithCreateTab("spatula", () -> new SpatulaItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> IRON_KNIFE = registerWithCreateTab("iron_knife", () -> new KnifeItem(Tiers.IRON, new Item.Properties()
            .attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));
    public static final Supplier<Item> GLASS_JAR = registerWithCreateTab("glass_jar", () -> new GlassJarItem(ModBlocks.GLASS_JAR.get(), new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> STOVE = registerWithCreateTab("stove", () -> new BlockItem(ModBlocks.STOVE.get(), new Item.Properties()));
    public static final Supplier<Item> OIL_BUCKET = registerWithCreateTab("oil_bucket", () -> new BucketItem(ModFluids.OIL_SOURCE.get(), new Item.Properties()
            .craftRemainder(Items.BUCKET).stacksTo(1)));
    //食物
    public static final Supplier<Item> STRANGE_FOOD = registerWithCreateTab("strange_food", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> FRIED_EGG = registerWithCreateTab("fried_egg", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(3.2f).build())));
    public static final Supplier<Item> COOKED_RICE = registerWithCreateTab("cooked_rice", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).usingConvertsTo(Items.BOWL).saturationModifier(4f).build())));
    public static final Supplier<Item> EGG_FRIED_RICE = registerWithCreateTab("egg_fried_rice", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(8).usingConvertsTo(Items.BOWL).saturationModifier(7.2f).build())));
    public static final Supplier<Item> FLOUR = registerWithCreateTab("flour", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DOUGH = registerWithCreateTab("dough", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> NOODLES = registerWithCreateTab("noodles", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1f).build())));
    public static final Supplier<Item> PIG_FEET = registerWithCreateTab("pig_feet", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5).saturationModifier(3f).build())));
    public static final Supplier<Item> SLICED_PORK = registerWithCreateTab("sliced_pork", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).build())));
    public static final Supplier<Item> SLICED_BEEF = registerWithCreateTab("sliced_beef", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2).saturationModifier(1.2f).build())));
    //可放置食物
    public static final Supplier<Item> PORK_FEET_RICE = registerWithCreateTab("pork_feet_rice", () -> new FoodBlockItem(ModBlocks.PORK_FEET_RICE.get(), new Item.Properties()
            .food(new FoodProperties.Builder().usingConvertsTo(Items.BOWL).nutrition(12).saturationModifier(9.4f)
                    .effect(() -> new MobEffectInstance(ModEffects.GRATIFICATION_EFFECT, 5 * 60 * 20), 1.0F).build())));
    public static final Supplier<Item> SAUTEED_SEASONAL_VEGETABLE = registerWithCreateTab("sauteed_seasonal_vegetable", () -> new FoodBlockItem(ModBlocks.SAUTEED_SEASONAL_VEGETABLE.get(), new Item.Properties()
            .food(new FoodProperties.Builder().usingConvertsTo(Items.BOWL).nutrition(6).saturationModifier(7.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * 20, 0), 1.0F).build())));
    public static final Supplier<Item> EGG_ADDED_STEWED_NOODLES = registerWithCreateTab("egg_added_stewed_noodles", () -> new FoodBlockItem(ModBlocks.STEWED_NOODLES.get(), new Item.Properties()
            .food(new FoodProperties.Builder().usingConvertsTo(Items.BOWL).nutrition(8).saturationModifier(8f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, 0), 1.0F).build())
            .component(ITEM_NAME, Component.translatable("item.lingshi.egg_added_stewed_noodles"))));
    public static final Supplier<Item> STEWED_NOODLES = registerWithCreateTab("stewed_noodles", () -> new FoodBlockItem(ModBlocks.STEWED_NOODLES.get(), new Item.Properties()
            .food(new FoodProperties.Builder().usingConvertsTo(Items.BOWL).nutrition(6).saturationModifier(5f).build())));
    public static final Supplier<Item> FRIED_FISH = registerWithCreateTab("fried_fish", () -> new FoodBlockItem(ModBlocks.FRIED_FISH.get(), new Item.Properties()
            .food(new FoodProperties.Builder().usingConvertsTo(Items.BOWL).nutrition(6).saturationModifier(6f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 2 * 20, 0), 1.0F).build())));

    private static Supplier<Item> registerWithCreateTab(String item_name, Supplier<Item> itemSupplier) {
        Supplier<Item> item = ITEMS.register(item_name, itemSupplier);
        ITEMS_LIST.add(item);
        return item;
    }

    private static Supplier<Item> register(String item_name, Supplier<Item> itemSupplier) {
        return ITEMS.register(item_name, itemSupplier);
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
