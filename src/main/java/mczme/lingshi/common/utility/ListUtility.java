package mczme.lingshi.common.utility;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListUtility {

    public static List<ItemStack> copy(List<ItemStack> list){
        List<ItemStack> newList = new ArrayList<>();
        for (ItemStack t : list) {
            newList.add(t.copy());
        }
        return newList;
    }
}
