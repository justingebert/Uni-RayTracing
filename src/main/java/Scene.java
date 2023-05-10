import Lights.PointLight;
import Objects.Object3D;
import Objects.Sphere;
import math.Vector3D;

import java.awt.*;
import java.util.ArrayList;

public class Scene {
    //continue Later
    private static Scene scene;
    ArrayList<Object3D> objects = new ArrayList<Object3D>();
    ArrayList<PointLight> lights = new ArrayList<PointLight>();


    public Scene(){
        objects.add(new Sphere(new Vector3D(0, 0, -10), Color.CYAN, 1));
        objects.add(new Sphere(new Vector3D(5, 0, -10), Color.GREEN, 1));
        objects.add(new Sphere(new Vector3D(-7, -3, -10), Color.RED, 1.5));
        lights.add(new PointLight(new Vector3D(3, 3, -2), Color.WHITE, 0.5));
    }

    public void addObject(Object3D object) {
        this.objects.add(object);
    }

    public static Scene getScene(){
        if(scene == null){
            scene = new Scene();
        }
        return scene;
    }
}
