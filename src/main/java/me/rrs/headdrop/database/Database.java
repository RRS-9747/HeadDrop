package me.rrs.headdrop.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.headdrop.HeadDrop;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {

    private final YamlDocument config = HeadDrop.getConfiguration();

    private HikariDataSource dataSource;

    public void createTable() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS headdrop (name VARCHAR(16), uuid VARCHAR(36), data INT, PRIMARY KEY (name, uuid));")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDataByUuid(String uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM headdrop WHERE uuid = ?;")) {
            statement.setString(1, uuid);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Integer getDataByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT data FROM headdrop WHERE name = ?;")) {
            statement.setString(1, name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void updateDataByUuid(String uuid, String name, int data) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM headdrop WHERE uuid = ?");
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO headdrop (name, uuid, data) VALUES (?, ?, ?)");
             PreparedStatement updateStatement = connection.prepareStatement("UPDATE headdrop SET name = ?, data = ? WHERE uuid = ?")) {

            // Check if a row already exists for the given UUID
            selectStatement.setString(1, uuid);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Row already exists, update the name and data columns
                    updateStatement.setString(1, name);
                    updateStatement.setInt(2, data);
                    updateStatement.setString(3, uuid);
                } else {
                    // Row doesn't exist, insert a new row with the name, UUID, and data columns
                    insertStatement.setString(1, name);
                    insertStatement.setString(2, uuid);
                    insertStatement.setInt(3, data);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void updateDataByName(String name, int data) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM headdrop WHERE name = ?;")) {
            statement.setString(1, name);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE headdrop SET data = ? WHERE name = ?;")) {
                        updateStatement.setInt(1, data);
                        updateStatement.setString(2, name);
                    }
                } else {
                    try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO headdrop (name, uuid, data) VALUES (?, ?, ?);")) {
                        UUID uuid = Bukkit.getPlayer(name).getUniqueId();
                        insertStatement.setString(1, name);
                        insertStatement.setString(2, uuid.toString());
                        insertStatement.setInt(3, data);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Map<String, Integer> getPlayerData() {
        Map<String, Integer> playerData = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT name, data FROM headdrop;")) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String name = result.getString("name");
                    int data = result.getInt("data");
                    playerData.put(name, data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerData;
    }





    public void setupDataSource() {
        String connectionString = config.getString("Database.URL");
        String username = config.getString("Database.User");
        String password = config.getString("Database.Password");
        HikariConfig config = new HikariConfig();

        // Determine the database type based on the JDBC driver
        String databaseType;
        String driverClassName;
        if (connectionString.contains("mysql")) {
            databaseType = "mysql";
            driverClassName = "com.mysql.cj.jdbc.Driver";
        } else if (connectionString.contains("postgresql")) {
            databaseType = "postgresql";
            driverClassName = "org.postgresql.Driver";
        } else if (connectionString.contains("sqlite")) {
            databaseType = "sqlite";
            driverClassName = "org.sqlite.JDBC";
        } else {
            throw new IllegalArgumentException("Unsupported database type");
        }

        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(connectionString);
        config.setUsername(username);
        config.setPassword(password);

        // Configure additional settings based on the database type
        switch (databaseType) {
            case "mysql":
                config.addDataSourceProperty("cachePrepStmts", "true");
                config.addDataSourceProperty("prepStmtCacheSize", "250");
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
                break;
            case "postgresql":
                config.addDataSourceProperty("cachePreparedStatements", "true");
                config.addDataSourceProperty("prepareThreshold", "3");
                break;
            case "sqlite":
                String databaseName = connectionString.substring(connectionString.lastIndexOf("/") + 1);
                File dataFolder = HeadDrop.getInstance().getDataFolder();
                File dbFile = new File(dataFolder, databaseName.replace("jdbc:sqlite:", ""));
                String absolutePath = dbFile.getAbsolutePath();
                config.setJdbcUrl("jdbc:sqlite:" + absolutePath);
                break;
        }

        this.dataSource = new HikariDataSource(config);
    }
}
