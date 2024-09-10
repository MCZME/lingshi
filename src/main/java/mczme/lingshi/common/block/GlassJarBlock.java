package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.block.entity.GlassJarBlockEntity;
import mczme.lingshi.common.registry.ModFluids;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class GlassJarBlock extends BaseEntityBlock {

    public static final MapCodec<GlassJarBlock> CODEC = simpleCodec(GlassJarBlock::new);
    public static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 15.3, 11);

    public GlassJarBlock(Properties pProperties) {
        super(pProperties);
    }

    public ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof GlassJarBlockEntity blockEntity){
            if(pStack.is(Items.WATER_BUCKET)){
                if(blockEntity.addFluidStack(new FluidStack(Fluids.WATER,1000))){
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            }else if(pStack.is(ModItems.OIL_BUCKET.get())){
                if(blockEntity.addFluidStack(new FluidStack(ModFluids.OIL_SOURCE.get(),1000))){
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                    blockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (pStack.is(Items.BUCKET)) {
                FluidStack fluidStack = blockEntity.removeFluidStack(1000);
                if(!fluidStack.isEmpty()){
                    pPlayer.setItemInHand(pHand, new ItemStack(fluidStack.getFluid().getBucket()));
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
        if(pLevel.getBlockEntity(pPos) instanceof GlassJarBlockEntity blockEntity){

        }
        return InteractionResult.PASS;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        ItemStack itemStack = new ItemStack(ModItems.GLASS_JAR.get());
        pLevel.getBlockEntity(pPos).saveToItem(itemStack,pLevel.registryAccess());
        Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), itemStack);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GlassJarBlockEntity(pPos,pState);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return canSupportRigidBlock(pLevel, pPos.below()) || canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

}
