import java.awt.*;

public class Sphere implements Object3D {

    Vector3D position;
    private double radius;
    Color c = Color.CYAN;

    public Sphere(Vector3D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public int getColor() {
        return c.getRGB();
    }

    //replace x with Ray -> ausmultiplizieren -> quadratische Gleichung
    @Override
    public double calculateIntersection(Vector3D origin, Vector3D direction) {

        double a = Vector3D.dot(direction,direction);
        double b = 2 * Vector3D.dot(direction, origin.subtract(position));
        double c = (Vector3D.dot(origin.subtract(position), origin.subtract(position))) - (radius * radius);
        Vector3D result = Util.abcFormel(a,b,c);

        //TODO Anzahl der Lösugnen anders machen
        int solutions = (int) Math.round(result.getZ());
        if(solutions == 0){
            return 0;
        }

        else if (solutions == 1){
            return result.getX();
        }

        else if(solutions == 2) {
            if(result.getX() < 0){
                if(result.getY() < 0){
                    return Double.MAX_VALUE;
                }
                //kugel um Kamera
                else {
                    return result.getY();
                }
            } else {
                if(result.getY() < 0){
                    return result.getX();
                }
                else {
                    return Math.min(result.getX(), result.getY());
                }
            }
        }else{
            return Double.MAX_VALUE;
        }
    }
    @Override
    public Vector3D getNormalAt(Vector3D point) {
        return point.subtract(position).normalize();
    }
}

