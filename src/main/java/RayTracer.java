import Objects.Object3D;
import math.Intersect;
import math.Ray;
import math.Vector3D;

import java.awt.*;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;


import javax.swing.*;

public class RayTracer {


    static int resX = 1920;
    static int resY = 1080;
    static double gamma = 2.2;
    static int[] pixels = new int[resX * resY];

    int sphereZ = 10;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 10),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            new Vector3D(1, 0, 0),
            resX,
            resY
    );


    public static void trace() {
        for (int y = 0; y < resY; ++y) {
            for (int x = 0; x < resX; ++x) {

                //gets the ray from the camera to the pixel
                Ray ray = camera.eyeToImage(x, y, resX, resY);

                Object3D objects = null;
                Intersect nearestIntersection = null;
                for(Object3D object : Scene.getScene().objects) {

                    Vector3D intersection = object.calculateIntersection(ray);
                    //if intersection is closer than the last one -> new nearestIntersection
                    if (intersection != null && (nearestIntersection == null || Vector3D.distance(nearestIntersection.getPosition(), ray.getOrigin()) > Vector3D.distance(intersection, ray.getOrigin()))) {
                        nearestIntersection = new Intersect(ray, object, intersection);
                    }
                }
                //TODO multiple light sources
                //TODO multiple lights -> calc without light vectors one one time rest for every light
                if (nearestIntersection != null) {
                    Vector3D light = Scene.getScene().lights.get(0).cookTorranceLight(nearestIntersection.getPosition(), nearestIntersection.getHitObject(),ray);
                    //pixels[y * resX + x] = light.toRGB();

                    // Apply gamma correction
                    double r = light.getX();
                    double g = light.getY();
                    double b = light.getZ();


                    int RGB = (0xFF << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
                    pixels[y * resX + x] = RGB;
                    //pixels[y * resX + x] = nearestIntersection.getHitObject().getColor();
                }
                //no Object found ->  BG color
                else {
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
        frame.setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, resY)); // Adjust the width as needed

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JSlider slider = new JSlider();
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});


        sidePanel.add(button1);
        sidePanel.add(button2);
        sidePanel.add(slider);
        sidePanel.add(comboBox);

        frame.add(sidePanel, BorderLayout.EAST);

        JLabel imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);


        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
