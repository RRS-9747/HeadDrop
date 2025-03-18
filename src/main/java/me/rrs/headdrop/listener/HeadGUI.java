package me.rrs.headdrop.listener;

import me.rrs.headdrop.HeadDrop;
import me.rrs.headdrop.database.EntityHead;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeadGUI implements InventoryHolder {

    // Configuration Constants
    private static final int INVENTORY_ROWS = 6;
    private static final int ITEMS_PER_PAGE = (INVENTORY_ROWS - 1) * 9;
    private static final int PREVIOUS_SLOT = 0;
    private static final int NEXT_SLOT = 8;
    private static final int INFO_SLOT = 4;
    private static final NamespacedKey BUTTON_KEY = new NamespacedKey(HeadDrop.getInstance(), "gui_button");

    private int currentPage = 0;
    private final List<EntityHead> filteredHeads;

    public HeadGUI() {
        this.filteredHeads = new ArrayList<>(Arrays.asList(EntityHead.values()));
    }

    @Override
    public Inventory getInventory() {
        Component title = Component.text("Mob Heads ", NamedTextColor.BLUE)
                .append(Component.text((currentPage + 1) + "/" + getTotalPages(), NamedTextColor.DARK_GRAY));

        Inventory inv = Bukkit.createInventory(this, INVENTORY_ROWS * 9, title);

        setupControls(inv);
        populateItems(inv);
        return inv;
    }

    private void setupControls(Inventory inv) {
        inv.setItem(PREVIOUS_SLOT, createNavigationItem("Previous", Material.ARROW));
        inv.setItem(NEXT_SLOT, createNavigationItem("Next", Material.ARROW));
        inv.setItem(INFO_SLOT, createInfoItem());
    }

    private ItemStack createNavigationItem(String text, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        Component displayName = Component.text(text, NamedTextColor.GREEN);
        meta.displayName(displayName);
        meta.getPersistentDataContainer().set(BUTTON_KEY, PersistentDataType.STRING, text.toLowerCase());
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createInfoItem() {
        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta meta = info.getItemMeta();
        Component displayName = Component.text("Filter: ", NamedTextColor.GOLD)
                .append(Component.text("", NamedTextColor.WHITE));
        List<Component> lore = List.of(
                Component.text("Current items: " + filteredHeads.size(), NamedTextColor.GRAY)
        );
        meta.displayName(displayName);
        meta.lore(lore);
        info.setItemMeta(meta);
        return info;
    }

    private void populateItems(Inventory inv) {
        int start = currentPage * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, filteredHeads.size());

        for (int i = start; i < end; i++) {
            int slot = 9 + (i - start); // Start from second row
            inv.setItem(slot, filteredHeads.get(i).getSkull());
        }
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) filteredHeads.size() / ITEMS_PER_PAGE);
    }

    public static class GUIListener implements Listener {

        @EventHandler
        public void onInventoryClick(InventoryClickEvent e) {
            if (!(e.getInventory().getHolder() instanceof HeadGUI gui)) return;

            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null) return;

            Player player = (Player) e.getWhoClicked();
            PersistentDataContainer data = clicked.getItemMeta().getPersistentDataContainer();

            if (data.has(BUTTON_KEY, PersistentDataType.STRING)) {
                String buttonType = data.get(BUTTON_KEY, PersistentDataType.STRING);
                handleButtonClick(gui, buttonType, player);
            } else if (e.getRawSlot() < gui.getInventory().getSize()) {
                handleItemTake(player, clicked);
            }
        }

        private void handleButtonClick(HeadGUI gui, String type, Player player) {
            switch (type) {
                case "previous" -> gui.currentPage = Math.max(0, gui.currentPage - 1);
                case "next" -> gui.currentPage = Math.min(gui.getTotalPages() - 1, gui.currentPage + 1);
            }
            player.openInventory(gui.getInventory());
        }

        private void handleItemTake(Player player, ItemStack item) {
            if (player.hasPermission("headdrop.take")) {
                player.getInventory().addItem(item).values().forEach(left ->
                        player.getWorld().dropItem(player.getLocation(), left));
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            }
        }
    }

}