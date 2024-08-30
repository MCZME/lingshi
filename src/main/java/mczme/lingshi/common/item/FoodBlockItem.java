package mczme.lingshi.common.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class FoodBlockItem extends BlockItem {

    public FoodBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult interactionresult = InteractionResult.FAIL;
        if(pContext.getPlayer().isShiftKeyDown()){
            interactionresult = this.place(new BlockPlaceContext(pContext));
        }
        if (!interactionresult.consumesAction() && pContext.getItemInHand().has(DataComponents.FOOD)) {
            InteractionResult interactionresult1 = super.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }
}
