package co.edu.uniquindio.laboratorio1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    public static void guardarDeportes(List<Deporte> deportes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("deportes.dat"))) {
            oos.writeObject(deportes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Deporte> cargarDeportes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("deportes.dat"))) {
            return (List<Deporte>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarEntrenadores(List<Entrenador> entrenadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("entrenadores.dat"))) {
            oos.writeObject(entrenadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Entrenador> cargarEntrenadores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("entrenadores.dat"))) {
            return (List<Entrenador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarMiembros(List<Miembro> miembros) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("miembros.dat"))) {
            oos.writeObject(miembros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Miembro> cargarMiembros() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("miembros.dat"))) {
            return (List<Miembro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarEntrenamientos(List<Entrenamiento> entrenamientos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("entrenamiento.dat"))) {
            oos.writeObject(entrenamientos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Entrenamiento> cargarEntrenamientos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("entrenamiento.dat"))) {
            return (List<Entrenamiento>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}