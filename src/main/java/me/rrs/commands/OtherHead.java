package me.rrs.commands;

import me.rrs.util.Lang;
import me.rrs.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class OtherHead implements CommandExecutor {

    Lang lang = new Lang();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            ItemStack skull = SkullCreator.itemFromName(args[0]);

            if (player.hasPermission("headdrop.head")) {
                if (args.length > 0) {
                    player.getInventory().addItem(skull);
                    lang.msg("&a&l[HeadDrop]&r", "Head-Success", "%player%", args[0], player);
                } else lang.msg("&c&l[HeadDrop]&r", "Head_Error", player);
            }else lang.noPerm(player);
        }else lang.pcmd();



        return true;
    }
}
