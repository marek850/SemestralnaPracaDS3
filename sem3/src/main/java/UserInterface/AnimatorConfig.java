package UserInterface;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AnimatorConfig {
    public static final BufferedImage EMPLOYEE;
    public static final BufferedImage EMPLOYEEB;
    public static final BufferedImage EMPLOYEEC;
    public static final BufferedImage BACKGROUND;
    public static final BufferedImage ASSEMBLY_STATION;
    public static final BufferedImage CHAIR;
    public static final BufferedImage TABLE;
    public static final BufferedImage WARDROBE;
    public static final BufferedImage EMPLOYEE_WOOD;
    public static final BufferedImage STORAGE;

    static {
        try {
            EMPLOYEE = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("worker.png"));
            EMPLOYEE_WOOD = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("worker-wood.png"));
            EMPLOYEEB = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("employee2.png"));
            EMPLOYEEC = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("employee3.png"));
            BACKGROUND = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("background.jpg"));
            ASSEMBLY_STATION = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("assembleStation.png"));
            CHAIR = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("chair.png"));
            TABLE = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("table.png"));
            WARDROBE = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("wardrobe.png"));
            STORAGE = ImageIO.read(AnimatorConfig.class.getClassLoader().getResourceAsStream("storage.png"));

        } catch (IOException e) {
            throw new RuntimeException("Nenacitalo obrazky pre animator.", e);
        }
    }
}
