package Objects;

import Objects.Object3D;
import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;

public class CSG extends Object3D{

    public enum CSGoperation {
        UNION,
        INTERSECT,
        DIFFERENCE
    }

    //private Operation operation;
    private Object3D left;
    private Object3D right;
    private CSGoperation operation;

    public CSG(Vector3D position, Color color, CSGoperation operation, Object3D left, Object3D right) {
        super(position, color);
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public Vector3D calculateIntersection(Ray ray) {
        Vector3D leftIntersection = left.calculateIntersection(ray);
        Vector3D rightIntersection = right.calculateIntersection(ray);

        switch (operation) {
            case UNION:
                return union(leftIntersection, rightIntersection, ray);
            case INTERSECT:
                return intersect(leftIntersection, rightIntersection, ray);
            case DIFFERENCE:
                return difference(leftIntersection, rightIntersection, ray);
            default:
                return null;
        }
    }

    private Vector3D union(Vector3D left, Vector3D right, Ray ray) {
        // Return the closest intersection
        if (left != null && right != null) {
            return Vector3D.distance(ray.getOrigin(),left) < Vector3D.distance(ray.getOrigin(),right) ? left : right;
        } else if (left != null) {
            return left;
        } else {
            return right;
        }
    }

    private Vector3D intersect(Vector3D left, Vector3D right, Ray ray) {
        // Return the intersection farthest from the ray origin
        if (left != null && right != null) {
            return Vector3D.distance(ray.getOrigin(),left) > Vector3D.distance(ray.getOrigin(),right) ? left : right;
        } else {
            return null;
        }
    }

    private Vector3D difference(Vector3D left, Vector3D right, Ray ray) {
        // Return the left intersection if it exists and the right intersection doesn't
        if (left != null && right != null) {
            return left;
        } else {
            return null;
        }
    }

    @Override
    public void applyTransformation(Matrix4x4 transformationMatrix) {

    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        double epsilon = 0.0001; // Small value for numerical stability

        // Calculate the normals at the specified point for the left and right child objects
        Vector3D leftNormal = left.getNormalAt(point);
        Vector3D rightNormal = right.getNormalAt(point);

        switch (operation) {
            case UNION:
                if (leftNormal == null && rightNormal == null) {
                    return null;
                } else if (leftNormal == null) {
                    return rightNormal;
                } else if (rightNormal == null) {
                    return leftNormal;
                } else {
                    // Return the average of the two normals
                    return leftNormal.add(rightNormal).normalize();
                }
            case INTERSECT:
                if (leftNormal != null && rightNormal != null) {
                    // Return the average of the two normals
                    return leftNormal.add(rightNormal).normalize();
                } else {
                    return null;
                }
            case DIFFERENCE:
                if (leftNormal != null && rightNormal == null) {
                    return leftNormal;
                } else {
                    return null;
                }
            default:
                return null;
        }
    }


}
