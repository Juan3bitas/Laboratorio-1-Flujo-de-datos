package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AgregarEntrenamientosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasAgregarEntrenamientos;

    @FXML
    private Button bGuardarAgregarEntrenamientos;

    @FXML
    private ChoiceBox<Deporte> cbDeporteEntrenamiento;

    @FXML
    private ChoiceBox<Entrenador> cbEntrenadorEntrenamiento;

    @FXML
    private ChoiceBox<EstadoEntrenamiento> cbEstadoEntrenamiento;

    @FXML
    private DatePicker dFechaEntrenamiento;

    @FXML
    private Label lTituloAgregarEntrenamientos;

    @FXML
    private Text tDeporteEntrenamiento;

    @FXML
    private Text tDuraciónEntrenamiento;

    @FXML
    private Text tEntrenadorEntrenamiento;

    @FXML
    private Text tEstadoEntrenamiento;

    // Agregar los Spinners para horas y minutos
    @FXML
    private Spinner<Integer> spinnerHoras;

    @FXML
    private Spinner<Integer> spinnerMinutos;

    @FXML
    private Text tfechaEntrenamiento;

    @FXML
    public void initialize() {
        // Inicializar los ChoiceBox con valores posibles
        cbEstadoEntrenamiento.getItems().addAll(EstadoEntrenamiento.values());
        cbDeporteEntrenamiento.getItems().addAll(Club.obtenerDeportes()); // Suponiendo que tienes un método para obtener los deportes
        cbEntrenadorEntrenamiento.getItems().addAll(Club.obtenerEntrenador()); // Corregido a "obtenerEntrenadores"

        // Configurar el Spinner de Horas
        SpinnerValueFactory<Integer> horasValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        spinnerHoras.setValueFactory(horasValueFactory);
        spinnerHoras.setEditable(true); // Permite la edición manual

        // Configurar el Spinner de Minutos
        SpinnerValueFactory<Integer> minutosValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        spinnerMinutos.setValueFactory(minutosValueFactory);
        spinnerMinutos.setEditable(true); // Permite la edición manual
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasAgregarEntrenamientos) {
            irAtras();
        } else if (sourceButton == bGuardarAgregarEntrenamientos) {
            guardarEntrenamiento();
        }
    }

    private void irAtras() {
        // Regresar a la página anterior
        try {
            Stage stage = (Stage) bAtrasAgregarEntrenamientos.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenamientos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar entrenamientos");
        }
    }

    private void guardarEntrenamiento() {
        // Obtener valores de la interfaz
        Deporte deporte = cbDeporteEntrenamiento.getValue();
        Entrenador entrenador = cbEntrenadorEntrenamiento.getValue();
        EstadoEntrenamiento estado = cbEstadoEntrenamiento.getValue();
        LocalDate fecha = dFechaEntrenamiento.getValue();

        // Obtener la duración desde los Spinners
        int horas = spinnerHoras.getValue();
        int minutos = spinnerMinutos.getValue();
        Duration duracion = Duration.ofHours(horas).plusMinutes(minutos);

        // Validar campos
        if (deporte == null || entrenador == null || estado == null || fecha == null) {
            showError("Por favor, complete todos los campos.");
            return;
        }

        // Validar que la duración no sea cero
        if (duracion.isZero()) {
            showError("La duración debe ser mayor a cero.");
            return;
        }

        try {
            // Crear un nuevo entrenamiento
            Entrenamiento nuevoEntrenamiento = new Entrenamiento(fecha, duracion, estado, deporte, entrenador);
            Administrador.GuardarEntrenamientosXML();
            Administrador.agregarEntrenamiento(nuevoEntrenamiento); // Suponiendo que tienes un método para agregar entrenamientos al club
                Administrador.verificarDirectorio();
                Administrador.escribirEntrenamientosEnArchivo();

            // Guardar los datos después de agregar el entrenamiento
            Administrador.guardarDatos();
            showConfirmation("Entrenamiento guardado con éxito.");

            // Limpiar los campos
            cbDeporteEntrenamiento.setValue(null);
            cbEntrenadorEntrenamiento.setValue(null);
            cbEstadoEntrenamiento.setValue(null);
            spinnerHoras.getValueFactory().setValue(0);
            spinnerMinutos.getValueFactory().setValue(0);
            dFechaEntrenamiento.setValue(null);
        } catch (IOException e) {
            showError("Error al guardar los datos.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
