package me.rrs.headdrop.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.headdrop.HeadDrop;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.*;
import java.util.*;

public class Database {

    private final YamlDocument config;
    private boolean isSQLite;
    private HikariDataSource dataSource;

    public Database(YamlDocument config) {
        this.config = config;
    }

    private String getCurrentTimestampFunction() {
        return isSQLite ? "datetime('now')" : "NOW()";
    }

    public void createTable() {
        String createTableSQL;
        if (isSQLite) {
            createTableSQL = "CREATE TABLE IF NOT EXISTS headdrop ("
                    + "name TEXT, "
                    + "uuid TEXT, "
                    + "data INTEGER, "
                    + "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "PRIMARY KEY (name, uuid)"
                    + ");";
        } else {
            createTableSQL = "CREATE TABLE IF NOT EXISTS headdrop ("
                    + "name VARCHAR(16), "
                    + "uuid VARCHAR(36), "
                    + "data INT, "
                    + "last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                    + "PRIMARY KEY (name, uuid)"
                    + ");";
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isSQLite) {
            addMissingColumnsSQLite();
        }
    }

    private void addMissingColumnsSQLite() {
        try (Connection connection = dataSource.getConnection()) {
            String query = "PRAGMA table_info(headdrop);";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                Set<String> existingColumns = new HashSet<>();
                while (rs.next()) {
                    existingColumns.add(rs.getString("name"));
                }

                if (!existingColumns.contains("last_updated")) {
                    String alterTableQuery = "ALTER TABLE headdrop ADD COLUMN last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP";
                    try (PreparedStatement alterStmt = connection.prepareStatement(alterTableQuery)) {
                        alterStmt.execute();
                        System.out.println("Column 'last_updated' added.");
                    } catch (SQLException e) {
                        System.out.println("Error adding missing column 'last_updated'.");
                        e.printStackTrace();
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDataByUuid(String uuid) {
        String query = "SELECT data FROM headdrop WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "SELECT data FROM headdrop WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String selectSQL = "SELECT * FROM headdrop WHERE uuid = ?";
        String insertSQL = "INSERT INTO headdrop (name, uuid, data, last_updated) VALUES (?, ?, ?, " + getCurrentTimestampFunction() + ")";
        String updateSQL = "UPDATE headdrop SET name = ?, data = ?, last_updated = " + getCurrentTimestampFunction() + " WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {

            selectStatement.setString(1, uuid);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                        updateStatement.setString(1, name);
                        updateStatement.setInt(2, data);
                        updateStatement.setString(3, uuid);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Row doesn't exist, insert it
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                        insertStatement.setString(1, name);
                        insertStatement.setString(2, uuid);
                        insertStatement.setInt(3, data);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDataByName(String name, int data) {
        String selectSQL = "SELECT * FROM headdrop WHERE name = ?";
        String insertSQL = "INSERT INTO headdrop (name, uuid, data, last_updated) VALUES (?, ?, ?, " + getCurrentTimestampFunction() + ")";
        String updateSQL = "UPDATE headdrop SET data = ?, last_updated = " + getCurrentTimestampFunction() + " WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {

            selectStatement.setString(1, name);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Row exists, update it
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                        updateStatement.setInt(1, data);
                        updateStatement.setString(2, name);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Row doesn't exist, insert it.
                    // Note: Ensure that Bukkit.getPlayer(name) is not null. Otherwise, handle the null case.
                    UUID uuid = Bukkit.getPlayer(name).getUniqueId();
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                        insertStatement.setString(1, name);
                        insertStatement.setString(2, uuid.toString());
                        insertStatement.setInt(3, data);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getPlayerData() {
        Map<String, Integer> playerData = new HashMap<>();
        String query = "SELECT name, data FROM headdrop";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                String name = result.getString("name");
                int data = result.getInt("data");
                playerData.put(name, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerData;
    }

    public void cleanupOldData(int days) {
        String query;
        if (isSQLite) {
            query = "DELETE FROM headdrop WHERE last_updated < datetime('now', '-' || ? || ' days')";
        } else {
            query = "DELETE FROM headdrop WHERE last_updated < NOW() - INTERVAL ? DAY";
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, days);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Cleaned up " + rowsDeleted + " rows older than " + days + " days.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupDataSource() {
        String connectionString = config.getString("Database.URL");
        String username = config.getString("Database.User");
        String password = config.getString("Database.Password");
        HikariConfig hikariConfig = new HikariConfig();

        if (connectionString.contains("mysql")) {
            isSQLite = false;
            hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
            hikariConfig.setJdbcUrl(connectionString);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        } else if (connectionString.contains("sqlite")) {
            isSQLite = true;
            hikariConfig.setDriverClassName("org.sqlite.JDBC");
            File dataFolder = HeadDrop.getInstance().getDataFolder();
            File dbFile = new File(dataFolder, connectionString.replace("jdbc:sqlite:", ""));
            hikariConfig.setJdbcUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Unsupported database type");
        }

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setLeakDetectionThreshold(2000);

        this.dataSource = new HikariDataSource(hikariConfig);

        if (isSQLite) {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute("PRAGMA journal_mode=WAL;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
