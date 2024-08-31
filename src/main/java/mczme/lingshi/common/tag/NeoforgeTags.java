package mczme.lingshi.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NeoforgeTags {

    public static final TagKey<Item> KNIFE = neoforgeTag(Registries.ITEM, "knife");
    public static final TagKey<Item> FLOUR = neoforgeTag(Registries.ITEM, "flour");
    public static final TagKey<Item> DOUGH = neoforgeTag(Registries.ITEM, "dough");
    public static final TagKey<Item> NOODLES = neoforgeTag(Registries.ITEM, "noodles");

    public static final TagKey<Item> CROPS_CABBAGE = neoforgeTag(Registries.ITEM, "crops/cabbage");

    private static <T> TagKey<T> neoforgeTag(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<T>> pRegistry, String name) {
        return TagKey.create(pRegistry, ResourceLocation.fromNamespaceAndPath("neoforge", name));
    }
}
