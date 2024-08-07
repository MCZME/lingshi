package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RiceSeedlingBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {

    public static final BooleanProperty EMERGE = BooleanProperty.create("emerge");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape[] SHAPE = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)
    };

    public RiceSeedlingBlock(Properties properties) {
        super(properties.noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY).randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(EMERGE, false).setValue(AGE, 0).setValue(WATERLOGGED, true));
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public int getMaxAge() {
        return 1;
    }

    public BlockState getStateForAge(int pAge) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), pAge);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        if (!pLevel.isAreaLoaded(pPos, 1)) return;
        if (pLevel.getRawBrightness(pPos.above(), 0) >= 6) {
            int age = getAge(pState);
            boolean isEmerge = pState.getValue(EMERGE);
            if (age <= getMaxAge()) {
                float f = 12.0F;
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    if (age != getMaxAge()) {
                        pLevel.setBlock(pPos, this.getStateForAge(age + 1), 2);
                        net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                    } else if (!isEmerge) {
                        RiceSeedlingTopBlock topBlock = ModBlocks.RICE_SEEDING_TOP.get();
                        if (topBlock.defaultBlockState().canSurvive(pLevel, pPos.above()) && pLevel.isEmptyBlock(pPos.above())) {
                            pLevel.setBlockAndUpdate(pPos, pState.setValue(EMERGE, true));
                            pLevel.setBlockAndUpdate(pPos.above(), topBlock.defaultBlockState());
                            net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                        }
                    } else if (pLevel.getBlockState(pPos.above()).is(ModBlocks.RICE_SEEDING_TOP.get())) {
                        if (pLevel.getBlockState(pPos.above()).getValue(RiceSeedlingTopBlock.AGE) == 4) {
                            pLevel.setBlockAndUpdate(pPos, pState.setValue(WATERLOGGED, false));
                        }
                    }
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE[getAge(pState)];
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        BlockState upperState = pLevel.getBlockState(pPos.above());
        if (upperState.getBlock() instanceof RiceSeedlingTopBlock) {
            return !((RiceSeedlingTopBlock) upperState.getBlock()).isMaxAge(upperState);
        }
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 1, 2);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        int ageGrowth = Math.min(this.getAge(pState) + this.getBonemealAgeIncrease(pLevel), 4);
        if (ageGrowth <= this.getMaxAge()) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, ageGrowth));
        } else {
            BlockState top = pLevel.getBlockState(pPos.above());
            if (top.getBlock() == ModBlocks.RICE_SEEDING_TOP.get()) {
                BonemealableBlock growable = (BonemealableBlock) pLevel.getBlockState(pPos.above()).getBlock();
                if (growable.isValidBonemealTarget(pLevel, pPos.above(), top)) {
                    growable.performBonemeal(pLevel, pLevel.random, pPos.above(), top);
                }
                if (pLevel.getBlockState(pPos.above()).getValue(RiceSeedlingTopBlock.AGE) == 4) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(WATERLOGGED, false));
                }
            } else {
                RiceSeedlingTopBlock Upper = ModBlocks.RICE_SEEDING_TOP.get();
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (Upper.defaultBlockState().canSurvive(pLevel, pPos.above()) && pLevel.isEmptyBlock(pPos.above())) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, this.getMaxAge()));
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(EMERGE, true));
                    pLevel.setBlock(pPos.above(), Upper.defaultBlockState().setValue(RiceSeedlingTopBlock.AGE, remainingGrowth), 2);
                }
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.DIRT);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        FluidState fluid = pLevel.getFluidState(pPos);
        if (pState.getValue(WATERLOGGED)) {
            return super.canSurvive(pState, pLevel, pPos) && fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
        } else {
            return super.canSurvive(pState, pLevel, pPos);
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return fluid.is(FluidTags.WATER) && fluid.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player pPlayer, BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(EMERGE, AGE, WATERLOGGED);
    }
}
