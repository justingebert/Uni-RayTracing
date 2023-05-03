import Objects.Object3D;
import math.Intersect;
import math.Ray;
import math.Vector3D;

import java.awt.*;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RayTracer {


    static int resX = 1024;
    static int resY = 768;
    static int[] pixels = new int[resX * resY];

    int sphereZ = 10;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 0),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            resX,
            resY
    );


    public static void trace() {
        for (int y = 0; y < resY; ++y) {
            for (int x = 0; x < resX; ++x) {
                //+0.5 to get the center of the pixel
                double u = camera.getLeft() + (camera.getRight() - camera.getLeft()) * (x + 0.5) / resX;
                double v = camera.getBottom() + (camera.getTop() - camera.getBottom()) * (y + 0.5) / resY;

                //Vector for current direction of ray
                Vector3D s1 = camera.getU().scale(u);
                Vector3D s2 = camera.getV().scale(v);
                Vector3D S = s1.add(s2);
                Vector3D Dir = S.normalize();
                Ray ray = new Ray(Dir, camera.getDirection());

                Object3D objects = null;
                Intersect nearestIntersection = null;
                for(Object3D object : Scene.getScene().objects) {

                    Vector3D intersection = object.calculateIntersection(ray);
                    if (intersection != null && (nearestIntersection == null || Vector3D.distance(nearestIntersection.getPosition(), ray.getOrigin()) > Vector3D.distance(intersection, ray.getOrigin()))) {
                        nearestIntersection = new Intersect(ray, object, intersection);

                    }
                }
                //no Object found -> black or BG color
                if (nearestIntersection != null) {
                    pixels[y * resX + x] = nearestIntersection.getHitObject().getColor();
                } else {
                    pixels[y * resX + x] = Color.BLACK.getRGB();
                }

            }
        }
    }
    public static void main(String[] args) {
        trace();
        //RENDER ARRAY IN WINDOW
        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);

        JFrame frame = new JFrame();
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
