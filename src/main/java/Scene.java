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
    private int id;
    private static Scene scene;
    private static ArrayList<Scene> scenes = new ArrayList<Scene>();
    ArrayList<Object3D> objects = new ArrayList<Object3D>();
    ArrayList<PointLight> lights = new ArrayList<PointLight>();
    Camera activeCamera;
    SkyBox skyBox;

    public Camera getActiveCamera() {
        return activeCamera;
    }
    public void setActiveCamera(Camera activeCamera) {
        this.activeCamera = activeCamera;
    }
    public SkyBox getSkyBox(){
        return skyBox;
    }

    public Scene(double roughness, double lightIntensity, double ioR){

        //System.out.println(roughness);
        Material greenMat = new Material(Color.decode("#c297e5"),0,roughness,0,1);
        Material transparentMat = new Material(Color.WHITE,0,0.33,0.95,ioR);
        Material floorMat = new Material(Color.decode("#acc4e9"),0,0.9,0,1);
        Material blackMat = new Material(Color.BLACK,0,0.1,0,1);
        Material cyanMat = new Material(Color.CYAN,0,0.1,0,1);
        Material purpleMat = new Material(Color.decode("#6e62ce"),0,0.9,0,1);

       Sphere sphere1 = new Sphere(new Vector3D(0, 3, -25), greenMat, 6); //center sphere
       Sphere sphere2 = new Sphere(new Vector3D(1.5, -0, -14), cyanMat, 1);
       //Sphere sphere3 = new Sphere(new Vector3D(2, -1, -8), transparentMat, 1);
       Sphere sphere4 = new Sphere(new Vector3D(3, 0, -10), transparentMat, 2); //transparent
       Sphere sphere5 = new Sphere(new Vector3D(0, 3010, -5), floorMat, 3000 );  //floor
       Sphere sphere6 = new Sphere(new Vector3D(2, 7, -12), cyanMat, 2); //spehre infront
       Sphere sphere7 = new Sphere(new Vector3D(-3, 8, -16), purpleMat, 1.7); //spehre infront left

       Quadric q1 = new Quadric( 1, 1, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), floorMat);
       Matrix4x4 quadricTransformation = new Matrix4x4();

       //TODO multiply matrix
        quadricTransformation.setQuadricRotationZ(40);
        q1.applyTransformation(quadricTransformation);
        Matrix4x4 quadricTransformation2 = new Matrix4x4();
        quadricTransformation2.setQuadricTranslation(-3,0,-6);
        q1.applyTransformation(quadricTransformation);

        objects.add(sphere1);
        objects.add(sphere2);
        //objects.add(sphere3);
        objects.add(sphere4);
        //objects.add(sphere5);
        objects.add(sphere6);
        objects.add(sphere7);

        //objects.add(q1);
        lights.add(new PointLight(new Vector3D(-10, -9, 10), Color.WHITE, .4*lightIntensity,1));
        //lights.add(new PointLight(new Vector3D(20, 5, 0), Color.WHITE, 10));
        lights.add(new PointLight(new Vector3D(50, 10, 3), Color.WHITE, .3*lightIntensity,1));

        lights.add(new PointLight(new Vector3D(-20, 50, 3), Color.WHITE, .2*lightIntensity,1));

        skyBox = new SkyBox("C:\\Users\\justi\\Documents\\PROJECTS\\Uni\\RayTracing\\src\\tex\\skynighte.png");

        this.id = scenes.size();
        scenes.add(this);
    }


    public int getId() {
        return id;
    }

    public static Scene getSceneById(int id){
        return scenes.get(id);
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
    public ArrayList<Object3D> getObjects() {return this.objects;}
    public Intersect RayData(Ray ray){
        Intersect nearestIntersection = null;
        for(Object3D object : this.objects) {
            Vector3D intersection = object.calculateIntersection(ray);
            //if intersection is closer than the last one -> new nearestIntersection
            if (intersection != null && (nearestIntersection == null || Vector3D.distance(nearestIntersection.getPosition(), ray.getOrigin()) > Vector3D.distance(intersection, ray.getOrigin()))) {
                nearestIntersection = new Intersect(ray, object, intersection);
            }
        }
        return nearestIntersection;
    }

    public void updateScene(){

    }
    public void updateObject(int id, double tx, double ty, double tz, double rx, double ry, double rz, double sx, double sy, double sz, boolean Animate){
        objects.stream().filter(object -> object.getId() == id).forEach(object -> {
            Matrix4x4 transformation = new Matrix4x4();
            transformation.setTranslation(tx,ty,tz);
            transformation.setRotation(rx,ry,rz);
            transformation.setScale(sx,sy,sz);
            object.applyTransformation(transformation);
        });
    }
    public void updateMaterial(){

    }
}
