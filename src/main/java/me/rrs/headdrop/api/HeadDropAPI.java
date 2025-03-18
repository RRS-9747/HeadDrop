package me.rrs.headdrop.api;

import me.rrs.headdrop.listener.EntityDeath;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class HeadDropAPI {
    private static final Map<EntityType, DropConfiguration> customDrops = new EnumMap<>(EntityType.class);
    private static EntityDeath entityDeathInstance;

    // Addon developer facing methods
    public static void registerEntityDrop(EntityType entityType, float baseDropChance,
                                          Supplier<ItemStack> headItemSupplier, int pointsValue) {
        DropConfiguration config = new DropConfiguration(baseDropChance, headItemSupplier, pointsValue);
        customDrops.put(entityType, config);

        // If integration already happened, register immediately
        if (entityDeathInstance != null) {
            entityDeathInstance.registerCustomDropHandler(entityType, createHandler(config));
        }
    }

    public static void updateDropChance(EntityType entityType, float newDropChance) {
        DropConfiguration config = customDrops.get(entityType);
        if (config != null) {
            customDrops.put(entityType, config.withBaseChance(newDropChance));
        }
    }

    public static void updateHeadItem(EntityType entityType, Supplier<ItemStack> newHeadSupplier) {
        DropConfiguration config = customDrops.get(entityType);
        if (config != null) {
            customDrops.put(entityType, config.withItemSupplier(newHeadSupplier));
        }
    }

    public static void removeEntityDrop(EntityType entityType) {
        customDrops.remove(entityType);
        if (entityDeathInstance != null) {
            entityDeathInstance.removeCustomDropHandler(entityType);
        }
    }

    // Internal integration
    public static void integrateWithEntityDeath(EntityDeath entityDeath) {
        entityDeathInstance = entityDeath;
        for (Map.Entry<EntityType, DropConfiguration> entry : customDrops.entrySet()) {
            entityDeath.registerCustomDropHandler(entry.getKey(), createHandler(entry.getValue()));
        }
    }

    private static Consumer<EntityDeathEvent> createHandler(DropConfiguration config) {
        return event -> {
            float totalChance = Math.min(config.baseChance + getCurrentLootBonus(), 100.0F);
            if (ThreadLocalRandom.current().nextFloat() * 100.0F > totalChance) return;

            ItemStack headItem = config.itemSupplier.get();
            Player killer = event.getEntity().getKiller();

            HeadDropEvent headDropEvent = new HeadDropEvent(killer, event.getEntity(), headItem);
            Bukkit.getPluginManager().callEvent(headDropEvent);

            if (!headDropEvent.isCancelled()) {
                event.getDrops().add(headItem);
                if (killer != null && entityDeathInstance != null) {
                    entityDeathInstance.awardPoints(killer, config.points);
                }
            }
        };
    }

    private static float getCurrentLootBonus() {
        return entityDeathInstance != null ?
                (float) entityDeathInstance.getCurrentLootBonus() : 0;
    }

    private record DropConfiguration(float baseChance, Supplier<ItemStack> itemSupplier, int points) {
        DropConfiguration withBaseChance(float newChance) {
            return new DropConfiguration(newChance, itemSupplier, points);
        }

        DropConfiguration withItemSupplier(Supplier<ItemStack> newSupplier) {
            return new DropConfiguration(baseChance, newSupplier, points);
        }
    }
}