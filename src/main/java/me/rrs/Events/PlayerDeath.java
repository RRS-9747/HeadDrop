package me.rrs.Events;

import me.rrs.HeadDrop;
import me.rrs.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public class PlayerDeath implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)

    public void onPlayerDeath(PlayerDeathEvent event) {

        Random random = new Random();

        int x = random.nextInt(101);
        Player killerPlayer = event.getEntity().getKiller();
        boolean needPermission = HeadDrop.getInstance().getConfig().getBoolean("Config.Player-Permission");

        List<String> worldList = HeadDrop.getInstance().getConfig().getStringList("Config.Disable-Worlds");

        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (event.getEntity().getWorld() != w) {


                if (needPermission) {
                    if (event.getEntity().getKiller() == killerPlayer) {
                        if (event.getEntity().hasPermission("headdrop.player")) {
                            if (HeadDrop.getInstance().getConfig().getBoolean("Drop.PLAYER")) {
                                if (x <= HeadDrop.getInstance().getConfig().getInt("Chance.PLAYER")) {
                                    ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                                    event.getDrops().add(skull);
                                }
                            }
                        }
                    }
                } else {
                    if (event.getEntity().getKiller() == killerPlayer) {
                        if (HeadDrop.getInstance().getConfig().getBoolean("Drop.PLAYER")) {
                            if (x <= HeadDrop.getInstance().getConfig().getInt("Chance.PLAYER")) {
                                ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                                event.getDrops().add(skull);
                            }
                        }
                    }
                }
            }
        }
    }

}
