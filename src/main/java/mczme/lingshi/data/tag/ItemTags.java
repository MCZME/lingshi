package mczme.lingshi.data.tag;

import mczme.lingshi.common.registry.ModItems;
import mczme.lingshi.lingshi;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static mczme.lingshi.common.tag.ModTags.*;
import static mczme.lingshi.common.tag.NeoforgeTags.*;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, lingshi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(KNIFE).add(ModItems.IRON_KNIFE.get());
        this.tag(ChoppingBoard_TOOL).add(ModItems.IRON_KNIFE.get(), Items.WATER_BUCKET);

        this.tag(FLOUR).add(ModItems.FLOUR.get());
        this.tag(DOUGH).add(ModItems.DOUGH.get());
        this.tag(NOODLES).add(ModItems.NOODLES.get());

        this.tag(CROPS_CABBAGE).add(ModItems.CABBAGE_LEAF.get(),ModItems.CABBAGE.get());
    }
}
