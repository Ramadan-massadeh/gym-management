package util;

import java.io.IOException;
import java.util.logging.*;

public class LogSetup {
    public static void configure() {
        try {
            Logger logger = Logger.getLogger("");
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            Logger.getLogger(LogSetup.class.getName()).log(Level.SEVERE, "Failed to configure logger", e);
        }
    }
}
