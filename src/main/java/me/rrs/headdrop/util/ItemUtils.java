package me.rrs.headdrop.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {
    public ItemStack rename(ItemStack head, String name, List<String> lore){
        NBTItem nbtI = new NBTItem(head);
        NBTCompound skull = nbtI.addCompound("SkullOwner");
        skull.setString("Name", name);
        ItemMeta meta = nbtI.getItem().getItemMeta();
        meta.setLore(lore);
        head.setItemMeta(meta);

        return head;
    }

    public ItemStack rename(ItemStack head, List<String> lore){
        NBTItem nbtI = new NBTItem(head);
        NBTCompound disp = nbtI.addCompound("display");
        ItemMeta meta = nbtI.getItem().getItemMeta();
        meta.setLore(lore);
        head.setItemMeta(meta);

        return head;
    }

}
