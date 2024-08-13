package mczme.lingshi.common.datamap.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record CookingFoodData(float cookedTime,float burntTime) {
    static float MAX_TIME = 30;

    public CookingFoodData(float cookedTime,float burntTime){
        if(cookedTime > MAX_TIME) this.cookedTime = MAX_TIME;
        else if(cookedTime < 0) this.cookedTime = 0;
        else this.cookedTime = cookedTime;
        this.burntTime = burntTime;
    }

    public static final Codec<CookingFoodData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("cooked_time").forGetter(CookingFoodData::cookedTime),
            Codec.FLOAT.fieldOf("complete_time").forGetter(CookingFoodData::burntTime)
    ).apply(instance, CookingFoodData::new));

}
