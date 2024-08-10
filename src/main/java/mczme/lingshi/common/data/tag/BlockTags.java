package mczme.lingshi.common.data.tag;

import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.lingshi;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.BlockTags.*;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, lingshi.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
//        斧 可挖掘
        tag(MINEABLE_WITH_AXE).add(ModBlocks.CHOPPING_BOARD.get());
    }
}