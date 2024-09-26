package co.edu.uniquindio.laboratorio1;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Entrenador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private Deporte especialidad;
    private List<Entrenamiento> entrenamientos;


    public Entrenador(String nombre, Deporte especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.entrenamientos = new ArrayList<>();
    }

    //Getters y Setters
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Deporte getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(Deporte especialidad) {
        this.especialidad = especialidad;
    }

    public List<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }
    
    public void setEntrenamientos(List<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    //CRUD

    public void agregarEntrenamiento(Entrenamiento entrenamiento){
        entrenamientos.add(entrenamiento);
    }

    public void eliminarEntrenamiento(Entrenamiento entrenamiento){
        entrenamientos.remove(entrenamiento);
    }

    @Override
    public String toString() {
        return nombre + " - " + especialidad;
    }
}