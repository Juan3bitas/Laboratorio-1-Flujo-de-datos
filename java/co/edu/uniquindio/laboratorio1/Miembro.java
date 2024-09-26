package co.edu.uniquindio.laboratorio1;

import java.io.Serializable;

public class Miembro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String direccionDeCorreo;
    private String numeroDeIdentificacion;
    private TipoMiembro tipoMiembro;
    
    public Miembro(String nombre, String direccionDeCorreo, String numeroDeIdentificacion, TipoMiembro tipoMiembro) {
        this.nombre = nombre;
        this.direccionDeCorreo = direccionDeCorreo;
        this.numeroDeIdentificacion = numeroDeIdentificacion;
        this.tipoMiembro = tipoMiembro;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionDeCorreo() {
        return direccionDeCorreo;
    }

    public void setDireccionDeCorreo(String direccionDeCorreo) {
        this.direccionDeCorreo = direccionDeCorreo;
    }

    public String getNumeroDeIdentificacion() {
        return numeroDeIdentificacion;
    }

    public void setNumeroDeIdentificacion(String numeroDeIdentificacion) {
        this.numeroDeIdentificacion = numeroDeIdentificacion;
    }

    public TipoMiembro getTipoMiembro() {
        return tipoMiembro;
    }

    public void setTipoMiembro(TipoMiembro tipoMiembro) {
        this.tipoMiembro = tipoMiembro;
    }


    @Override
    public String toString() {
        return  nombre + " - " + direccionDeCorreo + " - " + numeroDeIdentificacion + " - " + tipoMiembro;
    }

    
}
