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

    public Ray refract(Vector3D hitPos, Vector3D normal, double ioR) {
        double i, i1, i2;
        if (direction.dot(normal) > 0) {
            i1 = 1;
            i2 = ioR;
            i = 1.0 / ioR;

        } else {
            i = ioR;
            normal = normal.scale(-1);
        }

        //TODO problem with total internal reflection (i*i*(1-a*a) < 0)
        double a = direction.scale(-1).dot(normal);
        double b1 = 1-(i*i*(1-a*a));
        if(b1 < 0) {
            return reflect(hitPos, normal);
        }
        double b = Math.sqrt(b1);

        Vector3D refractionVector = direction.scale(i).add(normal.scale((a*i)-b));
        Vector3D refractionRayOrigin = hitPos.add(refractionVector.scale(0.001F));

        return new Ray(refractionRayOrigin, refractionVector);
    }

    public Ray shadowRay(Vector3D hitPos, Vector3D lightPos) {
        Vector3D shadowRayDirection = lightPos.subtract(hitPos);
        Vector3D shadowRayOrigin = hitPos.add(shadowRayDirection.scale(0.001F)); // Add a little to avoid hitting the same object again
        return new Ray(shadowRayOrigin, shadowRayDirection);
    }
}