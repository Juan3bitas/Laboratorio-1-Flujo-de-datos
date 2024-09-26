package co.edu.uniquindio.laboratorio1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class Deporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private static List<Miembro> miembros = new ArrayList<>();

    private String nombre;
    private String descripcion;
    private Dificultad dificultad;
    private List<Entrenador> entrenadores;

    public Deporte(String nombre, String descripcion, Dificultad dificultad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    //CRUD

    public void agregarEntrenador(Entrenador entrenador){
        //Valida la existencia del entrenador primero, en caso de ya existir, lanza una excepción
        for (Entrenador e : entrenadores){
            if (e.getNombre().equalsIgnoreCase(entrenador.getNombre())){
                Utilidades.getInstance().escribirLog(Level.WARNING, "Error al ingresar al entrenador en un deporte porque ya está en el sistema.");
                throw new IllegalArgumentException("El entrenador ya existe en la base de datos, vuelva a ingresarlo.");
            }
        }
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Incripcion del entrenador correcta.");
        entrenadores.add(entrenador);
    }
    
    public void agregarMiembro(Miembro miembro){
        //Mismo procedimiento de validación del método agregarEntrenador
        for (Miembro m : miembros){
            if(m.getNombre().equalsIgnoreCase(miembro.getNombre())){
                Utilidades.getInstance().escribirLog(Level.WARNING, "Error al ingresar al miembro en un deporte porque ya está en el sistema.");
                throw new IllegalArgumentException("El miembro ya existe en la base de datos, vuelva a ingresarlo.");
            }
        }
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Incripcion del miembro correcta.");
        miembros.add(miembro);
    }

    public void eliminarMiembro(Miembro miembro){
        miembros.remove(miembro);
    }

    public void eliminarEntrenador(Entrenador entrenador) {
        entrenadores.remove(entrenador);
    }

    public static List<Miembro> obtenerMiembros() {
        return miembros;
    }

    public static void guardarDatos() throws IOException {
        try (ObjectOutputStream oosMiembros = new ObjectOutputStream(new FileOutputStream("miembros.dat"))) {
            oosMiembros.writeObject(miembros);
        }
    }

    // Método para cargar datos desde archivos
    @SuppressWarnings("unchecked")
    public static void cargarDatos() throws IOException, ClassNotFoundException {
        File miembrosFile = new File("miembros.dat");

    // Cargar miembros
    if (miembrosFile.exists()) {
        try (ObjectInputStream oisMiembros = new ObjectInputStream(new FileInputStream(miembrosFile))) {
            miembros = (List<Miembro>) oisMiembros.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Error al cargar los miembros: " + e.getMessage(), e);
        }
    } else {
        System.out.println("El archivo de miembros no existe.");
        }
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " (Dificultad: " + dificultad + ")";
    }
    
}
