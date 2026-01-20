package es.cifpcarlos3.jasperreportss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Database {

    private static final String DEFAULT_DB_RELATIVE = "baseDeDatos/chinook.db";

    public static Connection getConnection() {
        String customPath = System.getProperty("chinook.db.path");
        Path dbPath;
        if (customPath != null && !customPath.isBlank()) {
            dbPath = Paths.get(customPath);
        } else {
            dbPath = Paths.get(System.getProperty("user.dir"), DEFAULT_DB_RELATIVE);
        }

        try {
            if (!Files.exists(dbPath)) {
                System.err.println("[WARN] No se encuentra la base de datos en: " + dbPath.toAbsolutePath());
            }
            String url = "jdbc:sqlite:" + dbPath.toString();
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
