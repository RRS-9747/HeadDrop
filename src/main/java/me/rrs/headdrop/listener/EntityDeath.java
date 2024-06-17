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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.IntStream;


public class EntityDeath implements Listener {

    private final YamlDocument config = HeadDrop.getInstance().getConfiguration();
    private final Map<EntityType, Consumer<EntityDeathEvent>> entityActions;
    private final ItemUtils itemUtils;
    private final List<String> loreList;

    public EntityDeath() {
        this.entityActions = new HashMap<>();
        this.itemUtils = new ItemUtils();
        this.loreList = config.getStringList("Lores");
        populateEntityActions(this.config);
    }


    float lootLvl;
    Embed embed;
    String title = null;
    String description = null;
    String footer = null;

    final ItemStack[] item = new ItemStack[1];

    private void updateDatabase(Player player) {
        if (!HeadDrop.getInstance().isFolia()) {
            Bukkit.getScheduler().runTaskAsynchronously(HeadDrop.getInstance(), () -> {
                if (config.getBoolean("Database.Enable")) {
                    String uuid = player.getUniqueId().toString();
                    if (config.getBoolean("Database.Online")) {
                        int count = HeadDrop.getInstance().getDatabase().getDataByUuid(uuid);
                        HeadDrop.getInstance().getDatabase().updateDataByUuid(uuid, player.getName(), count + 1);
                    } else {
                        int count = HeadDrop.getInstance().getDatabase().getDataByName(player.getName());
                        HeadDrop.getInstance().getDatabase().updateDataByName(player.getName(), count + 1);
                    }
                }
            });
        } else {
            if (config.getBoolean("Database.Enable")) {
                String uuid = player.getUniqueId().toString();
                if (config.getBoolean("Database.Online")) {
                    int count = HeadDrop.getInstance().getDatabase().getDataByUuid(uuid);
                    HeadDrop.getInstance().getDatabase().updateDataByUuid(uuid, player.getName(), count + 1);
                } else {
                    int count = HeadDrop.getInstance().getDatabase().getDataByName(player.getName());
                    HeadDrop.getInstance().getDatabase().updateDataByName(player.getName(), count + 1);
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void entityDropHeadEvent(final EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller() != null ? entity.getKiller() : null;

        if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            if (!WorldGuardSupport.canDrop(entity.getLocation())) return;
        }

        if (config.getBoolean("Config.Require-Axe") && (killer == null || !killer.getInventory().getItemInMainHand().getType().toString().contains("_AXE"))) {
            return;
        }

        if (!config.getBoolean("Config.Baby-HeadDrop") && entity instanceof Ageable && !((Ageable) entity).isAdult()) {
            return;
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("LevelledMobs")) {
            if (!entity.getPersistentDataContainer().getKeys().isEmpty() && entity.getType() != EntityType.PLAYER)
                return;
        }

        if (config.getBoolean("Config.Require-Killer-Player") && killer == null) return;

        if (config.getBoolean("Config.Killer-Require-Permission") && (killer == null || !killer.hasPermission("headdrop.killer"))) {
            return;
        }

        if (config.getStringList("Config.Disable-Worlds").contains(entity.getWorld().getName())) return;


        if (config.getBoolean("Bot.Enable") & killer != null) {
            String killerName = killer.getName();
            String mobName = entity.getName();

            title = config.getString("Bot.Title").replace("{KILLER}", killerName).replace("{MOB}", mobName);
            description = config.getString("Bot.Description").replace("{KILLER}", killerName).replace("{MOB}", mobName);
            footer = config.getString("Bot.Footer").replace("{KILLER}", killerName).replace("{MOB}", mobName);

            boolean placeholdersEnabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");

            title = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(killer, title) : title;
            description = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(killer, description) : description;
            footer = placeholdersEnabled ? PlaceholderAPI.setPlaceholders(killer, footer) : footer;
        }

        if (HeadDrop.getInstance().getConfiguration().getBoolean("Config.Enable-Looting")) {
            try {
                lootLvl += entity.getKiller().getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOTING) ?
                        entity.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING) : 0.00F;
            } catch (NoSuchFieldError error) {
                lootLvl += entity.getKiller().getInventory().getItemInMainHand().containsEnchantment(Enchantment.getByName("LOOT_BONUS_MOBS")) ?
                        entity.getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.getByName("LOOT_BONUS_MOBS")) : 0.00F;
            }
        }


        if (config.getBoolean("Config.Enable-Perm-Chance")) {
            lootLvl += IntStream.rangeClosed(1, 100)
                    .map(i -> 101 - i) //
                    .filter(i -> killer.hasPermission("headdrop.chance" + i))
                    .findFirst()
                    .orElse(0);
        }

        embed = new Embed();

        Consumer<EntityDeathEvent> action = entityActions.get(event.getEntityType());
        if (action != null) {
            action.accept(event);
        }
    }

