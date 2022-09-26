package me.rrs.listeners;

import me.rrs.HeadDrop;
import me.rrs.util.UpdateAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerJoin implements Listener {

    String newVersion;
    boolean hasUpdateGitHub, updateChecker;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {

        newVersion = UpdateAPI.getGithubVersion("RRS-9747", "HeadDrop");
        hasUpdateGitHub = UpdateAPI.hasGithubUpdate("RRS-9747", "HeadDrop");
        updateChecker = HeadDrop.getConfiguration().getBoolean("Config.Update-Checker");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String currentDate = dateFormat.format(date);


        if (event.getPlayer().hasPermission("headdrop.notify")) {
            if (hasUpdateGitHub && updateChecker) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You are using HeadDrop" + HeadDrop.getInstance().getDescription().getVersion()));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "However version " + newVersion + " is available."));
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "You can download it from: https://bit.ly/HeadDrop"));
            }
        }


        if (currentDate.equals("12/12")){
            if (event.getPlayer().hasPermission("HeadDrop.notify")){
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
            }
        }


    }



}
