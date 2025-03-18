package me.rrs.headdrop.commands;


import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.listener.HeadGUI;
import me.rrs.headdrop.util.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final Lang lang = new Lang();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(miniMessage.deserialize("<gold>HeadDrop by RRS</gold>"));
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
        if (sender instanceof Player player) {
            Component message = miniMessage.deserialize("""
            <dark_green>HeadDrop</dark_green> <reset>plugin by RRS.
            
            <aqua>> <light_purple>/headdrop help</light_purple> <reset>-> you already discovered it!
            
            <aqua>> <light_purple>/headdrop reload</light_purple> <reset>-> reload plugin config.
            
            <aqua>> <light_purple>/myhead</light_purple> <reset>-> Get your head.
            
            <aqua>> <light_purple>/head &lt;player Name&gt;</light_purple> <reset>-> Get another player head.
            """);

            player.sendMessage(message);
        }
    }

    private void reloadConfigAndLang(CommandSender sender) {
        if (sender instanceof Player player) {
            if (player.hasPermission("headdrop.reload")) {
                try {
                    HeadDrop.getInstance().getLang().reload();
                    HeadDrop.getInstance().getConfiguration().reload();
                    Component message = miniMessage.deserialize("<green>[HeadDrop]</green> <reset>Reloaded");
                    sender.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                lang.noPerm(player);
            }
        } else {
            try {
                HeadDrop.getInstance().getConfiguration().reload();
                HeadDrop.getInstance().getLang().reload();
                Bukkit.getLogger().info(HeadDrop.getInstance().getLang().getString("Reload"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showLeaderboard(CommandSender sender) {
        if (!HeadDrop.getInstance().getConfiguration().getBoolean("Database.Enable")){
            Bukkit.getLogger().severe("[HeadDrop] Enable database on config!");
            if (sender instanceof Player) sender.sendMessage("[HeadDrop] Check console log!");
            return;
        }
        Map<String, Integer> playerData = HeadDrop.getInstance().getDatabase().getPlayerData();
        List<Map.Entry<String, Integer>> sortedData = new ArrayList<>(playerData.entrySet());
        sortedData.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        sender.sendMessage(miniMessage.deserialize("<gold><bold>=-=-= Top Head Hunters =-=-=</bold></gold>"));
        sender.sendMessage(miniMessage.deserialize("<gray>----------------------------</gray>"));

        for (int i = 0; i < Math.min(sortedData.size(), 10); i++) {
            Map.Entry<String, Integer> entry = sortedData.get(i);
            Component message = miniMessage.deserialize("""
             <aqua>#%d</aqua> <yellow>%s</yellow> - <green>%d</green> <gold>Head(s)</gold>
            """.formatted(i + 1, entry.getKey(), entry.getValue()));
            sender.sendMessage(message);
        }

        sender.sendMessage(miniMessage.deserialize("<gray>----------------------------</gray>"));

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
                    writer.write("Require-Killer-Player: " + HeadDrop.getInstance().getConfiguration().getBoolean("Config.Require-Killer-Player") + "\n");
                    writer.write("Killer-Require-Permission: " + HeadDrop.getInstance().getConfiguration().getBoolean("Config.Killer-Require-Permission") + "\n");
                    writer.write("Enable-Looting: " + HeadDrop.getInstance().getConfiguration().getBoolean("Config.Enable-Looting") + "\n");
                    writer.write("Enable-Perm-Chance: " + HeadDrop.getInstance().getConfiguration().getBoolean("Config.Enable-Perm-Chance") + "\n");
                    writer.write("Database: " + HeadDrop.getInstance().getConfiguration().getBoolean("Database.Online") + "\n");
                    writer.write("Premium: " + "True" + "\n");
                }
                Bukkit.getLogger().info("[HeadDrop-Debug] debug.txt file created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openGUI(CommandSender sender) {
        if (sender instanceof Player player) {
            HeadGUI gui = new HeadGUI();
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