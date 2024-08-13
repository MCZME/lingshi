package mczme.lingshi.common.recipe.serializer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.common.recipe.SkilletRecipe;
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
            FluidStack.CODEC.listOf().fieldOf("fluids").forGetter(SkilletRecipe::getFluids),
            ItemStack.CODEC.fieldOf("result").forGetter(SkilletRecipe::getResult)
    ).apply(inst, SkilletRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, List<FluidStack>> FLUIDSTACK_STREAM_CODEC =
            FluidStack.STREAM_CODEC.apply(
                    ByteBufCodecs.list(SkilletRecipe.MAX_SLOT)
            );
    public static final StreamCodec<RegistryFriendlyByteBuf, List<Ingredient>> INGREDIENT_STREAM_CODEC =
            Ingredient.CONTENTS_STREAM_CODEC.apply(
                    ByteBufCodecs.list(SkilletRecipe.MAX_SLOT)
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, SkilletRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    INGREDIENT_STREAM_CODEC,SkilletRecipe::getItems,
                    FLUIDSTACK_STREAM_CODEC,SkilletRecipe::getFluids,
                    ItemStack.STREAM_CODEC,SkilletRecipe::getResult,
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
}
