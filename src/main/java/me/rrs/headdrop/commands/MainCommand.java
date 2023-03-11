package me.rrs.headdrop.commands;

import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    Lang lang = new Lang();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "HeadDrop by RRS"));

        }else {
            switch (args[0].toLowerCase()) {
                case "help":
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.DARK_GREEN + "HeadDrop" + ChatColor.RESET + " plugin by RRS.");
                        player.sendMessage(ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/headdrop help" + ChatColor.RESET + " -> you already discovered it!");
                        player.sendMessage(ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/headdrop reload" + ChatColor.RESET + " -> reload plugin config.");
                        player.sendMessage(ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/myhead" + ChatColor.RESET + " -> Get your head.");
                        player.sendMessage(ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/head <player Name>" + ChatColor.RESET + " -> Get another player head");
                    }
                    break;
                case "reload":
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (player.hasPermission("headdrop.reload")) {
                            try {
                                HeadDrop.getLang().reload();
                                HeadDrop.getConfiguration().reload();
                                lang.msg(ChatColor.GREEN + "HeadDrop" + ChatColor.RESET, "Reload", player);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            lang.noPerm(player);
                        }
                    } else {
                        try {
                            HeadDrop.getConfiguration().reload();
                            HeadDrop.getLang().reload();
                            Bukkit.getLogger().info(HeadDrop.getLang().getString("Reload"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "leaderboard":
                    List<Player> topPlayers = getTopPlayers();
                    sender.sendMessage(ChatColor.GOLD + "---- Top Online HeadHunter ----");
                    for (int i = 0; i < topPlayers.size(); i++) {
                        Player player = topPlayers.get(i);
                        PersistentDataContainer container = player.getPersistentDataContainer();
                        int headDropCount = container.get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER);
                        sender.sendMessage(ChatColor.YELLOW.toString() + (i + 1) + ". " + player.getName() + " - " + headDropCount + " Head(s)");
                    }

            }
        }
        return true;
    }

    public List<Player> getTopPlayers() {
        // Create a list to store the top players
        List<Player> topPlayers = new ArrayList<>();

        // Iterate through all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Retrieve the player's HeadDrop PDC count
            PersistentDataContainer container = player.getPersistentDataContainer();
            int headDropCount = container.get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER);

            // If the player's HeadDrop count is high enough, add them to the top players list
            if (topPlayers.size() < 10 || headDropCount > topPlayers.get(9).getPersistentDataContainer().get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER)) {
                topPlayers.add(player);
                topPlayers.sort((p1, p2) -> p2.getPersistentDataContainer().get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER) - p1.getPersistentDataContainer().get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER));
                if (topPlayers.size() > 10) {
                    topPlayers.remove(10);
                }
            }
        }

        return topPlayers;
    }
}
