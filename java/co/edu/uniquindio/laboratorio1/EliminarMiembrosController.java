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

public class EliminarMiembrosController {

    @FXML
    private Button bAtrasEliminarMiembros;

    @FXML
    private Button bBuscarEliminarMiembros;

    @FXML
    private Button bGuardarEliminarMiembros;

    @FXML
    private Label lTituloEliminarMiembros;

    @FXML
    private ListView<Miembro> lvMiembros;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarMiembros;

    @FXML
    public void initialize() {
        // Cargar todos los deportes en el ListView al inicializar la ventana
        lvMiembros.getItems().addAll(Deporte.obtenerMiembros());
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasEliminarMiembros) {
            irAtras();
        }   else if (sourceButton == bBuscarEliminarMiembros) {
            buscarMiembro();
        } else if (sourceButton == bGuardarEliminarMiembros) {
            eliminarMiembroSeleccionado();
        }
    }

    private void irAtras() {
        // Regresar a la página principal
        try {
            Stage stage = (Stage) bAtrasEliminarMiembros.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarMiembros.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar miembros");
        }
    }

    private void buscarMiembro() {
        String nombreMiembro = tfBuscarMiembros.getText().trim().toLowerCase();
        lvMiembros.getItems().clear();  // Limpiar la lista antes de agregar los resultados de la búsqueda

        if (!nombreMiembro.isEmpty()) {
            for (Miembro miembro : Deporte.obtenerMiembros()) {
                if (miembro.getNombre().toLowerCase().contains(nombreMiembro)) {
                    lvMiembros.getItems().add(miembro);
                }
            }

            if (lvMiembros.getItems().isEmpty()) {
                showError("No se encontraron miembros con ese nombre");
            }
        } else {
            // Si el campo de búsqueda está vacío, mostrar todos los deportes
            lvMiembros.getItems().addAll(Deporte.obtenerMiembros());
        }
    }

    private void eliminarMiembroSeleccionado() {
        Miembro miembroSeleccionado = lvMiembros.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmación de Eliminación");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("¿Estás seguro de que deseas eliminar el miembro: " + miembroSeleccionado.getNombre() + "?");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Deporte.obtenerMiembros().remove(miembroSeleccionado);  // Eliminar el deporte de la lista global
                    lvMiembros.getItems().remove(miembroSeleccionado);  // Eliminarlo de la lista en pantalla

                    try {
                        Deporte.guardarDatos(); // Guardar los datos después de eliminar el deporte
                        showInfo("Miembro eliminado correctamente");
                    } catch (IOException e) {
                        showError("Error al guardar los datos.");
                    }
                }
            });
        } else {
            showError("Por favor, seleccione un miembro para eliminar");
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