package mczme.lingshi.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class RiceSeedlingItem extends ItemNameBlockItem {
    public RiceSeedlingItem(Block block, Properties properties) {
        super(block,properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.place(new BlockPlaceContext(context));

        return result;
    }
}
