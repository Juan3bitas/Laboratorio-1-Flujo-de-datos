package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ActualizarDeportesController {

    @FXML
    private Button bAtrasActualizarDeportes;

    @FXML
    private Button bBuscarActualizarDeportes;

    @FXML
    private Button bActualizarDeportes;

    @FXML
    private Label lTituloActualizarDeportes;

    @FXML
    private ListView<Deporte> lvDeportes;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarDeportes;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        // Inicializar la lista de deportes
        lvDeportes.getItems().addAll(Club.obtenerDeportes());
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.ActualizarDeportes", locale);
        actualizarTextos();
    }

    private void actualizarTextos() {
        lTituloActualizarDeportes.setText(bundle.getString("titulo.actualizar.deportes"));
        bBuscarActualizarDeportes.setText(bundle.getString("buscar.deportes"));
        tTextoBuscar.setText(bundle.getString("texto.buscar"));
        bActualizarDeportes.setText(bundle.getString("actualizar.deportes"));
        bAtrasActualizarDeportes.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasActualizarDeportes) {
            irAtras();
        } else if (sourceButton == bBuscarActualizarDeportes) {
            buscarDeporte();
        } else if (sourceButton == bActualizarDeportes) {
            actualizarDeporte();
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
    
            Stage stage = (Stage) bAtrasActualizarDeportes.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar deportes");
        }
    }

    private void buscarDeporte() {
        String nombreDeporte = tfBuscarDeportes.getText().trim().toLowerCase();
        lvDeportes.getItems().clear();  // Limpiar la lista antes de agregar los resultados de la búsqueda

        if (!nombreDeporte.isEmpty()) {
            for (Deporte deporte : Club.obtenerDeportes()) {
                if (deporte.getNombre().toLowerCase().contains(nombreDeporte)) {
                    lvDeportes.getItems().add(deporte);
                }
            }

            if (lvDeportes.getItems().isEmpty()) {
                showError("No se encontraron deportes con ese nombre");
            }
        } else {
            // Si el campo de búsqueda está vacío, mostrar todos los deportes
            lvDeportes.getItems().addAll(Club.obtenerDeportes());
        }
    }

    private void actualizarDeporte() {
        Deporte deporteSeleccionado = lvDeportes.getSelectionModel().getSelectedItem();
        if (deporteSeleccionado == null) {
            showError("Por favor, selecciona un deporte de la lista.");
            return;
        }
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarDeporte.fxml"));
            Parent root = loader.load();
    
            EditarDeporteController controller = loader.getController();
            controller.inicializar(deporteSeleccionado);  // Inicializar el deporte seleccionado
            controller.cambiarIdioma(bundle.getLocale()); // Cambiar idioma según el contexto
    
            Stage stage = new Stage();
            stage.setTitle("Editar Deporte");
            stage.setScene(new Scene(root));
            stage.initOwner(bActualizarDeportes.getScene().getWindow()); // Establecer el propietario
            stage.showAndWait();
    
            // Refrescar la lista después de la edición
            lvDeportes.getItems().clear();
            lvDeportes.getItems().addAll(Club.obtenerDeportes()); // Actualizar la lista de deportes
            Club.guardarDatos(); // Guardar los datos después de la edición
    
        } catch (IOException e) {
            showError("Error al cargar la ventana de edición: " + e.getMessage()); // Mensaje de error más detallado
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}