package co.edu.uniquindio.laboratorio1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;

public class EditarEntrenamientoController {

    @FXML
    private TextField tfFechaEntrenamiento;

    @FXML
    private TextField tfDuracionEntrenamiento;

    @FXML
    private ChoiceBox<EstadoEntrenamiento> cbEstadoEntrenamiento;

    @FXML
    private Button bGuardar;

    @FXML
    private Button bCancelar;

    private Entrenamiento entrenamientoSeleccionado;

    @FXML
    public void initialize() {
        // Inicializa los valores predeterminados del ChoiceBox
        cbEstadoEntrenamiento.getItems().setAll(EstadoEntrenamiento.values());
    }

    public void inicializar(Entrenamiento entrenamiento) {
        this.entrenamientoSeleccionado = entrenamiento;
        tfFechaEntrenamiento.setText(entrenamiento.getFecha().toString());
        tfDuracionEntrenamiento.setText(String.valueOf(entrenamiento.getDuracion()));
        cbEstadoEntrenamiento.setValue(entrenamiento.getEstadoEntrenamiento());
    }

    @FXML
    private void guardarCambios() {
        // Validar los campos
        String fechaTexto = tfFechaEntrenamiento.getText().trim();
        String duracionTexto = tfDuracionEntrenamiento.getText().trim();
        EstadoEntrenamiento estado = cbEstadoEntrenamiento.getValue();

        LocalDate fecha;
        Duration duracion;

        try {
            fecha = LocalDate.parse(fechaTexto);
        } catch (Exception e) {
            showError("Formato de fecha inválido. Use el formato YYYY-MM-DD.");
            return;
        }

        try {
            duracion = Duration.parse(duracionTexto);
        } catch (Exception e) {
            showError("Formato de duración inválido. Use el formato ISO 8601, por ejemplo, PT1H30M.");
            return;
        }

        if (estado == null) {
            showError("Por favor, seleccione el estado del entrenamiento.");
            return;
        }

        // Actualizar el entrenamiento
        entrenamientoSeleccionado.setFecha(fecha);
        entrenamientoSeleccionado.setDuracion(duracion);
        entrenamientoSeleccionado.setEstadoEntrenamiento(estado);

        // Cerrar la ventana
        cerrarVentana();
    }

    @FXML
    public void cancelarEdicion() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfFechaEntrenamiento.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
