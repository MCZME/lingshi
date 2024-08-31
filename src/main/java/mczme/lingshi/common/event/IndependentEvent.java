package mczme.lingshi.common.event;

import mczme.lingshi.common.effect.GratificationEffect;
import net.neoforged.neoforge.common.NeoForge;

public class IndependentEvent {
    public static void register(){
        NeoForge.EVENT_BUS.register(new GratificationEffect.Event());
    }
}
