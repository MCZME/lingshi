package mczme.lingshi.common.effect;

import mczme.lingshi.common.registry.ModEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class GratificationEffect extends MobEffect {
    public GratificationEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(!pLivingEntity.level().isClientSide() && pLivingEntity instanceof  Player player) {
            FoodData foodData = player.getFoodData();
            if(foodData.getFoodLevel() < 18.0F) {
                float Exhaustion = Math.min(player.getFoodData().getExhaustionLevel(), 4.0F);
                if (player.getFoodData().getExhaustionLevel() > 0.0F) {
                    player.causeFoodExhaustion(-Exhaustion);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    public static class Event{
        @SubscribeEvent
        public void onEvent(LivingEntityUseItemEvent.Finish event) {
            if(event.getEntity() instanceof Player player && !event.getEntity().level().isClientSide()){
                if(player.hasEffect(ModEffects.GRATIFICATION_EFFECT)){
                    ItemStack itemStack = event.getItem();
                    if(itemStack.get(DataComponents.FOOD) != null) {
                        player.getFoodData().eat(0,itemStack.get(DataComponents.FOOD).saturation());
                    }
                }
            }
        }
    }

}
