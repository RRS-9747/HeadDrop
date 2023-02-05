package me.rrs.headdrop.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.database.EntityHead;
import me.rrs.headdrop.util.ItemUtils;
import me.rrs.headdrop.util.Embed;
import me.rrs.headdrop.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;


public class EntityDeath implements Listener {

    final Random random = new Random();
    final YamlDocument config = HeadDrop.getConfiguration();
    String description, footer, title;
    ItemUtils utils = new ItemUtils();
    Embed embed = new Embed();
    PersistentDataContainer container;

    private void updatePDC(Player player){
        container = player.getPersistentDataContainer();
        int currentValue = container.get(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER);
        int newValue = currentValue + 1;
        container.set(new NamespacedKey(HeadDrop.getInstance(), "HeadDrop"), PersistentDataType.INTEGER, newValue);
    }



    @EventHandler(priority = EventPriority.HIGH)
    public void entityDropHeadEvent(final EntityDeathEvent event) {

        final LivingEntity entity = event.getEntity();
        boolean killerExist = entity.getKiller() != null;


        if (config.getBoolean("Bot.Enable") && killerExist) {
            String killerName = entity.getKiller().getName();
            String mobName = entity.getName();

            title = config.getString("Bot.Title").replace("%killer%", killerName).replace("%mob%", mobName);
            description = config.getString("Bot.Description").replace("%killer%", killerName).replace("%mob%", mobName);
            footer = config.getString("Bot.Footer").replace("%killer%", killerName).replace("%mob%", mobName);

            boolean placeholdersEnabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

            title = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), title) : title;
            description = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), description) : description;
            footer = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), footer) : footer;
        }

        int lootLvl = killerExist && entity.getKiller().getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS) ?
                entity.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) : 0;


        if (!entity.getPersistentDataContainer().getKeys().isEmpty() && entity.getType() != EntityType.PLAYER) return;

        if (config.getBoolean("Config.Require-Killer-Player") && entity.getKiller() == null) return;

        if (config.getBoolean("Config.Killer-Require-Permission")) {
            if (entity.getKiller() == null || !entity.getKiller().hasPermission("headdrop.killer")) return;
        }

        List<String> worldList = config.getStringList("Config.Disable-Worlds");
        if (worldList.contains(entity.getWorld().getName())) return;

        EntityType type = entity.getType();

        int x = this.random.nextInt(100) + 1;
        ItemStack item;


        if (type.equals(EntityType.valueOf("PLAYER"))) {
            if ((config.getBoolean("PLAYER.Require-Permission")) && !entity.hasPermission("headdrop.player")){
                return;
            }
            if ((config.getBoolean("PLAYER.Drop")) && x <= config.getInt("PLAYER.Chance") + lootLvl) {
                ItemStack skull = SkullCreator.createSkullWithName(entity.getName());
                event.getDrops().add(skull);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
                }
        }else if (type == EntityType.BAT) {
            if ((config.getBoolean("BAT.Drop")) && x <= config.getInt("BAT.Chance") + lootLvl) {
                item = utils.rename(EntityHead.BAT.getItemStack(), config.getString("BAT.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if (type == EntityType.ENDER_DRAGON) {
            if ((config.getBoolean("ENDER_DRAGON.Drop")) && x <= config.getInt("ENDER_DRAGON.Chance") + lootLvl) {
                event.getDrops().add(new ItemStack(Material.DRAGON_HEAD));
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if(type == EntityType.ZOMBIE){
            if ((config.getBoolean("ZOMBIE.Drop")) && x <= config.getInt("ZOMBIE.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);
                event.getDrops().add(new ItemStack(Material.ZOMBIE_HEAD));
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if(type == EntityType.WITHER_SKELETON){
                if ((config.getBoolean("WITHER_SKELETON.Drop")) && x <= config.getInt("WITHER_SKELETON.Chance") + lootLvl) {
                    event.getDrops().removeIf(head -> head.getType() == Material.WITHER_SKELETON_SKULL);
                    event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL));
                    if (killerExist){
                        updatePDC(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
                }
        }else if(type == EntityType.CREEPER){
            if ((config.getBoolean("CREEPER.Drop")) && x <= config.getInt("CREEPER.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);
                event.getDrops().add(new ItemStack(Material.CREEPER_HEAD));
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if(type == EntityType.SKELETON){
            if ((config.getBoolean("SKELETON.Drop")) && x <= config.getInt("SKELETON.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.SKELETON_SKULL);
                event.getDrops().add(new ItemStack(Material.SKELETON_SKULL));
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if (type == EntityType.BLAZE) {
            if ((config.getBoolean("BLAZE.Drop")) && x <= config.getInt("BLAZE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.BLAZE.getItemStack(), config.getString("BLAZE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if (type == EntityType.SPIDER) {
            if ((config.getBoolean("SPIDER.Drop")) && x <= config.getInt("SPIDER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SPIDER.getItemStack(), config.getString("SPIDER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }else if (type == EntityType.CAVE_SPIDER) {
            if ((config.getBoolean("CAVE_SPIDER.Drop")) && x <= config.getInt("CAVE_SPIDER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.CAVE_SPIDER.getItemStack(), config.getString("CAVE_SPIDER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CHICKEN) {
            if ((config.getBoolean("CHICKEN.Drop")) && x <= config.getInt("CHICKEN.Chance") + lootLvl) {
                item = utils.rename(EntityHead.CHICKEN.getItemStack(), config.getString("CHICKEN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COW) {
            if ((config.getBoolean("COW.Drop")) && x <= config.getInt("COW.Chance") + lootLvl) {
                item = utils.rename(EntityHead.COW.getItemStack(), config.getString("COW.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMAN) {
            if ((config.getBoolean("ENDERMAN.Drop")) && x <= config.getInt("ENDERMAN.Chance") + lootLvl) {
                item = utils.rename(EntityHead.ENDERMAN.getItemStack(), config.getString("ENDERMAN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GIANT) {
            if ((config.getBoolean("GIANT.Drop")) && x <= config.getInt("GIANT.Chance") + lootLvl) {
                item = utils.rename(EntityHead.GIANT.getItemStack(), config.getString("GIANT.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HORSE) {
            if ((config.getBoolean("HORSE.Drop")) && x <= config.getInt("HORSE.Chance") + lootLvl) {
                Horse horse = (Horse) entity;

                String name = "HORSE.Name";
                switch (horse.getColor()) {
                    case WHITE:
                        item = utils.rename(EntityHead.HORSE_WHITE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CREAMY:
                        item = utils.rename(EntityHead.HORSE_CREAMY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CHESTNUT:
                        item = utils.rename(EntityHead.HORSE_CHESTNUT.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BROWN:
                        item = utils.rename(EntityHead.HORSE_BROWN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLACK:
                        item = utils.rename(EntityHead.HORSE_BLACK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.HORSE_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case DARK_BROWN:
                        item = utils.rename(EntityHead.HORSE_DARK_BROWN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ILLUSIONER) {
            if ((config.getBoolean("ILLUSIONER.Drop")) && x <= config.getInt("ILLUSIONER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.ILLUSIONER.getItemStack(), config.getString("ILLUSIONER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.IRON_GOLEM) {
            if ((config.getBoolean("IRON_GOLEM.Drop")) && x <= config.getInt("IRON_GOLEM.Chance") + lootLvl) {
                item = utils.rename(EntityHead.IRON_GOLEM.getItemStack(), config.getString("IRON_GOLEM.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MAGMA_CUBE) {
            if ((config.getBoolean("MAGMA_CUBE.Drop")) && x <= config.getInt("MAGMA_CUBE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.MAGMA_CUBE.getItemStack(), config.getString("MAGMA_CUBE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MUSHROOM_COW) {
            if ((config.getBoolean("MUSHROOM_COW.Drop")) && x <= config.getInt("MUSHROOM_COW.Chance") + lootLvl) {
                MushroomCow mushroomCow = (MushroomCow) entity;
                if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)){
                    item = utils.rename(EntityHead.MUSHROOM_COW_RED.getItemStack(), config.getString("MUSHROOM_COW.Name"));
                    event.getDrops().add(item);
                    if (killerExist){
                        updatePDC(entity.getKiller());
                    }
                } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                    item = utils.rename(EntityHead.MUSHROOM_COW_BROWN.getItemStack(), config.getString("MUSHROOM_COW.Name"));
                    event.getDrops().add(item);
                    if (killerExist){
                        updatePDC(entity.getKiller());
                    }
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.OCELOT) {
            if ((config.getBoolean("OCELOT.Drop")) && x <= config.getInt("OCELOT.Chance") + lootLvl) {
                item = utils.rename(EntityHead.OCELOT.getItemStack(), config.getString("OCELOT.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIG) {
            if ((config.getBoolean("PIG.Drop")) && x <= config.getInt("PIG.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PIG.getItemStack(), config.getString("PIG.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SHEEP) {
            if ((config.getBoolean("SHEEP.Drop")) && x <= config.getInt("SHEEP.Chance") + lootLvl) {
                Sheep sheep = (Sheep) entity;
                String name = "SHEEP.Name";

                switch (sheep.getColor()) {

                    case WHITE:
                        item = utils.rename(EntityHead.SHEEP_WHITE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;

                    case ORANGE:
                        item = utils.rename(EntityHead.SHEEP_ORANGE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case MAGENTA:
                        item = utils.rename(EntityHead.SHEEP_MAGENTA.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIGHT_BLUE:
                        item = utils.rename(EntityHead.SHEEP_LIGHT_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case YELLOW:
                        item = utils.rename(EntityHead.SHEEP_YELLOW.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIME:
                        item = utils.rename(EntityHead.SHEEP_LIME.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case PINK:
                        item = utils.rename(EntityHead.SHEEP_PINK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.SHEEP_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIGHT_GRAY:
                        item = utils.rename(EntityHead.SHEEP_LIGHT_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CYAN:
                        item = utils.rename(EntityHead.SHEEP_CYAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case PURPLE:
                        item = utils.rename(EntityHead.SHEEP_PURPLE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLUE:
                        item = utils.rename(EntityHead.SHEEP_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BROWN:
                        item = utils.rename(EntityHead.SHEEP_BROWN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GREEN:
                        item = utils.rename(EntityHead.SHEEP_GREEN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case RED:
                        item = utils.rename(EntityHead.SHEEP_RED.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLACK:
                        item = utils.rename(EntityHead.SHEEP_BLACK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    default:
                        Bukkit.getLogger().severe("If you notice this error, pls report it to plugin author");
                        throw new IllegalStateException("Unexpected value: " + sheep.getColor());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SILVERFISH) {
            if ((config.getBoolean("SILVERFISH.Drop")) && x <= config.getInt("SILVERFISH.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SILVERFISH.getItemStack(), config.getString("SILVERFISH.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SLIME) {
            if ((config.getBoolean("SLIME.Drop")) && x <= config.getInt("SLIME.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SLIME.getItemStack(), config.getString("SLIME.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SNOWMAN) {
            if ((config.getBoolean("SNOW_GOLEM.Drop")) && x <= config.getInt("SNOW_GOLEM.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SNOWMAN.getItemStack(), config.getString("SNOW_GOLEM.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SQUID) {
            if ((config.getBoolean("SQUID.Drop")) && x <= config.getInt("SQUID.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SQUID.getItemStack(), config.getString("SQUID.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITCH) {
            if ((config.getBoolean("WITCH.Drop")) && x <= config.getInt("WITCH.Chance") + lootLvl) {
                item = utils.rename(EntityHead.WITCH.getItemStack(), config.getString("WITCH.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WITHER) {
            if ((config.getBoolean("WITHER.Drop")) && x <= config.getInt("WITHER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.WITHER.getItemStack(), config.getString("WITHER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
            if ((config.getBoolean("ZOMBIFIED_PIGLIN.Drop")) && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance") + lootLvl) {
                item = utils.rename(EntityHead.ZOMBIFIED_PIGLIN.getItemStack(), config.getString("ZOMBIFIED_PIGLIN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GHAST) {
            if ((config.getBoolean("GHAST.Drop")) && x <= config.getInt("GHAST.Chance") + lootLvl) {
                item = utils.rename(EntityHead.GHAST.getItemStack(), config.getString("GHAST.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.WOLF) {
            if ((config.getBoolean("WOLF.Drop")) && x <= config.getInt("WOLF.Chance") + lootLvl) {
                Wolf wolf = (Wolf) entity;

                if (wolf.isAngry()) {
                    item = utils.rename(EntityHead.WOLF_ANGRY.getItemStack(), config.getString("WOLF.Name"));
                } else {
                    item = utils.rename(EntityHead.WOLF.getItemStack(), config.getString("WOLF.Name"));
                }
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VILLAGER) {
            if ((config.getBoolean("VILLAGER.Drop")) && x <= config.getInt("VILLAGER.Chance") + lootLvl) {
                Villager villager = (Villager) entity;

                String name = "VILLAGER.Name";
                switch (villager.getProfession()) {
                    case WEAPONSMITH:
                        item = utils.rename(EntityHead.VILLAGER_WEAPONSMITH.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case SHEPHERD:
                        item = utils.rename(EntityHead.VILLAGER_SHEPHERD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIBRARIAN:
                        item = utils.rename(EntityHead.VILLAGER_LIBRARIAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FLETCHER:
                        item = utils.rename(EntityHead.VILLAGER_FLETCHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FISHERMAN:
                        item = utils.rename(EntityHead.VILLAGER_FISHERMAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FARMER:
                        item = utils.rename(EntityHead.VILLAGER_FARMER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CLERIC:
                        item = utils.rename(EntityHead.VILLAGER_CLERIC.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CARTOGRAPHER:
                        item = utils.rename(EntityHead.VILLAGER_CARTOGRAPHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BUTCHER:
                        item = utils.rename(EntityHead.VILLAGER_BUTCHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case ARMORER:
                        item = utils.rename(EntityHead.VILLAGER_ARMORER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    default:
                        item = utils.rename(EntityHead.VILLAGER_NULL.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.8 Mob
        } else if (type == EntityType.RABBIT) {
            if ((config.getBoolean("RABBIT.Drop")) && x <= config.getInt("RABBIT.Chance") + lootLvl) {
                Rabbit rabbit = (Rabbit) entity;

                String name = "RABBIT.Name";
                switch (rabbit.getRabbitType()) {

                    case BROWN:
                        item = utils.rename(EntityHead.RABBIT_BROWN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WHITE:
                        item = utils.rename(EntityHead.RABBIT_WHITE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLACK:
                        item = utils.rename(EntityHead.RABBIT_BLACK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLACK_AND_WHITE:
                        item = utils.rename(EntityHead.RABBIT_BLACK_AND_WHITE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GOLD:
                        item = utils.rename(EntityHead.RABBIT_GOLD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case SALT_AND_PEPPER:
                        item = utils.rename(EntityHead.RABBIT_SALT_AND_PEPPER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case THE_KILLER_BUNNY:
                        item = utils.rename(EntityHead.RABBIT_THE_KILLER_BUNNY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ENDERMITE) {
            if ((config.getBoolean("ENDERMITE.Drop")) && x <= config.getInt("ENDERMITE.Chance") + lootLvl) {

                item = utils.rename(EntityHead.ENDERMITE.getItemStack(), config.getString("ENDERMITE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GUARDIAN) {
            if ((config.getBoolean("GUARDIAN.Drop")) && x <= config.getInt("GUARDIAN.Chance") + lootLvl) {

                item = utils.rename(EntityHead.GUARDIAN.getItemStack(), config.getString("GUARDIAN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.9 Mob
        } else if (type == EntityType.SHULKER) {
            if ((config.getBoolean("SHULKER.Drop")) && x <= config.getInt("SHULKER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SHULKER.getItemStack(), config.getString("SHULKER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
            //1.10 Mob
        } else if (type == EntityType.POLAR_BEAR) {
            if ((config.getBoolean("POLAR_BEAR.Drop")) && x <= config.getInt("POLAR_BEAR.Chance") + lootLvl) {
                item = utils.rename(EntityHead.POLAR_BEAR.getItemStack(), config.getString("POLAR_BEAR.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
            //1.11 Mob
        } else if (type == EntityType.ZOMBIE_VILLAGER) {
            if ((config.getBoolean("ZOMBIE_VILLAGER.Drop")) && x <= config.getInt("ZOMBIE_VILLAGER.Chance") + lootLvl) {
                ZombieVillager zombieVillager = (ZombieVillager) entity;

                String name = "ZOMBIE_VILLAGER.Name";
                switch (zombieVillager.getVillagerProfession()) {
                    case ARMORER:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_ARMORER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BUTCHER:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_BUTCHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CARTOGRAPHER:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CLERIC:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_CLERIC.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FARMER:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_FARMER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FISHERMAN:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_FISHERMAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case FLETCHER:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_FLETCHER.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIBRARIAN:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_LIBRARIAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case SHEPHERD:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_SHEPHERD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WEAPONSMITH:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    default:
                        item = utils.rename(EntityHead.ZOMBIE_VILLAGER_NULL.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VINDICATOR) {
            if ((config.getBoolean("VINDICATOR.Drop")) && x <= config.getInt("VINDICATOR.Chance") + lootLvl) {
                item = utils.rename(EntityHead.VINDICATOR.getItemStack(), config.getString("VINDICATOR.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.VEX) {
            if ((config.getBoolean("VEX.Drop")) && x <= config.getInt("VEX.Chance") + lootLvl) {

                Vex vex = (Vex) entity;
                if (vex.isCharging()) {
                    item = utils.rename(EntityHead.VEX_CHARGE.getItemStack(), config.getString("VEX.Name"));

                } else {
                    item = utils.rename(EntityHead.VEX.getItemStack(), config.getString("VEX.Name"));
                }
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.EVOKER) {
            if ((config.getBoolean("EVOKER.Drop")) && x <= config.getInt("EVOKER.Chance") + lootLvl) {

                item = utils.rename(EntityHead.EVOKER.getItemStack(), config.getString("EVOKER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HUSK) {
            if ((config.getBoolean("HUSK.Drop")) && x <= config.getInt("HUSK.Chance") + lootLvl) {
                item = utils.rename(EntityHead.HUSK.getItemStack(), config.getString("HUSK.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRAY) {
            if ((config.getBoolean("STRAY.Drop")) && x <= config.getInt("STRAY.Chance") + lootLvl) {
                item = utils.rename(EntityHead.STRAY.getItemStack(), config.getString("STRAY.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ELDER_GUARDIAN) {
            if ((config.getBoolean("ELDER_GUARDIAN.Drop")) && x <= config.getInt("ELDER_GUARDIAN.Chance") + lootLvl) {

                item = utils.rename(EntityHead.ELDER_GUARDIAN.getItemStack(), config.getString("ELDER_GUARDIAN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DONKEY) {
            if ((config.getBoolean("DONKEY.Drop")) && x <= config.getInt("DONKEY.Chance") + lootLvl) {

                item = utils.rename(EntityHead.DONKEY.getItemStack(), config.getString("DONKEY.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.ZOMBIE_HORSE) {
            if ((config.getBoolean("ZOMBIE_HORSE.Drop")) && x <= config.getInt("ZOMBIE_HORSE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.ZOMBIE_HORSE.getItemStack(), config.getString("ZOMBIE_HORSE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SKELETON_HORSE) {
            if ((config.getBoolean("SKELETON_HORSE.Drop")) && x <= config.getInt("SKELETON_HORSE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SKELETON_HORSE.getItemStack(), config.getString("SKELETON_HORSE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.MULE) {
            if ((config.getBoolean("MULE.Drop")) && x <= config.getInt("MULE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.MULE.getItemStack(), config.getString("MULE.Name"));


                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
            //1.12 Mob
        } else if (type == EntityType.PARROT) {
            if ((config.getBoolean("PARROT.Drop")) && x <= config.getInt("PARROT.Chance") + lootLvl) {
                Parrot parrot = (Parrot) entity;
                String name = "PARROT.Name";
                        switch (parrot.getVariant()) {
                    case BLUE:
                        item = utils.rename(EntityHead.PARROT_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CYAN:
                        item = utils.rename(EntityHead.PARROT_CYAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.PARROT_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case RED:
                        item = utils.rename(EntityHead.PARROT_RED.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GREEN:
                        item = utils.rename(EntityHead.PARROT_GREEN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.13 Mob
        } else if (type == EntityType.TROPICAL_FISH) {
            if ((config.getBoolean("TROPICAL_FISH.Drop")) && x <= config.getInt("TROPICAL_FISH.Chance") + lootLvl) {

                TropicalFish tropicalFish = (TropicalFish) entity;

                String name = "TROPICAL_FISH.Name";
                        switch (tropicalFish.getBodyColor()) {
                    case MAGENTA:
                        item = utils.rename(EntityHead.TROPICAL_FISH_MAGENTA.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIGHT_BLUE:
                        item = utils.rename(EntityHead.TROPICAL_FISH_LIGHT_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case YELLOW:
                        item = utils.rename(EntityHead.TROPICAL_FISH_YELLOW.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case PINK:
                        item = utils.rename(EntityHead.TROPICAL_FISH_PINK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.TROPICAL_FISH_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case LIGHT_GRAY:
                        item = utils.rename(EntityHead.TROPICAL_FISH_LIGHT_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CYAN:
                        item = utils.rename(EntityHead.TROPICAL_FISH_CYAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLUE:
                        item = utils.rename(EntityHead.TROPICAL_FISH_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GREEN:
                        item = utils.rename(EntityHead.TROPICAL_FISH_GREEN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case RED:
                        item = utils.rename(EntityHead.TROPICAL_FISH_RED.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BLACK:
                        item = utils.rename(EntityHead.TROPICAL_FISH_BLACK.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;

                    default:
                        item = utils.rename(EntityHead.TROPICAL_FISH_ORANGE.getItemStack(), config.getString(name));

                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PUFFERFISH) {
            if ((config.getBoolean("PUFFERFISH.Drop")) && x <= config.getInt("PUFFERFISH.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PUFFERFISH.getItemStack(), config.getString("PUFFERFISH.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.SALMON) {
            if ((config.getBoolean("SALMON.Drop")) && x <= config.getInt("SALMON.Chance") + lootLvl) {
                item = utils.rename(EntityHead.SALMON.getItemStack(), config.getString("SALMON.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.COD) {
            if ((config.getBoolean("COD.Drop")) && x <= config.getInt("COD.Chance") + lootLvl) {

                item = utils.rename(EntityHead.COD.getItemStack(), config.getString("COD.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TURTLE) {
            if ((config.getBoolean("TURTLE.Drop")) && x <= config.getInt("TURTLE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.TURTLE.getItemStack(), config.getString("TURTLE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DOLPHIN) {
            if ((config.getBoolean("DOLPHIN.Drop")) && x <= config.getInt("DOLPHIN.Chance") + lootLvl) {

                item = utils.rename(EntityHead.DOLPHIN.getItemStack(), config.getString("DOLPHIN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PHANTOM) {
            if ((config.getBoolean("PHANTOM.Drop")) && x <= config.getInt("PHANTOM.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PHANTOM.getItemStack(), config.getString("PHANTOM.Name"));


                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.DROWNED) {
            if ((config.getBoolean("DROWNED.Drop")) && x <= config.getInt("DROWNED.Chance") + lootLvl) {

                item = utils.rename(EntityHead.DROWNED.getItemStack(), config.getString("DROWNED.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.14 Mob
        } else if (type == EntityType.WANDERING_TRADER) {
            if ((config.getBoolean("WANDERING_TRADER.Drop")) && x <= config.getInt("WANDERING_TRADER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.WANDERING_TRADER.getItemStack(), config.getString("WANDERING_TRADER.Name"));


                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TRADER_LLAMA) {
            if ((config.getBoolean("TRADER_LLAMA.Drop")) && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                TraderLlama traderLlama = (TraderLlama) entity;

                String name = "TRADER_LLAMA.Name";
                switch (traderLlama.getColor()) {
                    case BROWN:
                        item = utils.rename(EntityHead.TRADER_LLAMA_BROWN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WHITE:
                        item = utils.rename(EntityHead.TRADER_LLAMA_WHITE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.TRADER_LLAMA_GRAY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CREAMY:
                        item = utils.rename(EntityHead.TRADER_LLAMA_CREAMY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.LLAMA) {
            if ((config.getBoolean("LLAMA.Drop")) && x <= config.getInt("LLAMA.Chance") + lootLvl) {
                Llama llama = (Llama) entity;

                switch (llama.getColor()) {
                    case BROWN:
                        item = utils.rename(EntityHead.LLAMA_BROWN.getItemStack(), config.getString("LLAMA.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case GRAY:
                        item = utils.rename(EntityHead.LLAMA_GRAY.getItemStack(), config.getString("LLAMA.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CREAMY:
                        item = utils.rename(EntityHead.LLAMA_CREAMY.getItemStack(), config.getString("LLAMA.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WHITE:
                        item = utils.rename(EntityHead.LLAMA_WHITE.getItemStack(), config.getString("LLAMA.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.RAVAGER) {
            if ((config.getBoolean("RAVAGER.Drop")) && x <= config.getInt("RAVAGER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.RAVAGER.getItemStack(), config.getString("RAVAGER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PILLAGER) {
            if ((config.getBoolean("PILLAGER.Drop")) && x <= config.getInt("PILLAGER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PILLAGER.getItemStack(), config.getString("PILLAGER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PANDA) {
            if ((config.getBoolean("PANDA.Drop")) && x <= config.getInt("PANDA.Chance") + lootLvl) {
                Panda panda = (Panda) entity;
                if (panda.getMainGene() == Panda.Gene.BROWN) {
                    item = utils.rename(EntityHead.PANDA_BROWN.getItemStack(), config.getString("PANDA.Name"));
                } else {
                    item = utils.rename(EntityHead.PANDA.getItemStack(), config.getString("PANDA.Name"));
                }
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.FOX) {
            if ((config.getBoolean("FOX.Drop")) && x <= config.getInt("FOX.Chance") + lootLvl) {
                Fox fox = (Fox) entity;

                switch (fox.getFoxType()) {
                    case RED:

                        item = utils.rename(EntityHead.FOX.getItemStack(), config.getString("FOX.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case SNOW:

                        item = utils.rename(EntityHead.FOX_WHITE.getItemStack(), config.getString("FOX.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CAT) {
            if ((config.getBoolean("CAT.Drop")) && x <= config.getInt("CAT.Chance") + lootLvl) {
                Cat cat = (Cat) entity;
                switch (cat.getCatType()) {
                    case BLACK:
                        item = utils.rename(EntityHead.CAT_BLACK.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case BRITISH_SHORTHAIR:
                        item = utils.rename(EntityHead.CAT_BRITISH.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case CALICO:
                        item = utils.rename(EntityHead.CAT_CALICO.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case JELLIE:
                        item = utils.rename(EntityHead.CAT_JELLIE.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case PERSIAN:
                        item = utils.rename(EntityHead.CAT_PERSIAN.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case RAGDOLL:
                        item = utils.rename(EntityHead.CAT_RAGDOLL.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case RED:
                        item = utils.rename(EntityHead.CAT_RED.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case SIAMESE:
                        item = utils.rename(EntityHead.CAT_SIAMESE.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case TABBY:
                        item = utils.rename(EntityHead.CAT_TABBY.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WHITE:
                        item = utils.rename(EntityHead.CAT_WHITE.getItemStack(), config.getString("CAT.Name"));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.15 Mob
        } else if (type == EntityType.BEE) {
            if ((config.getBoolean("BEE.Drop")) && x <= config.getInt("BEE.Chance") + lootLvl) {
                Bee bee = (Bee) entity;
                if (bee.getAnger() > 0) {
                    item = utils.rename(EntityHead.BEE_AWARE.getItemStack(), config.getString("BEE.Name"));
                } else {
                    item = utils.rename(EntityHead.BEE.getItemStack(), config.getString("BEE.Name"));
                }
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
            //1.16 Mob
        } else if (type == EntityType.ZOGLIN) {
            if ((config.getBoolean("ZOGLIN.Drop")) && x <= config.getInt("ZOGLIN.Chance") + lootLvl) {
                item = utils.rename(EntityHead.ZOGLIN.getItemStack(), config.getString("ZOGLIN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.STRIDER) {
            if ((config.getBoolean("STRIDER.Drop")) && x <= config.getInt("STRIDER.Chance") + lootLvl) {
                item = utils.rename(EntityHead.STRIDER.getItemStack(), config.getString("STRIDER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN) {
            if ((config.getBoolean("PIGLIN.Drop")) && x <= config.getInt("PIGLIN.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PIGLIN.getItemStack(), config.getString("PIGLIN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.HOGLIN) {
            if ((config.getBoolean("HOGLIN.Drop")) && x <= config.getInt("HOGLIN.Chance") + lootLvl) {

                item = utils.rename(EntityHead.HOGLIN.getItemStack(), config.getString("HOGLIN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.PIGLIN_BRUTE) {
            if ((config.getBoolean("PIGLIN_BRUTE.Drop")) && x <= config.getInt("PIGLIN_BRUTE.Chance") + lootLvl) {
                item = utils.rename(EntityHead.PIGLIN_BRUTE.getItemStack(), config.getString("PIGLIN_BRUTE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            // 1.17 Mob
        } else if (type == EntityType.GLOW_SQUID) {
            if ((config.getBoolean("GLOW_SQUID.Drop")) && x <= config.getInt("GLOW_SQUID.Chance") + lootLvl) {

                item = utils.rename(EntityHead.GLOW_SQUID.getItemStack(), config.getString("GLOW_SQUID.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.GOAT) {
            if ((config.getBoolean("GOAT.Drop")) && x <= config.getInt("GOAT.Chance") + lootLvl) {
                item = utils.rename(EntityHead.GOAT.getItemStack(), config.getString("GOAT.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.AXOLOTL) {
            if ((config.getBoolean("AXOLOTL.Drop")) && x <= config.getInt("AXOLOTL.Chance") + lootLvl) {
                Axolotl axolotl = (Axolotl) entity;

                String name = "AXOLOTL.Name";
                switch (axolotl.getVariant()) {
                    case LUCY:
                        item = utils.rename(EntityHead.AXOLOTL_LUCY.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                    case BLUE:
                        item = utils.rename(EntityHead.AXOLOTL_BLUE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                    case WILD:
                        item = utils.rename(EntityHead.AXOLOTL_WILD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                    case CYAN:
                        item = utils.rename(EntityHead.AXOLOTL_CYAN.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                    case GOLD:
                        item = utils.rename(EntityHead.AXOLOTL_GOLD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

            //1.19 Mob
        } else if (type == EntityType.ALLAY) {
            if ((config.getBoolean("ALLAY.Drop")) && x <= config.getInt("ALLAY.Chance") + lootLvl) {

                item = utils.rename(EntityHead.ALLAY.getItemStack(), config.getString("ALLAY.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

        } else if (type == EntityType.FROG) {
            if ((config.getBoolean("FROG.Drop")) && x <= config.getInt("FROG.Chance") + lootLvl) {
                Frog frog = (Frog) entity;
                String name = "FROG.Name";
                switch (frog.getVariant()) {
                    case TEMPERATE:
                        item = utils.rename(EntityHead.FROG_TEMPERATE.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case WARM:
                        item = utils.rename(EntityHead.FROG_WARM.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }

                        break;
                    case COLD:
                        item = utils.rename(EntityHead.FROG_COLD.getItemStack(), config.getString(name));
                        event.getDrops().add(item);
                        if (killerExist){
                            updatePDC(entity.getKiller());
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TADPOLE) {
            if ((config.getBoolean("TADPOLE.Drop")) && x <= config.getInt("TADPOLE.Chance") + lootLvl) {

                item = utils.rename(EntityHead.TADPOLE.getItemStack(), config.getString("TADPOLE.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }

        } else if (type == EntityType.WARDEN) {
            if ((config.getBoolean("WARDEN.Drop")) && x <= config.getInt("WARDEN.Chance") + lootLvl) {

                item = utils.rename(EntityHead.WARDEN.getItemStack(), config.getString("WARDEN.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.CAMEL) {
            if ((config.getBoolean("CAMEL.Drop")) && x <= config.getInt("CAMEL.Chance") + lootLvl) {

                item = utils.rename(EntityHead.CAMEL.getItemStack(), config.getString("CAMEL.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        } else if (type == EntityType.valueOf("SNIFFER")) {
            if ((config.getBoolean("SNIFFER.Drop")) && x <= config.getInt("SNIFFER.Chance") + lootLvl) {

                item = utils.rename(EntityHead.SNIFFER.getItemStack(), config.getString("SNIFFER.Name"));
                event.getDrops().add(item);
                if (killerExist){
                    updatePDC(entity.getKiller());
                }

                if ((config.getBoolean("Bot.Enable"))) embed.msg(title, description, footer);
            }
        }


    }
}