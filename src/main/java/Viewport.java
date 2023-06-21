import Objects.Camera;
import math.Vector3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Viewport extends JFrame {

    private JFrame frame;
    private JPanel sidePanel;
    private JDialog settingsDialog;

    Image image;
    private JLabel imageLabel;
    private JButton button1;
    private JSlider slider;
    private JComboBox<String> comboBox;
    //private Scene scene;
    private int[] pixels;
    static int resX;
    static int resY;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 1),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            new Vector3D(1, 0, 0),
            resX,
            resY
    );

    public Viewport(Scene scene, int resX, int resY) {
        scene.setActiveCamera(camera);
        //this.scene = scene;

        this.resX = resX;
        this.resY = resY;

        int[] pixels = Renderer.renderImage(scene, resY, resX);

        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);

        this.frame = new JFrame();
        frame.setTitle("Ray Tracer Viewport");

        this.imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);

    }

    public void updateImage(Scene scene) {
        int[] pixels = Renderer.renderImage(scene, resY, resX);
        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);
        imageLabel.setIcon(new ImageIcon(image));
        frame.repaint();
        System.out.println("Image updated.");
    }

    public void renderToImage(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        System.out.println("Rendering to image...");
        //Renderer.renderScene(scene, image.getGraphics(), width, height, 1F);

        File imgFile = new File("output.png");
        ImageIO.write(image, "PNG", new FileOutputStream(imgFile));
        System.out.println("Image saved.");

        Desktop.getDesktop().open(imgFile);
    }

    public void show() {
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }


    /*public void before() {
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
    }*/

}
