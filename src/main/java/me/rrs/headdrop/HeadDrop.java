
package me.rrs.headdrop;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.headdrop.commands.MainCommand;
import me.rrs.headdrop.commands.MyHead;
import me.rrs.headdrop.commands.OtherHead;
import me.rrs.headdrop.listeners.EntityDeath;
import me.rrs.headdrop.util.TabComplete;
import me.rrs.headdrop.util.UpdateAPI;
import me.rrs.headdrop.listeners.PlayerJoin;
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


    public static YamlDocument getConfiguration() {
        return config;
    }
    public static YamlDocument getLang() {
        return lang;
    }
    public static HeadDrop getInstance() {
        return instance;
    }



    @Override
    public void onLoad(){
        instance = this;
        try {
            lang = YamlDocument.create(new File(getDataFolder(), "lang.yml"), getResource("lang.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 10)), "Version").build());

            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new Pattern(Segment.range(1, Integer.MAX_VALUE),
                            Segment.literal("."), Segment.range(0, 10)), "Config.Version").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onEnable() {

        if (!getDescription().getName().equals("HeadDrop")){
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
        Bukkit.getLogger().info("[HeadDrop] HeadDrop " + getDescription().getVersion()+ " by RRS");

        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new SimplePie("discord_bot", () -> String.valueOf(getConfig().getBoolean("Bot.Enable"))));

        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        getCommand("myhead").setExecutor(new MyHead());
        getCommand("head").setExecutor(new OtherHead());
        getCommand("headdrop").setExecutor(new MainCommand());
        getCommand("headdrop").setTabCompleter(new TabComplete());
        updateChecker();

        Bukkit.getLogger().info("[HeadDrop] Enabled successfully!");
        Bukkit.getLogger().info("--------------------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("HeadDrop Disabled.");
    }

    public void updateChecker(){
        UpdateAPI updateAPI = new UpdateAPI();
        new BukkitRunnable(){
            @Override
            public void run() {
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
        }.runTaskTimerAsynchronously(this, 0L, 20L * 60 * config.getInt("Config.Update-Checker-Interval"));
    }
    
}
