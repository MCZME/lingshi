package mczme.lingshi.common.item;

import mczme.lingshi.common.datacomponent.Eat;
import mczme.lingshi.common.registry.ModDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ConditionalFood extends Item {
    public ConditionalFood(Properties pProperties) {
        super(pProperties.component(ModDataComponents.EAT,new Eat(false)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        FoodProperties foodproperties = itemstack.getFoodProperties(pPlayer);
        Eat eat = itemstack.get(ModDataComponents.EAT);
        if (foodproperties != null && eat != null && eat.getEat()) {
            if (pPlayer.canEat(foodproperties.canAlwaysEat())) {
                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        }
    }

}
