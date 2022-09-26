package me.rrs.util;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
    public static ItemStack rename(ItemStack Head, String Name){
        NBTItem nbti = new NBTItem(Head);
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", Name);

        return nbti.getItem();
    }

}
