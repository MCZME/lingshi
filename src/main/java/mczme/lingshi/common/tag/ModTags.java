package mczme.lingshi.common.tag;

import mczme.lingshi.lingshi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Block> HEAT_SOURCE = modTag(Registries.BLOCK, "heat_source");
    public static final TagKey<Block> CAN_SUPPORT = modTag(Registries.BLOCK, "can_support");

    public static final TagKey<Item> ChoppingBoard_TOOL = modTag(Registries.ITEM, "choppingboard_tool");

    private static <T> TagKey<T> modTag(net.minecraft.resources.ResourceKey<? extends net.minecraft.core.Registry<T>> pRegistry, String name) {
        return TagKey.create(pRegistry, ResourceLocation.fromNamespaceAndPath(lingshi.MODID, name));
    }

}
