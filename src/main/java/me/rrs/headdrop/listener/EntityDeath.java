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
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class EntityDeath implements Listener {

    final YamlDocument config = HeadDrop.getConfiguration();
    final ItemUtils utils = new ItemUtils();

    private void updateDB(Player player){
        if (config.getBoolean("Database.Online")){
            int count = HeadDrop.getDatabase().getDataByUuid(player.getUniqueId().toString());
            HeadDrop.getDatabase().updateDataByUuid(player.getUniqueId().toString(), player.getName(), count+1);
        }else {
            int count = HeadDrop.getDatabase().getDataByName(player.getName());
            HeadDrop.getDatabase().updateDataByName(player.getName(), count+1);
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void entityDropHeadEvent(final EntityDeathEvent event) {

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")){
            if (!WorldGuardSupport.canDrop(event.getEntity().getLocation())) return;
        }

        Embed embed = new Embed();

        String description = null;
        String footer = null;
        String title = null;
        final Random random = new Random();
        float x = random.nextFloat(101);

        final LivingEntity entity = event.getEntity();
        boolean killerExist = entity.getKiller() != null;


        if (!HeadDrop.getConfiguration().getBoolean("Config.Baby-HeadDrop")){
            if (entity instanceof Ageable){
                if (!((Ageable) entity).isAdult()){
                    return;
                }
            }
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

        int lootLvl = 0;
        if (HeadDrop.getConfiguration().getBoolean("Config.Enable-Looting")) {
            lootLvl = killerExist && entity.getKiller().getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS) ?
                    entity.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS) : 0;
        }

        if (HeadDrop.getConfiguration().getBoolean("Config.Enable-Perm-Chance")){
            if (killerExist){
                for (int i = 100; i > 0; i--){
                    if (entity.getKiller().hasPermission("headdrop.chance" + i)) {
                        lootLvl = lootLvl + i;
                    }
                }
            }
        }

        if (!entity.getPersistentDataContainer().getKeys().isEmpty() && entity.getType() != EntityType.PLAYER) return;

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
            if ((config.getBoolean("PLAYER.Drop")) && x <= config.getInt("PLAYER.Chance") + lootLvl) {
                ItemStack skull = SkullCreator.createSkullWithName(entity.getName());

                List<String> loreList = config.getStringList("PLAYER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                utils.rename(skull, loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("BAT.Drop")) && x <= config.getInt("BAT.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("BAT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.BAT.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ENDER_DRAGON.Drop")) && x <= config.getInt("ENDER_DRAGON.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ENDER_DRAGON.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                ItemStack skull = utils.rename(new ItemStack(Material.DRAGON_HEAD), loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.ZOMBIE) {
            if ((config.getBoolean("ZOMBIE.Drop")) && x <= config.getInt("ZOMBIE.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);

                List<String> loreList = config.getStringList("ZOMBIE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                ItemStack skull = utils.rename(new ItemStack(Material.ZOMBIE_HEAD), loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }
                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.WITHER_SKELETON) {
            if ((config.getBoolean("WITHER_SKELETON.Drop")) && x <= config.getInt("WITHER_SKELETON.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.WITHER_SKELETON_SKULL);

                List<String> loreList = config.getStringList("WITHER_SKELETON.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                ItemStack skull = utils.rename(new ItemStack(Material.WITHER_SKELETON_SKULL), loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.CREEPER) {
            if ((config.getBoolean("CREEPER.Drop")) && x <= config.getInt("CREEPER.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.CREEPER_HEAD);

                List<String> loreList = config.getStringList("WITHER_SKELETON.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                ItemStack skull = utils.rename(new ItemStack(Material.CREEPER_HEAD), loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }


            }
        } else if (type == EntityType.SKELETON) {
            if ((config.getBoolean("SKELETON.Drop")) && x <= config.getInt("SKELETON.Chance") + lootLvl) {
                event.getDrops().removeIf(head -> head.getType() == Material.SKELETON_SKULL);

                List<String> loreList = config.getStringList("WITHER_SKELETON.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                ItemStack skull = utils.rename(new ItemStack(Material.SKELETON_SKULL), loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(skull, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
                    event.getDrops().add(skull);

                    if (killerExist) {
                        updateDB(entity.getKiller());
                    }

                    if ((config.getBoolean("Bot.Enable")) && killerExist) {
                        embed.msg(title, description, footer);
                    }
                }

            }
        } else if (type == EntityType.BLAZE) {
            if ((config.getBoolean("BLAZE.Drop")) && x <= config.getInt("BLAZE.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("BLAZE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.BLAZE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SPIDER.Drop")) && x <= config.getInt("SPIDER.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("SPIDER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SPIDER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("CAVE_SPIDER.Drop")) && x <= config.getInt("CAVE_SPIDER.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("CAVE_SPIDER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.CAVE_SPIDER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("CHICKEN.Drop")) && x <= config.getInt("CHICKEN.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("CHICKEN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.CHICKEN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("COW.Drop")) && x <= config.getInt("COW.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("COW.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.COW.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ENDERMAN.Drop")) && x <= config.getInt("ENDERMAN.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("ENDERMAN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ENDERMAN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("GIANT.Drop")) && x <= config.getInt("GIANT.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("GIANT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.GIANT.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("HORSE.Drop")) && x <= config.getInt("HORSE.Chance") + lootLvl) {
                Horse horse = (Horse) entity;
                List<String> loreList = config.getStringList("HORSE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (horse.getColor()) {
                    case WHITE:
                        item = EntityHead.HORSE_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }
                        }


                        break;
                    case CREAMY:
                        item = EntityHead.HORSE_CREAMY.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
                            event.getDrops().add(item);

                            if (killerExist) {
                                updateDB(entity.getKiller());
                            }
                        }


                        break;
                    case CHESTNUT:
                        item = EntityHead.HORSE_CHESTNUT.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.HORSE_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.HORSE_BLACK.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.HORSE_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.HORSE_DARK_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
            if ((config.getBoolean("ILLUSIONER.Drop")) && x <= config.getInt("ILLUSIONER.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("ILLUSIONER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ILLUSIONER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("IRON_GOLEM.Drop")) && x <= config.getInt("IRON_GOLEM.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("IRON_GOLEM.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.IRON_GOLEM.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("MAGMA_CUBE.Drop")) && x <= config.getInt("MAGMA_CUBE.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("MAGMA_CUBE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.MAGMA_CUBE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("MUSHROOM_COW.Drop")) && x <= config.getInt("MUSHROOM_COW.Chance") + lootLvl) {
                MushroomCow mushroomCow = (MushroomCow) entity;
                List<String> loreList = config.getStringList("MUSHROOM_COW.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)) {
                    item = EntityHead.MUSHROOM_COW_RED.getItemStack(loreList);

                    HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                    Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                    if (!headDropEvent.isCancelled()){
                        event.getDrops().add(item);

                        if (killerExist) {
                            updateDB(entity.getKiller());
                        }

                        if ((config.getBoolean("Bot.Enable")) && killerExist) {
                            embed.msg(title, description, footer);
                        }
                    }


                } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                    item = EntityHead.MUSHROOM_COW_BROWN.getItemStack(loreList);

                    HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                    Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                    if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("OCELOT.Drop")) && x <= config.getInt("OCELOT.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("OCELOT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.OCELOT.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PIG.Drop")) && x <= config.getInt("PIG.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("PIG.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.PIG.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SHEEP.Drop")) && x <= config.getInt("SHEEP.Chance") + lootLvl) {
                Sheep sheep = (Sheep) entity;


                List<String> loreList = config.getStringList("SHEEP.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (sheep.getColor()) {

                    case WHITE:
                        item = EntityHead.SHEEP_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.SHEEP_ORANGE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.SHEEP_MAGENTA.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.SHEEP_LIGHT_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.SHEEP_YELLOW.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.SHEEP_LIME.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.SHEEP_PINK.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
                        item = EntityHead.SHEEP_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()){
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
                        item = EntityHead.SHEEP_LIGHT_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()){
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
                        item = EntityHead.SHEEP_CYAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()){
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
                        item = EntityHead.SHEEP_PURPLE.getItemStack(loreList);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()){
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
                        item = EntityHead.SHEEP_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent11 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent11);
                        if (!headDropEvent11.isCancelled()){
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
                        item = EntityHead.SHEEP_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent12 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent12);
                        if (!headDropEvent12.isCancelled()){
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
                        item = EntityHead.SHEEP_GREEN.getItemStack(loreList);
                        HeadDropEvent headDropEvent13 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent13);
                        if (!headDropEvent13.isCancelled()){
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
                        item = EntityHead.SHEEP_RED.getItemStack(loreList);
                        HeadDropEvent headDropEvent14 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent14);
                        if (!headDropEvent14.isCancelled()){
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
                        item = EntityHead.SHEEP_BLACK.getItemStack(loreList);
                        HeadDropEvent headDropEvent15 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent15);
                        if (!headDropEvent15.isCancelled()){
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
            if ((config.getBoolean("SILVERFISH.Drop")) && x <= config.getInt("SILVERFISH.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("SILVERFISH.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SILVERFISH.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SLIME.Drop")) && x <= config.getInt("SLIME.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("SLIME.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SLIME.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SNOW_GOLEM.Drop")) && x <= config.getInt("SNOW_GOLEM.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("SNOW_GOLEM.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SNOWMAN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SQUID.Drop")) && x <= config.getInt("SQUID.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("SQUID.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.SQUID.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("WITCH.Drop")) && x <= config.getInt("WITCH.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("WITCH.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.WITCH.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("WITHER.Drop")) && x <= config.getInt("WITHER.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("WITHER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.WITHER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ZOMBIFIED_PIGLIN.Drop")) && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("ZOMBIFIED_PIGLIN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.ZOMBIFIED_PIGLIN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("GHAST.Drop")) && x <= config.getInt("GHAST.Chance") + lootLvl) {
                List<String> loreList = config.getStringList("GHAST.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                item = EntityHead.GHAST.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("WOLF.Drop")) && x <= config.getInt("WOLF.Chance") + lootLvl) {
                Wolf wolf = (Wolf) entity;

                List<String> loreList = config.getStringList("WOLF.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (wolf.isAngry()) {
                    item = EntityHead.WOLF_ANGRY.getItemStack(loreList);
                } else {
                    item = EntityHead.WOLF.getItemStack(loreList);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("VILLAGER.Drop")) && x <= config.getInt("VILLAGER.Chance") + lootLvl) {
                Villager villager = (Villager) entity;

                List<String> loreList = config.getStringList("VILLAGER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (villager.getProfession()) {
                    case WEAPONSMITH:
                        item = EntityHead.VILLAGER_WEAPONSMITH.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.VILLAGER_SHEPHERD.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.VILLAGER_LIBRARIAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.VILLAGER_FLETCHER.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.VILLAGER_FISHERMAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.VILLAGER_FARMER.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.VILLAGER_CLERIC.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
                        item = EntityHead.VILLAGER_CARTOGRAPHER.getItemStack(loreList);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()){
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
                        item = EntityHead.VILLAGER_BUTCHER.getItemStack(loreList);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()){
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
                        item = EntityHead.VILLAGER_ARMORER.getItemStack(loreList);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()){
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
                        item = EntityHead.VILLAGER_NULL.getItemStack(loreList);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()){
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
            if ((config.getBoolean("RABBIT.Drop")) && x <= config.getInt("RABBIT.Chance") + lootLvl) {
                Rabbit rabbit = (Rabbit) entity;

                List<String> loreList = config.getStringList("RABBIT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (rabbit.getRabbitType()) {

                    case BROWN:
                        item = EntityHead.RABBIT_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.RABBIT_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.RABBIT_BLACK.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.RABBIT_BLACK_AND_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.RABBIT_GOLD.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.RABBIT_SALT_AND_PEPPER.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.RABBIT_THE_KILLER_BUNNY.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
            if ((config.getBoolean("ENDERMITE.Drop")) && x <= config.getInt("ENDERMITE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ENDERMITE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ENDERMITE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("GUARDIAN.Drop")) && x <= config.getInt("GUARDIAN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("GUARDIAN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GUARDIAN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SHULKER.Drop")) && x <= config.getInt("SHULKER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("SHULKER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SHULKER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("POLAR_BEAR.Drop")) && x <= config.getInt("POLAR_BEAR.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("POLAR_BEAR.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.POLAR_BEAR.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ZOMBIE_VILLAGER.Drop")) && x <= config.getInt("ZOMBIE_VILLAGER.Chance") + lootLvl) {
                ZombieVillager zombieVillager = (ZombieVillager) entity;

                List<String> loreList = config.getStringList("ZOMBIE_VILLAGER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (zombieVillager.getVillagerProfession()) {
                    case ARMORER:
                        item = EntityHead.ZOMBIE_VILLAGER_ARMORER.getItemStack(loreList);

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
                        item = EntityHead.ZOMBIE_VILLAGER_BUTCHER.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_CLERIC.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_FARMER.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_FISHERMAN.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_FLETCHER.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_LIBRARIAN.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_SHEPHERD.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH.getItemStack(loreList);
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
                        item = EntityHead.ZOMBIE_VILLAGER_NULL.getItemStack(loreList);
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
            if ((config.getBoolean("VINDICATOR.Drop")) && x <= config.getInt("VINDICATOR.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("VINDICATOR.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.VINDICATOR.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("VEX.Drop")) && x <= config.getInt("VEX.Chance") + lootLvl) {

                Vex vex = (Vex) entity;

                List<String> loreList = config.getStringList("VEX.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (vex.isCharging()) {
                    item = EntityHead.VEX_CHARGE.getItemStack(loreList);

                } else {
                    item = EntityHead.VEX.getItemStack(loreList);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("EVOKER.Drop")) && x <= config.getInt("EVOKER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("EVOKER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.EVOKER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("HUSK.Drop")) && x <= config.getInt("HUSK.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("HUSK.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.HUSK.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("STRAY.Drop")) && x <= config.getInt("STRAY.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("STRAY.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.STRAY.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ELDER_GUARDIAN.Drop")) && x <= config.getInt("ELDER_GUARDIAN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ELDER_GUARDIAN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ELDER_GUARDIAN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("DONKEY.Drop")) && x <= config.getInt("DONKEY.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("DONKEY.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DONKEY.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ZOMBIE_HORSE.Drop")) && x <= config.getInt("ZOMBIE_HORSE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ZOMBIE_HORSE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ZOMBIE_HORSE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SKELETON_HORSE.Drop")) && x <= config.getInt("SKELETON_HORSE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("SKELETON_HORSE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SKELETON_HORSE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("MULE.Drop")) && x <= config.getInt("MULE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("MULE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.MULE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PARROT.Drop")) && x <= config.getInt("PARROT.Chance") + lootLvl) {
                Parrot parrot = (Parrot) entity;


                List<String> loreList = config.getStringList("PARROT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (parrot.getVariant()) {
                    case BLUE:
                        item = EntityHead.PARROT_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.PARROT_CYAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.PARROT_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.PARROT_RED.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.PARROT_GREEN.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
            if ((config.getBoolean("TROPICAL_FISH.Drop")) && x <= config.getInt("TROPICAL_FISH.Chance") + lootLvl) {

                TropicalFish tropicalFish = (TropicalFish) entity;


                List<String> loreList = config.getStringList("TROPICAL_FISH.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (tropicalFish.getBodyColor()) {
                    case MAGENTA:
                        item = EntityHead.TROPICAL_FISH_MAGENTA.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_LIGHT_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_YELLOW.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_PINK.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_LIGHT_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_CYAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_GREEN.getItemStack(loreList);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_RED.getItemStack(loreList);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_BLACK.getItemStack(loreList);
                        HeadDropEvent headDropEvent10 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent10);
                        if (!headDropEvent10.isCancelled()){
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
                        item = EntityHead.TROPICAL_FISH_ORANGE.getItemStack(loreList);

                        HeadDropEvent headDropEvent11 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent11);
                        if (!headDropEvent11.isCancelled()){
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
            if ((config.getBoolean("PUFFERFISH.Drop")) && x <= config.getInt("PUFFERFISH.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("PUFFERFISH.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PUFFERFISH.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SALMON.Drop")) && x <= config.getInt("SALMON.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("SALMON.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SALMON.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("COD.Drop")) && x <= config.getInt("COD.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("COD.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.COD.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("TURTLE.Drop")) && x <= config.getInt("TURTLE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("TURTLE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.TURTLE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("DOLPHIN.Drop")) && x <= config.getInt("DOLPHIN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("DOLPHIN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DOLPHIN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PHANTOM.Drop")) && x <= config.getInt("PHANTOM.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("PHANTOM.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PHANTOM.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("DROWNED.Drop")) && x <= config.getInt("DROWNED.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("DROWNED.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.DROWNED.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("WANDERING_TRADER.Drop")) && x <= config.getInt("WANDERING_TRADER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("WANDERING_TRADER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.WANDERING_TRADER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("TRADER_LLAMA.Drop")) && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                TraderLlama traderLlama = (TraderLlama) entity;

                List<String> loreList = config.getStringList("TRADER_LLAMA.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (traderLlama.getColor()) {
                    case BROWN:
                        item = EntityHead.TRADER_LLAMA_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.TRADER_LLAMA_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.TRADER_LLAMA_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.TRADER_LLAMA_CREAMY.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
            if ((config.getBoolean("LLAMA.Drop")) && x <= config.getInt("LLAMA.Chance") + lootLvl) {
                Llama llama = (Llama) entity;

                List<String> loreList = config.getStringList("LLAMA.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());


                switch (llama.getColor()) {
                    case BROWN:
                        item = EntityHead.LLAMA_BROWN.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.LLAMA_GRAY.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.LLAMA_CREAMY.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.LLAMA_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
            if ((config.getBoolean("RAVAGER.Drop")) && x <= config.getInt("RAVAGER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("RAVAGER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.RAVAGER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PILLAGER.Drop")) && x <= config.getInt("PILLAGER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("PILLAGER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PILLAGER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PANDA.Drop")) && x <= config.getInt("PANDA.Chance") + lootLvl) {
                Panda panda = (Panda) entity;

                List<String> loreList = config.getStringList("PANDA.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (panda.getMainGene() == Panda.Gene.BROWN) {
                    item = EntityHead.PANDA_BROWN.getItemStack(loreList);
                } else {
                    item = EntityHead.PANDA.getItemStack(loreList);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("FOX.Drop")) && x <= config.getInt("FOX.Chance") + lootLvl) {
                Fox fox = (Fox) entity;

                List<String> loreList = config.getStringList("FOX.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (fox.getFoxType()) {
                    case RED:

                        item = EntityHead.FOX.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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

                        item = EntityHead.FOX_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()) {
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
            if ((config.getBoolean("CAT.Drop")) && x <= config.getInt("CAT.Chance") + lootLvl) {
                Cat cat = (Cat) entity;

                List<String> loreList = config.getStringList("CAT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (cat.getCatType()) {
                    case BLACK:
                        item = EntityHead.CAT_BLACK.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.CAT_BRITISH.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.CAT_CALICO.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.CAT_JELLIE.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.CAT_PERSIAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
                        item = EntityHead.CAT_RAGDOLL.getItemStack(loreList);
                        HeadDropEvent headDropEvent5 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent5);
                        if (!headDropEvent5.isCancelled()){
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
                        item = EntityHead.CAT_RED.getItemStack(loreList);
                        HeadDropEvent headDropEvent6 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent6);
                        if (!headDropEvent6.isCancelled()){
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
                        item = EntityHead.CAT_SIAMESE.getItemStack(loreList);
                        HeadDropEvent headDropEvent7 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent7);
                        if (!headDropEvent7.isCancelled()){
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
                        item = EntityHead.CAT_TABBY.getItemStack(loreList);
                        HeadDropEvent headDropEvent8 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent8);
                        if (!headDropEvent8.isCancelled()){
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
                        item = EntityHead.CAT_WHITE.getItemStack(loreList);
                        HeadDropEvent headDropEvent9 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent9);
                        if (!headDropEvent9.isCancelled()){
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
            if ((config.getBoolean("BEE.Drop")) && x <= config.getInt("BEE.Chance") + lootLvl) {
                Bee bee = (Bee) entity;

                List<String> loreList = config.getStringList("BEE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                if (bee.getAnger() > 0) {
                    item = EntityHead.BEE_AWARE.getItemStack(loreList);
                } else {
                    item = EntityHead.BEE.getItemStack(loreList);
                }

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("ZOGLIN.Drop")) && x <= config.getInt("ZOGLIN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ZOGLIN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ZOGLIN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("STRIDER.Drop")) && x <= config.getInt("STRIDER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("STRIDER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.STRIDER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PIGLIN.Drop")) && x <= config.getInt("PIGLIN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("PIGLIN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());
                //PIGLIN_HEAD added in 1.20
                if (Bukkit.getServer().getVersion().contains("1.19")){
                    item = EntityHead.PIGLIN.getItemStack(loreList);
                }else {
                    try {
                        item = utils.rename(new ItemStack(Material.PIGLIN_HEAD), config.getString("PIGLIN.Name"), loreList);

                    } catch (NoSuchFieldError e) {
                        item = EntityHead.PIGLIN.getItemStack(loreList);
                    }
                }
                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("HOGLIN.Drop")) && x <= config.getInt("HOGLIN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("HOGLIN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.HOGLIN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("PIGLIN_BRUTE.Drop")) && x <= config.getInt("PIGLIN_BRUTE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("PIGLIN_BRUTE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.PIGLIN_BRUTE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("GLOW_SQUID.Drop")) && x <= config.getInt("GLOW_SQUID.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("GLOW_SQUID.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GLOW_SQUID.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("GOAT.Drop")) && x <= config.getInt("GOAT.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("GOAT.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.GOAT.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("AXOLOTL.Drop")) && x <= config.getInt("AXOLOTL.Chance") + lootLvl) {
                Axolotl axolotl = (Axolotl) entity;

                List<String> loreList = config.getStringList("AXOLOTL.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (axolotl.getVariant()) {
                    case LUCY:
                        item = EntityHead.AXOLOTL_LUCY.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.AXOLOTL_BLUE.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.AXOLOTL_WILD.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
                        item = EntityHead.AXOLOTL_CYAN.getItemStack(loreList);
                        HeadDropEvent headDropEvent3 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent3);
                        if (!headDropEvent3.isCancelled()){
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
                        item = EntityHead.AXOLOTL_GOLD.getItemStack(loreList);
                        HeadDropEvent headDropEvent4 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent4);
                        if (!headDropEvent4.isCancelled()){
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
            if ((config.getBoolean("ALLAY.Drop")) && x <= config.getInt("ALLAY.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("ALLAY.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.ALLAY.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("FROG.Drop")) && x <= config.getInt("FROG.Chance") + lootLvl) {
                Frog frog = (Frog) entity;

                List<String> loreList = config.getStringList("FROG.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                switch (frog.getVariant()) {
                    case TEMPERATE:
                        item = EntityHead.FROG_TEMPERATE.getItemStack(loreList);
                        HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                        if (!headDropEvent.isCancelled()){
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
                        item = EntityHead.FROG_WARM.getItemStack(loreList);
                        HeadDropEvent headDropEvent1 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent1);
                        if (!headDropEvent1.isCancelled()){
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
                        item = EntityHead.FROG_COLD.getItemStack(loreList);
                        HeadDropEvent headDropEvent2 = new HeadDropEvent(item, entity.getKiller(), entity);
                        Bukkit.getServer().getPluginManager().callEvent(headDropEvent2);
                        if (!headDropEvent2.isCancelled()){
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
            if ((config.getBoolean("TADPOLE.Drop")) && x <= config.getInt("TADPOLE.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("TADPOLE.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.TADPOLE.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("WARDEN.Drop")) && x <= config.getInt("WARDEN.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("WARDEN.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.WARDEN.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("CAMEL.Drop")) && x <= config.getInt("CAMEL.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("CAMEL.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.CAMEL.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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
            if ((config.getBoolean("SNIFFER.Drop")) && x <= config.getInt("SNIFFER.Chance") + lootLvl) {

                List<String> loreList = config.getStringList("SNIFFER.Lore");
                loreList = loreList.stream()
                        .map(lore -> lore.replace("{KILLER}", entity.getKiller() != null ? entity.getKiller().getName() : "Unknown"))
                        .map(lore -> lore.replace("{DATE}", LocalDate.now().toString()))
                        .collect(Collectors.toList());

                item = EntityHead.SNIFFER.getItemStack(loreList);

                HeadDropEvent headDropEvent = new HeadDropEvent(item, entity.getKiller(), entity);
                Bukkit.getServer().getPluginManager().callEvent(headDropEvent);
                if (!headDropEvent.isCancelled()){
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