package mczme.lingshi.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.OptionalFieldCodec;

import java.util.Optional;

public class ModCodec {

//    序列化工具，可以对null进行处理
    public static <T> MapCodec<T> ModoptionalField(final String name, final Codec<T> codec, T defaultValue) {
        return new OptionalFieldCodec<>(name, codec, false).xmap(
                o -> o.orElse(defaultValue),
                a -> a==null ? Optional.empty() : Optional.of(a)
        );
    }

}
