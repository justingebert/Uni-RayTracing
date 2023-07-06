package math;

import Lights.Light;
import Materials.Material;
import Objects.Object3D;

import java.util.ArrayList;

public class Intersect {
    private Ray ray;
    private Object3D hitObject;
    private Vector3D hitPos;
    private Vector3D normal;

    public Intersect(Ray ray, Object3D hitObject, Vector3D hitPos) {
        this.ray = ray;
        this.hitObject = hitObject;
        this.hitPos = hitPos;
        this.normal = hitObject.getNormalAt(hitPos);
    }

    public Ray getRay() {
        return ray;
    }

    public Object3D getHitObject() {
        return hitObject;
    }

    public Vector3D getPosition() {
        return hitPos;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public Material getMaterial() {
        return hitObject.material;
    }

    public Ray generateRandomRay() {

        Vector3D randomDirection = Vector3D.randomUnitVector();
        if (randomDirection.dot(normal) < 0) {
            randomDirection = randomDirection.scale(-1);
        }
        Vector3D randomRayOrigin = hitPos.add(randomDirection.scale(0.001F)); // Add a little to avoid hitting the same object again

        return new Ray(randomRayOrigin, randomDirection);
    }

    public Ray generateRandomRayToLight(ArrayList<Light> lights) {
        Vector3D randomDirection = Vector3D.randomUnitVector();
        if (randomDirection.dot(normal) < 0) {
            randomDirection = randomDirection.scale(-1);
        }
        Vector3D randomRayOrigin = hitPos.add(randomDirection.scale(0.001F)); // Add a little to avoid hitting the same object again

        return new Ray(randomRayOrigin, randomDirection);
    }

    public Ray generateRandomRayToPrev(ArrayList<Intersect> prevIntersects) {
        Vector3D randomDirection = Vector3D.randomUnitVector();
        if (randomDirection.dot(normal) < 0) {
            randomDirection = randomDirection.scale(-1);
        }
        Vector3D randomRayOrigin = hitPos.add(randomDirection.scale(0.001F)); // Add a little to avoid hitting the same object again

        return new Ray(randomRayOrigin, randomDirection);
    }
}
