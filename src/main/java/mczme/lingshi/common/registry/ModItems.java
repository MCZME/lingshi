package mczme.lingshi.common.registry;

import mczme.lingshi.lingshi;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
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

    private static Supplier<Item> registerWithCreateTab(String item_name, Supplier<Item> itemSupplier) {
        Supplier<Item> item = ITEMS.register(item_name, itemSupplier);
        ITEMS_LIST.add(item);
        return item;
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
