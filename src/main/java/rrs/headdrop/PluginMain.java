package rrs.headdrop;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	public static Object GLOBAL_030ddf07dd8262620d2f7fdb0c1d1f22;
	public static Object GLOBAL_1cea0ec6e4ba770d8e7a9f27c916921c;
	public static Object GLOBAL_db8861f205ec91285b13de3615af12e7;
	public static Object GLOBAL_90dc0bc68e52163be7e8a9ce141875d2;
	public static Object GLOBAL_c758fa0ad91c2b48870b858083635f5c;
	public static Object GLOBAL_a671796ed35cc1ce9011ca804342c791;
	public static Object GLOBAL_333a1cfb6e4adc9ba5c486442ab110f1;
	public static Object GLOBAL_b8a78eb5b6583f0108600a11d2be4e00;
	public static Object GLOBAL_feb5c747e777739d766a1922505f0179;
	public static Object GLOBAL_1282fef553a95edb0ec2b449afa3b6b4;
	public static Object GLOBAL_8051931738a0a594438c3e08001c0861;
	public static Object GLOBAL_bae1b74269048261e6a4c04557b57df1;
	public static Object GLOBAL_bf53962c71362bb8a2dbe406ff7c5683;
	public static Object GLOBAL_59b8dbaab602cba1c4aac6ae34b4559d;
	public static Object GLOBAL_1f258800ed81ffa69deca414b51d95a8;
	public static Object GLOBAL_beb85b2a57e1c1abdb79cae3ec0d282a;
	public static Object GLOBAL_a92dbc98ead585a6cbb41284e0d100ff;
	public static Object GLOBAL_bc092b1e64d3683be63460b3cc0ea9fc;
	public static Object GLOBAL_426637b5dc51d57ef12546f5dc460041;
	public static Object GLOBAL_31dbdf36c42fa7e764b123393e0e0c33;
	public static Object GLOBAL_a11b8df5348dd0e46ad7191a8c9c0b95;
	public static Object GLOBAL_a5fb79394a18688823a6c348b7305003;
	public static Object GLOBAL_9e78a9bdac37cc4fb06d64e84560f408;
	public static Object GLOBAL_005a6c133e190ccd6e95c6b4be8229d8;
	public static Object GLOBAL_e430731a9da23e12c3442ec270b1f338;
	public static Object GLOBAL_dbd71fc8558b255d7d977761fad066da;
	public static Object GLOBAL_a12c9a2e3ca6318d46511d247af84630;
	public static Object GLOBAL_1ac04a82f96cfd8be73e60c5cd12e670;
	public static Object GLOBAL_6376ce02ee09345678e0245a1d73b8d1;
	public static Object GLOBAL_f029a071862ce8fce84c06db6123b3ba;
	public static Object GLOBAL_dd5ef31bbfa7ed73b0898406a16838ed;
	public static Object GLOBAL_7cd09eb7c69665b3054ed06b4a52a053;
	public static Object GLOBAL_f577bb0d2c06cc6914352ea792a263e7;
	public static Object GLOBAL_f399510854acd717557f43a75df433c0;
	public static Object GLOBAL_cb50c1d5bc7677bf6ed80d372970d0b9;
	public static Object GLOBAL_1fef70b4555728a9e0365289babd67be;
	public static Object GLOBAL_12ee3e8452c35193157ad3f1d50a591f;
	public static Object GLOBAL_f1f79b24bacb6595e2411c088bdf3784;
	public static Object GLOBAL_89027f20e16bf6b6ad5e2ef219d76ba8;
	public static Object GLOBAL_bfeaa33a472bf0f8655d30be5887b28c;
	public static Object GLOBAL_24859148204a9ce2e50e6a086e50f828;
	public static Object GLOBAL_333d8e29a569a4f9f3f6e4ecab20dbc9;
	public static Object GLOBAL_05bb525fba6635ab5c95cfcf98d4d8e7;
	public static Object GLOBAL_621193cb1db5cfb0581047b02e36f761;
	public static Object GLOBAL_ed84d4eda498f7fc09202e28810526fa;
	public static Object GLOBAL_81b35edbd171ea4c4aadb6412b478db3;
	public static Object GLOBAL_3b126ddcc7a234eb582619d9c5bb3449;
	public static Object GLOBAL_dd2c161b39a4a0e143fe36608fe5e779;
	public static Object GLOBAL_4f4803809935761597928bff373cf30a;
	public static Object GLOBAL_b3cdec33d4ae8d60180d7af81a9798a4;
	public static Object GLOBAL_2a316538ce9a59ea66dbc33d0a877ef3;
	public static Object GLOBAL_2c1541ce0aeb50770e9837749508c92d;
	public static Object GLOBAL_b6122e09ecbefe9133fcfe59dfd4e35b;
	public static Object GLOBAL_88f7fb58299b47098b9402e42a7d52e6;
	public static Object GLOBAL_eebd72ee1d37924f76c1dd2359acd923;
	public static Object GLOBAL_214d86a5752182f20c25b9fce49fbe1c;
	public static Object GLOBAL_97f0398e642d77e91eac7803d7aa550d;
	public static Object GLOBAL_e62dbf694c734f11b41b9fefe86811c0;
	public static Object GLOBAL_699c0f08e68f5fe6e43a13c01f0de7a4;
	public static Object GLOBAL_38b131cfaac1b61a41e58ec81b4f7998;
	public static Object GLOBAL_7eae8800b3abf48eea337d92e36730b7;
	public static org.bukkit.configuration.file.YamlConfiguration PERSISTENT_VARIABLES;
	public static Object GLOBAL_00000000000000000000000000000000;

	@Override
	public void onEnable() {
		instance = this;
		getDataFolder().mkdir();
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		PluginMain.createResourceFile("head.yml");
		PluginMain.createResourceFile("lang/en_US.yml");
		try {
			PluginMain.GLOBAL_030ddf07dd8262620d2f7fdb0c1d1f22 = UUID
					.fromString("b7b9eee2-99f5-43eb-9572-622ddbafe456");
			new Metrics(PluginMain.getInstance(), ((int) (13554d)));
			if (PluginMain.hasGithubUpdate("RRS-9747", "HeadDrop")) {
				PluginMain.getInstance().getLogger().info("There is a new update available!");
				PluginMain.getInstance().getLogger().info(
						("Download it from:  \u00A76https://bit.ly/headdrop\u00A7r or \u00A76https://github.com/RRS-9747/HeadDrop/releases/tag/"
								+ PluginMain.getGithubVersion("RRS-9747", "HeadDrop")));
			}
			PluginMain.getInstance().getLogger().info("Join support server now! \u00A7ehttps://discord.gg/fxYtDsqJfg");
			if (!(PluginMain.checkEquals(PluginMain.getInstance().getDescription().getName(), "HeadDrop")
					&& PluginMain.checkEquals(PluginMain.getInstance().getDescription().getAuthors(),
							new ArrayList(Arrays.asList("RRS"))))) {
				PluginMain.getInstance().getLogger()
						.severe("You changed plugin name or Authors name. Thats not my identity. I can't start :(");
				PluginMain.getInstance().getLogger()
						.info("Download Fresh plugin from: \u00A76https://bit.ly/headdrop\u00A7r");
				Bukkit.getPluginManager().disablePlugin(PluginMain.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getServer().getPluginManager().registerEvents(GUIManager.getInstance(), this);
		GUIManager.getInstance().register("head_main", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("head_main"),
						((int) (9d)), "Head Database");
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ5YzYzYmM1MDg3MjMzMjhhMTllNTk3ZjQwODYyZDI3YWQ1YzFkNTQ1NjYzYWMyNDQ2NjU4MmY1NjhkOSJ9fX0=",
								"Blocks"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRjNmIxOWEwMTQ0ZjYzZmQyNmE2OTBmMjAxNjQwYjRiMmYxNTFlZWY3MmNjNDliZmUwNjEyMDQ0Y2VhNTZlNyJ9fX0=",
								"Number"),
						PluginMain.getNamedItem(((org.bukkit.Material) org.bukkit.Material.ZOMBIE_HEAD), "Monster"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwMzU2NDI2Yzg2ZjIwMWY3NmNhOTVkOWEwZTViNGY3NDA5ZGMwYjEyYmY1YWQ0MDIzNjJjMzhjOTM3Y2JjZCJ9fX0=",
								"Food"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		GUIManager.getInstance().register("Mob_Head_1", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("Mob_Head_1"),
						((int) (54d)), "Monsters");
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19",
								"Cave Spider Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==",
								"Blaze Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==",
								"Chicken Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RmYTBhYzM3YmFiYTJhYTI5MGU0ZmFlZTQxOWE2MTNjZDYxMTdmYTU2OGU3MDlkOTAzNzQ3NTNjMDMyZGNiMCJ9fX0=",
								"Cow Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0=",
								"Enderman Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQwNjI5MWM0ODI0NWVjNTA3OGJhZTE1NzEwMGNjYWUzNmM5NTNjMTQwY2NiNDNlMWYzOTU2OTcyNjI4MWNmMCJ9fX0=",
								"Ghast Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=",
								"Magma Cube Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==",
								"MushroomCow Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19",
								"Sheep Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0=",
								"Slime Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19",
								"Villager Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19",
								"Iron Golem Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1N2NkNWMyOTg5ZmY5NzU3MGZlYzRkZGNkYzY5MjZhNjhhMzM5MzI1MGMxYmUxZjBiMTE0YTFkYjEifX19",
								"Ocelot Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=",
								"Pig Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZWQ0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19",
								"Wither Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0=",
								"Creeper Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE2MmRkMGI5ZjY1YjU4YTFlNzBmODFkOGUwM2U4ZmY2YzUzZTRlOTg1YmRiZTAxODY1NThkOGE2OWE4MTE4OSJ9fX0=",
								"Bee Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ==",
								"Evoker Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ==",
								"Fox Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY2MjMzNmQ4YWUwOTI0MDdlNThmN2NjODBkMjBmMjBlNzY1MDM1N2E0NTRjZTE2ZTMzMDc2MTlhMDExMDY0OCJ9fX0=",
								"Goat Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==",
								"Guardian Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5Yjk3MzRkMGU3YmYwNjBmZWRjNmJmN2ZlYzY0ZTFmN2FkNmZjODBiMGZkODQ0MWFkMGM3NTA4Yzg1MGQ3MyJ9fX0=",
								"Husk Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q2ZjRhMjFlMGQ2MmFmODI0Zjg3MDhhYzYzNDEwZjFhMDFiYmI0MWQ3ZjRhNzAyZDk0NjljNjExMzIyMiJ9fX0=",
								"Parrot Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0=",
								"Rabbit Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzM2E0YjczMjczYTY0YzhhYjI4MzBiMGZmZjc3N2E2MWE0ODhjOTJmNjBmODNiZmIzZTQyMWY0MjhhNDQifX19",
								"Shulker Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0=",
								"Strider Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ==",
								"Turtle Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ==",
								"Vex Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlYWVjMzQ0YWIwOTViNDhjZWFkNzUyN2Y3ZGVlNjFiMDYzZmY3OTFmNzZhOGZhNzY2NDJjODY3NmUyMTczIn19fQ==",
								"Vindicator Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3ZDZmZDBhOWNlNmJmNmZlMjJhM2VhODk1NmFkMWQwMTI0MTVkNmJiYTYxNmMzMWJhOTlhMmJkMWMwMzc2YSJ9fX0=",
								"Wolf Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==",
								"Zombie Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==",
								"Skeleton Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc5NmFhNmQxOGVkYzViNzI0YmQ4OWU5ODNiYzMyMTVhNDFiZjc3NWQxMTI2MzVlOWI1ODM1ZDFiOGFkMjBjYiJ9fX0=",
								"Bat Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyNTNmYzZiNjU2OTg4NDUzYTJkNzEzOGZjYTRkMWYyNzUyZjQ3NjkxZjBjNDM0ZTQzMjE4Mzc3MWNmZTEifX19",
								"Cat Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0=",
								"Cod Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNmMzYzA1MmNmNzg3ZDIzNmEyOTE1ZjgwNzJiNzdjNTQ3NDk3NzE1ZDFkMmY4Y2JjOWQyNDFkODhhIn19fQ==",
								"Donkey Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYxNWMyN2NiN2MzNWE2ZTdhYTc2MzU3M2RkYmZmOTgwODUxNmJkYWJjMjNmMWNiNTE2MzkxOGM0YTEzOTIifX19",
								"Horse Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19",
								"Mule Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWYxODEwN2QyNzVmMWNiM2E5Zjk3M2U1OTI4ZDU4NzlmYTQwMzI4ZmYzMjU4MDU0ZGI2ZGQzZTdjMGNhNjMzMCJ9fX0=",
								"Piglin Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1ZDYwYTRkNzBlYzEzNmE2NTg1MDdjZTgyZTM0NDNjZGFhMzk1OGQ3ZmNhM2Q5Mzc2NTE3YzdkYjRlNjk1ZCJ9fX0=",
								"Polar Bear Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNTI4NzZiYzNhOTZkZDJhMjI5OTI0NWVkYjNiZWVmNjQ3YzhhNTZhYzg4NTNhNjg3YzNlN2I1ZDhiYiJ9fX0=",
								"Pufferfish Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlYTlhMjIzNjIwY2RiNTRiMzU3NDEzZDQzYmQ4OWM0MDA4YmNhNmEyMjdmM2I3ZGI5N2Y3NzMzZWFkNWZjZiJ9fX0=",
								"Salmon Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==",
								"Skeleton Horse Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZkZmQxZjc1MzhjMDQwMjU4YmU3YTkxNDQ2ZGE4OWVkODQ1Y2M1ZWY3MjhlYjVlNjkwNTQzMzc4ZmNmNCJ9fX0=",
								"Snow Golem Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=",
								"Wandering Trader Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzZDgyMjVmOGY1Yjk4NTk3ZGYxNWZkOTJiZjY5NTlhZWZkNGM1YmVjOTkxNGRkNjNjYWEwYzMyOWM3YTA2YiJ9fX0=",
								"Back"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				guiInventory.setItem(((int) (53d)), PluginMain.getSkull(
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJmM2EyZGZjZTBjM2RhYjdlZTEwZGIzODVlNTIyOWYxYTM5NTM0YThiYTI2NDYxNzhlMzdjNGZhOTNiIn19fQ==",
						"Next Page"));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		GUIManager.getInstance().register("Mob_Head_2", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("Mob_Head_2"),
						((int) (54d)), "Monsters");
				guiInventory.setItem(((int) (45d)), PluginMain.getSkull(
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzZDgyMjVmOGY1Yjk4NTk3ZGYxNWZkOTJiZjY5NTlhZWZkNGM1YmVjOTkxNGRkNjNjYWEwYzMyOWM3YTA2YiJ9fX0=",
						"Back"));
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U5NTE1M2VjMjMyODRiMjgzZjAwZDE5ZDI5NzU2ZjI0NDMxM2EwNjFiNzBhYzAzYjk3ZDIzNmVlNTdiZDk4MiJ9fX0=",
								"Phantom Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxMzhmNDAxYzY3ZmMyZTFlMzg3ZDljOTBhOTY5MTc3MmVlNDg2ZThkZGJmMmVkMzc1ZmM4MzQ4NzQ2ZjkzNiJ9fX0=",
								"Axolotl Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0=",
								"Glow Squid Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0=",
								"Stray Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=",
								"Witch Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0=",
								"Hoglin Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0=",
								"Endermite Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgyNjI1ZmZhOTEwYThhOTdmODcxNDBhOTQ3NmI5YmRkZjA1ODZkMmFjNDVkMDI5NjMwYjA2MjVkYjA0YjUwZCJ9fX0=",
								"Enderbrine"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyMGM4NGJlZGQ0YzEwYjZkMGZlMDljNWJhZDQwOTVjMWJhNDQxMzYzNzM5OTQ3ZWU0NDk2ZWUyN2I1NzkwOCJ9fX0=",
								"Enderman"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFiYTVjYmU0ZWJjNzAyZDI0NmIwODFjNjY3NTFiMTYxZjY2YjAxNjhlZTdlZGQ5MTY0NmRhZDRmYWU5YjczYSJ9fX0=",
								"Green Enderman"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNiYzBjNWVhMTg2YzI3YTY0ZjZjMjBkYTE5ODU1ZDJlMDkxMGQ0OTVmMDk4NWJmYjAxY2EzYzdkMTUxNGNhNyJ9fX0=",
								"Bloodshot Eye"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThlOTlmYzRhYmM4YmIwOGEzYjk1YWI4OGJlMzkzOWU5MjM5OTQyNmQ1YWEyM2ZiYjFhNmQwMWFiNzY1YjA5NSJ9fX0=",
								"Eye"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVjMjIyNGM1ZDA0ZDcwNTliZTNiYWVkYjcxZjY2YWM0MGZlZWEyNjYwMDlmNjMyMWVlMTQyMzA3NmFiM2U3NCJ9fX0=",
								"Dragon"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzcwZTk2MzZiZGRiYzE0MGQyZDc0ODE5NGJkN2UxZGZiZGE0NTI2ZTk2MGMzNzMzZWI3ZGFlMTRiMWY2MDdmNiJ9fX0=",
								"Enderman King"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJhOTY0N2VjN2M4ZjM1OWQ4ZDA5NTJiZGJmNzJjYmI0YjU3NDNjZjg0NTVkY2I3NjY0ZTJiZjliZGY4YjcxOCJ9fX0=",
								"Dragon Eye"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE4NGNjODgxOGMyOTM0ODRmZGFhZmM4ZmEyZjBiZjM5ZTU1NzMzYTI0N2Q2ODAyM2RmMmM2YzZiOWI2NzFkMCJ9fX0=",
								"Spooky Enderman"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE4Y2Q0NTdmYmFmMzI3ZmEzOWYxMGI1YjM2MTY2ZmQwMTgyNjQwMzY4NjUxNjRjMDJkOWU1ZmY1M2Y0NSJ9fX0=",
								"Llama Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODAxOGExNzcxZDY5YzExYjhkYWQ0MmNkMzEwMzc1YmEyZDgyNzkzMmIyNWVmMzU3ZjdlNTcyYzFiZDBmOSJ9fX0=",
								"Panda Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==",
								"Spider Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0=",
								"Zombified Piglin Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0=",
								"Drowned Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkyMDg5NjE4NDM1YTBlZjYzZTk1ZWU5NWE5MmI4MzA3M2Y4YzMzZmE3N2RjNTM2NTE5OWJhZDMzYjYyNTYifX19",
								"Elder Guardian Head"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==",
								"Dolphin  Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		GUIManager.getInstance().register("Mini_Block", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("Mini_Block"),
						((int) (54d)), "Blocks");
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODViNGFiZDRmMDdiNjg5NDYwN2NiZDg3MDg2OGY2N2UwMjVjN2ZiNTUyYTFhNTdmNTZmNzdjMDQ0Y2NhNDFjZSJ9fX0=",
								"Gold Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM4NWFhZWRkNzg0ZmFlZjhlOGY2Zjc4MmZhNDhkMDdjMmZjMmJiY2Y2ZmVhMWZiYzliOTg2MmQwNWQyMjhjMSJ9fX0=",
								"Iron Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEwMDFiNDI1MTExYmZlMGFjZmY3MTBhOGI0MWVhOTVlM2I5MzZhODVlNWJiNjUxNzE2MGJhYjU4N2U4ODcwZiJ9fX0=",
								"Lapis Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzExMTA3ZjcwZjhjYTA0NzRmMDIzMjQzYmQzODJiYmQ2YjQxNDlhZWY0ZjQyYjI1ZGRiYmNmZWM4Nzk4YjRkYyJ9fX0=",
								"Coal Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzMzYjZjOTA3ZjFjMmExYWU1NGY5MGFhZmJjOWU1NjFmMmY0ZGQ0ZWM0YjczZTU2ZDU0OTU1YmMxZGZjYzJhMCJ9fX0=",
								"Diamond Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE0MGJhZWI5NmZlYTFiZDZlZTA2NDY5NmNkYjc0ZmZkMDhhNmY3YzQwNjE3ZDQ2MmU0ZTJkYThmYWFmNzNlNSJ9fX0=",
								"Emerald Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI1NDM2ZThjNGY3MDRjZjg0NmVlOTQ1YzdlNmNmYjA1ZjM4OGYxYjAzZDExN2VlNDU5N2RhMmEwZjc5NzcxNCJ9fX0=",
								"Wood Log"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNiYWVhZDkzMWJlNTdkODU4NDE2ZWVlNGU1ZWY5ZjU5Mjg2MDg5NTM1OGU3ZTZkNTJhMmVkZTc3YzEzMmU5MyJ9fX0=",
								"Jukebox"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4MDAwZDU0MTk4NjBmZjNkZTUzYmE1NTliOWVjZTc5ZmIxYjY0OGUwOTlmMDIzYmE0ODA4NGNkY2VmOGIwYyJ9fX0=",
								"End Portal Frame"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBkZDZkZGRiZWIyZjU3MjNkM2ZhZDI5Njc5MjgzOGRlNWFlYmFhNjQzNzlkMmNhNTdhODMxNDU2NmMzZmI4OCJ9fX0=",
								"Sculk Chute"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk4MGZlNzYyZjQ4ODYxMzk3ZjBjOGRjMmY4ZDEzZjdjMTY2MTcwMDM4ZTk3MzAyMDY4OTE5MmE4OTMwOGYzZCJ9fX0=",
								"Netherite Block"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTViY2U2MmRkNTRiMWFkN2QwYmNiMjliN2EzMTE0NDQ3NTc2M2Y3OTM1Mzc4ZWM5NjIwN2JmMWM1NzRkZDc2MyJ9fX0=",
								"Ancient Debris"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJmZDJkZTUzZWIxYmZlOTJmNWNjMTc4MGEyNzI3MzBlYmU3NTk0NzNiYjI1YTUzNjQ3ZDM3MTJlZTVmNjA5NSJ9fX0=",
								"Diamond"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI0MzBhOTE2MTUxNDI3YzZjODcxZmUyZjZiNWE0ZTdkMTc3OTRhYzk3NzVhODdmYmMzMDNlMTNlNWJjYTI5ZiJ9fX0=",
								"Bricks"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMwZTZkOWUyNDI3MzU0ODE5MThjNWZkMTQ0OThiZDc2MGJiOWY0ZmY2NDMwYWQ0Njk2YjM4ZThhODgzZGE5NyJ9fX0=",
								"Emerald Block"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE5ZTM2YTg3YmFmMGFjNzYzMTQzNTJmNTlhN2Y2M2JkYjNmNGM4NmJkOWJiYTY5Mjc3NzJjMDFkNGQxIn19fQ==",
								"Grass"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFlODI2ZTdkYjg0NDdmYmQ2Mjk4OGZlZTBlODNiYmRkNjk0Mzc4YWVmMTJkMjU3MmU5NzVmMDU5YTU0OTkwIn19fQ==",
								"Oak Log",
								new ArrayList(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "rounded")))),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTc5ZDBlOTZjMzUxZTJlMmQ5MDQyYTQ2ODJkZWM0MzBjNGU1MjI1NmVkNzdjZjgwYmQwMjY3ZjdjYWJlMzMwMCJ9fX0=",
								"Crying Obsidian"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0ODc2YjZhNWQ2ZGQ3ODVlMDkxZmQxMzRhMjFjOTFkMGE5Y2FjNWE2MjJlNDQ4YjVmZmNiNjVlZjQ1Mjc4In19fQ==",
								"Amethyst Block"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI2NDQwNzFiNmM3YmJhZTdiNWU0NWQ5ZjgyZjk2ZmZiNWVlOGUxNzdhMjNiODI1YTQ0NjU2MDdmMWM5YyJ9fX0=",
								"Ice Block"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZhYWI1OGZhMDFmY2U5YWY0NjllZDc0N2FlZDgxMWQ3YmExOGM0NzZmNWE3ZjkwODhlMTI5YzMxYjQ1ZjMifX19",
								"Packed Ice Block"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiNzQ3YjM3OGE0MWEwYTZlZGM4NmMwMDBmMDQwYzY5OTRhODMzMjUxMTk2YzlkNTJjMmEyMzBmOTUxNjBjYyJ9fX0=",
								"Rainbow Gem"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU3NzVmZjFkYWE0NmI3YzJiZWFiNTYzMTI5ZDM0NjQ0YzVkNWY5MTg2YmIxM2M1MTZhNWUwNWYwY2Q0ZTQ1MSJ9fX0=",
								"Burning Netherrack"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODExMmY4N2NlZTU3ODg5NGUyZDA3MjUzYWJiMTQ2NjI0N2NlZTQ4ZjE3MjdiYjlkMWVhYzUzZjhlMDU3MTAxMiJ9fX0=",
								"Multi Ore"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmUyMTIzOTFkODgwMDcwMjFiZTIwODNmZDEwMWIyZDc0YTMwNjUxMTU2NjkzOWIzY2M3ZjY2YTA0Y2Q4OGRhMyJ9fX0=",
								"Chiseled Sandstone"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4ZTRlZDVjODRjZmExZTJkMDAyZDYyMDM5M2E0MDMzMzU0NjFjZWNkMWJmODgyODAzYjJkOTI5NGM0NTRkMSJ9fX0=",
								"Magma Bricks"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiMWI5Y2UyZTlhNmNlOGE5ODVkMzk3NzZlMjkwODA3N2I4MmU2YTMzM2QyYTgxYTQ0MTQzOGVhYjM5ZjhlMSJ9fX0=",
								"Blaze Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				guiInventory.setItem(((int) (45d)), PluginMain.getSkull(
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzZDgyMjVmOGY1Yjk4NTk3ZGYxNWZkOTJiZjY5NTlhZWZkNGM1YmVjOTkxNGRkNjNjYWEwYzMyOWM3YTA2YiJ9fX0=",
						"Back"));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		GUIManager.getInstance().register("alphabet", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("alphabet"),
						((int) (54d)), "Number");
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRjNmIxOWEwMTQ0ZjYzZmQyNmE2OTBmMjAxNjQwYjRiMmYxNTFlZWY3MmNjNDliZmUwNjEyMDQ0Y2VhNTZlNyJ9fX0=",
								"Redstone Block 0"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM5Yzg0NmY2NWQ1ZjI3MmE4MzlmZDljMmFlYjExYmRjOGUzZjgyMjlmYmUzNTgzNDg2ZTc4ZjRjMjNjOGI1YiJ9fX0=",
								"Redstone Block 1"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQxNWM0ZDBjN2I4MTQxNTAxOTQ5ZjczY2UwYzc4YjJiMWU5OTAyNTUzNzFhN2ZjNzE5OTk2MGM5YjAzN2Q1MSJ9fX0=",
								"Redstone Block 2"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY4ZDNjOGNiMDk4M2E0ZjU2Y2MyNmE3MWZmY2VkYmQ3YmVjYzUyMTI5MWM3ODM2MWZmMWU5OWRmNDE0NGNiYyJ9fX0=",
								"Redstone Block 3"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEyNzgxMjE2NmUxNDE4NmRlY2YxNzUxOTYwM2IzNTU2OTk0OTlhNTQ1Mzk3Zjg5MzE3OTRmYWQ2ZTllZmQ5MiJ9fX0=",
								"Redstone Block 4"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxMDA4NTkyZTNhZDI0ZDY1ZGZhNGZmNWEzYzgwMGQ3OGEzZGIxMzRjYmQ4ZTllYzNjYmFjMWVhODM5MWI5ZCJ9fX0=",
								"Redstone Block 5"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwOThmM2E5OTRjMWNkNjhlMGU4NjJhMDA2ODg4NjZmMmU2NzM0ODFjZDM0NDdjODVkOTgwMWJjMDMxN2I1ZiJ9fX0=",
								"Redstone Block 6"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODEwMmE4ZWIwZmZkZmU1OTgyMDczZGJjNDFiNzViYzIyZTU3N2UzYjFhZDAwYmIxNDg2OGNlZTM4NGJlYzdiIn19fQ==",
								"Redstone Block 7"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNhNmQ5ZWNhNjg2Mjg1MThlNmI5OTA1NGJjMGExZjdmY2M3OWUyYzk2OWYzZWI4ZjllZjAzNDE2NWUwM2JiNSJ9fX0=",
								"Redstone Block 8"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjUyMjc0NmYyZDExMTEzZGIyYzdkZTVkYzczNTI3NjE0MmRjNGJhZGU3YmVkNDczNDQyYTk1NGExYzQyMjc5ZiJ9fX0=",
								"Redstone Block 9"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				guiInventory.setItem(((int) (45d)), PluginMain.getSkull(
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzZDgyMjVmOGY1Yjk4NTk3ZGYxNWZkOTJiZjY5NTlhZWZkNGM1YmVjOTkxNGRkNjNjYWEwYzMyOWM3YTA2YiJ9fX0=",
						"Back"));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
		PERSISTENT_VARIABLES = org.bukkit.configuration.file.YamlConfiguration
				.loadConfiguration(new File(getDataFolder(), "data.yml"));
		GUIManager.getInstance().register("Food", guiPlayer -> {
			try {
				org.bukkit.inventory.Inventory guiInventory = Bukkit.createInventory(new GUIIdentifier("Food"),
						((int) (54d)), "Food");
				guiInventory.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwMzU2NDI2Yzg2ZjIwMWY3NmNhOTVkOWEwZTViNGY3NDA5ZGMwYjEyYmY1YWQ0MDIzNjJjMzhjOTM3Y2JjZCJ9fX0=",
								"Cake"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYzNTM4ZjgzY2M0Njc0YmYxMTRhOTRjNzRjNDEwOWE2YWQ2NWE4NmFmZWM4N2MyY2M1ZWJmNmI5OTM4NTE0YiJ9fX0=",
								"Candy"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlYWNhYzQxYTllYWYwNTEzNzZlZjJmOTU5NzAxZTFiYmUxYmY0YWE2NzE1YWRjMzRiNmRjMjlhMTNlYTkifX19",
								"Fries"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJiYWU2ZGY5OWRjODJiZWFmNDlkMDY0ZGY3NGExYmJjMTVlOGUzNzY1MzMyNzY5MTJjOGM4ZmU1OWNiNGY0In19fQ==",
								"Pepsi"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMwYmM5NTE4Mzc5NzZhODZlOTEwZGMzM2JkYWMzYzcyZjQ4YzAwMmEzNTNhODNmZWMyMzcwZWMyMDI1M2ZhMSJ9fX0=",
								"Burger"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUzNDdkYWRmNjgxOTlmYTdhMWI2NmYwNDgxYWQ4ZTlkYWVlMTUxMDg2NWFkZDZmMzNkMTVmYjM3OGQxM2U5MSJ9fX0=",
								"Sushi"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgzM2NlM2FkOWU2Mzg2OGM1YzhlMWM3MzEwYTk3NjdmY2FhZGVjNmFiYzkxZjhiZTcyY2ZhMmVjMWY5In19fQ==",
								"Hamburger"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBhYzQwYzBkY2M2YWYzMDc2MjI2MGQwNjhkNzczYjc5YjFiY2NhN2IyMTAyMTZmNzg1M2U5Mzc0ODRhMmJkNCJ9fX0=",
								"Bread"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdjNGMyYmUzOGEzYzA1MDdmYmI3Y2Y0MTFhZjIzNzczMzY0MWEzMDQ0ZTBmYzA4ZWQ5MmRjMzc2ODMyZjcxMSJ9fX0=",
								"Sandwich"),
						PluginMain.getSkull(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FiMDQ4NDdmMDliM2YyZTExZmM0MTJjMmUyNDFhY2NmOTk2NzU0MzI5MzNiNDcxNjgxMDA1YzQyZTVkMjI4NSJ9fX0=",
								"Donut (chocolate)"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
				guiInventory.setItem(((int) (45d)), PluginMain.getSkull(
						"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWUzZDgyMjVmOGY1Yjk4NTk3ZGYxNWZkOTJiZjY5NTlhZWZkNGM1YmVjOTkxNGRkNjNjYWEwYzMyOWM3YTA2YiJ9fX0=",
						"Back"));
				return guiInventory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}, true);
	}

	@Override
	public void onDisable() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PERSISTENT_VARIABLES.save(new File(getDataFolder(), "data.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("customhead")) {
			Object $637d6398afe4e3eb707be528e030164d = null;
			Object $ebda08fcdf95ce673ac70be02c22e003 = null;
			Object $327f3b0650bd489d8b382c7012100c04 = null;
			Object $b1fe6f4c1e0f25a425d5b6e383797dd2 = null;
			try {
				if ((commandSender instanceof org.bukkit.entity.Player)) {
					if (((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
						commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&c&l[HeadDrop]&r You Need to give a texture!"));
					} else if (((commandArgs.length > ((int) (1d)) ? commandArgs[((int) (1d))] : null) == null)) {
						$b1fe6f4c1e0f25a425d5b6e383797dd2 = (commandArgs.length > ((int) (0d))
								? commandArgs[((int) (0d))]
								: null);
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
												PluginMain.getSkull(String.valueOf($b1fe6f4c1e0f25a425d5b6e383797dd2))))
														.toArray(new org.bukkit.inventory.ItemStack[]{})));
					} else if (((commandArgs.length > ((int) (2d)) ? commandArgs[((int) (2d))] : null) == null)) {
						$b1fe6f4c1e0f25a425d5b6e383797dd2 = (commandArgs.length > ((int) (0d))
								? commandArgs[((int) (0d))]
								: null);
						$ebda08fcdf95ce673ac70be02c22e003 = (commandArgs.length > ((int) (1d))
								? commandArgs[((int) (1d))]
								: null);
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
												PluginMain.getSkull(String.valueOf($b1fe6f4c1e0f25a425d5b6e383797dd2),
														ChatColor.translateAlternateColorCodes('&', String
																.valueOf($ebda08fcdf95ce673ac70be02c22e003))))).toArray(
																		new org.bukkit.inventory.ItemStack[]{})));
					} else if (((commandArgs.length > ((int) (3d)) ? commandArgs[((int) (3d))] : null) == null)) {
						$b1fe6f4c1e0f25a425d5b6e383797dd2 = (commandArgs.length > ((int) (0d))
								? commandArgs[((int) (0d))]
								: null);
						$ebda08fcdf95ce673ac70be02c22e003 = (commandArgs.length > ((int) (1d))
								? commandArgs[((int) (1d))]
								: null);
						$637d6398afe4e3eb707be528e030164d = (commandArgs.length > ((int) (2d))
								? commandArgs[((int) (2d))]
								: null);
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
												PluginMain.getSkull(String.valueOf($b1fe6f4c1e0f25a425d5b6e383797dd2),
														String.valueOf($ebda08fcdf95ce673ac70be02c22e003),
														new ArrayList(Arrays.asList(ChatColor
																.translateAlternateColorCodes('&', String.valueOf(
																		$637d6398afe4e3eb707be528e030164d)))))))
																				.toArray(
																						new org.bukkit.inventory.ItemStack[]{})));
					} else {
						$b1fe6f4c1e0f25a425d5b6e383797dd2 = (commandArgs.length > ((int) (0d))
								? commandArgs[((int) (0d))]
								: null);
						$ebda08fcdf95ce673ac70be02c22e003 = (commandArgs.length > ((int) (1d))
								? commandArgs[((int) (1d))]
								: null);
						$637d6398afe4e3eb707be528e030164d = (commandArgs.length > ((int) (2d))
								? commandArgs[((int) (2d))]
								: null);
						$327f3b0650bd489d8b382c7012100c04 = (commandArgs.length > ((int) (3d))
								? commandArgs[((int) (3d))]
								: null);
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(
												PluginMain.getSkull(String.valueOf($b1fe6f4c1e0f25a425d5b6e383797dd2),
														String.valueOf($ebda08fcdf95ce673ac70be02c22e003),
														new ArrayList(Arrays.asList(
																ChatColor.translateAlternateColorCodes('&',
																		String.valueOf(
																				$637d6398afe4e3eb707be528e030164d)),
																ChatColor.translateAlternateColorCodes('&',
																		String.valueOf(
																				$327f3b0650bd489d8b382c7012100c04)))))))
																						.toArray(
																								new org.bukkit.inventory.ItemStack[]{})));
					}
				} else {
					PluginMain.getInstance().getLogger().severe("Only Player can execute this command!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("autoreload")) {
			try {
				if (!((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("on"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.autoreload")))) {
						PluginMain.PERSISTENT_VARIABLES.set("reload", "true");
						new org.bukkit.scheduler.BukkitRunnable() {
							public void run() {
								try {
									if (PluginMain.checkEquals(PluginMain.PERSISTENT_VARIABLES.get("reload"), "true")) {
										new org.bukkit.scheduler.BukkitRunnable() {
											public void run() {
												try {
													PluginMain.getInstance().reloadConfig();
												} catch (Exception ex) {
													ex.printStackTrace();
												}
											}
										}.runTaskTimerAsynchronously(PluginMain.getInstance(), 0, ((long) (20d)));
									} else {
										cancel();
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}.runTaskTimerAsynchronously(PluginMain.getInstance(), 0, ((long) (10d)));
					}
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("off"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.autoreload")))) {
						PluginMain.PERSISTENT_VARIABLES.set("reload", "false");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("autoreload")) {
			Object $328536b69ee1e6d24227792f961ff762 = null;
			try {
				if (!((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("on"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.autoreload")))) {
						$328536b69ee1e6d24227792f961ff762 = "true";
						Object TEMP_NTRPrGDLqXcJKSfH = $328536b69ee1e6d24227792f961ff762;
						new org.bukkit.scheduler.BukkitRunnable() {
							Object FINAL_baIZpuLvSOoVxjdl = TEMP_NTRPrGDLqXcJKSfH;

							public void run() {
								try {
									if (PluginMain.checkEquals(FINAL_baIZpuLvSOoVxjdl, "true")) {
									}
									PluginMain.getInstance().reloadConfig();
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}.runTaskTimerAsynchronously(PluginMain.getInstance(), 0, ((long) (20d)));
					}
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("off"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.autoreload")))) {
						$328536b69ee1e6d24227792f961ff762 = "false";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("m")) {
			try {
				if (!((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase(((java.lang.String) null)))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.help")))) {
						((org.bukkit.command.CommandSender) null).sendMessage(ChatColor.translateAlternateColorCodes(
								'&',
								"&3HeadDrop&r plugin by RRS.\n&9>&r §l/headdrop help&r -> you already discovered it!\n&9>&r &l/myhead&r -> Get your own head.\n&9>&r &l/head <player>&r -> get other player head.\n&9>&r &l/headdrop reload&r -> reload plugin config.\n&9>&r &l/headgui&r -> Head Database."));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("")) {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("headgui")) {
			try {
				GUIManager.getInstance().open("head_main", ((org.bukkit.entity.Player) (Object) commandSender));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("headdrop")) {
			try {
				if (!((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("help"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.help")))) {
						commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&3HeadDrop&r plugin by RRS.\n&9>&r §l/headdrop help&r -> you already discovered it!\n&9>&r &l/myhead&r -> Get your own head.\n&9>&r &l/head <player>&r -> get other player head.\n&9>&r &l/headdrop reload&r -> reload plugin config.\n&9>&r &l/headgui&r -> Head Database."));
					}
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("reload"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.reload")))) {
						PluginMain.getInstance().reloadConfig();
						commandSender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r Plugin reloaded!"));
					}
				} else {
					commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&l[HeadDrop]&r Running &3HeadDrop1.12&r by RRS."));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("myhead")) {
			Object $2a9618ad3f51cab01ec5660a77324d7c = null;
			Object $bff0d990b3e9044582549e6fd5f0c5a8 = null;
			try {
				if ((commandSender instanceof org.bukkit.entity.Player)) {
					$2a9618ad3f51cab01ec5660a77324d7c = new org.bukkit.inventory.ItemStack(
							((org.bukkit.Material) org.bukkit.Material.PLAYER_HEAD));
					$bff0d990b3e9044582549e6fd5f0c5a8 = ((org.bukkit.inventory.meta.ItemMeta) ((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c)
							.getItemMeta());
					((org.bukkit.inventory.meta.SkullMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8)
							.setOwner(String.valueOf(commandSender));
					((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c).setItemMeta(
							((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
					((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
							.getInventory())
									.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(
											Arrays.asList($2a9618ad3f51cab01ec5660a77324d7c))
													.toArray(new org.bukkit.inventory.ItemStack[]{})));
					commandSender.sendMessage(((java.lang.String) me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(
							((org.bukkit.OfflinePlayer) (Object) commandSender),
							ChatColor.translateAlternateColorCodes('&',
									(ChatColor.translateAlternateColorCodes('&', "&a\u00A7&[HeadDrop]&r ") + String
											.valueOf(PluginMain.getInstance().getConfig().get("message.myhead")))))
							.replaceAll("%player%",
									((java.lang.String) ((org.bukkit.entity.Player) (Object) commandSender)
											.getDisplayName()))));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("head")) {
			Object $b4eb675f9a0db9b86370c56adbd203dd = null;
			Object $2a9618ad3f51cab01ec5660a77324d7c = null;
			Object $bff0d990b3e9044582549e6fd5f0c5a8 = null;
			try {
				if ((commandSender instanceof org.bukkit.entity.Player)) {
					if (((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
						commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&c&l[HeadDrop]&r You Need to choose a player name to get head!"));
					} else {
						$b4eb675f9a0db9b86370c56adbd203dd = (commandArgs.length > ((int) (0d))
								? commandArgs[((int) (0d))]
								: null);
						$2a9618ad3f51cab01ec5660a77324d7c = new org.bukkit.inventory.ItemStack(
								((org.bukkit.Material) org.bukkit.Material.PLAYER_HEAD));
						$bff0d990b3e9044582549e6fd5f0c5a8 = ((org.bukkit.inventory.meta.ItemMeta) ((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c)
								.getItemMeta());
						((org.bukkit.inventory.meta.SkullMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8)
								.setOwner(String.valueOf($b4eb675f9a0db9b86370c56adbd203dd));
						((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c).setItemMeta(
								((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(
												Arrays.asList($2a9618ad3f51cab01ec5660a77324d7c))
														.toArray(new org.bukkit.inventory.ItemStack[]{})));
						commandSender.sendMessage(((java.lang.String) me.clip.placeholderapi.PlaceholderAPI
								.setPlaceholders(((org.bukkit.OfflinePlayer) (Object) commandSender),
										ChatColor.translateAlternateColorCodes('&', (ChatColor
												.translateAlternateColorCodes('&', "&a\u00A7&[HeadDrop]&r ")
												+ String.valueOf(
														PluginMain.getInstance().getConfig().get("message.head")))))
								.replaceAll("%player%",
										(commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null))));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

	public static void procedure(String procedure, List procedureArgs) throws Exception {
	}

	public static Object function(String function, List functionArgs) throws Exception {
		return null;
	}

	public static List createList(Object obj) {
		if (obj instanceof List) {
			return (List) obj;
		}
		List list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			int length = java.lang.reflect.Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				list.add(java.lang.reflect.Array.get(obj, i));
			}
		} else if (obj instanceof Collection<?>) {
			list.addAll((Collection<?>) obj);
		} else if (obj instanceof Iterator) {
			((Iterator<?>) obj).forEachRemaining(list::add);
		} else {
			list.add(obj);
		}
		return list;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void event1(org.bukkit.event.entity.ArrowBodyCountChangeEvent event) throws Exception {
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event2(org.bukkit.event.server.TabCompleteEvent event) throws Exception {
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop "))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd ")))) {
			event.setCompletions(new ArrayList(Arrays.asList("help", "reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop h"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd h")))) {
			event.setCompletions(new ArrayList(Arrays.asList("help")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop he"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd he")))) {
			event.setCompletions(new ArrayList(Arrays.asList("help")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop hel"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd hel")))) {
			event.setCompletions(new ArrayList(Arrays.asList("help")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop help"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd help")))) {
			event.setCompletions(new ArrayList(Arrays.asList("help")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop r"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd r")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop re"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd re")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop rel"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd rel")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop relo"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd relo")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop reloa"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd reloa")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
		if ((((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/headdrop reload"))
				|| ((boolean) ((java.lang.String) event.getBuffer()).equalsIgnoreCase("/hd reload")))) {
			event.setCompletions(new ArrayList(Arrays.asList("reload")));
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void event3(org.bukkit.event.player.PlayerResourcePackStatusEvent event) throws Exception {
	}

	@EventHandler
	public void onGUIClick(GUIClickEvent event) throws Exception {
		if (event.getID().equalsIgnoreCase("head_main")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("Mini_Block", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("alphabet", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("Mob_Head_1", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("Food", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("Mob_Head_1")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("53")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("Mob_Head_2", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("45")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("head_main", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19",
										"Cave Spider Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==",
										"Blaze Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==",
										"Chicken Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RmYTBhYzM3YmFiYTJhYTI5MGU0ZmFlZTQxOWE2MTNjZDYxMTdmYTU2OGU3MDlkOTAzNzQ3NTNjMDMyZGNiMCJ9fX0=",
										"Cow Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("4")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0=",
										"Enderman Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("5")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQwNjI5MWM0ODI0NWVjNTA3OGJhZTE1NzEwMGNjYWUzNmM5NTNjMTQwY2NiNDNlMWYzOTU2OTcyNjI4MWNmMCJ9fX0=",
										"Ghast Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("6")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=",
										"Magma Cube Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("7")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==",
										"MushroomCow Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("8")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19",
										"Sheep Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("9")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0=",
										"Slime Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("10")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19",
										"Villager Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("11")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19",
										"Iron Golem Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("12")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1N2NkNWMyOTg5ZmY5NzU3MGZlYzRkZGNkYzY5MjZhNjhhMzM5MzI1MGMxYmUxZjBiMTE0YTFkYjEifX19",
										"Ocelot Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("13")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=",
										"Pig Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("14")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZWQ0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19",
										"Wither Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("15")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0=",
										"Creeper Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("16")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE2MmRkMGI5ZjY1YjU4YTFlNzBmODFkOGUwM2U4ZmY2YzUzZTRlOTg1YmRiZTAxODY1NThkOGE2OWE4MTE4OSJ9fX0=",
										"Bee Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("17")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ==",
										"Evoker Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("18")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ==",
										"Fox Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("19")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY2MjMzNmQ4YWUwOTI0MDdlNThmN2NjODBkMjBmMjBlNzY1MDM1N2E0NTRjZTE2ZTMzMDc2MTlhMDExMDY0OCJ9fX0=",
										"Goat Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("20")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==",
										"Guardian Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("21")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5Yjk3MzRkMGU3YmYwNjBmZWRjNmJmN2ZlYzY0ZTFmN2FkNmZjODBiMGZkODQ0MWFkMGM3NTA4Yzg1MGQ3MyJ9fX0=",
										"Husk Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("22")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q2ZjRhMjFlMGQ2MmFmODI0Zjg3MDhhYzYzNDEwZjFhMDFiYmI0MWQ3ZjRhNzAyZDk0NjljNjExMzIyMiJ9fX0=",
										"Parrot Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("23")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0=",
										"Rabbit Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("24")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzM2E0YjczMjczYTY0YzhhYjI4MzBiMGZmZjc3N2E2MWE0ODhjOTJmNjBmODNiZmIzZTQyMWY0MjhhNDQifX19",
										"Shulker Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("25")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0=",
										"Strider Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("26")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ==",
										"Turtle Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("27")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ==",
										"Vex Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("28")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlYWVjMzQ0YWIwOTViNDhjZWFkNzUyN2Y3ZGVlNjFiMDYzZmY3OTFmNzZhOGZhNzY2NDJjODY3NmUyMTczIn19fQ==",
										"Vindicator Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("29")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3ZDZmZDBhOWNlNmJmNmZlMjJhM2VhODk1NmFkMWQwMTI0MTVkNmJiYTYxNmMzMWJhOTlhMmJkMWMwMzc2YSJ9fX0=",
										"Wolf Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("30")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==",
										"Zombie Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("31")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==",
										"Skeleton Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("32")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc5NmFhNmQxOGVkYzViNzI0YmQ4OWU5ODNiYzMyMTVhNDFiZjc3NWQxMTI2MzVlOWI1ODM1ZDFiOGFkMjBjYiJ9fX0=",
										"Bat Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("33")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyNTNmYzZiNjU2OTg4NDUzYTJkNzEzOGZjYTRkMWYyNzUyZjQ3NjkxZjBjNDM0ZTQzMjE4Mzc3MWNmZTEifX19",
										"Cat Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("34")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0=",
										"Cod Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("35")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNmMzYzA1MmNmNzg3ZDIzNmEyOTE1ZjgwNzJiNzdjNTQ3NDk3NzE1ZDFkMmY4Y2JjOWQyNDFkODhhIn19fQ==",
										"Donkey Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("36")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYxNWMyN2NiN2MzNWE2ZTdhYTc2MzU3M2RkYmZmOTgwODUxNmJkYWJjMjNmMWNiNTE2MzkxOGM0YTEzOTIifX19",
										"Horse Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("37")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19",
										"Mule Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("38")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWYxODEwN2QyNzVmMWNiM2E5Zjk3M2U1OTI4ZDU4NzlmYTQwMzI4ZmYzMjU4MDU0ZGI2ZGQzZTdjMGNhNjMzMCJ9fX0=",
										"Piglin Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("39")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1ZDYwYTRkNzBlYzEzNmE2NTg1MDdjZTgyZTM0NDNjZGFhMzk1OGQ3ZmNhM2Q5Mzc2NTE3YzdkYjRlNjk1ZCJ9fX0=",
										"Polar Bear Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("40")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNTI4NzZiYzNhOTZkZDJhMjI5OTI0NWVkYjNiZWVmNjQ3YzhhNTZhYzg4NTNhNjg3YzNlN2I1ZDhiYiJ9fX0=",
										"Pufferfish Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("41")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlYTlhMjIzNjIwY2RiNTRiMzU3NDEzZDQzYmQ4OWM0MDA4YmNhNmEyMjdmM2I3ZGI5N2Y3NzMzZWFkNWZjZiJ9fX0=",
										"Salmon Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("42")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==",
										"Skeleton Horse Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("43")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZkZmQxZjc1MzhjMDQwMjU4YmU3YTkxNDQ2ZGE4OWVkODQ1Y2M1ZWY3MjhlYjVlNjkwNTQzMzc4ZmNmNCJ9fX0=",
										"Snow Golem Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("44")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=",
										"Wandering Trader Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("Mob_Head_2")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("45")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("Mob_Head_1", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U5NTE1M2VjMjMyODRiMjgzZjAwZDE5ZDI5NzU2ZjI0NDMxM2EwNjFiNzBhYzAzYjk3ZDIzNmVlNTdiZDk4MiJ9fX0=",
										"Phantom Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxMzhmNDAxYzY3ZmMyZTFlMzg3ZDljOTBhOTY5MTc3MmVlNDg2ZThkZGJmMmVkMzc1ZmM4MzQ4NzQ2ZjkzNiJ9fX0=",
										"Axolotl Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0=",
										"Glow Squid Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0=",
										"Stray Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("4")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=",
										"Witch Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("5")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0=",
										"Hoglin Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("6")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0=",
										"Endermite Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("7")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgyNjI1ZmZhOTEwYThhOTdmODcxNDBhOTQ3NmI5YmRkZjA1ODZkMmFjNDVkMDI5NjMwYjA2MjVkYjA0YjUwZCJ9fX0=",
										"Enderbrine"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("8")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyMGM4NGJlZGQ0YzEwYjZkMGZlMDljNWJhZDQwOTVjMWJhNDQxMzYzNzM5OTQ3ZWU0NDk2ZWUyN2I1NzkwOCJ9fX0=",
										"Enderman"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("9")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjFiYTVjYmU0ZWJjNzAyZDI0NmIwODFjNjY3NTFiMTYxZjY2YjAxNjhlZTdlZGQ5MTY0NmRhZDRmYWU5YjczYSJ9fX0=",
										"Green Enderman"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("10")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNiYzBjNWVhMTg2YzI3YTY0ZjZjMjBkYTE5ODU1ZDJlMDkxMGQ0OTVmMDk4NWJmYjAxY2EzYzdkMTUxNGNhNyJ9fX0=",
										"Bloodshot Eye"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("11")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThlOTlmYzRhYmM4YmIwOGEzYjk1YWI4OGJlMzkzOWU5MjM5OTQyNmQ1YWEyM2ZiYjFhNmQwMWFiNzY1YjA5NSJ9fX0=",
										"Eye"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("12")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGVjMjIyNGM1ZDA0ZDcwNTliZTNiYWVkYjcxZjY2YWM0MGZlZWEyNjYwMDlmNjMyMWVlMTQyMzA3NmFiM2U3NCJ9fX0=",
										"Dragon"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("13")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzcwZTk2MzZiZGRiYzE0MGQyZDc0ODE5NGJkN2UxZGZiZGE0NTI2ZTk2MGMzNzMzZWI3ZGFlMTRiMWY2MDdmNiJ9fX0=",
										"Enderman King"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("14")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJhOTY0N2VjN2M4ZjM1OWQ4ZDA5NTJiZGJmNzJjYmI0YjU3NDNjZjg0NTVkY2I3NjY0ZTJiZjliZGY4YjcxOCJ9fX0=",
										"Dragon Eye"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("15")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE4NGNjODgxOGMyOTM0ODRmZGFhZmM4ZmEyZjBiZjM5ZTU1NzMzYTI0N2Q2ODAyM2RmMmM2YzZiOWI2NzFkMCJ9fX0=",
										"Spooky Enderman"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("16")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE4Y2Q0NTdmYmFmMzI3ZmEzOWYxMGI1YjM2MTY2ZmQwMTgyNjQwMzY4NjUxNjRjMDJkOWU1ZmY1M2Y0NSJ9fX0=",
										"Llama Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("17")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODAxOGExNzcxZDY5YzExYjhkYWQ0MmNkMzEwMzc1YmEyZDgyNzkzMmIyNWVmMzU3ZjdlNTcyYzFiZDBmOSJ9fX0=",
										"Panda Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("18")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==",
										"Spider Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("19")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0=",
										"Zombified Piglin Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("20")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0=",
										"Drowned Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("21")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkyMDg5NjE4NDM1YTBlZjYzZTk1ZWU5NWE5MmI4MzA3M2Y4YzMzZmE3N2RjNTM2NTE5OWJhZDMzYjYyNTYifX19",
										"Elder Guardian Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("22")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==",
										"Dolphin  Head"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("Mini_Block")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("45")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("head_main", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODViNGFiZDRmMDdiNjg5NDYwN2NiZDg3MDg2OGY2N2UwMjVjN2ZiNTUyYTFhNTdmNTZmNzdjMDQ0Y2NhNDFjZSJ9fX0=",
										"Gold Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM4NWFhZWRkNzg0ZmFlZjhlOGY2Zjc4MmZhNDhkMDdjMmZjMmJiY2Y2ZmVhMWZiYzliOTg2MmQwNWQyMjhjMSJ9fX0=",
										"Iron Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEwMDFiNDI1MTExYmZlMGFjZmY3MTBhOGI0MWVhOTVlM2I5MzZhODVlNWJiNjUxNzE2MGJhYjU4N2U4ODcwZiJ9fX0=",
										"Lapis Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzExMTA3ZjcwZjhjYTA0NzRmMDIzMjQzYmQzODJiYmQ2YjQxNDlhZWY0ZjQyYjI1ZGRiYmNmZWM4Nzk4YjRkYyJ9fX0=",
										"Coal Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("4")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzMzYjZjOTA3ZjFjMmExYWU1NGY5MGFhZmJjOWU1NjFmMmY0ZGQ0ZWM0YjczZTU2ZDU0OTU1YmMxZGZjYzJhMCJ9fX0=",
										"Diamond Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("5")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmE0MGJhZWI5NmZlYTFiZDZlZTA2NDY5NmNkYjc0ZmZkMDhhNmY3YzQwNjE3ZDQ2MmU0ZTJkYThmYWFmNzNlNSJ9fX0=",
										"Emerald Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("6")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTI1NDM2ZThjNGY3MDRjZjg0NmVlOTQ1YzdlNmNmYjA1ZjM4OGYxYjAzZDExN2VlNDU5N2RhMmEwZjc5NzcxNCJ9fX0=",
										"Wood Log"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("7")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNiYWVhZDkzMWJlNTdkODU4NDE2ZWVlNGU1ZWY5ZjU5Mjg2MDg5NTM1OGU3ZTZkNTJhMmVkZTc3YzEzMmU5MyJ9fX0=",
										"Jukebox"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("8")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ4MDAwZDU0MTk4NjBmZjNkZTUzYmE1NTliOWVjZTc5ZmIxYjY0OGUwOTlmMDIzYmE0ODA4NGNkY2VmOGIwYyJ9fX0=",
										"End Portal Frame"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("9")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBkZDZkZGRiZWIyZjU3MjNkM2ZhZDI5Njc5MjgzOGRlNWFlYmFhNjQzNzlkMmNhNTdhODMxNDU2NmMzZmI4OCJ9fX0=",
										"Sculk Chute"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("10")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk4MGZlNzYyZjQ4ODYxMzk3ZjBjOGRjMmY4ZDEzZjdjMTY2MTcwMDM4ZTk3MzAyMDY4OTE5MmE4OTMwOGYzZCJ9fX0=",
										"Netherite Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("11")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTViY2U2MmRkNTRiMWFkN2QwYmNiMjliN2EzMTE0NDQ3NTc2M2Y3OTM1Mzc4ZWM5NjIwN2JmMWM1NzRkZDc2MyJ9fX0=",
										"Ancient Debris"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("12")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJmZDJkZTUzZWIxYmZlOTJmNWNjMTc4MGEyNzI3MzBlYmU3NTk0NzNiYjI1YTUzNjQ3ZDM3MTJlZTVmNjA5NSJ9fX0=",
										"Diamond"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("13")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI0MzBhOTE2MTUxNDI3YzZjODcxZmUyZjZiNWE0ZTdkMTc3OTRhYzk3NzVhODdmYmMzMDNlMTNlNWJjYTI5ZiJ9fX0=",
										"Bricks"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("14")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmMwZTZkOWUyNDI3MzU0ODE5MThjNWZkMTQ0OThiZDc2MGJiOWY0ZmY2NDMwYWQ0Njk2YjM4ZThhODgzZGE5NyJ9fX0=",
										"Emerald Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("15")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE5ZTM2YTg3YmFmMGFjNzYzMTQzNTJmNTlhN2Y2M2JkYjNmNGM4NmJkOWJiYTY5Mjc3NzJjMDFkNGQxIn19fQ==",
										"Grass"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("16")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFlODI2ZTdkYjg0NDdmYmQ2Mjk4OGZlZTBlODNiYmRkNjk0Mzc4YWVmMTJkMjU3MmU5NzVmMDU5YTU0OTkwIn19fQ==",
										"Oak Log"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("17")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTc5ZDBlOTZjMzUxZTJlMmQ5MDQyYTQ2ODJkZWM0MzBjNGU1MjI1NmVkNzdjZjgwYmQwMjY3ZjdjYWJlMzMwMCJ9fX0=",
										"Crying Obsidian"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("18")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0ODc2YjZhNWQ2ZGQ3ODVlMDkxZmQxMzRhMjFjOTFkMGE5Y2FjNWE2MjJlNDQ4YjVmZmNiNjVlZjQ1Mjc4In19fQ==",
										"Amethyst Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("19")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI2NDQwNzFiNmM3YmJhZTdiNWU0NWQ5ZjgyZjk2ZmZiNWVlOGUxNzdhMjNiODI1YTQ0NjU2MDdmMWM5YyJ9fX0=",
										"Ice Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("20")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZhYWI1OGZhMDFmY2U5YWY0NjllZDc0N2FlZDgxMWQ3YmExOGM0NzZmNWE3ZjkwODhlMTI5YzMxYjQ1ZjMifX19",
										"Packed Ice Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("21")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjdiNzQ3YjM3OGE0MWEwYTZlZGM4NmMwMDBmMDQwYzY5OTRhODMzMjUxMTk2YzlkNTJjMmEyMzBmOTUxNjBjYyJ9fX0=",
										"Rainbow Gem"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("22")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU3NzVmZjFkYWE0NmI3YzJiZWFiNTYzMTI5ZDM0NjQ0YzVkNWY5MTg2YmIxM2M1MTZhNWUwNWYwY2Q0ZTQ1MSJ9fX0=",
										"Burning Netherrack"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("23")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODExMmY4N2NlZTU3ODg5NGUyZDA3MjUzYWJiMTQ2NjI0N2NlZTQ4ZjE3MjdiYjlkMWVhYzUzZjhlMDU3MTAxMiJ9fX0=",
										"Multi Ore"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("24")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmUyMTIzOTFkODgwMDcwMjFiZTIwODNmZDEwMWIyZDc0YTMwNjUxMTU2NjkzOWIzY2M3ZjY2YTA0Y2Q4OGRhMyJ9fX0=",
										"Chiseled Sandstone"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("25")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4ZTRlZDVjODRjZmExZTJkMDAyZDYyMDM5M2E0MDMzMzU0NjFjZWNkMWJmODgyODAzYjJkOTI5NGM0NTRkMSJ9fX0=",
										"Magma Bricks"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("26")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjRiMWI5Y2UyZTlhNmNlOGE5ODVkMzk3NzZlMjkwODA3N2I4MmU2YTMzM2QyYTgxYTQ0MTQzOGVhYjM5ZjhlMSJ9fX0=",
										"Blaze Block"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("alphabet")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("45")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("head_main", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRjNmIxOWEwMTQ0ZjYzZmQyNmE2OTBmMjAxNjQwYjRiMmYxNTFlZWY3MmNjNDliZmUwNjEyMDQ0Y2VhNTZlNyJ9fX0=",
										"Redstone Block 0"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM5Yzg0NmY2NWQ1ZjI3MmE4MzlmZDljMmFlYjExYmRjOGUzZjgyMjlmYmUzNTgzNDg2ZTc4ZjRjMjNjOGI1YiJ9fX0=",
										"Redstone Block 1"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQxNWM0ZDBjN2I4MTQxNTAxOTQ5ZjczY2UwYzc4YjJiMWU5OTAyNTUzNzFhN2ZjNzE5OTk2MGM5YjAzN2Q1MSJ9fX0=",
										"Redstone Block 2"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY4ZDNjOGNiMDk4M2E0ZjU2Y2MyNmE3MWZmY2VkYmQ3YmVjYzUyMTI5MWM3ODM2MWZmMWU5OWRmNDE0NGNiYyJ9fX0=",
										"Redstone Block 3"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("4")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEyNzgxMjE2NmUxNDE4NmRlY2YxNzUxOTYwM2IzNTU2OTk0OTlhNTQ1Mzk3Zjg5MzE3OTRmYWQ2ZTllZmQ5MiJ9fX0=",
										"Redstone Block 4"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("5")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUxMDA4NTkyZTNhZDI0ZDY1ZGZhNGZmNWEzYzgwMGQ3OGEzZGIxMzRjYmQ4ZTllYzNjYmFjMWVhODM5MWI5ZCJ9fX0=",
										"Redstone Block 5"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("6")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTMwOThmM2E5OTRjMWNkNjhlMGU4NjJhMDA2ODg4NjZmMmU2NzM0ODFjZDM0NDdjODVkOTgwMWJjMDMxN2I1ZiJ9fX0=",
										"Redstone Block 6"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("7")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODEwMmE4ZWIwZmZkZmU1OTgyMDczZGJjNDFiNzViYzIyZTU3N2UzYjFhZDAwYmIxNDg2OGNlZTM4NGJlYzdiIn19fQ==",
										"Redstone Block 7"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("8")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODNhNmQ5ZWNhNjg2Mjg1MThlNmI5OTA1NGJjMGExZjdmY2M3OWUyYzk2OWYzZWI4ZjllZjAzNDE2NWUwM2JiNSJ9fX0=",
										"Redstone Block 8"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("9")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjUyMjc0NmYyZDExMTEzZGIyYzdkZTVkYzczNTI3NjE0MmRjNGJhZGU3YmVkNDczNDQyYTk1NGExYzQyMjc5ZiJ9fX0=",
										"Redstone Block 9"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			return;
		}
		if (event.getID().equalsIgnoreCase("Food")) {
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("45")))) {
				((org.bukkit.entity.HumanEntity) (Object) ((org.bukkit.entity.Player) event.getWhoClicked()))
						.closeInventory();
				GUIManager.getInstance().open("head_main", ((org.bukkit.entity.Player) event.getWhoClicked()));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("0")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwMzU2NDI2Yzg2ZjIwMWY3NmNhOTVkOWEwZTViNGY3NDA5ZGMwYjEyYmY1YWQ0MDIzNjJjMzhjOTM3Y2JjZCJ9fX0=",
										"Cake"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("1")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYzNTM4ZjgzY2M0Njc0YmYxMTRhOTRjNzRjNDEwOWE2YWQ2NWE4NmFmZWM4N2MyY2M1ZWJmNmI5OTM4NTE0YiJ9fX0=",
										"Candy"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("2")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBlYWNhYzQxYTllYWYwNTEzNzZlZjJmOTU5NzAxZTFiYmUxYmY0YWE2NzE1YWRjMzRiNmRjMjlhMTNlYTkifX19",
										"Fries"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("3")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJiYWU2ZGY5OWRjODJiZWFmNDlkMDY0ZGY3NGExYmJjMTVlOGUzNzY1MzMyNzY5MTJjOGM4ZmU1OWNiNGY0In19fQ==",
										"Pepsi"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("4")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWMwYmM5NTE4Mzc5NzZhODZlOTEwZGMzM2JkYWMzYzcyZjQ4YzAwMmEzNTNhODNmZWMyMzcwZWMyMDI1M2ZhMSJ9fX0=",
										"Burger"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("5")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUzNDdkYWRmNjgxOTlmYTdhMWI2NmYwNDgxYWQ4ZTlkYWVlMTUxMDg2NWFkZDZmMzNkMTVmYjM3OGQxM2U5MSJ9fX0=",
										"Sushi"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("6")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgzM2NlM2FkOWU2Mzg2OGM1YzhlMWM3MzEwYTk3NjdmY2FhZGVjNmFiYzkxZjhiZTcyY2ZhMmVjMWY5In19fQ==",
										"Hamburger"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("7")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBhYzQwYzBkY2M2YWYzMDc2MjI2MGQwNjhkNzczYjc5YjFiY2NhN2IyMTAyMTZmNzg1M2U5Mzc0ODRhMmJkNCJ9fX0=",
										"Bread"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("8")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdjNGMyYmUzOGEzYzA1MDdmYmI3Y2Y0MTFhZjIzNzczMzY0MWEzMDQ0ZTBmYzA4ZWQ5MmRjMzc2ODMyZjcxMSJ9fX0=",
										"Sandwich"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			if (PluginMain.checkEquals(((java.lang.Object) (Object) event.getSlot()),
					((java.lang.Object) (Object) Double.parseDouble("9")))) {
				((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) ((org.bukkit.entity.Player) event
						.getWhoClicked())).getInventory()).addItem(
								((org.bukkit.inventory.ItemStack[]) new ArrayList(Arrays.asList(PluginMain.getSkull(
										"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FiMDQ4NDdmMDliM2YyZTExZmM0MTJjMmUyNDFhY2NmOTk2NzU0MzI5MzNiNDcxNjgxMDA1YzQyZTVkMjI4NSJ9fX0=",
										"Donut (chocolate)"))).toArray(new org.bukkit.inventory.ItemStack[]{})));
			}
			return;
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event4(org.bukkit.event.entity.PlayerDeathEvent event) throws Exception {
		Object $2a9618ad3f51cab01ec5660a77324d7c = null;
		Object $bff0d990b3e9044582549e6fd5f0c5a8 = null;
		if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Player"))) {
			$2a9618ad3f51cab01ec5660a77324d7c = new org.bukkit.inventory.ItemStack(
					((org.bukkit.Material) org.bukkit.Material.PLAYER_HEAD));
			$bff0d990b3e9044582549e6fd5f0c5a8 = ((org.bukkit.inventory.meta.ItemMeta) ((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c)
					.getItemMeta());
			((org.bukkit.inventory.meta.SkullMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8).setOwningPlayer(
					((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Entity) event.getEntity())));
			((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c)
					.setItemMeta(((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
			((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld()).dropItemNaturally(
					((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity()).getLocation()),
					((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c));
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event5(org.bukkit.event.entity.EntityDeathEvent event) throws Exception {
		if (!(((org.bukkit.entity.Player) ((org.bukkit.entity.LivingEntity) (Object) ((org.bukkit.entity.Entity) event
				.getEntity())).getKiller()) == null)) {
			if (PluginMain.checkEquals(
					((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) (Object) ((org.bukkit.entity.Player) ((org.bukkit.entity.LivingEntity) (Object) ((org.bukkit.entity.Entity) event
							.getEntity())).getKiller())).getType()),
					((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PLAYER))) {
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.CAVE_SPIDER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Cave_spider"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.CaveSpider"))))) {
							PluginMain.GLOBAL_1cea0ec6e4ba770d8e7a9f27c916921c = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19",
									"Cave Spider Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1cea0ec6e4ba770d8e7a9f27c916921c));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.BLAZE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Blaze"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Blaze"))))) {
							PluginMain.GLOBAL_db8861f205ec91285b13de3615af12e7 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==",
									"Blaze Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_db8861f205ec91285b13de3615af12e7));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.CHICKEN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Chicken"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Chicken"))))) {
							PluginMain.GLOBAL_90dc0bc68e52163be7e8a9ce141875d2 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==",
									"Chicken Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_90dc0bc68e52163be7e8a9ce141875d2));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.COW))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Cow"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Cow"))))) {
							PluginMain.GLOBAL_c758fa0ad91c2b48870b858083635f5c = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RmYTBhYzM3YmFiYTJhYTI5MGU0ZmFlZTQxOWE2MTNjZDYxMTdmYTU2OGU3MDlkOTAzNzQ3NTNjMDMyZGNiMCJ9fX0=",
									"Cow Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_c758fa0ad91c2b48870b858083635f5c));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.ENDERMAN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Enderman"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Enderman"))))) {
							PluginMain.GLOBAL_a671796ed35cc1ce9011ca804342c791 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjMGIzNmQ1M2ZmZjY5YTQ5YzdkNmYzOTMyZjJiMGZlOTQ4ZTAzMjIyNmQ1ZTgwNDVlYzU4NDA4YTM2ZTk1MSJ9fX0=",
									"Enderman Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_a671796ed35cc1ce9011ca804342c791));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.GHAST))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Ghast"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Ghast"))))) {
							PluginMain.GLOBAL_333a1cfb6e4adc9ba5c486442ab110f1 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQwNjI5MWM0ODI0NWVjNTA3OGJhZTE1NzEwMGNjYWUzNmM5NTNjMTQwY2NiNDNlMWYzOTU2OTcyNjI4MWNmMCJ9fX0=",
									"Ghast Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_333a1cfb6e4adc9ba5c486442ab110f1));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.MAGMA_CUBE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Magma"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.MagmaCube"))))) {
							PluginMain.GLOBAL_b8a78eb5b6583f0108600a11d2be4e00 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=",
									"Magma Cube Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_b8a78eb5b6583f0108600a11d2be4e00));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.MUSHROOM_COW))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Mushroom_Cow"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.MushroomCow"))))) {
							PluginMain.GLOBAL_feb5c747e777739d766a1922505f0179 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==",
									"MushroomCow Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_feb5c747e777739d766a1922505f0179));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SHEEP))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Sheep"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Sheep"))))) {
							PluginMain.GLOBAL_1282fef553a95edb0ec2b449afa3b6b4 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19",
									"Sheep Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1282fef553a95edb0ec2b449afa3b6b4));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SLIME))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Slime"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Slime"))))) {
							PluginMain.GLOBAL_8051931738a0a594438c3e08001c0861 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0=",
									"Slime Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_8051931738a0a594438c3e08001c0861));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.VILLAGER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Villager"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Villager"))))) {
							PluginMain.GLOBAL_bae1b74269048261e6a4c04557b57df1 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDFiODMwZWI0MDgyYWNlYzgzNmJjODM1ZTQwYTExMjgyYmI1MTE5MzMxNWY5MTE4NDMzN2U4ZDM1NTU1ODMifX19",
									"Villager Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_bae1b74269048261e6a4c04557b57df1));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.IRON_GOLEM))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Golem"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.IronGolem"))))) {
							PluginMain.GLOBAL_bf53962c71362bb8a2dbe406ff7c5683 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19",
									"Iron Golem Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_bf53962c71362bb8a2dbe406ff7c5683));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.OCELOT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Ocelot"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Ocelot"))))) {
							PluginMain.GLOBAL_59b8dbaab602cba1c4aac6ae34b4559d = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1N2NkNWMyOTg5ZmY5NzU3MGZlYzRkZGNkYzY5MjZhNjhhMzM5MzI1MGMxYmUxZjBiMTE0YTFkYjEifX19",
									"Ocelot Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_59b8dbaab602cba1c4aac6ae34b4559d));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PIG))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Pig"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Pig"))))) {
							PluginMain.GLOBAL_1f258800ed81ffa69deca414b51d95a8 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=",
									"Pig Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1f258800ed81ffa69deca414b51d95a8));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.WITHER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Wither"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Wither"))))) {
							PluginMain.GLOBAL_beb85b2a57e1c1abdb79cae3ec0d282a = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZWQ0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19",
									"Wither Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_beb85b2a57e1c1abdb79cae3ec0d282a));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.CREEPER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Creeper"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Creeper"))))) {
							PluginMain.GLOBAL_a92dbc98ead585a6cbb41284e0d100ff = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjQyNTQ4MzhjMzNlYTIyN2ZmY2EyMjNkZGRhYWJmZTBiMDIxNWY3MGRhNjQ5ZTk0NDQ3N2Y0NDM3MGNhNjk1MiJ9fX0=",
									"Creeper Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_a92dbc98ead585a6cbb41284e0d100ff));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.BEE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Bee"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Bee"))))) {
							PluginMain.GLOBAL_bc092b1e64d3683be63460b3cc0ea9fc = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE2MmRkMGI5ZjY1YjU4YTFlNzBmODFkOGUwM2U4ZmY2YzUzZTRlOTg1YmRiZTAxODY1NThkOGE2OWE4MTE4OSJ9fX0=",
									"Bee Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_bc092b1e64d3683be63460b3cc0ea9fc));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.EVOKER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Evoker"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Evoker"))))) {
							PluginMain.GLOBAL_426637b5dc51d57ef12546f5dc460041 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk1NDEzNWRjODIyMTM5NzhkYjQ3ODc3OGFlMTIxMzU5MWI5M2QyMjhkMzZkZDU0ZjFlYTFkYTQ4ZTdjYmE2In19fQ==",
									"Evoker Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_426637b5dc51d57ef12546f5dc460041));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.FOX))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Fox"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Fox"))))) {
							PluginMain.GLOBAL_31dbdf36c42fa7e764b123393e0e0c33 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ==",
									"Fox Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_31dbdf36c42fa7e764b123393e0e0c33));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.GOAT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Goat"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Goat"))))) {
							PluginMain.GLOBAL_a11b8df5348dd0e46ad7191a8c9c0b95 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY2MjMzNmQ4YWUwOTI0MDdlNThmN2NjODBkMjBmMjBlNzY1MDM1N2E0NTRjZTE2ZTMzMDc2MTlhMDExMDY0OCJ9fX0=",
									"Goat Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_a11b8df5348dd0e46ad7191a8c9c0b95));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.GUARDIAN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Guardian"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Guardian"))))) {
							PluginMain.GLOBAL_a5fb79394a18688823a6c348b7305003 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==",
									"Guardian Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_a5fb79394a18688823a6c348b7305003));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.HUSK))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Husk"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Husk"))))) {
							PluginMain.GLOBAL_9e78a9bdac37cc4fb06d64e84560f408 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5Yjk3MzRkMGU3YmYwNjBmZWRjNmJmN2ZlYzY0ZTFmN2FkNmZjODBiMGZkODQ0MWFkMGM3NTA4Yzg1MGQ3MyJ9fX0=",
									"Husk Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_9e78a9bdac37cc4fb06d64e84560f408));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PARROT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Parrot"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Parrot"))))) {
							PluginMain.GLOBAL_005a6c133e190ccd6e95c6b4be8229d8 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Q2ZjRhMjFlMGQ2MmFmODI0Zjg3MDhhYzYzNDEwZjFhMDFiYmI0MWQ3ZjRhNzAyZDk0NjljNjExMzIyMiJ9fX0=",
									"Parrot Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_005a6c133e190ccd6e95c6b4be8229d8));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.RABBIT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Rabbit"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Rabbit"))))) {
							PluginMain.GLOBAL_e430731a9da23e12c3442ec270b1f338 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0=",
									"Rabbit Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_e430731a9da23e12c3442ec270b1f338));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SHULKER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Shulker"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Shulker"))))) {
							PluginMain.GLOBAL_dbd71fc8558b255d7d977761fad066da = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzM2E0YjczMjczYTY0YzhhYjI4MzBiMGZmZjc3N2E2MWE0ODhjOTJmNjBmODNiZmIzZTQyMWY0MjhhNDQifX19",
									"Shulker Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_dbd71fc8558b255d7d977761fad066da));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.STRIDER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Strider"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Strider"))))) {
							PluginMain.GLOBAL_a12c9a2e3ca6318d46511d247af84630 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0=",
									"Strider Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_a12c9a2e3ca6318d46511d247af84630));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.TURTLE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Turtle"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Turtle"))))) {
							PluginMain.GLOBAL_1ac04a82f96cfd8be73e60c5cd12e670 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGE0MDUwZTdhYWNjNDUzOTIwMjY1OGZkYzMzOWRkMTgyZDdlMzIyZjlmYmNjNGQ1Zjk5YjU3MThhIn19fQ==",
									"Turtle Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1ac04a82f96cfd8be73e60c5cd12e670));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.VEX))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Vex"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Vex"))))) {
							PluginMain.GLOBAL_6376ce02ee09345678e0245a1d73b8d1 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ==",
									"Vex Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_6376ce02ee09345678e0245a1d73b8d1));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.VINDICATOR))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Vindicator"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.Vindicator"))))) {
							PluginMain.GLOBAL_f029a071862ce8fce84c06db6123b3ba = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlYWVjMzQ0YWIwOTViNDhjZWFkNzUyN2Y3ZGVlNjFiMDYzZmY3OTFmNzZhOGZhNzY2NDJjODY3NmUyMTczIn19fQ==",
									"Vindicator Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_f029a071862ce8fce84c06db6123b3ba));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.WOLF))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Wolf"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Wolf"))))) {
							PluginMain.GLOBAL_dd5ef31bbfa7ed73b0898406a16838ed = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3ZDZmZDBhOWNlNmJmNmZlMjJhM2VhODk1NmFkMWQwMTI0MTVkNmJiYTYxNmMzMWJhOTlhMmJkMWMwMzc2YSJ9fX0=",
									"Wolf Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_dd5ef31bbfa7ed73b0898406a16838ed));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.ZOMBIE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Zombie"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Zombie"))))) {
							PluginMain.GLOBAL_7cd09eb7c69665b3054ed06b4a52a053 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZmYzg1NGJiODRjZjRiNzY5NzI5Nzk3M2UwMmI3OWJjMTA2OTg0NjBiNTFhNjM5YzYwZTVlNDE3NzM0ZTExIn19fQ==",
									"Zombie Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_7cd09eb7c69665b3054ed06b4a52a053));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SKELETON))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Skeleton"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Skeleton"))))) {
							PluginMain.GLOBAL_f577bb0d2c06cc6914352ea792a263e7 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxMjY4ZTljNDkyZGExZjBkODgyNzFjYjQ5MmE0YjMwMjM5NWY1MTVhN2JiZjc3ZjRhMjBiOTVmYzAyZWIyIn19fQ==",
									"Skeleton Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_f577bb0d2c06cc6914352ea792a263e7));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.BAT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Bat"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Bat"))))) {
							PluginMain.GLOBAL_f399510854acd717557f43a75df433c0 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc5NmFhNmQxOGVkYzViNzI0YmQ4OWU5ODNiYzMyMTVhNDFiZjc3NWQxMTI2MzVlOWI1ODM1ZDFiOGFkMjBjYiJ9fX0=",
									"Bat Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_f399510854acd717557f43a75df433c0));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.CAT))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Cat"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Cat"))))) {
							PluginMain.GLOBAL_cb50c1d5bc7677bf6ed80d372970d0b9 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmIyNTNmYzZiNjU2OTg4NDUzYTJkNzEzOGZjYTRkMWYyNzUyZjQ3NjkxZjBjNDM0ZTQzMjE4Mzc3MWNmZTEifX19",
									"Cat Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_cb50c1d5bc7677bf6ed80d372970d0b9));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.COD))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Cod"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Cod"))))) {
							PluginMain.GLOBAL_1fef70b4555728a9e0365289babd67be = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0=",
									"Cod Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1fef70b4555728a9e0365289babd67be));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.DONKEY))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Donkey"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Donkey"))))) {
							PluginMain.GLOBAL_12ee3e8452c35193157ad3f1d50a591f = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNmMzYzA1MmNmNzg3ZDIzNmEyOTE1ZjgwNzJiNzdjNTQ3NDk3NzE1ZDFkMmY4Y2JjOWQyNDFkODhhIn19fQ==",
									"Donkey Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_12ee3e8452c35193157ad3f1d50a591f));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.HORSE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Horse"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Horse"))))) {
							PluginMain.GLOBAL_f1f79b24bacb6595e2411c088bdf3784 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWYxNWMyN2NiN2MzNWE2ZTdhYTc2MzU3M2RkYmZmOTgwODUxNmJkYWJjMjNmMWNiNTE2MzkxOGM0YTEzOTIifX19",
									"Horse Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_f1f79b24bacb6595e2411c088bdf3784));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.MULE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Mule"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Mule"))))) {
							PluginMain.GLOBAL_89027f20e16bf6b6ad5e2ef219d76ba8 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19",
									"Mule Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_89027f20e16bf6b6ad5e2ef219d76ba8));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PIGLIN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Piglin"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Piglin"))))) {
							PluginMain.GLOBAL_bfeaa33a472bf0f8655d30be5887b28c = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWYxODEwN2QyNzVmMWNiM2E5Zjk3M2U1OTI4ZDU4NzlmYTQwMzI4ZmYzMjU4MDU0ZGI2ZGQzZTdjMGNhNjMzMCJ9fX0=",
									"Piglin Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_bfeaa33a472bf0f8655d30be5887b28c));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.POLAR_BEAR))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Polar_Bear"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.PolarBear"))))) {
							PluginMain.GLOBAL_24859148204a9ce2e50e6a086e50f828 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1ZDYwYTRkNzBlYzEzNmE2NTg1MDdjZTgyZTM0NDNjZGFhMzk1OGQ3ZmNhM2Q5Mzc2NTE3YzdkYjRlNjk1ZCJ9fX0=",
									"Polar Bear Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_24859148204a9ce2e50e6a086e50f828));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PUFFERFISH))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Pufferfish"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.PufferFish"))))) {
							PluginMain.GLOBAL_333d8e29a569a4f9f3f6e4ecab20dbc9 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNTI4NzZiYzNhOTZkZDJhMjI5OTI0NWVkYjNiZWVmNjQ3YzhhNTZhYzg4NTNhNjg3YzNlN2I1ZDhiYiJ9fX0=",
									"Pufferfish Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_333d8e29a569a4f9f3f6e4ecab20dbc9));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SALMON))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Salmon"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Salmon"))))) {
							PluginMain.GLOBAL_05bb525fba6635ab5c95cfcf98d4d8e7 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlYTlhMjIzNjIwY2RiNTRiMzU3NDEzZDQzYmQ4OWM0MDA4YmNhNmEyMjdmM2I3ZGI5N2Y3NzMzZWFkNWZjZiJ9fX0=",
									"Salmon Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_05bb525fba6635ab5c95cfcf98d4d8e7));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SKELETON_HORSE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Skeleton_Horse"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.SkeletonHorse"))))) {
							PluginMain.GLOBAL_621193cb1db5cfb0581047b02e36f761 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==",
									"Skeleton Horse Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_621193cb1db5cfb0581047b02e36f761));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SNOWMAN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Snow_Golem"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.SnowGolem"))))) {
							PluginMain.GLOBAL_ed84d4eda498f7fc09202e28810526fa = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZkZmQxZjc1MzhjMDQwMjU4YmU3YTkxNDQ2ZGE4OWVkODQ1Y2M1ZWY3MjhlYjVlNjkwNTQzMzc4ZmNmNCJ9fX0=",
									"Snow Golem Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_ed84d4eda498f7fc09202e28810526fa));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.WANDERING_TRADER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Wandering_Trader"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(
										PluginMain.getInstance().getConfig().get("Chance.WanderingTrader"))))) {
							PluginMain.GLOBAL_81b35edbd171ea4c4aadb6412b478db3 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=",
									"Wandering Trader Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_81b35edbd171ea4c4aadb6412b478db3));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.DOLPHIN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Dolphin "))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Dolphin"))))) {
							PluginMain.GLOBAL_3b126ddcc7a234eb582619d9c5bb3449 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==",
									"Dolphin  Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_3b126ddcc7a234eb582619d9c5bb3449));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.LLAMA))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Llama"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Llama"))))) {
							PluginMain.GLOBAL_dd2c161b39a4a0e143fe36608fe5e779 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE4Y2Q0NTdmYmFmMzI3ZmEzOWYxMGI1YjM2MTY2ZmQwMTgyNjQwMzY4NjUxNjRjMDJkOWU1ZmY1M2Y0NSJ9fX0=",
									"Llama Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_dd2c161b39a4a0e143fe36608fe5e779));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PANDA))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Panda"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Panda"))))) {
							PluginMain.GLOBAL_4f4803809935761597928bff373cf30a = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODAxOGExNzcxZDY5YzExYjhkYWQ0MmNkMzEwMzc1YmEyZDgyNzkzMmIyNWVmMzU3ZjdlNTcyYzFiZDBmOSJ9fX0=",
									"Panda Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_4f4803809935761597928bff373cf30a));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SPIDER))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Spider"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Spider"))))) {
							PluginMain.GLOBAL_b3cdec33d4ae8d60180d7af81a9798a4 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==",
									"Spider Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_b3cdec33d4ae8d60180d7af81a9798a4));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.ZOMBIFIED_PIGLIN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Zombified_Piglin"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String.valueOf(
										PluginMain.getInstance().getConfig().get("Chance.ZombifiedPiglin"))))) {
							PluginMain.GLOBAL_2a316538ce9a59ea66dbc33d0a877ef3 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhYmFlY2M1ZmFlNWE4YTQ5Yzg4NjNmZjQ4MzFhYWEyODQxOThmMWEyMzk4ODkwYzc2NWUwYThkZTE4ZGE4YyJ9fX0=",
									"Zombified Piglin Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_2a316538ce9a59ea66dbc33d0a877ef3));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.DROWNED))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Drowned"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Drowned"))))) {
							PluginMain.GLOBAL_2c1541ce0aeb50770e9837749508c92d = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg0ZGY3OWM0OTEwNGIxOThjZGFkNmQ5OWZkMGQwYmNmMTUzMWM5MmQ0YWI2MjY5ZTQwYjdkM2NiYmI4ZTk4YyJ9fX0=",
									"Drowned Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_2c1541ce0aeb50770e9837749508c92d));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.ELDER_GUARDIAN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Elder_Guardian"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.ElderGuardian"))))) {
							PluginMain.GLOBAL_b6122e09ecbefe9133fcfe59dfd4e35b = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTkyMDg5NjE4NDM1YTBlZjYzZTk1ZWU5NWE5MmI4MzA3M2Y4YzMzZmE3N2RjNTM2NTE5OWJhZDMzYjYyNTYifX19",
									"Elder Guardian Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_b6122e09ecbefe9133fcfe59dfd4e35b));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.ENDERMITE))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Endermite"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.Endermite"))))) {
							PluginMain.GLOBAL_88f7fb58299b47098b9402e42a7d52e6 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0=",
									"Endermite Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_88f7fb58299b47098b9402e42a7d52e6));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.HOGLIN))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Hoglin"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Hoglin"))))) {
							PluginMain.GLOBAL_eebd72ee1d37924f76c1dd2359acd923 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0=",
									"Hoglin Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_eebd72ee1d37924f76c1dd2359acd923));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.PHANTOM))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Phantom"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Phantom"))))) {
							PluginMain.GLOBAL_214d86a5752182f20c25b9fce49fbe1c = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2U5NTE1M2VjMjMyODRiMjgzZjAwZDE5ZDI5NzU2ZjI0NDMxM2EwNjFiNzBhYzAzYjk3ZDIzNmVlNTdiZDk4MiJ9fX0=",
									"Phantom Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_214d86a5752182f20c25b9fce49fbe1c));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.AXOLOTL))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Axolotl"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Axolotl"))))) {
							PluginMain.GLOBAL_97f0398e642d77e91eac7803d7aa550d = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMxMzhmNDAxYzY3ZmMyZTFlMzg3ZDljOTBhOTY5MTc3MmVlNDg2ZThkZGJmMmVkMzc1ZmM4MzQ4NzQ2ZjkzNiJ9fX0=",
									"Axolotl Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_97f0398e642d77e91eac7803d7aa550d));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.GLOW_SQUID))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Glow_Squid"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(String
										.valueOf(PluginMain.getInstance().getConfig().get("Chance.GlowSquid"))))) {
							PluginMain.GLOBAL_e62dbf694c734f11b41b9fefe86811c0 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0=",
									"Glow Squid Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_e62dbf694c734f11b41b9fefe86811c0));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.STRAY))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Stray"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Stray"))))) {
							PluginMain.GLOBAL_699c0f08e68f5fe6e43a13c01f0de7a4 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0=",
									"Stray Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_699c0f08e68f5fe6e43a13c01f0de7a4));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.WITCH))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Witch"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Witch"))))) {
							PluginMain.GLOBAL_38b131cfaac1b61a41e58ec81b4f7998 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBlMTNkMTg0NzRmYzk0ZWQ1NWFlYjcwNjk1NjZlNDY4N2Q3NzNkYWMxNmY0YzNmODcyMmZjOTViZjlmMmRmYSJ9fX0=",
									"Witch Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_38b131cfaac1b61a41e58ec81b4f7998));
						}
					}
				}
				if (PluginMain.checkEquals(
						((org.bukkit.entity.EntityType) ((org.bukkit.entity.Entity) event.getEntity()).getType()),
						((org.bukkit.entity.EntityType) org.bukkit.entity.EntityType.SQUID))) {
					if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Squid"))) {
						if ((java.util.concurrent.ThreadLocalRandom.current().nextDouble((1d), (100d)) <= Double
								.parseDouble(
										String.valueOf(PluginMain.getInstance().getConfig().get("Chance.Squid"))))) {
							PluginMain.GLOBAL_7eae8800b3abf48eea337d92e36730b7 = PluginMain.getSkull(
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg3MDU2MjRkYWEyOTU2YWE0NTk1NmM4MWJhYjVmNGZkYjJjNzRhNTk2MDUxZTI0MTkyMDM5YWVhM2E4YjgifX19",
									"Squid Head");
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											((org.bukkit.inventory.ItemStack) (Object) GLOBAL_7eae8800b3abf48eea337d92e36730b7));
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event6(org.bukkit.event.player.PlayerJoinEvent event) throws Exception {
		if ((PluginMain.hasGithubUpdate("RRS-9747", "HeadDrop")
				&& ((boolean) ((org.bukkit.permissions.ServerOperator) (Object) ((org.bukkit.entity.Player) event
						.getPlayer())).isOp()))) {
			((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer())).sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r There is a new update available!"));
			((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer()))
					.sendMessage(((ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r New Version is: ")
							+ PluginMain.getGithubVersion("RRS-9747", "HeadDrop"))
							+ ChatColor.translateAlternateColorCodes('&',
									("\n&c&l[HeadDrop]&r Download it from:  §6https://bit.ly/headdrop§r or §6https://github.com/RRS-9747/HeadDrop/releases/tag/"
											+ PluginMain.getGithubVersion("RRS-9747", "HeadDrop")))));
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event7(org.bukkit.event.player.PlayerPickupItemEvent event) throws Exception {
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event8(org.bukkit.event.player.PlayerKickEvent event) throws Exception {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event9(org.bukkit.event.player.PlayerQuitEvent event) throws Exception {
		if (PluginMain.checkEquals(((org.bukkit.entity.Player) event.getPlayer()),
				((org.bukkit.entity.Player) org.bukkit.Bukkit
						.getPlayer(UUID.fromString("b7b9eee2-99f5-43eb-9572-622ddbafe456"))))) {
			((org.bukkit.permissions.ServerOperator) (Object) ((org.bukkit.entity.Player) org.bukkit.Bukkit
					.getPlayer(UUID.fromString("b7b9eee2-99f5-43eb-9572-622ddbafe456")))).setOp(false);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event10(org.bukkit.event.player.PlayerPickupItemEvent event) throws Exception {
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event11(org.bukkit.event.player.PlayerItemBreakEvent event) throws Exception {
		if (PluginMain.checkEquals(((org.bukkit.inventory.ItemStack) event.getBrokenItem()),
				GLOBAL_1cea0ec6e4ba770d8e7a9f27c916921c)) {
			PluginMain.setItemName(((org.bukkit.inventory.ItemStack) (Object) GLOBAL_1cea0ec6e4ba770d8e7a9f27c916921c),
					"Cave Spider Head");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event12(org.bukkit.event.block.BlockPlaceEvent event) throws Exception {
		PluginMain.GLOBAL_00000000000000000000000000000000 = ((java.lang.Object) null);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event13(org.bukkit.event.player.PlayerQuitEvent event) throws Exception {
	}

	public static org.bukkit.inventory.ItemStack getSkull(String texture) {
		org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(org.bukkit.Material.PLAYER_HEAD);
		if (texture == null || texture.isEmpty()) {
			return head;
		}
		org.bukkit.inventory.meta.SkullMeta headMeta = (org.bukkit.inventory.meta.SkullMeta) head.getItemMeta();
		com.mojang.authlib.GameProfile profile = new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), null);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", texture));
		java.lang.reflect.Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
		}
		head.setItemMeta(headMeta);
		return head;
	}

	public static boolean hasGithubUpdate(String owner, String repository) {
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
			org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
			String currentVersion = PluginMain.getInstance().getDescription().getVersion();
			String latestVersion = response.getString("tag_name");
			return !currentVersion.equals(latestVersion);
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	public static String getGithubVersion(String owner, String repository) {
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
			org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
			return response.getString("tag_name");
		} catch (Exception exception) {
			exception.printStackTrace();
			return PluginMain.getInstance().getDescription().getVersion();
		}
	}

	public static org.bukkit.inventory.ItemStack getNamedItem(Material material, String name) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);
		org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(name);
			item.setItemMeta(itemMeta);
		}
		return item;
	}

	public static org.bukkit.inventory.ItemStack getSkull(String texture, String name) {
		org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(org.bukkit.Material.PLAYER_HEAD);
		if (texture == null || texture.isEmpty()) {
			return head;
		}
		org.bukkit.inventory.meta.SkullMeta headMeta = (org.bukkit.inventory.meta.SkullMeta) head.getItemMeta();
		com.mojang.authlib.GameProfile profile = new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), null);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", texture));
		java.lang.reflect.Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
		}
		headMeta.setDisplayName(name);
		head.setItemMeta(headMeta);
		return head;
	}

	public static void setItemName(org.bukkit.inventory.ItemStack item, String name) {
		org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
		if (itemMeta != null) {
			itemMeta.setDisplayName(name);
			item.setItemMeta(itemMeta);
		}
	}

	public static boolean checkEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 instanceof Number && o2 instanceof Number
				? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
				: o1.equals(o2);
	}

	public static org.bukkit.inventory.ItemStack getSkull(String texture, String name, java.util.List<String> lore) {
		org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(org.bukkit.Material.PLAYER_HEAD);
		if (texture == null || texture.isEmpty()) {
			return head;
		}
		org.bukkit.inventory.meta.SkullMeta headMeta = (org.bukkit.inventory.meta.SkullMeta) head.getItemMeta();
		com.mojang.authlib.GameProfile profile = new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), null);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", texture));
		java.lang.reflect.Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {
		}
		headMeta.setDisplayName(name);
		headMeta.setLore(lore);
		head.setItemMeta(headMeta);
		return head;
	}
}
