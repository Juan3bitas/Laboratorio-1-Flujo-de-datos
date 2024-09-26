package co.edu.uniquindio.laboratorio1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        try {
            Club.cargarDatos();
            Deporte.cargarDatos();
            Administrador.cargarDatos();

            // Cargar la interfaz principal desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaginaPrincipal.fxml"));
            Parent root = loader.load();
            
            // Configurar la escena y el escenario
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestión de Club Deportivo");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al cargar la interfaz principal: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showError("Error al cargar los datos: clase no encontrada: " + e.getMessage());
        }
    }

    private void showError(String message) {
        // Método auxiliar para mostrar alertas de error
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}