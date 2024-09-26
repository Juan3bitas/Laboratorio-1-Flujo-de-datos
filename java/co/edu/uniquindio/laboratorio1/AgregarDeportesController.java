package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
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

public class AgregarDeportesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasAgregarDeportes;

    @FXML
    private Button bGuardarAgregarDeportes;

    @FXML
    private ChoiceBox<Dificultad> cbDificultadDeporte;

    @FXML
    private Label lTituloAgregarDeportes;

    @FXML
    private Text tDescripcionDeporte;

    @FXML
    private Text tDificultadDeporte;

    @FXML
    private Text tNombreDeporte;

    @FXML
    private TextField tfDescripcionDeporte;

    @FXML
    private TextField tfNombreDeporte;

    private ResourceBundle bundle;
    @FXML
    public void initialize() {
        cbDificultadDeporte.getItems().addAll(Dificultad.values());
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.AgregarDeportes", locale);
        actualizarTextos();
    }

    private void actualizarTextos() {
        lTituloAgregarDeportes.setText(bundle.getString("titulo.agregar.deportes"));
        tfNombreDeporte.setPromptText(bundle.getString("nombre.deporte"));
        tfDescripcionDeporte.setPromptText(bundle.getString("descripcion.deporte"));
        tNombreDeporte.setText(bundle.getString("nombre.deporte"));
        tDescripcionDeporte.setText(bundle.getString("descripcion.deporte"));
        tDificultadDeporte.setText(bundle.getString("dificultad.deporte"));
        //cbDificultadDeporte.setPromptText(bundle.getString("dificultad.deporte"));
        bGuardarAgregarDeportes.setText(bundle.getString("guardar.agregar.deportes"));
        bAtrasAgregarDeportes.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasAgregarDeportes) {
            irAtras();
        } else if (sourceButton == bGuardarAgregarDeportes) {
            guardarDeporte();
        }
    }

    private void irAtras() {
        // Regresar a la página gestionar con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarDeportes.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página gestionar y pasarle el idioma actual
            GestionarDeportesController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasAgregarDeportes.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar deportes");
        }
    }

    private void guardarDeporte() {
        String nombre = tfNombreDeporte.getText();
        String descripcion = tfDescripcionDeporte.getText();
        Dificultad dificultad = cbDificultadDeporte.getValue();

        if (nombre == null || nombre.trim().isEmpty() ||
            descripcion == null || descripcion.trim().isEmpty() ||
            dificultad == null) {

            showError("Por favor, complete todos los campos.");
            return;
        }

        Deporte nuevoDeporte = new Deporte(nombre, descripcion, dificultad);
        Club.agregarDeporte(nuevoDeporte); // Guardar el deporte en el club
            Club.verificarDirectorio();
            Club.escribirDeportesEnArchivo();

        // Persistir los cambios
        try {
            Club.guardarDatos(); // Guardar los datos después de agregar el deporte
            showConfirmation("Deporte guardado con éxito.");
            // Limpiar los campos
            tfNombreDeporte.clear();
            tfDescripcionDeporte.clear();
            cbDificultadDeporte.setValue(null);
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
