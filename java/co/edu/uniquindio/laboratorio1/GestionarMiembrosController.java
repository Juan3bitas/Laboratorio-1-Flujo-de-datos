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

public class GestionarMiembrosController {

    @FXML
    private Button bMostrarMiembro;

    @FXML
    private Button bAgregarMiembro;

    @FXML
    private Button bActualizarMiembro;

    @FXML
    private Button bEliminarMiembro;

    @FXML
    private Button bAtrasMiembro;

    @FXML
    private Label lTituloMiembros;

    private ResourceBundle bundle;

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.GestionarMiembros", locale);
        actualizarTextos();
    }

    private void actualizarTextos() {
        lTituloMiembros.setText(bundle.getString("titulo.miembros"));
        bMostrarMiembro.setText(bundle.getString("mostrar.miembro"));
        bAgregarMiembro.setText(bundle.getString("agregar.miembro"));
        bActualizarMiembro.setText(bundle.getString("actualizar.miembro"));
        bEliminarMiembro.setText(bundle.getString("eliminar.miembro"));
        bAtrasMiembro.setText(bundle.getString("atras"));
    }


    @SuppressWarnings("exports")
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bMostrarMiembro) {
            mostrarMiembros();
        } else if (sourceButton == bAgregarMiembro) {
            agregarMiembro();
        } else if (sourceButton == bActualizarMiembro) {
            actualizarMiembro();
        } else if (sourceButton == bEliminarMiembro) {
            eliminarMiembro();
        } else if (sourceButton == bAtrasMiembro) {
            irAtras();
        }
    }

    private void mostrarMiembros() {
        // Aquí deberías cargar la interfaz para mostrar deportes
        System.out.println("Mostrar Miembros");
        // Por ejemplo, podrías cargar otra escena o abrir una nueva ventana
        try {
            Stage stage = (Stage) bMostrarMiembro.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MostrarMiembros.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Mostrar Miembros");
        }
    }

    private void agregarMiembro() {
        // Aquí deberías cargar la interfaz para agregar un nuevo miembro
        System.out.println("Agregar Miembros");
        try {
            Stage stage = (Stage) bAgregarMiembro.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("AgregarMiembros.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Agregar Miembros");
        }
    }

    private void actualizarMiembro() {
        // Aquí deberías cargar la interfaz para actualizar un miembro existente
        System.out.println("Actualizar Miembros");
        try {
            Stage stage = (Stage) bActualizarMiembro.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ActualizarMiembros.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Actualizar Miembros");
        }
    }

    private void eliminarMiembro() {
        // Aquí deberías cargar la interfaz para eliminar un miembro existente
        System.out.println("Eliminar Miembro");
        try {
            Stage stage = (Stage) bEliminarMiembro.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("EliminarMiembros.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la interfaz de Eliminar Miembros");
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
    
            Stage stage = (Stage) bAtrasMiembro.getScene().getWindow();
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