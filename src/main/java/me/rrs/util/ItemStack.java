package me.rrs.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStack {


    public static void rename(org.bukkit.inventory.ItemStack Itemstack, String Owner){
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);
        meta.setOwner(Owner);
        Itemstack.setItemMeta(meta);
    }
}



