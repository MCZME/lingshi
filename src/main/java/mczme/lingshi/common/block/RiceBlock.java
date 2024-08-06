package mczme.lingshi.common.block;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import org.jetbrains.annotations.NotNull;

public class RiceBlock extends CropBlock {
    public RiceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.RICE.get();
    }
}
