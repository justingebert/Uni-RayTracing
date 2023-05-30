package Objects;

import Materials.Material;
import math.*;
import java.awt.*;

public abstract class Object3D {

    protected Vector3D position;
    protected Color color;
    public Material material;

    public Object3D(Vector3D position, Material material){
        this.position = position;
        this.material = material;
    }

    public Vector3D getPosition() {
        return position;
    }

    public int getColor() {
        return color.getRGB();
    }

    public Vector3D getColVec() {
        return new Vector3D(color.getRed(), color.getGreen(), color.getBlue());
    }

    //TODO change to implement ray class
    //public abstract double calculateIntersection(Vector3D origin, Vector3D direction);

    public abstract Vector3D calculateIntersection(Ray ray);

    public abstract void applyTransformation(Matrix4x4 transformationMatrix);

    //public abstract Vector3D calculateIntersection2(Ray ray);
    public abstract Vector3D getNormalAt(Vector3D point);

}
