package me.rrs.headdrop.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
        List<Component> finalLore = new ArrayList<>();

        rawLore.forEach(lore -> {
            if (!lore.equalsIgnoreCase("")) {
                String processedLore = lore
                        .replace("{KILLER}", killer != null ? killer.getName() : "Unknown")
                        .replace("{DATE}", LocalDate.now().toString())
                        .replace("{WEAPON}", killer != null ? killer.getInventory().getItemInMainHand().getType().toString() : "Unknown");
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    processedLore = killer != null ? PlaceholderAPI.setPlaceholders(killer, processedLore) : PlaceholderAPI.setPlaceholders(null, processedLore);
                }
                finalLore.add(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacyAmpersand().deserialize(processedLore));
            }
        });

        itemMeta.lore(finalLore);
        head.setItemMeta(itemMeta);
    }

}