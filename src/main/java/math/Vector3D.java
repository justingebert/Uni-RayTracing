package math;

import java.awt.*;

public class Vector3D {
    private double x, y, z;

    public Vector3D(double x, double y, double z) {
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
            throw new IllegalArgumentException("NaN");

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double x){
        this.x = x;
        this.y = x;
        this.z = x;
    }
    //generated
    public static Vector3D randomUnitVector() {
        double a = Math.random() * 2 * Math.PI;
        double z = Math.random() * 2 - 1;
        double r = Math.sqrt(1 - z * z);
        return new Vector3D(r * Math.cos(a), r * Math.sin(a), z);
    }

    public Vector3D getValues(){
        return new Vector3D(x,y,z);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D negate() {
        return new Vector3D(-this.x, -this.y, -this.z);
    }

    public Vector3D add(Vector3D vec) {
        return new Vector3D(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    public Vector3D subtract(Vector3D vec) {
        return new Vector3D(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public Vector3D scale(double scalar) {
        return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3D sqrt() {
        return new Vector3D(Math.sqrt(this.x), Math.sqrt(this.y), Math.sqrt(this.z));
    }
    public Vector3D multiply(Vector3D vec) {
        return new Vector3D(this.x * vec.x, this.y * vec.y, this.z * vec.z);
    }

    public Vector3D divide(Vector3D vec) {
        return new Vector3D(this.x / vec.x, this.y / vec.y, this.z / vec.z);
    }

    public double length() {
        return (double) Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3D normalize() {
        double length = length();
        if(length == 0)
            throw new IllegalArgumentException("Cannot normalize a vector with length 0");
            //return new math.Vector3D(0, 0, 0);

        return new Vector3D(this.x / length, this.y / length, this.z / length);
    }


    public static double dot(Vector3D a, Vector3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public double dot (Vector3D other) {
        return x * other.getX() + y * other.getY() + z * other.getZ();
    }

    public Vector3D cross(Vector3D other) {
        double cx = y * other.getZ() - z * other.getY();
        double cy = z * other.getX() - x * other.getZ();
        double cz = x * other.getY() - y * other.getX();
        return new Vector3D(cx, cy, cz);
    }
    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }

    //TODO make with subtract and length
    public static float distance(Vector3D a, Vector3D b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2)+Math.pow(a.y - b.y, 2)+Math.pow(a.z - b.z, 2));
    }
    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public int toRGB(){
        /*Color color = new Color((int) x, (int) y, (int) z);
        return color.getRGB();*/
        return (0xFF << 24) | ((int) this.getX() << 16) | ((int) this.getY() << 8) | (int) this.getZ();
    }
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    public Vector3D clamp(double min, double max) {
        return new Vector3D(Math.max(min, Math.min(max, x)), Math.max(min, Math.min(max, y)), Math.max(min, Math.min(max, z)));
    }
}
