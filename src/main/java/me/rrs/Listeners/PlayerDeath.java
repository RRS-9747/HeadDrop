package me.rrs.Listeners;

import me.rrs.HeadDrop;
import me.rrs.Util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public class PlayerDeath implements Listener {

    final FileConfiguration config = HeadDrop.getInstance().getConfig();
    Random random = new Random();

    @EventHandler(priority = EventPriority.NORMAL)

    public void PlayerDropHeadEvent(PlayerDeathEvent event) {

        boolean needPermission = this.config.getBoolean("PLAYER.Require-Permission");
        boolean isInDisabledWorld = false;

        int x = random.nextInt(100) + 1;


        List<String> worldList = config.getStringList("Config.Disable-Worlds");


        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (event.getEntity().getWorld().equals(w)) {
                isInDisabledWorld = true;
            }
        }

        if (!isInDisabledWorld) {

            if (event.getEntity().getKiller() != null) {
                if (needPermission) {
                    if (event.getEntity().hasPermission("headdrop.player")) {
                        if (HeadDrop.getInstance().getConfig().getBoolean("PLAYER.Drop")) {
                            if (x <= HeadDrop.getInstance().getConfig().getInt("PLAYER.Chance")) {
                                ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                                event.getDrops().add(skull);
                            }
                        }
                    }
                } else {
                    if (HeadDrop.getInstance().getConfig().getBoolean("PLAYER.Drop")) {
                        if (x <= HeadDrop.getInstance().getConfig().getInt("PLAYER.Chance")) {
                            ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                            event.getDrops().add(skull);
                        }
                    }
                }
            }
        }
    }

}
