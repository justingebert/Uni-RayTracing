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
        Viewport viewport = new Viewport(resX, resY);

        viewport.show();

        while (true) {
            viewport.updateImage();
        }
    }
}
