import Objects.Camera;
import Objects.Object3D;
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
import java.util.ArrayList;

public class Viewport extends JFrame {

    static int resX;
    static int resY;

    static Renderer renderer = new Renderer();
    private Timer timer;
    private JFrame frame;
    private JLabel imageLabel;
    private Scene currentScene;
    private Object3D selectedObject;
    private ArrayList<Scene> sceneList = new ArrayList<>();
    private JCheckBox reflectionCheckBox;
    private JCheckBox refractionCheckBox;
    private JCheckBox indirectLightingCheckBox;
    private JCheckBox superSamplingCheckBox;
    private double roughness = 0.5;
    private double lightIntensity = 1.0;
    private double ioR = 1.5;
    private int NUM_OF_THREADS = 12;

    static Camera camera = new Camera(
            new Vector3D(0, 0, 1),
            new Vector3D(0, 0, -1),
            new Vector3D(0, 1, 0),
            new Vector3D(1, 0, 0),
            resX,
            resY
    );

    public Viewport(int resX, int resY) {
        currentScene = new Scene(0.5,1.0,1.5);
        currentScene.setActiveCamera(camera);

        selectedObject = currentScene.getObjects().get(0);

        this.resX = resX;
        this.resY = resY;

        int[] pixels = renderer.renderImage(currentScene, resY, resX, NUM_OF_THREADS);

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
        sidePanelLeft.setLayout(new BoxLayout(sidePanelLeft, BoxLayout.Y_AXIS));
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
                currentScene = new Scene(roughness, lightIntensity, ioR);
                currentScene.setActiveCamera(camera);
            }
        });

        lightIntensitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                lightIntensity = (double) lightIntensitySlider.getValue() / 100.0;
                // Update the roughness value in the material
                //updateRoughness(roughnessValue);
                currentScene = new Scene(roughness, lightIntensity, ioR);
                currentScene.setActiveCamera(camera);
            }
        });

        ioRSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ioR = (double) ioRSlider.getValue() / 100.0;
                // Update the roughness value in the material
                //updateRoughness(roughnessValue);
                currentScene = new Scene(roughness, lightIntensity, ioR);
                currentScene.setActiveCamera(camera);
            }
        });

        sidePanelRight.add(roughnessLabel);
        sidePanelRight.add(roughnessSlider);

        sidePanelRight.add(lightIntensityLabel);
        sidePanelRight.add(lightIntensitySlider);

        sidePanelRight.add(ioRLabel);
        sidePanelRight.add(ioRSlider);

        //?? Render Options??// TODO make floating panel
        this.reflectionCheckBox = new JCheckBox("Reflections", true);
        this.refractionCheckBox = new JCheckBox("Refractions", true);
        this.indirectLightingCheckBox = new JCheckBox("Indirect Light", true);
        this.superSamplingCheckBox = new JCheckBox("Super Sampling", true);
        reflectionCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setReflections(reflectionCheckBox.isSelected());
            }
        });

        refractionCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setRefractions(refractionCheckBox.isSelected());
            }
        });

        indirectLightingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setIndirectLighting(indirectLightingCheckBox.isSelected());
            }
        });

        superSamplingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderer.setSuperSampling(superSamplingCheckBox.isSelected());
            }
        });

        sidePanelRight.add(reflectionCheckBox);
        sidePanelRight.add(refractionCheckBox);
        sidePanelRight.add(indirectLightingCheckBox);
        sidePanelRight.add(superSamplingCheckBox);
        //*OPTIONS LEFT*//

