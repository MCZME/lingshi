package mczme.lingshi.common.registry;

import mczme.lingshi.common.effect.GratificationEffect;
import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOD_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, lingshi.MODID);

    public static final DeferredHolder<MobEffect,GratificationEffect> GRATIFICATION_EFFECT = MOD_EFFECTS.register("gratification_effect", () -> new GratificationEffect(MobEffectCategory.BENEFICIAL, 0xffffff));

    public static void register(IEventBus eventBus){
        MOD_EFFECTS.register(eventBus);
    }
}
