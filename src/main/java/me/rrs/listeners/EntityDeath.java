package me.rrs.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.database.EntityHead;
import me.rrs.util.Embed;
import me.rrs.util.ItemUtils;
import me.rrs.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;


public class EntityDeath implements Listener {

    final Random random = new Random();
    final YamlDocument config = HeadDrop.getConfiguration();
    String title, description, footer, killer = "%killer%", mob = "%mob%";

    @EventHandler(priority = EventPriority.HIGH)
    public void EntityDropHeadEvent(final EntityDeathEvent event) {

        boolean killerExist = false;
        final LivingEntity entity = event.getEntity();
        if (entity.getKiller() != null){
            killerExist = true;
        }
        if (config.getBoolean("Bot.Enable")) {
            if (killerExist) {
                title = config.getString("Bot.Title")
                        .replace(killer, entity.getKiller().getName())
                        .replace(mob, entity.getName());

                description = config.getString("Bot.Description")
                        .replace(killer, entity.getKiller().getName())
                        .replace(mob, entity.getName());

                footer = config.getString("Bot.Footer")
                        .replace(killer, entity.getKiller().getName())
                        .replace(mob, entity.getName());

                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    title = PlaceholderAPI.setPlaceholders(entity.getKiller(), title);
                    description = PlaceholderAPI.setPlaceholders(entity.getKiller(), description);
                    footer = PlaceholderAPI.setPlaceholders(entity.getKiller(), footer);
                }
            }
        }

