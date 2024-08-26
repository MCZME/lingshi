package mczme.lingshi.common.block.entity.baseblockentity;

import mczme.lingshi.client.menu.Slot.CookingItemStackHandler;
import mczme.lingshi.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public interface ICanBeHeated {

    FluidStack getFluid();

    CookingItemStackHandler getItemStacks();

    int[] getCookingTime();

    ItemStack getResult();

    ItemStack getContainer();

    int getMAX();

    default boolean isHeated(Level pLevel, BlockPos pPos){
        return pLevel.getBlockState(pPos.below()).is(ModTags.HEAT_SOURCE);
    }
}
