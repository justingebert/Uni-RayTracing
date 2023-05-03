package Lights;

import Objects.Object3D;
import math.Vector3D;

import java.awt.*;


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
        double x = Math.max(Math.min(light.getX(),255), 0);
        double y = Math.max(Math.min(light.getY(),255), 0);
        double z = Math.max(Math.min(light.getZ(),255), 0);
        Vector3D ret = new Vector3D(x, y, z);
        return ret;
    }

}
