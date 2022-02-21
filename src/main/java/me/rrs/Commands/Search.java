package me.rrs.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.Database.Database;
import me.rrs.HeadDrop;
import me.rrs.Util.SkullRetriever;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;


public class Search implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            String Head_Search_Error = HeadDrop.getInstance().getConfig().getString("Lang.Head-Search-Error");
            String Permission_Error = HeadDrop.getInstance().getConfig().getString("Lang.Permission-Error");


            if (player.hasPermission("head.search")) {
                if (args.length > 0) {

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SkullRetriever retriever = new SkullRetriever();
                            retriever.setDatabase(Database.MINECRAFT_HEADS);


                            String query = args[0];
                            player.sendMessage("Searching for " + args[0]);
                            String texture = retriever.getMostRelevantSkull(query);
                            ItemStack skull = retriever.getCustomSkull(texture, args[0]);
                            player.getInventory().addItem(skull);
                        }
                    }.runTaskAsynchronously(HeadDrop.getInstance());


                } else
                    if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Head_Search_Error)));

                    }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Head_Search_Error));


            } else
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error)));

                }else player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r " + Permission_Error));



        }else Bukkit.getLogger().log(Level.SEVERE, "This is a player only command!");

            return true;

        }

}
