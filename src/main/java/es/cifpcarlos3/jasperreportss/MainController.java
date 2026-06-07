package es.cifpcarlos3.jasperreportss;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML
    private TextField txtGenre;
    @FXML
    private Button btnGenerate;
    @FXML
    private Button btnExit;
    @FXML
    private Label lblStatus;

    private static final String REPORT_PATH = "/es/cifpcarlos3/jasperreportss/informes/spotify_artists_report.jrxml";

    @FXML
    public void initialize() {
        try (Connection con = Database.getConnection()) {
            if (con != null) {
                setStatus("Conectado a SQLite correctamente.");
            } else {
                setStatus("No se pudo conectar a la base de datos.");
            }
        } catch (Exception e) {
            setStatus("Error de conexión: " + e.getMessage());
        }
    }

    @FXML
    private void onGenerateReport() {
        String genre = txtGenre != null ? txtGenre.getText() : null;
        if (genre != null) {
            genre = genre.trim();
            if (genre.isEmpty()) genre = null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("P_GENRE_NAME", genre);
        params.put("REPORT_CREATOR", "Creador: " + System.getProperty("user.name", "Desconocido"));

        try (Connection con = Database.getConnection()) {
            if (con == null) {
                showError("No hay conexión con la base de datos.");
                return;
            }

            try (InputStream jrxml = getClass().getResourceAsStream(REPORT_PATH)) {
                if (jrxml == null) {
                    showError("No se encontró la plantilla del informe: " + REPORT_PATH);
                    return;
                }
                JasperReport report = JasperCompileManager.compileReport(jrxml);
                JasperPrint print = JasperFillManager.fillReport(report, params, con);
                JasperViewer.viewReport(print, false);
            }
        } catch (Exception ex) {
            showError("Error generando el informe: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }

    private void setStatus(String msg) {
        if (lblStatus != null) lblStatus.setText(msg);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
