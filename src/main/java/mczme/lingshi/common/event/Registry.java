package mczme.lingshi.common.event;

import mczme.lingshi.lingshi;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static mczme.lingshi.common.datamap.DataMapTypes.COOKING_FOOD;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Registry {
    @SubscribeEvent
    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(COOKING_FOOD);
    }
}
