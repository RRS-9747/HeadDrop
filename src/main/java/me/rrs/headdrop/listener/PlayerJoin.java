package me.rrs.headdrop.listener;

import me.rrs.headdrop.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDate;
import java.time.Month;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.getMonth() == Month.DECEMBER && currentDate.getDayOfMonth() == 12
                && event.getPlayer().hasPermission("headdrop.notify")) {
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r Today is my Birthday :D Leave a review on spigot as a gift :3"));
        }

    }

}
