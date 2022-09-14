package me.rrs.commands;

import me.rrs.util.Lang;
import me.rrs.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomHead implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length > 0){
                if (player.hasPermission("headdrop.customhead")) {
                    ItemStack skull = SkullCreator.itemFromBase64(args[0]);
                    player.getInventory().addItem(skull);
                    Lang.msg("&a&l[HeadDrop]&r", "Custom-Head-Success", player);


                }else Lang.noPerm(player);


            }else Lang.msg("&c&l[HeadDrop]&r", "Base64-Error", player);



        }else Lang.pcmd();


        return true;
    }
}
