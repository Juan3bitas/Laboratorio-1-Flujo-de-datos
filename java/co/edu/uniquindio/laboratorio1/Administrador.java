package co.edu.uniquindio.laboratorio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Administrador {
    private static List<Entrenamiento> listaEntrenamientos = new ArrayList<>();

    private String nombre;
    private String identificacion;

    public Administrador(String nombre, String identificacion) {
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public static List<Entrenamiento> obtenerEntrenamientos() {
        return listaEntrenamientos;
    }

    public static void agregarEntrenamiento(Entrenamiento entrenamiento) {
        LocalDate fechaActual = LocalDate.now();

        // Obtener la fecha y estado del entrenamiento
        LocalDate fechaEntrenamiento = entrenamiento.getFecha();
        EstadoEntrenamiento estadoEntrenamiento = entrenamiento.getEstadoEntrenamiento();

        // Validar que el estado y la fecha sean consistentes
        if (fechaEntrenamiento.isAfter(fechaActual) && estadoEntrenamiento == EstadoEntrenamiento.COMPLETADO) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al agregar un entrenamiento al entrenador. Es posterior a la fecha actual.");
            throw new IllegalArgumentException("Un entrenamiento completado no puede tener una fecha posterior a la fecha actual.");
        }

        if (fechaEntrenamiento.isBefore(fechaActual) && estadoEntrenamiento == EstadoEntrenamiento.PROGRAMADO) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al programar la fecha del entrenamiento. Es anterior a la fecha actual.");
            throw new IllegalArgumentException("Un entrenamiento programado no puede tener una fecha anterior a la fecha actual.");
        }
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Se agregó correctamente el entrenamiento al entrenador.");
        listaEntrenamientos.add(entrenamiento);
    }

    public void eliminarEntrenamiento(Entrenador entrenador, Entrenamiento entrenamiento){
        if (entrenamiento == null) {
            System.out.println("El entrenamiento proporcionado es nulo y no puede ser eliminado.");
        }
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Se eliminó correctamente el entrenamiento.");
        entrenador.eliminarEntrenamiento(entrenamiento);
    }

    public List<Entrenamiento> leerEntrenamientos(Entrenador entrenador) {
        List<Entrenamiento> entrenamientos = entrenador.getEntrenamientos();
        if (entrenamientos.isEmpty()) {
            System.out.println("No se encontraron entrenamientos para el entrenador: " + entrenador.getNombre());
        }
        return entrenamientos;
    }

    public void actualizarEntrenamientos(Entrenamiento entrenamientoModificable, Entrenador entrenador, LocalDate nuevaFecha, 
                                            Duration nuevaDuracion, EstadoEntrenamiento nuevoEstadoEntrenamiento, Deporte nuevoDeporte){
        if (entrenamientoModificable == null) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al actualizar el entrenamiento. El entrenamiento es nulo.");
            throw new IllegalArgumentException("El entrenamiento proporcionado es nulo, por lo que no puede ser actualizado.");
        }

        if (nuevaFecha != null) {
        entrenamientoModificable.setFecha(nuevaFecha);
        }

        if (nuevaDuracion != null) {
        entrenamientoModificable.setDuracion(nuevaDuracion);
        }
        
        if (nuevoEstadoEntrenamiento != null) {
        entrenamientoModificable.setEstadoEntrenamiento(nuevoEstadoEntrenamiento);
        }
        
        if (nuevoDeporte != null) {
        entrenamientoModificable.setDeporte(nuevoDeporte);
        }   
    }

    public static void guardarDatos() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("entrenamientos.dat"))) {
            oos.writeObject(listaEntrenamientos);
        }
    }

    @SuppressWarnings("unchecked")
    public static void cargarDatos() throws IOException, ClassNotFoundException {
        File file = new File("entrenamientos.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                listaEntrenamientos = (List<Entrenamiento>) ois.readObject();
            }
        }
    }

    public static void verificarDirectorio(){
        String directorioPath = "C://Users/usuario/Dropbox/U/Cuarto semestre/Programación III/laboratorio1up/laboratoriodeporte/Recursos_Java";
        File directorio = new File(directorioPath);

        if (!directorio.exists()){
            boolean creado = directorio.mkdirs();
            if (creado){
                System.out.println("Directorio en existencia" + directorioPath);
            }
            else {
                System.out.println("El directorio no existe");
            }
        }
    }

    public static void  escribirEntrenamientosEnArchivo(){
        String ruta = "C://Users/usuario/Dropbox/U/Cuarto semestre/Programación III/laboratorio1up/laboratoriodeporte/Recursos_Java/entrenamientos.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))){
            int i = 0;

            for (Entrenamiento entrenamiento : listaEntrenamientos){
                writer.write(entrenamiento.toString() + "\n");
                writer.newLine();
                i++;

                if (i%10 == 0){
                    writer.flush();
                }
            }
            writer.flush();
            Utilidades.getInstance().escribirLog(Level.CONFIG,"Correcto funcionamiento. Se escribió entrenamiento en un archivo.");
            System.out.println("Se han escrito los entrenamientos en" + ruta);
        } catch (IOException e){
            Utilidades.getInstance().escribirLog(Level.WARNING,"Error en el funcionamiento al escribir un deporte en un archivo.");
            System.out.println("IOException encontrada");
            }

    }

    //
    public static void GuardarEntrenamientosXML () throws IOException{
        String Filepath = "C:/Users/usuario/Downloads/laboratorio1up/laboratoriodeporte/entrenamientos.xml";

        try(BufferedWriter xmlWriter = new BufferedWriter (new FileWriter(Filepath))){

            xmlWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlWriter.write("<entrenamientos>\n");

            for(Entrenamiento entrenamiento: listaEntrenamientos){
                xmlWriter.write("\t<entrenamiento>\n");
                xmlWriter.write("\t\t <Fecha>" + entrenamiento.getFecha()+ "</Fecha>\n");
                xmlWriter.write("\t\t <Duracion>" + entrenamiento.getDuracion() + "</Duracion> \n");
                xmlWriter.write("\t\t <Entrenador>" + entrenamiento.getEntrenador() + "</Entrenador> \n");
                xmlWriter.write("\t\t <Deporte>" + entrenamiento.getDeporte()+ "</Deporte>\n");
                xmlWriter.write("\t</entrenamiento>\n");



            }
            xmlWriter.write("</entrenamientos>\n");

        }

    }
    public static List<Entrenamiento> CargarEntrenamientosDesdeXML() throws IOException {
        List<Entrenamiento> entrenamientos = new ArrayList<>();
        String filepath = "C:/Users/usuario/Downloads/laboratorio1up/laboratoriodeporte/entrenamientos.xml";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            Entrenamiento entrenamiento = null;
            LocalDate fecha = null;
            Duration duracion = null;
            String entrenador = null;
            String deporte = null;

            // Leer el archivo línea por línea
            while ((line = br.readLine()) != null) {
                line = line.trim();  // Quitar espacios en blanco innecesarios

                if (line.startsWith("<entrenamiento>")) {
                    // Nuevo entrenamiento encontrado, reiniciar las variables
                    entrenamiento = new Entrenamiento(fecha, duracion, null, null, null);
                } else if (line.startsWith("<Fecha>")) {
                    // Extraer la fecha
                    String fechaStr = line.replace("<Fecha>", "").replace("</Fecha>", "").trim();
                    fecha = LocalDate.parse(fechaStr);
                } else if (line.startsWith("<Duracion>")) {
                    // Extraer la duración
                    String duracionStr = line.replace("<Duracion>", "").replace("</Duracion>", "").trim();
                    duracion = Duration.parse(duracionStr);
                } else if (line.startsWith("<Entrenador>")) {

                    entrenador = line.replace("<Entrenador>", "").replace("</Entrenador>", "").trim();
                } else if (line.startsWith("<Deporte>")) {

                    deporte = line.replace("<Deporte>", "").replace("</Deporte>", "").trim();
                } else if (line.startsWith("</entrenamiento>")) {
                    // Al cerrar un entrenamiento, agregamos el objeto a la lista
                    entrenamiento = new Entrenamiento(fecha, duracion, deporte, entrenador);
                    entrenamientos.add(entrenamiento);
                }
            }
        }

        return entrenamientos;
    }
}

