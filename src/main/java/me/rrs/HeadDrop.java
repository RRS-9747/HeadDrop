package me.rrs;

import me.rrs.Commands.*;
import me.rrs.Listeners.*;
import me.rrs.Util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;


    public static HeadDrop getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDescription().getName().equals("HeadDrop")){
            Bukkit.getLogger().severe("You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        saveDefaultConfig();


        Metrics metrics = new Metrics(this, 13554);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new EntityDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getCommand("myhead").setExecutor(new MyHead());
        getCommand("head").setExecutor(new OtherHead());
        getCommand("headdrop").setExecutor(new MainCommand());
        getCommand("search").setExecutor(new Search());
        getCommand("customhead").setExecutor(new CustomHead());
        getCommand("update").setExecutor(new AutoUpdate());
        getCommand("headdrop").setTabCompleter(new me.rrs.TabCompleter.HeadDrop());




    }

    @Override
    public void onLoad(){
        if (this.getDataFolder().exists()) {

            File config = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
            File oc = new File(this.getDataFolder().getAbsolutePath() + "/config.yml.old");
            double version = this.getConfig().getDouble("Config.Version");



            if (config.exists()) {
                if (version != 2.4) {

                    if (oc.exists()) {
                        oc.delete();
                    }
                        try {
                            Path oldConfig = Paths.get(this.getDataFolder().getAbsolutePath() + "/config.yml");
                            Files.move(oldConfig, oldConfig.resolveSibling("config.yml.old"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                }
            }

        }

    }




    @Override
    public void onDisable() {

        Bukkit.getLogger().warning("HeadDrop Disable.");

    }



}
