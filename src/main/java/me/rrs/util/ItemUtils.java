package me.rrs.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemUtils {



    public static void setLore(ItemStack item, String[] lore){
        ItemMeta m = item.getItemMeta();
        m.setLore(Arrays.asList(lore));
        item.setItemMeta(m);
    }

    public static ItemStack rename(ItemStack Head, String Name){
        NBTItem nbti = new NBTItem(Head);
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", Name);

        return nbti.getItem();
    }


}
