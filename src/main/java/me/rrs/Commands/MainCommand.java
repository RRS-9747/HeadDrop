package me.rrs.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.Util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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

                }
                if(args[0].equalsIgnoreCase("reload")){
                    if (player.hasPermission("head.reload")) {
                        player.sendMessage("Can't reload HeadDrop. please restart your server!");


                    }else{
                            Lang.noPerm(player);

                    }
                }
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS"));
            }
        }else{
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")){
                    Bukkit.getLogger().severe("Can't reload HeadDrop. please restart your server!");
                    HeadDrop.getInstance().reloadConfig();

            }else Bukkit.getLogger().warning("&bHeadDrop "+ HeadDrop.getInstance().getDescription().getVersion()+ "&r by RRS");

        }


        return true;
    }
}
