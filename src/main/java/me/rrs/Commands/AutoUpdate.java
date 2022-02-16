package me.rrs.Commands;

import me.rrs.HeadDrop;
import me.rrs.util.UpdateAPI;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AutoUpdate implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender ,Command command ,String label ,String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission("headdrop.update")) {
                if (UpdateAPI.hasGithubUpdate("RRS-9747", "HeadDrop")) {


                    try {
                        URL url = new URL("https://github.com/RRS-9747/HeadDrop/releases/download/" + UpdateAPI.getGithubVersion("RRS-9747", "HeadDrop") + "/HeadDrop.jar");
                        File file = new File(HeadDrop.getInstance().getServer().getUpdateFolderFile() + "/HeadDrop.jar");
                        FileUtils.copyURLToFile(url, file);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&a&l[HeadDrop]&r Plugin updated successfully. Please restart your server."));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r No new update available."));
                }


            }else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[HeadDrop]&r You don't have permission to run this command!"));
            }
        }

        return true;
    }
}
