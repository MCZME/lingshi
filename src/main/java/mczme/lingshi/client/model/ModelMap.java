package mczme.lingshi.client.model;

import mczme.lingshi.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ModelMap {
    private static final Map<Item, Item> map = new HashMap<>();

    public ModelMap(){
    }

    public static void init() {
        map.put(Items.EGG, ModItems.FRIED_EGG.get());
    }

    public static ItemStack get(ItemStack stack) {
        Item item = map.get(stack.getItem());
        if (item != null) {
            return new ItemStack(item, stack.getCount());
        }
        return stack;
    }
}
