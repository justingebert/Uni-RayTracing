package Lights;

import math.Vector3D;

import java.awt.*;

public class SpotLight {

    Vector3D position;
    public Color color;
    public double intensity;
    public double openAngle;

    public SpotLight(Vector3D position, Color color, double intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }

}
