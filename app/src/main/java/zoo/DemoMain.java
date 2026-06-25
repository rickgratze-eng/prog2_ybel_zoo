package zoo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoMain {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger(Zoo.class.getName());

        logger.setLevel(Level.FINE);

        Zoo zoo = new Zoo();

        System.out.println("Logger level set to FINE.");
        System.out.println(zoo.summary());
    }
}