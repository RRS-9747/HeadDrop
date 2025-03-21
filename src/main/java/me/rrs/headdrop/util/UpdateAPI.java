package me.rrs.headdrop.util;

import me.rrs.headdrop.HeadDrop;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateAPI{
    public boolean hasGithubUpdate(String owner, String repository) {
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

    public String getGithubVersion(String owner, String repository) {
        try (java.io.InputStream inputStream = new java.net.URL(
                "https://api.github.com/repos/" + owner + "/" + repository + "/releases/latest").openStream()) {
            org.json.JSONObject response = new org.json.JSONObject(new org.json.JSONTokener(inputStream));
            return response.getString("tag_name");
        } catch (Exception exception) {
            exception.printStackTrace();
            return HeadDrop.getInstance().getDescription().getVersion();
        }
    }

    public boolean hasSpigotUpdate(String resourceId) {
        boolean hasUpdate = false;
        try (InputStream inputStream =
                     new URI("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).toURL().openStream();
             java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
            if (scanner.hasNext())
                hasUpdate = !HeadDrop.getInstance().getDescription().getVersion().equalsIgnoreCase(scanner.next());
        } catch (IOException ioException) {
            ioException.printStackTrace();
            hasUpdate = false;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return hasUpdate;
    }


    public String getSpigotVersion (String resourceId){
        String newVersion = HeadDrop.getInstance().getDescription().getVersion();
        try (InputStream inputStream =
                     new URI("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).toURL().openStream();
             java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
            if (scanner.hasNext()) newVersion = String.valueOf(scanner.next());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return newVersion;
    }
}