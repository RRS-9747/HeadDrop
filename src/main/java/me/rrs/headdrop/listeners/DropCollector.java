package me.rrs.headdrop.listeners;

import me.rrs.headdrop.HeadDrop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DropCollector implements Listener {

    @EventHandler
    public void onHeadDrop(HeadDropEvent event) {
        if (HeadDrop.getConfiguration().getBoolean("Config.Auto-Pickup")){
            if (event.getKiller() != null){
                event.setCancelled(true);
                event.getKiller().getInventory().addItem(event.getHead());
            }
        }

    }
}
