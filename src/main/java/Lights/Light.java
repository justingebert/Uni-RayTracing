package Lights;

import math.Vector3D;

import java.awt.*;

public abstract class Light {

    Vector3D position;
    Color color;
    double intensity;

    public Light(Vector3D position, Color color, double intensity){
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }
    public Vector3D getPosition(){
            return position;
        };

    public Color getColor(){
            return color;
        };

    public double getIntensity(){
            return intensity;
        };


}
