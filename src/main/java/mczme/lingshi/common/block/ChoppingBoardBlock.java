package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.block.entity.ChoppingBoardBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChoppingBoardBlock extends BaseEntityBlock {

    public ChoppingBoardBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public ItemInteractionResult useItemOn(
            ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
    ) {
        if (pLevel.getBlockEntity(pPos) instanceof ChoppingBoardBlockEntity blockEntity) {
            if (!pLevel.isClientSide) {
                ItemStack stack = pStack.consumeAndReturn(1, pPlayer);
                if (blockEntity.getTheItem().isEmpty() && !stack.isEmpty()) {
                    blockEntity.setTheItem(stack);
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }else {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            } else {
                return ItemInteractionResult.CONSUME;
            }
        }
        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;

    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof ChoppingBoardBlockEntity blockentity) {
            if (!pLevel.isClientSide) {
                ItemStack stack = blockentity.getTheItem();
                if (!stack.isEmpty()) {
                    Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), stack);
                    blockentity.setTheItem(ItemStack.EMPTY);
                    blockentity.setChanged();
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ChoppingBoardBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
