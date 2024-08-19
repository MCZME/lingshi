package mczme.lingshi.common.datamap.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;


public record CookingFoodData(int cookedTime,int burntTime) {
    static int MAX_TIME = 30;

    public CookingFoodData(int cookedTime,int burntTime){
        if(cookedTime > MAX_TIME) this.cookedTime = MAX_TIME;
        else this.cookedTime = Math.max(cookedTime, 0);
        this.burntTime = burntTime;
    }

    public static final Codec<CookingFoodData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("cooked_time").forGetter(CookingFoodData::cookedTime),
            Codec.INT.fieldOf("burnt_time").forGetter(CookingFoodData::burntTime)
    ).apply(instance, CookingFoodData::new));

}
