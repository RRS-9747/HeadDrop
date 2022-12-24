package me.rrs.headdrop.commands;

import me.rrs.headdrop.util.Lang;
import me.rrs.headdrop.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MyHead implements CommandExecutor {

    Lang lang = new Lang();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player){

            Player player = (Player) sender;
            ItemStack skull = SkullCreator.itemFromName(player.getName());

            if (player.hasPermission("headdrop.ownhead")) {
                player.getInventory().addItem(skull);
                lang.msg("&a&l[HeadDrop]&r", "MyHead-Success", player);
            }else lang.noPerm(player);
        }else lang.pcmd();


        return true;
    }
}
