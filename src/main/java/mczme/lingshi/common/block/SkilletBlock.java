package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.block.entity.SkilletBlockEntity;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static mczme.lingshi.common.tag.ModTags.CAN_SUPPORT;

public class SkilletBlock extends BaseEntityBlock {

    public static final MapCodec<SkilletBlock> CODEC = simpleCodec(SkilletBlock::new);
    public static final BooleanProperty HAS_SUPPORT = BooleanProperty.create("has_support");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SHAPE = Shapes.join(Block.box(1, 0, 1, 15, 3, 15),
            Block.box(2, 1, 2, 14, 3, 14),
            BooleanOp.ONLY_FIRST);

    public SkilletBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HAS_SUPPORT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof SkilletBlockEntity blockEntity) {
            if (!blockEntity.getItemStacks().getStackInSlot(5).isEmpty() && pStack.is(blockEntity.getItemStacks().getStackInSlot(5).getItem())) {
                if (pPlayer.addItem(blockEntity.result)) {
                    if(!pStack.is(ModItems.SPATULA.get())){
                        pStack.consume(1, pPlayer);
                    }
                    blockEntity.clear();
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (pStack.is(ModItems.SPATULA.get())) {
                if(blockEntity.size()==0){
                    blockEntity.setFluid(FluidStack.EMPTY);
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }else if (!blockEntity.isEmpty()) {
                    blockEntity.stirFryCount++;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (!blockEntity.isFull() && !pStack.isEmpty()) {
                if (pStack.is(Items.WATER_BUCKET)) {
                    blockEntity.setFluid(new FluidStack(Fluids.WATER, 250));
                    blockEntity.getCookingTime()[5] = 0;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else if (pStack.is(ModItems.OIL_BUCKET.get())) {
                    blockEntity.setFluid(new FluidStack(ModFluids.OIL_SOURCE.get(), 100));
                    blockEntity.getCookingTime()[5] = 0;
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
                blockEntity.setItem(pStack.consumeAndReturn(1, pPlayer));
                blockEntity.stirFryCount = 0;
                blockEntity.setChanged();
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof SkilletBlockEntity blockEntity) {
            if (pPlayer.isShiftKeyDown()) {
                if (!pLevel.isClientSide()) {
                    pPlayer.openMenu(blockEntity, pPos);
                }
            } else if (!blockEntity.isEmpty()) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY() + 0.2, pPos.getZ(), blockEntity.dropItem());
                blockEntity.setChanged();
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel.getBlockEntity(pPos) instanceof SkilletBlockEntity blockEntity) {
            for (int i = 0; i < blockEntity.getMAX(); i++) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), blockEntity.dropItem());
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SkilletBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType) {
        return !pLevel.isClientSide ? SkilletBlockEntity::serverTick : null;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(HAS_SUPPORT) ? Shapes.or(SHAPE, Block.box(0, -1, 0, 16, 0, 16)) : pState.getShape(pLevel, pPos);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(CAN_SUPPORT) || canSupportRigidBlock(pLevel, pPos.below()) || canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        boolean isSupport = pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(CAN_SUPPORT);
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HAS_SUPPORT, isSupport);
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
        pBuilder.add(FACING, HAS_SUPPORT);
    }
}