/*        sceneListModel = new DefaultListModel<>();
        sceneList = new JList<>(sceneListModel);
        sceneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sceneList.addListSelectionListener(new SceneSelectionListener());

        addObjectButton = new JButton("Add Object");
        addObjectButton.addActionListener(new AddObjectListener());
        removeObjectButton = new JButton("Remove Object");
        removeObjectButton.addActionListener(new RemoveObjectListener());*/

        String[] scenes = new String[sceneList.size()];
        for(int i = 0; i < sceneList.size(); i++){
            int id = sceneList.get(i).getId();
            scenes[i] = Integer.toString(id);
        }
        JComboBox<String> sceneBox = new JComboBox<>(scenes);
        sceneBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt((String) sceneBox.getSelectedItem());
                currentScene = sceneList.get(id);
                currentScene.setActiveCamera(camera);
            }
        });

        JComboBox<String> ObjectBox = new JComboBox<>();

        JComboBox<String> SkyboxBox = new JComboBox<>();

        //sidePanelLeft.add(sceneBox);

        JLabel translateXLabel = new JLabel("Translate X");
        JSlider translateXSlider = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        translateXSlider.setMajorTickSpacing(10);

        JButton sinTX = new JButton("SinTX");
        sinTX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer == null) {

                    timer = new Timer(16, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            double amplitude = 1.0;
                            double frequency = 1.0;

                            double time = System.currentTimeMillis() / 1000.0; //seconds
                            double sinValue = amplitude * Math.sin(2 * Math.PI * frequency * time);

                            currentScene.updateObject(selectedObject.getId(),sinValue, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, true);

                            updateImage();
                        }
                    });
                    timer.start();
                } else {
                    timer.stop();
                    timer = null;
                }
            }
        });

        JLabel translateYLabel = new JLabel("Translate Y");
        JSlider translateYSlider = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        translateYSlider.setMajorTickSpacing(10);

        JButton sinTY = new JButton("SinTY");

        JLabel translateZLabel = new JLabel("Translate Z");
        JSlider translateZSlider = new JSlider(JSlider.HORIZONTAL, -10, 10, 0);
        translateZSlider.setMajorTickSpacing(10);

        JButton sinTZ = new JButton("SinTZ");

        JLabel rotateXLabel = new JLabel("Rotate X");
        JSlider rotateXSlider = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        rotateXSlider.setMajorTickSpacing(10);

        JButton sinRX = new JButton("SinRX");

        JLabel rotateYLabel = new JLabel("Rotate Y");
        JSlider rotateYSlider = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        rotateYSlider.setMajorTickSpacing(10);

        JButton sinRY = new JButton("SinRY");

        JLabel rotateZLabel = new JLabel("Rotate Z");
        JSlider rotateZSlider = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        rotateZSlider.setMajorTickSpacing(10);

        JButton sinRZ = new JButton("SinRZ");

        JLabel scaleXLabel = new JLabel("Scale X");
        JSlider scaleXSlider = new JSlider(JSlider.HORIZONTAL, -5, 5, 1);
        scaleXSlider.setMajorTickSpacing(10);

        JButton sinSX = new JButton("SinSX");

        JLabel scaleYLabel = new JLabel("Scale Y");
        JSlider scaleYSlider = new JSlider(JSlider.HORIZONTAL, -5, 5, 1);
        scaleYSlider.setMajorTickSpacing(10);

        JButton sinSY = new JButton("SinSY");

        JLabel scaleZLabel = new JLabel("Scale Z");
        JSlider scaleZSlider = new JSlider(JSlider.HORIZONTAL, -5, 5, 1);
        scaleZSlider.setMajorTickSpacing(10);

        JButton sinSZ = new JButton("SinSZ");

        sidePanelLeft.add(translateXLabel);
        sidePanelLeft.add(translateXSlider);
        sidePanelLeft.add(sinTX);
        sidePanelLeft.add(translateYLabel);
        sidePanelLeft.add(translateYSlider);
        sidePanelLeft.add(sinTY);
        sidePanelLeft.add(translateZLabel);
        sidePanelLeft.add(translateZSlider);
        sidePanelLeft.add(sinTZ);

        sidePanelLeft.add(rotateXLabel);
        sidePanelLeft.add(rotateXSlider);
        sidePanelLeft.add(sinRX);
        sidePanelLeft.add(rotateYLabel);
        sidePanelLeft.add(rotateYSlider);
        sidePanelLeft.add(sinRY);
        sidePanelLeft.add(rotateZLabel);
        sidePanelLeft.add(rotateZSlider);
        sidePanelLeft.add(sinRZ);

        sidePanelLeft.add(scaleXLabel);
        sidePanelLeft.add(scaleXSlider);
        sidePanelLeft.add(sinSX);
        sidePanelLeft.add(scaleYLabel);
        sidePanelLeft.add(scaleYSlider);
        sidePanelLeft.add(sinSY);
        sidePanelLeft.add(scaleZLabel);
        sidePanelLeft.add(scaleZSlider);
        sidePanelLeft.add(sinSZ);

        this.imageLabel = new JLabel(new ImageIcon(image));
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(sidePanelRight, BorderLayout.EAST);
        frame.add(sidePanelLeft, BorderLayout.WEST);

    }

    public void updateImage() {
        int[] pixels = renderer.renderImage(currentScene, resY, resX, NUM_OF_THREADS);
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
