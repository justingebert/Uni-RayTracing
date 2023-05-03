import math.Ray;
import math.Vector3D;

public class Camera {



    private Vector3D Position;
    private Vector3D Direction;
    private Vector3D Up;
    private Vector3D Right;
    private double aspectRatio;
    private double aspectX;
    private double aspectY;
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
    private Vector3D leftBottomCorner;

    //transition from d to FOV
    public Camera(Vector3D Position, Vector3D Direction, Vector3D Up, Vector3D right, int imageWidth, int imageHeight) {
        this.Position = Position;
        this.Direction = Direction.normalize();
        this.Up = Up.normalize();
        this.Right = right.normalize();
        this.aspectRatio = (float) imageWidth / (float) imageHeight;
        //make procedural
        this.aspectX = 1.6;
        this.aspectY = 0.9;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        //calculate back, U, V with cross product
        /*this.Back = Position.subtract(Direction).normalize();
        this.U = Up.cross(Back).normalize();
        this.V = Back.cross(U).normalize();

        this.left = -imageWidth / 2;
        this.right = imageWidth / 2;
        this.top = imageHeight / 2;
        this.bottom = -imageHeight / 2;

        //FOV
        this.d = top/Math.tan(Math.tan(Math.PI/4)/2);

        this.WD = this.Back.scale(this.d*-1.0);*/

        Vector3D leftV = Direction.add(Right.negate().scale(aspectX/2));
        this.leftBottomCorner = leftV.add(Up.negate().scale(aspectY/2));

    }

    //Ray vom Auge zum Bildpunkt
    public Ray eyeToImage(int x, int y, int imageWidth, int imageHeight) {
        Vector3D ray = leftBottomCorner.add(Right.scale(x*(aspectX/imageWidth)).add(Up.scale(y*(aspectY/imageHeight))));
        return new Ray(Position, ray);
    }

    public Vector3D getPosition() {
        return Position;
    }

    public Vector3D getDirection() {
        return Direction;
    }

    public Vector3D getUp() {
        return Up;
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