        int lootLvl = 0;
        if (killerExist){
            ItemStack item = entity.getKiller().getInventory().getItemInMainHand();
            if (item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)){
                lootLvl = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
            }
        }


        if (!entity.getPersistentDataContainer().getKeys().isEmpty()) return;
        if (Boolean.TRUE.equals(config.getBoolean("Config.Require-Killer-Player") && (entity.getKiller() == null))) return;
        if (Boolean.TRUE.equals(config.getBoolean("Config.Killer-Require-Permission"))) {
            if (entity.getKiller() == null) return;
            if (!entity.getKiller().hasPermission("headdrop.killer")) return;
        }

        List<String> worldList = config.getStringList("Config.Disable-Worlds");
        for (String world : worldList) {
            if (entity.getWorld().getName().equalsIgnoreCase(world)) return;
        }

        EntityHead head = new EntityHead();
        EntityType type = entity.getType();

        int x = this.random.nextInt(100) + 1;
        ItemStack item;

        if (type == EntityType.PLAYER) {
            if (config.getBoolean("PLAYER.Require-Permission") && entity.hasPermission("headdrop.player")){
                if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance") + lootLvl) {
                    ItemStack skull = SkullCreator.itemFromName(entity.getName());
                    event.getDrops().add(skull);
                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
            }
        }else if (type == EntityType.BAT) {
            if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance") + lootLvl) {
                item = ItemUtils.rename(head.BAT, config.getString("BAT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if(type == EntityType.ZOMBIE){
            if (config.getBoolean("ZOMBIE.Drop") && x <= config.getInt("ZOMBIE.Chance") + lootLvl) {
                event.getDrops().add(new ItemStack(Material.ZOMBIE_HEAD));

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if(type == EntityType.WITHER_SKELETON){
                if (config.getBoolean("WITHER_SKELETON.Drop") && x <= config.getInt("WITHER_SKELETON.Chance") + lootLvl) {
                    event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL));

                    if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                }
        }else if(type == EntityType.CREEPER){
            if (config.getBoolean("CREEPER.Drop") && x <= config.getInt("CREEPER.Chance") + lootLvl) {
                event.getDrops().add(new ItemStack(Material.CREEPER_HEAD));

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if(type == EntityType.SKELETON){
            if (config.getBoolean("SKELETON.Drop") && x <= config.getInt("SKELETON.Chance") + lootLvl) {
                event.getDrops().add(new ItemStack(Material.SKELETON_SKULL));

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if (type == EntityType.BLAZE) {
            if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.BLAZE, config.getString("BLAZE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if (type == EntityType.SPIDER) {
            if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SPIDER, config.getString("SPIDER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }else if (type == EntityType.CAVE_SPIDER) {
            if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.CAVE_SPIDER, config.getString("CAVE_SPIDER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CHICKEN) {
            if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance") + lootLvl) {
                item = ItemUtils.rename(head.CHICKEN, config.getString("CHICKEN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COW) {
            if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance") + lootLvl) {
                item = ItemUtils.rename(head.COW, config.getString("COW.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMAN) {
            if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance") + lootLvl) {
                item = ItemUtils.rename(head.ENDERMAN, config.getString("ENDERMAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GIANT) {
            if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance") + lootLvl) {
                item = ItemUtils.rename(head.GIANT, config.getString("GIANT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HORSE) {
            if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance") + lootLvl) {
                Horse horse = (Horse) entity;

                String name = "HORSE.Name";
                switch (horse.getColor()) {
                    case WHITE:
                        item = ItemUtils.rename(head.HORSE_WHITE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CREAMY:
                        item = ItemUtils.rename(head.HORSE_CREAMY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CHESTNUT:
                        item = ItemUtils.rename(head.HORSE_CHESTNUT, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BROWN:
                        item = ItemUtils.rename(head.HORSE_BROWN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.HORSE_BLACK, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.HORSE_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case DARK_BROWN:
                        item = ItemUtils.rename(head.HORSE_DARK_BROWN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ILLUSIONER) {
            if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.ILLUSIONER, config.getString("ILLUSIONER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.IRON_GOLEM) {
            if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance") + lootLvl) {
                item = ItemUtils.rename(head.IRON_GOLEM, config.getString("IRON_GOLEM.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MAGMA_CUBE) {
            if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.MAGMA_CUBE, config.getString("MAGMA_CUBE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MUSHROOM_COW) {
            if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance") + lootLvl) {
                MushroomCow mushroomCow = (MushroomCow) entity;
                if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)){
                    item = ItemUtils.rename(head.MUSHROOM_COW_RED, config.getString("MUSHROOM_COW.Name"));
                    event.getDrops().add(item);
                } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                    item = ItemUtils.rename(head.MUSHROOM_COW_BROWN, config.getString("MUSHROOM_COW.Name"));
                    event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.OCELOT) {
            if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance") + lootLvl) {
                item = ItemUtils.rename(head.OCELOT, config.getString("OCELOT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIG) {
            if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PIG, config.getString("PIG.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SHEEP) {
            if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance") + lootLvl) {
                Sheep sheep = (Sheep) entity;
                String name = "SHEEP.Name";

                switch (sheep.getColor()) {

                    case WHITE:
                        item = ItemUtils.rename(head.SHEEP_WHITE, config.getString(name));
                        event.getDrops().add(item);
                        break;

                    case ORANGE:
                        item = ItemUtils.rename(head.SHEEP_ORANGE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case MAGENTA:
                        item = ItemUtils.rename(head.SHEEP_MAGENTA, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_BLUE:
                        item = ItemUtils.rename(head.SHEEP_LIGHT_BLUE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case YELLOW:
                        item = ItemUtils.rename(head.SHEEP_YELLOW, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIME:
                        item = ItemUtils.rename(head.SHEEP_LIME, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case PINK:
                        item = ItemUtils.rename(head.SHEEP_PINK, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.SHEEP_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_GRAY:
                        item = ItemUtils.rename(head.SHEEP_LIGHT_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.SHEEP_CYAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case PURPLE:
                        item = ItemUtils.rename(head.SHEEP_PURPLE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLUE:
                        item = ItemUtils.rename(head.SHEEP_BLUE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BROWN:
                        item = ItemUtils.rename(head.SHEEP_BROWN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.SHEEP_GREEN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.SHEEP_RED, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.SHEEP_BLACK, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    default:
                        Bukkit.getLogger().severe("If you notice this error, pls report it to plugin author");
                        throw new IllegalStateException("Unexpected value: " + sheep.getColor());
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SILVERFISH) {
            if (config.getBoolean("SILVERFISH.Drop") && x <= config.getInt("SILVERFISH.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SILVERFISH, config.getString("SILVERFISH.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SLIME) {
            if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SLIME, config.getString("SLIME.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SNOWMAN) {
            if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SNOWMAN, config.getString("SNOW_GOLEM.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SQUID) {
            if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SQUID, config.getString("SQUID.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITCH) {
            if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance") + lootLvl) {
                item = ItemUtils.rename(head.WITCH, config.getString("WITCH.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITHER) {
            if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.WITHER, config.getString("WITHER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
            if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance") + lootLvl) {
                item = ItemUtils.rename(head.ZOMBIFIED_PIGLIN, config.getString("ZOMBIFIED_PIGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GHAST) {
            if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance") + lootLvl) {
                item = ItemUtils.rename(head.GHAST, config.getString("GHAST.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WOLF) {
            if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance") + lootLvl) {
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
            if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance") + lootLvl) {
                Villager villager = (Villager) entity;

                String name = "VILLAGER.Name";
                switch (villager.getProfession()) {
                    case WEAPONSMITH:
                        item = ItemUtils.rename(head.VILLAGER_WEAPONSMITH, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case SHEPHERD:
                        item = ItemUtils.rename(head.VILLAGER_SHEPHERD, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIBRARIAN:
                        item = ItemUtils.rename(head.VILLAGER_LIBRARIAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FLETCHER:
                        item = ItemUtils.rename(head.VILLAGER_FLETCHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FISHERMAN:
                        item = ItemUtils.rename(head.VILLAGER_FISHERMAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FARMER:
                        item = ItemUtils.rename(head.VILLAGER_FARMER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CLERIC:
                        item = ItemUtils.rename(head.VILLAGER_CLERIC, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CARTOGRAPHER:
                        item = ItemUtils.rename(head.VILLAGER_CARTOGRAPHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BUTCHER:
                        item = ItemUtils.rename(head.VILLAGER_BUTCHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case ARMORER:
                        item = ItemUtils.rename(head.VILLAGER_ARMORER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    default:
                        item = ItemUtils.rename(head.VILLAGER_NULL, config.getString(name));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.8 Mob
        } else if (type == EntityType.RABBIT) {
            if (config.getBoolean("RABBIT.Drop") && x <= config.getInt("RABBIT.Chance") + lootLvl) {
                Rabbit rabbit = (Rabbit) entity;

                String name = "RABBIT.Name";
                switch (rabbit.getRabbitType()) {

                    case BROWN:
                        item = ItemUtils.rename(head.RABBIT_BROWN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.RABBIT_WHITE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.RABBIT_BLACK, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLACK_AND_WHITE:
                        item = ItemUtils.rename(head.RABBIT_BLACK_AND_WHITE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GOLD:
                        item = ItemUtils.rename(head.RABBIT_GOLD, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case SALT_AND_PEPPER:
                        item = ItemUtils.rename(head.RABBIT_SALT_AND_PEPPER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case THE_KILLER_BUNNY:
                        item = ItemUtils.rename(head.RABBIT_THE_KILLER_BUNNY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMITE) {
            if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance") + lootLvl) {

                item = ItemUtils.rename(head.ENDERMITE, config.getString("ENDERMITE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GUARDIAN) {
            if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance") + lootLvl) {

                item = ItemUtils.rename(head.GUARDIAN, config.getString("GUARDIAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.9 Mob
        } else if (type == EntityType.SHULKER) {
            if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SHULKER, config.getString("SHULKER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.10 Mob
        } else if (type == EntityType.POLAR_BEAR) {
            if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance") + lootLvl) {
                item = ItemUtils.rename(head.POLAR_BEAR, config.getString("POLAR_BEAR.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.11 Mob
        } else if (type == EntityType.ZOMBIE_VILLAGER) {
            if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance") + lootLvl) {
                ZombieVillager zombieVillager = (ZombieVillager) entity;

                String name = "ZOMBIE_VILLAGER.Name";
                switch (zombieVillager.getVillagerProfession()) {
                    case ARMORER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_ARMORER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BUTCHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_BUTCHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CARTOGRAPHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_CARTOGRAPHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CLERIC:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_CLERIC, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FARMER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FARMER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FISHERMAN:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FISHERMAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case FLETCHER:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_FLETCHER, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIBRARIAN:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_LIBRARIAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case SHEPHERD:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_SHEPHERD, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case WEAPONSMITH:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_WEAPONSMITH, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    default:
                        item = ItemUtils.rename(head.ZOMBIE_VILLAGER_NULL, config.getString(name));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VINDICATOR) {
            if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance") + lootLvl) {
                item = ItemUtils.rename(head.VINDICATOR, config.getString("VINDICATOR.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VEX) {
            if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance") + lootLvl) {

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
            if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance") + lootLvl) {

                item = ItemUtils.rename(head.EVOKER, config.getString("EVOKER.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HUSK) {
            if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance") + lootLvl) {
                item = ItemUtils.rename(head.HUSK, config.getString("HUSK.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRAY) {
            if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance") + lootLvl) {
                item = ItemUtils.rename(head.STRAY, config.getString("STRAY.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ELDER_GUARDIAN) {
            if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance") + lootLvl) {

                item = ItemUtils.rename(head.ELDER_GUARDIAN, config.getString("ELDER_GUARDIAN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DONKEY) {
            if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance") + lootLvl) {

                item = ItemUtils.rename(head.DONKEY, config.getString("DONKEY.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIE_HORSE) {
            if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.ZOMBIE_HORSE, config.getString("ZOMBIE_HORSE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SKELETON_HORSE) {
            if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SKELETON_HORSE, config.getString("SKELETON_HORSE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MULE) {
            if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.MULE, config.getString("MULE.Name"));


                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
            //1.12 Mob
        } else if (type == EntityType.PARROT) {
            if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance") + lootLvl) {
                Parrot parrot = (Parrot) entity;
                String name = "PARROT.Name";
                        switch (parrot.getVariant()) {
                    case BLUE:
                        item = ItemUtils.rename(head.PARROT_BLUE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.PARROT_CYAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.PARROT_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.PARROT_RED, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.PARROT_GREEN, config.getString(name));
                        event.getDrops().add(item);
                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.13 Mob
        } else if (type == EntityType.TROPICAL_FISH) {
            if (config.getBoolean("TROPICAL_FISH.Drop") && x <= config.getInt("TROPICAL_FISH.Chance") + lootLvl) {

                TropicalFish tropicalFish = (TropicalFish) entity;

                String name = "TROPICAL_FISH.Name";
                        switch (tropicalFish.getBodyColor()) {
                    case MAGENTA:
                        item = ItemUtils.rename(head.TROPICAL_FISH_MAGENTA, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_BLUE:
                        item = ItemUtils.rename(head.TROPICAL_FISH_LIGHT_BLUE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case YELLOW:
                        item = ItemUtils.rename(head.TROPICAL_FISH_YELLOW, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case PINK:
                        item = ItemUtils.rename(head.TROPICAL_FISH_PINK, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.TROPICAL_FISH_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case LIGHT_GRAY:
                        item = ItemUtils.rename(head.TROPICAL_FISH_LIGHT_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CYAN:
                        item = ItemUtils.rename(head.TROPICAL_FISH_CYAN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLUE:
                        item = ItemUtils.rename(head.TROPICAL_FISH_BLUE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GREEN:
                        item = ItemUtils.rename(head.TROPICAL_FISH_GREEN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case RED:
                        item = ItemUtils.rename(head.TROPICAL_FISH_RED, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case BLACK:
                        item = ItemUtils.rename(head.TROPICAL_FISH_BLACK, config.getString(name));
                        event.getDrops().add(item);

                        break;

                    default:
                        item = ItemUtils.rename(head.TROPICAL_FISH_ORANGE, config.getString(name));

                        event.getDrops().add(item);
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PUFFERFISH) {
            if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PUFFERFISH, config.getString("PUFFERFISH.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SALMON) {
            if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance") + lootLvl) {
                item = ItemUtils.rename(head.SALMON, config.getString("SALMON.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COD) {
            if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance") + lootLvl) {

                item = ItemUtils.rename(head.COD, config.getString("COD.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TURTLE) {
            if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.TURTLE, config.getString("TURTLE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DOLPHIN) {
            if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance") + lootLvl) {

                item = ItemUtils.rename(head.DOLPHIN, config.getString("DOLPHIN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PHANTOM) {
            if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PHANTOM, config.getString("PHANTOM.Name"));


                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DROWNED) {
            if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance") + lootLvl) {

                item = ItemUtils.rename(head.DROWNED, config.getString("DROWNED.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            //1.14 Mob
        } else if (type == EntityType.WANDERING_TRADER) {
            if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.WANDERING_TRADER, config.getString("WANDERING_TRADER.Name"));


                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TRADER_LLAMA) {
            if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                TraderLlama traderLlama = (TraderLlama) entity;

                String name = "TRADER_LLAMA.Name";
                switch (traderLlama.getColor()) {
                    case BROWN:
                        item = ItemUtils.rename(head.TRADER_LLAMA_BROWN, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case WHITE:
                        item = ItemUtils.rename(head.TRADER_LLAMA_WHITE, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case GRAY:
                        item = ItemUtils.rename(head.TRADER_LLAMA_GRAY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                    case CREAMY:
                        item = ItemUtils.rename(head.TRADER_LLAMA_CREAMY, config.getString(name));
                        event.getDrops().add(item);

                        break;
                }
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.LLAMA) {
            if (config.getBoolean("LLAMA.Drop") && x <= config.getInt("LLAMA.Chance") + lootLvl) {
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
            if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.RAVAGER, config.getString("RAVAGER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PILLAGER) {
            if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PILLAGER, config.getString("PILLAGER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PANDA) {
            if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance") + lootLvl) {
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
            if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance") + lootLvl) {
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
            if (config.getBoolean("CAT.Drop") && x <= config.getInt("CAT.Chance") + lootLvl) {
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
            if (config.getBoolean("BEE.Drop") && x <= config.getInt("BEE.Chance") + lootLvl) {
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
            if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance") + lootLvl) {
                item = ItemUtils.rename(head.ZOGLIN, config.getString("ZOGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRIDER) {
            if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance") + lootLvl) {
                item = ItemUtils.rename(head.STRIDER, config.getString("STRIDER.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN) {
            if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PIGLIN, config.getString("PIGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HOGLIN) {
            if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance") + lootLvl) {

                item = ItemUtils.rename(head.HOGLIN, config.getString("HOGLIN.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN_BRUTE) {
            if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance") + lootLvl) {
                item = ItemUtils.rename(head.PIGLIN_BRUTE, config.getString("PIGLIN_BRUTE.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

            // 1.17 Mob
        } else if (type == EntityType.GLOW_SQUID) {
            if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance") + lootLvl) {

                item = ItemUtils.rename(head.GLOW_SQUID, config.getString("GLOW_SQUID.Name"));
                event.getDrops().add(item);
                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GOAT) {
            if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance") + lootLvl) {
                item = ItemUtils.rename(head.GOAT, config.getString("GOAT.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        } else if (type == EntityType.AXOLOTL) {
            if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance") + lootLvl) {
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
            if (config.getBoolean("ALLAY.Drop") && x <= config.getInt("ALLAY.Chance") + lootLvl) {

                item = ItemUtils.rename(head.ALLAY, config.getString("ALLAY.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

        } else if (type == EntityType.FROG) {
            if (config.getBoolean("FROG.Drop") && x <= config.getInt("FROG.Chance") + lootLvl) {
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
            if (config.getBoolean("TADPOLE.Drop") && x <= config.getInt("TADPOLE.Chance") + lootLvl) {

                item = ItemUtils.rename(head.TADPOLE, config.getString("TADPOLE.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }

        } else if (type == EntityType.WARDEN) {
            if (config.getBoolean("WARDEN.Drop") && x <= config.getInt("WARDEN.Chance") + lootLvl) {

                item = ItemUtils.rename(head.WARDEN, config.getString("WARDEN.Name"));
                event.getDrops().add(item);

                if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
            }
        }
    }
}