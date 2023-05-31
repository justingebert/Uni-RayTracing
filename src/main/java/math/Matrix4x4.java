package math;


//TODO not overwrite matrix parameters with different values - scale, translate, rotate back to back

import java.util.Arrays;

public class Matrix4x4 {
    public double[][] matrix;

    public Matrix4x4() {
        matrix = new double[4][4];
        matrix[0][0] = 1.0;
        matrix[1][1] = 1.0;
        matrix[2][2] = 1.0;
        matrix[3][3] = 1.0;
    }

    public Matrix4x4(double[][] matrix) {
        this.matrix = matrix;
    }

    public double [] [] getMatrix() {
        return matrix;
    }

    public void setQuadric(double a, double b, double c, double d, double e, double f, double g, double h, double i, double j) {
        matrix[0][0] = a;
        matrix[1][1] = b;
        matrix[2][2] = c;
        matrix[0][1] = d;
        matrix[1][0] = d;
        matrix[2][0] = e;
        matrix[0][2] = e;
        matrix[2][1] = f;
        matrix[1][2] = f;
        matrix[3][0] = g;
        matrix[0][3] = g;
        matrix[1][3] = h;
        matrix[3][1] = h;
        matrix[2][3] = i;
        matrix[3][2] = i;
        matrix[3][3] = j;
    }

    public void setTranslation(double tx, double ty, double tz) {
        matrix[0][3] = tx;
        matrix[1][3] = ty;
        matrix[2][3] = tz;
    }

    public void setQuadricTranslation(double tx, double ty, double tz) {
        matrix[0][3] = -1*tx;
        matrix[1][3] = -1*ty;
        matrix[2][3] = -1*tz;
    }

    public void setRotationX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        matrix[1][1] = cos;
        matrix[1][2] = -sin;
        matrix[2][1] = sin;
        matrix[2][2] = cos;
    }

    public void setQuadricRotationX(double angle) {
        double cos = Math.cos(-1*angle);
        double sin = Math.sin(-1*angle);

        matrix[1][1] = cos;
        matrix[1][2] = -sin;
        matrix[2][1] = sin;
        matrix[2][2] = cos;
    }

    public void setRotationY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        matrix[0][0] = cos;
        matrix[0][2] = sin;
        matrix[2][0] = -sin;
        matrix[2][2] = cos;
    }

    public void setQuadraticRotationY(double angle) {
        double cos = Math.cos(-1*angle);
        double sin = Math.sin(-1*angle);

        matrix[0][0] = cos;
        matrix[0][2] = sin;
        matrix[2][0] = -sin;
        matrix[2][2] = cos;
    }


    public void setRotationZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        matrix[0][0] = cos;
        matrix[0][1] = -sin;
        matrix[1][0] = sin;
        matrix[1][1] = cos;
    }

    public void setQuadricRotationZ(double angle) {
        double cos = Math.cos(-1*angle);
        double sin = Math.sin(-1*angle);

        matrix[0][0] = cos;
        matrix[0][1] = -sin;
        matrix[1][0] = sin;
        matrix[1][1] = cos;
    }

    public void setScale(double sx, double sy, double sz) {
        matrix[0][0] = sx;
        matrix[1][1] = sy;
        matrix[2][2] = sz;
    }

    public void setQuadricScale(double sx, double sy, double sz) {
        matrix[0][0] = 1.0/sx;
        matrix[1][1] = 1.0/sy;
        matrix[2][2] = 1.0/sz;
    }

    public Vector3D multiplyPoint(Vector3D point) {
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();
        double w = 1.0;

        double resultX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z + matrix[0][3] * w;
        double resultY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z + matrix[1][3] * w;
        double resultZ = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z + matrix[2][3] * w;
        double resultW = matrix[3][0] * x + matrix[3][1] * y + matrix[3][2] * z + matrix[3][3] * w;

        if (resultW != 0.0) {
            resultX /= resultW;
            resultY /= resultW;
            resultZ /= resultW;
        }

        return new Vector3D(resultX, resultY, resultZ);
    }

    public Vector3D multiplyDirection(Vector3D direction) {
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();
        double w = 0.0;

        double resultX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z + matrix[0][3] * w;
        double resultY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z + matrix[1][3] * w;
        double resultZ = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z + matrix[2][3] * w;

        return new Vector3D(resultX, resultY, resultZ);
    }

    public Matrix4x4 multiply(Matrix4x4 other) {

        double[][] result = new double[4][4];
        double[][] matrix1 = getMatrix();
        double[][] matrix2 = other.getMatrix();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double sum = 0.0;
                for (int k = 0; k < 4; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }

        return new Matrix4x4(result);
    }

    public Matrix4x4 transpose() {
        Matrix4x4 result = new Matrix4x4();

        for (int i = 0; i < 4; i++) {
            result.matrix[i][0] = matrix[0][i];
            result.matrix[i][1] = matrix[1][i];
            result.matrix[i][2] = matrix[2][i];
            result.matrix[i][3] = matrix[3][i];
        }

        return result;
    }

    public double [] toArray(){
        double [] array = new double[16];
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++, k++){
                array[k] = matrix[i][j];
            }
        }
        return array;
    }

    public double [] getQuadricParameters(){
        double [] array = new double[10];
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = i; j < 4; j++, k++){
                array[k] = matrix[i][j];
            }
        }
        return array;
    }

    public void print() {
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(matrix[i]));

        }
        System.out.println();
    }
}
