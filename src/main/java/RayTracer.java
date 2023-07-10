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

    public static void main(String[] args) {
        int frameCount = 0;
        long startTime = System.nanoTime();

        Viewport viewport = new Viewport(resX, resY);

        viewport.show();

        while (true) {
            viewport.updateImage();

            frameCount++;

            // Calculate elapsed time
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - startTime;
            double elapsedSeconds = elapsedTime / 1_000_000_000.0;

            // Check if 1 second has elapsed
            if (elapsedSeconds >= 1.0) {
                // Calculate frames per second
                double fps = frameCount / elapsedSeconds;

                // Print FPS to console
                System.out.println("FPS: " + fps);

                // Reset frame count and start time
                frameCount = 0;
                startTime = currentTime;
            }
        }
    }
}
