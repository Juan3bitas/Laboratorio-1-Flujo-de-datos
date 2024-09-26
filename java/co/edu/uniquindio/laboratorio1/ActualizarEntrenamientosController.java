package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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

public class ActualizarEntrenamientosController {

    @FXML
    private Button bAtrasActualizarEntrenamientos;

    @FXML
    private Button bBuscarActualizarEntrenamientos;

    @FXML
    private Button bGuardarActualizarEntrenamientos;

    @FXML
    private Label lTituloActualizarEntrenamientos;

    @FXML
    private ListView<Entrenamiento> lvEntrenamientos;

    @FXML
    private Text tTextoBuscar;

    @FXML
    private TextField tfBuscarEntrenamientos;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        // Inicializar la lista de deportes
        lvEntrenamientos.getItems().addAll(Administrador.obtenerEntrenamientos());
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.ActualizarEntrenamientos", locale);
        actualizarTextos();
    }

    public void actualizarTextos(){
        lTituloActualizarEntrenamientos.setText(bundle.getString("titulo.actualizar.entrenamientos"));
        bBuscarActualizarEntrenamientos.setText(bundle.getString("buscar.entrenamientos"));
        tTextoBuscar.setText(bundle.getString("texto.buscar"));
        bGuardarActualizarEntrenamientos.setText(bundle.getString("actualizar.entrenamientos"));
        bAtrasActualizarEntrenamientos.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasActualizarEntrenamientos) {
            irAtras();
        } else if (sourceButton == bBuscarActualizarEntrenamientos) {
            buscarEntrenamiento();
        } else if (sourceButton == bGuardarActualizarEntrenamientos) {
            actualizarEntrenamiento();
        }
    }

    private void irAtras() {
        // Regresar a la página gestionar con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenamientos.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página gestionar y pasarle el idioma actual
            GestionarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasActualizarEntrenamientos.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar entrenamientos");
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

    private void actualizarEntrenamiento() {
        Entrenamiento entrenamientoSeleccionado = lvEntrenamientos.getSelectionModel().getSelectedItem();
        if (entrenamientoSeleccionado == null) {
            showError("Por favor, selecciona un entrenamiento de la lista.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarEntrenamiento.fxml"));
            Parent root = loader.load();

            EditarEntrenamientoController controller = loader.getController();
            controller.inicializar(entrenamientoSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Entrenamiento");
            stage.setScene(new Scene(root));
            stage.initOwner(bGuardarActualizarEntrenamientos.getScene().getWindow()); // Establece la ventana principal como propietario
            stage.showAndWait();

            // Refrescar la lista después de la edición
            lvEntrenamientos.getItems().clear();
            lvEntrenamientos.getItems().addAll(Administrador.obtenerEntrenamientos()); // Actualizar la lista de entrenamientos
            Administrador.guardarDatos(); // Guardar los datos después de la edición

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
