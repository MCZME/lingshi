package mczme.lingshi.common.block;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class CabbageBlock extends ModCropBlock {
    public CabbageBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.CABBAGE_SEED.get();
    }
}
