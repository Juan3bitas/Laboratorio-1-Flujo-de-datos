package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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

public class AgregarEntrenadoresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bAtrasAgregarEntrenadores;

    @FXML
    private Button bGuardarAgregarEntrenadores;

    @FXML
    private ChoiceBox<Deporte> cbEspecialidadEntrenador;

    @FXML
    private Label lTituloAgregarEntrenadores;

    @FXML
    private Text tEspecialidadEntrenador;

    @FXML
    private Text tNombreEntrenadores;

    @FXML
    private TextField tfNombreEntrenador;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {
        cargarDeportes();
    }

    private void cargarDeportes() {
        List<Deporte> deportes = Club.obtenerDeportes();  
        cbEspecialidadEntrenador.getItems().addAll(deportes);
    }

    public void cambiarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("co.edu.uniquindio.laboratorio1.resources.AgregarEntrenadores", locale);
        actualizarTextos();
    }
    
    private void actualizarTextos() {
        lTituloAgregarEntrenadores.setText(bundle.getString("titulo.agregar.entrenadores"));
        tNombreEntrenadores.setText(bundle.getString("nombre.entrenador"));
        tEspecialidadEntrenador.setText(bundle.getString("especialidad.entrenador"));
        bGuardarAgregarEntrenadores.setText(bundle.getString("guardar.agregar.entrenadores"));
        bAtrasAgregarEntrenadores.setText(bundle.getString("atras"));
    }

    @SuppressWarnings("exports")
    @FXML
    public void click(javafx.event.ActionEvent event) {
        // Determinar cuál botón fue presionado y llamar al método correspondiente
        Button sourceButton = (Button) event.getSource();

        if (sourceButton == bAtrasAgregarEntrenadores) {
            irAtras();
        } else if (sourceButton == bGuardarAgregarEntrenadores) {
            guardarEntrenador();
        }
    }

    private void irAtras() {
        // Regresar a la página gestionar con el idioma actual
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionarEntrenadores.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la página gestionar y pasarle el idioma actual
            GestionarEntrenadoresController controller = loader.getController();
            controller.cambiarIdioma(bundle.getLocale()); // Pasa el idioma actual
    
            Stage stage = (Stage) bAtrasAgregarEntrenadores.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Error al cargar la página gestionar entrenadores");
        }
    }

    private void guardarEntrenador() {
        // Validar los campos
        String nombre = tfNombreEntrenador.getText();
        Deporte especialidad = cbEspecialidadEntrenador.getValue();

        if (nombre.isEmpty() || especialidad == null) {
            showError("Por favor, completa todos los campos.");
            return;
        }

        // Crear un nuevo entrenador
        Entrenador nuevoEntrenador = new Entrenador(nombre, especialidad);
        Club.agregarEntrenador(nuevoEntrenador);

        try {
            Club.guardarDatos(); // Guardar los datos después de agregar el deporte
            showConfirmation("Entrenador guardado con éxito.");
            // Limpiar los campos
            tfNombreEntrenador.clear();
            cbEspecialidadEntrenador.setValue(null);
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