package Objects;

import math.Matrix4x4;
import math.Vector3D;

public class Transformation {
    private Matrix4x4 matrix; // Assuming a 4x4 matrix for homogeneous transformations

    public Transformation() {
        matrix = new Matrix4x4();
    }

    // Methods to construct transformation matrices

    public void translate(Vector3D translation) {
        // Construct a translation matrix
        Matrix4x4 translationMatrix = new Matrix4x4();
        translationMatrix.setTranslation(translation.getX(), translation.getY(), translation.getZ());

        // Multiply the current matrix by the translation matrix
        matrix = matrix.multiply(translationMatrix);
    }

    public void rotateX(double angle) {
        // Construct a rotation matrix around the X-axis
        Matrix4x4 rotationMatrix = new Matrix4x4();
        rotationMatrix.setRotationX(angle);

        // Multiply the current matrix by the rotation matrix
        matrix = matrix.multiply(rotationMatrix);
    }

    public void rotateY(double angle) {
        // Construct a rotation matrix around the Y-axis
        Matrix4x4 rotationMatrix = new Matrix4x4();
        rotationMatrix.setRotationY(angle);

        // Multiply the current matrix by the rotation matrix
        matrix = matrix.multiply(rotationMatrix);
    }

    public void rotateZ(double angle) {
        // Construct a rotation matrix around the Z-axis
        Matrix4x4 rotationMatrix = new Matrix4x4();
        rotationMatrix.setRotationZ(angle);

        // Multiply the current matrix by the rotation matrix
        matrix = matrix.multiply(rotationMatrix);
    }

    public void scale(Vector3D scale) {
        // Construct a scaling matrix
        Matrix4x4 scalingMatrix = new Matrix4x4();
        scalingMatrix.setScale(scale.getX(), scale.getY(), scale.getZ());

        // Multiply the current matrix by the scaling matrix
        matrix = matrix.multiply(scalingMatrix);
    }

    // Getter method for the transformation matrix
    public Matrix4x4 getMatrix() {
        return matrix;
    }
}

