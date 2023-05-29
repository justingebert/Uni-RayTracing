package Materials;

import math.Vector3D;

import java.awt.*;

public class Material {
    Color color;
    double metalness;
    double roughness;
    double reflectivity;
    double transprancy;

    //add a constructor
    public Material(Color color, double metalness, double roughness, double reflectivity, double transprancy) {
        this.color = color;
        this.metalness = metalness;
        this.roughness = roughness;
        this.reflectivity = reflectivity;
        this.transprancy = transprancy;
    }

}
