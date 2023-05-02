import java.util.ArrayList;

public class Camera {



    private Vector3D position;
    private Vector3D direction;
    private Vector3D up;
    private double fieldOfView;
    private double aspectRatio;
    private int imageWidth;
    private int imageHeight;


    private Vector3D Back;
    private Vector3D U;
    private Vector3D V;

    private int left;
    private int right;
    private int top;
    private int bottom;
    private double d;
    private Vector3D WD;

    //transition from d to FOV
    public Camera(Vector3D position, Vector3D direction, Vector3D up, int imageWidth, int imageHeight) {
        this.position = position;
        this.direction = direction.normalize();
        this.up = up.normalize();
        this.aspectRatio = (float) imageWidth / (float) imageHeight;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        //calculate back, U, V with cross product
        this.Back = position.subtract(direction).normalize();
        this.U = up.cross(Back).normalize();
        this.V = Back.cross(U).normalize();

        this.left = -imageWidth / 2;
        this.right = imageWidth / 2;
        this.top = imageHeight / 2;
        this.bottom = -imageHeight / 2;

        //FOV
        this.d = top/Math.tan(Math.tan(Math.PI/4)/2);

        this.WD = this.Back.scale(this.d*-1.0);


    }

   /* public ArrayList<Ray> generateRays() {
        ArrayList<Ray> rays = new ArrayList<>();
        double halfWidth = Math.tan(Math.toRadians(fieldOfView) / 2);
        double halfHeight = halfWidth / aspectRatio;
        Vector3D xAxis = direction.cross(up).normalize();
        Vector3D yAxis = up;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                double xNorm = (x + 0.5) / imageWidth;
                double yNorm = (y + 0.5) / imageHeight;
                double xPixel = (2 * xNorm - 1) * halfWidth;
                double yPixel = (1 - 2 * yNorm) * halfHeight;
                Vector3D pixelPos = position.add(direction.scale(1)).add(xAxis.scale(xPixel)).add(yAxis.scale(yPixel));
                Vector3D rayDirection = pixelPos.subtract(position).normalize();
                rays.add(new Ray(position, rayDirection));
            }
        }

        return rays;
    }*/

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public Vector3D getUp() {
        return up;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public Vector3D getBack() {
        return Back;
    }

    public Vector3D getU() {
        return U;
    }

    public Vector3D getV() {
        return V;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public double getD() {
        return d;
    }

    public Vector3D getWD() {
        return WD;
    }
}