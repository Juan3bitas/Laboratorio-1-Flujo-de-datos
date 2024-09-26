package co.edu.uniquindio.laboratorio1;

import java.io.IOException;
import java.util.logging.*;

public class Utilidades {
    private static Utilidades instance;
    private Logger logger;

    private Utilidades() {
        logger = Logger.getLogger("ClubLogger");
        try {
            FileHandler fileHandler = new FileHandler("club_log.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Capturar todos los niveles
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Utilidades getInstance() {
        if (instance == null) {
            instance = new Utilidades();
        }
        return instance;
    }

    @SuppressWarnings("exports")
    public void escribirLog(Level level, String mensaje) {
        logger.log(level, mensaje);
    }
}