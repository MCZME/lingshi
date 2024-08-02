package mczme.lingshi.common.createtab;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static mczme.lingshi.common.registry.ModItems.ITEMS_LIST;
import static mczme.lingshi.lingshi.MODID;

public class CreateTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LINGSHI_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("lingshi.lingshi_tab"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.RICE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ITEMS_LIST.forEach(item -> output.accept(item.get()));
            }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
