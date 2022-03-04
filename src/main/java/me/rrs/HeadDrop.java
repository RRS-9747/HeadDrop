package me.rrs;

import me.rrs.Commands.*;
import me.rrs.Listeners.*;
import me.rrs.Util.Config;
import me.rrs.Util.ConfigUpdater;
import me.rrs.Util.Metrics;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;
    private static JDA Bot;
    private static Config lang;
    private static Config data;

    public static Config getData() {
        return data;
    }
    public static Config getLang() {
        return lang;
    }
    public static HeadDrop getInstance() {
        return instance;
    }
    public static JDA getBot() {
        return Bot;
    }



    @Override
    public void onEnable() {
        instance = this;
        lang = new Config("lang.yml", getDataFolder().getAbsolutePath());
        data = new Config("data.yml", getDataFolder().getAbsolutePath());

        if (!this.getDescription().getName().equals("HeadDrop")){
            Bukkit.getLogger().severe("You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        try {
            lang.create();
            data.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        File langFile = new File(getDataFolder(), "lang.yml");

        try {
            ConfigUpdater.update(this, "config.yml", configFile, Collections.emptyList());
            ConfigUpdater.update(this, "lang.yml", langFile, Collections.emptyList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();




        Metrics metrics = new Metrics(this, 13554);
        metrics.addCustomChart(new Metrics.SimplePie("discord_bot", () -> String.valueOf(getConfig().getBoolean("Bot.Enable"))));
        String token = this.getConfig().getString("Bot.Token");
        
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

        if (this.getConfig().getBoolean("Bot.Enable")) {
            try {
                 Bot = JDABuilder.createDefault(token)
                         .setActivity(Activity.watching("For Head"))
                         .build();

                 String name = Bot.getSelfUser().getName();
                 String discriminator = Bot.getSelfUser().getDiscriminator();
                 Bukkit.getLogger().warning(name + "#" + discriminator + " Enable successfully!");

            } catch (LoginException e) {
                Bukkit.getLogger().severe("Error enabling bot!");
                e.printStackTrace();
            }
        }



    }

    @Override
    public void onLoad(){

    }




    @Override
    public void onDisable() {

        if (getConfig().getBoolean("Bot.Enable")) {
            Bot.shutdownNow();
            Bukkit.getLogger().warning("Bot shutdown Successfully.");
        }
        Bukkit.getLogger().warning("HeadDrop Disabled.");

    }



}
