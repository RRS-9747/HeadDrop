package me.rrs.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){

            Player player = (Player) sender;
            String Reload = HeadDrop.getInstance().getConfig().getString("Lang.Reload");
            String Permission_Error = HeadDrop.getInstance().getConfig().getString("Lang.Permission-Error");

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

                }else if(args[0].equalsIgnoreCase("reload")){
                    if (player.hasPermission("head.reload")) {
                        HeadDrop.getInstance().reloadConfig();
                        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                            player.sendMessage(PlaceholderAPI.setPlaceholders(player,ChatColor.translateAlternateColorCodes('&' ,"&a&l[HeadDrop]&r" + Reload )));
                        }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&l[HeadDrop]&r" + Reload ));


                    }else{
                        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                            player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error)));
                        }else player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&l[HeadDrop]&r " + Permission_Error));

                    }
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS"));
            }
        }else{
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Bukkit.getLogger().warning("HeadDrop reloaded");
                }
            } else {
                Bukkit.getLogger().warning("&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS");
            }

        }


        return true;
    }
}
