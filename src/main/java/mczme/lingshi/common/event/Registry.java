package mczme.lingshi.common.event;

import mczme.lingshi.common.registry.BlockEntityTypes;
import mczme.lingshi.lingshi;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static mczme.lingshi.common.datamap.DataMapTypes.COOKING_FOOD;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Registry {

//    data map
    @SubscribeEvent
    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(COOKING_FOOD);
    }

//    Capabilities
    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }}
