package mczme.lingshi.common.tag;

import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NeoforgeTags {

    public static final TagKey<Item> KNIFE = neoforgeTag(Registries.ITEM, "knife");

    private static <T> TagKey<T> neoforgeTag(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<T>> pRegistry, String name) {
        return TagKey.create(pRegistry, ResourceLocation.fromNamespaceAndPath("neoforge", name));
    }
}
