package me.rrs.listeners;

import de.tr7zw.nbtapi.NBTItem;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.database.LivingEntityHead;
import me.rrs.util.Embed;
import me.rrs.util.ItemUtils;
import me.rrs.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public class EntityDeath implements Listener {

    ItemStack item;
    NBTItem nbtItem;
    final YamlDocument config = HeadDrop.getConfiguration();
    String title, description, footer;

    Random random = new Random();
    LivingEntityHead entityHead = new LivingEntityHead();


    @EventHandler(priority = EventPriority.NORMAL)
    public void EntityDropHeadEvent(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")){
            BukkitAPIHelper mythicMobsAPI = new BukkitAPIHelper();
            if (mythicMobsAPI.isMythicMob(entity)) return;
        }

        if (config.getBoolean("Config.Require-Killer-Player")){
            if (event.getEntity().getKiller() == null) return;
        }

        if (config.getBoolean("Config.Killer-Require-Permission")) {
            if (!event.getEntity().getKiller().hasPermission("headdrop.killer")) return;
        }

        boolean isInDisabledWorld = false;
        int x = random.nextInt(100) + 1;

        List<String> worldList = HeadDrop.getConfiguration().getStringList("Config.Disable-Worlds");

        title = config.getString("Bot.Title")
                .replaceAll("%killer%", entity.getKiller().getName())
                .replaceAll("%mob%", entity.getName());


        description = config.getString("Bot.Description")
                .replaceAll("%killer%", entity.getKiller().getName())
                .replaceAll("%mob%", entity.getName());

        footer = config.getString("Bot.Footer")
                .replaceAll("%killer%", entity.getKiller().getName())
                .replaceAll("%mob%", entity.getName());


        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            title = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), title);
            description = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), description);
            footer = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), footer);
        }


        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (entity.getWorld().equals(w)) {
                isInDisabledWorld = true;
            }
        }

        if (!isInDisabledWorld) {
            EntityType type = entity.getType();


            if (type == EntityType.PLAYER) {
                if (config.getBoolean("PLAYER.Require-Permission")) {
                    if (!event.getEntity().hasPermission("headdrop.player")) return;
                }

                if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                    ItemStack skull = SkullCreator.itemFromName(event.getEntity().getName());
                    event.getDrops().add(skull);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.BAT) {
                if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance")) {

                    item = ItemUtils.rename(entityHead.BAT, ChatColor.YELLOW + config.getString("BAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.BLAZE) {
                if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance")) {

                    item = ItemUtils.rename(entityHead.BLAZE, ChatColor.YELLOW + config.getString("BLAZE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SPIDER) {
                if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance")) {

                    item = ItemUtils.rename(entityHead.SPIDER, ChatColor.YELLOW + config.getString("SPIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CAVE_SPIDER) {
                if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance")) {

                    item = ItemUtils.rename(entityHead.CAVE_SPIDER, ChatColor.YELLOW + config.getString("CAVE_SPIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CHICKEN) {
                if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance")) {

                    item = ItemUtils.rename(entityHead.CHICKEN, ChatColor.YELLOW + config.getString("CHICKEN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.COW) {
                if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance")) {

                    item = ItemUtils.rename(entityHead.COW, ChatColor.YELLOW + config.getString("COW.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ENDERMAN) {
                if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance")) {

                    item = ItemUtils.rename(entityHead.ENDERMAN, ChatColor.YELLOW + config.getString("ENDERMAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GIANT) {
                if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance")) {

                    item = ItemUtils.rename(entityHead.GIANT, ChatColor.YELLOW + config.getString("GIANT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HORSE) {
                if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance")) {
                    Horse horse = (Horse) entity;

                    switch (horse.getColor()) {
                        case WHITE:
                            item = ItemUtils.rename(entityHead.HORSE_WHITE, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(entityHead.HORSE_CREAMY, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CHESTNUT:
                            item = ItemUtils.rename(entityHead.HORSE_CHESTNUT, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BROWN:
                            item = ItemUtils.rename(entityHead.HORSE_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLACK:
                            item = ItemUtils.rename(entityHead.HORSE_BLACK, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.HORSE_GRAY, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case DARK_BROWN:
                            item = ItemUtils.rename(entityHead.HORSE_DARK_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + horse.getColor());
                            event.getDrops().add(nbtItem.getItem());


                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ILLUSIONER) {
                if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance")) {
                    item = ItemUtils.rename(entityHead.ILLUSIONER, ChatColor.YELLOW + config.getString("ILLUSIONER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.IRON_GOLEM) {
                if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance")) {
                    item = ItemUtils.rename(entityHead.IRON_GOLEM, ChatColor.YELLOW + config.getString("IRON_GOLEM.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MAGMA_CUBE) {
                if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance")) {
                    item = ItemUtils.rename(entityHead.MAGMA_CUBE, ChatColor.YELLOW + config.getString("MAGMA_CUBE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MUSHROOM_COW) {
                if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance")) {
                    MushroomCow mushroomCow = (MushroomCow) entity;

                    switch (mushroomCow.getVariant()) {
                        case RED:
                            item = ItemUtils.rename(entityHead.MUSHROOM_COW_RED, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + mushroomCow.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BROWN:
                            item = ItemUtils.rename(entityHead.MUSHROOM_COW_BROWN, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + mushroomCow.getVariant());
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.OCELOT) {
                if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance")) {
                    item = ItemUtils.rename(entityHead.OCELOT, ChatColor.YELLOW + config.getString("OCELOT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIG) {
                if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance")) {
                    item = ItemUtils.rename(entityHead.PIG, ChatColor.YELLOW + config.getString("PIG.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SHEEP) {
                if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance")) {
                    Sheep sheep = (Sheep) entity;

                    switch (sheep.getColor()) {

                        case WHITE:
                            item = ItemUtils.rename(entityHead.SHEEP_WHITE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());
                            break;

                        case ORANGE:
                            item = ItemUtils.rename(entityHead.SHEEP_ORANGE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case MAGENTA:
                            item = ItemUtils.rename(entityHead.SHEEP_MAGENTA, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIGHT_BLUE:
                            item = ItemUtils.rename(entityHead.SHEEP_LIGHT_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case YELLOW:
                            item = ItemUtils.rename(entityHead.SHEEP_YELLOW, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIME:
                            item = ItemUtils.rename(entityHead.SHEEP_LIME, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case PINK:
                            item = ItemUtils.rename(entityHead.SHEEP_PINK, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.SHEEP_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIGHT_GRAY:
                            item = ItemUtils.rename(entityHead.SHEEP_LIGHT_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CYAN:
                            item = ItemUtils.rename(entityHead.SHEEP_CYAN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case PURPLE:
                            item = ItemUtils.rename(entityHead.SHEEP_PURPLE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLUE:
                            item = ItemUtils.rename(entityHead.SHEEP_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BROWN:
                            item = ItemUtils.rename(entityHead.SHEEP_BROWN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GREEN:
                            item = ItemUtils.rename(entityHead.SHEEP_GREEN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case RED:
                            item = ItemUtils.rename(entityHead.SHEEP_RED, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLACK:
                            item = ItemUtils.rename(entityHead.SHEEP_BLACK, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + sheep.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SILVERFISH) {
                if (config.getBoolean("SILVERFISH.Drop") && x <= config.getInt("SILVERFISH.Chance")) {
                    item = ItemUtils.rename(entityHead.SILVERFISH, ChatColor.YELLOW + config.getString("SILVERFISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SLIME) {
                if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance")) {
                    item = ItemUtils.rename(entityHead.SLIME, ChatColor.YELLOW + config.getString("SLIME.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SNOWMAN) {
                if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance")) {
                    item = ItemUtils.rename(entityHead.SNOWMAN, ChatColor.YELLOW + config.getString("SNOW_GOLEM.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SQUID) {
                if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance")) {
                    item = ItemUtils.rename(entityHead.SQUID, ChatColor.YELLOW + config.getString("SQUID.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WITCH) {
                if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance")) {
                    item = ItemUtils.rename(entityHead.WITCH, ChatColor.YELLOW + config.getString("WITCH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WITHER) {
                if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance")) {
                    item = ItemUtils.rename(entityHead.WITHER, ChatColor.YELLOW + config.getString("WITHER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
                if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance")) {
                    item = ItemUtils.rename(entityHead.ZOMBIFIED_PIGLIN, ChatColor.YELLOW + config.getString("ZOMBIFIED_PIGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GHAST) {
                if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance")) {
                    item = ItemUtils.rename(entityHead.GHAST, ChatColor.YELLOW + config.getString("GHAST.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WOLF) {
                if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance")) {
                    Wolf wolf = (Wolf) entity;

                    if (wolf.isAngry()) {
                        item = ItemUtils.rename(entityHead.WOLF_ANGRY, ChatColor.YELLOW + config.getString("WOLF.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "WOLF_ANGER");
                        event.getDrops().add(nbtItem.getItem());
                    } else {
                        item = ItemUtils.rename(entityHead.WOLF, ChatColor.YELLOW + config.getString("WOLF.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "WOLF");
                        event.getDrops().add(nbtItem.getItem());
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VILLAGER) {
                if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance")) {
                    Villager villager = (Villager) entity;

                    switch (villager.getProfession()) {
                        case WEAPONSMITH:
                            item = ItemUtils.rename(entityHead.VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case SHEPHERD:
                            item = ItemUtils.rename(entityHead.VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIBRARIAN:
                            item = ItemUtils.rename(entityHead.VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FLETCHER:
                            item = ItemUtils.rename(entityHead.VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FISHERMAN:
                            item = ItemUtils.rename(entityHead.VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FARMER:
                            item = ItemUtils.rename(entityHead.VILLAGER_FARMER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CLERIC:
                            item = ItemUtils.rename(entityHead.VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CARTOGRAPHER:
                            item = ItemUtils.rename(entityHead.VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BUTCHER:
                            item = ItemUtils.rename(entityHead.VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case ARMORER:
                            item = ItemUtils.rename(entityHead.VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + villager.getProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        default:
                            item = ItemUtils.rename(entityHead.VILLAGER_NULL, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", "VILLAGER_DEFAULT");
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.8 Mob
            } else if (type == EntityType.RABBIT) {
                if (config.getBoolean("RABBIT.Drop") && x <= config.getInt("RABBIT.Chance")) {
                    Rabbit rabbit = (Rabbit) entity;
                    switch (rabbit.getRabbitType()) {

                        case BROWN:
                            item = ItemUtils.rename(entityHead.RABBIT_BROWN, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case WHITE:
                            item = ItemUtils.rename(entityHead.RABBIT_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLACK:
                            item = ItemUtils.rename(entityHead.RABBIT_BLACK, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLACK_AND_WHITE:
                            item = ItemUtils.rename(entityHead.RABBIT_BLACK_AND_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GOLD:
                            item = ItemUtils.rename(entityHead.RABBIT_GOLD, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case SALT_AND_PEPPER:
                            item = ItemUtils.rename(entityHead.RABBIT_SALT_AND_PEPPER, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case THE_KILLER_BUNNY:
                            item = ItemUtils.rename(entityHead.RABBIT_THE_KILLER_BUNNY, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + rabbit.getRabbitType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ENDERMITE) {
                if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance")) {

                    item = ItemUtils.rename(entityHead.ENDERMITE, ChatColor.YELLOW + config.getString("ENDERMITE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GUARDIAN) {
                if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance")) {

                    item = ItemUtils.rename(entityHead.GUARDIAN, ChatColor.YELLOW + config.getString("GUARDIAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.9 Mob
            } else if (type == EntityType.SHULKER) {
                if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance")) {
                    item = ItemUtils.rename(entityHead.SHULKER, ChatColor.YELLOW + config.getString("SHULKER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.10 Mob
            } else if (type == EntityType.POLAR_BEAR) {
                if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance")) {
                    item = ItemUtils.rename(entityHead.POLAR_BEAR, ChatColor.YELLOW + config.getString("POLAR_BEAR.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.11 Mob
            } else if (type == EntityType.ZOMBIE_VILLAGER) {
                if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance")) {
                    ZombieVillager zombieVillager = (ZombieVillager) entity;

                    switch (zombieVillager.getVillagerProfession()) {
                        case ARMORER:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BUTCHER:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CARTOGRAPHER:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CLERIC:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FARMER:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FARMER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FISHERMAN:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case FLETCHER:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIBRARIAN:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case SHEPHERD:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case WEAPONSMITH:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + zombieVillager.getVillagerProfession());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        default:
                            item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_NULL, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", "ZOMBIE_VILLAGER_DEFAULT");
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VINDICATOR) {
                if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance")) {
                    item = ItemUtils.rename(entityHead.VINDICATOR, ChatColor.YELLOW + config.getString("VINDICATOR.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VEX) {
                if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance")) {

                    Vex vex = (Vex) entity;
                    if (vex.isCharging()) {
                        item = ItemUtils.rename(entityHead.VEX_CHARGE, ChatColor.YELLOW + config.getString("VEX.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "VEX_ANGER");
                        event.getDrops().add(nbtItem.getItem());
                    } else {
                        item = ItemUtils.rename(entityHead.VEX, ChatColor.YELLOW + config.getString("VEX.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "VEX");
                        event.getDrops().add(nbtItem.getItem());
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.EVOKER) {
                if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance")) {

                    item = ItemUtils.rename(entityHead.EVOKER, ChatColor.YELLOW + config.getString("EVOKER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HUSK) {
                if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance")) {
                    item = ItemUtils.rename(entityHead.HUSK, ChatColor.YELLOW + config.getString("HUSK.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.STRAY) {
                if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance")) {
                    item = ItemUtils.rename(entityHead.STRAY, ChatColor.YELLOW + config.getString("STRAY.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ELDER_GUARDIAN) {
                if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance")) {

                    item = ItemUtils.rename(entityHead.ELDER_GUARDIAN, ChatColor.YELLOW + config.getString("ELDER_GUARDIAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DONKEY) {
                if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance")) {

                    item = ItemUtils.rename(entityHead.DONKEY, ChatColor.YELLOW + config.getString("DONKEY.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ZOMBIE_HORSE) {
                if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance")) {
                    item = ItemUtils.rename(entityHead.ZOMBIE_HORSE, ChatColor.YELLOW + config.getString("ZOMBIE_HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SKELETON_HORSE) {
                if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance")) {
                    item = ItemUtils.rename(entityHead.SKELETON_HORSE, ChatColor.YELLOW + config.getString("SKELETON_HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MULE) {
                if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance")) {
                    item = ItemUtils.rename(entityHead.MULE, ChatColor.YELLOW + config.getString("MULE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.12 Mob
            } else if (type == EntityType.PARROT) {
                if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance")) {
                    Parrot parrot = (Parrot) entity;

                    switch (parrot.getVariant()) {
                        case BLUE:
                            item = ItemUtils.rename(entityHead.PARROT_BLUE, ChatColor.YELLOW + config.getString("PARROT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + parrot.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CYAN:
                            item = ItemUtils.rename(entityHead.PARROT_CYAN, ChatColor.YELLOW + config.getString("PARROT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + parrot.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.PARROT_GRAY, ChatColor.YELLOW + config.getString("PARROT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + parrot.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case RED:
                            item = ItemUtils.rename(entityHead.PARROT_RED, ChatColor.YELLOW + config.getString("PARROT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + parrot.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GREEN:
                            item = ItemUtils.rename(entityHead.PARROT_GREEN, ChatColor.YELLOW + config.getString("PARROT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + parrot.getVariant());
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.13 Mob
            } else if (type == EntityType.TROPICAL_FISH) {
                if (config.getBoolean("TROPICAL_FISH.Drop") && x <= config.getInt("TROPICAL_FISH.Chance")) {

                    TropicalFish tropicalFish = (TropicalFish) entity;

                    switch (tropicalFish.getBodyColor()) {
                        case MAGENTA:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_MAGENTA, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIGHT_BLUE:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_LIGHT_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case YELLOW:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_YELLOW, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case PINK:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_PINK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case LIGHT_GRAY:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_LIGHT_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CYAN:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_CYAN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLUE:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GREEN:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_GREEN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case RED:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_RED, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLACK:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_BLACK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + tropicalFish.getBodyColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;

                        default:
                            item = ItemUtils.rename(entityHead.TROPICAL_FISH_ORANGE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", "TROPICAL_FISH_DEFAULT");
                            event.getDrops().add(nbtItem.getItem());
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PUFFERFISH) {
                if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance")) {
                    item = ItemUtils.rename(entityHead.PUFFERFISH, ChatColor.YELLOW + config.getString("PUFFERFISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SALMON) {
                if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance")) {
                    item = ItemUtils.rename(entityHead.SALMON, ChatColor.YELLOW + config.getString("SALMON.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.COD) {
                if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance")) {

                    item = ItemUtils.rename(entityHead.COD, ChatColor.YELLOW + config.getString("COD.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.TURTLE) {
                if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance")) {
                    item = ItemUtils.rename(entityHead.TURTLE, ChatColor.YELLOW + config.getString("TURTLE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DOLPHIN) {
                if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance")) {

                    item = ItemUtils.rename(entityHead.DOLPHIN, ChatColor.YELLOW + config.getString("DOLPHIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PHANTOM) {
                if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance")) {
                    item = ItemUtils.rename(entityHead.PHANTOM, ChatColor.YELLOW + config.getString("PHANTOM.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DROWNED) {
                if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance")) {

                    item = ItemUtils.rename(entityHead.DROWNED, ChatColor.YELLOW + config.getString("DROWNED.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.14 Mob
            } else if (type == EntityType.WANDERING_TRADER) {
                if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance")) {
                    item = ItemUtils.rename(entityHead.WANDERING_TRADER, ChatColor.YELLOW + config.getString("WANDERING_TRADER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.TRADER_LLAMA) {
                if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                    TraderLlama traderLlama = (TraderLlama) entity;

                    switch (traderLlama.getColor()) {
                        case BROWN:
                            item = ItemUtils.rename(entityHead.TRADER_LLAMA_BROWN, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + traderLlama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case WHITE:
                            item = ItemUtils.rename(entityHead.TRADER_LLAMA_WHITE, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + traderLlama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.TRADER_LLAMA_GRAY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + traderLlama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(entityHead.TRADER_LLAMA_CREAMY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + traderLlama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.LLAMA) {
                if (config.getBoolean("LLAMA.Drop") && x <= config.getInt("LLAMA.Chance")) {
                    Llama llama = (Llama) entity;

                    switch (llama.getColor()) {
                        case BROWN:
                            item = ItemUtils.rename(entityHead.LLAMA_BROWN, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + llama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GRAY:
                            item = ItemUtils.rename(entityHead.LLAMA_GRAY, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + llama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(entityHead.LLAMA_CREAMY, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + llama.getColor());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case WHITE:
                            item = ItemUtils.rename(entityHead.LLAMA_WHITE, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + llama.getColor());
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.RAVAGER) {
                if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance")) {
                    item = ItemUtils.rename(entityHead.RAVAGER, ChatColor.YELLOW + config.getString("RAVAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PILLAGER) {
                if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance")) {
                    item = ItemUtils.rename(entityHead.PILLAGER, ChatColor.YELLOW + config.getString("PILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PANDA) {
                if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance")) {
                    Panda panda = (Panda) entity;
                    switch (panda.getMainGene()) {
                        case BROWN:
                            item = ItemUtils.rename(entityHead.PANDA_BROWN, ChatColor.YELLOW + config.getString("PANDA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + panda.getMainGene());
                            event.getDrops().add(nbtItem.getItem());
                            break;

                        default:
                            item = ItemUtils.rename(entityHead.PANDA, ChatColor.YELLOW + config.getString("PANDA.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", "PANDA_DEFAULT");
                            event.getDrops().add(nbtItem.getItem());

                            break;

                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.FOX) {
                if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance")) {
                    Fox fox = (Fox) entity;

                    switch (fox.getFoxType()) {
                        case RED:

                            item = ItemUtils.rename(entityHead.FOX, ChatColor.YELLOW + config.getString("FOX.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + fox.getFoxType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case SNOW:

                            item = ItemUtils.rename(entityHead.FOX_WHITE, ChatColor.YELLOW + config.getString("FOX.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + fox.getFoxType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CAT) {
                if (config.getBoolean("CAT.Drop") && x <= config.getInt("CAT.Chance")) {
                    Cat cat = (Cat) entity;
                    switch (cat.getCatType()) {
                        case BLACK:
                            item = ItemUtils.rename(entityHead.CAT_BLACK, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BRITISH_SHORTHAIR:
                            item = ItemUtils.rename(entityHead.CAT_BRITISH, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CALICO:
                            item = ItemUtils.rename(entityHead.CAT_CALICO, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case JELLIE:
                            item = ItemUtils.rename(entityHead.CAT_JELLIE, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case PERSIAN:
                            item = ItemUtils.rename(entityHead.CAT_PERSIAN, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case RAGDOLL:
                            item = ItemUtils.rename(entityHead.CAT_RAGDOLL, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case RED:
                            item = ItemUtils.rename(entityHead.CAT_RED, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case SIAMESE:
                            item = ItemUtils.rename(entityHead.CAT_SIAMESE, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case TABBY:
                            item = ItemUtils.rename(entityHead.CAT_TABBY, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case WHITE:
                            item = ItemUtils.rename(entityHead.CAT_WHITE, ChatColor.YELLOW + config.getString("CAT.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + cat.getCatType());
                            event.getDrops().add(nbtItem.getItem());
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.15 Mob
            } else if (type == EntityType.BEE) {
                if (config.getBoolean("BEE.Drop") && x <= config.getInt("BEE.Chance")) {
                    Bee bee = (Bee) entity;
                    if (bee.getAnger() > 0) {
                        item = ItemUtils.rename(entityHead.BEE_Aware, ChatColor.YELLOW + config.getString("BEE.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "BEE_ANGER");
                        event.getDrops().add(nbtItem.getItem());
                    } else {
                        item = ItemUtils.rename(entityHead.BEE, ChatColor.YELLOW + config.getString("BEE.Name"));
                        nbtItem = new NBTItem(item);
                        nbtItem.setString("HeadDrop", "BEE");
                        event.getDrops().add(nbtItem.getItem());
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }


                //1.16 Mob
            } else if (type == EntityType.ZOGLIN) {
                if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance")) {
                    item = ItemUtils.rename(entityHead.ZOGLIN, ChatColor.YELLOW + config.getString("ZOGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.STRIDER) {
                if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance")) {
                    item = ItemUtils.rename(entityHead.STRIDER, ChatColor.YELLOW + config.getString("STRIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIGLIN) {
                if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance")) {
                    item = ItemUtils.rename(entityHead.PIGLIN, ChatColor.YELLOW + config.getString("PIGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HOGLIN) {
                if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance")) {

                    item = ItemUtils.rename(entityHead.HOGLIN, ChatColor.YELLOW + config.getString("HOGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIGLIN_BRUTE) {
                if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance")) {
                    item = ItemUtils.rename(entityHead.PIGLIN_BRUTE, ChatColor.YELLOW + config.getString("PIGLIN_BRUTE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                // 1.17 Mob
            } else if (type == EntityType.GLOW_SQUID) {
                if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance")) {

                    item = ItemUtils.rename(entityHead.GLOW_SQUID, ChatColor.YELLOW + config.getString("GLOW_SQUID.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GOAT) {
                if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance")) {

                    item = ItemUtils.rename(entityHead.GOAT, ChatColor.YELLOW + config.getString("GOAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", entity.getType().toString());
                    event.getDrops().add(nbtItem.getItem());

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.AXOLOTL) {
                if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance")) {
                    Axolotl axolotl = (Axolotl) entity;

                    switch (axolotl.getVariant()) {
                        case LUCY:
                            item = ItemUtils.rename(entityHead.AXOLOTL_LUCY, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + axolotl.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case BLUE:
                            item = ItemUtils.rename(entityHead.AXOLOTL_BLUE, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + axolotl.getVariant());
                            event.getDrops().add(nbtItem.getItem());
                            break;

                        case WILD:
                            item = ItemUtils.rename(entityHead.AXOLOTL_WILD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + axolotl.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case CYAN:
                            item = ItemUtils.rename(entityHead.AXOLOTL_CYAN, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + axolotl.getVariant());
                            event.getDrops().add(nbtItem.getItem());

                            break;
                        case GOLD:
                            item = ItemUtils.rename(entityHead.AXOLOTL_GOLD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                            nbtItem = new NBTItem(item);
                            nbtItem.setString("HeadDrop", entity.getType() + "_" + axolotl.getVariant());
                            event.getDrops().add(nbtItem.getItem());


                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            }
        }
    }
}