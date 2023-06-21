import Lights.PointLight;
import Materials.Material;
import Objects.*;
import math.Intersect;
import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;
import java.util.ArrayList;

public class Scene {
    //continue Later
    private static Scene scene;
    ArrayList<Object3D> objects = new ArrayList<Object3D>();
    ArrayList<PointLight> lights = new ArrayList<PointLight>();
    Camera activeCamera;


    public Camera getActiveCamera() {
        return activeCamera;
    }
    public void setActiveCamera(Camera activeCamera) {
        this.activeCamera = activeCamera;
    }


    public Scene(double roughness){

        System.out.println(roughness);
        Material greenMat = new Material(Color.GREEN,0,roughness,0,1);
        Material blueMat = new Material(Color.WHITE,0,0.25,0.75,1.8);
        Material redMat = new Material(Color.RED,0,0.1,0,1);
        Material blackMat = new Material(Color.BLACK,0,0.1,0,1);
        Material cyanMat = new Material(Color.CYAN,0,0.2,0,1);


       Sphere sphere1 = new Sphere(new Vector3D(-1, 0, -10), greenMat, 1.5);
       Sphere sphere2 = new Sphere(new Vector3D(2, -0, -12), redMat, 1);
       Sphere sphere3 = new Sphere(new Vector3D(2, -1, -8), blueMat, 1);
       Sphere sphere4 = new Sphere(new Vector3D(-1, 0, -3), blueMat, .5);

       Quadric q1 = new Quadric( 1, 1, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), redMat);
       Matrix4x4 quadricTransformation = new Matrix4x4();

       //TODO multiply matrix
        quadricTransformation.setQuadricRotationZ(40);
        q1.applyTransformation(quadricTransformation);
        Matrix4x4 quadricTransformation2 = new Matrix4x4();
        quadricTransformation2.setQuadricTranslation(-3,0,-6);
        q1.applyTransformation(quadricTransformation);


        objects.add(sphere1);
        objects.add(sphere2);
        objects.add(sphere3);
        objects.add(sphere4);

        //objects.add(q1);
        lights.add(new PointLight(new Vector3D(-10, -9, 10), Color.WHITE, .4));
        //lights.add(new PointLight(new Vector3D(20, 5, 0), Color.WHITE, 10));
        lights.add(new PointLight(new Vector3D(50, 10, 3), Color.WHITE, .3));

        lights.add(new PointLight(new Vector3D(-20, 50, 3), Color.WHITE, .2));
    }

    public void addObject(Object3D object) {
        this.objects.add(object);
    }

    public void addLight(PointLight light) {
        this.lights.add(light);
    }
    public ArrayList<PointLight> getLights() {
        return this.lights;
    }

    public static Scene getScene(){
        if(scene == null){
            scene = new Scene(0.5);
        }
        return scene;
    }

    public Intersect RayData(Ray ray){
        Intersect nearestIntersection = null;
        for(Object3D object : this.objects) {
            Vector3D intersection = object.calculateIntersection(ray);
            //if intersection is closer than the last one -> new nearestIntersection
            if (intersection != null && (nearestIntersection == null || Vector3D.distance(nearestIntersection.getPosition(), ray.getOrigin()) > Vector3D.distance(intersection, ray.getOrigin()) && Vector3D.distance(intersection, ray.getOrigin()) < 0 )) {
                nearestIntersection = new Intersect(ray, object, intersection);
            }
        }
        return nearestIntersection;
    }
}
