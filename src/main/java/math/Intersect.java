package math;

import Objects.Object3D;

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
}
