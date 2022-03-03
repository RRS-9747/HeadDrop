package me.rrs.Commands;

import me.rrs.HeadDrop;
import me.rrs.Util.Lang;
import me.rrs.Util.UpdateAPI;
import org.apache.commons.io.FileUtils;
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
                        Lang.msg("&a&l[HeadDrop]&r", "Update-successfully", player);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Lang.msg("&a&l[HeadDrop]&r", "No-Update", player);
                }


            }else {
                Lang.noPerm(player);
            }
        }

        return true;
    }
}
