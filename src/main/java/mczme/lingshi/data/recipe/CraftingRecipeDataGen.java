package mczme.lingshi.data.recipe;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import static mczme.lingshi.data.recipe.Recipes.has;

public class CraftingRecipeDataGen {

    public CraftingRecipeDataGen(RecipeOutput output) {
        buildRecipes(output);
    }

    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_KNIFE.get())
                .pattern("#X")
                .define('X', Items.IRON_INGOT)
                .define('#', Items.STICK)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SPATULA.get())
                .pattern("  X")
                .pattern(" X ")
                .pattern("#  ")
                .define('X', Items.IRON_INGOT)
                .define('#', Items.STICK)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CHOPPING_BOARD.get())
                .pattern("XX#")
                .pattern("XX#")
                .define('X', ItemTags.PLANKS)
                .define('#', Items.STICK)
                .unlockedBy("has_iron_ingot", has(Items.STICK))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.SKILLET.get())
                .pattern("AA#")
                .pattern("XX ")
                .define('X', ItemTags.TERRACOTTA)
                .define('#', ItemTags.PLANKS)
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(ItemTags.PLANKS))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.COOKING_POT.get())
                .pattern("A A")
                .pattern("X X")
                .pattern("XXX")
                .define('X', ItemTags.TERRACOTTA)
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(ItemTags.TERRACOTTA))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.POT_LID.get())
                .pattern(" A ")
                .pattern("XXX")
                .define('X', ItemTags.TERRACOTTA)
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(ItemTags.TERRACOTTA))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.STOVE.get())
                .pattern("X X")
                .pattern("XAX")
                .define('X', Items.MUD_BRICKS)
                .define('A', Items.CAMPFIRE)
                .unlockedBy("has_iron_ingot", has(Items.MUD_BRICKS))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.GLASS_JAR.get())
                .pattern(" A ")
                .pattern("X X")
                .pattern(" X ")
                .define('X', Tags.Items.GLASS_PANES)
                .define('A', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_glass_panes", has(Tags.Items.GLASS_PANES))
                .save(output);
    }

}
