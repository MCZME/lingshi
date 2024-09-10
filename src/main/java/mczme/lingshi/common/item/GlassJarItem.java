package mczme.lingshi.common.item;

import mczme.lingshi.common.block.entity.GlassJarBlockEntity;
import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.common.registry.ModFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.FluidStack;

public class GlassJarItem extends BlockItem {

    public GlassJarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player pPlayer = pContext.getPlayer();
        Level pLevel = pContext.getLevel();
        InteractionHand hand = pContext.getHand();
        ItemStack itemStack = pContext.getItemInHand();
        BlockHitResult blockhitresult =
                getPlayerPOVHitResult(pLevel, pPlayer, getFluidStack(itemStack).isEmpty() ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (!pLevel.mayInteract(pPlayer, blockpos) || !pPlayer.mayUseItemAt(blockpos1, direction, itemStack)) {
                return InteractionResult.FAIL;
            } else if(getFluidStack(itemStack).isEmpty()){
                if(pLevel.getBlockState(blockpos).is(Blocks.WATER)) {
                    return tryFillJar(Fluids.WATER, pPlayer, blockpos, pLevel, itemStack, hand);
                }else if(pLevel.getBlockState(blockpos).is(ModBlocks.OIL_LIQUID_BLOCK.get())) {
                    return tryFillJar(ModFluids.OIL_SOURCE.get(), pPlayer, blockpos, pLevel, itemStack, hand);
                }
            }
        }
        return super.useOn(pContext);
    }

    private InteractionResult tryFillJar(Fluid fluid, Player player, BlockPos pos, Level level, ItemStack itemStack, InteractionHand hand) {
        BlockState blockstate1 = level.getBlockState(pos);
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        GlassJarBlockEntity blockEntity = new GlassJarBlockEntity(pos, level.getBlockState(pos));
        blockEntity.setFluidStack(new FluidStack(fluid, 1000));
        blockEntity.saveToItem(itemStack,level.registryAccess());
        level.removeBlockEntity(pos);
        ((BucketPickup)(blockstate1.getBlock())).getPickupSound(blockstate1).ifPresent(p_150709_ -> player.playSound(p_150709_, 1.0F, 1.0F));
        player.setItemInHand(hand, itemStack);
        return InteractionResult.SUCCESS;
    }

    private FluidStack getFluidStack(ItemStack pStack) {
        if(pStack.get(DataComponents.BLOCK_ENTITY_DATA)!=null) {
            CustomData customData = pStack.get(DataComponents.BLOCK_ENTITY_DATA);
            FluidStack fluidStack = FluidStack.EMPTY;
            if (customData.copyTag().getCompound("fluidStack") != null) {
                CompoundTag compoundTag = customData.copyTag().getCompound("fluidStack");
                fluidStack = FluidStack.parse(Minecraft.getInstance().level.registryAccess(), compoundTag).orElse(FluidStack.EMPTY);
            }
            return fluidStack;
        }
        return FluidStack.EMPTY;
    }

}
