package mczme.lingshi.common.registry;

import mczme.lingshi.common.item.KnifeItem;
import mczme.lingshi.common.item.SpatulaItem;
import mczme.lingshi.lingshi;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(lingshi.MODID);
    public static final List<Supplier<Item>> ITEMS_LIST = new ArrayList<>();

    public static final Supplier<Item> RICE = registerWithCreateTab("rice", ()->new BlockItem(ModBlocks.RICE.get(), new Item.Properties()));
    public static final Supplier<Item> RICE_SEEDLING =registerWithCreateTab("rice_seedling", ()->new BlockItem(ModBlocks.RICE_SEEDLING.get(), new Item.Properties()));
    public static final Supplier<Item> RICE_OF_EAR = registerWithCreateTab("rice_of_ear", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> TEA_TREE = registerWithCreateTab("tea_tree", () -> new BlockItem(ModBlocks.TEA_TREE.get(), new Item.Properties()));
    public static final Supplier<Item> TEA_LEAF = registerWithCreateTab("tea_leaf", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> SKILLET = registerWithCreateTab("skillet", () -> new BlockItem(ModBlocks.SKILLET.get(),new Item.Properties()));
    public static final Supplier<Item> CHOPPING_BOARD = registerWithCreateTab("chopping_board", () -> new BlockItem(ModBlocks.CHOPPING_BOARD.get(), new Item.Properties()));

    public static final Supplier<Item> SPATULA = registerWithCreateTab("spatula",()->new SpatulaItem(new Item.Properties()));
    public static final Supplier<Item> IRON_KNIFE = registerWithCreateTab("iron_knife", () -> new KnifeItem(Tiers.IRON,new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))));

    public static final Supplier<Item> OIL_BUCKET = registerWithCreateTab("oil_bucket",()->new BucketItem(ModFluids.OIL_SOURCE.get(),new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<Item> STRANGE_FOOD = registerWithCreateTab("strange_food",()->new Item(new Item.Properties()));
    public static final Supplier<Item> FRIED_EGG = registerWithCreateTab("fried_egg",()->new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(3.2f).build())));
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
