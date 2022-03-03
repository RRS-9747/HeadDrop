package me.rrs.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final String fileName;
    private final String pathToFile;

    public Config(String fileName, String pathToFile) {
        this.pathToFile = pathToFile;
        this.fileName = fileName;
    }

    public void create() throws IOException {
        (new File(this.pathToFile + "/")).mkdirs();
        (new File(this.pathToFile + "/" + this.fileName)).createNewFile();
    }

    public File getFile() {
        return new File(this.pathToFile + "/" + this.fileName);
    }

    private FileConfiguration getFileConfiguration() {
        return YamlConfiguration.loadConfiguration(this.getFile());
    }

    public void saveFile() throws IOException {
        this.getFileConfiguration().save(this.getFile());
    }

    public String getString(String path) {
        return this.getFileConfiguration().getString(path);
    }

    public Integer getInteger(String path) {
        return this.getFileConfiguration().getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.getFileConfiguration().getBoolean(path);
    }

    public void addDefault(String path, Object value) {
        FileConfiguration cfg = this.getFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault(path, value);

        try {
            cfg.save(this.getFile());
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
