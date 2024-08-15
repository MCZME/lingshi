package mczme.lingshi.client.recipebook;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public enum CookingFoodRecipeLabel {
    HEAT("heat"),
    PAN_FRY("pan_fry"),
    STIR_FRY("stir_fry"),
    BOIL("boil"),
    STEW("stew"),
    DEEP_FRY("deep_fly"),
    MISC("misc");

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
