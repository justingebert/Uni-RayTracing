package math;

import math.Ray;
import math.Vector3D;

public class Line {
    public Vector3D A;
    public Vector3D B;

    public  Line(Vector3D pointA, Vector3D pointB) {
        this.A = pointA;
        this.B = pointB;
    }

    public Ray asRay() {
        return new Ray(A, B.subtract(A).normalize());
    }
}