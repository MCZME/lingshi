package mczme.lingshi.common.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.CookingPotRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mczme.lingshi.common.util.ModCodec.ModoptionalField;

public class CookingPotRecipeSerializer implements RecipeSerializer<CookingPotRecipe> {
    public static final MapCodec<CookingPotRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(CookingPotRecipe::getItems),
            ModoptionalField("fluid", FluidStack.OPTIONAL_CODEC,FluidStack.EMPTY).forGetter(CookingPotRecipe::getFluid),
            ModoptionalField("container", CookingPotRecipe.CookingPotContainer.CODEC, CookingPotRecipe.CookingPotContainer.EMPTY).forGetter(CookingPotRecipe::getContainer),
            ItemStack.CODEC.fieldOf("result").forGetter(CookingPotRecipe::getResult),
            Codec.STRING.fieldOf("group").forGetter(CookingPotRecipe::getGroup),
            CookingFoodRecipeLabel.CODEC.fieldOf("label").forGetter(CookingPotRecipe::getLabel)
    ).apply(inst, CookingPotRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_STREAM_CODEC =
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list(CookingPotRecipe.MAX_SLOT));
    public static final StreamCodec<RegistryFriendlyByteBuf, CookingFoodRecipeLabel> CookingFoodLabel_STREAM_CODEC =
            StreamCodec.of(SkilletRecipeSerializer::encode, SkilletRecipeSerializer::decode);

    public static final StreamCodec<RegistryFriendlyByteBuf, CookingPotRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    INGREDIENT_STREAM_CODEC, CookingPotRecipe::getItems,
                    StreamCodec.of(SkilletRecipeSerializer::FluidStack_encode, SkilletRecipeSerializer::FluidStack_decode), CookingPotRecipe::getFluid,
                    CookingPotRecipe.CookingPotContainer.STREAM_CODEC, CookingPotRecipe::getContainer,
                    ItemStack.STREAM_CODEC, CookingPotRecipe::getResult,
                    ByteBufCodecs.STRING_UTF8, CookingPotRecipe::getGroup,
                    CookingFoodLabel_STREAM_CODEC, CookingPotRecipe::getLabel,
                    CookingPotRecipe::new
            );

    @Override
    public @NotNull MapCodec<CookingPotRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, CookingPotRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
