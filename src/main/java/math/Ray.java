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

    public Ray reflect(Vector3D hitPos, Vector3D normal) {
        Vector3D reflectedDirection = direction.subtract(normal.scale(2 * direction.dot(normal)));
        Vector3D reflectedRayOrigin = hitPos.add(reflectedDirection.scale(0.001F)); // Add a little to avoid hitting the same object again
        return new Ray(reflectedRayOrigin, reflectedDirection);
    }

    public Ray refract(Vector3D normal, double ior) {
        double cosi = Math.max(-1, Math.min(1, Vector3D.dot(direction, normal)));
        double etai = 1, etat = ior;
        Vector3D n = normal;
        if (cosi < 0) {
            cosi = -cosi;
        } else {
            double temp = etai;
            etai = etat;
            etat = temp;
            n = n.scale(-1);
        }
        double eta = etai / etat;
        double k = 1 - eta * eta * (1 - cosi * cosi);
        Vector3D refractedDirection = k < 0 ? new Vector3D(0, 0, 0) : direction.scale(eta).add(n.scale(eta * cosi - Math.sqrt(k)));
        return new Ray(origin, refractedDirection);
    }

    public Ray shadowRay(Vector3D hitPos, Vector3D lightPos) {
        Vector3D shadowRayDirection = lightPos.subtract(hitPos);
        Vector3D shadowRayOrigin = hitPos.add(shadowRayDirection.scale(0.001F)); // Add a little to avoid hitting the same object again
        return new Ray(shadowRayOrigin, shadowRayDirection);
    }
}