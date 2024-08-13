package mczme.lingshi.common.datamap.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record CookingFoodData(float time) {
    static float MAX_TIME = 30;

    public CookingFoodData(float time){
        if(time > MAX_TIME) this.time = MAX_TIME;
        else if(time < 0) this.time = 0;
        else this.time = time;
    }

    public static final Codec<CookingFoodData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("time").forGetter(CookingFoodData::time)
    ).apply(instance, CookingFoodData::new));

}
