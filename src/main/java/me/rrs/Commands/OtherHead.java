package me.rrs.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.Util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class OtherHead implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            ItemStack skull = SkullCreator.itemFromName(args[0]);
            String Head_Success = HeadDrop.getInstance().getConfig().getString("Lang.Head-Success");
            String Head_Error = HeadDrop.getInstance().getConfig().getString("Lang.Head-Error");
            String Permission_Error = HeadDrop.getInstance().getConfig().getString("Lang.Permission-Error");

            if (player.hasPermission("headdrop.head")) {


                if (args.length > 0) {
                    player.getInventory().addItem(skull);
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r " + Head_Success).replaceAll("%player%", args[0])));

                    }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r " + Head_Success).replaceAll("%player%", args[0]));


                } else {
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Head_Error)));

                    }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Head_Error));

                }
            }else{
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error)));

                }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error));

            }




        }else{
            Bukkit.getLogger().log(Level.SEVERE, "This is player only command!");
        }

        return true;
    }
}
