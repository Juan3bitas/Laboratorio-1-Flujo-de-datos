package co.edu.uniquindio.laboratorio1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class Club implements Serializable{
    private static final long serialVersionUID = 1L;
     
    
    private static List<Deporte> listaDeportes = new ArrayList<>();
    private static List<Entrenador> listaEntrenadores = new ArrayList<>();

    private static String nombre;
    private String descripcion;
    private Administrador administrador;


    public Club(String nombre, String descripcion, Administrador administrador) {
        this.descripcion = descripcion;
        this.administrador = administrador;
    }
    
    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    //CRUD

    public static void agregarMiembro(Deporte deporte, Miembro miembro){
        deporte.agregarMiembro(miembro);
    }

    public static void eliminarMiembro(Deporte deporte, Miembro miembro){
        deporte.eliminarMiembro(miembro);
    }

    @SuppressWarnings("static-access")
    public static void inscribirMiembroADeporte(Deporte deporte, Miembro miembro) {
        //Si la dificultad del deporte es alta, los miembros juveniles no pueden ser inscritos
        
        if (deporte.getDificultad() == Dificultad.ALTO && miembro.getTipoMiembro() == TipoMiembro.JUVENIL) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al inscribir un miembro juvenil en un deporte de dificultad alta.");
            throw new IllegalArgumentException("Los miembros juveniles no se pueden inscribir en deportes de alta dificultad.");    
        }
    

        //Si el miembro ya esta inscrito, no se puede volver a inscribir
        if (deporte.obtenerMiembros().contains(miembro)) {
            Utilidades.getInstance().escribirLog(Level.WARNING, "Error al inscribir un miembro que ya estaba inscrito.");
            throw new IllegalArgumentException("El miembro ya está inscrito en este deporte.");
        }
        
        //Inscribe al miembro
        deporte.agregarMiembro(miembro);
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Incripcion del miembro correcta.");
        System.out.println("El miembro " + miembro.getNombre() + " ha sido inscrito en el deporte: " + deporte.getNombre());
    }

    // Método para agregar un deporte
    public static void agregarDeporte(Deporte deporte) {
        Utilidades.getInstance().escribirLog(Level.CONFIG,"Correcto funcionamiento. Se agregó un deporte.");
        listaDeportes.add(deporte);
    }

    // Método para obtener todos los deportes
    public static List<Deporte> obtenerDeportes() {
        return listaDeportes;
    }

    // Método para agregar un entrenador
    public static void agregarEntrenador(Entrenador entrenador) {
        Utilidades.getInstance().escribirLog(Level.CONFIG, "Correcto funcionamiento. Se agregó un entrenador.");
        listaEntrenadores.add(entrenador);
    }

    // Método para obtener todos los entrenadores
    public static List<Entrenador> obtenerEntrenador() {
        return listaEntrenadores;
    }

    public static void guardarDatos() throws IOException {
        try (ObjectOutputStream oosDeportes = new ObjectOutputStream(new FileOutputStream("deportes.dat"));
             ObjectOutputStream oosEntrenadores = new ObjectOutputStream(new FileOutputStream("entrenadores.dat"));) {
            oosDeportes.writeObject(listaDeportes);
            oosEntrenadores.writeObject(listaEntrenadores);
        }
    }

    // Método para cargar datos desde archivos
    @SuppressWarnings("unchecked")
    public static void cargarDatos() throws IOException, ClassNotFoundException {
        File deportesFile = new File("deportes.dat");
        File entrenadoresFile = new File("entrenadores.dat");
        

        // Cargar deportes
        if (deportesFile.exists()) {
            try (ObjectInputStream oisDeportes = new ObjectInputStream(new FileInputStream(deportesFile))) {
                listaDeportes = (List<Deporte>) oisDeportes.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new IOException("Error al cargar los deportes: " + e.getMessage(), e);
            }
        } else {
            System.out.println("El archivo de deportes no existe.");
        }

        // Cargar entrenadores
        if (entrenadoresFile.exists()) {
            try (ObjectInputStream oisEntrenadores = new ObjectInputStream(new FileInputStream(entrenadoresFile))) {
                listaEntrenadores = (List<Entrenador>) oisEntrenadores.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new IOException("Error al cargar los entrenadores: " + e.getMessage(), e);
            }
        } else {
            System.out.println("El archivo de entrenadores no existe.");
        }

    }

    public static void verificarDirectorio(){
        String directorioPath = "C://Users/usuario/Dropbox/U/Cuarto semestre/Programación III/laboratorio1up/laboratoriodeporte/Recursos_Java";
        File directorio = new File(directorioPath);

        if (!directorio.exists()){
            boolean creado = directorio.mkdirs();
            if (creado){
                Utilidades.getInstance().escribirLog(Level.INFO, "Correcto funcionamiento. El directorio existe.");
                System.out.println("Directorio en existencia" + directorioPath);
            }
            else {
                Utilidades.getInstance().escribirLog(Level.INFO, "Correcto funcionamiento. El directorio no existe");
                System.out.println("El directorio no existe");
            }
        }
    }

    public static void  escribirDeportesEnArchivo(){
        String ruta = "C://Users/usuario/Dropbox/U/Cuarto semestre/Programación III/laboratorio1up/laboratoriodeporte/Recursos_Java/deportes.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))){
            int i = 0;

            for (Deporte deporte : listaDeportes){
                writer.write(deporte.toString() + "\n" );
                writer.newLine();
                i++;

                if (i%10 == 0){
                    writer.flush();
                }
            }
            writer.flush();
            Utilidades.getInstance().escribirLog(Level.CONFIG,"Correcto funcionamiento. Se escribió deportes en un archivo.");
            System.out.println("Se han escrito los deportes en" + ruta);
        } catch (IOException e){
            Utilidades.getInstance().escribirLog(Level.WARNING,"Error en el funcionamiento al escribir un deporte en un archivo.");
            System.out.println("IOException encontrada");
        }
    }
}
