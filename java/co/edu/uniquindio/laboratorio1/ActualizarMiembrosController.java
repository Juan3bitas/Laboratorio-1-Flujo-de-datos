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

public class ActualizarMiembrosController {

    @FXML
    private Button bAtrasActualizarMiembros;

    @FXML
    private Button bBuscarActualizarMiembros;

    @FXML
    private Button bGuardarActualizarMiembros;

    @FXML
    private Label lTituloActualizarMiembros;

    @FXML
    private ListView<Miembro> lvMiembros;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarMiembros;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        // Inicializar la lista de deportes
        lvMiembros.getItems().addAll(Deporte.obtenerMiembros());
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.ActualizarMiembros", locale);
        actualizarTextos();
    }

    public void actualizarTextos(){
        lTituloActualizarMiembros.setText(bundle.getString("titulo.actualizar.miembros"));
        bBuscarActualizarMiembros.setText(bundle.getString("buscar.miembros"));
        tTextoBuscar.setText(bundle.getString("texto.buscar"));
        bGuardarActualizarMiembros.setText(bundle.getString("actualizar.miembros"));
        bAtrasActualizarMiembros.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasActualizarMiembros) {
            irAtras();
        } else if (sourceButton == bBuscarActualizarMiembros) {
            buscarMiembro();
        } else if (sourceButton == bGuardarActualizarMiembros) {
            actualizarMiembro();
        }
        
    }

    private void irAtras() {
        // Regresar a la página gestionar con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarMiembros.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página gestionar y pasarle el idioma actual
            GestionarMiembrosController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasActualizarMiembros.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar miembros");
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

    private void actualizarMiembro() {
        Miembro miembroSeleccionado = lvMiembros.getSelectionModel().getSelectedItem();
        if (miembroSeleccionado == null) {
            showError("Por favor, selecciona un miembro de la lista.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarMiembro.fxml"));
            Parent root = loader.load();

            EditarMiembroController controller = loader.getController();
            controller.inicializar(miembroSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Miembro");
            stage.setScene(new Scene(root));
            stage.initOwner(bGuardarActualizarMiembros.getScene().getWindow()); // Establece la ventana principal como propietario
            stage.showAndWait();

            // Refrescar la lista después de la edición
            lvMiembros.getItems().clear();
            lvMiembros.getItems().addAll(Deporte.obtenerMiembros()); // Actualizar la lista de deportes
            Deporte.guardarDatos(); // Guardar los datos después de la edición

        } catch (IOException e) {
            showError("Error al cargar la ventana de edición.");
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