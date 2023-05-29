package Objects;

import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

import java.awt.*;

import static java.lang.Math.*;

//TODO implment transformation as parameter object or list
public class Quadric extends Object3D{

    //1*x^2+ 1*y^2+ 1*z^2+ 2*(0*x*y+ 0*x*z+ 0*y*z+ 0*x+ 0*y+0*z) + -1 < 0

    double a, b, c, d, e, f, g, h, i, j;
    public Quadric(double a,double b, double c, double d, double e, double f, double g, double h, double i, double j,Vector3D position, Color color) {
        super(position, color);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
    }

    @Override
    public Vector3D calculateIntersection(Ray ray) {


        double ox = ray.getOrigin().getX();
        double oy = ray.getOrigin().getY();
        double oz = ray.getOrigin().getZ();
        double dx = ray.getDirection().getX();
        double dy = ray.getDirection().getY();
        double dz = ray.getDirection().getZ();

        double a = this.a;
        double b = this.b;
        double c = this.c;
        double d = this.d;
        double e = this.e;
        double f = this.f;
        double g = this.g;
        double h = this.h;
        double i = this.i;
        double j = this.j;

        double A = a * dx * dx + b * dy * dy + c * dz * dz + d * dx * dy + e * dx * dz + f * dy * dz;
        double B = 2 * (a * ox * dx + b * oy * dy + c * oz * dz) + d * (ox * dy + oy * dx) + e * (ox * dz + oz * dx) + f * (oy * dz + oz * dy) + g * dx + h * dy + i * dz;
        double C = a * ox * ox + b * oy * oy + c * oz * oz + d * ox * oy + e * ox * oz + f * oy * oz + g * ox + h * oy + i * oz + j;

        // Calculate the discriminant of the quadratic equation
        double discriminant = B * B - 4 * A * C;

        if (discriminant < 0) {
            // No intersection
            return null;
        } else {
            // Calculate the solutions of the quadratic equation
            double t1 = (-B - sqrt(discriminant)) / (2 * A);
            double t2 = (-B + sqrt(discriminant)) / (2 * A);

            if (t1 < 0 && t2 < 0) {
                // Intersection points are behind the ray origin
                return null;
            }

            double t = (t1 < t2 && t1 >= 0) ? t1 : t2; // Choose the closer intersection point

            return ray.getOrigin().add(ray.getDirection().scale(t));
        }
    }
    //TODO transforming still a bit buggy tranlation also sclaes the quadric
    @Override
    public void applyTransformation(Matrix4x4 transformationMatrix) {
       Matrix4x4 transposed = transformationMatrix.transpose();
       Matrix4x4 inverse = transformationMatrix;
       Matrix4x4 quadric = new Matrix4x4();

       quadric.setQuadric(a,b,c,d,e,f,g,h,i,j);

       Matrix4x4 transformedQuadric = transposed.multiply(quadric.multiply(inverse));
       transformedQuadric.print();
       double [][] quadricArray = transformedQuadric.getMatrix();
       this.a = quadricArray[0][0];
       this.b = quadricArray[1][1];
       this.c = quadricArray[2][2];
       this.d = quadricArray[0][1];
       this.e = quadricArray[0][2];
       this.f = quadricArray[1][2];
       this.g = quadricArray[0][3];
       this.h = quadricArray[1][3];
       this.i = quadricArray[2][3];
       this.j = quadricArray[2][1];
    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        double px = point.getX();
        double py = point.getY();
        double pz = point.getZ();

        // Calculate the components of the surface normal at the given point
        double nx = 2 * a * px + d * py + e * pz + g;
        double ny = 2 * b * py + d * px + f * pz + h;
        double nz = 2 * c * pz + e * px + f * py + i;

        // Create and return the surface normal vector
        return new Vector3D (nx, ny, nz).normalize();
    }

}
