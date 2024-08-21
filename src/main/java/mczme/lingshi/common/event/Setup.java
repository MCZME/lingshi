package mczme.lingshi.common.event;

import mczme.lingshi.client.model.ModelMap;
import mczme.lingshi.lingshi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class Setup {
    @SubscribeEvent
    public static void fMLClientSetupEvent(FMLClientSetupEvent event)
    {
        ModelMap.init();
    }
}
