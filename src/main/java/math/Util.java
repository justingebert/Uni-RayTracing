package math;

import math.Vector3D;

import java.awt.*;

public class Util {
    //returns Nullstellen und in z Anzahl der Lösungen
    public static Vector3D abcFormel(double a, double b, double c){

        if(a == 0){
            return new Vector3D(0,0,0);
        }
        double diskriminante = b*b - 4*a*c;
        if(diskriminante < 0){
            return new Vector3D(0,0,0);
        } else if (diskriminante == 0) {
            return new Vector3D(-b/(2*a),0,1);
        }
        else{
            double x1 = (-b + Math.sqrt(diskriminante))/(2*a);
            double x2 = (-b - Math.sqrt(diskriminante))/(2*a);
            return new Vector3D(x1,x2,2);
        }
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Vector3D colorToVector3D(Color c) {
        return new Vector3D(c.getRed(), c.getGreen(), c.getBlue());
    }
}
