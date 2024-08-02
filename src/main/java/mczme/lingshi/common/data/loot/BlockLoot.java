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

    );

    public BlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS,lookupProvider);
    }


    @Override
    protected void generate() {

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BLOCK;
    }
}
