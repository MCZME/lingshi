package mczme.lingshi.common.util;

import mczme.lingshi.common.block.entity.baseblockentity.ICanBeHeated;
import mczme.lingshi.common.item.baseitem.IFluidStackItem;
import mczme.lingshi.common.registry.BlockEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidActionUtil {
    /**
     * 向流体容器添加流体
     * @param blockEntity 要添加流体的方块容器
     * @param amount 添加的流体数量
     * @param itemStack 手中存储流体的容器
     * @param player 玩家
     */
    public static boolean AddFluidStack(BlockEntity blockEntity, int amount, ItemStack itemStack, Player player) {
        if (blockEntity instanceof ICanBeHeated fluidEntity) {
            FluidStack fluidStack = fluidEntity.getFluid();
            if(itemStack.getItem() instanceof IFluidStackItem container){
                FluidStack fluidStack1 = container.getFluidStack(itemStack);
                if(fluidStack.isEmpty()){
                    if(amount<=fluidEntity.getMaxAmount()){
                        Fluid fluid = fluidStack1.getFluid();
                        fluidStack1.grow(-amount);
                        fluidEntity.setFluid(new FluidStack(fluid,amount));
                        ItemStack itemStack1 = saveBlockEntityToItemStack(itemStack,fluidStack1,blockEntity.getLevel().registryAccess());
                        player.setItemInHand(player.getUsedItemHand(),itemStack1);
                        return true;
                    }
                }
                if (fluidStack.is(fluidStack1.getFluid())) {
                    if(fluidEntity.getMaxAmount()==fluidStack.getAmount()){
                        return false;
                    }else if(amount<=fluidEntity.getMaxAmount()-fluidStack.getAmount()){
                        fluidStack1.grow(-amount);
                        fluidStack.grow(amount);
                    }else if(amount>fluidEntity.getMaxAmount()-fluidStack.getAmount()){
                        fluidStack.grow(fluidEntity.getMaxAmount()-fluidStack.getAmount());
                        fluidStack1.grow(-fluidEntity.getMaxAmount()+fluidStack.getAmount());
                    }
                    ItemStack itemStack1 = saveBlockEntityToItemStack(itemStack,fluidStack1,blockEntity.getLevel().registryAccess());
                    player.setItemInHand(player.getUsedItemHand(),itemStack1);
                    return true;
                }
            }
        }
        return false;
    }

    private static ItemStack saveBlockEntityToItemStack(ItemStack pStack, FluidStack fluidStack, HolderLookup.Provider pRegistries){
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.remove("id");
        if(!fluidStack.isEmpty()){
            compoundtag.put("fluidStack",fluidStack.save(pRegistries));
            BlockEntity.addEntityType(compoundtag, BlockEntityTypes.GLASS_JAR_BLOCK_ENTITY.get());
            pStack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(compoundtag));
        }else {
            pStack.remove(DataComponents.BLOCK_ENTITY_DATA);
        }
        return pStack;
    }
}
