package mczme.lingshi.common.block.entity.baseblockentity;

import mczme.lingshi.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ICanBeHeated {

    default boolean isHeated(Level pLevel, BlockPos pPos){
        return pLevel.getBlockState(pPos.below()).is(ModTags.HEAT_SOURCE);
    }
}
