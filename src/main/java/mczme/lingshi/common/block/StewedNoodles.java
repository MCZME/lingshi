package mczme.lingshi.common.block;

import mczme.lingshi.common.block.baseblock.FoodBlock;
import mczme.lingshi.common.registry.ModEffects;
import mczme.lingshi.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class StewedNoodles extends FoodBlock {

    public static final BooleanProperty EGG = BooleanProperty.create("egg");

    public StewedNoodles(Properties properties) {
        super(properties);
        this.defaultBlockState().setValue(EGG, false);
    }

    public ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pStack.is(ModItems.FRIED_EGG.get())&& !pState.getValue(EGG)) {
            pStack.consumeAndReturn(1,pPlayer);
            pLevel.setBlock(pPos, pState.setValue(EGG, true), Block.UPDATE_CLIENTS);
            return ItemInteractionResult.SUCCESS;
        }
        ItemStack itemStack = new ItemStack(pState.getBlock().asItem());
        if(pState.getValue(EGG)) {
            itemStack = new ItemStack(ModItems.EGG_ADDED_STEWED_NOODLES.get());
        }
        if (pState.getValue(AMOUNT) == 0) {
            pLevel.destroyBlock(pPos, true);
            return ItemInteractionResult.SUCCESS;
        } else {
            if (pPlayer.canEat(false)) {
                pLevel.setBlock(pPos, pState.setValue(AMOUNT, 0), Block.UPDATE_CLIENTS);
                pPlayer.eat(pLevel, itemStack.copy());
                if (pPlayer.hasEffect(ModEffects.GRATIFICATION_EFFECT)) {
                    FoodProperties foodData = itemStack.get(DataComponents.FOOD);
                    pPlayer.eat(pLevel, itemStack, new FoodProperties.Builder().nutrition(0).saturationModifier(foodData.saturation()).build());
                }

                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        boolean egg = pContext.getItemInHand().is(ModItems.EGG_ADDED_STEWED_NOODLES.get());
        return  this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(AMOUNT, 1).setValue(EGG, egg);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(EGG);
    }
}
