package Objects;

import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;

public class Cylinder extends Object3D{
    public Cylinder(Vector3D position, Color color) {
        super(position, color);
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
