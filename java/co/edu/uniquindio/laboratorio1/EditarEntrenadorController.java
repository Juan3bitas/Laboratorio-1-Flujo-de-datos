package co.edu.uniquindio.laboratorio1;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditarEntrenadorController {
    @FXML
    private TextField tfNombreEntrenador;

    @FXML
    private ChoiceBox<Deporte> cbEspecialidadEntrenador;

    @FXML
    private Button bGuardar;

    @FXML
    private Button bCancelar;

    private Entrenador entrenadorSeleccionado;

    @FXML
    public void initialize() {
        cargarDeportes();
    }

    private void cargarDeportes() {
        List<Deporte> deportes = Club.obtenerDeportes();  
        cbEspecialidadEntrenador.getItems().addAll(deportes);
    }

    public void inicializar(Entrenador entrenador) {
        this.entrenadorSeleccionado = entrenador;
        tfNombreEntrenador.setText(entrenador.getNombre());
        cbEspecialidadEntrenador.setValue(entrenador.getEspecialidad());
    }

    @FXML
    public void guardarCambios() {
        // Validar los campos
        String nombre = tfNombreEntrenador.getText();
        Deporte especialidad = cbEspecialidadEntrenador.getValue();

        if (nombre.isEmpty() || especialidad == null) {
            showError("Por favor, completa todos los campos.");
            return;
        }

        // Actualizar el deporte
        entrenadorSeleccionado.setNombre(nombre);
        entrenadorSeleccionado.setEspecialidad(especialidad);

        // Cerrar la ventana
        cerrarVentana();
    }

    @FXML
    public void cancelarEdicion() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombreEntrenador.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
