package mczme.lingshi.common.block;

import mczme.lingshi.common.block.baseblock.ModCropBlock;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class BeanBlock extends ModCropBlock {
    public BeanBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.BEAN.get();
    }
}
