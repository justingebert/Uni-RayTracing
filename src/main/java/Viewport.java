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
    private JLabel imageLabel;
    private JButton button1;
    private JButton button2;
    private JSlider slider;
    private JComboBox<String> comboBox;
    private Scene scene;
    private int[] pixels;



    public Viewport(Scene scene, int[] pixels, int resX, int resY) {
        this.scene = scene;
        this.pixels = pixels;

        setTitle("Ray Tracer Viewport");
        setLayout(new BorderLayout());

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, resY));

        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        slider = new JSlider();
        comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});

        sidePanel.add(button1);
        sidePanel.add(button2);
        sidePanel.add(slider);
        sidePanel.add(comboBox);

        add(sidePanel, BorderLayout.EAST);

        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        updateImage();

        // Add listeners to the components
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button1 click event
                // Update scene or materials
                updateImage();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button2 click event
                // Update scene or materials
                updateImage();
            }
        });

        slider.addChangeListener(e -> {
            // Handle slider value change event
            // Update scene or materials
            updateImage();
        });

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle comboBox selection change event
                // Update scene or materials
                updateImage();
            }
        });

        setVisible(true);
    }

    private void updateImage(){

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
