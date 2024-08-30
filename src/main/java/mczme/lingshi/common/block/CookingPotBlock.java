package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.block.entity.CookingPotBlockEntity;
import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;


public class CookingPotBlock extends BaseEntityBlock {

    public static final BooleanProperty COVER = BooleanProperty.create("cover");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 12, 13);

    public CookingPotBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(COVER, false));
    }

    public ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof CookingPotBlockEntity blockEntity) {
            if (!blockEntity.getItemStacks().getStackInSlot(6).isEmpty() && pStack.is(blockEntity.getItemStacks().getStackInSlot(6).getItem())) {
                if (pPlayer.addItem(new ItemStack(blockEntity.getItemStacks().getStackInSlot(7).getItem(), 1))) {
                    blockEntity.getItemStacks().getStackInSlot(7).shrink(1);
                    if(!pStack.is(ModItems.SPATULA.get())){
                        pStack.consume(1, pPlayer);
                    }
                    blockEntity.stewingTime = 0;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (pStack.is(ModItems.SPATULA.get())) {
                blockEntity.clearDrop(pLevel,pPos);
                blockEntity.setChanged();
                return ItemInteractionResult.SUCCESS;
            } else if (!blockEntity.isFull() && !pStack.isEmpty() && !pStack.is(ModItems.POT_LID.get()) && !pState.getValue(COVER)) {
                if (pStack.is(Items.WATER_BUCKET)) {
                    blockEntity.setFluid(new FluidStack(Fluids.WATER, 1000));
                    blockEntity.getCookingTime()[6] = 0;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else if (pStack.is(ModItems.OIL_BUCKET.get())) {
                    blockEntity.setFluid(new FluidStack(ModFluids.OIL_SOURCE.get(), 1000));
                    blockEntity.getCookingTime()[6] = 0;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
                if (pStack.getCount() <= 16) {
                    blockEntity.setItem(pStack.consumeAndReturn(pStack.getCount(), pPlayer));
                } else {
                    blockEntity.setItem(pStack.consumeAndReturn(16, pPlayer));
                }
                blockEntity.setChanged();
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof CookingPotBlockEntity blockEntity) {
            if (pState.getValue(COVER)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(COVER, false));
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY() + 0.7, pPos.getZ(), new ItemStack(ModItems.POT_LID.get()));
                return InteractionResult.SUCCESS;
            } else if (pPlayer.isShiftKeyDown()) {
                pPlayer.openMenu(blockEntity, pPos);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            } else if (!blockEntity.isEmpty()) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY() + 0.2, pPos.getZ(), blockEntity.dropItem());
                blockEntity.setChanged();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof CookingPotBlockEntity blockEntity) {
            if (!pNewState.is(ModBlocks.COOKING_POT.get())) {
                for (int i = 0; i < blockEntity.getMAX(); i++) {
                    Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), blockEntity.dropItem());
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below()) || canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CookingPotBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, COVER);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType) {
        return !pLevel.isClientSide ? CookingPotBlockEntity::serverTick : null;
    }

}
