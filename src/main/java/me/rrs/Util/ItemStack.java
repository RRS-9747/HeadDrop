package me.rrs.Util;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemStack {
    public static void rename(org.bukkit.inventory.ItemStack itemStack, String name){
        ItemMeta m = itemStack.getItemMeta();
        if (m != null) {
            m.setDisplayName(name);
        }
        itemStack.setItemMeta(m);
    }


    public static void setLore(org.bukkit.inventory.ItemStack itemStack, String[] lore){
        ItemMeta m = itemStack.getItemMeta();
        if (m != null){
            m.setLore(Arrays.asList(lore));
        }
        itemStack.setItemMeta(m);
    }

}
