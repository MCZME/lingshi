package mczme.lingshi.common.network;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CookingDataServerPayloadHandler {

    public static void handleData(final CookingData data, final IPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.enqueueWork(() -> {

                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("lingshi.networking.failed", e.getMessage()));
                    return null;
                });
    }

}
