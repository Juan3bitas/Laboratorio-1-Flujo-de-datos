package co.edu.uniquindio.laboratorio1;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarMiembroController {

    @FXML
    private TextField tfNombreMiembro;

    @FXML
    private ChoiceBox<TipoMiembro> cbTipoMiembro;

    @FXML
    private TextField tfIdentificacion;

    @FXML
    private Button bGuardar;

    @FXML
    private Button bCancelar;

    @FXML
    private TextField tfCorreoMiembro;

    @FXML
    private ChoiceBox<Deporte> cbDeporte;

    private Miembro miembroSeleccionado;

    @FXML
    public void initialize() {
        // Inicialización de datos de la ChoiceBox si es necesario
        cargarDeportes();
        cbTipoMiembro.getItems().setAll(TipoMiembro.values());
    }

    private void cargarDeportes(){
        List<Deporte> deportes = Club.obtenerDeportes();
        cbDeporte.getItems().addAll(deportes);
    }

    public void inicializar(Miembro miembro) {
        this.miembroSeleccionado = miembro;
        tfNombreMiembro.setText(miembro.getNombre());
        tfCorreoMiembro.setText(miembro.getDireccionDeCorreo());
        tfIdentificacion.setText(miembro.getNumeroDeIdentificacion());
        cbTipoMiembro.setValue(miembro.getTipoMiembro());
    }

    @FXML
    public void guardarCambios() {
        // Validar los campos
        String nombre = tfNombreMiembro.getText();
        String correo = tfCorreoMiembro.getText();
        String identificacion = tfIdentificacion.getText();
        TipoMiembro tipo = cbTipoMiembro.getValue();
        Deporte deporte = cbDeporte.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || identificacion.isEmpty() || tipo == null || deporte == null) {
            showError("Por favor, completa todos los campos.");
            return;
        }

        // Actualizar el deporte
        miembroSeleccionado.setNombre(nombre);
        miembroSeleccionado.setDireccionDeCorreo(correo);
        miembroSeleccionado.setNumeroDeIdentificacion(identificacion);
        miembroSeleccionado.setTipoMiembro(tipo);

        // Cerrar la ventana
        cerrarVentana();
    }

    @FXML
    public void cancelarEdicion() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombreMiembro.getScene().getWindow();
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
