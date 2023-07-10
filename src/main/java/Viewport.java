import Objects.Camera;
import math.Vector3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private Scene scene;
    private int[] pixels;
    static int resX;
    static int resY;
    private double roughness = 0.5;
    private double lightIntensity = 1.0;
    private double ioR = 1.5;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 1),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            new Vector3D(1, 0, 0),
            resX,
            resY
    );

    public Viewport(int resX, int resY) {
        scene = new Scene(0.5,1.0,1.5);
        scene.setActiveCamera(camera);

        this.resX = resX;
        this.resY = resY;

        int[] pixels = Renderer.renderImage(scene, resY, resX, 6);

        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);


        //*FRAME*//
        this.frame = new JFrame();
        frame.setTitle("Ray Tracer Viewport");
        //*PANELS*//
        JPanel sidePanelRight = new JPanel();
        sidePanelRight.setLayout(new BoxLayout(sidePanelRight, BoxLayout.Y_AXIS));
        sidePanelRight.setPreferredSize(new Dimension(200, resY));

        JPanel sidePanelLeft = new JPanel();
        sidePanelLeft.setLayout(new BoxLayout(sidePanelRight, BoxLayout.Y_AXIS));
        sidePanelLeft.setPreferredSize(new Dimension(200, resY));

        //*OPTIONS RIGHT*//
        JLabel roughnessLabel = new JLabel("Roughness");
        JSlider roughnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        roughnessSlider.setMajorTickSpacing(10);

        JLabel lightIntensityLabel = new JLabel("lightIntensity");
        JSlider lightIntensitySlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        lightIntensitySlider.setMajorTickSpacing(10);

        JLabel ioRLabel = new JLabel("ioR");
        JSlider ioRSlider = new JSlider(JSlider.HORIZONTAL, 0, 300, 100);
        ioRSlider.setMajorTickSpacing(10);

        roughnessSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                roughness = (double) roughnessSlider.getValue() / 100.0;
                // Update the roughness value in the material
                //updateRoughness(roughnessValue);
                scene = new Scene(roughness, lightIntensity, ioR);
                scene.setActiveCamera(camera);
            }
        });

        lightIntensitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                lightIntensity = (double) lightIntensitySlider.getValue() / 100.0;
                // Update the roughness value in the material
                //updateRoughness(roughnessValue);
                scene = new Scene(roughness, lightIntensity, ioR);
                scene.setActiveCamera(camera);
            }
        });

        ioRSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ioR = (double) ioRSlider.getValue() / 100.0;
                // Update the roughness value in the material
                //updateRoughness(roughnessValue);
                scene = new Scene(roughness, lightIntensity, ioR);
                scene.setActiveCamera(camera);
            }
        });

        sidePanelRight.add(roughnessLabel);
        sidePanelRight.add(roughnessSlider);

        sidePanelRight.add(lightIntensityLabel);
        sidePanelRight.add(lightIntensitySlider);

        sidePanelRight.add(ioRLabel);
        sidePanelRight.add(ioRSlider);

        //*OPTIONS LEFT*//
        JLabel translateXLabel = new JLabel("Translate X");
        JSlider translateXSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        translateXSlider.setMajorTickSpacing(10);

        JLabel translateYLabel = new JLabel("Translate Y");
        JSlider translateYSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        translateYSlider.setMajorTickSpacing(10);

        JLabel translateZLabel = new JLabel("Translate Z");
        JSlider translateZSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        translateZSlider.setMajorTickSpacing(10);

        JLabel rotateXLabel = new JLabel("Rotate X");
        JSlider rotateXSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        rotateXSlider.setMajorTickSpacing(10);

        JLabel rotateYLabel = new JLabel("Rotate Y");
        JSlider rotateYSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        rotateYSlider.setMajorTickSpacing(10);

        JLabel rotateZLabel = new JLabel("Rotate Z");
        JSlider rotateZSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        rotateZSlider.setMajorTickSpacing(10);

        JLabel scaleXLabel = new JLabel("Scale X");
        JSlider scaleXSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        scaleXSlider.setMajorTickSpacing(10);

        JLabel scaleYLabel = new JLabel("Scale Y");
        JSlider scaleYSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        scaleYSlider.setMajorTickSpacing(10);

        JLabel scaleZLabel = new JLabel("Scale Z");
        JSlider scaleZSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        scaleZSlider.setMajorTickSpacing(10);



        this.imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(sidePanelRight, BorderLayout.EAST);

    }

    public void updateImage() {
        int[] pixels = Renderer.renderImage(scene, resY, resX, 6);
        MemoryImageSource mis = new MemoryImageSource(resX, resY, new DirectColorModel(24, 0xff0000, 0xff00, 0xff), pixels, 0, resX);
        Image image = Toolkit.getDefaultToolkit().createImage(mis);
        imageLabel.setIcon(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.repaint();
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

}
