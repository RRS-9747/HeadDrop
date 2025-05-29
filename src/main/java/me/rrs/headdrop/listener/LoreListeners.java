package me.rrs.headdrop.listener;

import com.google.gson.JsonParseException;
import me.rrs.headdrop.HeadDrop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoreListeners implements Listener {

    private final NamespacedKey loreKey;

    public LoreListeners() {
        this.loreKey = new NamespacedKey(HeadDrop.getInstance(), "headdrop_lore");
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.getType() != Material.PLAYER_HEAD || !item.hasItemMeta()) return;

        Block block = event.getBlockPlaced();
        if (!(block.getState() instanceof TileState tileState)) return;

        PersistentDataContainer container = tileState.getPersistentDataContainer();
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.lore() == null) return;

        try {
            String lore = meta.lore().stream()
                    .map(component -> GsonComponentSerializer.gson().serialize(component))
                    .collect(Collectors.joining("§"));
            container.set(loreKey, PersistentDataType.STRING, lore);
            tileState.update();
        } catch (Exception ignored) {
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.PLAYER_HEAD) return;

        if (!(block.getState() instanceof Skull skull)) return;

        PersistentDataContainer container = skull.getPersistentDataContainer();
        if (!container.has(loreKey, PersistentDataType.STRING)) return;

        event.setDropItems(false); // Prevent default drops
        Collection<ItemStack> drops = block.getDrops();

        for (ItemStack drop : drops) {
            if (drop.getType() != Material.PLAYER_HEAD || !drop.hasItemMeta()) continue;

            ItemMeta meta = drop.getItemMeta();
            String loreString = container.get(loreKey, PersistentDataType.STRING);
            if (loreString != null) {
                try {
                    // Split the lore string by "§" and deserialize each JSON to Component
                    List<Component> loreComponents = Arrays.stream(loreString.split("§"))
                            .map(json -> {
                                try {
                                    return GsonComponentSerializer.gson().deserialize(json);
                                } catch (JsonParseException e) {
                                    return Component.empty();
                                }
                            })
                            .collect(Collectors.toList());
                    meta.lore(loreComponents);
                } catch (Exception ignored) {
                }
            }
            drop.setItemMeta(meta);
            block.getWorld().dropItemNaturally(block.getLocation(), drop);
        }
    }
}