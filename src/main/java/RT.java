import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RT {

    public static void main(String[] args) {
        int resX = 1024;
        int resY = 768;

        int[] pixels = new int[resX * resY];

        int sphereZ = 10;



        for (int y = 0; y < resY; ++y) {
            int yCentered = y - resY/2;
            for (int x = 0; x < resX; ++x) {
                int xCentered = x - resX/2;

                int sphere = (int) (Math.pow(xCentered,2) + Math.pow(yCentered,2) + Math.pow(sphereZ,2));
                int radius = 5000;

                if(sphere < radius){
                    pixels[y * resX + x] = 0xff0278;
                }
            }
        }


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
