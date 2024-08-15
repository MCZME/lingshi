package mczme.lingshi;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class ModEnumParams {

    public static final EnumProxy<RecipeBookCategories> SKILLET_SEARCH_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(Items.COMPASS) );
    public static final EnumProxy<RecipeBookCategories> SKILLET_HEAT_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(ModItems.RICE_OF_EAR.get()));
    public static final EnumProxy<RecipeBookCategories> PAN_FRY_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(ModItems.RICE.get()));
    public static final EnumProxy<RecipeBookCategories> STIR_FRY_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(ModItems.IRON_KNIFE.get()));
    public static final EnumProxy<RecipeBookCategories> BOIL_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(Items.WHEAT));
    public static final EnumProxy<RecipeBookCategories> MISC_ENUM_PROXY = new EnumProxy<>(RecipeBookCategories.class, params(Items.BUCKET));

    private static Supplier<List<ItemStack>> params(Item item){
        return ()-> List.of(new ItemStack(item));
    }
}
