package me.rrs.util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Lang {


    public void msg(String prefix, String path, Player player){
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', prefix + " " + HeadDrop.getLang().getString(path))));
        }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + " " + HeadDrop.getLang().getString(path)));
    }

    public void msg(String prefix, String path, String placeholder, String obj, Player player){
        String s = HeadDrop.getLang().getString(path).replaceAll(placeholder, obj);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', prefix + " " + s)));
        }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + s));
    }


    public void noPerm(Player player){
        msg("&c&l[HeadDrop]&r", "Permission-Error", player);
    }

    public void pcmd(){
        Bukkit.getLogger().severe(ChatColor.translateAlternateColorCodes('&', HeadDrop.getLang().getString("Player-Command")));

    }
}

