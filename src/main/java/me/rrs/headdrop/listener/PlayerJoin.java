package me.rrs.headdrop.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        String currentDate = dateFormat.format(date);

        if (currentDate.equals("12/12") && event.getPlayer().hasPermission("HeadDrop.notify")){
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
        }
    }
}
