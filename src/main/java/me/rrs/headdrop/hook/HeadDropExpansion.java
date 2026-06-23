package me.rrs.headdrop.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rrs.headdrop.HeadDrop;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeadDropExpansion extends PlaceholderExpansion {
    private static List<Map.Entry<String, Integer>> leaderboardCache = null;
    private static long lastCacheUpdate = 0;
    @Override
    public @NotNull String getIdentifier() {
        return "headdrop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "RRS";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equals("points") || params.equals("headcount")) {
            if (player == null) return "0";
            if (HeadDrop.getInstance().getDatabase() != null) {
                return String.valueOf(HeadDrop.getInstance().getDatabase().getDataByUuid(player.getUniqueId().toString()));
            }
            return "0";
        }

        // Leaderboard Placeholders: %headdrop_top_name_1%, %headdrop_top_points_1%
        if (params.startsWith("top_")) {
            updateLeaderboardCache();
            String[] parts = params.split("_");
            if (parts.length < 3) return "";
            
            try {
                int rank = Integer.parseInt(parts[2]);
                if (rank < 1 || rank > leaderboardCache.size()) return "N/A";
                
                Map.Entry<String, Integer> entry = leaderboardCache.get(rank - 1);
                if (parts[1].equals("name")) return entry.getKey();
                if (parts[1].equals("points")) return String.valueOf(entry.getValue());
            } catch (NumberFormatException ignored) {}
            return "";
        }

        // Mob Config Placeholders: %headdrop_mob_chance_ZOMBIE%, %headdrop_mob_points_ZOMBIE%, %headdrop_mob_enabled_ZOMBIE%
        if (params.startsWith("mob_")) {
            String[] parts = params.split("_", 3);
            if (parts.length < 3) return "";
            
            String mobName = parts[2].toUpperCase();
            var config = HeadDrop.getInstance().getConfiguration();
            if (!config.contains(mobName)) return "Unknown Mob";

            if (parts[1].equals("chance")) return String.valueOf(config.getFloat(mobName + ".Chance", 0f));
            if (parts[1].equals("points")) return String.valueOf(config.getInt(mobName + ".Point", 0));
            if (parts[1].equals("enabled")) return String.valueOf(config.getBoolean(mobName + ".Drop", false));
        }

        // Real-time Bonus Placeholders (requires online player)
        if (player != null && player.isOnline()) {
            Player onlinePlayer = player.getPlayer();
            if (onlinePlayer != null) {
                if (params.equals("looting_bonus")) return String.valueOf(getLootingLevel(onlinePlayer.getInventory().getItemInMainHand()));
                if (params.equals("perm_bonus")) return String.valueOf(getPermBonus(onlinePlayer));
                if (params.equals("total_bonus")) return String.valueOf(getLootingLevel(onlinePlayer.getInventory().getItemInMainHand()) + getPermBonus(onlinePlayer));
            }
        }

        return "";
    }

    private void updateLeaderboardCache() {
        long now = System.currentTimeMillis();
        // Cache leaderboard for 30 seconds to prevent heavy DB hits
        if (leaderboardCache == null || now - lastCacheUpdate > 30000) {
            if (HeadDrop.getInstance().getDatabase() != null) {
                Map<String, Integer> data = HeadDrop.getInstance().getDatabase().getPlayerData();
                leaderboardCache = data.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .collect(Collectors.toList());
                lastCacheUpdate = now;
            } else {
                leaderboardCache = new ArrayList<>();
            }
        }
    }

    private double getLootingLevel(ItemStack item) {
        if (item == null || item.getType().isAir()) return 0;
        if (!HeadDrop.getInstance().getConfiguration().getBoolean("Config.Enable-Looting", false)) return 0;
        
        // Match the logic in EntityDeath.java
        return item.getEnchantmentLevel(Enchantment.LOOTING);
    }

    private int getPermBonus(Player player) {
        if (!HeadDrop.getInstance().getConfiguration().getBoolean("Config.Enable-Perm-Chance", false)) return 0;
        
        return IntStream.rangeClosed(1, 100)
                .filter(i -> player.hasPermission("headdrop.chance" + i))
                .max()
                .orElse(0);
    }
}
