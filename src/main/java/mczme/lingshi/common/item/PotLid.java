package mczme.lingshi.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

import static mczme.lingshi.common.block.CookingPotBlock.COVER;

public class PotLid extends Item {
    public PotLid(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().getBlockState(pContext.getClickedPos()).getValue(COVER)) {
            pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos(), pContext.getLevel().getBlockState(pContext.getClickedPos()).setValue(COVER, true));
        }
        return InteractionResult.SUCCESS;
    }

}
