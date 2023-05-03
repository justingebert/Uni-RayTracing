package Objects;

import Objects.Object3D;
import math.Ray;
import math.Util;
import math.Vector3D;

import java.awt.*;

public class Sphere extends Object3D {
    private double radius;

    public Sphere(Vector3D position,Color color, double radius) {
        super(position, color);
        this.radius = radius;
    }

    //replace x with math.Ray -> ausmultiplizieren -> quadratische Gleichung
    @Override
    public double calculateIntersection(Vector3D origin, Vector3D direction) {

        double a = Vector3D.dot(direction,direction);
        double b = 2 * Vector3D.dot(direction, origin.subtract(position));
        double c = (Vector3D.dot(origin.subtract(position), origin.subtract(position))) - (radius * radius);
        Vector3D result = Util.abcFormel(a,b,c);

        //TODO Anzahl der Lösugnen anders machen
        int solutions = (int) Math.round(result.getZ());
        if(solutions == 0){
            return 0;
        }

        else if (solutions == 1){
            return result.getX();
        }

        else if(solutions == 2) {
            if(result.getX() < 0){
                if(result.getY() < 0){
                    return Double.MAX_VALUE;
                }
                //kugel um Kamera
                else {
                    return result.getY();
                }
            } else {
                if(result.getY() < 0){
                    return result.getX();
                }
                else {
                    return Math.min(result.getX(), result.getY());
                }
            }
        }else{
            return Double.MAX_VALUE;
        }
    }

    @Override
    public Vector3D calculateIntersection(Ray ray) {
        //scalar von rayOrigin zu Kugelmitte
        double t = Vector3D.dot(position.subtract(ray.getOrigin()), ray.getDirection());
        //vektor von rayOrigin zu Schnittpunkt
        Vector3D p =  ray.getOrigin().add(ray.getDirection().scale(t));

        //Abstand
        double y = position.subtract(p).length();
        if (y < radius) {
            double x = (float) Math.sqrt(radius*radius - y*y);
            double t1 = t-x;
            if (t1 > 0) return ray.getOrigin().add(ray.getDirection().scale(t1));
            else return null;
        } else {
            return null;
        }
    }

   /* @Override
    public Vector3D calculateIntersection(Ray ray) {
        //scalar von rayOrigin zu Kugelmitte
        Vector3D oc = ray.getOrigin().subtract(position);
        double a = Vector3D.dot(ray.getDirection(), ray.getDirection());
        double b = 2.0 * Vector3D.dot(oc, ray.getDirection());
        double c = Vector3D.dot(oc, oc) - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if(discriminant < 0) return null;
        double wurzel = Math.sqrt(discriminant);

    }*/



    @Override
    public Vector3D getNormalAt(Vector3D point) {
        return point.subtract(position).normalize();
    }
}

