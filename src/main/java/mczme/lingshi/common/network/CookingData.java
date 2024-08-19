package mczme.lingshi.common.network;

import mczme.lingshi.lingshi;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record CookingData(BlockPos pPos, ItemStack result) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CookingData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(lingshi.MODID, "skillet_cooking_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CookingData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, CookingData::pPos,
            ItemStack.OPTIONAL_STREAM_CODEC,CookingData::result,
            CookingData::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
