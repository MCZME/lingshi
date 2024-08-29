package mczme.lingshi.common.event;

import mczme.lingshi.lingshi;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static mczme.lingshi.common.datamap.DataMapTypes.*;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Registry {

    //    data map
    @SubscribeEvent
    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(COOKING_FOOD_FLUID);
        event.register(COOKING_FOOD_ITEM);
    }

    //    Capabilities
    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }

//    network
    @SubscribeEvent
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {

    }

}
