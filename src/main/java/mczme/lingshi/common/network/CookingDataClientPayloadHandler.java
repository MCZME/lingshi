package mczme.lingshi.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CookingDataClientPayloadHandler {

    private static BlockPos blockPos;
    private static ItemStack result;

    public static void handleData(final CookingData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
                    blockPos = data.pPos();
                    result = data.result();
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.disconnect(Component.translatable("lingshi.networking.failed", e.getMessage()));
                    return null;
                });
    }

    public static BlockPos getBlockPos(){
        return blockPos;
    }

    public static ItemStack getResult() {
        return result;
    }
}
