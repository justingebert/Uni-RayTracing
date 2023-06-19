import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Viewport extends JFrame {

    private JFrame frame;
    private JPanel sidePanel;
    private JDialog settingsDialog;


    public Viewport(JFrame container, JDialog settingsDialog, JPanel sidePanel){
        this.frame = container;
        this.sidePanel = sidePanel;
        this.settingsDialog = settingsDialog;

        frame.setLayout(new BorderLayout());

        //JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        //sidePanel.setPreferredSize(new Dimension(200, resY)); // Adjust the width as needed

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JSlider slider = new JSlider();
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});


        sidePanel.add(button1);
        sidePanel.add(button2);
        sidePanel.add(slider);
        sidePanel.add(comboBox);

        frame.add(sidePanel, BorderLayout.EAST);

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

}
