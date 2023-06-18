import Lights.PointLight;
import Materials.Material;
import Objects.*;
import math.Matrix4x4;
import math.Vector3D;

import java.awt.*;
import java.util.ArrayList;

public class Scene {
    //continue Later
    private static Scene scene;
    ArrayList<Object3D> objects = new ArrayList<Object3D>();
    ArrayList<PointLight> lights = new ArrayList<PointLight>();


    public Scene(){

        Material greenMat = new Material(Color.GREEN,0,0.9,1,0);
        Material blueMat = new Material(Color.BLUE,0,0.1,1,0);
        Material redMat = new Material(Color.RED,0,0.05,0,0);
        Material blackMat = new Material(Color.BLACK,0,0.1,0,0);
        Material cyanMat = new Material(Color.CYAN,0,0.2,0,0);


       Sphere sphere1 = new Sphere(new Vector3D(0, 0, -10), greenMat, 2);

       Quadric q1 = new Quadric( 1, 0, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), redMat);
       Matrix4x4 quadricTransformation = new Matrix4x4();

       //TODO multiply matrix
        quadricTransformation.setQuadricRotationZ(40);
        q1.applyTransformation(quadricTransformation);
        Matrix4x4 quadricTransformation2 = new Matrix4x4();
        quadricTransformation2.setQuadricTranslation(-3,0,-6);
        q1.applyTransformation(quadricTransformation);


        objects.add(sphere1);
        objects.add(q1);
        lights.add(new PointLight(new Vector3D(5, -3, 10), Color.WHITE, .3));

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
