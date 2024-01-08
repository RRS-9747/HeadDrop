package me.rrs.headdrop.listener;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.database.EntityHead;
import me.rrs.headdrop.hook.WorldGuardSupport;
import me.rrs.headdrop.util.Embed;
import me.rrs.headdrop.util.ItemUtils;
import me.rrs.headdrop.util.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class EntityDeath implements Listener {

    private final YamlDocument config = HeadDrop.getConfiguration();
    private final ItemUtils utils = new ItemUtils();

    List<String> lores = config.getStringList("Lores");

    private void updateDB(Player player) {
        try {
            Bukkit.getScheduler().runTaskAsynchronously(HeadDrop.getInstance(), () -> updateDatabase(player));
        } catch (UnsupportedOperationException e) {
            updateDatabase(player);
        }
    }

    private void updateDatabase(Player player) {
        if (config.getBoolean("Database.Online")) {
            int count = HeadDrop.getDatabase().getDataByUuid(player.getUniqueId().toString());
            HeadDrop.getDatabase().updateDataByUuid(player.getUniqueId().toString(), player.getName(), count + 1);
        } else {
            int count = HeadDrop.getDatabase().getDataByName(player.getName());
            HeadDrop.getDatabase().updateDataByName(player.getName(), count + 1);
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void entityDropHeadEvent(final EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            if (!WorldGuardSupport.canDrop(entity.getLocation())) return;
        }

        if (!config.getBoolean("Config.Baby-HeadDrop") && entity instanceof Ageable && !((Ageable) entity).isAdult()) {
            return;
        }

        Embed embed = new Embed();

        String description = null;
        String footer = null;
        String title = null;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        float x = random.nextFloat(0.01F, 100.0F);

        boolean killerExist = entity.getKiller() != null;

        if (!Bukkit.getPluginManager().isPluginEnabled("LevelledMobs")) {
            if (!entity.getPersistentDataContainer().getKeys().isEmpty() && entity.getType() != EntityType.PLAYER)
                return;
        }


        if (config.getBoolean("Bot.Enable") && killerExist) {
            String killerName = entity.getKiller().getName();
            String mobName = entity.getName();

            title = config.getString("Bot.Title").replace("{KILLER}", killerName).replace("{MOB}", mobName);
            description = config.getString("Bot.Description").replace("{KILLER}", killerName).replace("{MOB}", mobName);
            footer = config.getString("Bot.Footer").replace("{KILLER}", killerName).replace("{MOB}", mobName);

            boolean placeholdersEnabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

            title = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), title) : title;
            description = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), description) : description;
            footer = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(entity.getKiller(), footer) : footer;
        }

        float lootLvl = 0.00F;
        if (HeadDrop.getConfiguration().getBoolean("Config.Enable-Looting")) {
            lootLvl += killerExist && entity.getKiller().getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS) ?
                    entity.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) : 0.00F;
        }

        if (config.getBoolean("Config.Enable-Perm-Chance") && killerExist) {
            for (float i = 100; i > 0; i--) {
                if (entity.getKiller().hasPermission("headdrop.chance" + i)) {
                    lootLvl += i;
                }
            }
        }

        if (config.getBoolean("Config.Require-Killer-Player") && entity.getKiller() == null) return;

        if (config.getBoolean("Config.Killer-Require-Permission") && (entity.getKiller() == null ||
                !entity.getKiller().hasPermission("headdrop.killer"))) {
            return;
        }

        List<String> worldList = config.getStringList("Config.Disable-Worlds");
        if (worldList.contains(entity.getWorld().getName())) return;

        EntityType type = entity.getType();

        ItemStack item;

        if (type == EntityType.PLAYER) {
            if ((config.getBoolean("PLAYER.Require-Permission")) && !entity.hasPermission("headdrop.player")) {
                return;
            }
            if ((config.getBoolean("PLAYER.Drop")) && x <= config.getFloat("PLAYER.Chance") + lootLvl) {
                ItemStack skull = SkullCreator.createSkullWithName(entity.getName());
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                utils.rename(skull, lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.BAT) {
            if ((config.getBoolean("BAT.Drop")) && x <= config.getFloat("BAT.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.BAT.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.ENDER_DRAGON) {
            if ((config.getBoolean("ENDER_DRAGON.Drop")) && x <= config.getFloat("ENDER_DRAGON.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .map(lore -> ChatColor.translateAlternateColorCodes('&', lore))
                        .collect(Collectors.toList());

                item = new ItemStack(Material.DRAGON_HEAD);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lores);
                item.setItemMeta(meta);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.ZOMBIE) {
            if ((config.getBoolean("ZOMBIE.Drop")) && x <= config.getFloat("ZOMBIE.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);


                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .map(lore -> ChatColor.translateAlternateColorCodes('&', lore))
                        .collect(Collectors.toList());

                item = new ItemStack(Material.ZOMBIE_HEAD);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lores);
                item.setItemMeta(meta);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.WITHER_SKELETON) {
            if ((config.getBoolean("WITHER_SKELETON.Drop")) && x <= config.getFloat("WITHER_SKELETON.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.WITHER_SKELETON_SKULL);

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .map(lore -> ChatColor.translateAlternateColorCodes('&', lore))
                        .collect(Collectors.toList());

                item = new ItemStack(Material.WITHER_SKELETON_SKULL);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lores);
                item.setItemMeta(meta);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.CREEPER) {
            if ((config.getBoolean("CREEPER.Drop")) && x <= config.getFloat("CREEPER.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.CREEPER_HEAD);

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .map(lore -> ChatColor.translateAlternateColorCodes('&', lore))
                        .collect(Collectors.toList());

                item = new ItemStack(Material.CREEPER_HEAD);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lores);
                item.setItemMeta(meta);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.SKELETON) {
            if ((config.getBoolean("SKELETON.Drop")) && x <= config.getFloat("SKELETON.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.SKELETON_SKULL);

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .map(lore -> ChatColor.translateAlternateColorCodes('&', lore))
                        .collect(Collectors.toList());

                item = new ItemStack(Material.SKELETON_SKULL);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(lores);
                item.setItemMeta(meta);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.BLAZE) {
            if ((config.getBoolean("BLAZE.Drop")) && x <= config.getFloat("BLAZE.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.BLAZE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {

                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.SPIDER) {
            if ((config.getBoolean("SPIDER.Drop")) && x <= config.getFloat("SPIDER.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SPIDER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.CAVE_SPIDER) {
            if ((config.getBoolean("CAVE_SPIDER.Drop")) && x <= config.getFloat("CAVE_SPIDER.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.CAVE_SPIDER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.CHICKEN) {
            if ((config.getBoolean("CHICKEN.Drop")) && x <= config.getFloat("CHICKEN.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.CHICKEN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.COW) {
            if ((config.getBoolean("COW.Drop")) && x <= config.getFloat("COW.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.COW.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.ENDERMAN) {
            if ((config.getBoolean("ENDERMAN.Drop")) && x <= config.getFloat("ENDERMAN.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ENDERMAN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.GIANT) {
            if ((config.getBoolean("GIANT.Drop")) && x <= config.getFloat("GIANT.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.GIANT.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.HORSE) {
            if ((config.getBoolean("HORSE.Drop")) && x <= config.getFloat("HORSE.Chance") + lootLvl) {
                Horse horse = (Horse) entity;
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (horse.getColor()) {
                    case WHITE:
                        item = EntityHead.HORSE_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }
                        }


                        break;
                    case CREAMY:
                        item = EntityHead.HORSE_CREAMY.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }
                        }


                        break;
                    case CHESTNUT:
                        item = EntityHead.HORSE_CHESTNUT.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BROWN:
                        item = EntityHead.HORSE_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLACK:
                        item = EntityHead.HORSE_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case GRAY:
                        item = EntityHead.HORSE_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case DARK_BROWN:
                        item = EntityHead.HORSE_DARK_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                }
            }
        } else if (type == EntityType.ILLUSIONER) {
            if ((config.getBoolean("ILLUSIONER.Drop")) && x <= config.getFloat("ILLUSIONER.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ILLUSIONER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);


                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.IRON_GOLEM) {
            if ((config.getBoolean("IRON_GOLEM.Drop")) && x <= config.getFloat("IRON_GOLEM.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.IRON_GOLEM.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.MAGMA_CUBE) {
            if ((config.getBoolean("MAGMA_CUBE.Drop")) && x <= config.getFloat("MAGMA_CUBE.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.MAGMA_CUBE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.MUSHROOM_COW) {
            if ((config.getBoolean("MUSHROOM_COW.Drop")) && x <= config.getFloat("MUSHROOM_COW.Chance") + lootLvl) {
                MushroomCow mushroomCow = (MushroomCow) entity;
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)) {
                    item = EntityHead.MUSHROOM_COW_RED.getSkull(lores);

                    HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                    Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                    if (!headDropEvent.isCancelled()) {
                        event.getDrops().add(item);

                        if (killerExist) {
                            updateDB(entity.getKiller());
                        }

                        if ((config.getBoolean("Bot.Enable")) && killerExist) {
                            embed.msg(title, description, footer);
                        }
                    }


                } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                    item = EntityHead.MUSHROOM_COW_BROWN.getSkull(lores);

                    HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                    Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                    if (!headDropEvent.isCancelled()) {
                        event.getDrops().add(item);

                        if (killerExist) {
                            updateDB(entity.getKiller());
                        }

                        if ((config.getBoolean("Bot.Enable")) && killerExist) {
                            embed.msg(title, description, footer);
                        }
                    }


                }

            }
        } else if (type == EntityType.OCELOT) {
            if ((config.getBoolean("OCELOT.Drop")) && x <= config.getFloat("OCELOT.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.OCELOT.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.PIG) {
            if ((config.getBoolean("PIG.Drop")) && x <= config.getFloat("PIG.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.PIG.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.SHEEP) {
            if ((config.getBoolean("SHEEP.Drop")) && x <= config.getFloat("SHEEP.Chance") + lootLvl) {
                Sheep sheep = (Sheep) entity;


                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (sheep.getColor()) {

                    case WHITE:
                        item = EntityHead.SHEEP_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case ORANGE:
                        item = EntityHead.SHEEP_ORANGE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case MAGENTA:
                        item = EntityHead.SHEEP_MAGENTA.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case LIGHT_BLUE:
                        item = EntityHead.SHEEP_LIGHT_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case YELLOW:
                        item = EntityHead.SHEEP_YELLOW.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case LIME:
                        item = EntityHead.SHEEP_LIME.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case PINK:
                        item = EntityHead.SHEEP_PINK.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case GRAY:
                        item = EntityHead.SHEEP_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case LIGHT_GRAY:
                        item = EntityHead.SHEEP_LIGHT_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case CYAN:
                        item = EntityHead.SHEEP_CYAN.getSkull(lores);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case PURPLE:
                        item = EntityHead.SHEEP_PURPLE.getSkull(lores);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLUE:
                        item = EntityHead.SHEEP_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent11 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent11);
                        if (!headDropEvent11.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BROWN:
                        item = EntityHead.SHEEP_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent12 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent12);
                        if (!headDropEvent12.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case GREEN:
                        item = EntityHead.SHEEP_GREEN.getSkull(lores);
                        HeadDropEvent headDropEvent13 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent13);
                        if (!headDropEvent13.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case RED:
                        item = EntityHead.SHEEP_RED.getSkull(lores);
                        HeadDropEvent headDropEvent14 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent14);
                        if (!headDropEvent14.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLACK:
                        item = EntityHead.SHEEP_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent15 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent15);
                        if (!headDropEvent15.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    default:
                        Bukkit.getLogger().severe("If you notice this error, pls report it to plugin author");
                        throw new IllegalStateException("Unexpected value: " + sheep.getColor());
                }

            }
        } else if (type == EntityType.SILVERFISH) {
            if ((config.getBoolean("SILVERFISH.Drop")) && x <= config.getFloat("SILVERFISH.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SILVERFISH.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);


                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.SLIME) {
            if ((config.getBoolean("SLIME.Drop")) && x <= config.getFloat("SLIME.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SLIME.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.SNOWMAN) {
            if ((config.getBoolean("SNOW_GOLEM.Drop")) && x <= config.getFloat("SNOW_GOLEM.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SNOWMAN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.SQUID) {
            if ((config.getBoolean("SQUID.Drop")) && x <= config.getFloat("SQUID.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SQUID.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.WITCH) {
            if ((config.getBoolean("WITCH.Drop")) && x <= config.getFloat("WITCH.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.WITCH.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.WITHER) {
            if ((config.getBoolean("WITHER.Drop")) && x <= config.getFloat("WITHER.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.WITHER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.ZOMBIFIED_PIGLIN) {
            if ((config.getBoolean("ZOMBIFIED_PIGLIN.Drop")) && x <= config.getFloat("ZOMBIFIED_PIGLIN.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ZOMBIFIED_PIGLIN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.GHAST) {
            if ((config.getBoolean("GHAST.Drop")) && x <= config.getFloat("GHAST.Chance") + lootLvl) {
                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.GHAST.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.WOLF) {
            if ((config.getBoolean("WOLF.Drop")) && x <= config.getFloat("WOLF.Chance") + lootLvl) {
                Wolf wolf = (Wolf) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (wolf.isAngry()) {
                    item = EntityHead.WOLF_ANGRY.getSkull(lores);
                } else {
                    item = EntityHead.WOLF.getSkull(lores);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.VILLAGER) {
            if ((config.getBoolean("VILLAGER.Drop")) && x <= config.getFloat("VILLAGER.Chance") + lootLvl) {
                Villager villager = (Villager) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (villager.getProfession()) {
                    case WEAPONSMITH:
                        item = EntityHead.VILLAGER_WEAPONSMITH.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case SHEPHERD:
                        item = EntityHead.VILLAGER_SHEPHERD.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case LIBRARIAN:
                        item = EntityHead.VILLAGER_LIBRARIAN.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case FLETCHER:
                        item = EntityHead.VILLAGER_FLETCHER.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case FISHERMAN:
                        item = EntityHead.VILLAGER_FISHERMAN.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case FARMER:
                        item = EntityHead.VILLAGER_FARMER.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case CLERIC:
                        item = EntityHead.VILLAGER_CLERIC.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case CARTOGRAPHER:
                        item = EntityHead.VILLAGER_CARTOGRAPHER.getSkull(lores);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BUTCHER:
                        item = EntityHead.VILLAGER_BUTCHER.getSkull(lores);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case ARMORER:
                        item = EntityHead.VILLAGER_ARMORER.getSkull(lores);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case LEATHERWORKER:
                        item = EntityHead.VILLAGER_LEATHERWORKER.getSkull(lores);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case MASON:
                        item = EntityHead.VILLAGER_MASON.getSkull(lores);
                        HeadDropEvent headDropEvent11 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent11);
                        if (!headDropEvent11.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case TOOLSMITH:
                        item = EntityHead.VILLAGER_TOOLSMITH.getSkull(lores);
                        HeadDropEvent headDropEvent13 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent13);
                        if (!headDropEvent13.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    default:
                        item = EntityHead.VILLAGER_NULL.getSkull(lores);
                        HeadDropEvent headDropEvent14 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent14);
                        if (!headDropEvent14.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                }
            }

            //1.8 Mob
        } else if (type == EntityType.RABBIT) {
            if ((config.getBoolean("RABBIT.Drop")) && x <= config.getFloat("RABBIT.Chance") + lootLvl) {
                Rabbit rabbit = (Rabbit) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (rabbit.getRabbitType()) {

                    case BROWN:
                        item = EntityHead.RABBIT_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case WHITE:
                        item = EntityHead.RABBIT_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLACK:
                        item = EntityHead.RABBIT_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLACK_AND_WHITE:
                        item = EntityHead.RABBIT_BLACK_AND_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GOLD:
                        item = EntityHead.RABBIT_GOLD.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case SALT_AND_PEPPER:
                        item = EntityHead.RABBIT_SALT_AND_PEPPER.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case THE_KILLER_BUNNY:
                        item = EntityHead.RABBIT_THE_KILLER_BUNNY.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                }
            }
        } else if (type == EntityType.ENDERMITE) {
            if ((config.getBoolean("ENDERMITE.Drop")) && x <= config.getFloat("ENDERMITE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ENDERMITE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.GUARDIAN) {
            if ((config.getBoolean("GUARDIAN.Drop")) && x <= config.getFloat("GUARDIAN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GUARDIAN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }

            //1.9 Mob
        } else if (type == EntityType.SHULKER) {
            if ((config.getBoolean("SHULKER.Drop")) && x <= config.getFloat("SHULKER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SHULKER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
            //1.10 Mob
        } else if (type == EntityType.POLAR_BEAR) {
            if ((config.getBoolean("POLAR_BEAR.Drop")) && x <= config.getFloat("POLAR_BEAR.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.POLAR_BEAR.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
            //1.11 Mob
        } else if (type == EntityType.ZOMBIE_VILLAGER) {
            if ((config.getBoolean("ZOMBIE_VILLAGER.Drop")) && x <= config.getFloat("ZOMBIE_VILLAGER.Chance") + lootLvl) {
                ZombieVillager zombieVillager = (ZombieVillager) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (zombieVillager.getVillagerProfession()) {
                    case ARMORER:
                        item = EntityHead.ZOMBIE_VILLAGER_ARMORER.getSkull(lores);

                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BUTCHER:
                        item = EntityHead.ZOMBIE_VILLAGER_BUTCHER.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CARTOGRAPHER:
                        item = EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CLERIC:
                        item = EntityHead.ZOMBIE_VILLAGER_CLERIC.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);


                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case FARMER:
                        item = EntityHead.ZOMBIE_VILLAGER_FARMER.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case FISHERMAN:
                        item = EntityHead.ZOMBIE_VILLAGER_FISHERMAN.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case FLETCHER:
                        item = EntityHead.ZOMBIE_VILLAGER_FLETCHER.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case LIBRARIAN:
                        item = EntityHead.ZOMBIE_VILLAGER_LIBRARIAN.getSkull(lores);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case SHEPHERD:
                        item = EntityHead.ZOMBIE_VILLAGER_SHEPHERD.getSkull(lores);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case WEAPONSMITH:
                        item = EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH.getSkull(lores);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    default:
                        item = EntityHead.ZOMBIE_VILLAGER_NULL.getSkull(lores);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                }
            }
        } else if (type == EntityType.VINDICATOR) {
            if ((config.getBoolean("VINDICATOR.Drop")) && x <= config.getFloat("VINDICATOR.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.VINDICATOR.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.VEX) {
            if ((config.getBoolean("VEX.Drop")) && x <= config.getFloat("VEX.Chance") + lootLvl) {

                Vex vex = (Vex) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (vex.isCharging()) {
                    item = EntityHead.VEX_CHARGE.getSkull(lores);

                } else {
                    item = EntityHead.VEX.getSkull(lores);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.EVOKER) {
            if ((config.getBoolean("EVOKER.Drop")) && x <= config.getFloat("EVOKER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.EVOKER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.HUSK) {
            if ((config.getBoolean("HUSK.Drop")) && x <= config.getFloat("HUSK.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.HUSK.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.STRAY) {
            if ((config.getBoolean("STRAY.Drop")) && x <= config.getFloat("STRAY.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.STRAY.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.ELDER_GUARDIAN) {
            if ((config.getBoolean("ELDER_GUARDIAN.Drop")) && x <= config.getFloat("ELDER_GUARDIAN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ELDER_GUARDIAN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.DONKEY) {
            if ((config.getBoolean("DONKEY.Drop")) && x <= config.getFloat("DONKEY.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DONKEY.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.ZOMBIE_HORSE) {
            if ((config.getBoolean("ZOMBIE_HORSE.Drop")) && x <= config.getFloat("ZOMBIE_HORSE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ZOMBIE_HORSE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.SKELETON_HORSE) {
            if ((config.getBoolean("SKELETON_HORSE.Drop")) && x <= config.getFloat("SKELETON_HORSE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SKELETON_HORSE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.MULE) {
            if ((config.getBoolean("MULE.Drop")) && x <= config.getFloat("MULE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.MULE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
            //1.12 Mob
        } else if (type == EntityType.PARROT) {
            if ((config.getBoolean("PARROT.Drop")) && x <= config.getFloat("PARROT.Chance") + lootLvl) {
                Parrot parrot = (Parrot) entity;


                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (parrot.getVariant()) {
                    case BLUE:
                        item = EntityHead.PARROT_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CYAN:
                        item = EntityHead.PARROT_CYAN.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GRAY:
                        item = EntityHead.PARROT_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case RED:
                        item = EntityHead.PARROT_RED.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GREEN:
                        item = EntityHead.PARROT_GREEN.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                }
            }

            //1.13 Mob
        } else if (type == EntityType.TROPICAL_FISH) {
            if ((config.getBoolean("TROPICAL_FISH.Drop")) && x <= config.getFloat("TROPICAL_FISH.Chance") + lootLvl) {

                TropicalFish tropicalFish = (TropicalFish) entity;


                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (tropicalFish.getBodyColor()) {
                    case MAGENTA:
                        item = EntityHead.TROPICAL_FISH_MAGENTA.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }

                        }


                        break;
                    case LIGHT_BLUE:
                        item = EntityHead.TROPICAL_FISH_LIGHT_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case YELLOW:
                        item = EntityHead.TROPICAL_FISH_YELLOW.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case PINK:
                        item = EntityHead.TROPICAL_FISH_PINK.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GRAY:
                        item = EntityHead.TROPICAL_FISH_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case LIGHT_GRAY:
                        item = EntityHead.TROPICAL_FISH_LIGHT_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CYAN:
                        item = EntityHead.TROPICAL_FISH_CYAN.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case BLUE:
                        item = EntityHead.TROPICAL_FISH_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case GREEN:
                        item = EntityHead.TROPICAL_FISH_GREEN.getSkull(lores);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case RED:
                        item = EntityHead.TROPICAL_FISH_RED.getSkull(lores);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }


                        break;
                    case BLACK:
                        item = EntityHead.TROPICAL_FISH_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;

                    default:
                        item = EntityHead.TROPICAL_FISH_ORANGE.getSkull(lores);

                        HeadDropEvent headDropEvent11 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent11);
                        if (!headDropEvent11.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                }
            }
        } else if (type == EntityType.PUFFERFISH) {
            if ((config.getBoolean("PUFFERFISH.Drop")) && x <= config.getFloat("PUFFERFISH.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PUFFERFISH.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.SALMON) {
            if ((config.getBoolean("SALMON.Drop")) && x <= config.getFloat("SALMON.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SALMON.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.COD) {
            if ((config.getBoolean("COD.Drop")) && x <= config.getFloat("COD.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.COD.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.TURTLE) {
            if ((config.getBoolean("TURTLE.Drop")) && x <= config.getFloat("TURTLE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.TURTLE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.DOLPHIN) {
            if ((config.getBoolean("DOLPHIN.Drop")) && x <= config.getFloat("DOLPHIN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DOLPHIN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.PHANTOM) {
            if ((config.getBoolean("PHANTOM.Drop")) && x <= config.getFloat("PHANTOM.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PHANTOM.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.DROWNED) {
            if ((config.getBoolean("DROWNED.Drop")) && x <= config.getFloat("DROWNED.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DROWNED.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }

            //1.14 Mob
        } else if (type == EntityType.WANDERING_TRADER) {
            if ((config.getBoolean("WANDERING_TRADER.Drop")) && x <= config.getFloat("WANDERING_TRADER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.WANDERING_TRADER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.TRADER_LLAMA) {
            if ((config.getBoolean("TRADER_LLAMA.Drop")) && x <= config.getFloat("TRADER_LLAMA.Chance")) {
                TraderLlama traderLlama = (TraderLlama) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (traderLlama.getColor()) {
                    case BROWN:
                        item = EntityHead.TRADER_LLAMA_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case WHITE:
                        item = EntityHead.TRADER_LLAMA_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GRAY:
                        item = EntityHead.TRADER_LLAMA_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CREAMY:
                        item = EntityHead.TRADER_LLAMA_CREAMY.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }

                        }

                        break;
                }
            }
        } else if (type == EntityType.LLAMA) {
            if ((config.getBoolean("LLAMA.Drop")) && x <= config.getFloat("LLAMA.Chance") + lootLvl) {
                Llama llama = (Llama) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());


                switch (llama.getColor()) {
                    case BROWN:
                        item = EntityHead.LLAMA_BROWN.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case GRAY:
                        item = EntityHead.LLAMA_GRAY.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case CREAMY:
                        item = EntityHead.LLAMA_CREAMY.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case WHITE:
                        item = EntityHead.LLAMA_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                }
            }
        } else if (type == EntityType.RAVAGER) {
            if ((config.getBoolean("RAVAGER.Drop")) && x <= config.getFloat("RAVAGER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.RAVAGER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);
                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.PILLAGER) {
            if ((config.getBoolean("PILLAGER.Drop")) && x <= config.getFloat("PILLAGER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PILLAGER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.PANDA) {
            if ((config.getBoolean("PANDA.Drop")) && x <= config.getFloat("PANDA.Chance") + lootLvl) {
                Panda panda = (Panda) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (panda.getMainGene() == Panda.Gene.BROWN) {
                    item = EntityHead.PANDA_BROWN.getSkull(lores);
                } else {
                    item = EntityHead.PANDA.getSkull(lores);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.FOX) {
            if ((config.getBoolean("FOX.Drop")) && x <= config.getFloat("FOX.Chance") + lootLvl) {
                Fox fox = (Fox) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (fox.getFoxType()) {
                    case RED:
                        item = EntityHead.FOX.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case SNOW:
                        item = EntityHead.FOX_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);
                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                }
            }
        } else if (type == EntityType.CAT) {
            if ((config.getBoolean("CAT.Drop")) && x <= config.getFloat("CAT.Chance") + lootLvl) {
                Cat cat = (Cat) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (cat.getCatType()) {
                    case BLACK:
                        item = EntityHead.CAT_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case BRITISH_SHORTHAIR:
                        item = EntityHead.CAT_BRITISH.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case CALICO:
                        item = EntityHead.CAT_CALICO.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case JELLIE:
                        item = EntityHead.CAT_JELLIE.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case PERSIAN:
                        item = EntityHead.CAT_PERSIAN.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case RAGDOLL:
                        item = EntityHead.CAT_RAGDOLL.getSkull(lores);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case RED:
                        item = EntityHead.CAT_RED.getSkull(lores);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case SIAMESE:
                        item = EntityHead.CAT_SIAMESE.getSkull(lores);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case TABBY:
                        item = EntityHead.CAT_TABBY.getSkull(lores);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                    case ALL_BLACK:
                        item = EntityHead.CAT_ALL_BLACK.getSkull(lores);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case WHITE:
                        item = EntityHead.CAT_WHITE.getSkull(lores);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                }
            }

            //1.15 Mob
        } else if (type == EntityType.BEE) {
            if ((config.getBoolean("BEE.Drop")) && x <= config.getFloat("BEE.Chance") + lootLvl) {
                Bee bee = (Bee) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (bee.getAnger() > 0) {
                    item = EntityHead.BEE_AWARE.getSkull(lores);
                } else {
                    item = EntityHead.BEE.getSkull(lores);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
            //1.16 Mob
        } else if (type == EntityType.ZOGLIN) {
            if ((config.getBoolean("ZOGLIN.Drop")) && x <= config.getFloat("ZOGLIN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ZOGLIN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.STRIDER) {
            if ((config.getBoolean("STRIDER.Drop")) && x <= config.getFloat("STRIDER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.STRIDER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.PIGLIN) {
            if ((config.getBoolean("PIGLIN.Drop")) && x <= config.getFloat("PIGLIN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .map(lore -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(null, lore) : lore)
                        .collect(Collectors.toList());
                //PIGLIN_HEAD added in 1.20
                if (Bukkit.getServer().getVersion().contains("1.19")) {
                    item = EntityHead.PIGLIN.getSkull(lores);
                } else {
                    try {
                        item = new ItemStack(Material.PIGLIN_HEAD);
                        ItemMeta meta = item.getItemMeta();
                        meta.setLore(lores);
                        item.setItemMeta(meta);
                        event.getDrops().removeIf(head -> head.getType() == Material.PIGLIN_HEAD);

                    } catch (NoSuchFieldError e) {
                        item = EntityHead.PIGLIN.getSkull(lores);
                    }
                }
                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.HOGLIN) {
            if ((config.getBoolean("HOGLIN.Drop")) && x <= config.getFloat("HOGLIN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.HOGLIN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.PIGLIN_BRUTE) {
            if ((config.getBoolean("PIGLIN_BRUTE.Drop")) && x <= config.getFloat("PIGLIN_BRUTE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PIGLIN_BRUTE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }

            // 1.17 Mob
        } else if (type == EntityType.GLOW_SQUID) {
            if ((config.getBoolean("GLOW_SQUID.Drop")) && x <= config.getFloat("GLOW_SQUID.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GLOW_SQUID.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.GOAT) {
            if ((config.getBoolean("GOAT.Drop")) && x <= config.getFloat("GOAT.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GOAT.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.AXOLOTL) {
            if ((config.getBoolean("AXOLOTL.Drop")) && x <= config.getFloat("AXOLOTL.Chance") + lootLvl) {
                Axolotl axolotl = (Axolotl) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (axolotl.getVariant()) {
                    case LUCY:
                        item = EntityHead.AXOLOTL_LUCY.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case BLUE:
                        item = EntityHead.AXOLOTL_BLUE.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case WILD:
                        item = EntityHead.AXOLOTL_WILD.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case CYAN:
                        item = EntityHead.AXOLOTL_CYAN.getSkull(lores);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                    case GOLD:
                        item = EntityHead.AXOLOTL_GOLD.getSkull(lores);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                }
            }

            //1.19 Mob
        } else if (type == EntityType.ALLAY) {
            if ((config.getBoolean("ALLAY.Drop")) && x <= config.getFloat("ALLAY.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ALLAY.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.FROG) {
            if ((config.getBoolean("FROG.Drop")) && x <= config.getFloat("FROG.Chance") + lootLvl) {
                Frog frog = (Frog) entity;

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (frog.getVariant()) {
                    case TEMPERATE:
                        item = EntityHead.FROG_TEMPERATE.getSkull(lores);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case WARM:
                        item = EntityHead.FROG_WARM.getSkull(lores);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }

                        break;
                    case COLD:
                        item = EntityHead.FROG_COLD.getSkull(lores);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()) {
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }

                            if ((config.getBoolean("Bot.Enable")) && killerExist) {
                                embed.msg(title, description, footer);
                            }
                        }
                        break;
                }
                if ((config.getBoolean("Bot.Enable")) && killerExist)
                    embed.msg(title, description, footer);
            }
        } else if (type == EntityType.TADPOLE) {
            if ((config.getBoolean("TADPOLE.Drop")) && x <= config.getFloat("TADPOLE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.TADPOLE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.WARDEN) {
            if ((config.getBoolean("WARDEN.Drop")) && x <= config.getFloat("WARDEN.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.WARDEN.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }

            //1.20 Mob
        } else if (type == EntityType.CAMEL) {
            if ((config.getBoolean("CAMEL.Drop")) && x <= config.getFloat("CAMEL.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.CAMEL.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        } else if (type == EntityType.SNIFFER) {
            if ((config.getBoolean("SNIFFER.Drop")) && x <= config.getFloat("SNIFFER.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SNIFFER.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
            //1.21 Mob
        } else if (type == EntityType.BREEZE) {
            if ((config.getBoolean("BREEZE.Drop")) && x <= config.getFloat("BREEZE.Chance") + lootLvl) {

                lores = lores.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.BREEZE.getSkull(lores);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()) {
                    event.getDrops().add(item);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }
            }
        }

    }
}