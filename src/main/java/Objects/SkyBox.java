package Objects;

import math.Ray;
import math.Vector3D;

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

    public Vector3D getColor(Ray ray) {
        Vector3D direction = ray.getDirection().normalize();
        double u = 0.5 + (Math.atan2(direction.getZ(), direction.getX()) / (2 * Math.PI));
        double v = 0.5 - (Math.asin(direction.getY()) / Math.PI);

        int x = (int) (u * textureWidth);
        int y = (int) (v * textureHeight);

        int rgb = texture.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return new Vector3D(r / 255.0, g / 255.0, b / 255.0);
    }

}
