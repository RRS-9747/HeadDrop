package me.rrs.headdrop.listener;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.headdrop.HeadDrop;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PoweredCreeper implements Listener {

    final YamlDocument config = HeadDrop.getConfiguration();

    boolean enableDrop = false;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChargedCreeperDeath(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            if (livingEntity.getHealth() - event.getDamage() <= 0.0D &&
                    event.getDamager() instanceof Creeper &&
                    ((Creeper) event.getDamager()).isPowered()) {
                enableDrop = true;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onHeadDrop(HeadDropEvent event){
        if (config.getBoolean("Config.Require-Charged-Creeper") && !config.getBoolean("Config.Require-Killer-Player")) {
            event.setCancelled(!enableDrop);
        }
        enableDrop = false;
    }
}
