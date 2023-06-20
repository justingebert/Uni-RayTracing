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

        int[] pixels = Renderer.renderImage(scene, resY, resX);

        SwingUtilities.invokeLater(() -> new Viewport(scene, pixels, resX, resY));
    }
}
