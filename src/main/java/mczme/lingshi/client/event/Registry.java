package mczme.lingshi.client.event;

import mczme.lingshi.client.BlockEntityRenderer.ChoppingBoardBER;
import mczme.lingshi.common.registry.BlockEntitys;
import mczme.lingshi.lingshi;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = lingshi.MODID, bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class Registry {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntitys.CHOPPING_BOARD_BLOCKENTITY.get(), ChoppingBoardBER::new);
    }
}
