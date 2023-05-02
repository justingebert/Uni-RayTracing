import java.util.ArrayList;

public class Scene {
    //continue Later
    private static Scene scene;
    ArrayList<Object3D> objects = new ArrayList<Object3D>();
    ArrayList<Light> lights = new ArrayList<Light>();


    public Scene(){
        objects.add(new Sphere(new Vector3D(0, 0, -5), 6));
    }

    public static Scene getScene(){
        if(scene == null){
            scene = new Scene();
        }
        return scene;
    }
}
