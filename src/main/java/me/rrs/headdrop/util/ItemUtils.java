package me.rrs.headdrop.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public ItemStack rename(ItemStack head, String name, List<String> rawLore) {
        NBTItem nbtI = new NBTItem(head);
        NBTCompound skull = nbtI.addCompound("SkullOwner");
        skull.setString("Name", name);
        ItemMeta meta = nbtI.getItem().getItemMeta();
        if (rawLore != null || !rawLore.isEmpty()) {
            List<String> finalLore = new ArrayList<>();
            for (String lore : rawLore) {
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    finalLore.add(PlaceholderAPI.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', lore)));
                }else {
                    finalLore.add(lore);
                }
            }
            meta.setLore(finalLore);
        }
        head.setItemMeta(meta);

        return head;
    }

    public ItemStack rename(ItemStack head, List<String> rawLore) {
        ItemMeta meta = new NBTItem(head).getItem().getItemMeta();
        if (rawLore != null || !rawLore.isEmpty()) {
            List<String> finalLore = new ArrayList<>();
            for (String lore : rawLore) {
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    finalLore.add(PlaceholderAPI.setPlaceholders(null, ChatColor.translateAlternateColorCodes('&', lore)));
                }else {
                    finalLore.add(lore);
                }
            }
            meta.setLore(finalLore);
        }
        head.setItemMeta(meta);
        return head;
    }


}
