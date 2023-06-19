package Materials;

import math.Vector3D;

import java.awt.*;

public class Material {
    public Color color;
    public double metalness;
    public double roughness;
    public double reflectivity;
    public double transparency;

    public double ioR;
    
    //add a constructor
    public Material(Color color, double metalness, double roughness, double transparency, double ioR) {
        this.color = color;
        this.metalness = metalness;
        this.roughness = roughness;
        this.reflectivity = 1-roughness;
        this.transparency = transparency;
        this.ioR = ioR;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getMetalness() {
        return metalness;
    }

    public void setMetalness(double metalness) {
        this.metalness = metalness;
    }

    public double getRoughness() {
        return roughness;
    }

    public void setRoughness(double roughness) {
        this.roughness = roughness;
    }

    public double getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(double reflectivity) {
        this.reflectivity = reflectivity;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getIoR() {
        return ioR;
    }

    public Vector3D getAlbedo(){
        return new Vector3D(color.getRed(), color.getGreen(), color.getBlue()).scale(1.0/255.0);
    }
    
}
