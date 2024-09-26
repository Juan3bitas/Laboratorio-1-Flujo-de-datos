package co.edu.uniquindio.laboratorio1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionarDeportesController {

    @FXML
    private Button bMostrarDeporte;

    @FXML
    private Button bAgregarDeporte;

    @FXML
    private Button bActualizarDeporte;

    @FXML
    private Button bEliminarDeporte;

    @FXML
    private Button bAtrasDeportes;

    @FXML
    private Label lTituloDeportes;

    private ResourceBundle bundle;

    // Método que será llamado desde PaginaPrincipalController para cambiar el idioma
    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.GestionarDeportes", locale);
        actualizarTextos();
    }

    // Actualizar los textos de la interfaz basándose en el idioma seleccionado
    private void actualizarTextos() {
        lTituloDeportes.setText(bundle.getString("titulo.deportes"));
        bMostrarDeporte.setText(bundle.getString("mostrar.deportes"));
        bAgregarDeporte.setText(bundle.getString("agregar.deporte"));
        bActualizarDeporte.setText(bundle.getString("actualizar.deporte"));
        bEliminarDeporte.setText(bundle.getString("eliminar.deporte"));
        bAtrasDeportes.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bMostrarDeporte) {
            mostrarDeportes();
        } else if (sourceButton == bAgregarDeporte) {
            agregarDeporte();
        } else if (sourceButton == bActualizarDeporte) {
            actualizarDeporte();
        } else if (sourceButton == bEliminarDeporte) {
            eliminarDeporte();
        } else if (sourceButton == bAtrasDeportes) {
            irAtras();
        }
    }

    private void mostrarDeportes() {
        // Cargar la interfaz para mostrar deportes
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MostrarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de MostrarDeportesController
            MostrarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasar el idioma actual
    
            Stage stage = (Stage) bMostrarDeporte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Mostrar Deportes");
        }
    }

    private void agregarDeporte() {
        // Cargar la interfaz para agregar un deporte
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de AgregarDeportesController
            AgregarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasar el idioma actual
    
            Stage stage = (Stage) bAgregarDeporte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Agregar Deportes");
        }
    }

    private void actualizarDeporte() {
        // Cargar la interfaz para actualizar un deporte
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ActualizarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de ActualizarDeportesController
            ActualizarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasar el idioma actual
    
            Stage stage = (Stage) bActualizarDeporte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Actualizar Deportes");
        }
    }

    private void eliminarDeporte() {
        // Cargar la interfaz para eliminar un deporte
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de EliminarDeportesController
            EliminarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasar el idioma actual
    
            Stage stage = (Stage) bAgregarDeporte.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Eliminar Deportes");
        }
    }

    private void irAtras() {
        // Regresar a la página principal con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PaginaPrincipal.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página principal y pasarle el idioma actual
            PaginaPrincipalController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasDeportes.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página principal");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
