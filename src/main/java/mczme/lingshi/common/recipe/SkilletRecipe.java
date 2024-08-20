package mczme.lingshi.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.input.SkilletRecipeInput;
import mczme.lingshi.common.registry.ModRecipes;
import mczme.lingshi.common.registry.ModSerializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mczme.lingshi.common.util.ModCodec.ModoptionalField;
import static net.neoforged.neoforge.common.util.RecipeMatcher.findMatches;

public class SkilletRecipe extends CookingFoodRecipe implements Recipe<SkilletRecipeInput> {

    public static final int MAX_SLOT = 5;

    private final List<Ingredient> items;
    private final FluidStack fluids;
    private final SkilletCookingContainer container;
    private final ItemStack result;

    final String group;

    public SkilletRecipe(List<Ingredient> items, FluidStack fluids, SkilletCookingContainer container, ItemStack result, String group, CookingFoodRecipeLabel label) {
        super(label);
        this.items = items;
        this.fluids = fluids;
        this.container = container;
        this.result = result;
        this.group = group;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(items);
        return list;
    }

    @Override
    public boolean matches(SkilletRecipeInput pInput, @NotNull Level pLevel) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;
        if (!pInput.isEmpty()) {
            for (int j = 0; j < pInput.size(); ++j) {
                ItemStack itemstack = pInput.getItem(j);
                if (!itemstack.isEmpty()) {
                    ++i;
                    inputs.add(itemstack);
                }
            }
        }
        return i == this.items.size() && findMatches(inputs, this.items) != null && pInput.getFluid().is(fluids.getFluid()) ;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SkilletRecipeInput pInput, HolderLookup.@NotNull Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 1;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModSerializer.SKILLET_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.SKILLET_RECIPE.get();
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public FluidStack getFluid() {
        return fluids;
    }

    public List<Ingredient> getItems() {
        return items;
    }

    public SkilletCookingContainer getContainer() {
        return container;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    public record SkilletCookingContainer(ItemStack container, int stirFryCount) {

        public static final SkilletCookingContainer EMPTY = new SkilletCookingContainer(ItemStack.EMPTY,0);

        public static final Codec<SkilletCookingContainer> CODEC = Codec.lazyInitialized(
                ()->RecordCodecBuilder.create(inst->inst.group(
                        ModoptionalField("container", ItemStack.OPTIONAL_CODEC, ItemStack.EMPTY).forGetter(SkilletCookingContainer::container),
                        ModoptionalField("stir_fry_count",Codec.INT,0).forGetter(SkilletCookingContainer::stirFryCount)
                ).apply(inst,SkilletCookingContainer::new)));

        public static final StreamCodec<RegistryFriendlyByteBuf, SkilletCookingContainer> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC,SkilletCookingContainer::container,
                ByteBufCodecs.INT,SkilletCookingContainer::stirFryCount,
                SkilletCookingContainer::new
        );

        @Override
        public ItemStack container() {
            return container.copy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SkilletCookingContainer that = (SkilletCookingContainer) o;
            return stirFryCount == that.stirFryCount && Objects.equals(container, that.container);
        }

        @Override
        public int hashCode() {
            return Objects.hash(container, stirFryCount);
        }
    }

}
