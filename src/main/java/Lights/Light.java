package Lights;

import math.Vector3D;

public interface Light {

    Vector3D getPosition();

    Vector3D fromPosition(Vector3D fromPosition);
}
