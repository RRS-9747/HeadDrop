package me.rrs.Commands;

import me.rrs.HeadDrop;
import me.rrs.Util.Lang;
import me.rrs.Util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class OtherHead implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            ItemStack skull = SkullCreator.itemFromName(args[0]);

            if (player.hasPermission("headdrop.head")) {


                if (args.length > 0) {
                    player.getInventory().addItem(skull);
                    Lang.msg("&a&l[HeadDrop]&r", "Head-Success" , player, "%player%", args[0]);



                } else Lang.msg("&c&l[HeadDrop]&r", "Head_Error", player);

            }else Lang.noPerm(player);




        }else{
            Lang.pcmd();
        }

        return true;
    }
}
