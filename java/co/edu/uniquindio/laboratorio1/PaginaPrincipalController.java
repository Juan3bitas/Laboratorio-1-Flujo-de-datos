package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PaginaPrincipalController {

    private Locale idiomaActual; // Variable para almacenar el idioma actual seleccionado
    private ResourceBundle bundle; // Para almacenar el bundle de recursos actual

    @FXML
    private Button bDeportes;

    @FXML
    private Button bEntrenadores;

    @FXML
    private Button bMiembros;

    @FXML
    private Button bEntrenamientos;

    @FXML
    private Button bEspañol;

    @FXML
    private Button bEnglish;

    @FXML
    private Label lTituloGestionar;

    @FXML
    public void initialize() {
        // Cargar por defecto el idioma español
        idiomaActual = new Locale("es", "CO");
        cambiarIdioma(idiomaActual);
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(ActionEvent event) {
        Button button = (Button) event.getSource();
        String fxmlFile = null;

        // Determina qué archivo FXML cargar según el botón presionado
        if (button == bDeportes) {
            fxmlFile = "GestionarDeportes.fxml";
        } else if (button == bEntrenadores) {
            fxmlFile = "GestionarEntrenadores.fxml";
        } else if (button == bMiembros) {
            fxmlFile = "GestionarMiembros.fxml";
        } else if (button == bEntrenamientos) {
            fxmlFile = "GestionarEntrenamientos.fxml";
        }

        if (fxmlFile != null) {
            try {
                // Cargar el archivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Obtener el controlador de la interfaz cargada
                Object controller = loader.getController();

                // Cambiar el idioma en el controlador
                cambiarIdiomaEnControlador(controller);

                // Cambiar la escena en el escenario actual
                Stage stage = (Stage) button.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
                showError("Error al cargar la interfaz " + fxmlFile);
            }
        }
    }

    // Método para cambiar el idioma actual
    public void cambiarIdioma(Locale nuevoIdioma) {
        idiomaActual = nuevoIdioma;
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.PaginaPrincipal", idiomaActual);
        actualizarTextos();
    }

    // Método para actualizar los textos de la interfaz principal según el idioma
    private void actualizarTextos() {
        lTituloGestionar.setText(bundle.getString("titulo.gestionar"));
        bDeportes.setText(bundle.getString("deportes"));
        bEntrenadores.setText(bundle.getString("entrenadores"));
        bMiembros.setText(bundle.getString("miembros"));
        bEntrenamientos.setText(bundle.getString("entrenamientos"));
        bEspañol.setText(bundle.getString("espanol"));
        bEnglish.setText(bundle.getString("ingles"));
    }

    // Método para propagar el cambio de idioma a otros controladores
    public void cambiarIdiomaEnControlador(Object controller) {
        if (controller instanceof GestionarDeportesController) {
            ((GestionarDeportesController) controller).cambiarIdioma(idiomaActual);
        } else if (controller instanceof GestionarEntrenadoresController) {
            ((GestionarEntrenadoresController) controller).cambiarIdioma(idiomaActual);
        } else if (controller instanceof GestionarMiembrosController) {
            ((GestionarMiembrosController) controller).cambiarIdioma(idiomaActual);
        } else if (controller instanceof GestionarEntrenamientosController) {
            ((GestionarEntrenamientosController) controller).cambiarIdioma(idiomaActual);
        }
    }

    // Métodos para cambiar el idioma a español e inglés
    @FXML
    private void cambiarAEspanol() {
        cambiarIdioma(new Locale("es", "CO"));
    }

    @FXML
    private void cambiarAIngles() {
        cambiarIdioma(new Locale("en", "US"));
    }

    // Método para mostrar mensajes de error
    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