    private void populateEntityActions(YamlDocument config) {
        entityActions.put(EntityType.PLAYER, event -> {
            if ((config.getBoolean("PLAYER.Require-Permission")) && !event.getEntity().hasPermission("headdrop.player")) {
                return;
            }
            if ((config.getBoolean("PLAYER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PLAYER.Chance") + lootLvl) {

                ItemStack skull = SkullCreator.createSkullWithName(event.getEntity().getName());
                itemUtils.addLore(skull, loreList, event.getEntity().getKiller());
                event.getDrops().add(skull);

                if (event.getEntity().getKiller() != null) {
                    if ((config.getBoolean("Bot.Enable"))) {
                        embed.msg(title, description, footer);
                    }
                    updateDatabase(event.getEntity().getKiller());
                }

            }
        });
        try {
            entityActions.put(EntityType.BAT, event -> {
                if ((config.getBoolean("BAT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("BAT.Chance") + lootLvl) {

                    item[0] = EntityHead.BAT.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ENDER_DRAGON, event -> {
                if ((config.getBoolean("ENDER_DRAGON.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ENDER_DRAGON.Chance") + lootLvl) {

                    item[0] = new ItemStack(Material.DRAGON_HEAD);
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE, event -> {
                if ((config.getBoolean("ZOMBIE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ZOMBIE.Chance") + lootLvl) {
                    event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);

                    item[0] = new ItemStack(Material.ZOMBIE_HEAD);
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WITHER_SKELETON, event -> {
                if ((config.getBoolean("WITHER_SKELETON.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WITHER_SKELETON.Chance") + lootLvl) {
                    event.getDrops().removeIf(head -> head.getType() == Material.WITHER_SKELETON_SKULL);

                    item[0] = new ItemStack(Material.WITHER_SKELETON_SKULL);
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.CREEPER, event -> {
                if ((config.getBoolean("CREEPER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("CREEPER.Chance") + lootLvl) {
                    event.getDrops().removeIf(head -> head.getType() == Material.CREEPER_HEAD);

                    item[0] = new ItemStack(Material.CREEPER_HEAD);
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SKELETON, event -> {
                if ((config.getBoolean("SKELETON.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SKELETON.Chance") + lootLvl) {
                    event.getDrops().removeIf(head -> head.getType() == Material.SKELETON_SKULL);

                    item[0] = new ItemStack(Material.SKELETON_SKULL);
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.BLAZE, event -> {
                if ((config.getBoolean("BLAZE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("BLAZE.Chance") + lootLvl) {

                    item[0] = EntityHead.BLAZE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SPIDER, event -> {
                if ((config.getBoolean("SPIDER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SPIDER.Chance") + lootLvl) {

                    item[0] = EntityHead.SPIDER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.CAVE_SPIDER, event -> {
                if ((config.getBoolean("CAVE_SPIDER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("CAVE_SPIDER.Chance") + lootLvl) {

                    item[0] = EntityHead.CAVE_SPIDER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.CHICKEN, event -> {
                if ((config.getBoolean("CHICKEN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("CHICKEN.Chance") + lootLvl) {

                    item[0] = EntityHead.CHICKEN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.COW, event -> {
                if ((config.getBoolean("COW.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("COW.Chance") + lootLvl) {

                    item[0] = EntityHead.COW.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ENDERMAN, event -> {
                if ((config.getBoolean("ENDERMAN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ENDERMAN.Chance") + lootLvl) {

                    item[0] = EntityHead.ENDERMAN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.GIANT, event -> {
                if ((config.getBoolean("GIANT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("GIANT.Chance") + lootLvl) {

                    item[0] = EntityHead.GIANT.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.HORSE, event -> {
                if ((config.getBoolean("HORSE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("HORSE.Chance") + lootLvl) {
                    Horse horse = (Horse) event.getEntity();
                    item[0] = switch (horse.getColor()) {
                        case WHITE -> EntityHead.HORSE_WHITE.getSkull();
                        case CREAMY -> EntityHead.HORSE_CREAMY.getSkull();
                        case CHESTNUT -> EntityHead.HORSE_CHESTNUT.getSkull();
                        case BROWN -> EntityHead.HORSE_BROWN.getSkull();
                        case BLACK -> EntityHead.HORSE_BLACK.getSkull();
                        case GRAY -> EntityHead.HORSE_GRAY.getSkull();
                        case DARK_BROWN -> EntityHead.HORSE_DARK_BROWN.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ILLUSIONER, event -> {
                if ((config.getBoolean("ILLUSIONER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ILLUSIONER.Chance") + lootLvl) {

                    item[0] = EntityHead.ILLUSIONER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.IRON_GOLEM, event -> {
                if ((config.getBoolean("IRON_GOLEM.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("IRON_GOLEM.Chance") + lootLvl) {

                    item[0] = EntityHead.IRON_GOLEM.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.MAGMA_CUBE, event -> {
                if ((config.getBoolean("MAGMA_CUBE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("MAGMA_CUBE.Chance") + lootLvl) {

                    item[0] = EntityHead.MAGMA_CUBE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.MOOSHROOM, event -> {
                if ((config.getBoolean("MUSHROOM_COW.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("MUSHROOM_COW.Chance") + lootLvl) {
                    MushroomCow mushroomCow = (MushroomCow) event.getEntity();

                    if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)) {
                        item[0] = EntityHead.MUSHROOM_COW_RED.getSkull();
                    } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                        item[0] = EntityHead.MUSHROOM_COW_BROWN.getSkull();
                    } else item[0] = null;

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.valueOf("MUSHROOM_COW"), event -> {
                if ((config.getBoolean("MUSHROOM_COW.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("MUSHROOM_COW.Chance") + lootLvl) {
                    MushroomCow mushroomCow = (MushroomCow) event.getEntity();

                    if (mushroomCow.getVariant().equals(MushroomCow.Variant.RED)) {
                        item[0] = EntityHead.MUSHROOM_COW_RED.getSkull();
                    } else if (mushroomCow.getVariant().equals(MushroomCow.Variant.BROWN)) {
                        item[0] = EntityHead.MUSHROOM_COW_BROWN.getSkull();
                    } else item[0] = null;

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.OCELOT, event -> {
                if ((config.getBoolean("OCELOT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("OCELOT.Chance") + lootLvl) {

                    item[0] = EntityHead.OCELOT.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PIG, event -> {
                if ((config.getBoolean("PIG.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PIG.Chance") + lootLvl) {
                    item[0] = EntityHead.PIG.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SHEEP, event -> {
                if ((config.getBoolean("SHEEP.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SHEEP.Chance") + lootLvl) {
                    Sheep sheep = (Sheep) event.getEntity();

                    item[0] = switch (sheep.getColor()) {
                        case WHITE -> EntityHead.SHEEP_WHITE.getSkull();
                        case ORANGE -> EntityHead.SHEEP_ORANGE.getSkull();
                        case MAGENTA -> EntityHead.SHEEP_MAGENTA.getSkull();
                        case LIGHT_BLUE -> EntityHead.SHEEP_LIGHT_BLUE.getSkull();
                        case YELLOW -> EntityHead.SHEEP_YELLOW.getSkull();
                        case LIME -> EntityHead.SHEEP_LIME.getSkull();
                        case PINK -> EntityHead.SHEEP_PINK.getSkull();
                        case GRAY -> EntityHead.SHEEP_GRAY.getSkull();
                        case LIGHT_GRAY -> EntityHead.SHEEP_LIGHT_GRAY.getSkull();
                        case CYAN -> EntityHead.SHEEP_CYAN.getSkull();
                        case PURPLE -> EntityHead.SHEEP_PURPLE.getSkull();
                        case BLUE -> EntityHead.SHEEP_BLUE.getSkull();
                        case BROWN -> EntityHead.SHEEP_BROWN.getSkull();
                        case GREEN -> EntityHead.SHEEP_GREEN.getSkull();
                        case RED -> EntityHead.SHEEP_RED.getSkull();
                        case BLACK -> EntityHead.SHEEP_BLACK.getSkull();
                    };

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SILVERFISH, event -> {
                if ((config.getBoolean("SILVERFISH.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SILVERFISH.Chance") + lootLvl) {

                    item[0] = EntityHead.SILVERFISH.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SLIME, event -> {
                if ((config.getBoolean("SLIME.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SLIME.Chance") + lootLvl) {

                    item[0] = EntityHead.SLIME.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SNOW_GOLEM, event -> {
                if ((config.getBoolean("SNOW_GOLEM.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SNOW_GOLEM.Chance") + lootLvl) {

                    item[0] = EntityHead.SNOWMAN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.valueOf("SNOWMAN"), event -> {
                if ((config.getBoolean("SNOW_GOLEM.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SNOW_GOLEM.Chance") + lootLvl) {

                    item[0] = EntityHead.SNOWMAN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SQUID, event -> {
                if ((config.getBoolean("SQUID.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SQUID.Chance") + lootLvl) {

                    item[0] = EntityHead.SQUID.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WITCH, event -> {
                if ((config.getBoolean("WITCH.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WITCH.Chance") + lootLvl) {

                    item[0] = EntityHead.WITCH.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WITHER, event -> {
                if ((config.getBoolean("WITHER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WITHER.Chance") + lootLvl) {

                    item[0] = EntityHead.WITHER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIFIED_PIGLIN, event -> {
                if ((config.getBoolean("ZOMBIFIED_PIGLIN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ZOMBIFIED_PIGLIN.Chance") + lootLvl) {

                    item[0] = EntityHead.ZOMBIFIED_PIGLIN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.GHAST, event -> {
                if ((config.getBoolean("GHAST.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("GHAST.Chance") + lootLvl) {

                    item[0] = EntityHead.GHAST.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WOLF, event -> {
                if ((config.getBoolean("WOLF.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WOLF.Chance") + lootLvl) {
                    Wolf wolf = (Wolf) event.getEntity();

                    if (wolf.isAngry()) {
                        item[0] = EntityHead.WOLF_ANGRY.getSkull();
                    } else {
                        item[0] = EntityHead.WOLF.getSkull();
                    }
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.VILLAGER, event -> {
                if ((config.getBoolean("VILLAGER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("VILLAGER.Chance") + lootLvl) {
                    Villager villager = (Villager) event.getEntity();

                    item[0] = switch (villager.getProfession()) {
                        case WEAPONSMITH -> EntityHead.VILLAGER_WEAPONSMITH.getSkull();
                        case SHEPHERD -> EntityHead.VILLAGER_SHEPHERD.getSkull();
                        case LIBRARIAN -> EntityHead.VILLAGER_LIBRARIAN.getSkull();
                        case FLETCHER -> EntityHead.VILLAGER_FLETCHER.getSkull();
                        case FISHERMAN -> EntityHead.VILLAGER_FISHERMAN.getSkull();
                        case FARMER -> EntityHead.VILLAGER_FARMER.getSkull();
                        case CLERIC -> EntityHead.VILLAGER_CLERIC.getSkull();
                        case CARTOGRAPHER -> EntityHead.VILLAGER_CARTOGRAPHER.getSkull();
                        case BUTCHER -> EntityHead.VILLAGER_BUTCHER.getSkull();
                        case ARMORER -> EntityHead.VILLAGER_ARMORER.getSkull();
                        case LEATHERWORKER -> EntityHead.VILLAGER_LEATHERWORKER.getSkull();
                        case MASON -> EntityHead.VILLAGER_MASON.getSkull();
                        case TOOLSMITH -> EntityHead.VILLAGER_TOOLSMITH.getSkull();
                        default -> EntityHead.VILLAGER_NULL.getSkull();
                    };

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

                //1.8 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.RABBIT, event -> {
                if ((config.getBoolean("RABBIT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("RABBIT.Chance") + lootLvl) {
                    Rabbit rabbit = (Rabbit) event.getEntity();

                    item[0] = switch (rabbit.getRabbitType()) {
                        case BROWN -> EntityHead.RABBIT_BROWN.getSkull();
                        case WHITE -> EntityHead.RABBIT_WHITE.getSkull();
                        case BLACK -> EntityHead.RABBIT_BLACK.getSkull();
                        case BLACK_AND_WHITE -> EntityHead.RABBIT_BLACK_AND_WHITE.getSkull();
                        case GOLD -> EntityHead.RABBIT_GOLD.getSkull();
                        case SALT_AND_PEPPER -> EntityHead.RABBIT_SALT_AND_PEPPER.getSkull();
                        case THE_KILLER_BUNNY -> EntityHead.RABBIT_THE_KILLER_BUNNY.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ENDERMITE, event -> {
                if ((config.getBoolean("ENDERMITE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ENDERMITE.Chance") + lootLvl) {

                    item[0] = EntityHead.ENDERMITE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.GUARDIAN, event -> {
                if ((config.getBoolean("GUARDIAN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("GUARDIAN.Chance") + lootLvl) {

                    item[0] = EntityHead.GUARDIAN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

                //1.9 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SHULKER, event -> {
                if ((config.getBoolean("SHULKER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SHULKER.Chance") + lootLvl) {

                    item[0] = EntityHead.SHULKER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
                //1.10 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.POLAR_BEAR, event -> {
                if ((config.getBoolean("POLAR_BEAR.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("POLAR_BEAR.Chance") + lootLvl) {

                    item[0] = EntityHead.POLAR_BEAR.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

                //1.11 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE_VILLAGER, event -> {
                if ((config.getBoolean("ZOMBIE_VILLAGER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ZOMBIE_VILLAGER.Chance") + lootLvl) {
                    ZombieVillager zombieVillager = (ZombieVillager) event.getEntity();

                    item[0] = switch (zombieVillager.getVillagerProfession()) {
                        case ARMORER -> EntityHead.ZOMBIE_VILLAGER_ARMORER.getSkull();
                        case BUTCHER -> EntityHead.ZOMBIE_VILLAGER_BUTCHER.getSkull();
                        case CARTOGRAPHER -> EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER.getSkull();
                        case CLERIC -> EntityHead.ZOMBIE_VILLAGER_CLERIC.getSkull();
                        case FARMER -> EntityHead.ZOMBIE_VILLAGER_FARMER.getSkull();
                        case FISHERMAN -> EntityHead.ZOMBIE_VILLAGER_FISHERMAN.getSkull();
                        case FLETCHER -> EntityHead.ZOMBIE_VILLAGER_FLETCHER.getSkull();
                        case LIBRARIAN -> EntityHead.ZOMBIE_VILLAGER_LIBRARIAN.getSkull();
                        case SHEPHERD -> EntityHead.ZOMBIE_VILLAGER_SHEPHERD.getSkull();
                        case WEAPONSMITH -> EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH.getSkull();
                        default -> EntityHead.ZOMBIE_VILLAGER_NULL.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.VINDICATOR, event -> {
                if ((config.getBoolean("VINDICATOR.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("VINDICATOR.Chance") + lootLvl) {

                    item[0] = EntityHead.VINDICATOR.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.VEX, event -> {
                if ((config.getBoolean("VEX.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("VEX.Chance") + lootLvl) {

                    Vex vex = (Vex) event.getEntity();

                    if (vex.isCharging()) {
                        item[0] = EntityHead.VEX_CHARGE.getSkull();
                    } else {
                        item[0] = EntityHead.VEX.getSkull();
                    }

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.EVOKER, event -> {
                if ((config.getBoolean("EVOKER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("EVOKER.Chance") + lootLvl) {

                    item[0] = EntityHead.EVOKER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.HUSK, event -> {
                if ((config.getBoolean("HUSK.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("HUSK.Chance") + lootLvl) {

                    item[0] = EntityHead.HUSK.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.STRAY, event -> {
                if ((config.getBoolean("STRAY.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("STRAY.Chance") + lootLvl) {

                    item[0] = EntityHead.STRAY.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ELDER_GUARDIAN, event -> {
                if ((config.getBoolean("ELDER_GUARDIAN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ELDER_GUARDIAN.Chance") + lootLvl) {

                    item[0] = EntityHead.ELDER_GUARDIAN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.DONKEY, event -> {
                if ((config.getBoolean("DONKEY.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("DONKEY.Chance") + lootLvl) {

                    item[0] = EntityHead.DONKEY.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE_HORSE, event -> {
                if ((config.getBoolean("ZOMBIE_HORSE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ZOMBIE_HORSE.Chance") + lootLvl) {

                    item[0] = EntityHead.ZOMBIE_HORSE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SKELETON_HORSE, event -> {
                if ((config.getBoolean("SKELETON_HORSE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SKELETON_HORSE.Chance") + lootLvl) {

                    item[0] = EntityHead.SKELETON_HORSE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.MULE, event -> {
                if ((config.getBoolean("MULE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("MULE.Chance") + lootLvl) {

                    item[0] = EntityHead.MULE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
                //1.12 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PARROT, event -> {
                if ((config.getBoolean("PARROT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PARROT.Chance") + lootLvl) {
                    Parrot parrot = (Parrot) event.getEntity();

                    item[0] = switch (parrot.getVariant()) {
                        case BLUE -> EntityHead.PARROT_BLUE.getSkull();
                        case CYAN -> EntityHead.PARROT_CYAN.getSkull();
                        case GRAY -> EntityHead.PARROT_GRAY.getSkull();
                        case RED -> EntityHead.PARROT_RED.getSkull();
                        case GREEN -> EntityHead.PARROT_GREEN.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

                //1.13 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.TROPICAL_FISH, event -> {
                if ((config.getBoolean("TROPICAL_FISH.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("TROPICAL_FISH.Chance") + lootLvl) {

                    TropicalFish tropicalFish = (TropicalFish) event.getEntity();

                    item[0] = switch (tropicalFish.getBodyColor()) {
                        case MAGENTA -> EntityHead.TROPICAL_FISH_MAGENTA.getSkull();
                        case LIGHT_BLUE -> EntityHead.TROPICAL_FISH_LIGHT_BLUE.getSkull();
                        case YELLOW -> EntityHead.TROPICAL_FISH_YELLOW.getSkull();
                        case PINK -> EntityHead.TROPICAL_FISH_PINK.getSkull();
                        case GRAY -> EntityHead.TROPICAL_FISH_GRAY.getSkull();
                        case LIGHT_GRAY -> EntityHead.TROPICAL_FISH_LIGHT_GRAY.getSkull();
                        case CYAN -> EntityHead.TROPICAL_FISH_CYAN.getSkull();
                        case BLUE -> EntityHead.TROPICAL_FISH_BLUE.getSkull();
                        case GREEN -> EntityHead.TROPICAL_FISH_GREEN.getSkull();
                        case RED -> EntityHead.TROPICAL_FISH_RED.getSkull();
                        case BLACK -> EntityHead.TROPICAL_FISH_BLACK.getSkull();
                        case ORANGE -> EntityHead.TROPICAL_FISH_ORANGE.getSkull();
                        default -> {
                            Bukkit.getLogger().severe("If you notice this error, pls report it to plugin author");
                            throw new IllegalStateException("Unexpected value: " + tropicalFish.getBodyColor());
                        }
                    };

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PUFFERFISH, event -> {
                if ((config.getBoolean("PUFFERFISH.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PUFFERFISH.Chance") + lootLvl) {

                    item[0] = EntityHead.PUFFERFISH.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SALMON, event -> {
                if ((config.getBoolean("SALMON.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SALMON.Chance") + lootLvl) {

                    item[0] = EntityHead.SALMON.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.COD, event -> {
                if ((config.getBoolean("COD.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("COD.Chance") + lootLvl) {

                    item[0] = EntityHead.COD.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.TURTLE, event -> {
                if ((config.getBoolean("TURTLE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("TURTLE.Chance") + lootLvl) {

                    item[0] = EntityHead.TURTLE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.DOLPHIN, event -> {
                if ((config.getBoolean("DOLPHIN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("DOLPHIN.Chance") + lootLvl) {

                    item[0] = EntityHead.DOLPHIN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PHANTOM, event -> {
                if ((config.getBoolean("PHANTOM.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PHANTOM.Chance") + lootLvl) {

                    item[0] = EntityHead.PHANTOM.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.DROWNED, event -> {
                if ((config.getBoolean("DROWNED.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("DROWNED.Chance") + lootLvl) {

                    item[0] = EntityHead.DROWNED.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }


                //1.14 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WANDERING_TRADER, event -> {
                if ((config.getBoolean("WANDERING_TRADER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WANDERING_TRADER.Chance") + lootLvl) {

                    item[0] = EntityHead.WANDERING_TRADER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.TRADER_LLAMA, event -> {
                if ((config.getBoolean("TRADER_LLAMA.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("TRADER_LLAMA.Chance")) {
                    TraderLlama traderLlama = (TraderLlama) event.getEntity();

                    item[0] = switch (traderLlama.getColor()) {
                        case BROWN -> EntityHead.TRADER_LLAMA_BROWN.getSkull();
                        case WHITE -> EntityHead.TRADER_LLAMA_WHITE.getSkull();
                        case GRAY -> EntityHead.TRADER_LLAMA_GRAY.getSkull();
                        case CREAMY -> EntityHead.TRADER_LLAMA_CREAMY.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.LLAMA, event -> {
                if ((config.getBoolean("LLAMA.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("LLAMA.Chance") + lootLvl) {
                    Llama llama = (Llama) event.getEntity();

                    item[0] = switch (llama.getColor()) {
                        case BROWN -> EntityHead.LLAMA_BROWN.getSkull();
                        case GRAY -> EntityHead.LLAMA_GRAY.getSkull();
                        case CREAMY -> EntityHead.LLAMA_CREAMY.getSkull();
                        case WHITE -> EntityHead.LLAMA_WHITE.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.RAVAGER, event -> {
                if ((config.getBoolean("RAVAGER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("RAVAGER.Chance") + lootLvl) {

                    item[0] = EntityHead.RAVAGER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PILLAGER, event -> {
                if ((config.getBoolean("PILLAGER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PILLAGER.Chance") + lootLvl) {

                    item[0] = EntityHead.PILLAGER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PANDA, event -> {
                if ((config.getBoolean("PANDA.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PANDA.Chance") + lootLvl) {
                    Panda panda = (Panda) event.getEntity();

                    if (panda.getMainGene() == Panda.Gene.BROWN) {
                        item[0] = EntityHead.PANDA_BROWN.getSkull();
                    } else {
                        item[0] = EntityHead.PANDA.getSkull();
                    }

                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.FOX, event -> {
                if ((config.getBoolean("FOX.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("FOX.Chance") + lootLvl) {
                    Fox fox = (Fox) event.getEntity();

                    item[0] = switch (fox.getFoxType()) {
                        case RED -> EntityHead.FOX.getSkull();
                        case SNOW -> EntityHead.FOX_WHITE.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.CAT, event -> {
                if ((config.getBoolean("CAT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("CAT.Chance") + lootLvl) {
                    Cat cat = (Cat) event.getEntity();

                    item[0] = switch (cat.getCatType()) {
                        case BLACK -> EntityHead.CAT_BLACK.getSkull();
                        case BRITISH_SHORTHAIR -> EntityHead.CAT_BRITISH.getSkull();
                        case CALICO -> EntityHead.CAT_CALICO.getSkull();
                        case JELLIE -> EntityHead.CAT_JELLIE.getSkull();
                        case PERSIAN -> EntityHead.CAT_PERSIAN.getSkull();
                        case RAGDOLL -> EntityHead.CAT_RAGDOLL.getSkull();
                        case RED -> EntityHead.CAT_RED.getSkull();
                        case SIAMESE -> EntityHead.CAT_SIAMESE.getSkull();
                        case TABBY -> EntityHead.CAT_TABBY.getSkull();
                        case ALL_BLACK -> EntityHead.CAT_ALL_BLACK.getSkull();
                        case WHITE -> EntityHead.CAT_WHITE.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

                //1.15 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.BEE, event -> {
                if ((config.getBoolean("BEE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("BEE.Chance") + lootLvl) {
                    Bee bee = (Bee) event.getEntity();

                    if (bee.getAnger() > 0) {
                        item[0] = EntityHead.BEE_AWARE.getSkull();
                    } else {
                        item[0] = EntityHead.BEE.getSkull();
                    }
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
                //1.16 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ZOGLIN, event -> {
                if ((config.getBoolean("ZOGLIN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ZOGLIN.Chance") + lootLvl) {

                    item[0] = EntityHead.ZOGLIN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.STRIDER, event -> {
                if ((config.getBoolean("STRIDER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("STRIDER.Chance") + lootLvl) {

                    item[0] = EntityHead.STRIDER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PIGLIN, event -> {
                if ((config.getBoolean("PIGLIN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PIGLIN.Chance") + lootLvl) {

                    item[0] = EntityHead.PIGLIN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.HOGLIN, event -> {
                if ((config.getBoolean("HOGLIN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("HOGLIN.Chance") + lootLvl) {

                    item[0] = EntityHead.HOGLIN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.PIGLIN_BRUTE, event -> {
                if ((config.getBoolean("PIGLIN_BRUTE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("PIGLIN_BRUTE.Chance") + lootLvl) {

                    item[0] = EntityHead.PIGLIN_BRUTE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

                // 1.17 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.GLOW_SQUID, event -> {
                if ((config.getBoolean("GLOW_SQUID.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("GLOW_SQUID.Chance") + lootLvl) {

                    item[0] = EntityHead.GLOW_SQUID.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.GOAT, event -> {
                if ((config.getBoolean("GOAT.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("GOAT.Chance") + lootLvl) {

                    item[0] = EntityHead.GOAT.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.AXOLOTL, event -> {
                if ((config.getBoolean("AXOLOTL.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("AXOLOTL.Chance") + lootLvl) {
                    Axolotl axolotl = (Axolotl) event.getEntity();

                    item[0] = switch (axolotl.getVariant()) {
                        case LUCY -> EntityHead.AXOLOTL_LUCY.getSkull();
                        case BLUE -> EntityHead.AXOLOTL_BLUE.getSkull();
                        case WILD -> EntityHead.AXOLOTL_WILD.getSkull();
                        case CYAN -> EntityHead.AXOLOTL_CYAN.getSkull();
                        case GOLD -> EntityHead.AXOLOTL_GOLD.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }

                //1.19 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.ALLAY, event -> {
                if ((config.getBoolean("ALLAY.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ALLAY.Chance") + lootLvl) {

                    item[0] = EntityHead.ALLAY.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.FROG, event -> {
                if ((config.getBoolean("FROG.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("FROG.Chance") + lootLvl) {
                    Frog frog = (Frog) event.getEntity();

                    item[0] = switch (frog.getVariant()) {
                        case TEMPERATE -> EntityHead.FROG_TEMPERATE.getSkull();
                        case WARM -> EntityHead.FROG_WARM.getSkull();
                        case COLD -> EntityHead.FROG_COLD.getSkull();
                    };
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.TADPOLE, event -> {
                if ((config.getBoolean("TADPOLE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("TADPOLE.Chance") + lootLvl) {

                    item[0] = EntityHead.TADPOLE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.WARDEN, event -> {
                if ((config.getBoolean("WARDEN.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("WARDEN.Chance") + lootLvl) {

                    item[0] = EntityHead.WARDEN.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }

                //1.20 Mob
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.CAMEL, event -> {
                if ((config.getBoolean("CAMEL.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("CAMEL.Chance") + lootLvl) {

                    item[0] = EntityHead.CAMEL.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.SNIFFER, event -> {
                if ((config.getBoolean("SNIFFER.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("SNIFFER.Chance") + lootLvl) {

                    item[0] = EntityHead.SNIFFER.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        //1.21 mob
        try {
            entityActions.put(EntityType.ARMADILLO, event -> {
                if ((config.getBoolean("ARMADILLO.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("ARMADILLO.Chance") + lootLvl) {

                    item[0] = EntityHead.ARMADILLO.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.BREEZE, event -> {
                if ((config.getBoolean("BREEZE.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("BREEZE.Chance") + lootLvl) {

                    item[0] = EntityHead.BREEZE.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }
                }
            });
        } catch (NoSuchFieldError ignored) {
        }
        try {
            entityActions.put(EntityType.BOGGED, event -> {
                if ((config.getBoolean("BOGGED.Drop")) && ThreadLocalRandom.current().nextFloat() * (100.0F - 0.01F) + 0.01F <= config.getFloat("BOGGED.Chance") + lootLvl) {

                    item[0] = EntityHead.BOGGED.getSkull();
                    itemUtils.addLore(item[0], loreList, event.getEntity().getKiller());
                    event.getDrops().add(item[0]);

                    if (event.getEntity().getKiller() != null) {
                        if ((config.getBoolean("Bot.Enable"))) {
                            embed.msg(title, description, footer);
                        }
                        updateDatabase(event.getEntity().getKiller());
                    }

                }
            });
        } catch (NoSuchFieldError ignored) {}

    }
}
