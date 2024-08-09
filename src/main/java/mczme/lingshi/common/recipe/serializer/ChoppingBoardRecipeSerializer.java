package mczme.lingshi.common.recipe.serializer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.common.recipe.ChoppingBoardRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class ChoppingBoardRecipeSerializer implements RecipeSerializer<ChoppingBoardRecipe> {

    public static final MapCodec<ChoppingBoardRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ChoppingBoardRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(ChoppingBoardRecipe::getResult)
    ).apply(inst, ChoppingBoardRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChoppingBoardRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, ChoppingBoardRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, ChoppingBoardRecipe::getResult,
                    ChoppingBoardRecipe::new
            );

    @Override
    public @NotNull MapCodec<ChoppingBoardRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, ChoppingBoardRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
