package me.rrs.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Itemstack {
    public static void rename(ItemStack itemStack, String name){
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
