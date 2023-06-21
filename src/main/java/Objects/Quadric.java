package Objects;

import Materials.Material;
import math.Matrix4x4;
import math.Ray;
import math.Vector3D;

//TODO implment transformation as parameter object or list
public class Quadric extends Object3D{

    //1*x^2+ 1*y^2+ 1*z^2+ 2*(0*x*y+ 0*x*z+ 0*y*z+ 0*x+ 0*y+0*z) + -1 < 0

    public Matrix4x4 transformationMatrix;
    public Matrix4x4 inverseTransformationMatrix;

    double a, b, c, d, e, f, g, h, i, j;
    public Quadric(double a,double b, double c, double d, double e, double f, double g, double h, double i, double j,Vector3D position, Material material) {
        super(position, material);
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
        double ox2 = ox * ox;
        double oy = ray.getOrigin().getY();
        double oy2 = oy * oy;
        double oz = ray.getOrigin().getZ();
        double oz2 = oz * oz;
        double dx = ray.getDirection().getX();
        double dx2 = dx * dx;
        double dy = ray.getDirection().getY();
        double dy2 = dy * dy;
        double dz = ray.getDirection().getZ();
        double dz2 = dz * dz;

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


        //double A = a * dx2 + b * dy2 + c * dz2 + 2*d * dx * dy + 2*e * dx * dz + 2*f * dy * dz;
        //TODO klammern angucken
        //double B = 2 * (a * ox * dx + b * oy * dy + c * oz * dz + d * (ox * dy + oy * dx) + e * (ox * dz + oz * dx) + f * (oy * dz + oz * dy) + g * dx + h * dy + i * dz);
        //double B = 2 * (a * ox * dx + b * oy * dy + c * oz * dz + d * ox * dy + d * oy * dx + e * ox * dz + e* oz * dx + f * oy * dz + f* oz * dy + g * dx + h * dy + i * dz);
        //double C = a * ox2 + b * oy2 + c * oz2 + 2*(d * ox * oy + e * ox * oz + f * oy * oz + g * ox + h * oy + i * oz) + j;

        double A = a*dx2 + b*dy2 + c*dz2 + 2*(d*dx*dy + e*dx*dz + f*dy*dz);
        double B = 2*(a*ox*dx + b*oy*dy + c*oz*dz + d * (ox*dy + oy*dx) + e * (ox*dz + oz*dx) + f*(oy*dz + oz+dy) + g*dx + h*dy + i*dz);
        double C = a*ox2 + b*oy2 + c*oz2 + 2*(d*ox*oy + e*ox*oz + f*oy*oz + g*ox + h*oy + i*oz) + j;

        // Calculate the discriminant of the quadratic equation
        double discriminant = B * B - (4 * A * C);

        //TODO a can't be zero
        if (discriminant < 0) {
            // No intersection
            return null;
        } else {
                // Calculate the solutions of the quadratic equation
                double t1 = (-B - Math.sqrt(discriminant)) / (2 * A);
                double t2 = (-B + Math.sqrt(discriminant)) / (2 * A);

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


       quadric.setQuadric(this.a,this.b,this.c,this.d,this.e,this.f,this.g,this.h,this.i,this.j);
       //quadric.print();

       //transposed.print();

       Matrix4x4 transformedQuadric1 = transposed.multiply(quadric);
       Matrix4x4 transformedQuadric2 = transformedQuadric1.multiply(inverse);

       //transformedQuadric2.print();
       double [][] quadricArray = transformedQuadric2.getMatrix();

       this.transformationMatrix = transformationMatrix;
       //setQuadric(transformedQuadric.getQuadricParameters());

       this.a = transformedQuadric2.matrix[0][0];
       this.b = transformedQuadric2.matrix[1][1];
       this.c = transformedQuadric2.matrix[2][2];
       this.d = transformedQuadric2.matrix[0][1];
       this.e = transformedQuadric2.matrix[0][2];
       this.f = transformedQuadric2.matrix[1][2];
       this.g = transformedQuadric2.matrix[0][3];
       this.h = transformedQuadric2.matrix[1][3];
       this.i = transformedQuadric2.matrix[2][3];
       this.j = transformedQuadric2.matrix[3][3];
       //print();
    }

    @Override
    public Vector3D getNormalAt(Vector3D point) {
        double px = point.getX();
        double py = point.getY();
        double pz = point.getZ();

        // Calculate the components of the surface normal at the given point
        double nx = 2 * (a * px + d * py + e * pz + g);
        double ny = 2 * (b * py + d * px + f * pz + h);
        double nz = 2 * (c * pz + e * px + f * py + i);


        return new Vector3D (nx, ny, nz).normalize();
    }

    public void print(){
        Matrix4x4 quadric = new Matrix4x4();
        quadric.setQuadric(this.a,this.b,this.c,this.d,this.e,this.f,this.g,this.h,this.i,this.j);
        quadric.print();
    }

    public void setQuadric(Matrix4x4 quadric){

    }


}
