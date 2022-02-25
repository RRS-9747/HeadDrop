package me.rrs.Listeners;

import me.rrs.HeadDrop;
import me.rrs.Util.UpdateAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;


public class ServerLoad implements Listener {



    @EventHandler
    public void onLoad(ServerLoadEvent event){


        boolean hasUpdateGitHub = UpdateAPI.hasGithubUpdate("RRS-9747", "HeadDrop");
        boolean updateChecker = HeadDrop.getInstance().getConfig().getBoolean("Config.Update-Checker");




        if (hasUpdateGitHub){
                if (updateChecker) {
                    Bukkit.getLogger().warning("HeadDrop new version is available!");
                    Bukkit.getLogger().warning("Download it from: https://bit.ly/HeadDrop");

                }
        }
    }
}
