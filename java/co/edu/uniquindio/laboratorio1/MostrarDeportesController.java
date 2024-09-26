package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
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

public class MostrarDeportesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasDeportes;

    @FXML
    private Label lTituloMostrarDeportes;

    @FXML
    private ListView<String> spMostrarDeportes;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        cargarDeportes();
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.MostrarDeportes", locale);
        actualizarTextos();
    }

    private void actualizarTextos() {
        lTituloMostrarDeportes.setText(bundle.getString("titulo.mostrar.deportes"));
        bAtrasDeportes.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasDeportes) {
            irAtras();
        }
    }

    private void irAtras() {
        // Regresar a la página gestionar con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página gestionar y pasarle el idioma actual
            GestionarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasDeportes.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar deportes");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void cargarDeportes() {
        List<Deporte> deportes = Club.obtenerDeportes();

        for (Deporte deporte : deportes) {
            spMostrarDeportes.getItems().add(deporte.getNombre() + " - " + deporte.getDescripcion() + " - " + deporte.getDificultad());
        }
    }

}