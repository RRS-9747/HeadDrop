package me.rrs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import me.rrs.HeadDrop;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


public class UpdateAPI {
    public UpdateAPI() {
    }

    public static boolean checkForUpdate(Plugin pl, int spigotResourceID, boolean downloadIfAvailable) {
        boolean update = false;

        Double currentVersion;
        Double versionName;
        try {
            JSONArray versions = getArray(spigotResourceID);
            if (versions.size() == 0) {
                return update;
            }

            JSONObject latest = (JSONObject)versions.get(versions.size() - 1);
            versionName = Double.parseDouble(latest.get("name").toString());
            currentVersion = Double.parseDouble(pl.getDescription().getVersion());
        } catch (NumberFormatException var8) {
            return update;
        }

        if (versionName > currentVersion) {
            update = true;
            if (downloadIfAvailable) {
                download(pl, spigotResourceID);
            }
        }

        return update;
    }

    private static JSONArray getArray(int spigotResourceID) {
        JSONArray array = new JSONArray();

        try {
            array = (JSONArray)JSONValue.parseWithException(connect(spigotResourceID));
        } catch (ParseException var3) {
            var3.printStackTrace();
        }

        return array;
    }

    private static String connect(int spigotResourceID) {
        String json = "[]";

        try {
            URL versionURL = new URL("https://api.spiget.org/v2/resources/" + spigotResourceID + "/versions");
            URLConnection conn = versionURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String input;
            String buffer;
            for(input = ""; (buffer = in.readLine()) != null; input = input + buffer) {
            }

            in.close();
            return input;
        } catch (IOException var7) {
            return json;
        }
    }

    public static boolean download(Plugin pl, int spigotResourceID) {
        File jar = new File(((JavaPlugin)pl).getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        try {
            URL url = new URL("https://api.spiget.org/v2/resources/" + spigotResourceID + "/download");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(jar, false);
            fos.getChannel().transferFrom(rbc, 0L, 9223372036854775807L);
            return true;
        } catch (IOException var6) {
            return false;
        }
    }


    public static boolean hasGithubUpdate(String owner, String repository) {
        try (java.io.InputStream inputStream = new java.net.URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
            String currentVersion = HeadDrop.getInstance().getDescription().getVersion();
            String latestVersion = response.getString("tag_name");
            return !currentVersion.equals(latestVersion);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String getGithubVersion(String owner, String repository) {
        try (java.io.InputStream inputStream = new java.net.URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
            return response.getString("tag_name");
        } catch (Exception exception) {
            exception.printStackTrace();
            return HeadDrop.getInstance().getDescription().getVersion();
        }
    }
}

