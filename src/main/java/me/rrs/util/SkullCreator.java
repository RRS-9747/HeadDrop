package me.rrs.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

public class SkullCreator {

	private SkullCreator() {}

	private static boolean warningPosted = false;

	private static Field blockProfileField;
	private static Method metaSetProfileMethod;
	private static Field metaProfileField;

	public static ItemStack createSkull() {
		checkLegacy();

		try {
			return new ItemStack(Material.valueOf("PLAYER_HEAD"));
		} catch (IllegalArgumentException e) {
			return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
		}
	}


	public static ItemStack itemFromName(String name) {
		return itemWithName(createSkull(), name);
	}


	public static ItemStack itemFromBase64(String base64) {
		return itemWithBase64(createSkull(), base64);
	}

	@Deprecated
	public static ItemStack itemWithName(ItemStack item, String name) {
		notNull(item, "item");
		notNull(name, "name");

		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(name);
		item.setItemMeta(meta);

		return item;
	}



	public static ItemStack itemWithBase64(ItemStack item, String base64) {
		notNull(item, "item");
		notNull(base64, "base64");

		if (!(item.getItemMeta() instanceof SkullMeta)) {
			return null;
		}
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		mutateItemMeta(meta, base64);
		item.setItemMeta(meta);

		return item;
	}

	@Deprecated
	public static void blockWithName(Block block, String name) {
		notNull(block, "block");
		notNull(name, "name");

		Skull state = (Skull) block.getState();
		state.setOwningPlayer(Bukkit.getOfflinePlayer(name));
		state.update(false, false);
	}


	public static void blockWithUrl(Block block, String url) {
		notNull(block, "block");
		notNull(url, "url");

		blockWithBase64(block, urlToBase64(url));
	}

	public static void blockWithBase64(Block block, String base64) {
		notNull(block, "block");
		notNull(base64, "base64");

		setToSkull(block);
		Skull state = (Skull) block.getState();
		mutateBlockState(state, base64);
		state.update(false, false);
	}

	private static void setToSkull(Block block) {
		checkLegacy();

		try {
			block.setType(Material.valueOf("PLAYER_HEAD"), false);
		} catch (IllegalArgumentException e) {
			block.setType(Material.valueOf("SKULL"), false);
			Skull state = (Skull) block.getState();
			state.setSkullType(SkullType.PLAYER);
			state.update(false, false);
		}
	}

	private static void notNull(Object o, String name) {
		if (o == null) {
			throw new NullPointerException(name + " should not be null!");
		}
	}

	private static String urlToBase64(String url) {

		URI actualUrl;
		try {
			actualUrl = new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl + "\"}}}";
		return Base64.getEncoder().encodeToString(toEncode.getBytes());
	}

	private static GameProfile makeProfile(String b64) {
		UUID id = new UUID(
				b64.substring(b64.length() - 20).hashCode(),
				b64.substring(b64.length() - 10).hashCode()
		);
		GameProfile profile = new GameProfile(id, "Player");
		profile.getProperties().put("textures", new Property("textures", b64));
		return profile;
	}

	private static void mutateBlockState(Skull block, String b64) {
		try {
			if (blockProfileField == null) {
				blockProfileField = block.getClass().getDeclaredField("profile");
				blockProfileField.setAccessible(true);
			}
			blockProfileField.set(block, makeProfile(b64));
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void mutateItemMeta(SkullMeta meta, String b64) {
		try {
			if (metaSetProfileMethod == null) {
				metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
				metaSetProfileMethod.setAccessible(true);
			}
			metaSetProfileMethod.invoke(meta, makeProfile(b64));
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
			try {
				if (metaProfileField == null) {
					metaProfileField = meta.getClass().getDeclaredField("profile");
					metaProfileField.setAccessible(true);
				}
				metaProfileField.set(meta, makeProfile(b64));

			} catch (NoSuchFieldException | IllegalAccessException ex2) {
				ex2.printStackTrace();
			}
		}
	}

	private static void checkLegacy() {
		try {
			Material.class.getDeclaredField("PLAYER_HEAD");
			Material.valueOf("SKULL");

			if (!warningPosted) {
				Bukkit.getLogger().warning("HeadDrop - Using the legacy bukkit API with 1.13+ bukkit versions is not supported!");
				warningPosted = true;
			}
		} catch (NoSuchFieldException | IllegalArgumentException ignored) {}
	}
}