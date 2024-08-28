package mczme.lingshi.common.data.loot;

import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class BlockLoot extends BlockLootSubProvider {

    public static final Set<Block> BLOCK = Set.of(
            ModBlocks.TEA_TREE.get(),
            ModBlocks.TEA_LEAF.get(),
            ModBlocks.CHOPPING_BOARD.get(),
            ModBlocks.SKILLET.get(),
            ModBlocks.COOKING_POT.get(),
            ModBlocks.STOVE.get()
    );

    public BlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS,lookupProvider);
    }


    @Override
    protected void generate() {
        dropSelf(ModBlocks.TEA_TREE.get());
        dropOther(ModBlocks.TEA_LEAF.get(), ModItems.TEA_LEAF.get());
        dropSelf(ModBlocks.CHOPPING_BOARD.get());
        dropSelf(ModBlocks.SKILLET.get());
        dropSelf(ModBlocks.COOKING_POT.get());
        dropSelf(ModBlocks.STOVE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BLOCK;
    }
}
