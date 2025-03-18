package me.rrs.headdrop.listener;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.api.HeadDropAPI;
import me.rrs.headdrop.api.HeadDropEvent;
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
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;


public class EntityDeath implements Listener {

    private final YamlDocument config = HeadDrop.getInstance().getConfiguration();
    private final Map<EntityType, Consumer<EntityDeathEvent>> entityActions = new EnumMap<>(EntityType.class);
    private final ItemUtils itemUtils = new ItemUtils();
    private final List<String> loreList;
    private final Set<UUID> spawnerSpawnedMobs = new HashSet<>();
    private final boolean worldGuardEnabled;

    private static final Enchantment LOOTING_ENCHANTMENT =
            Enchantment.LOOTING != null ? Enchantment.LOOTING :
                    Enchantment.getByName("LOOT_BONUS_MOBS"); // Legacy support

    public EntityDeath() {
        this.loreList = config.getStringList("Lores");
        populateEntityActions(config);
        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
        HeadDropAPI.integrateWithEntityDeath(this);
    }

    private void updateDatabase(Player player, int point) {
        if (!config.getBoolean("Database.Enable")) {
            return;
        }

        final String uuid = player.getUniqueId().toString();
        final boolean useUuid = config.getBoolean("Database.Online");
        final int currentCount = useUuid
                ? HeadDrop.getInstance().getDatabase().getDataByUuid(uuid)
                : HeadDrop.getInstance().getDatabase().getDataByName(player.getName());

        Bukkit.getScheduler().runTaskAsynchronously(HeadDrop.getInstance(), () -> {
            if (useUuid) {
                HeadDrop.getInstance().getDatabase().updateDataByUuid(uuid, player.getName(), currentCount + point);
            } else {
                HeadDrop.getInstance().getDatabase().updateDataByName(player.getName(), currentCount + point);
            }
        });
    }

    private double getLootingLevel(ItemStack item) {
        return item.getEnchantmentLevel(LOOTING_ENCHANTMENT);
    }

    private void sendEmbedMessage(Player killer, LivingEntity entity) {
        if (!config.getBoolean("Bot.Enable") || killer == null) {
            return;
        }

        String killerName = killer.getName();
        String mobName = entity.getName();

        String title = config.getString("Bot.Title", "")
                .replace("{KILLER}", killerName)
                .replace("{MOB}", mobName);
        String description = config.getString("Bot.Description", "")
                .replace("{KILLER}", killerName)
                .replace("{MOB}", mobName);
        String footer = config.getString("Bot.Footer", "")
                .replace("{KILLER}", killerName)
                .replace("{MOB}", mobName);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            title = PlaceholderAPI.setPlaceholders(killer, title);
            description = PlaceholderAPI.setPlaceholders(killer, description);
            footer = PlaceholderAPI.setPlaceholders(killer, footer);
        }

        final String finalTitle = title;
        final String finalDescription = description;
        final String finalFooter = footer;

