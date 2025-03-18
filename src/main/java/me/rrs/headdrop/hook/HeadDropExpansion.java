package me.rrs.headdrop.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rrs.headdrop.HeadDrop;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HeadDropExpansion extends PlaceholderExpansion {
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
        if (player == null || !player.isOnline()) {
            return "";
        }

        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer == null) {
            return "";
        }


        if (HeadDrop.getInstance().getDatabase() != null){
            if (params.equals("headcount")) {
                Map<String, Integer> playerData = HeadDrop.getInstance().getDatabase().getPlayerData();
                int playerHeadCount = playerData.getOrDefault(player.getName(), 0);
                return String.valueOf(playerHeadCount);
            }
        }

        return "";
    }
}
