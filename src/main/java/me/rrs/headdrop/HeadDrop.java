package me.rrs.headdrop;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.headdrop.commands.Head;
import me.rrs.headdrop.commands.MainCommand;
import me.rrs.headdrop.database.Database;
import me.rrs.headdrop.hook.GeyserMC;
import me.rrs.headdrop.hook.HeadDropExpansion;
import me.rrs.headdrop.hook.WorldGuardSupport;
import me.rrs.headdrop.listener.*;
import me.rrs.headdrop.util.UpdateAPI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;

import java.io.File;
import java.io.IOException;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;
    private YamlDocument lang;
    private YamlDocument config;
    private Database database;

    public YamlDocument getConfiguration() {
        return config;
    }

    public YamlDocument getLang() {
        return lang;
    }

    public static HeadDrop getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public void onLoad() {
        instance = this;
        try {
            lang = YamlDocument.create(new File(getDataFolder(), "lang.yml"), getResource("lang.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 100)), "Version").build());

            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 100)), "Config.Version").build());

        } catch (IOException e) {
            e.printStackTrace();
        }

        database = new Database(config);
        database.setupDataSource();
        database.createTable();

        try {
            new WorldGuardSupport();
        }catch (NoClassDefFoundError ignored){
        }

    }

    @Override
    public void onEnable() {

        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("==============================");
        Bukkit.getLogger().info("     HeadDrop Plugin v" + getDescription().getVersion());
        Bukkit.getLogger().info("==============================");

        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new SimplePie("discord_bot", () -> String.valueOf(getConfig().getBoolean("Bot.Enable"))));
        metrics.addCustomChart(new SimplePie("web", () -> String.valueOf(getConfig().getBoolean("Web.Enable"))));

        database.cleanupOldData(config.getInt("Database.Cleanup"));

        // Register events
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new EntityDeath(), this);
        pluginManager.registerEvents(new GUI(), this);

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new HeadDropExpansion().register();
        }

        // Set commands
        getCommand("head").setExecutor(new Head());
        getCommand("headdrop").setExecutor(new MainCommand());



        // Check for Geyser-Spigot plugin
        if (Bukkit.getPluginManager().isPluginEnabled("Geyser-Spigot")){
            GeyserApi.api().eventBus().subscribe(new GeyserMC(), GeyserDefineCustomSkullsEvent.class, GeyserMC::onDefineCustomSkulls);
            Bukkit.getLogger().info("Hooked into Geyser!");
        }

        // Start update checker
        if (!isFolia()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    updateChecker();
                }
            }.runTaskTimerAsynchronously(this, 0L, 20L * 60L * 30L);
        } else {
            updateChecker();
        }


        // Start web server if enabled
        if (config.getBoolean("Web.Enable")) {
            if (config.getBoolean("Database.Enable")){
                WebsiteController handler = new WebsiteController();
                try {
                    handler.start(config.getInt("Web.Port"));
                    Bukkit.getLogger().info("[HeadDrop] Website is now online at " + config.getInt("Web.Port" + " port"));
                } catch (IOException e) {
                    Bukkit.getLogger().severe("[HeadDrop] Something went wrong with the leaderboard website!");
                    throw new RuntimeException(e);
                }
            }else Bukkit.getLogger().severe("[HeadDrop] Database need to be enable as well to host the leaderboard website!");
        }

        Bukkit.getLogger().info("[HeadDrop] Enabled successfully!");
    }

    @Override
    public void onDisable() {
        if (config.getBoolean("Web.Enable")) {
            WebsiteController handler = new WebsiteController();
            handler.stop();
        }

        Bukkit.getLogger().info("HeadDrop Disabled.");
    }
    public void updateChecker() {
        UpdateAPI updateAPI = new UpdateAPI();

        if (updateAPI.hasGithubUpdate("RRS-9747", "HeadDrop")) {
            String newVersion = updateAPI.getGithubVersion("RRS-9747", "HeadDrop");
            if (config.getBoolean("Config.Update-Notify")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("headdrop.notify")) {
                        p.sendMessage("§e§l--------------------------------");
                        p.sendMessage("§b§lYou are using §6§lHeadDrop " + getDescription().getVersion());
                        p.sendMessage("§b§lHowever, version §6§l" + newVersion + " §bis available.");
                        p.sendMessage("§b§lYou can download it from: §6§lhttps://www.spigotmc.org/resources/99976/");
                        p.sendMessage("§e§l--------------------------------");
                    }
                }
                if (!Bukkit.getOnlinePlayers().isEmpty()){
                    Bukkit.getLogger().info("--------------------------------");
                    Bukkit.getLogger().info("You are using HeadDrop v" + getDescription().getVersion());
                    Bukkit.getLogger().info("However version " + newVersion + " is available.");
                    Bukkit.getLogger().info("You can download it from: " + "https://www.spigotmc.org/resources/99976/");
                    Bukkit.getLogger().info("--------------------------------");
                }
            }
        }
    }

    public boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }


}
