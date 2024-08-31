package mczme.lingshi.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlateFoodBlock extends FoodBlock {

    public static final VoxelShape SHAPE = Shapes.or(Block.box(2, 0, 2, 14, 3, 14),
            Block.box(1,1,1,15,1,15)
    );

    public PlateFoodBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
