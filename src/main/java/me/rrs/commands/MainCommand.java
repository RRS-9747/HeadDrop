package me.rrs.commands;

import me.rrs.HeadDrop;
import me.rrs.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainCommand implements CommandExecutor {

    Lang lang = new Lang();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player){

            Player player = (Player) sender;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("help")) {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3HeadDrop&r plugin by RRS."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/headdrop help&r -> you already discovered it!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/headdrop reload&r -> reload plugin config."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/myhead&r -> Get your head."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/head <player Name>&r -> Get another player head"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&9>&r &l/search <Head Name>&r -> Search for a head in online."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/customhead <base64>&r -> Get head from base64."));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9>&r &l/updatehd&r -> Auto update the plugin."));

                }else if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("head.reload")) {
                        try {
                            HeadDrop.getLang().reload();
                            HeadDrop.getConfiguration().reload();
                            lang.msg("&a&l[HeadDrop]&r", "Reload", player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else lang.noPerm(player);
                }
            }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS"));
        }else{
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")){
                try {
                    HeadDrop.getConfiguration().reload();
                    HeadDrop.getLang().reload();
                    Bukkit.getLogger().info(HeadDrop.getLang().getString("Reload"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else Bukkit.getLogger().warning("&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS");
        }
        return true;
    }
}
