package co.edu.uniquindio.laboratorio1;

import java.io.IOException;

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

public class EliminarEntrenadoresController {

    @FXML
    private Button bAtrasEliminarEntrenadores;

    @FXML
    private Button bBuscarEliminarEntrenadores;

    @FXML
    private Button bGuardarEliminarEntrenadores;

    @FXML
    private Label lTituloEliminarEntrenadores;

    @FXML
    private ListView<Entrenador> lvEntrenadores;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarEntrenadores;

    @FXML
    public void initialize() {
    // Cargar todos los entrenadores en el ListView al inicializar la ventana
        lvEntrenadores.getItems().addAll(Club.obtenerEntrenador());

        
    }
    
    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasEliminarEntrenadores) {
            irAtras();
        } else if (sourceButton == bBuscarEliminarEntrenadores) {
            buscarEntrenador();
        } else if (sourceButton == bGuardarEliminarEntrenadores) {
            eliminarEntrenadorSeleccionado();
        }
    }

    private void irAtras() {
        // Regresar a la página principal
        try {
            Stage stage = (Stage) bAtrasEliminarEntrenadores.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenadores.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar entrenadores");
        }
    }

    private void buscarEntrenador() {
        String nombreEntrenador = tfBuscarEntrenadores.getText().trim().toLowerCase();
        lvEntrenadores.getItems().clear();  // Limpiar la lista antes de agregar los resultados de la búsqueda

        if (!nombreEntrenador.isEmpty()) {
            for (Entrenador entrenador : Club.obtenerEntrenador()) {
                if (entrenador.getNombre().toLowerCase().contains(nombreEntrenador)) {
                    lvEntrenadores.getItems().add(entrenador);
                }
            }

            if (lvEntrenadores.getItems().isEmpty()) {
                showError("No se encontraron entrenadores con ese nombre");
            }
        } else { 
            // Si el campo de búsqueda está vacío, mostrar todos los entrenadores
            lvEntrenadores.getItems().addAll(Club.obtenerEntrenador());
        }
    }

    private void eliminarEntrenadorSeleccionado() {
        Entrenador entrendorSeleccionado = lvEntrenadores.getSelectionModel().getSelectedItem();
        if (entrendorSeleccionado != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmación de Eliminación");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("¿Estás seguro de que deseas eliminar el entrenador: " + entrendorSeleccionado.getNombre() + "?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Club.obtenerEntrenador().remove(entrendorSeleccionado);  // Eliminar el entrenador de la lista global
                    lvEntrenadores.getItems().remove(entrendorSeleccionado);  // Eliminarlo de la lista en pantalla

                    try {
                        Club.guardarDatos(); // Guardar los datos después de eliminar el entrenador
                        showInfo("Deporte eliminado correctamente");
                    } catch (IOException e) {
                        showError("Error al guardar los datos.");
                    }
                }
            });
        } else {
            showError("Por favor, seleccione un deporte para eliminar");
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