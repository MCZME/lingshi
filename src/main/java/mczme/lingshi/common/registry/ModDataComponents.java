package mczme.lingshi.common.registry;

import mczme.lingshi.common.datacomponent.Eat;
import mczme.lingshi.lingshi;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(lingshi.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Eat>> EAT = REGISTRAR.registerComponentType(
            "eat",
            builder -> builder
                    .persistent(Eat.BASIC_CODEC)
                    .networkSynchronized(Eat.BASIC_STREAM_CODEC)
    );

    public static void register(IEventBus modEventBus) {
        REGISTRAR.register(modEventBus);
    }
}
