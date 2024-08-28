package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.block.entity.ChoppingBoardBlockEntity;
import mczme.lingshi.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChoppingBoardBlock extends BaseEntityBlock {

    public static final VoxelShape NORTH_SHAPE = Block.box(1.0D, 0.0D, 4.0D, 15.0D, 1.0D, 13.0D);
    public static final VoxelShape SOUTH_SHAPE = Block.box(1.0D, 0.0D, 3.0D, 15.0D, 1.0D, 12.0D);
    public static final VoxelShape WEST_SHAPE = Block.box(4.0D, 0.0D, 1.0D, 13.0D, 1.0D, 15.0D);
    public static final VoxelShape EAST_SHAPE = Block.box(3.0D, 0.0D, 1.0D, 12.0D, 1.0D, 15.0D);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ChoppingBoardBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof ChoppingBoardBlockEntity blockEntity) {
            if (!pStack.isEmpty()) {
                if (pStack.is(ModTags.ChoppingBoard_TOOL) && !blockEntity.getTheItem().isEmpty()) {
                    List<ItemStack> stacks = blockEntity.getRecipeAndResult(pPlayer.getMainHandItem());
                    if (stacks == null || stacks.isEmpty()) {
                        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    }
                    stacks.forEach(itemStack -> Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), itemStack));
                    blockEntity.setTheItem(ItemStack.EMPTY);
                    if (!pLevel.isClientSide()) {
                        pStack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
                    }
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else if (pStack.is(ModTags.ChoppingBoard_TOOL) && blockEntity.getTheItem().isEmpty() && !pPlayer.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
                    blockEntity.setTheItem(pPlayer.getItemInHand(InteractionHand.OFF_HAND).consumeAndReturn(1, pPlayer));
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else if (blockEntity.getTheItem().isEmpty()) {
                    blockEntity.setTheItem(pStack.consumeAndReturn(1, pPlayer));
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;

    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof ChoppingBoardBlockEntity blockentity) {
            ItemStack stack = blockentity.getTheItem();
            if (!stack.isEmpty()) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), stack);
                blockentity.setTheItem(ItemStack.EMPTY);
                blockentity.setChanged();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof ChoppingBoardBlockEntity blockEntity) {
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), blockEntity.getTheItem());
            blockEntity.setTheItem(ItemStack.EMPTY);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ChoppingBoardBlockEntity(pPos, pState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below()) || canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