        Bukkit.getScheduler().runTaskAsynchronously(HeadDrop.getInstance(), () -> {
            new Embed().msg(finalTitle, finalDescription, finalFooter);
        });
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (config.getBoolean("Config.Nerf-Spawner")
                && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            spawnerSpawnedMobs.add(event.getEntity().getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void entityDropHeadEvent(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        if (!config.getBoolean(event.getEntityType() + ".Drop")) {
            return;
        }

        if (spawnerSpawnedMobs.remove(entity.getUniqueId())) {
            return;
        }

        if (!isDropAllowed(entity, killer)) {
            return;
        }

        if (worldGuardEnabled && !WorldGuardSupport.canDrop(entity.getLocation())) {
            return;
        }

        double lootBonus = 0;
        boolean enableLooting = config.getBoolean("Config.Enable-Looting");
        boolean enablePermChance = config.getBoolean("Config.Enable-Perm-Chance");

        if (killer != null) {
            if (enableLooting) {
                lootBonus += getLootingLevel(killer.getInventory().getItemInMainHand());
            }
            if (enablePermChance) {
                int permBonus = IntStream.rangeClosed(1, 100)
                        .filter(i -> killer.hasPermission("headdrop.chance" + i))
                        .max()
                        .orElse(0);
                lootBonus += permBonus;
            }
        }

        Consumer<EntityDeathEvent> action = entityActions.get(entity.getType());
        if (action != null) {
            ActionContext.setLootBonus(lootBonus);
            action.accept(event);
            ActionContext.clearLootBonus();
        }
    }

    private boolean isDropAllowed(LivingEntity entity, Player killer) {
        if (!config.getBoolean("Config.Baby-HeadDrop")
                && entity instanceof Ageable ageable
                && !ageable.isAdult()) {
            return false;
        }

        if (config.getBoolean("Require-Weapon.Enable")) {
            List<String> requiredWeapons = config.getStringList("Weapons");
            if (!requiredWeapons.isEmpty()) {
                if (killer == null) {
                    return false;
                }

                ItemStack weapon = killer.getInventory().getItemInMainHand();
                String weaponName = weapon.getType().toString();

                boolean matches = requiredWeapons.stream()
                        .anyMatch(weaponName::equalsIgnoreCase);
                if (!matches) {
                    return false;
                }
            }
        }

        if (config.getBoolean("Config.Require-Killer-Player") && killer == null) {
            return false;
        }

        if (config.getBoolean("Config.Killer-Require-Permission")
                && (killer == null || !killer.hasPermission("headdrop.killer"))) {
            return false;
        }

        return !config.getStringList("Config.Disable-Worlds")
                .contains(entity.getWorld().getName());
    }

    private void handleEntityDrop(EntityDeathEvent event, String entityType, Supplier<ItemStack> itemSupplier) {
        float baseChance = config.getFloat(entityType + ".Chance");
        float lootBonus = (float) ActionContext.getLootBonus();

        float totalChance = Math.min(baseChance + lootBonus, 100.0F);
        float randomValue = ThreadLocalRandom.current().nextFloat() * 100.0F;

        if (randomValue > totalChance) {
            return;
        }

        Player killer = event.getEntity().getKiller();
        ItemStack headItem = itemSupplier.get();


        HeadDropEvent headDropEvent = new HeadDropEvent(killer, event.getEntity(), headItem);
        Bukkit.getPluginManager().callEvent(headDropEvent);
        if (headDropEvent.isCancelled()) {
            return;
        }

        itemUtils.addLore(headItem, loreList, event.getEntity().getKiller());
        event.getDrops().add(headItem);


        if (killer != null) {
            if (config.getBoolean("Bot.Enable")) {
                sendEmbedMessage(killer, event.getEntity());
            }
            updateDatabase(killer, config.getInt(entityType + ".Point"));
        }
    }

    public static class ActionContext {
        private static final ThreadLocal<Double> lootBonus = new ThreadLocal<>();

        public static void setLootBonus(double bonus) {
            lootBonus.set(bonus);
        }

        public static double getLootBonus() {
            Double bonus = lootBonus.get();
            return bonus != null ? bonus : 0;
        }

        public static void clearLootBonus() {
            lootBonus.remove();
        }
    }

    private void populateEntityActions(YamlDocument config) {
        try {
            entityActions.put(EntityType.PLAYER, event -> {
                if ((config.getBoolean("PLAYER.Require-Permission")) && !event.getEntity().hasPermission("headdrop.player")) {
                    return;
                }
                handleEntityDrop(event, "PLAYER", () -> SkullCreator.createSkullWithName(event.getEntity().getName()));
            });
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.CREEPER, event -> {
                event.getDrops().removeIf(head -> head.getType() == Material.CREEPER_HEAD);
                handleEntityDrop(event, "CREEPER", () -> new ItemStack(Material.CREEPER_HEAD));
            });
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.SKELETON, event -> {
                event.getDrops().removeIf(head -> head.getType() == Material.SKELETON_SKULL);
                handleEntityDrop(event, "SKELETON", () -> new ItemStack(Material.SKELETON_SKULL));
            });
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.WITHER_SKELETON, event -> {
                event.getDrops().removeIf(head -> head.getType() == Material.WITHER_SKELETON_SKULL);
                handleEntityDrop(event, "WITHER_SKELETON", () -> new ItemStack(Material.WITHER_SKELETON_SKULL));
            });
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.ZOMBIE, event -> {
                event.getDrops().removeIf(head -> head.getType() == Material.ZOMBIE_HEAD);
                handleEntityDrop(event, "ZOMBIE", () -> new ItemStack(Material.ZOMBIE_HEAD));
            });
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.BEE, event -> handleEntityDrop(event, "BEE",
                    () -> {
                        Bee bee = (Bee) event.getEntity();
                        return bee.getAnger() > 0 ? EntityHead.BEE_AWARE.getSkull() : EntityHead.BEE.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {}

        try {
            entityActions.put(EntityType.PANDA, event -> handleEntityDrop(event, "PANDA",
                    () -> {
                        Panda panda = (Panda) event.getEntity();
                        return panda.getMainGene() == Panda.Gene.BROWN ? EntityHead.PANDA_BROWN.getSkull() : EntityHead.PANDA.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {}

        try {
            entityActions.put(EntityType.MOOSHROOM, event -> handleEntityDrop(event, "MOOSHROOM",
                    () -> {
                        MushroomCow mushroomCow = (MushroomCow) event.getEntity();
                        return switch (mushroomCow.getVariant()) {
                            case RED -> EntityHead.MOOSHROOM_RED.getSkull();
                            case BROWN -> EntityHead.MOOSHROOM_BROWN.getSkull();
                        };
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {}
        try {
            entityActions.put(EntityType.ZOMBIE_VILLAGER, event -> handleEntityDrop(event, "ZOMBIE_VILLAGER",
                    () -> {
                        ZombieVillager zombieVillager = (ZombieVillager) event.getEntity();
                        return switch (zombieVillager.getVillagerProfession().toString()) {
                            case "ARMORER" -> EntityHead.ZOMBIE_VILLAGER_ARMORER.getSkull();
                            case "BUTCHER" -> EntityHead.ZOMBIE_VILLAGER_BUTCHER.getSkull();
                            case "CARTOGRAPHER" -> EntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER.getSkull();
                            case "CLERIC" -> EntityHead.ZOMBIE_VILLAGER_CLERIC.getSkull();
                            case "FARMER" -> EntityHead.ZOMBIE_VILLAGER_FARMER.getSkull();
                            case "FISHERMAN" -> EntityHead.ZOMBIE_VILLAGER_FISHERMAN.getSkull();
                            case "FLETCHER" -> EntityHead.ZOMBIE_VILLAGER_FLETCHER.getSkull();
                            case "LIBRARIAN" -> EntityHead.ZOMBIE_VILLAGER_LIBRARIAN.getSkull();
                            case "SHEPHERD" -> EntityHead.ZOMBIE_VILLAGER_SHEPHERD.getSkull();
                            case "WEAPONSMITH" -> EntityHead.ZOMBIE_VILLAGER_WEAPONSMITH.getSkull();
                            default -> EntityHead.ZOMBIE_VILLAGER_NULL.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.PARROT, event -> handleEntityDrop(event, "PARROT",
                    () -> {
                        Parrot parrot = (Parrot) event.getEntity();
                        return switch (parrot.getVariant()) {
                            case BLUE -> EntityHead.PARROT_BLUE.getSkull();
                            case CYAN -> EntityHead.PARROT_CYAN.getSkull();
                            case GRAY -> EntityHead.PARROT_GRAY.getSkull();
                            case RED -> EntityHead.PARROT_RED.getSkull();
                            case GREEN -> EntityHead.PARROT_GREEN.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.TROPICAL_FISH, event -> handleEntityDrop(event, "TROPICAL_FISH",
                    () -> {
                        TropicalFish tropicalFish = (TropicalFish) event.getEntity();
                        return switch (tropicalFish.getBodyColor()) {
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
                            default -> throw new IllegalStateException("Unexpected value: " + tropicalFish.getBodyColor());
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.TRADER_LLAMA, event -> handleEntityDrop(event, "TRADER_LLAMA",
                    () -> {
                        TraderLlama traderLlama = (TraderLlama) event.getEntity();
                        return switch (traderLlama.getColor()) {
                            case BROWN -> EntityHead.LLAMA_BROWN.getSkull();
                            case GRAY -> EntityHead.LLAMA_GRAY.getSkull();
                            case CREAMY -> EntityHead.LLAMA_CREAMY.getSkull();
                            case WHITE -> EntityHead.LLAMA_WHITE.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.LLAMA, event -> handleEntityDrop(event, "LLAMA",
                    () -> {
                        Llama llama = (Llama) event.getEntity();
                        return switch (llama.getColor()) {
                            case BROWN -> EntityHead.LLAMA_BROWN.getSkull();
                            case GRAY -> EntityHead.LLAMA_GRAY.getSkull();
                            case CREAMY -> EntityHead.LLAMA_CREAMY.getSkull();
                            case WHITE -> EntityHead.LLAMA_WHITE.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.FOX, event -> handleEntityDrop(event, "FOX",
                    () -> {
                        Fox fox = (Fox) event.getEntity();
                        return switch (fox.getFoxType()) {
                            case RED -> EntityHead.FOX.getSkull();
                            case SNOW -> EntityHead.FOX_WHITE.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.CAT, event -> handleEntityDrop(event, "CAT",
                    () -> {
                        Cat cat = (Cat) event.getEntity();
                        return switch (cat.getCatType().toString()) {
                            case "BLACK" -> EntityHead.CAT_BLACK.getSkull();
                            case "BRITISH_SHORTHAIR" -> EntityHead.CAT_BRITISH.getSkull();
                            case "CALICO" -> EntityHead.CAT_CALICO.getSkull();
                            case "JELLIE" -> EntityHead.CAT_JELLIE.getSkull();
                            case "PERSIAN" -> EntityHead.CAT_PERSIAN.getSkull();
                            case "RAGDOLL" -> EntityHead.CAT_RAGDOLL.getSkull();
                            case "RED" -> EntityHead.CAT_RED.getSkull();
                            case "SIAMESE" -> EntityHead.CAT_SIAMESE.getSkull();
                            case "TABBY" -> EntityHead.CAT_TABBY.getSkull();
                            case "ALL_BLACK" -> EntityHead.CAT_ALL_BLACK.getSkull();
                            case "WHITE" -> EntityHead.CAT_WHITE.getSkull();
                            default -> throw new IllegalStateException("Unexpected value: " + cat.getCatType());
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.AXOLOTL, event -> handleEntityDrop(event, "AXOLOTL",
                    () -> {
                        Axolotl axolotl = (Axolotl) event.getEntity();
                        return switch (axolotl.getVariant()) {
                            case LUCY -> EntityHead.AXOLOTL_LUCY.getSkull();
                            case BLUE -> EntityHead.AXOLOTL_BLUE.getSkull();
                            case WILD -> EntityHead.AXOLOTL_WILD.getSkull();
                            case CYAN -> EntityHead.AXOLOTL_CYAN.getSkull();
                            case GOLD -> EntityHead.AXOLOTL_GOLD.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.FROG, event -> handleEntityDrop(event, "FROG",
                    () -> {
                        Frog frog = (Frog) event.getEntity();
                        return switch (frog.getVariant().toString()) {
                            case "TEMPERATE" -> EntityHead.FROG_TEMPERATE.getSkull();
                            case "WARM" -> EntityHead.FROG_WARM.getSkull();
                            case "COLD" -> EntityHead.FROG_COLD.getSkull();
                            default -> throw new IllegalStateException("Unexpected value: " + frog.getVariant());
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.HORSE, event -> handleEntityDrop(event, "HORSE",
                    () -> {
                        Horse horse = (Horse) event.getEntity();
                        return switch (horse.getColor()) {
                            case WHITE -> EntityHead.HORSE_WHITE.getSkull();
                            case CREAMY -> EntityHead.HORSE_CREAMY.getSkull();
                            case CHESTNUT -> EntityHead.HORSE_CHESTNUT.getSkull();
                            case BROWN -> EntityHead.HORSE_BROWN.getSkull();
                            case BLACK -> EntityHead.HORSE_BLACK.getSkull();
                            case GRAY -> EntityHead.HORSE_GRAY.getSkull();
                            case DARK_BROWN -> EntityHead.HORSE_DARK_BROWN.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.SHEEP, event -> handleEntityDrop(event, "SHEEP",
                    () -> {
                        Sheep sheep = (Sheep) event.getEntity();
                        return switch (sheep.getColor()) {
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
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.WOLF, event -> handleEntityDrop(event, "WOLF",
                    () -> {
                        Wolf wolf = (Wolf) event.getEntity();
                        String variant = wolf.getVariant().toString().toUpperCase().replace("MINECRAFT:", "");
                        return switch (variant) {
                            case "ASHEN" -> EntityHead.WOLF_ASHEN.getSkull();
                            case "BLACK" -> EntityHead.WOLF_BLACK.getSkull();
                            case "CHESTNUT" -> EntityHead.WOLF_CHESTNUT.getSkull();
                            case "RUSTY" -> EntityHead.WOLF_RUSTY.getSkull();
                            case "SNOWY" -> EntityHead.WOLF_SNOWY.getSkull();
                            case "SPOTTED" -> EntityHead.WOLF_SPOTTED.getSkull();
                            case "STRIPED" -> EntityHead.WOLF_STRIPED.getSkull();
                            case "WOODS" -> EntityHead.WOLF_WOODS.getSkull();
                            default -> EntityHead.WOLF_PALE.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.VILLAGER, event -> handleEntityDrop(event, "VILLAGER",
                    () -> {
                        Villager villager = (Villager) event.getEntity();
                        return switch (villager.getProfession().toString()) {
                            case "WEAPONSMITH" -> EntityHead.VILLAGER_WEAPONSMITH.getSkull();
                            case "SHEPHERD" -> EntityHead.VILLAGER_SHEPHERD.getSkull();
                            case "LIBRARIAN" -> EntityHead.VILLAGER_LIBRARIAN.getSkull();
                            case "FLETCHER" -> EntityHead.VILLAGER_FLETCHER.getSkull();
                            case "FISHERMAN" -> EntityHead.VILLAGER_FISHERMAN.getSkull();
                            case "FARMER" -> EntityHead.VILLAGER_FARMER.getSkull();
                            case "CLERIC" -> EntityHead.VILLAGER_CLERIC.getSkull();
                            case "CARTOGRAPHER" -> EntityHead.VILLAGER_CARTOGRAPHER.getSkull();
                            case "BUTCHER" -> EntityHead.VILLAGER_BUTCHER.getSkull();
                            case "ARMORER" -> EntityHead.VILLAGER_ARMORER.getSkull();
                            case "LEATHERWORKER" -> EntityHead.VILLAGER_LEATHERWORKER.getSkull();
                            case "MASON" -> EntityHead.VILLAGER_MASON.getSkull();
                            case "TOOLSMITH" -> EntityHead.VILLAGER_TOOLSMITH.getSkull();
                            default -> EntityHead.VILLAGER_NULL.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {
            entityActions.put(EntityType.RABBIT, event -> handleEntityDrop(event, "RABBIT",
                    () -> {
                        Rabbit rabbit = (Rabbit) event.getEntity();
                        return switch (rabbit.getRabbitType()) {
                            case BROWN -> EntityHead.RABBIT_BROWN.getSkull();
                            case WHITE -> EntityHead.RABBIT_WHITE.getSkull();
                            case BLACK -> EntityHead.RABBIT_BLACK.getSkull();
                            case BLACK_AND_WHITE -> EntityHead.RABBIT_BLACK_AND_WHITE.getSkull();
                            case GOLD -> EntityHead.RABBIT_GOLD.getSkull();
                            case SALT_AND_PEPPER -> EntityHead.RABBIT_SALT_AND_PEPPER.getSkull();
                            case THE_KILLER_BUNNY -> EntityHead.RABBIT_THE_KILLER_BUNNY.getSkull();
                        };
                    }));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.CREAKING, event -> handleEntityDrop(event, "CREAKING", EntityHead.CREAKING::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ENDER_DRAGON, event -> handleEntityDrop(event, "ENDER_DRAGON", () -> new ItemStack(Material.DRAGON_HEAD)));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.CAVE_SPIDER, event -> handleEntityDrop(event, "CAVE_SPIDER", EntityHead.CAVE_SPIDER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SPIDER, event -> handleEntityDrop(event, "SPIDER", EntityHead.SPIDER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.BLAZE, event -> handleEntityDrop(event, "BLAZE", EntityHead.BLAZE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.BAT, event -> handleEntityDrop(event, "BAT", EntityHead.BAT::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.CHICKEN, event -> handleEntityDrop(event, "CHICKEN", EntityHead.CHICKEN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.COW, event -> handleEntityDrop(event, "COW", EntityHead.COW::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ENDERMAN, event -> handleEntityDrop(event, "ENDERMAN", EntityHead.ENDERMAN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.GIANT, event -> handleEntityDrop(event, "GIANT", EntityHead.GIANT::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ILLUSIONER, event -> handleEntityDrop(event, "ILLUSIONER", EntityHead.ILLUSIONER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.IRON_GOLEM, event -> handleEntityDrop(event, "IRON_GOLEM", EntityHead.IRON_GOLEM::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.MAGMA_CUBE, event -> handleEntityDrop(event, "MAGMA_CUBE", EntityHead.MAGMA_CUBE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.OCELOT, event -> handleEntityDrop(event, "OCELOT", EntityHead.OCELOT::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PIG, event -> handleEntityDrop(event, "PIG", EntityHead.PIG::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SILVERFISH, event -> handleEntityDrop(event, "SILVERFISH", EntityHead.SILVERFISH::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SNOW_GOLEM, event -> handleEntityDrop(event, "SNOW_GOLEM", EntityHead.SNOWMAN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SQUID, event -> handleEntityDrop(event, "SQUID", EntityHead.SQUID::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.WITCH, event -> handleEntityDrop(event, "WITCH", EntityHead.WITCH::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.WITHER, event -> handleEntityDrop(event, "WITHER", EntityHead.WITHER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ZOMBIFIED_PIGLIN, event -> handleEntityDrop(event, "ZOMBIFIED_PIGLIN", EntityHead.ZOMBIFIED_PIGLIN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.GHAST, event -> handleEntityDrop(event, "GHAST", EntityHead.GHAST::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ENDERMITE, event -> handleEntityDrop(event, "ENDERMITE", EntityHead.ENDERMITE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.GUARDIAN, event -> handleEntityDrop(event, "GUARDIAN", EntityHead.GUARDIAN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SHULKER, event -> handleEntityDrop(event, "SHULKER", EntityHead.SHULKER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.POLAR_BEAR, event -> handleEntityDrop(event, "POLAR_BEAR", EntityHead.POLAR_BEAR::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.VINDICATOR, event -> handleEntityDrop(event, "VINDICATOR", EntityHead.VINDICATOR::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.VEX, event -> handleEntityDrop(event, "VEX", EntityHead.VEX::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.EVOKER, event -> handleEntityDrop(event, "EVOKER", EntityHead.EVOKER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.HUSK, event -> handleEntityDrop(event, "HUSK", EntityHead.HUSK::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.STRAY, event -> handleEntityDrop(event, "STRAY", EntityHead.STRAY::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ELDER_GUARDIAN, event -> handleEntityDrop(event, "ELDER_GUARDIAN", EntityHead.ELDER_GUARDIAN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.DONKEY, event -> handleEntityDrop(event, "DONKEY", EntityHead.DONKEY::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ZOMBIE_HORSE, event -> handleEntityDrop(event, "ZOMBIE_HORSE", EntityHead.ZOMBIE_HORSE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SKELETON_HORSE, event -> handleEntityDrop(event, "SKELETON_HORSE", EntityHead.SKELETON_HORSE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.MULE, event -> handleEntityDrop(event, "MULE", EntityHead.MULE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PUFFERFISH, event -> handleEntityDrop(event, "PUFFERFISH", EntityHead.PUFFERFISH::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SALMON, event -> handleEntityDrop(event, "SALMON", EntityHead.SALMON::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.COD, event -> handleEntityDrop(event, "COD", EntityHead.COD::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.TURTLE, event -> handleEntityDrop(event, "TURTLE", EntityHead.TURTLE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.DOLPHIN, event -> handleEntityDrop(event, "DOLPHIN", EntityHead.DOLPHIN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PHANTOM, event -> handleEntityDrop(event, "PHANTOM", EntityHead.PHANTOM::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.DROWNED, event -> handleEntityDrop(event, "DROWNED", EntityHead.DROWNED::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.WANDERING_TRADER, event -> handleEntityDrop(event, "WANDERING_TRADER", EntityHead.WANDERING_TRADER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.RAVAGER, event -> handleEntityDrop(event, "RAVAGER", EntityHead.RAVAGER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PILLAGER, event -> handleEntityDrop(event, "PILLAGER", EntityHead.PILLAGER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ZOGLIN, event -> handleEntityDrop(event, "ZOGLIN", EntityHead.ZOGLIN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PIGLIN, event -> handleEntityDrop(event, "PIGLIN", EntityHead.PIGLIN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.HOGLIN, event -> handleEntityDrop(event, "HOGLIN", EntityHead.HOGLIN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.PIGLIN_BRUTE, event -> handleEntityDrop(event, "PIGLIN_BRUTE", EntityHead.PIGLIN_BRUTE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.GLOW_SQUID, event -> handleEntityDrop(event, "GLOW_SQUID", EntityHead.GLOW_SQUID::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.GOAT, event -> handleEntityDrop(event, "GOAT", EntityHead.GOAT::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ALLAY, event -> handleEntityDrop(event, "ALLAY", EntityHead.ALLAY::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.TADPOLE, event -> handleEntityDrop(event, "TADPOLE", EntityHead.TADPOLE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.WARDEN, event -> handleEntityDrop(event, "WARDEN", EntityHead.WARDEN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.CAMEL, event -> handleEntityDrop(event, "CAMEL", EntityHead.CAMEL::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.SNIFFER, event -> handleEntityDrop(event, "SNIFFER", EntityHead.SNIFFER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.STRIDER, event -> handleEntityDrop(event, "STRIDER", EntityHead.STRIDER::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.ARMADILLO, event -> handleEntityDrop(event, "ARMADILLO", EntityHead.ARMADILLO::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.BREEZE, event -> handleEntityDrop(event, "BREEZE", EntityHead.BREEZE::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.BOGGED, event -> handleEntityDrop(event, "BOGGED", EntityHead.BOGGED::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
        try {entityActions.put(EntityType.valueOf("SNOWMAN"), event -> handleEntityDrop(event, "SNOW_GOLEM", EntityHead.SNOWMAN::getSkull));
        }catch (NoSuchFieldError | IllegalArgumentException ignored){}
    }





    public void registerCustomDropHandler(EntityType type, Consumer<EntityDeathEvent> handler) {
        entityActions.put(type, handler);
    }

    public void removeCustomDropHandler(EntityType type) {
        entityActions.remove(type);
    }

    public double getCurrentLootBonus() {
        return ActionContext.getLootBonus();
    }

    public void awardPoints(Player player, int points) {
        updateDatabase(player, points);
    }





}