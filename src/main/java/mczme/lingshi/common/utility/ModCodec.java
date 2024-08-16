package mczme.lingshi.common.utility;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.OptionalFieldCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

public class ModCodec {

//    序列化工具，可以对null进行处理
    public static MapCodec<ItemStack> ModoptionalField(final String name, final Codec<ItemStack> codec, ItemStack defaultValue) {
        return new OptionalFieldCodec<>(name, codec, false).xmap(
                o -> o.orElse(defaultValue),
                a -> a==null ? Optional.empty() : Optional.of(a)
        );
    }

    public static MapCodec<FluidStack> ModoptionalField(final String name, final Codec<FluidStack> codec, FluidStack defaultValue) {
        return new OptionalFieldCodec<>(name, codec, false).xmap(
                o -> o.orElse(defaultValue),
                a -> a==null ? Optional.empty() : Optional.of(a)
        );
    }

}
