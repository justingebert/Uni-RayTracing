package Lights;

import math.Vector3D;

public class PointLight implements Light {

    Vector3D position;
    Vector3D Intensity;

    public PointLight(Vector3D position, Vector3D Intensity) {
        this.position = position;
        this.Intensity = Intensity;
    }
    @Override
    public Vector3D getPosition() {
        return null;
    }

    @Override
    public Vector3D fromPosition(Vector3D fromPosition) {
        return null;
    }
}
