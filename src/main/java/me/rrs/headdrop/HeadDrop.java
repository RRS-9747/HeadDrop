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
import me.rrs.headdrop.listener.DropCollector;
import me.rrs.headdrop.listener.EntityDeath;
import me.rrs.headdrop.listener.PlayerJoin;
import me.rrs.headdrop.util.TabComplete;
import me.rrs.headdrop.util.UpdateAPI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;
    private static YamlDocument lang;
    private static YamlDocument config;
    private static Database database;


    public static YamlDocument getConfiguration() {
        return config;
    }

    public static YamlDocument getLang() {
        return lang;
    }

    public static HeadDrop getInstance() {
        return instance;
    }

    public static Database getDatabase() {
        return database;
    }


    @Override
    public void onEnable() {

        if (!getDescription().getName().equals("HeadDrop")) {
            Bukkit.getLogger().severe("Please Download a fresh jar from https://www.spigotmc.org/resources/99976/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("██╗  ██╗███████╗ █████╗ ██████╗ ██████╗ ██████╗  █████╗ ██████╗ ");
        Bukkit.getLogger().info("██║  ██║██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗");
        Bukkit.getLogger().info("███████║█████╗  ███████║██║  ██║██║  ██║██████╔╝██║  ██║██████╔╝");
        Bukkit.getLogger().info("██╔══██║██╔══╝  ██╔══██║██║  ██║██║  ██║██╔══██╗██║  ██║██╔═══╝ ");
        Bukkit.getLogger().info("██║  ██║███████╗██║  ██║██████╔╝██████╔╝██║  ██║╚█████╔╝██║     ");
        Bukkit.getLogger().info("╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚════╝ ╚═╝     ");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("--------------------------------------------------------------------------");
        Bukkit.getLogger().info("[HeadDrop] HeadDrop " + getDescription().getVersion() + " by RRS");

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

        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new SimplePie("discord_bot", () -> String.valueOf(getConfig().getBoolean("Bot.Enable"))));
        metrics.addCustomChart(new SimplePie("web", () -> String.valueOf(getConfig().getBoolean("Web.Enable"))));

        database = new Database();
        database.setupDataSource();
        database.createTable();

        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new DropCollector(), this);

        getCommand("head").setExecutor(new Head());
        getCommand("headdrop").setExecutor(new MainCommand());
        getCommand("headdrop").setTabCompleter(new TabComplete());

        try {
            new BukkitRunnable() {
                @Override
                public void run() {
                    updateChecker();
                }
            }.runTaskTimerAsynchronously(this, 0L, 20L * 60 * config.getInt("Config.Update-Checker-Interval"));
        }catch (UnsupportedOperationException e) {
            updateChecker();
        }


        if (config.getBoolean("Web.Enable")) {
            WebsiteController handler = new WebsiteController();
            try {
                handler.start(config.getInt("Web.Port"));
                Bukkit.getLogger().info("[HeadDrop] Website is now online at " + config.getInt("Web.Port" + " port"));
            } catch (IOException e) {
                Bukkit.getLogger().severe("[HeadDrop] Something went wrong with the leaderboard website!");
                throw new RuntimeException(e);
            }
        }

        Bukkit.getLogger().info("[HeadDrop] Enabled successfully!");
        Bukkit.getLogger().info("--------------------------------------------------------------------------");
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
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("headdrop.notify")) {
                    p.sendMessage("--------------------------------");
                    p.sendMessage("You are using HeadDrop " + getDescription().getVersion());
                    p.sendMessage("However version " + newVersion + " is available.");
                    p.sendMessage("You can download it from: " + "https://www.spigotmc.org/resources/99976/");
                    p.sendMessage("--------------------------------");
                }
            }
        }
    }


}
