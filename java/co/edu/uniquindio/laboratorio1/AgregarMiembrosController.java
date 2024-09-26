package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AgregarMiembrosController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasAgregarMiembros;

    @FXML
    private Button bGuardarAgregarMiembros;

    @FXML
    private ChoiceBox<TipoMiembro> cbTipoMiembro;

    @FXML
    private ChoiceBox<Deporte> cbDeporte;

    @FXML
    private Label lTituloAgregarMiembros;

    @FXML
    private Text tCorreoMiembro;

    @FXML
    private Text tIdentificacionMiembro;

    @FXML
    private Text tNombreMiembro;

    @FXML
    private Text tTipoMiembro;

    @FXML
    private Text tDeporte;

    @FXML
    private TextField tfCorreoMiembro;

    @FXML
    private TextField tfIdentificacionMiembro;

    @FXML
    private TextField tfNombreMiembro;

    @FXML
    public void initialize() {
        cargarDeportes();
        cbTipoMiembro.getItems().addAll(TipoMiembro.values());
    }

    private void cargarDeportes(){
        List<Deporte> deportes = Persistencia.cargarDeportes();
        cbDeporte.getItems().addAll(deportes);
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasAgregarMiembros) {
            irAtras();
        } else if (sourceButton == bGuardarAgregarMiembros) {
            guardarMiembro();
        }
    }

    private void irAtras() {
        // Regresar a la página anterior
        try {
            Stage stage = (Stage) bAtrasAgregarMiembros.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarMiembros.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página de gestionar miembros");
        }
    }

    private void guardarMiembro() {
        String nombre = tfNombreMiembro.getText();
        String correo = tfCorreoMiembro.getText();
        String identificacion = tfIdentificacionMiembro.getText();
        Deporte deporte = cbDeporte.getValue();
        TipoMiembro tipoMiembro = cbTipoMiembro.getValue();

        if (nombre == null || nombre.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            identificacion == null || identificacion.trim().isEmpty() ||
            deporte == null || tipoMiembro == null) {

            showError("Por favor, complete todos los campos.");
            return;
        }

        //Crear miembro en deporte
        
        Miembro miembro = new Miembro(nombre, correo, identificacion, tipoMiembro);
        Club.inscribirMiembroADeporte(deporte, miembro);

        // Persistir los cambios
        try {
            Deporte.guardarDatos(); // Guardar los datos después de agregar el miembro
            showConfirmation("Miembro guardado con éxito.");
            // Limpiar los campos
            tfNombreMiembro.clear();
            tfCorreoMiembro.clear();
            tfIdentificacionMiembro.clear();
            cbDeporte.setValue(null);
            cbTipoMiembro.setValue(null);
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
