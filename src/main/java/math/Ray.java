package math;

import math.Vector3D;

public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    public  Ray(Vector3D origin, Vector3D direction) {
        this.origin = origin;

        if (direction.length() != 1) {
            direction = direction.normalize();
        }
        this.direction = direction;
    }

    public Line asLine(float length) {
        return new Line(origin, origin.add(direction.scale(length)));
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }
}