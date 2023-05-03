package Lights;

import Objects.Object3D;
import math.Vector3D;

import java.awt.*;

import static math.Util.clamp;


//TODO make abtract to implemnt area an spot lights
public class Light {

    Vector3D position;
    public Color color;
    public double intensity;

    public Light(Vector3D position, Color color, double intensity){
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }
    Vector3D getPosition(){
        return position;
    };

    public Vector3D diffLight(Vector3D point, Object3D object){
        Vector3D normal = object.getNormalAt(point);
        Vector3D lightDir = position.subtract(point).normalize();

        //TODO distance quadratisch verrechnen?
        double distance = lightDir.length();

        double angle = Vector3D.dot(lightDir.normalize(), normal);

        Vector3D light = object.getColVec();
        light = light.scale(angle * (intensity / (lightDir.length() * lightDir.length())));
        double x = clamp(light.getX(), 0, 255);
        double y = clamp(light.getY(), 0, 255);
        double z = clamp(light.getZ(), 0, 255);

        return new Vector3D(x, y, z);
    }

}
