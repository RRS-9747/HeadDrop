package me.rrs.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.lone.itemsadder.api.CustomEntity;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.database.EntityHead;
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
    final YamlDocument config = HeadDrop.getConfiguration();
    String title, description, footer;

    final Random random = new Random();


    @EventHandler(priority = EventPriority.HIGH)
    public void EntityDropHeadEvent(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        boolean isInDisabledWorld = false;
        int x = random.nextInt(100) + 1;
        List<String> worldList = HeadDrop.getConfiguration().getStringList("Config.Disable-Worlds");

        if (config.getBoolean("Bot.Enable")){
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
                title = PlaceholderAPI.setPlaceholders(entity.getKiller(), title);
                description = PlaceholderAPI.setPlaceholders(entity.getKiller(), description);
                footer = PlaceholderAPI.setPlaceholders(entity.getKiller(), footer);
            }
        }

        if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs")){
            BukkitAPIHelper mythicMobsAPI = new BukkitAPIHelper();
            if (mythicMobsAPI.isMythicMob(entity)) return;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")){
            if (CustomEntity.isCustomEntity(entity)) return;
        }

        if (config.getBoolean("Config.Require-Killer-Player")){
            if (entity.getKiller() == null) return;
        }

        if (config.getBoolean("Config.Killer-Require-Permission")) {
            if (!entity.getKiller().hasPermission("headdrop.killer")) return;
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
                    if (!entity.hasPermission("headdrop.player")) return;
                }

                if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                    ItemStack skull = SkullCreator.itemFromName(entity.getName());
                    event.getDrops().add(skull);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.BAT) {
                if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance")) {
                    item = ItemUtils.rename(EntityHead.BAT, ChatColor.YELLOW + config.getString("BAT.Name"));
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.BLAZE) {
                if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance")) {
                    item = ItemUtils.rename(EntityHead.BLAZE, ChatColor.YELLOW + config.getString("BLAZE.Name"));
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SPIDER) {
                if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance")) {
                    item = ItemUtils.rename(EntityHead.SPIDER, ChatColor.YELLOW + config.getString("SPIDER.Name"));
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CAVE_SPIDER) {
                if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance")) {
                    item = ItemUtils.rename(EntityHead.CAVE_SPIDER, ChatColor.YELLOW + config.getString("CAVE_SPIDER.Name"));
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CHICKEN) {
                if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance")) {
                    item = ItemUtils.rename(EntityHead.CHICKEN, ChatColor.YELLOW + config.getString("CHICKEN.Name"));
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.COW) {
                if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance")) {

                    item = ItemUtils.rename(EntityHead.COW, ChatColor.YELLOW + config.getString("COW.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ENDERMAN) {
                if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance")) {

                    item = ItemUtils.rename(EntityHead.ENDERMAN, ChatColor.YELLOW + config.getString("ENDERMAN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GIANT) {
                if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance")) {

                    item = ItemUtils.rename(EntityHead.GIANT, ChatColor.YELLOW + config.getString("GIANT.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HORSE) {
                if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance")) {
                    Horse horse = (Horse) entity;

                    switch (horse.getColor()) {
                        case WHITE:
                            item = ItemUtils.rename(EntityHead.HORSE_WHITE, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(EntityHead.HORSE_CREAMY, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case CHESTNUT:
                            item = ItemUtils.rename(EntityHead.HORSE_CHESTNUT, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case BROWN:
                            item = ItemUtils.rename(EntityHead.HORSE_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLACK:
                            item = ItemUtils.rename(EntityHead.HORSE_BLACK, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.HORSE_GRAY, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);

                            break;
                        case DARK_BROWN:
                            item = ItemUtils.rename(EntityHead.HORSE_DARK_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));

                            event.getDrops().add(item);


                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ILLUSIONER) {
                if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance")) {
                    item = ItemUtils.rename(EntityHead.ILLUSIONER, ChatColor.YELLOW + config.getString("ILLUSIONER.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.IRON_GOLEM) {
                if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance")) {
                    item = ItemUtils.rename(EntityHead.IRON_GOLEM, ChatColor.YELLOW + config.getString("IRON_GOLEM.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MAGMA_CUBE) {
                if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance")) {
                    item = ItemUtils.rename(EntityHead.MAGMA_CUBE, ChatColor.YELLOW + config.getString("MAGMA_CUBE.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MUSHROOM_COW) {
                if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance")) {
                    MushroomCow mushroomCow = (MushroomCow) entity;

                    switch (mushroomCow.getVariant()) {
                        case RED:
                            item = ItemUtils.rename(EntityHead.MUSHROOM_COW_RED, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));

                            event.getDrops().add(item);

                            break;
                        case BROWN:
                            item = ItemUtils.rename(EntityHead.MUSHROOM_COW_BROWN, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));

                            event.getDrops().add(item);
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.OCELOT) {
                if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance")) {
                    item = ItemUtils.rename(EntityHead.OCELOT, ChatColor.YELLOW + config.getString("OCELOT.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIG) {
                if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance")) {
                    item = ItemUtils.rename(EntityHead.PIG, ChatColor.YELLOW + config.getString("PIG.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SHEEP) {
                if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance")) {
                    Sheep sheep = (Sheep) entity;

                    switch (sheep.getColor()) {

                        case WHITE:
                            item = ItemUtils.rename(EntityHead.SHEEP_WHITE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);
                            break;

                        case ORANGE:
                            item = ItemUtils.rename(EntityHead.SHEEP_ORANGE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case MAGENTA:
                            item = ItemUtils.rename(EntityHead.SHEEP_MAGENTA, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIGHT_BLUE:
                            item = ItemUtils.rename(EntityHead.SHEEP_LIGHT_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case YELLOW:
                            item = ItemUtils.rename(EntityHead.SHEEP_YELLOW, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIME:
                            item = ItemUtils.rename(EntityHead.SHEEP_LIME, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case PINK:
                            item = ItemUtils.rename(EntityHead.SHEEP_PINK, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.SHEEP_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIGHT_GRAY:
                            item = ItemUtils.rename(EntityHead.SHEEP_LIGHT_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case CYAN:
                            item = ItemUtils.rename(EntityHead.SHEEP_CYAN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case PURPLE:
                            item = ItemUtils.rename(EntityHead.SHEEP_PURPLE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLUE:
                            item = ItemUtils.rename(EntityHead.SHEEP_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case BROWN:
                            item = ItemUtils.rename(EntityHead.SHEEP_BROWN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case GREEN:
                            item = ItemUtils.rename(EntityHead.SHEEP_GREEN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case RED:
                            item = ItemUtils.rename(EntityHead.SHEEP_RED, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLACK:
                            item = ItemUtils.rename(EntityHead.SHEEP_BLACK, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                            event.getDrops().add(item);

                            break;
                        default:
                            Bukkit.getLogger().severe("If you notice this error, pls report it to plugin author");
                            throw new IllegalStateException("Unexpected value: " + sheep.getColor());
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SILVERFISH) {
                if (config.getBoolean("SILVERFISH.Drop") && x <= config.getInt("SILVERFISH.Chance")) {
                    item = ItemUtils.rename(EntityHead.SILVERFISH, ChatColor.YELLOW + config.getString("SILVERFISH.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SLIME) {
                if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance")) {
                    item = ItemUtils.rename(EntityHead.SLIME, ChatColor.YELLOW + config.getString("SLIME.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SNOWMAN) {
                if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance")) {
                    item = ItemUtils.rename(EntityHead.SNOWMAN, ChatColor.YELLOW + config.getString("SNOW_GOLEM.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SQUID) {
                if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance")) {
                    item = ItemUtils.rename(EntityHead.SQUID, ChatColor.YELLOW + config.getString("SQUID.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WITCH) {
                if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance")) {
                    item = ItemUtils.rename(EntityHead.WITCH, ChatColor.YELLOW + config.getString("WITCH.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WITHER) {
                if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance")) {
                    item = ItemUtils.rename(EntityHead.WITHER, ChatColor.YELLOW + config.getString("WITHER.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
                if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance")) {
                    item = ItemUtils.rename(EntityHead.ZOMBIFIED_PIGLIN, ChatColor.YELLOW + config.getString("ZOMBIFIED_PIGLIN.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GHAST) {
                if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance")) {
                    item = ItemUtils.rename(EntityHead.GHAST, ChatColor.YELLOW + config.getString("GHAST.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.WOLF) {
                if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance")) {
                    Wolf wolf = (Wolf) entity;

                    if (wolf.isAngry()) {
                        item = ItemUtils.rename(EntityHead.WOLF_ANGRY, ChatColor.YELLOW + config.getString("WOLF.Name"));
                        

                        event.getDrops().add(item);
                    } else {
                        item = ItemUtils.rename(EntityHead.WOLF, ChatColor.YELLOW + config.getString("WOLF.Name"));

                        event.getDrops().add(item);
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VILLAGER) {
                if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance")) {
                    Villager villager = (Villager) entity;

                    switch (villager.getProfession()) {
                        case WEAPONSMITH:
                            item = ItemUtils.rename(EntityHead.VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case SHEPHERD:
                            item = ItemUtils.rename(EntityHead.VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIBRARIAN:
                            item = ItemUtils.rename(EntityHead.VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FLETCHER:
                            item = ItemUtils.rename(EntityHead.VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FISHERMAN:
                            item = ItemUtils.rename(EntityHead.VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FARMER:
                            item = ItemUtils.rename(EntityHead.VILLAGER_FARMER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case CLERIC:
                            item = ItemUtils.rename(EntityHead.VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case CARTOGRAPHER:
                            item = ItemUtils.rename(EntityHead.VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case BUTCHER:
                            item = ItemUtils.rename(EntityHead.VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case ARMORER:
                            item = ItemUtils.rename(EntityHead.VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        default:
                            item = ItemUtils.rename(EntityHead.VILLAGER_NULL, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                            event.getDrops().add(item);
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
                            item = ItemUtils.rename(EntityHead.RABBIT_BROWN, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case WHITE:
                            item = ItemUtils.rename(EntityHead.RABBIT_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLACK:
                            item = ItemUtils.rename(EntityHead.RABBIT_BLACK, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLACK_AND_WHITE:
                            item = ItemUtils.rename(EntityHead.RABBIT_BLACK_AND_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case GOLD:
                            item = ItemUtils.rename(EntityHead.RABBIT_GOLD, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case SALT_AND_PEPPER:
                            item = ItemUtils.rename(EntityHead.RABBIT_SALT_AND_PEPPER, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                        case THE_KILLER_BUNNY:
                            item = ItemUtils.rename(EntityHead.RABBIT_THE_KILLER_BUNNY, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                            event.getDrops().add(item);

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ENDERMITE) {
                if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance")) {

                    item = ItemUtils.rename(EntityHead.ENDERMITE, ChatColor.YELLOW + config.getString("ENDERMITE.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GUARDIAN) {
                if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance")) {

                    item = ItemUtils.rename(EntityHead.GUARDIAN, ChatColor.YELLOW + config.getString("GUARDIAN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.9 Mob
            } else if (type == EntityType.SHULKER) {
                if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance")) {
                    item = ItemUtils.rename(EntityHead.SHULKER, ChatColor.YELLOW + config.getString("SHULKER.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.10 Mob
            } else if (type == EntityType.POLAR_BEAR) {
                if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance")) {
                    item = ItemUtils.rename(EntityHead.POLAR_BEAR, ChatColor.YELLOW + config.getString("POLAR_BEAR.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.11 Mob
            } else if (type == EntityType.ZOMBIE_VILLAGER) {
                if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance")) {
                    ZombieVillager zombieVillager = (ZombieVillager) entity;

                    switch (zombieVillager.getVillagerProfession()) {
                        case ARMORER:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case BUTCHER:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case CARTOGRAPHER:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case CLERIC:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FARMER:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_FARMER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FISHERMAN:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case FLETCHER:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIBRARIAN:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case SHEPHERD:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        case WEAPONSMITH:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);

                            break;
                        default:
                            item = ItemUtils.rename(EntityHead.ZOMBIE_VILLAGER_NULL, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                            event.getDrops().add(item);
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VINDICATOR) {
                if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance")) {
                    item = ItemUtils.rename(EntityHead.VINDICATOR, ChatColor.YELLOW + config.getString("VINDICATOR.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.VEX) {
                if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance")) {

                    Vex vex = (Vex) entity;
                    if (vex.isCharging()) {
                        item = ItemUtils.rename(EntityHead.VEX_CHARGE, ChatColor.YELLOW + config.getString("VEX.Name"));

                        event.getDrops().add(item);
                    } else {
                        item = ItemUtils.rename(EntityHead.VEX, ChatColor.YELLOW + config.getString("VEX.Name"));

                        event.getDrops().add(item);
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.EVOKER) {
                if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance")) {

                    item = ItemUtils.rename(EntityHead.EVOKER, ChatColor.YELLOW + config.getString("EVOKER.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HUSK) {
                if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance")) {
                    item = ItemUtils.rename(EntityHead.HUSK, ChatColor.YELLOW + config.getString("HUSK.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.STRAY) {
                if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance")) {
                    item = ItemUtils.rename(EntityHead.STRAY, ChatColor.YELLOW + config.getString("STRAY.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DROPPED_ITEM) {
            } else if (type == EntityType.EXPERIENCE_ORB) {
            } else if (type == EntityType.AREA_EFFECT_CLOUD) {
            } else if (type == EntityType.ELDER_GUARDIAN) {
                if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance")) {

                    item = ItemUtils.rename(EntityHead.ELDER_GUARDIAN, ChatColor.YELLOW + config.getString("ELDER_GUARDIAN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DONKEY) {
                if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance")) {

                    item = ItemUtils.rename(EntityHead.DONKEY, ChatColor.YELLOW + config.getString("DONKEY.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.ZOMBIE_HORSE) {
                if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance")) {
                    item = ItemUtils.rename(EntityHead.ZOMBIE_HORSE, ChatColor.YELLOW + config.getString("ZOMBIE_HORSE.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SKELETON_HORSE) {
                if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance")) {
                    item = ItemUtils.rename(EntityHead.SKELETON_HORSE, ChatColor.YELLOW + config.getString("SKELETON_HORSE.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.MULE) {
                if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance")) {
                    item = ItemUtils.rename(EntityHead.MULE, ChatColor.YELLOW + config.getString("MULE.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
                //1.12 Mob
            } else if (type == EntityType.PARROT) {
                if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance")) {
                    Parrot parrot = (Parrot) entity;

                    switch (parrot.getVariant()) {
                        case BLUE:
                            item = ItemUtils.rename(EntityHead.PARROT_BLUE, ChatColor.YELLOW + config.getString("PARROT.Name"));

                            event.getDrops().add(item);

                            break;
                        case CYAN:
                            item = ItemUtils.rename(EntityHead.PARROT_CYAN, ChatColor.YELLOW + config.getString("PARROT.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.PARROT_GRAY, ChatColor.YELLOW + config.getString("PARROT.Name"));

                            event.getDrops().add(item);

                            break;
                        case RED:
                            item = ItemUtils.rename(EntityHead.PARROT_RED, ChatColor.YELLOW + config.getString("PARROT.Name"));

                            event.getDrops().add(item);

                            break;
                        case GREEN:
                            item = ItemUtils.rename(EntityHead.PARROT_GREEN, ChatColor.YELLOW + config.getString("PARROT.Name"));

                            event.getDrops().add(item);
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
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_MAGENTA, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIGHT_BLUE:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_LIGHT_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case YELLOW:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_YELLOW, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case PINK:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_PINK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case LIGHT_GRAY:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_LIGHT_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case CYAN:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_CYAN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLUE:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case GREEN:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_GREEN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case RED:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_RED, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLACK:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_BLACK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);

                            break;

                        default:
                            item = ItemUtils.rename(EntityHead.TROPICAL_FISH_ORANGE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                            event.getDrops().add(item);
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PUFFERFISH) {
                if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance")) {
                    item = ItemUtils.rename(EntityHead.PUFFERFISH, ChatColor.YELLOW + config.getString("PUFFERFISH.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.SALMON) {
                if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance")) {
                    item = ItemUtils.rename(EntityHead.SALMON, ChatColor.YELLOW + config.getString("SALMON.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.COD) {
                if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance")) {

                    item = ItemUtils.rename(EntityHead.COD, ChatColor.YELLOW + config.getString("COD.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.TURTLE) {
                if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance")) {
                    item = ItemUtils.rename(EntityHead.TURTLE, ChatColor.YELLOW + config.getString("TURTLE.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DOLPHIN) {
                if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance")) {

                    item = ItemUtils.rename(EntityHead.DOLPHIN, ChatColor.YELLOW + config.getString("DOLPHIN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PHANTOM) {
                if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance")) {
                    item = ItemUtils.rename(EntityHead.PHANTOM, ChatColor.YELLOW + config.getString("PHANTOM.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.DROWNED) {
                if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance")) {

                    item = ItemUtils.rename(EntityHead.DROWNED, ChatColor.YELLOW + config.getString("DROWNED.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.14 Mob
            } else if (type == EntityType.WANDERING_TRADER) {
                if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance")) {
                    item = ItemUtils.rename(EntityHead.WANDERING_TRADER, ChatColor.YELLOW + config.getString("WANDERING_TRADER.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.TRADER_LLAMA) {
                if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                    TraderLlama traderLlama = (TraderLlama) entity;

                    switch (traderLlama.getColor()) {
                        case BROWN:
                            item = ItemUtils.rename(EntityHead.TRADER_LLAMA_BROWN, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case WHITE:
                            item = ItemUtils.rename(EntityHead.TRADER_LLAMA_WHITE, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.TRADER_LLAMA_GRAY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(EntityHead.TRADER_LLAMA_CREAMY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.LLAMA) {
                if (config.getBoolean("LLAMA.Drop") && x <= config.getInt("LLAMA.Chance")) {
                    Llama llama = (Llama) entity;

                    switch (llama.getColor()) {
                        case BROWN:
                            item = ItemUtils.rename(EntityHead.LLAMA_BROWN, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case GRAY:
                            item = ItemUtils.rename(EntityHead.LLAMA_GRAY, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case CREAMY:
                            item = ItemUtils.rename(EntityHead.LLAMA_CREAMY, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                            event.getDrops().add(item);

                            break;
                        case WHITE:
                            item = ItemUtils.rename(EntityHead.LLAMA_WHITE, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                            event.getDrops().add(item);
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.RAVAGER) {
                if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance")) {
                    item = ItemUtils.rename(EntityHead.RAVAGER, ChatColor.YELLOW + config.getString("RAVAGER.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PILLAGER) {
                if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance")) {
                    item = ItemUtils.rename(EntityHead.PILLAGER, ChatColor.YELLOW + config.getString("PILLAGER.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PANDA) {
                if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance")) {
                    Panda panda = (Panda) entity;
                    if (panda.getMainGene() == Panda.Gene.BROWN) {
                        item = ItemUtils.rename(EntityHead.PANDA_BROWN, ChatColor.YELLOW + config.getString("PANDA.Name"));

                        event.getDrops().add(item);
                    } else {
                        item = ItemUtils.rename(EntityHead.PANDA, ChatColor.YELLOW + config.getString("PANDA.Name"));

                        event.getDrops().add(item);
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.FOX) {
                if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance")) {
                    Fox fox = (Fox) entity;

                    switch (fox.getFoxType()) {
                        case RED:

                            item = ItemUtils.rename(EntityHead.FOX, ChatColor.YELLOW + config.getString("FOX.Name"));

                            event.getDrops().add(item);

                            break;
                        case SNOW:

                            item = ItemUtils.rename(EntityHead.FOX_WHITE, ChatColor.YELLOW + config.getString("FOX.Name"));

                            event.getDrops().add(item);

                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.CAT) {
                if (config.getBoolean("CAT.Drop") && x <= config.getInt("CAT.Chance")) {
                    Cat cat = (Cat) entity;
                    switch (cat.getCatType()) {
                        case BLACK:
                            item = ItemUtils.rename(EntityHead.CAT_BLACK, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case BRITISH_SHORTHAIR:
                            item = ItemUtils.rename(EntityHead.CAT_BRITISH, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case CALICO:
                            item = ItemUtils.rename(EntityHead.CAT_CALICO, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case JELLIE:
                            item = ItemUtils.rename(EntityHead.CAT_JELLIE, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case PERSIAN:
                            item = ItemUtils.rename(EntityHead.CAT_PERSIAN, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case RAGDOLL:
                            item = ItemUtils.rename(EntityHead.CAT_RAGDOLL, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case RED:
                            item = ItemUtils.rename(EntityHead.CAT_RED, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case SIAMESE:
                            item = ItemUtils.rename(EntityHead.CAT_SIAMESE, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case TABBY:
                            item = ItemUtils.rename(EntityHead.CAT_TABBY, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);

                            break;
                        case WHITE:
                            item = ItemUtils.rename(EntityHead.CAT_WHITE, ChatColor.YELLOW + config.getString("CAT.Name"));

                            event.getDrops().add(item);
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.15 Mob
            } else if (type == EntityType.BEE) {
                if (config.getBoolean("BEE.Drop") && x <= config.getInt("BEE.Chance")) {
                    Bee bee = (Bee) entity;
                    if (bee.getAnger() > 0) {
                        item = ItemUtils.rename(EntityHead.BEE_Aware, ChatColor.YELLOW + config.getString("BEE.Name"));

                        event.getDrops().add(item);
                    } else {
                        item = ItemUtils.rename(EntityHead.BEE, ChatColor.YELLOW + config.getString("BEE.Name"));

                        event.getDrops().add(item);
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }


                //1.16 Mob
            } else if (type == EntityType.ZOGLIN) {
                if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance")) {
                    item = ItemUtils.rename(EntityHead.ZOGLIN, ChatColor.YELLOW + config.getString("ZOGLIN.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.STRIDER) {
                if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance")) {
                    item = ItemUtils.rename(EntityHead.STRIDER, ChatColor.YELLOW + config.getString("STRIDER.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIGLIN) {
                if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance")) {
                    item = ItemUtils.rename(EntityHead.PIGLIN, ChatColor.YELLOW + config.getString("PIGLIN.Name"));
                    
                    
                    event.getDrops().add(item);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.HOGLIN) {
                if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance")) {

                    item = ItemUtils.rename(EntityHead.HOGLIN, ChatColor.YELLOW + config.getString("HOGLIN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.PIGLIN_BRUTE) {
                if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance")) {
                    item = ItemUtils.rename(EntityHead.PIGLIN_BRUTE, ChatColor.YELLOW + config.getString("PIGLIN_BRUTE.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                // 1.17 Mob
            } else if (type == EntityType.GLOW_SQUID) {
                if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance")) {

                    item = ItemUtils.rename(EntityHead.GLOW_SQUID, ChatColor.YELLOW + config.getString("GLOW_SQUID.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.GOAT) {
                if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance")) {

                    item = ItemUtils.rename(EntityHead.GOAT, ChatColor.YELLOW + config.getString("GOAT.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.AXOLOTL) {
                if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance")) {
                    Axolotl axolotl = (Axolotl) entity;

                    switch (axolotl.getVariant()) {
                        case LUCY:
                            item = ItemUtils.rename(EntityHead.AXOLOTL_LUCY, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                            event.getDrops().add(item);

                            break;
                        case BLUE:
                            item = ItemUtils.rename(EntityHead.AXOLOTL_BLUE, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                            event.getDrops().add(item);
                            break;

                        case WILD:
                            item = ItemUtils.rename(EntityHead.AXOLOTL_WILD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                            event.getDrops().add(item);

                            break;
                        case CYAN:
                            item = ItemUtils.rename(EntityHead.AXOLOTL_CYAN, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                            event.getDrops().add(item);

                            break;
                        case GOLD:
                            item = ItemUtils.rename(EntityHead.AXOLOTL_GOLD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                            event.getDrops().add(item);


                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

                //1.19 Mob
            } else if (type == EntityType.ALLAY) {
                if (config.getBoolean("ALLAY.Drop") && x <= config.getInt("ALLAY.Chance")) {

                    item = ItemUtils.rename(EntityHead.ALLAY, ChatColor.YELLOW + config.getString("ALLAY.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

            } else if (type == EntityType.FROG) {
                if (config.getBoolean("FROG.Drop") && x <= config.getInt("FROG.Chance")) {
                    Frog frog = (Frog) entity;
                    switch (frog.getVariant()) {
                        case TEMPERATE:
                            item = ItemUtils.rename(EntityHead.FROG_TEMPERATE, ChatColor.YELLOW + config.getString("FROG.Name"));
                            

                            event.getDrops().add(item);

                            break;
                        case WARM:
                            item = ItemUtils.rename(EntityHead.FROG_WARM, ChatColor.YELLOW + config.getString("FROG.Name"));
                            

                            event.getDrops().add(item);

                            break;
                        case COLD:
                            item = ItemUtils.rename(EntityHead.FROG_COLD, ChatColor.YELLOW + config.getString("FROG.Name"));

                            event.getDrops().add(item);
                            break;
                    }
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            } else if (type == EntityType.TADPOLE) {
                if (config.getBoolean("TADPOLE.Drop") && x <= config.getInt("TADPOLE.Chance")) {

                    item = ItemUtils.rename(EntityHead.TADPOLE, ChatColor.YELLOW + config.getString("TADPOLE.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }

            } else if (type == EntityType.WARDEN) {
                if (config.getBoolean("WARDEN.Drop") && x <= config.getInt("WARDEN.Chance")) {

                    item = ItemUtils.rename(EntityHead.WARDEN, ChatColor.YELLOW + config.getString("WARDEN.Name"));
                    
                    
                    event.getDrops().add(item);

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            }
        }
    }
}