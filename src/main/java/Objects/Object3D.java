package Objects;

import Materials.Material;
import math.*;
import java.awt.*;
import java.util.List;

public abstract class Object3D {
    protected int id;
    protected Vector3D position;
    public Material material;
    private static List<Object3D> objects = new java.util.ArrayList<>();

    public Object3D(Vector3D position, Material material){
        this.position = position;
        this.material = material;
        id = objects.size();
        objects.add(this);
    }

    public Vector3D getPosition() {
        return position;
    }
    public int getId() {return id;}
    public Vector3D getColVec() {return new Vector3D(this.getColor().getRed(), this.getColor().getGreen(), this.getColor().getBlue());}
    public abstract Vector3D calculateIntersection(Ray ray);
    public abstract void applyTransformation(Matrix4x4 transformationMatrix);
    //public abstract Vector3D calculateIntersection2(Ray ray);
    public abstract Vector3D getNormalAt(Vector3D point);
    public double getReflectivity() {
        return material.getReflectivity();
    }
    public double getTransparency() {
        return material.getTransparency();
    }
    public double getMetalness() {
        return material.getMetalness();
    }
    public double getRoughness() {
        return material.getRoughness();
    }
    public Color getColor() {
        return material.getColor();
    }
    public Material getMaterial() {
        return material;
    }
}
