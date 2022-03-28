package me.rrs.commands;

import me.rrs.util.Lang;
import me.rrs.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;



public class MyHead implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){

            Player player = (Player) sender;
            ItemStack skull = SkullCreator.itemFromName(player.getName());

            if (player.hasPermission("headdrop.ownhead")) {

                player.getInventory().addItem(skull);
                Lang.msg("&a&l[HeadDrop]&r", "MyHead-Success", player);


            }else
                Lang.noPerm(player);



        }else{
            Lang.pcmd();
        }



        return true;
    }
}
