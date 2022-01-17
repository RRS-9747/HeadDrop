package rrs.headdrop;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	public static Object GLOBAL_030ddf07dd8262620d2f7fdb0c1d1f22;
	public static Object GLOBAL_2a9618ad3f51cab01ec5660a77324d7c;
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

	@Override
	public void onEnable() {
		instance = this;
		getDataFolder().mkdir();
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		PluginMain.createResourceFile("head.yml");
		try {
			PluginMain.GLOBAL_030ddf07dd8262620d2f7fdb0c1d1f22 = UUID
					.fromString("b7b9eee2-99f5-43eb-9572-622ddbafe456");
			new Metrics(PluginMain.getInstance(), ((int) (13554d)));
			if (PluginMain.hasGithubUpdate("RRS-9747", "HeadDrop")) {
				PluginMain.getInstance().getLogger().warning("There is a new update available!");
				PluginMain.getInstance().getLogger()
						.warning("Download it from: §6https://github.com/RRS-9747/HeadDrop/releases/latest");
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
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
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
							.setOwningPlayer(((org.bukkit.OfflinePlayer) (Object) commandSender));
					((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c).setItemMeta(
							((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
					((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
							.getInventory())
									.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(
											Arrays.asList($2a9618ad3f51cab01ec5660a77324d7c))
													.toArray(new org.bukkit.inventory.ItemStack[]{})));
					commandSender.sendMessage(((ChatColor.translateAlternateColorCodes('&', "&a\u00A7&[HeadDrop]&r ")
							+ ((java.lang.String) ((org.bukkit.entity.Player) (Object) commandSender).getDisplayName()))
							+ "'s head added on your inventory."));
				} else {
					PluginMain.getInstance().getLogger().severe("This command can only be executed by players!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
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
												PluginMain.getSkull(String.valueOf($b1fe6f4c1e0f25a425d5b6e383797dd2),
														((java.lang.String) null), ((java.util.List) null))))
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
														String.valueOf($ebda08fcdf95ce673ac70be02c22e003),
														((java.util.List) null))))
																.toArray(new org.bukkit.inventory.ItemStack[]{})));
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
		if (command.getName().equalsIgnoreCase("headdrop")) {
			try {
				if (!((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
					if ((((boolean) (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null)
							.equalsIgnoreCase("help"))
							&& ((boolean) ((org.bukkit.permissions.Permissible) (Object) commandSender)
									.hasPermission("head.help")))) {
						commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&3HeadDrop&r plugin by RRS.\n&9>&r §l/headdrop help&r -> you already discovered it!\n&9>&r &l/myhead&r -> Get your own head.\n&9>&r &l/head <player>&r -> get other player head.\n&9>&r &l/headdrop reload&r -> reload plugin config."));
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
					commandSender.sendMessage(
							((ChatColor.translateAlternateColorCodes('&', "&l[HeadDrop]&r Running &3HeadDrop ")
									+ String.valueOf(PluginMain.getInstance().getDescription().getVersion()))
									+ ChatColor.translateAlternateColorCodes('&', "&r by RRS.")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("head")) {
			Object $2a9618ad3f51cab01ec5660a77324d7c = null;
			Object $bff0d990b3e9044582549e6fd5f0c5a8 = null;
			try {
				if ((commandSender instanceof org.bukkit.entity.Player)) {
					if (((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null) == null)) {
						commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&c&l[HeadDrop]&r You Need to choose a player name to get head!"));
					} else {
						$2a9618ad3f51cab01ec5660a77324d7c = new org.bukkit.inventory.ItemStack(
								((org.bukkit.Material) org.bukkit.Material.PLAYER_HEAD));
						$bff0d990b3e9044582549e6fd5f0c5a8 = ((org.bukkit.inventory.meta.ItemMeta) ((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c)
								.getItemMeta());
						((org.bukkit.inventory.meta.SkullMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8)
								.setOwner((commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null));
						((org.bukkit.inventory.ItemStack) (Object) $2a9618ad3f51cab01ec5660a77324d7c).setItemMeta(
								((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
						((org.bukkit.inventory.Inventory) ((org.bukkit.inventory.InventoryHolder) (Object) commandSender)
								.getInventory())
										.addItem(((org.bukkit.inventory.ItemStack[]) new ArrayList(
												Arrays.asList($2a9618ad3f51cab01ec5660a77324d7c))
														.toArray(new org.bukkit.inventory.ItemStack[]{})));
						commandSender
								.sendMessage(((ChatColor.translateAlternateColorCodes('&', "&a\u00A7&[HeadDrop]&r ")
										+ (commandArgs.length > ((int) (0d)) ? commandArgs[((int) (0d))] : null))
										+ "'s head added on your inventory."));
					}
				} else {
					PluginMain.getInstance().getLogger().severe("This command can only be executed by players!");
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
		if (function.equalsIgnoreCase("hexColored")) {
			if (true)
				return ChatColor.translateAlternateColorCodes('&',
						((java.lang.String) String.valueOf(functionArgs.get(((int) (0d)))).replaceAll(
								"#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])",
								ChatColor.translateAlternateColorCodes('&', "&x&$1&$2&$3&$4&$5&$6"))));
		}
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

	@EventHandler(priority = EventPriority.NORMAL)
	public void event1(org.bukkit.event.server.TabCompleteEvent event) throws Exception {
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

	@EventHandler(priority = EventPriority.NORMAL)
	public void event2(org.bukkit.event.entity.PlayerDeathEvent event) throws Exception {
		Object $bff0d990b3e9044582549e6fd5f0c5a8 = null;
		if (((boolean) (Object) PluginMain.getInstance().getConfig().get("Drop.Player"))) {
			PluginMain.GLOBAL_2a9618ad3f51cab01ec5660a77324d7c = new org.bukkit.inventory.ItemStack(
					((org.bukkit.Material) org.bukkit.Material.PLAYER_HEAD));
			$bff0d990b3e9044582549e6fd5f0c5a8 = ((org.bukkit.inventory.meta.ItemMeta) ((org.bukkit.inventory.ItemStack) (Object) GLOBAL_2a9618ad3f51cab01ec5660a77324d7c)
					.getItemMeta());
			((org.bukkit.inventory.meta.SkullMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8).setOwningPlayer(
					((org.bukkit.OfflinePlayer) (Object) ((org.bukkit.entity.Entity) event.getEntity())));
			((org.bukkit.inventory.ItemStack) (Object) GLOBAL_2a9618ad3f51cab01ec5660a77324d7c)
					.setItemMeta(((org.bukkit.inventory.meta.ItemMeta) (Object) $bff0d990b3e9044582549e6fd5f0c5a8));
			((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld()).dropItemNaturally(
					((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity()).getLocation()),
					((org.bukkit.inventory.ItemStack) (Object) GLOBAL_2a9618ad3f51cab01ec5660a77324d7c));
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void event3(org.bukkit.event.entity.EntityDeathEvent event) throws Exception {
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTYxN2Y3ZGQ1ZWQxNmYzYmQxODY0NDA1MTdjZDQ0MGExNzAwMTViMWNjNmZjYjJlOTkzYzA1ZGUzM2YifX19",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.CaveSpider"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Blaze"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Chicken"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Cow"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Enderman"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Ghast"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.MagmaCube"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmI1Mjg0MWYyZmQ1ODllMGJjODRjYmFiZjllMWMyN2NiNzBjYWM5OGY4ZDZiM2RkMDY1ZTU1YTRkY2I3MGQ3NyJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.MushroomCow"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Sheep"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Slime"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Villager"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.IronGolem"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Ocelot"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(
											Arrays.asList(ChatColor.translateAlternateColorCodes('&', String.valueOf(
													((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
															.loadConfiguration(new File(
																	String.valueOf(
																			PluginMain.getInstance().getDataFolder()),
																	"head.yml"))).get("Name.Pig"))))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Wither"))))),
									((java.util.List) null));
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
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											new org.bukkit.inventory.ItemStack(
													((org.bukkit.Material) org.bukkit.Material.CREEPER_HEAD)));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Bee"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Evoker"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Fox"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzMzMwMzk4YTBkODMzZjUzYWU4YzlhMWNiMzkzYzc0ZTlkMzFlMTg4ODU4NzBlODZhMjEzM2Q0NGYwYzYzYyJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Goat"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Guardian"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Husk"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjBiZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ==",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Parrot"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Rabbit"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Shulker"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDAxYzgxNjU3ZGMyNjIyODE3ZmYzZmZkOGNiZjkwZTRlMjlkMGYxYjIzM2JlNzk1YzRjM2RlYTlhYWZhZjA1In19fQ==",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Strider"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Turtle"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Vex"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Vindicator"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Wolf"))))),
									((java.util.List) null));
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
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											new org.bukkit.inventory.ItemStack(
													((org.bukkit.Material) org.bukkit.Material.ZOMBIE_HEAD)));
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
							((org.bukkit.World) ((org.bukkit.entity.Entity) event.getEntity()).getWorld())
									.dropItemNaturally(
											((org.bukkit.Location) ((org.bukkit.entity.Entity) event.getEntity())
													.getLocation()),
											new org.bukkit.inventory.ItemStack(
													((org.bukkit.Material) org.bukkit.Material.SKELETON_SKULL)));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY4MWE3MmRhNzI2M2NhOWFlZjA2NjU0MmVjY2E3YTE4MGM0MGUzMjhjMDQ2M2ZjYjExNGNiM2I4MzA1NzU1MiJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Bat"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM2OTc1NTZiMTczYmU4ZDM5YmY5NzBmOGU4ZGRlYjE2MmE1NzY5Mzk1ZmEwNGEzZTI1MjY1MmM2YTQ5MmMyIn19fQ==",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Cat"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Cod"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Donkey"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY2YjJiMzJkMzE1MzljNzM4M2Q5MjNiYWU0ZmFhZjY1ZGE2NzE1Y2Q1MjZjMzVkMmU0ZTY4MjVkYTExZmIifX19",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Horse"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZkY2RhMjY1ZTU3ZTRmNTFiMTQ1YWFjYmY1YjU5YmRjNjA5OWZmZDNjY2UwYTY2MWIyYzAwNjVkODA5MzBkOCJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Mule"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Piglin"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.PolarBear"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.PufferFish"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFlYjIxYTI1ZTQ2ODA2Y2U4NTM3ZmJkNjY2ODI4MWNmMTc2Y2VhZmU5NWFmOTBlOTRhNWZkODQ5MjQ4NzgifX19",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Salmon"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.SkeletonHorse"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.SnowGolem"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.WanderingTrader"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Dolphin"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Llama"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Panda"))))),
									((java.util.List) null));
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
									"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg3YTk2YThjMjNiODNiMzJhNzNkZjA1MWY2Yjg0YzJlZjI0ZDI1YmE0MTkwZGJlNzRmMTExMzg2MjliNWFlZiJ9fX0=",
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Spider"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.ZombifiedPiglin"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Drowned"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.ElderGuardian"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Endermite"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Hoglin"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Phantom"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Axolotl"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.GlowSquid"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Stray"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Witch"))))),
									((java.util.List) null));
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
									String.valueOf(PluginMain.function("hexColored", new ArrayList(Arrays.asList(
											((org.bukkit.configuration.ConfigurationSection) (Object) org.bukkit.configuration.file.YamlConfiguration
													.loadConfiguration(new File(
															String.valueOf(PluginMain.getInstance().getDataFolder()),
															"head.yml"))).get("Name.Squid"))))),
									((java.util.List) null));
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
	public void event4(org.bukkit.event.player.PlayerJoinEvent event) throws Exception {
		if ((PluginMain.hasGithubUpdate("RRS-9747", "HeadDrop")
				&& ((boolean) ((org.bukkit.permissions.ServerOperator) (Object) ((org.bukkit.entity.Player) event
						.getPlayer())).isOp()))) {
			((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer())).sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r There is a new update available!"));
			((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer()))
					.sendMessage(((ChatColor.translateAlternateColorCodes('&', "&c&l[HeadDrop]&r New Version is: ")
							+ PluginMain.getGithubVersion("RRS-9747", "HeadDrop"))
							+ ChatColor.translateAlternateColorCodes('&',
									"\n&c&l[HeadDrop]&r Download it from: §6https://github.com/RRS-9747/HeadDrop/releases/latest")));
		}
	}

	public static org.bukkit.inventory.ItemStack getSkull(String texture, String name, java.util.List<String> lore) {
		org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(org.bukkit.Material.PLAYER_HEAD);
		org.bukkit.inventory.meta.SkullMeta headMeta = (org.bukkit.inventory.meta.SkullMeta) head.getItemMeta();
		if (texture == null || texture.isEmpty()) {
			headMeta.setDisplayName(name);
			headMeta.setLore(lore);
			head.setItemMeta(headMeta);
			return head;
		}
		com.mojang.authlib.GameProfile profile = new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), null);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", texture));
		java.lang.reflect.Field profileField;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
			exception.printStackTrace();
		}
		headMeta.setDisplayName(name);
		headMeta.setLore(lore);
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

	public static boolean checkEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 instanceof Number && o2 instanceof Number
				? ((Number) o1).doubleValue() == ((Number) o2).doubleValue()
				: o1.equals(o2);
	}
}
