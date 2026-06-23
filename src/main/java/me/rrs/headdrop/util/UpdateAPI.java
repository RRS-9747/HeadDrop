package me.rrs.headdrop.util;

import me.rrs.headdrop.HeadDrop;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class UpdateAPI{
    public boolean hasGithubUpdate(String owner, String repository) {
        try {
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) URI.create(
                    "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "HeadDrop");
            try (java.io.InputStream inputStream = connection.getInputStream()) {
                org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
                String currentVersion = HeadDrop.getInstance().getPluginMeta().getVersion();
                String latestVersion = response.getString("tag_name");
                return !currentVersion.equals(latestVersion);
            }
        } catch (Exception exception) {
            HeadDrop.getInstance().getLogger().warning("Failed to check for updates: " + exception.getMessage());
            return false;
        }
    }

    public String getGithubVersion(String owner, String repository) {
        try {
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) URI.create(
                    "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "HeadDrop");
            try (java.io.InputStream inputStream = connection.getInputStream()) {
                org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
                return response.getString("tag_name");
            }
        } catch (Exception exception) {
            return HeadDrop.getInstance().getPluginMeta().getVersion();
        }
    }

    public boolean hasSpigotUpdate(String resourceId) {
        boolean hasUpdate = false;
        try {
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) URI.create("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "HeadDrop");
            try (InputStream inputStream = connection.getInputStream();
                 java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
                if (scanner.hasNext())
                    hasUpdate = !HeadDrop.getInstance().getPluginMeta().getVersion().equalsIgnoreCase(scanner.next());
            }
        } catch (Exception exception) {
            hasUpdate = false;
        }
        return hasUpdate;
    }


    public String getSpigotVersion (String resourceId){
        String newVersion = HeadDrop.getInstance().getPluginMeta().getVersion();
        try {
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) URI.create("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "HeadDrop");
            try (InputStream inputStream = connection.getInputStream();
                 java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
                if (scanner.hasNext()) newVersion = String.valueOf(scanner.next());
            }
        } catch (Exception exception) {
        }
        return newVersion;
    }
}