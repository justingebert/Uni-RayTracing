package Objects;

import Materials.Material;
import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;

public class Cylinder extends Object3D{
    public Cylinder(Vector3D position, Material material) {
        super(position, material);
    }
    @Override
    public Vector3D calculateIntersection(Ray ray) {
        return null;
    }

    @Override
    public void applyTransformation(Matrix4x4 transformationMatrix) {
    }
    @Override
    public Vector3D getNormalAt(Vector3D point) {
        return null;
    }
}
