public interface Object3D {

    public abstract int getColor();
    //TODO change to implement ray class
    public abstract double calculateIntersection(Vector3D origin, Vector3D direction);

    //public abstract double calculateIntersection(Ray ray);

    public abstract Vector3D getNormalAt(Vector3D point);

}
