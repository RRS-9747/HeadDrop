package me.rrs.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class CustomHead implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            String MyHead_Success = HeadDrop.getInstance().getConfig().getString("Lang.MyHead-Success");
            String Permission_Error = HeadDrop.getInstance().getConfig().getString("Lang.Permission-Error");

            if (args.length > 0){
                if (player.hasPermission("headdrop.customhead")) {
                    ItemStack skull = SkullCreator.itemFromBase64(args[0]);
                    player.getInventory().addItem(skull);

                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r Head Addeed on your Inventory ")));


                }else{
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error)));
                    }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error));
                }


            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r You need to give a base64 code"));


        }else Bukkit.getLogger().log(Level.SEVERE, "This is player only command!");


        return true;
    }
}
