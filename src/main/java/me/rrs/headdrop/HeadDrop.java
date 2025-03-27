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
import me.rrs.headdrop.listener.EntityDeath;
import me.rrs.headdrop.listener.HeadGUI;
import me.rrs.headdrop.util.UpdateAPI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;

import java.io.File;
import java.io.IOException;

public class HeadDrop extends JavaPlugin {

    // Instance management
    private static HeadDrop instance;
    public static HeadDrop getInstance() { return instance; }

    // Configuration files
    private YamlDocument lang;
    private YamlDocument config;
    public YamlDocument getConfiguration() { return config; }
    public YamlDocument getLang() { return lang; }

    // Core components
    private Database database;
    public Database getDatabase() { return database; }

    // Lifecycle methods
    @Override
    public void onLoad() {
        instance = this;
        loadConfigurations();
        setupDatabase();
        initializeWorldGuardSupport();
    }

    @Override
    public void onEnable() {
        displayStartupMessage();
        setupMetrics();
        registerComponents();
        startUpdateChecker();
        startWebServer();
        logInfo("Enabled successfully!");
    }

    @Override
    public void onDisable() {
        stopWebServer();
        logInfo("Disabled");
    }

    // region Configuration & Setup
    private void loadConfigurations() {
        try {
            lang = createYamlDocument("lang.yml", "Version");
            config = createYamlDocument("config.yml", "Config.Version");
        } catch (IOException e) {
            logSevere("Failed to load configurations!");
            e.printStackTrace();
        }
    }

    private YamlDocument createYamlDocument(String fileName, String versionKey) throws IOException {
        return YamlDocument.create(
                new File(getDataFolder(), fileName),
                getResource(fileName),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder()
                        .setAutoSave(true)
                        .setVersioning(new Pattern(
                                        Segment.range(1, Integer.MAX_VALUE),
                                        Segment.literal("."),
                                        Segment.range(0, 100)),
                                versionKey
                        ).build()
        );
    }

    private void setupDatabase() {
        database = new Database();
        database.setupDataSource();
        database.createTable();
        database.cleanupOldData(config.getInt("Database.Cleanup", 30));
    }

    private void initializeWorldGuardSupport() {
        try {
            new WorldGuardSupport();
        } catch (NoClassDefFoundError ignored) {
        }
    }

    // region Component Registration
    private void registerComponents() {
        registerEvents();
        registerCommands();
        registerPlaceholderAPI();
        registerGeyserHook();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityDeath(), this);
        pm.registerEvents(new HeadGUI.GUIListener(), this);
    }

    private void registerCommands() {
        getCommand("head").setExecutor(new Head());
        getCommand("headdrop").setExecutor(new MainCommand());
    }

    private void registerPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new HeadDropExpansion().register();
            logInfo("Hooked into PlaceholderAPI!");
        }
    }

    private void registerGeyserHook() {
        if (Bukkit.getPluginManager().isPluginEnabled("Geyser-Spigot")) {
            GeyserApi.api().eventBus().subscribe(
                    new GeyserMC(),
                    GeyserDefineCustomSkullsEvent.class,
                    GeyserMC::onDefineCustomSkulls
            );
            logInfo("Hooked into Geyser!");
        }
    }

    // region Web Server
    private void startWebServer() {
        if (!config.getBoolean("Web.Enable")) return;

        if (!config.getBoolean("Database.Enable")) {
            logSevere("Database must be enabled to host the leaderboard website!");
            return;
        }

        try {
            WebsiteController handler = new WebsiteController();
            handler.start(config.getInt("Web.Port"));
            logInfo("Website online at port " + config.getInt("Web.Port"));
        } catch (IOException e) {
            logSevere("Failed to start web server!");
            throw new RuntimeException(e);
        }
    }

    private void stopWebServer() {
        if (config.getBoolean("Web.Enable")) {
            new WebsiteController().stop();
        }
    }

    // region Update Checking
    private void startUpdateChecker() {
        if (isFolia()) {
            checkForUpdates();
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() { checkForUpdates(); }
        }.runTaskTimerAsynchronously(this, 0L, 20L * 60L * 30L);
    }

    private void checkForUpdates() {
        UpdateAPI updateAPI = new UpdateAPI();
        if (updateAPI.hasGithubUpdate("RRS-9747", "HeadDrop")) {
            String newVersion = updateAPI.getGithubVersion("RRS-9747", "HeadDrop");
            notifyPlayers(newVersion);
            logUpdateInfo(newVersion);
        }
    }

    private void notifyPlayers(String newVersion) {
        String[] message = createUpdateMessage(newVersion);
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("headdrop.notify"))
                .forEach(p -> p.sendMessage(message));
    }

    private void logUpdateInfo(String newVersion) {
        String currentVersion = getPluginMeta().getVersion();
        logInfo("Update available!");
        logInfo("Current: v" + currentVersion + " | New: v" + newVersion);
        logInfo("Download: https://modrinth.com/plugin/headdrop");
    }

    private String[] createUpdateMessage(String newVersion) {
        String current = getPluginMeta().getVersion();
        return new String[] {
                "§e§l--------------------------------",
                "§b§lCurrent Version: §6§l" + current,
                "§b§lAvailable Update: §6§l" + newVersion,
                "§b§lDownload: §6§lhttps://modrinth.com/plugin/headdrop",
                "§e§l--------------------------------"
        };
    }

    // region Utilities
    private void displayStartupMessage() {
        String version = getPluginMeta().getVersion();
        logInfo("\n==============================");
        logInfo("    HeadDrop Plugin v" + version);
        logInfo("==============================\n");
    }

    public boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private void setupMetrics() {
        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new SimplePie("discord_bot", () ->
                String.valueOf(config.getBoolean("Bot.Enable"))));
        metrics.addCustomChart(new SimplePie("web", () ->
                String.valueOf(config.getBoolean("Web.Enable"))));
    }

    private void logInfo(String message) {
        getLogger().info("[HeadDrop] " + message);
    }

    private void logSevere(String message) {
        getLogger().severe("[HeadDrop] " + message);
    }
}
