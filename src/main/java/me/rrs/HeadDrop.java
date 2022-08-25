
package me.rrs;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.Pattern;
import dev.dejvokep.boostedyaml.dvs.segment.Segment;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.rrs.commands.*;
import me.rrs.listeners.EntityDeath;
import me.rrs.listeners.HeadRestore;
import me.rrs.listeners.PlayerJoin;
import me.rrs.tab.HeaddropCMD;
import me.rrs.util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
    public void onEnable() {
        String version = getServer().getVersion();
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

        if (!getDescription().getName().equals("HeadDrop")){
            Bukkit.getLogger().severe("You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new Metrics.SimplePie("discord_bot", () -> String.valueOf(getConfig().getBoolean("Bot.Enable"))));

        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
       if (version.contains("1.16.4") || version.contains("1.16.5")  || version.contains("1.17") || version.contains("1.18") || version.contains("1.19")){
            getServer().getPluginManager().registerEvents(new HeadRestore(), this);
        }


        getCommand("myhead").setExecutor(new MyHead());
        getCommand("head").setExecutor(new OtherHead());
        getCommand("headdrop").setExecutor(new MainCommand());
        getCommand("search").setExecutor(new Search());
        getCommand("customhead").setExecutor(new CustomHead());
        getCommand("headdrop").setTabCompleter(new HeaddropCMD());
        Bukkit.getLogger().info("HeadDrop " + getDescription().getVersion() + " enabled successfully!");
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("HeadDrop Disabled.");

    }



}
