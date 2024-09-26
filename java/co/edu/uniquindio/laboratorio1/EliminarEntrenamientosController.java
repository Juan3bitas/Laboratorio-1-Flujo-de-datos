package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EliminarEntrenamientosController {

    @FXML
    private Button bAtrasEliminarEntrenamientos;

    @FXML
    private Button bBuscarEliminarEntrenamientos;

    @FXML
    private Button bGuardarEliminarEntrenamientos;

    @FXML
    private Label lTituloEliminarEntrenamientos;

    @FXML
    private ListView<Entrenamiento> lvEntrenamientos;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarEntrenamientos;

    @FXML
    public void initialize() {
        // Cargar todos los entrenamientos en el ListView al inicializar la ventana
        lvEntrenamientos.getItems().addAll(Administrador.obtenerEntrenamientos());
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasEliminarEntrenamientos) {
            irAtras();
        } else if (sourceButton == bBuscarEliminarEntrenamientos) {
            buscarEntrenamiento();
        } else if (sourceButton == bGuardarEliminarEntrenamientos) {
            eliminarEntrenamientoSeleccionado();
        }
    }

    private void irAtras() {
        // Regresar a la página principal
        try {
            Stage stage = (Stage) bAtrasEliminarEntrenamientos.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenamientos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar entrenamientos");
        }
    }

    private void buscarEntrenamiento() {
        String fechaTexto = tfBuscarEntrenamientos.getText().trim();
        LocalDate fecha;
        lvEntrenamientos.getItems().clear();  // Limpiar la lista antes de agregar los resultados de la búsqueda

        if (fechaTexto.isEmpty()) {
            // Si el campo de texto está vacío, mostrar todos los entrenamientos
            lvEntrenamientos.getItems().addAll(Administrador.obtenerEntrenamientos());
            return;
        }
    
        try {
            fecha = LocalDate.parse(fechaTexto); // Intentar parsear la fecha
        } catch (Exception e) {
            showError("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
            return;
        }
    
        List<Entrenamiento> entrenamientos = Administrador.obtenerEntrenamientos();
        for (Entrenamiento entrenamiento : entrenamientos) {
            if (entrenamiento.getFecha().equals(fecha)) {
                lvEntrenamientos.getItems().add(entrenamiento);
            }
        }
    
        if (lvEntrenamientos.getItems().isEmpty()) {
            showError("No se encontraron entrenamientos en la fecha proporcionada.");
        }
    }

    private void eliminarEntrenamientoSeleccionado() {
        Entrenamiento entrenamientoSeleccionado = lvEntrenamientos.getSelectionModel().getSelectedItem();
        if (entrenamientoSeleccionado != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmación de Eliminación");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("¿Estás seguro de que deseas eliminar el entrenamiento en la fecha: " + entrenamientoSeleccionado.getFecha() + "?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Administrador.obtenerEntrenamientos().remove(entrenamientoSeleccionado);  // Eliminar el entrenamiento de la lista global
                    lvEntrenamientos.getItems().remove(entrenamientoSeleccionado);  // Eliminarlo de la lista en pantalla

                    try {
                        Administrador.guardarDatos(); // Guardar los datos después de eliminar el entrenamiento
                        showInfo("Entrenamiento eliminado correctamente");
                    } catch (IOException e) {
                        showError("Error al guardar los datos.");
                    }
                }
            });
        } else {
            showError("Por favor, seleccione un entrenamiento para eliminar");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
