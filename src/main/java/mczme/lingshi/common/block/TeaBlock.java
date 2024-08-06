package mczme.lingshi.common.block;

import mczme.lingshi.common.registry.ModBlocks;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TeaBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0, 0.0, 0, 16.0, 2.0, 16.0),
            Block.box(0, 0.0, 0, 16.0, 4.0, 16.0),
            Block.box(0, 0.0, 0, 16.0, 6.0, 16.0),
            Block.box(0, 0.0, 0, 16.0, 8.0, 16.0)
    };

    public TeaBlock(Properties properties) {
        super(properties.randomTicks().noCollission().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos)) && this.mayPlaceOn(level.getBlockState(pos.below()), level, pos);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.TEA_LEAF.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.TEA_TREE.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

}
