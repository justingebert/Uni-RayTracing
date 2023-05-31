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
        Material redMat = new Material(Color.RED,0,0.3,0,0);
        Material blackMat = new Material(Color.BLACK,0,0.1,0,0);
        Material cyanMat = new Material(Color.CYAN,0,0.2,0,0);


       Sphere sphere1 = new Sphere(new Vector3D(0, 0, -10), greenMat, 2);
       Sphere sphere2 = new Sphere(new Vector3D(2, 0, -10), greenMat, 2);
       Sphere sphere4 = new Sphere(new Vector3D(6, 0, -10), greenMat, 2);
       Sphere sphere5 = new Sphere(new Vector3D(8, 0, -10), greenMat, 2);

       Sphere sphere6 = new Sphere(new Vector3D(-4, 0, -10), greenMat, 2);
       Sphere sphere7 = new Sphere(new Vector3D(-6, 0, -10), greenMat, 2);

       CSG csg1 = new CSG(new Vector3D(0, 0, -10), blueMat, CSG.CSGoperation.UNION, sphere1, sphere2);
       CSG csg2 = new CSG(new Vector3D(0, 0, -10), greenMat, CSG.CSGoperation.INTERSECT, sphere4, sphere5);
       CSG csg4 = new CSG(new Vector3D(0, 0, -10), redMat, CSG.CSGoperation.DIFFERENCE, sphere6, sphere7);
       Sphere sphere3 = new Sphere(new Vector3D(-1, 0, -9), greenMat, 1);
       CSG csg3 = new CSG(new Vector3D(0, 0, -10), greenMat, CSG.CSGoperation.DIFFERENCE, csg1, sphere3);


       Sphere sphere8 = new Sphere(new Vector3D(0, 5, -10), greenMat, 2);
       Sphere sphere9 = new Sphere(new Vector3D(2, 5, -10), greenMat, 2);
       Sphere sphere10 = new Sphere(new Vector3D(4, 5, -10), greenMat, 2);

       CSG csg5 = new CSG(new Vector3D(0, 0, -10), greenMat, CSG.CSGoperation.DIFFERENCE, sphere9, sphere8);
       CSG csg6 = new CSG(new Vector3D(0, 0, -10), greenMat, CSG.CSGoperation.DIFFERENCE, csg5, sphere10);


       Matrix4x4 transformation = new Matrix4x4();
       transformation.setScale(.5,.5,.5);
       csg6.applyTransformation(transformation);



       Quadric q1 = new Quadric( 1, 1, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), redMat);
       //Matrix4x4 quadricTransformation = new Matrix4x4();

        //quadricTransformation.setQuadricRotationZ(40);
        //quadricTransformation.setQuadricScale(1,1,1);
        //quadricTransformation.print();
        //q1.applyTransformation(quadricTransformation);
        //Matrix4x4 quadricTransformation2 = new Matrix4x4();
        //quadricTransformation2.setQuadricTranslation(0,1,-5);
        //q1.applyTransformation(quadricTransformation2);
        //objects.add(sphere1);
        //objects.add(sphere2);
        //objects.add(sphere3);
        //objects.add(sphere4);
        //objects.add(sphere5);
        //objects.add(sphere6);

        //objects.add(new Sphere(new Vector3D(-10, 0, -10), greenMat, 2));
        //objects.add(new Sphere(new Vector3D(-8, 0, -10), blueMat, 2));
        objects.add(csg1);
        objects.add(csg2);
        objects.add(csg4);
        objects.add(csg6);
        //objects.add(new Sphere(new Vector3D(5, 0, -10), blueMat, 1));
        //objects.add(new Sphere(new Vector3D(-7, -3, -10), redMat, 1.5));
        //objects.add(new Plane(new Vector3D(0, 1, -10), cyanMat, new Vector3D(0, 1, 0)));
        objects.add(q1);
        //objects.add(new Quadric( 1, 1, 1, 0, 1, 1, -1, 0, 0,0, new Vector3D(0, 0, -10), Color.CYAN));
        lights.add(new PointLight(new Vector3D(5, -3, 10), Color.WHITE, .5));
        //lights.add(new PointLight(new Vector3D(-3, 0, 2), Color.WHITE, 0.5));


     /*   Quadric quadric = new Quadric(1, 0, 1, 0, 0, 0, 0, 0, 0,-1, new Vector3D(0, 0, -10), Color.CYAN);
        Matrix4x4 quadricTransformation = new Matrix4x4();
        quadricTransformation.setQuadricTranslation(-3, 0, -3);
        //quadricTransformation.setQuadricScale(1., 1., 1.);
        quadric.applyTransformation(quadricTransformation);
        objects.add(quadric);*/



        /*Sphere sphere = new Sphere(new Vector3D(0, 0, -10), blackMat, 1);
        Matrix4x4 sphereTransformation = new Matrix4x4();
        //sphereTransformation.setTranslation(1, 1, 1);
        sphereTransformation.setScale(.5,5, 5);
        sphere.applyTransformation(sphereTransformation);
        objects.add(sphere);*/

        /*Sphere sphere2 = new Sphere(new Vector3D(1, 0, -3), blackMat, 2);
        Sphere sphere3 = new Sphere(new Vector3D(0, 0, -2), blackMat, 2);
        CSG csg = new CSG(new Vector3D(0, 0, 0), greenMat, CSG.CSGoperation.DIFFERENCE,sphere2, sphere3);
        objects.add(csg);*/

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
