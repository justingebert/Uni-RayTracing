package Objects;

import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;

public class Plane extends Object3D{

    private Vector3D normal;

    public Plane(Vector3D position, Color color, Vector3D normal) {
        super(position, color);
        this.normal = normal.normalize();
    }
    @Override
    public Vector3D calculateIntersection(Ray ray) {
        double d = Vector3D.dot(this.normal, ray.getDirection());
        if(d == 0){
            return null;
        }
        double t = Vector3D.dot(this.normal, position.subtract(ray.getOrigin())) / d;
        if(t < 0){
            return null;
        }
        return ray.getOrigin().add(ray.getDirection().scale(t));

    }

    @Override
    public void applyTransformation(Matrix4x4 transformationMatrix) {
        System.out.println("no transformation for plane yet");
    }

//    @Override
//    public Vector3D calculateIntersection2(Ray ray) {
//        return null;
//    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        return this.normal;
    }
}
