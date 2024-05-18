package me.rrs.headdrop.commands;

import me.rrs.headdrop.util.Lang;
import me.rrs.headdrop.util.SkullCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Head implements CommandExecutor {

    private final Lang lang = new Lang();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            ItemStack skull = SkullCreator.createSkullWithName((args.length > 0) ? args[0] : player.getName());
            if (player.hasPermission("headdrop.head")) {
                player.getInventory().addItem(skull);
                String message = (args.length > 0) ? "Head-Success" : "MyHead-Success";
                lang.msg("&a&l[HeadDrop]&r", message, (args.length > 0) ? "%player%" : "", (args.length > 0) ? args[0] : "", player);
            } else {
                lang.noPerm(player);
            }
        } else {
            lang.pcmd();
        }


        return true;
    }
}
