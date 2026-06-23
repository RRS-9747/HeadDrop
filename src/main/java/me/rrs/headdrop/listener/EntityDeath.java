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

    private static final Enchantment LOOTING_ENCHANTMENT = Enchantment.LOOTING != null ? Enchantment.LOOTING
            : Enchantment.getByName("LOOT_BONUS_MOBS"); // Legacy support

    public EntityDeath() {
        this.loreList = config.getStringList("Lores");
        populateEntityActions();
        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard")
                && config.getBoolean("Config.WorldGuard-Integration", true);
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
                .replace("{MOB}", mobName)
                .replace("{WEAPON}", killer.getInventory().getItemInMainHand().getType().toString());
        String description = config.getString("Bot.Description", "")
                .replace("{KILLER}", killerName)
                .replace("{MOB}", mobName)
                .replace("{WEAPON}", killer.getInventory().getItemInMainHand().getType().toString());
        String footer = config.getString("Bot.Footer", "")
                .replace("{KILLER}", killerName)
                .replace("{MOB}", mobName)
                .replace("{WEAPON}", killer.getInventory().getItemInMainHand().getType().toString());

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
            List<String> requiredWeapons = config.getStringList("Require-Weapon.Weapons");

            if (requiredWeapons == null || requiredWeapons.isEmpty())
                return false;
            if (killer == null)
                return false;

            ItemStack weapon = killer.getInventory().getItemInMainHand();
            String weaponName = weapon.getType().name();
            return requiredWeapons.stream()
                    .filter(Objects::nonNull)
                    .map(String::toUpperCase)
                    .anyMatch(weaponName::equals);
        }

        if (config.getBoolean("Config.Require-Killer-Player") && killer == null) {
            return false;
        }

        if ((killer == null || !killer.hasPermission("headdrop.killer"))) {
            return false;
        }

        return !config.getStringList("Config.Disable-Worlds")
                .contains(entity.getWorld().getName());
    }

    private String getVariantKey(Object entity) {
        try {
            Object variant = entity.getClass().getMethod("getVariant").invoke(entity);
            return variant instanceof org.bukkit.Keyed k ? k.getKey().getKey() : null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    protected void handleEntityDrop(EntityDeathEvent event, String entityType, Supplier<ItemStack> itemSupplier) {
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

    public void populateEntityActions() {
        try {
            entityActions.put(EntityType.PLAYER, event -> {
                if (!event.getEntity().hasPermission("headdrop.player")) {
                    return;
                }
                handleEntityDrop(event, "PLAYER", () -> SkullCreator.createSkullWithName(event.getEntity().getName()));
            });
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CREEPER, event -> {
                event.getDrops().removeIf(head -> {
                    try {
                        return head.getType() == Material.valueOf("CREEPER_HEAD");
                    } catch (IllegalArgumentException e) {
                        return head.getType() == Material.valueOf("SKULL_ITEM") && head.getDurability() == 4;
                    }
                });
                handleEntityDrop(event, "CREEPER", () -> {
                    Creeper creeper = (Creeper) event.getEntity();
                    if (creeper.isPowered()) {
                        return EntityHead.CREEPER_CHARGED.getSkull();
                    }
                    return EntityHead.CREEPER.getSkull();
                });
            });
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SKELETON, event -> {
                event.getDrops().removeIf(head -> {
                    try {
                        return head.getType() == Material.valueOf("SKELETON_SKULL");
                    } catch (IllegalArgumentException e) {
                        return head.getType() == Material.valueOf("SKULL_ITEM") && head.getDurability() == 0;
                    }
                });
                handleEntityDrop(event, "SKELETON", EntityHead.SKELETON::getSkull);
            });
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WITHER_SKELETON, event -> {
                event.getDrops().removeIf(head -> {
                    try {
                        return head.getType() == Material.valueOf("WITHER_SKELETON_SKULL");
                    } catch (IllegalArgumentException e) {
                        return head.getType() == Material.valueOf("SKULL_ITEM") && head.getDurability() == 1;
                    }
                });
                handleEntityDrop(event, "WITHER_SKELETON", EntityHead.WITHER_SKELETON::getSkull);
            });
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE, event -> {
                event.getDrops().removeIf(head -> {
                    try {
                        return head.getType() == Material.valueOf("ZOMBIE_HEAD");
                    } catch (IllegalArgumentException e) {
                        return head.getType() == Material.valueOf("SKULL_ITEM") && head.getDurability() == 2;
                    }
                });
                handleEntityDrop(event, "ZOMBIE", EntityHead.ZOMBIE::getSkull);
            });
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.BEE, event -> handleEntityDrop(event, "BEE",
                    () -> {
                        Bee bee = (Bee) event.getEntity();
                        boolean angry = bee.getAnger() > 0;
                        boolean pollinated = bee.hasNectar();
                        boolean stung = bee.hasStung();
                        if (pollinated && angry && stung)
                            return EntityHead.BEE_POLLINATED_ANGRY_STUNG.getSkull();
                        if (pollinated && angry)
                            return EntityHead.BEE_POLLINATED_ANGRY.getSkull();
                        if (pollinated && stung)
                            return EntityHead.BEE_POLLINATED_STUNG.getSkull();
                        if (angry && stung)
                            return EntityHead.BEE_ANGRY_STUNG.getSkull();
                        if (pollinated)
                            return EntityHead.BEE_POLLINATED.getSkull();
                        if (angry)
                            return EntityHead.BEE_ANGRY.getSkull();
                        if (stung)
                            return EntityHead.BEE_STUNG.getSkull();
                        return EntityHead.BEE.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }

        try {
            entityActions.put(EntityType.PANDA, event -> handleEntityDrop(event, "PANDA",
                    () -> {
                        Panda panda = (Panda) event.getEntity();
                        return EntityHead.getGenericSkull("PANDA", panda.getMainGene().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }

        try {
            entityActions.put(EntityType.MOOSHROOM, event -> handleEntityDrop(event, "MOOSHROOM",
                    () -> {
                        MushroomCow mushroomCow = (MushroomCow) event.getEntity();
                        return switch (mushroomCow.getVariant()) {
                            case RED -> EntityHead.MUSHROOM_COW_RED.getSkull();
                            case BROWN -> EntityHead.MUSHROOM_COW_BROWN.getSkull();
                        };
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE_VILLAGER, event -> handleEntityDrop(event, "ZOMBIE_VILLAGER",
                    () -> {
                        ZombieVillager zombieVillager = (ZombieVillager) event.getEntity();
                        String profession = zombieVillager.getVillagerProfession().getKey().getKey().toUpperCase();
                        if (profession.equals("NITWIT"))
                            profession = "NONE";
                        String type = zombieVillager.getVillagerType().getKey().getKey().toUpperCase();
                        return EntityHead.getZombieVillagerSkull(profession, type);
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
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
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.TROPICAL_FISH, event -> handleEntityDrop(event, "TROPICAL_FISH",
                    () -> {
                        TropicalFish fish = (TropicalFish) event.getEntity();
                        org.bukkit.DyeColor b = fish.getBodyColor();
                        org.bukkit.DyeColor p = fish.getPatternColor();
                        TropicalFish.Pattern pt = fish.getPattern();
                        return EntityHead.getGenericSkull("TROPICAL_FISH", pt.name() + "_" + b.name() + "_" + p.name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.TRADER_LLAMA, event -> handleEntityDrop(event, "TRADER_LLAMA",
                    () -> {
                        TraderLlama traderLlama = (TraderLlama) event.getEntity();
                        return EntityHead.getGenericSkull("TRADER_LLAMA", traderLlama.getColor().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.LLAMA, event -> handleEntityDrop(event, "LLAMA",
                    () -> {
                        Llama llama = (Llama) event.getEntity();
                        return EntityHead.getGenericSkull("LLAMA", llama.getColor().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.FOX, event -> handleEntityDrop(event, "FOX",
                    () -> {
                        Fox fox = (Fox) event.getEntity();
                        return EntityHead.getGenericSkull("FOX", fox.getFoxType().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CAT, event -> handleEntityDrop(event, "CAT",
                    () -> {
                        Cat cat = (Cat) event.getEntity();
                        String typeStr;
                        try {
                            typeStr = cat.getCatType().getKey().getKey().toUpperCase();
                        } catch (NoSuchMethodError e) {
                            typeStr = cat.getCatType().toString().toUpperCase();
                        }
                        String collar = cat.isTamed() ? "_" + cat.getCollarColor().name() + "_COLLARED" : "";
                        return EntityHead.getGenericSkull("CAT", typeStr + collar);
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.AXOLOTL, event -> handleEntityDrop(event, "AXOLOTL",
                    () -> {
                        Axolotl axolotl = (Axolotl) event.getEntity();
                        return EntityHead.getGenericSkull("AXOLOTL", axolotl.getVariant().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.FROG, event -> handleEntityDrop(event, "FROG", () -> {
                String v = getVariantKey(event.getEntity());

                return "warm".equals(v) ? EntityHead.FROG_WARM.getSkull()
                        : "cold".equals(v) ? EntityHead.FROG_COLD.getSkull()
                                : EntityHead.FROG_TEMPERATE.getSkull();
            }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.HORSE, event -> handleEntityDrop(event, "HORSE",
                    () -> {
                        Horse horse = (Horse) event.getEntity();
                        return EntityHead.getGenericSkull("HORSE", horse.getColor().name());
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
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
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WOLF, event -> handleEntityDrop(event, "WOLF",
                    () -> {
                        Wolf wolf = (Wolf) event.getEntity();
                        String typeStr = "";
                        try {
                            typeStr = wolf.getVariant().getKey().getKey().toUpperCase();
                        } catch (Exception ignored) {
                            typeStr = "PALE";
                        }

                        if (wolf.isAngry()) {
                            return EntityHead.getGenericSkull("WOLF", typeStr + "_ANGRY");
                        } else if (wolf.isTamed()) {
                            return EntityHead.getGenericSkull("WOLF",
                                    typeStr + "_TAME_" + wolf.getCollarColor().name() + "_COLLARED");
                        } else {
                            return EntityHead.getGenericSkull("WOLF", typeStr);
                        }
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.VILLAGER, event -> handleEntityDrop(event, "VILLAGER",
                    () -> {
                        Villager villager = (Villager) event.getEntity();
                        String profession = villager.getProfession().getKey().getKey().toUpperCase();
                        if (profession.equals("NITWIT"))
                            profession = "NONE";
                        String type = villager.getVillagerType().getKey().getKey().toUpperCase();
                        return EntityHead.getVillagerSkull(profession, type);
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.RABBIT, event -> handleEntityDrop(event, "RABBIT",
                    () -> {
                        Rabbit rabbit = (Rabbit) event.getEntity();
                        // Check for "Toast" named rabbit (special skin)
                        if (rabbit.getCustomName() != null && rabbit.getCustomName().equals("Toast")) {
                            return EntityHead.RABBIT_TOAST.getSkull();
                        }
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
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.STRIDER, event -> handleEntityDrop(event, "STRIDER",
                    () -> {
                        Strider strider = (Strider) event.getEntity();
                        String state = strider.isShivering() ? "COLD" : "WARM";
                        String saddled = strider.hasSaddle() ? "_SADDLED" : "";
                        return EntityHead.getGenericSkull("STRIDER", state + saddled);
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }

        try {
            entityActions.put(EntityType.CHICKEN, event -> handleEntityDrop(event, "CHICKEN", () -> {
                String v = getVariantKey(event.getEntity());

                return "cold".equals(v) ? EntityHead.CHICKEN_COLD.getSkull()
                        : "warm".equals(v) ? EntityHead.CHICKEN_WARM.getSkull()
                                : EntityHead.CHICKEN_TEMPERATE.getSkull();
            }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.COW, event -> handleEntityDrop(event, "COW", () -> {
                String v = getVariantKey(event.getEntity());

                return "cold".equals(v) ? EntityHead.COW_COLD.getSkull()
                        : "warm".equals(v) ? EntityHead.COW_WARM.getSkull()
                                : EntityHead.COW_TEMPERATE.getSkull();
            }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PIG, event -> handleEntityDrop(event, "PIG", () -> {
                String v = getVariantKey(event.getEntity());

                return "cold".equals(v) ? EntityHead.PIG_COLD.getSkull()
                        : "warm".equals(v) ? EntityHead.PIG_WARM.getSkull()
                                : EntityHead.PIG_TEMPERATE.getSkull();
            }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CREAKING,
                    event -> handleEntityDrop(event, "CREAKING", EntityHead.CREAKING::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ENDER_DRAGON,
                    event -> handleEntityDrop(event, "ENDER_DRAGON", () -> new ItemStack(Material.DRAGON_HEAD)));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CAVE_SPIDER,
                    event -> handleEntityDrop(event, "CAVE_SPIDER", EntityHead.CAVE_SPIDER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SPIDER,
                    event -> handleEntityDrop(event, "SPIDER", EntityHead.SPIDER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.BLAZE, event -> handleEntityDrop(event, "BLAZE", EntityHead.BLAZE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.BAT, event -> handleEntityDrop(event, "BAT", EntityHead.BAT::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ENDERMAN,
                    event -> handleEntityDrop(event, "ENDERMAN", EntityHead.ENDERMAN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.GIANT, event -> handleEntityDrop(event, "GIANT", EntityHead.GIANT::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ILLUSIONER,
                    event -> handleEntityDrop(event, "ILLUSIONER", EntityHead.ILLUSIONER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.IRON_GOLEM, event -> handleEntityDrop(event, "IRON_GOLEM",
                    () -> {
                        IronGolem golem = (IronGolem) event.getEntity();
                        double healthPercent = golem.getHealth() / golem.getMaxHealth();
                        if (healthPercent <= 0.25)
                            return EntityHead.IRON_GOLEM_HIGH_CRACKINESS.getSkull();
                        if (healthPercent <= 0.50)
                            return EntityHead.IRON_GOLEM_MEDIUM_CRACKINESS.getSkull();
                        if (healthPercent <= 0.75)
                            return EntityHead.IRON_GOLEM_LOW_CRACKINESS.getSkull();
                        return EntityHead.getGenericSkull("IRON_GOLEM", "");
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.MAGMA_CUBE, event -> handleEntityDrop(event, "MAGMA_CUBE",
                    () -> {
                        if (Math.random() < 0.5)
                            return EntityHead.MAGMA_CUBE_LAVA_INSIDE.getSkull();
                        return EntityHead.MAGMA_CUBE.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.OCELOT,
                    event -> handleEntityDrop(event, "OCELOT", EntityHead.OCELOT_WILD_OCELOT::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SILVERFISH,
                    event -> handleEntityDrop(event, "SILVERFISH", EntityHead.SILVERFISH::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SNOW_GOLEM,
                    event -> handleEntityDrop(event, "SNOW_GOLEM", () -> {
                        Snowman snowman = (Snowman) event.getEntity();
                        if (snowman.isDerp()) {
                            return EntityHead.getGenericSkull("SNOWMAN", "DERP");
                        }
                        return EntityHead.SNOWMAN.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SQUID, event -> handleEntityDrop(event, "SQUID", EntityHead.SQUID::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WITCH, event -> handleEntityDrop(event, "WITCH", EntityHead.WITCH::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WITHER, event -> handleEntityDrop(event, "WITHER",
                    () -> {
                        Wither wither = (Wither) event.getEntity();
                        boolean armored = wither.getHealth() > wither.getMaxHealth() / 2.0;
                        boolean invulnerable = wither.getInvulnerabilityTicks() > 0;
                        if (armored && invulnerable)
                            return EntityHead.WITHER_ARMORED_INVULNERABLE.getSkull();
                        if (armored)
                            return EntityHead.WITHER_ARMORED.getSkull();
                        if (invulnerable)
                            return EntityHead.WITHER_INVULNERABLE.getSkull();
                        return EntityHead.WITHER.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIFIED_PIGLIN,
                    event -> handleEntityDrop(event, "ZOMBIFIED_PIGLIN", EntityHead.ZOMBIFIED_PIGLIN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.GHAST, event -> handleEntityDrop(event, "GHAST",
                    () -> {
                        Ghast ghast = (Ghast) event.getEntity();
                        return ghast.isCharging() ? EntityHead.GHAST_SCREAMING.getSkull()
                                : EntityHead.getGenericSkull("GHAST", "");
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ENDERMITE,
                    event -> handleEntityDrop(event, "ENDERMITE", EntityHead.ENDERMITE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.GUARDIAN,
                    event -> handleEntityDrop(event, "GUARDIAN", EntityHead.GUARDIAN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SHULKER, event -> handleEntityDrop(event, "SHULKER",
                    () -> {
                        Shulker shulker = (Shulker) event.getEntity();
                        if (shulker.getColor() == null)
                            return EntityHead.SHULKER.getSkull();
                        return switch (shulker.getColor()) {
                            case BLACK -> EntityHead.SHULKER_BLACK.getSkull();
                            case BLUE -> EntityHead.SHULKER_BLUE.getSkull();
                            case BROWN -> EntityHead.SHULKER_BROWN.getSkull();
                            case CYAN -> EntityHead.SHULKER_CYAN.getSkull();
                            case GRAY -> EntityHead.SHULKER_GRAY.getSkull();
                            case GREEN -> EntityHead.SHULKER_GREEN.getSkull();
                            case LIGHT_BLUE -> EntityHead.SHULKER_LIGHT_BLUE.getSkull();
                            case LIGHT_GRAY -> EntityHead.SHULKER_LIGHT_GRAY.getSkull();
                            case LIME -> EntityHead.SHULKER_LIME.getSkull();
                            case MAGENTA -> EntityHead.SHULKER_MAGENTA.getSkull();
                            case ORANGE -> EntityHead.SHULKER_ORANGE.getSkull();
                            case PINK -> EntityHead.SHULKER_PINK.getSkull();
                            case PURPLE -> EntityHead.SHULKER_PURPLE.getSkull();
                            case RED -> EntityHead.SHULKER_RED.getSkull();
                            case WHITE -> EntityHead.SHULKER_WHITE.getSkull();
                            case YELLOW -> EntityHead.SHULKER_YELLOW.getSkull();
                        };
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.POLAR_BEAR,
                    event -> handleEntityDrop(event, "POLAR_BEAR", EntityHead.POLAR_BEAR::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.VINDICATOR,
                    event -> handleEntityDrop(event, "VINDICATOR", EntityHead.VINDICATOR::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.VEX, event -> handleEntityDrop(event, "VEX",
                    () -> {
                        org.bukkit.entity.Vex vex = (org.bukkit.entity.Vex) event.getEntity();
                        if (vex.isCharging()) {
                            return EntityHead.getGenericSkull("VEX", "CHARGING");
                        } else
                            return EntityHead.getGenericSkull("VEX", "");
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.EVOKER,
                    event -> handleEntityDrop(event, "EVOKER", EntityHead.EVOKER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.HUSK, event -> handleEntityDrop(event, "HUSK", EntityHead.HUSK::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.STRAY, event -> handleEntityDrop(event, "STRAY", EntityHead.STRAY::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ELDER_GUARDIAN,
                    event -> handleEntityDrop(event, "ELDER_GUARDIAN", EntityHead.ELDER_GUARDIAN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.DONKEY,
                    event -> handleEntityDrop(event, "DONKEY", EntityHead.DONKEY::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE_HORSE,
                    event -> handleEntityDrop(event, "ZOMBIE_HORSE", EntityHead.ZOMBIE_HORSE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SKELETON_HORSE,
                    event -> handleEntityDrop(event, "SKELETON_HORSE", EntityHead.SKELETON_HORSE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.MULE, event -> handleEntityDrop(event, "MULE", EntityHead.MULE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PUFFERFISH,
                    event -> handleEntityDrop(event, "PUFFERFISH", EntityHead.PUFFERFISH::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SALMON,
                    event -> handleEntityDrop(event, "SALMON", EntityHead.SALMON::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.COD, event -> handleEntityDrop(event, "COD", EntityHead.COD::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.TURTLE,
                    event -> handleEntityDrop(event, "TURTLE", EntityHead.TURTLE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.DOLPHIN,
                    event -> handleEntityDrop(event, "DOLPHIN", EntityHead.DOLPHIN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PHANTOM,
                    event -> handleEntityDrop(event, "PHANTOM", EntityHead.PHANTOM::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.DROWNED,
                    event -> handleEntityDrop(event, "DROWNED", EntityHead.DROWNED::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WANDERING_TRADER,
                    event -> handleEntityDrop(event, "WANDERING_TRADER", EntityHead.WANDERING_TRADER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.RAVAGER,
                    event -> handleEntityDrop(event, "RAVAGER", EntityHead.RAVAGER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PILLAGER,
                    event -> handleEntityDrop(event, "PILLAGER", EntityHead.PILLAGER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOGLIN,
                    event -> handleEntityDrop(event, "ZOGLIN", EntityHead.ZOGLIN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PIGLIN,
                    event -> handleEntityDrop(event, "PIGLIN", EntityHead.PIGLIN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.HOGLIN,
                    event -> handleEntityDrop(event, "HOGLIN", EntityHead.HOGLIN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PIGLIN_BRUTE,
                    event -> handleEntityDrop(event, "PIGLIN_BRUTE", EntityHead.PIGLIN_BRUTE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.GLOW_SQUID,
                    event -> handleEntityDrop(event, "GLOW_SQUID", EntityHead.GLOW_SQUID::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.GOAT, event -> handleEntityDrop(event, "GOAT",
                    () -> {
                        Goat goat = (Goat) event.getEntity();
                        String screaming = goat.isScreaming() ? "SCREAMING" : "";
                        String horns = "";
                        if (!goat.hasLeftHorn() && !goat.hasRightHorn())
                            horns = "_NO_HORNS";
                        else if (!goat.hasLeftHorn())
                            horns = "_NO_LEFT_HORN";
                        else if (!goat.hasRightHorn())
                            horns = "_NO_RIGHT_HORN";

                        String combined = screaming + horns;
                        if (combined.startsWith("_"))
                            combined = combined.substring(1);
                        return EntityHead.getGenericSkull("GOAT", combined);
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ALLAY, event -> handleEntityDrop(event, "ALLAY", EntityHead.ALLAY::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.TADPOLE,
                    event -> handleEntityDrop(event, "TADPOLE", EntityHead.TADPOLE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.WARDEN,
                    event -> handleEntityDrop(event, "WARDEN", EntityHead.WARDEN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CAMEL, event -> handleEntityDrop(event, "CAMEL", EntityHead.CAMEL::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SNIFFER,
                    event -> handleEntityDrop(event, "SNIFFER", EntityHead.SNIFFER::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ARMADILLO,
                    event -> handleEntityDrop(event, "ARMADILLO", EntityHead.ARMADILLO::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.BREEZE,
                    event -> handleEntityDrop(event, "BREEZE", EntityHead.BREEZE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.BOGGED, event -> handleEntityDrop(event, "BOGGED",
                    () -> {
                        Bogged bogged = (Bogged) event.getEntity();
                        return bogged.isSheared() ? EntityHead.BOGGED_SHEARED.getSkull() : EntityHead.BOGGED.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.valueOf("SNOWMAN"),
                    event -> handleEntityDrop(event, "SNOW_GOLEM", EntityHead.SNOWMAN::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.HAPPY_GHAST, event -> handleEntityDrop(event, "HAPPY_GHAST",
                    () -> {
                        if (event.getEntity() instanceof org.bukkit.entity.Ageable ageable && !ageable.isAdult()) {
                            return EntityHead.HAPPY_GHAST_BABY.getSkull();
                        } else if (event.getEntity() instanceof org.bukkit.entity.Zombie zombie && zombie.isBaby()) {
                            return EntityHead.HAPPY_GHAST_BABY.getSkull();
                        }
                        return EntityHead.HAPPY_GHAST.getSkull();
                    }));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.COPPER_GOLEM,
                    event -> handleEntityDrop(event, "COPPER_GOLEM", EntityHead.COPPER_GOLEM_UNAFFECTED::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.NAUTILUS,
                    event -> handleEntityDrop(event, "NAUTILUS", EntityHead.NAUTILUS::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.PARCHED,
                    event -> handleEntityDrop(event, "PARCHED", EntityHead.PARCHED::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.ZOMBIE_NAUTILUS,
                    event -> handleEntityDrop(event, "ZOMBIE_NAUTILUS",
                            EntityHead.ZOMBIE_NAUTILUS_TEMPERATE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.CAMEL_HUSK,
                    event -> handleEntityDrop(event, "CAMEL_HUSK", EntityHead.CAMEL_HUSK::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
        try {
            entityActions.put(EntityType.SULFUR_CUBE,
                    event -> handleEntityDrop(event, "SULFUR_CUBE", EntityHead.SULFUR_CUBE::getSkull));
        } catch (NoSuchFieldError | IllegalArgumentException ignored) {
        }
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