package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
    
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class MostrarEntrenadoresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasEntrenadores;

    @FXML
    private Label lTituloMostrarEntrenadores;

    @FXML
    private ListView<Entrenador> spMostrarEntrenadores;

    @FXML
    public void initialize() {
        cargarEntrenadores();
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasEntrenadores) {
            irAtras();
        }
    }

    private void irAtras() {
        // Regresar a la página principal
        try {
            Stage stage = (Stage) bAtrasEntrenadores.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenadores.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar entrenadores");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void cargarEntrenadores() {
        List<Entrenador> entrenadores = Club.obtenerEntrenador();
        spMostrarEntrenadores.getItems().addAll(entrenadores);
    }
}