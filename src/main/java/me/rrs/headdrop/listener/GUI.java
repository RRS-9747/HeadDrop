package me.rrs.headdrop.listener;

import me.rrs.headdrop.database.EntityHead;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GUI implements Listener, InventoryHolder {

    private final int itemsPerPage = 45;
    private int currentPage = 0;

    public Inventory getInventory() {
        int totalItems = EntityHead.values().length;
        int totalPages = (totalItems - 1) / itemsPerPage + 1;
        Inventory inventory = Bukkit.createInventory(this, 54, "Heads - Page " + (currentPage + 1) + "/" + totalPages);

        // Add "Previous" button to slot 0
        inventory.setItem(0, createButton(ChatColor.GREEN + "Previous Page"));

        // Add "Next" button to slot 8
        inventory.setItem(8, createButton(ChatColor.GREEN + "Next Page"));

        // Calculate the starting index for the current page
        int startIndex = currentPage * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        // Iterate through the EntityHead values and add items to the inventory within the current page range
        int slot = 9;
        for (int i = startIndex; i < endIndex; i++) {
            EntityHead head = EntityHead.values()[i];
            inventory.setItem(slot, head.getSkull(new ArrayList<>()));
            slot++;
        }

        return inventory;
    }

    private ItemStack createButton(String displayName) {
        ItemStack button = new ItemStack(Material.ARROW);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(displayName);
        button.setItemMeta(meta);
        return button;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof GUI) {
            GUI gui = (GUI) event.getInventory().getHolder();
            Player player = (Player) event.getWhoClicked();

            event.setCancelled(true); // Cancel the event to prevent item dragging

            ItemStack clickedItem = event.getCurrentItem();
            
            if (clickedItem != null && clickedItem.getType() != Material.ARROW && event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                if (player.hasPermission("headdrop.gui.move")){
                    player.getInventory().addItem(clickedItem.clone());
                }
            } else if (clickedItem != null && clickedItem.getType() == Material.ARROW) {
                String displayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                if (displayName.equals("Next Page")) {
                    int totalItems = EntityHead.values().length;
                    int totalPages = (totalItems - 1) / itemsPerPage + 1;
                    gui.currentPage = Math.min(totalPages - 1, gui.currentPage + 1);
                } else if (displayName.equals("Previous Page")) {
                    gui.currentPage = Math.max(0, gui.currentPage - 1);
                }

                // Update the inventory with the new page
                player.openInventory(gui.getInventory());
            }
        }
    }
}