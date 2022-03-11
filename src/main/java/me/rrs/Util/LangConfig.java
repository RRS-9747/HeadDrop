package me.rrs.Util;

import me.rrs.HeadDrop;

import java.io.IOException;

public class LangConfig {

    public void loadLang(){
        try {
            HeadDrop.getLang().create();
            HeadDrop.getLang().addDefault("MyHead-Success", "Your Head added in your inventory!");
            HeadDrop.getLang().addDefault("Head-Success", "%player%'s Head added in your inventory");
            HeadDrop.getLang().addDefault("Head-Error", "You Need to give a player name to get head!");
            HeadDrop.getLang().addDefault("Head-Search-Error", "You need to give a name to search for head!");
            HeadDrop.getLang().addDefault("Custom-Head-Success", "Head Added on your Inventory");
            HeadDrop.getLang().addDefault("Reload", "Plugin reloaded!");
            HeadDrop.getLang().addDefault("Permission-Error", "You don't have permission to execute this command!");
            HeadDrop.getLang().addDefault("Update-successfully", "Plugin updated successfully. Please restart your server.");
            HeadDrop.getLang().addDefault("No-Update", "No new update available.");
            HeadDrop.getLang().addDefault("Base64-Error", "You need to give a base64 code");
            HeadDrop.getLang().addDefault("Player-Command", "This is player only command!");
            HeadDrop.getLang().addDefault("Searching", "Searching for %name%...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
