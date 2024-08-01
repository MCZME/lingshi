package mczme.lingshi.common.registry;

import mczme.lingshi.lingshi;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(lingshi.MODID);

    public static final Supplier<BlockItem> RICE = ITEMS.registerSimpleBlockItem("rice", ModBlocks.RICE, new Item.Properties());
    public static final Supplier<BlockItem> RICE_SEEDING =ITEMS.registerSimpleBlockItem("rice_seeding", ModBlocks.RICE_SEEDING, new Item.Properties());

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
