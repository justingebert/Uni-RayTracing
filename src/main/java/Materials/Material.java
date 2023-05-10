package Materials;

import math.Vector3D;

public class Material {
    Vector3D color;
    boolean metal;
    double roughness;
    double reflectivity;
    double transprancy;

    //add a constructor
    public Material(Vector3D color, boolean metal, double roughness, double reflectivity, double transprancy) {
        this.color = color;
        this.metal = metal;
        this.roughness = roughness;
        this.reflectivity = reflectivity;
        this.transprancy = transprancy;
    }

}
