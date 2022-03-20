package me.rrs.Listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.HeadDrop;
import me.rrs.Util.Embed;
import me.rrs.Util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;


public class PlayerDeath implements Listener {

    final YamlDocument config = HeadDrop.getConfiguration();
    Random random = new Random();
    String description;
    String title;
    String footer;


    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerDropHeadEvent(PlayerDeathEvent event) {

        boolean needPermission = this.config.getBoolean("PLAYER.Require-Permission");
        boolean isInDisabledWorld = false;

        int x = random.nextInt(100) + 1;

        String rawTitle = config.getString("Bot.Title");
        String rawDescription = config.getString("Bot.Description");
        String rawFooter = config.getString("Bot.Footer");




        List<String> worldList = config.getStringList("Config.Disable-Worlds");


        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (event.getEntity().getWorld().equals(w)) {
                isInDisabledWorld = true;
            }
        }

        if (!isInDisabledWorld) {

            if (event.getEntity().getKiller() == null) return;

            String rawTitle1 = rawTitle.replaceAll("%killer%", event.getEntity().getKiller().getName());
            title = rawTitle1.replaceAll("%mob%", event.getEntity().getName());

            String rawDescription1 = rawDescription.replaceAll("%killer%", event.getEntity().getKiller().getName());
            description = rawDescription1.replaceAll("%mob%", event.getEntity().getName());

            String rawFooter1 = rawFooter.replaceAll("%killer%", event.getEntity().getKiller().getName());
            footer = rawFooter1.replaceAll("%mob%", event.getEntity().getName());




            if (needPermission) {
                if (event.getEntity().hasPermission("headdrop.player")) {
                    if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                        ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                        ItemMeta rawSkull = skull.getItemMeta();
                        event.getDrops().add(skull);
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }
                }
            } else {
                if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                    ItemStack skull = SkullCreator.itemFromName(event.getEntity().getDisplayName());
                    event.getDrops().add(skull);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            }
        }
    }

}
