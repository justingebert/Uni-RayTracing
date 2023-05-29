import Lights.PointLight;
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

        objects.add(new Sphere(new Vector3D(0, 0, -10), Color.CYAN, 1));
        objects.add(new Sphere(new Vector3D(5, 0, -10), Color.GREEN, 1));
        objects.add(new Sphere(new Vector3D(-7, -3, -10), Color.RED, 1.5));
        objects.add(new Plane(new Vector3D(0, 1, -10), Color.BLUE, new Vector3D(0, 1, 0)));
        //objects.add(new Quadric( 1, 1, 1, 0, 1, 1, -1, 0, 0,0, new Vector3D(0, 0, -10), Color.CYAN));
        lights.add(new PointLight(new Vector3D(3, 3, 3), Color.WHITE, 0.5));


       /* Quadric quadric = new Quadric(1, 0, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), Color.CYAN);
        Matrix4x4 quadricTransformation = new Matrix4x4();
        quadricTransformation.setQuadricTranslation(-3, 0, -3);
        //quadricTransformation.setQuadricScale(1., 1., 1.);
        quadric.applyTransformation(quadricTransformation);
        objects.add(quadric);*/



        Sphere sphere = new Sphere(new Vector3D(0, 0, -10), Color.WHITE, 1);
        Matrix4x4 sphereTransformation = new Matrix4x4();
        //sphereTransformation.setTranslation(1, 1, 1);
        sphereTransformation.setScale(.5,5, 5);
        sphere.applyTransformation(sphereTransformation);
        objects.add(sphere);

        Sphere sphere2 = new Sphere(new Vector3D(1, 0, -8), Color.WHITE, 2);
        Sphere sphere3 = new Sphere(new Vector3D(0, 0, -8), Color.WHITE, 2);
        CSG csg = new CSG(new Vector3D(0, 0, 0), Color.GREEN, CSG.CSGoperation.DIFFERENCE,sphere2, sphere3);
        objects.add(csg);

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
