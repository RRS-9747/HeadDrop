package me.rrs.headdrop.commands;

import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.listener.GUI;
import me.rrs.headdrop.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final Lang lang = new Lang();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "HeadDrop by RRS"));
        } else {
            switch (args[0].toLowerCase()) {
                case "help":
                    sendHelpMessage(sender);
                    break;
                case "reload":
                    reloadConfigAndLang(sender);
                    break;
                case "leaderboard":
                    showLeaderboard(sender);
                    break;
                case "debug":
                    generateDebugFile(sender);
                    break;
                case "gui":
                    openGUI(sender);
                    break;
            }
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(
                    ChatColor.DARK_GREEN + "HeadDrop" + ChatColor.RESET + " plugin by RRS.",
                    ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/headdrop help" + ChatColor.RESET + " -> you already discovered it!",
                    ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/headdrop reload" + ChatColor.RESET + " -> reload plugin config.",
                    ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/myhead" + ChatColor.RESET + " -> Get your head.",
                    ChatColor.AQUA + "> " + ChatColor.LIGHT_PURPLE + "/head <player Name>" + ChatColor.RESET + " -> Get another player head"
            );
        }
    }

    private void reloadConfigAndLang(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("headdrop.reload")) {
                try {
                    HeadDrop.getLang().reload();
                    HeadDrop.getConfiguration().reload();
                    lang.msg(ChatColor.GREEN + "[HeadDrop] " + ChatColor.RESET, "Reload", player);
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
    }

    private void showLeaderboard(CommandSender sender) {
        if (!HeadDrop.getConfiguration().getBoolean("Database.Enable")){
            Bukkit.getLogger().severe("[HeadDrop] Enable database on config!");
            if (sender instanceof Player) sender.sendMessage("[HeadDrop] Check console log!");
            return;
        }
        Map<String, Integer> playerData = HeadDrop.getDatabase().getPlayerData();
        List<Map.Entry<String, Integer>> sortedData = new ArrayList<>(playerData.entrySet());
        sortedData.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        sender.sendMessage(ChatColor.GOLD + "---- Top HeadHunter ----");
        for (int i = 0; i < Math.min(sortedData.size(), 10); i++) {
            Map.Entry<String, Integer> entry = sortedData.get(i);
            sender.sendMessage(ChatColor.YELLOW.toString() + (i + 1) + ". " + entry.getKey() + " - " + entry.getValue() + " Head(s)");
        }
    }

    private void generateDebugFile(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            try {
                File debugFile = new File(HeadDrop.getInstance().getDataFolder().getAbsolutePath() + File.separator + "debug.txt");
                if (debugFile.exists()) {
                    debugFile.delete();
                }
                debugFile.createNewFile();

                try (FileWriter writer = new FileWriter(debugFile)) {
                    writer.write("Server Name: " + Bukkit.getServer().getName() + "\n");
                    writer.write("Server Version: " + Bukkit.getServer().getVersion() + "\n");
                    writer.write("Plugin Version: " + HeadDrop.getInstance().getDescription().getVersion() + "\n");
                    writer.write("Java Version: " + System.getProperty("java.version") + "\n");
                    writer.write("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n");
                    writer.write("\n");
                    writer.write("Require-Killer-Player: " + HeadDrop.getConfiguration().getBoolean("Config.Require-Killer-Player") + "\n");
                    writer.write("Killer-Require-Permission: " + HeadDrop.getConfiguration().getBoolean("Config.Killer-Require-Permission") + "\n");
                    writer.write("Enable-Looting: " + HeadDrop.getConfiguration().getBoolean("Config.Enable-Looting") + "\n");
                    writer.write("Enable-Perm-Chance: " + HeadDrop.getConfiguration().getBoolean("Config.Enable-Perm-Chance") + "\n");
                    writer.write("Database: " + HeadDrop.getConfiguration().getBoolean("Database.Online") + "\n");
                    writer.write("Premium: " + "True" + "\n");
                }
                Bukkit.getLogger().info("[HeadDrop-Debug] debug.txt file created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openGUI(CommandSender sender) {
        if (sender instanceof Player) {
            GUI gui = new GUI();
            Player player = (Player) sender;
            player.openInventory(gui.getInventory());
        } else {
            lang.pcmd();
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        if (cmd.getName().equals("headdrop") && args.length ==1){
            return Arrays.asList("help", "reload", "leaderboard", "gui");
        }
        return Collections.emptyList();
    }
}