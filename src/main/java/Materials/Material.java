package Materials;

import math.Vector3D;

public class Material {
    Vector3D color;
    double metalness;
    double roughness;
    double reflectivity;
    double transprancy;

    //add a constructor
    public Material(Vector3D color, double metalness, double roughness, double reflectivity, double transprancy) {
        this.color = color;
        this.metalness = metalness;
        this.roughness = roughness;
        this.reflectivity = reflectivity;
        this.transprancy = transprancy;
    }

}
