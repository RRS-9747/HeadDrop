package me.rrs.headdrop.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.headdrop.HeadDrop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Lang {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public void msg(String prefix, String path, Player player) {
        String message = HeadDrop.getInstance().getLang().getString(path);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        // Use MiniMessage to parse color codes
        Component component = miniMessage.deserialize(prefix + " " + message);
        player.sendMessage(component);
    }

    public void msg(String prefix, String path, String placeholder, String obj, Player player) {
        String message = HeadDrop.getInstance().getLang().getString(path).replace(placeholder, obj);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        // Use MiniMessage to parse color codes
        Component component = miniMessage.deserialize(prefix + " " + message);
        player.sendMessage(component);
    }

    public void noPerm(Player player) {
        msg("&c&l[HeadDrop]&r", "Permission-Error", player);
    }

    public void pcmd() {
        Bukkit.getLogger().severe(miniMessage.deserialize(HeadDrop.getInstance().getLang().getString("Player-Command")).toString());
    }
}
