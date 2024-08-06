package mczme.lingshi.common.block;

import com.mojang.serialization.MapCodec;
import mczme.lingshi.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TeaTreeBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final VoxelShape[] SHAPE = new VoxelShape[]{Block.box(7.0, 0, 7.0, 10, 10, 10),
            Shapes.or(Block.box(7.0, 0, 7.0, 10, 8, 10),Block.box(3.5,8,3.5,12.5,14,12.5)),
            Shapes.or(Block.box(7.0, 0, 7.0, 10, 6, 10),Block.box(2.0,6,2,14,14,14)),
            Shapes.or(Block.box(7.0, 0, 7.0, 10, 5, 10),Block.box(0,5,0,16,16,16))};

    public TeaTreeBlock(Properties properties) {
        super(properties.randomTicks().noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return null;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public int getMaxAge() {
        return 3;
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
            if (age <= getMaxAge()) {
                float f = 12.0F;
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    if (age != getMaxAge()) {
                        pLevel.setBlock(pPos, this.getStateForAge(age + 1), 2);
                        net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                    } else {
                        TeaBlock topBlock = ModBlocks.TEA_LEAF.get();
                        if (topBlock.defaultBlockState().canSurvive(pLevel, pPos.above()) && pLevel.isEmptyBlock(pPos.above())) {
                            pLevel.setBlockAndUpdate(pPos.above(), topBlock.defaultBlockState());
                            net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(pLevel, pPos, pState);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        BlockState upperState = pLevel.getBlockState(pPos.above());
        if (upperState.getBlock() instanceof TeaBlock) {
            return !((TeaBlock) upperState.getBlock()).isMaxAge(upperState);
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
        int ageGrowth = Math.min(this.getAge(pState) + this.getBonemealAgeIncrease(pLevel), 5);
        if (ageGrowth <= this.getMaxAge()) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, ageGrowth));
        } else {
            BlockState top = pLevel.getBlockState(pPos.above());
            if (top.getBlock() == ModBlocks.TEA_LEAF.get()) {
                BonemealableBlock growable = (BonemealableBlock) pLevel.getBlockState(pPos.above()).getBlock();
                if (growable.isValidBonemealTarget(pLevel, pPos.above(), top)) {
                    growable.performBonemeal(pLevel, pLevel.random, pPos.above(), top);
                }
            } else {
                TeaBlock Upper = ModBlocks.TEA_LEAF.get();
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (Upper.defaultBlockState().canSurvive(pLevel, pPos.above()) && pLevel.isEmptyBlock(pPos.above())) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, this.getMaxAge()));
                    pLevel.setBlock(pPos.above(), Upper.defaultBlockState().setValue(TeaBlock.AGE, remainingGrowth), 2);
                }
            }
        }
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity) {
            pEntity.makeStuckInBlock(pState, new Vec3(0.8F, 0.75, 0.8F));
        }
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE[getAge(pState)];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
