package co.edu.uniquindio.laboratorio1;

import java.time.Duration;
import java.time.LocalDate;
import java.util.logging.Level;
import java.io.Serializable;

public class Entrenamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private Duration duracion;
    private EstadoEntrenamiento estadoEntrenamiento;
    private Deporte deporte;
    private Entrenador entrenador;  

    
    public Entrenamiento(LocalDate fecha, Duration duracion, EstadoEntrenamiento estadoEntrenamiento, Deporte deporte, Entrenador entrenador) {
        this.fecha = fecha;
        this.duracion = duracion;
        this.estadoEntrenamiento = estadoEntrenamiento;
        this.deporte = deporte;
        this.entrenador = entrenador;
    }
    
    public Entrenamiento(LocalDate fecha, Duration duracion, String deporte, String entrenador) {
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
    LocalDate fechaActual = LocalDate.now();

    if (estadoEntrenamiento == EstadoEntrenamiento.PROGRAMADO) {
        // Si el estado es: programado, la fecha no puede ser anterior a la fecha actual
        if (fecha.isBefore(fechaActual)) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al programar la fecha del entrenamiento. Es anterior a la fecha actual.");
            throw new IllegalArgumentException("La fecha del entrenamiento no puede ser anterior a la fecha actual.");
        }
    } else if (estadoEntrenamiento == EstadoEntrenamiento.COMPLETADO) {
        // Si el estado es completado, la fecha no puede ser posterior a la fecha actual
        if (fecha.isAfter(fechaActual)) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al programar la fecha del entrenamiento. Es posterior a la fecha actual.");
            throw new IllegalArgumentException("La fecha del entrenamiento completado no puede ser posterior a la fecha actual.");
        }
    }

    this.fecha = fecha;
}
        
    public Duration getDuracion() {
        return duracion;
    }
    
    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public EstadoEntrenamiento getEstadoEntrenamiento() {
        return estadoEntrenamiento;
    }

    public void setEstadoEntrenamiento(EstadoEntrenamiento estadoEntrenamiento) {
        this.estadoEntrenamiento = estadoEntrenamiento;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public void definirEstadoEntrenamietoFinalizado() {
        //Define si el entrenamiento ya terminó al operar con el atributo duración y la fecha actual
        LocalDate ahora = LocalDate.now();
        if (ahora.isAfter(fecha.plus(duracion))) {
            Utilidades.getInstance().escribirLog(Level.INFO, "El entrenamiento ha finalizado.");
                estadoEntrenamiento = EstadoEntrenamiento.COMPLETADO;
        }
    }

    @Override
    public String toString() {
        return fecha + " - " + duracion + " (Estado: " + estadoEntrenamiento + ")" + deporte + entrenador;
    }
    
}