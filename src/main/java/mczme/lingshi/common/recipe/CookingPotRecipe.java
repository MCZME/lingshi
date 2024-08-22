package mczme.lingshi.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mczme.lingshi.client.recipebook.CookingFoodRecipeLabel;
import mczme.lingshi.common.recipe.input.CookingFoodRecipeInput;
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

public class CookingPotRecipe extends CookingFoodRecipe implements Recipe<CookingFoodRecipeInput> {

    public static final int MAX_SLOT = 6;

    private final List<Ingredient> items;
    private final FluidStack fluids;
    private final CookingPotContainer container;
    private final ItemStack result;

    final String group;

    public CookingPotRecipe(List<Ingredient> items, FluidStack fluids, CookingPotContainer container, ItemStack result, String group,CookingFoodRecipeLabel label) {
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
    public boolean matches(CookingFoodRecipeInput pInput, Level pLevel) {
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
    public ItemStack assemble(CookingFoodRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModSerializer.COOKING_POT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COOKING_POT_RECIPE.get();
    }
    public FluidStack getFluid() {
        return fluids;
    }

    public List<Ingredient> getItems() {
        return items;
    }

    public ItemStack getResult() {
        return result;
    }

    public CookingPotContainer getContainer() {
        return container;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    public record CookingPotContainer(ItemStack container, int braisingTime) {

        public static final CookingPotContainer EMPTY = new CookingPotContainer(ItemStack.EMPTY, 0);

        public static final Codec<CookingPotContainer> CODEC = Codec.lazyInitialized(
                () -> RecordCodecBuilder.create(inst -> inst.group(
                        ModoptionalField("container", ItemStack.OPTIONAL_CODEC, ItemStack.EMPTY).forGetter(CookingPotContainer::container),
                        ModoptionalField("braising_time", Codec.INT, 0).forGetter(CookingPotContainer::braisingTime)
                ).apply(inst, CookingPotContainer::new)));

        public static final StreamCodec<RegistryFriendlyByteBuf, CookingPotContainer> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, CookingPotContainer::container,
                ByteBufCodecs.INT, CookingPotContainer::braisingTime,
                CookingPotContainer::new
        );

        @Override
        public ItemStack container() {
            return container.copy();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CookingPotContainer that = (CookingPotContainer) o;
            return braisingTime == that.braisingTime && Objects.equals(container, that.container);
        }

        @Override
        public int hashCode() {
            return Objects.hash(container, braisingTime);
        }
    }

}
