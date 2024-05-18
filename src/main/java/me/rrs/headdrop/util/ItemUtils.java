package me.rrs.headdrop.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    public void addLore(ItemStack head, List<String> rawLore, Player killer) {
        if (rawLore == null || rawLore.isEmpty()) return;

        ItemMeta itemMeta = head.getItemMeta();
        List<String> finalLore = new ArrayList<>();

        rawLore.forEach(lore -> {
            if (!lore.equalsIgnoreCase("")) {
                lore = lore
                        .replace("{KILLER}", killer != null ? killer.getName() : "Unknown")
                        .replace("{DATE}", LocalDate.now().toString());
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    lore = killer != null ? PlaceholderAPI.setPlaceholders(killer, lore) : PlaceholderAPI.setPlaceholders(null, lore);
                }
                finalLore.add(ChatColor.translateAlternateColorCodes('&', lore));
            }
        });

        itemMeta.setLore(finalLore);
        head.setItemMeta(itemMeta);
    }

}
