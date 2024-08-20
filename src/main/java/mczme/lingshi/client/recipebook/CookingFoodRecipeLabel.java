package mczme.lingshi.client.recipebook;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public enum CookingFoodRecipeLabel {
    HEAT("heat"), //    加热
    PAN_FRY("pan_fry"), // 煎
    STIR_FRY("stir_fry"), //  炒
    BOIL("boil"),  //  煮
    STEW("stew"),  //  炖
    DEEP_FRY("deep_fly"),  //  油炸
    MISC("misc");  // 其他

    public final String string;
    public static final Codec<CookingFoodRecipeLabel> CODEC = Codec.STRING.flatXmap(s -> {
            CookingFoodRecipeLabel label = isinside(s);
            if(s!=null) return DataResult.success(label);
            else return DataResult.error(()-> "Unknown cooking food recipe label: " + s);
        },label -> DataResult.success(label.toString())
    );

    CookingFoodRecipeLabel(String string) {
        this.string = string;
    }

    public static CookingFoodRecipeLabel isinside(String s){
        CookingFoodRecipeLabel[] t = values();
        for(CookingFoodRecipeLabel i : t) {
            if (i.toString().equals(s)) return i;
        }
        return null;
    }

    @Override
    public String toString() {
        return string;
    }
}
