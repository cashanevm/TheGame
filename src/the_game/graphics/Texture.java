package the_game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    public  static Render floor = loadBitmap("/textures/floor.png");

    public static Render loadBitmap(String path) {
        try {
            BufferedImage image = ImageIO.read(Texture.class.getResource(path));
            int width = image.getWidth();
            int height = image.getHeight();
            Render result = new Render(width,height);
            image.getRGB(0,0,width,height, result.pixels, 0 ,width);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("crash");
        }
    }
}
