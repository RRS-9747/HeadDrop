package me.rrs;

import me.rrs.Commands.*;
import me.rrs.Listeners.*;
import me.rrs.Util.Metrics;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;
    private static JDA Bot;


    public static HeadDrop getInstance() {
        return instance;
    }


    public static JDA getBot() {
        return Bot;
    }

    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDescription().getName().equals("HeadDrop")){
            Bukkit.getLogger().severe("You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();


        Metrics metrics = new Metrics(this, 13554);
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
                 Bukkit.getLogger().warning(name + discriminator + "Enable successfully!");

            } catch (LoginException e) {
                Bukkit.getLogger().severe("Error enabling bot!");
                e.printStackTrace();
            }
        }




    }

    @Override
    public void onLoad(){
        if (this.getDataFolder().exists()) {

            File config = new File(this.getDataFolder().getAbsolutePath() + "/config.yml");
            File oc = new File(this.getDataFolder().getAbsolutePath() + "/config.yml.old");
            double version = this.getConfig().getDouble("Config.Version");



            if (config.exists()) {
                if (version != 2.9) {

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
