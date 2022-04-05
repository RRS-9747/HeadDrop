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


    public static void setLore(ItemStack item, String[] lore){
        ItemMeta m = item.getItemMeta();
        m.setLore(Arrays.asList(lore));
        item.setItemMeta(m);
    }

}
