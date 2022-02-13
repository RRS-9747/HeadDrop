package me.rrs;

import me.rrs.Commands.*;
import me.rrs.Events.*;
import me.rrs.util.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class HeadDrop extends JavaPlugin {

    private static HeadDrop instance;


    public static HeadDrop getInstance() {
        return instance;
    }




    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDescription().getName().equals("HeadDrop")){
            Bukkit.getLogger().log(Level.SEVERE, "You can't change my name!");
            Bukkit.getPluginManager().disablePlugin(this);
        }


        saveDefaultConfig();
        Metrics metrics = new Metrics(this, 13554);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new Entitydeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getCommand("myhead").setExecutor(new MyHead());
        getCommand("head").setExecutor(new OtherHead());
        getCommand("headdrop").setExecutor(new MainCommand());
        getCommand("search").setExecutor(new Search());



    }

    @Override
    public void onLoad(){

    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("HeadDrop Disable!");

    }



}
