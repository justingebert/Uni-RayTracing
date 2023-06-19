import Objects.Camera;
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

    int sphereZ = 10;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 1),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            new Vector3D(1, 0, 0),
            resX,
            resY
    );


    public static void main(String[] args) {
        Scene scene = new Scene();
        scene.setActiveCamera(camera);
        int []pixels = Renderer.renderImage(scene, resY, resX);

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
