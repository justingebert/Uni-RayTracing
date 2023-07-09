package Objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SkyBox {

    private BufferedImage texture;
    private int textureWidth;
    private int textureHeight;

    public SkyBox(String path) {
        try {
            texture = ImageIO.read(new File(path));
            textureWidth = texture.getWidth();
            textureHeight = texture.getHeight();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
