package me.rrs.Events;

import me.rrs.HeadDrop;
import me.rrs.util.SkullCreator;
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

    Player killerPlayer;
    FileConfiguration config;

    @EventHandler(priority = EventPriority.NORMAL)

    public void onPlayerDropHeadEvent(PlayerDeathEvent event) {

        Random random = new Random();
        int x = random.nextInt(101);

        this.killerPlayer = event.getEntity().getKiller();
        this.config = HeadDrop.getInstance().getConfig();
        boolean needPermission = this.config.getBoolean("PLAYER.Require-Permission");

        List<String> worldList = this.config.getStringList("Config.Disable-Worlds");

        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (event.getEntity().getWorld() != w) {



                    if (event.getEntity().getKiller() == killerPlayer) {
                        if (needPermission) {
                        if (event.getEntity().hasPermission("headdrop.player")) {
                            if (HeadDrop.getInstance().getConfig().getBoolean("PLAYER.Drop")) {
                                if (x <= HeadDrop.getInstance().getConfig().getInt("PLAYER.Chance")) {
                                    ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                                    event.getDrops().add(skull);
                                }
                            }
                        }
                    }else {
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

}
