package mczme.lingshi.common.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class Eat {

    public boolean eat = false;

    public static final Codec<Eat> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("getEat").forGetter(Eat::getEat)
            ).apply(instance, Eat::new)
    );
    public static final StreamCodec<ByteBuf, Eat> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, Eat::getEat,
            Eat::new
    );

    public Eat(boolean eat) {
        this.eat = eat;
    }

    public Boolean getEat() {
        return eat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.eat);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof Eat ex
                    && this.eat == ex.eat;
        }
    }

}
