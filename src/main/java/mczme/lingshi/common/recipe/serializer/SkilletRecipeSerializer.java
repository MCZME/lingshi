package mczme.lingshi.common.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.SkilletRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public class SkilletRecipeSerializer implements RecipeSerializer<SkilletRecipe> {

    public static final MapCodec<SkilletRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(SkilletRecipe::getItems),
            FluidStack.OPTIONAL_CODEC.optionalFieldOf("fluid",null).forGetter(SkilletRecipe::getFluid),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("container", null).forGetter(SkilletRecipe::getContainer),
            ItemStack.CODEC.fieldOf("result").forGetter(SkilletRecipe::getResult),
            Codec.STRING.fieldOf("group").forGetter(SkilletRecipe::getGroup),
            CookingFoodRecipeLabel.CODEC.fieldOf("label").forGetter(SkilletRecipe::getLabel)
    ).apply(inst, SkilletRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_STREAM_CODEC =
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list(SkilletRecipe.MAX_SLOT));
    public static final StreamCodec<RegistryFriendlyByteBuf, CookingFoodRecipeLabel>  CookingFoodLabel_STREAM_CODEC =
            StreamCodec.of(SkilletRecipeSerializer::encode,SkilletRecipeSerializer::decode);

    public static final StreamCodec<RegistryFriendlyByteBuf, SkilletRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    INGREDIENT_STREAM_CODEC,SkilletRecipe::getItems,
                    FluidStack.OPTIONAL_STREAM_CODEC,SkilletRecipe::getFluid,
                    ItemStack.OPTIONAL_STREAM_CODEC,SkilletRecipe::getContainer,
                    ItemStack.STREAM_CODEC,SkilletRecipe::getResult,
                    ByteBufCodecs.STRING_UTF8,SkilletRecipe::getGroup,
                    CookingFoodLabel_STREAM_CODEC,SkilletRecipe::getLabel,
                    SkilletRecipe::new
            );

    @Override
    public MapCodec<SkilletRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SkilletRecipe> streamCodec() {
        return STREAM_CODEC;
    }

    public static CookingFoodRecipeLabel decode(FriendlyByteBuf buffer){
        return CookingFoodRecipeLabel.isinside(buffer.readUtf());
    }

    public static void encode(FriendlyByteBuf buffer,CookingFoodRecipeLabel label){
        buffer.writeUtf(label.toString() !=null ? label.toString():"");
    }


}
