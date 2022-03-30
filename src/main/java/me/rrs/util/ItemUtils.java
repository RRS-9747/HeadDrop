package me.rrs.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtils {
    public static ItemStack rename(ItemStack item, String name){
        ItemStack m = item.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }


    public static void setLore(org.bukkit.inventory.ItemStack itemStack, String[] lore){
        ItemMeta m = itemStack.getItemMeta();
        if (m != null){
            m.setLore(Arrays.asList(lore));
        }
        itemStack.setItemMeta(m);
    }

}
