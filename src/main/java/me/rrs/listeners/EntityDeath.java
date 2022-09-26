package me.rrs.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.database.EntityHead;
import me.rrs.util.Embed;
import me.rrs.util.ItemUtils;
import me.rrs.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public class EntityDeath implements Listener {

    final YamlDocument config = HeadDrop.getConfiguration();
    String title, description, footer;

    @EventHandler(priority = EventPriority.HIGH)
    public void EntityDropHeadEvent(final EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        if (config.getBoolean("Bot.Enable")) {
            if (entity.getKiller() != null) {
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
        }

        if (!entity.getPersistentDataContainer().getKeys().isEmpty()) return;
        if (config.getBoolean("Config.Require-Killer-Player")) if (entity.getKiller() == null) return;
        if (config.getBoolean("Config.Killer-Require-Permission")) {
            if (!entity.getKiller().hasPermission("headdrop.killer")) return;
        }

        List<String> worldList = config.getStringList("Config.Disable-Worlds");
        for (String world : worldList) {
            if (entity.getWorld().getName().equalsIgnoreCase(world)) return;
        }

        EntityHead head = new EntityHead();
        EntityType type = entity.getType();

        final Random random = new Random();
        int x = random.nextInt(100) + 1;
        ItemStack item;

        if (type == EntityType.PLAYER) {
            if (config.getBoolean("PLAYER.Require-Permission"))
                if (!entity.hasPermission("headdrop.player")) return;

            if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                ItemStack skull = SkullCreator.itemFromName(entity.getName());
                event.getDrops().add(skull);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.BAT) {
            if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance")) {
                item = ItemUtils.rename(head.BAT, config.getString("BAT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.BLAZE) {
            if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance")) {
                item = ItemUtils.rename(head.BLAZE, config.getString("BLAZE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SPIDER) {
            if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance")) {
                item = ItemUtils.rename(head.SPIDER, config.getString("SPIDER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CAVE_SPIDER) {
            if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance")) {
                item = ItemUtils.rename(head.CAVE_SPIDER, config.getString("CAVE_SPIDER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CHICKEN) {
            if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance")) {
                item = ItemUtils.rename(head.CHICKEN, config.getString("CHICKEN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COW) {
            if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance")) {
                item = ItemUtils.rename(head.COW, config.getString("COW.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMAN) {
            if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance")) {
                item = ItemUtils.rename(head.ENDERMAN, config.getString("ENDERMAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GIANT) {
            if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance")) {
                item = ItemUtils.rename(head.GIANT, config.getString("GIANT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HORSE) {
            if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance")) {
                Horse horse = (Horse) entity;

                switch (horse.getColor()) {
                    case WHITE:
                        item = ItemUtils.rename(head.HORSE_WHITE, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case CREAMY:
                        item = ItemUtils.rename(head.HORSE_CREAMY, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case CHESTNUT:
                        item = ItemUtils.rename(head.HORSE_CHESTNUT, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case BROWN:
                        item = ItemUtils.rename(head.HORSE_BROWN, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.HORSE_BLACK, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.HORSE_GRAY, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                    case DARK_BROWN:
                        item = ItemUtils.rename(head.HORSE_DARK_BROWN, config.getString("HORSE.Name"));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ILLUSIONER) {
            if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance")) {
                item = ItemUtils.rename(head.ILLUSIONER, config.getString("ILLUSIONER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.IRON_GOLEM) {
            if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance")) {
                item = ItemUtils.rename(head.IRON_GOLEM, config.getString("IRON_GOLEM.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MAGMA_CUBE) {
            if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance")) {
                item = ItemUtils.rename(head.MAGMA_CUBE, config.getString("MAGMA_CUBE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MUSHROOM_COW) {
            if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance")) {
                MushroomCow mushroomCow = (MushroomCow) entity;

                switch (mushroomCow.getVariant()) {
                    case RED:
                        item = ItemUtils.rename(head.MUSHROOM_COW_RED, config.getString("MUSHROOM_COW.Name"));
                        event.getDrops().add(item);

                        break;
                    case BROWN:
                        item = ItemUtils.rename(head.MUSHROOM_COW_BROWN, config.getString("MUSHROOM_COW.Name"));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.OCELOT) {
            if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance")) {
                item = ItemUtils.rename(head.OCELOT, config.getString("OCELOT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIG) {
            if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance")) {
                item = ItemUtils.rename(head.PIG, config.getString("PIG.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SHEEP) {
            if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance")) {
                Sheep sheep = (Sheep) entity;

                switch (sheep.getColor()) {

                    case WHITE:
                        item = ItemUtils.rename(head.SHEEP_WHITE, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);
                        break;

                    case ORANGE:
                        item = ItemUtils.rename(head.SHEEP_ORANGE, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case MAGENTA:
                        item = ItemUtils.rename(head.SHEEP_MAGENTA, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_BLUE:
                        item = ItemUtils.rename(head.SHEEP_LIGHT_BLUE, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case YELLOW:
                        item = ItemUtils.rename(head.SHEEP_YELLOW, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIME:
                        item = ItemUtils.rename(head.SHEEP_LIME, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case PINK:
                        item = ItemUtils.rename(head.SHEEP_PINK, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.SHEEP_GRAY, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_GRAY:
                        item = ItemUtils.rename(head.SHEEP_LIGHT_GRAY, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.SHEEP_CYAN, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case PURPLE:
                        item = ItemUtils.rename(head.SHEEP_PURPLE, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLUE:
                        item = ItemUtils.rename(head.SHEEP_BLUE, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case BROWN:
                        item = ItemUtils.rename(head.SHEEP_BROWN, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.SHEEP_GREEN, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.SHEEP_RED, config.getString("SHEEP.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.SHEEP_BLACK, config.getString("SHEEP.Name"));
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
                item = ItemUtils.rename(head.SILVERFISH, config.getString("SILVERFISH.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SLIME) {
            if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance")) {
                item = ItemUtils.rename(head.SLIME, config.getString("SLIME.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SNOWMAN) {
            if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance")) {
                item = ItemUtils.rename(head.SNOWMAN, config.getString("SNOW_GOLEM.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SQUID) {
            if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance")) {
                item = ItemUtils.rename(head.SQUID, config.getString("SQUID.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITCH) {
            if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance")) {
                item = ItemUtils.rename(head.WITCH, config.getString("WITCH.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITHER) {
            if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance")) {
                item = ItemUtils.rename(head.WITHER, config.getString("WITHER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
            if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance")) {
                item = ItemUtils.rename(head.ZOMBIFIED_PIGLIN, config.getString("ZOMBIFIED_PIGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GHAST) {
            if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance")) {
                item = ItemUtils.rename(head.GHAST, config.getString("GHAST.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WOLF) {
            if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance")) {
                Wolf wolf = (Wolf) entity;

                if (wolf.isAngry()) {
                    item = ItemUtils.rename(head.WOLF_ANGRY, config.getString("WOLF.Name"));
                    event.getDrops().add(item);
                } else {
                    item = ItemUtils.rename(head.WOLF, config.getString("WOLF.Name"));
                    event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VILLAGER) {
            if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance")) {
                Villager villager = (Villager) entity;

                switch (villager.getProfession()) {
                    case WEAPONSMITH:
                        item = ItemUtils.rename(head.VILLAGER_WEAPONSMITH, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case SHEPHERD:
                        item = ItemUtils.rename(head.VILLAGER_SHEPHERD, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIBRARIAN:
                        item = ItemUtils.rename(head.VILLAGER_LIBRARIAN, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FLETCHER:
                        item = ItemUtils.rename(head.VILLAGER_FLETCHER, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FISHERMAN:
                        item = ItemUtils.rename(head.VILLAGER_FISHERMAN, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FARMER:
                        item = ItemUtils.rename(head.VILLAGER_FARMER, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case CLERIC:
                        item = ItemUtils.rename(head.VILLAGER_CLERIC, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case CARTOGRAPHER:
                        item = ItemUtils.rename(head.VILLAGER_CARTOGRAPHER, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case BUTCHER:
                        item = ItemUtils.rename(head.VILLAGER_BUTCHER, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case ARMORER:
                        item = ItemUtils.rename(head.VILLAGER_ARMORER, config.getString("VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    default:
                        item = ItemUtils.rename(head.VILLAGER_NULL, config.getString("VILLAGER.Name"));
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
                        item = ItemUtils.rename(head.RABBIT_BROWN, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.RABBIT_WHITE, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.RABBIT_BLACK, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLACK_AND_WHITE:
                        item = ItemUtils.rename(head.RABBIT_BLACK_AND_WHITE, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case GOLD:
                        item = ItemUtils.rename(head.RABBIT_GOLD, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case SALT_AND_PEPPER:
                        item = ItemUtils.rename(head.RABBIT_SALT_AND_PEPPER, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                    case THE_KILLER_BUNNY:
                        item = ItemUtils.rename(head.RABBIT_THE_KILLER_BUNNY, config.getString("RABBIT.Name"));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMITE) {
            if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance")) {

                item = ItemUtils.rename(head.ENDERMITE, config.getString("ENDERMITE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GUARDIAN) {
            if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance")) {

                item = ItemUtils.rename(head.GUARDIAN, config.getString("GUARDIAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.9 Mob
        } else if (type == EntityType.SHULKER) {
            if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance")) {
                item = ItemUtils.rename(head.SHULKER, config.getString("SHULKER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.10 Mob
        } else if (type == EntityType.POLAR_BEAR) {
            if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance")) {
                item = ItemUtils.rename(head.POLAR_BEAR, config.getString("POLAR_BEAR.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.11 Mob
        } else if (type == EntityType.ZOMBIE_VILLAGER) {
            if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance")) {
                ZombieVillager zombieVillager = (ZombieVillager) entity;

                switch (zombieVillager.getVillagerProfession()) {
                    case ARMORER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_ARMORER, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case BUTCHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_BUTCHER, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case CARTOGRAPHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_CARTOGRAPHER, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case CLERIC:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_CLERIC, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FARMER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FARMER, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FISHERMAN:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FISHERMAN, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case FLETCHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FLETCHER, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIBRARIAN:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_LIBRARIAN, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case SHEPHERD:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_SHEPHERD, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    case WEAPONSMITH:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_WEAPONSMITH, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);

                        break;
                    default:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_NULL, config.getString("ZOMBIE_VILLAGER.Name"));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VINDICATOR) {
            if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance")) {
                item = ItemUtils.rename(head.VINDICATOR, config.getString("VINDICATOR.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VEX) {
            if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance")) {

                Vex vex = (Vex) entity;
                if (vex.isCharging()) {
                    item = ItemUtils.rename(head.VEX_CHARGE, config.getString("VEX.Name"));

                    event.getDrops().add(item);
                } else {
                    item = ItemUtils.rename(head.VEX, config.getString("VEX.Name"));
                    event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.EVOKER) {
            if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance")) {

                item = ItemUtils.rename(head.EVOKER, config.getString("EVOKER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HUSK) {
            if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance")) {
                item = ItemUtils.rename(head.HUSK, config.getString("HUSK.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRAY) {
            if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance")) {
                item = ItemUtils.rename(head.STRAY, config.getString("STRAY.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ELDER_GUARDIAN) {
            if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance")) {

                item = ItemUtils.rename(head.ELDER_GUARDIAN, config.getString("ELDER_GUARDIAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DONKEY) {
            if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance")) {

                item = ItemUtils.rename(head.DONKEY, config.getString("DONKEY.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIE_HORSE) {
            if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance")) {
                item = ItemUtils.rename(head.ZOMBIE_HORSE, config.getString("ZOMBIE_HORSE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SKELETON_HORSE) {
            if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance")) {
                item = ItemUtils.rename(head.SKELETON_HORSE, config.getString("SKELETON_HORSE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MULE) {
            if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance")) {
                item = ItemUtils.rename(head.MULE, config.getString("MULE.Name"));


                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.12 Mob
        } else if (type == EntityType.PARROT) {
            if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance")) {
                Parrot parrot = (Parrot) entity;

                switch (parrot.getVariant()) {
                    case BLUE:
                        item = ItemUtils.rename(head.PARROT_BLUE, config.getString("PARROT.Name"));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.PARROT_CYAN, config.getString("PARROT.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.PARROT_GRAY, config.getString("PARROT.Name"));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.PARROT_RED, config.getString("PARROT.Name"));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.PARROT_GREEN, config.getString("PARROT.Name"));
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
                        item = ItemUtils.rename(head.TROPICAL_FISH_MAGENTA, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_BLUE:
                        item = ItemUtils.rename(head.TROPICAL_FISH_LIGHT_BLUE, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case YELLOW:
                        item = ItemUtils.rename(head.TROPICAL_FISH_YELLOW, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case PINK:
                        item = ItemUtils.rename(head.TROPICAL_FISH_PINK, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.TROPICAL_FISH_GRAY, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_GRAY:
                        item = ItemUtils.rename(head.TROPICAL_FISH_LIGHT_GRAY, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.TROPICAL_FISH_CYAN, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLUE:
                        item = ItemUtils.rename(head.TROPICAL_FISH_BLUE, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.TROPICAL_FISH_GREEN, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.TROPICAL_FISH_RED, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.TROPICAL_FISH_BLACK, config.getString("TROPICAL_FISH.Name"));
                        event.getDrops().add(item);

                        break;

                    default:
                        item = ItemUtils.rename(head.TROPICAL_FISH_ORANGE, config.getString("TROPICAL_FISH.Name"));

                        event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PUFFERFISH) {
            if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance")) {
                item = ItemUtils.rename(head.PUFFERFISH, config.getString("PUFFERFISH.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SALMON) {
            if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance")) {
                item = ItemUtils.rename(head.SALMON, config.getString("SALMON.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COD) {
            if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance")) {

                item = ItemUtils.rename(head.COD, config.getString("COD.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TURTLE) {
            if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance")) {
                item = ItemUtils.rename(head.TURTLE, config.getString("TURTLE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DOLPHIN) {
            if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance")) {

                item = ItemUtils.rename(head.DOLPHIN, config.getString("DOLPHIN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PHANTOM) {
            if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance")) {
                item = ItemUtils.rename(head.PHANTOM, config.getString("PHANTOM.Name"));


                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DROWNED) {
            if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance")) {

                item = ItemUtils.rename(head.DROWNED, config.getString("DROWNED.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.14 Mob
        } else if (type == EntityType.WANDERING_TRADER) {
            if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance")) {
                item = ItemUtils.rename(head.WANDERING_TRADER, config.getString("WANDERING_TRADER.Name"));


                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TRADER_LLAMA) {
            if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                TraderLlama traderLlama = (TraderLlama) entity;

                switch (traderLlama.getColor()) {
                    case BROWN:
                        item = ItemUtils.rename(head.TRADER_LLAMA_BROWN, config.getString("TRADER_LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.TRADER_LLAMA_WHITE, config.getString("TRADER_LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.TRADER_LLAMA_GRAY, config.getString("TRADER_LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case CREAMY:
                        item = ItemUtils.rename(head.TRADER_LLAMA_CREAMY, config.getString("TRADER_LLAMA.Name"));
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
                        item = ItemUtils.rename(head.LLAMA_BROWN, config.getString("LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.LLAMA_GRAY, config.getString("LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case CREAMY:
                        item = ItemUtils.rename(head.LLAMA_CREAMY, config.getString("LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.LLAMA_WHITE, config.getString("LLAMA.Name"));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.RAVAGER) {
            if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance")) {
                item = ItemUtils.rename(head.RAVAGER, config.getString("RAVAGER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PILLAGER) {
            if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance")) {
                item = ItemUtils.rename(head.PILLAGER, config.getString("PILLAGER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PANDA) {
            if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance")) {
                Panda panda = (Panda) entity;
                if (panda.getMainGene() == Panda.Gene.BROWN) {
                    item = ItemUtils.rename(head.PANDA_BROWN, config.getString("PANDA.Name"));

                    event.getDrops().add(item);
                } else {
                    item = ItemUtils.rename(head.PANDA, config.getString("PANDA.Name"));

                    event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.FOX) {
            if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance")) {
                Fox fox = (Fox) entity;

                switch (fox.getFoxType()) {
                    case RED:

                        item = ItemUtils.rename(head.FOX, config.getString("FOX.Name"));
                        event.getDrops().add(item);

                        break;
                    case SNOW:

                        item = ItemUtils.rename(head.FOX_WHITE, config.getString("FOX.Name"));
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
                        item = ItemUtils.rename(head.CAT_BLACK, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case BRITISH_SHORTHAIR:
                        item = ItemUtils.rename(head.CAT_BRITISH, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case CALICO:
                        item = ItemUtils.rename(head.CAT_CALICO, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case JELLIE:
                        item = ItemUtils.rename(head.CAT_JELLIE, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case PERSIAN:
                        item = ItemUtils.rename(head.CAT_PERSIAN, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case RAGDOLL:
                        item = ItemUtils.rename(head.CAT_RAGDOLL, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.CAT_RED, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case SIAMESE:
                        item = ItemUtils.rename(head.CAT_SIAMESE, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case TABBY:
                        item = ItemUtils.rename(head.CAT_TABBY, config.getString("CAT.Name"));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.CAT_WHITE, config.getString("CAT.Name"));
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
                    item = ItemUtils.rename(head.BEE_Aware, config.getString("BEE.Name"));
                    event.getDrops().add(item);
                } else {
                    item = ItemUtils.rename(head.BEE, config.getString("BEE.Name"));
                    event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.16 Mob
        } else if (type == EntityType.ZOGLIN) {
            if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance")) {
                item = ItemUtils.rename(head.ZOGLIN, config.getString("ZOGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRIDER) {
            if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance")) {
                item = ItemUtils.rename(head.STRIDER, config.getString("STRIDER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN) {
            if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance")) {
                item = ItemUtils.rename(head.PIGLIN, config.getString("PIGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HOGLIN) {
            if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance")) {

                item = ItemUtils.rename(head.HOGLIN, config.getString("HOGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN_BRUTE) {
            if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance")) {
                item = ItemUtils.rename(head.PIGLIN_BRUTE, config.getString("PIGLIN_BRUTE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            // 1.17 Mob
        } else if (type == EntityType.GLOW_SQUID) {
            if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance")) {

                item = ItemUtils.rename(head.GLOW_SQUID, config.getString("GLOW_SQUID.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GOAT) {
            if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance")) {
                item = ItemUtils.rename(head.GOAT, config.getString("GOAT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.AXOLOTL) {
            if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance")) {
                Axolotl axolotl = (Axolotl) entity;

                switch (axolotl.getVariant()) {
                    case LUCY:
                        item = ItemUtils.rename(head.AXOLOTL_LUCY, config.getString("AXOLOTL.Name"));
                        event.getDrops().add(item);
                        break;
                    case BLUE:
                        item = ItemUtils.rename(head.AXOLOTL_BLUE, config.getString("AXOLOTL.Name"));
                        event.getDrops().add(item);
                        break;
                    case WILD:
                        item = ItemUtils.rename(head.AXOLOTL_WILD, config.getString("AXOLOTL.Name"));
                        event.getDrops().add(item);
                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.AXOLOTL_CYAN, config.getString("AXOLOTL.Name"));
                        event.getDrops().add(item);
                        break;
                    case GOLD:
                        item = ItemUtils.rename(head.AXOLOTL_GOLD, config.getString("AXOLOTL.Name"));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.19 Mob
        } else if (type == EntityType.ALLAY) {
            if (config.getBoolean("ALLAY.Drop") && x <= config.getInt("ALLAY.Chance")) {

                item = ItemUtils.rename(head.ALLAY, config.getString("ALLAY.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

        } else if (type == EntityType.FROG) {
            if (config.getBoolean("FROG.Drop") && x <= config.getInt("FROG.Chance")) {
                Frog frog = (Frog) entity;
                switch (frog.getVariant()) {
                    case TEMPERATE:
                        item = ItemUtils.rename(head.FROG_TEMPERATE, config.getString("FROG.Name"));
                        event.getDrops().add(item);

                        break;
                    case WARM:
                        item = ItemUtils.rename(head.FROG_WARM, config.getString("FROG.Name"));
                        event.getDrops().add(item);

                        break;
                    case COLD:
                        item = ItemUtils.rename(head.FROG_COLD, config.getString("FROG.Name"));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TADPOLE) {
            if (config.getBoolean("TADPOLE.Drop") && x <= config.getInt("TADPOLE.Chance")) {

                item = ItemUtils.rename(head.TADPOLE, config.getString("TADPOLE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

        } else if (type == EntityType.WARDEN) {
            if (config.getBoolean("WARDEN.Drop") && x <= config.getInt("WARDEN.Chance")) {

                item = ItemUtils.rename(head.WARDEN, config.getString("WARDEN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }
    }
}