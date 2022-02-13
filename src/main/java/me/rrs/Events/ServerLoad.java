package me.rrs.Events;

import me.rrs.HeadDrop;
import me.rrs.util.UpdateAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.logging.Level;

public class ServerLoad implements Listener {

    @EventHandler
    public void onLoad(ServerLoadEvent event){


        boolean hasUpdateGitHub = UpdateAPI.hasGithubUpdate("RRS-9747", "HeadDrop");
        boolean updateChecker = HeadDrop.getInstance().getConfig().getBoolean("Config.Update-Checker");
        boolean autoUpdate = HeadDrop.getInstance().getConfig().getBoolean("Config.Auto-Update");
        boolean hasUpdateSpigot = UpdateAPI.checkForUpdate(HeadDrop.getInstance(), 99976, false);
        boolean success = UpdateAPI.download(HeadDrop.getInstance(), 99976);
        boolean isUpdate = UpdateAPI.checkForUpdate(HeadDrop.getInstance(), 99976, false);


        if (hasUpdateGitHub || hasUpdateSpigot){
            if (!autoUpdate) {
                if (updateChecker) {
                    Bukkit.getLogger().warning("HeadDrop new version is available!");
                    Bukkit.getLogger().warning("Download it from: https://bit.ly/HeadDrop");

                }
            }else{
                if (isUpdate) {
                    Bukkit.getLogger().warning("There is an update available, it will now be downloaded!");
                    if (success){
                        Bukkit.getLogger().warning("Downloaded update successfully! Please restart your server");

                    }else Bukkit.getLogger().log(Level.SEVERE, "Failed to download the update! Check your connection.");
                }
            }
        }
    }
}
