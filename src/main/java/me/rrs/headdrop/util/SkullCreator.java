package me.rrs.headdrop.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class SkullCreator {

	private SkullCreator() {}

	public static ItemStack createSkull() {
		return new ItemStack(Material.PLAYER_HEAD);
	}

	public static ItemStack createSkullWithName(String name) {
		ItemStack skull = createSkull();
		return itemWithName(skull, name);
	}

	public static ItemStack createSkullWithBase64(String base64, UUID uuid) {
		ItemStack skull = createSkull();
		return itemWithBase64(skull, base64, uuid);
	}

	public static ItemStack itemWithName(ItemStack item, String name) {
		notNull(item, "item");
		notNull(name, "name");

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
		item.setItemMeta(meta);

		return item;
	}

	private static ItemStack itemWithBase64(ItemStack item, String base64, UUID uuid) {
		notNull(item, "item");
		notNull(base64, "base64");

		if (!(item.getItemMeta() instanceof SkullMeta meta)) {
			return null;
		}

		try {
			String json = new String(Base64.getDecoder().decode(base64));

			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			String textureUrl = jsonObject.getAsJsonObject("textures")
					.getAsJsonObject("SKIN")
					.get("url").getAsString();

			PlayerProfile profile = Bukkit.createProfile(uuid);
			PlayerTextures textures = profile.getTextures();
			textures.setSkin(new URL(textureUrl));
			profile.setTextures(textures);

			meta.setOwnerProfile(profile);
			item.setItemMeta(meta);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	private static void notNull(Object o, String name) {
		if (o == null) {
			throw new NullPointerException(name + " should not be null!");
		}
	}
}