package es.cifpcarlos3.jasperreportss;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/es/cifpcarlos3/jasperreportss/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        // Cargar stylesheet de forma segura (además del definido en FXML)
        java.net.URL css = HelloApplication.class.getResource("src/main/resources/es/cifpcarlos3/jasperreportss/styles");
        stage.setTitle("SPOTIFY - Informes");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
