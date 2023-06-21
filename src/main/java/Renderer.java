import Lights.PointLight;
import Objects.Object3D;
import math.Intersect;
import math.Ray;
import math.Vector3D;

import java.awt.*;
import java.util.ArrayList;

import static math.Util.clamp;

public class Renderer {
    private static final float GLOBAL_ILLUMINATION = 0.0003F;
    private static final int MAX_REFLECTION_BOUNCES = 5;
    private static final boolean SHOW_SKYBOX = true;

    public static int[] renderImage(Scene scene, int resY, int resX) {
        int[] pixels = new int[resX * resY];
        for (int y = 0; y < resY; ++y) {
            for (int x = 0; x < resX; ++x) {

                Ray ray = scene.getActiveCamera().eyeToImage(x, y, resX, resY);
                Intersect intersection = scene.RayData(ray);
                if (intersection != null) {
                    Vector3D color = calcColorAtHit(intersection, scene, ray, MAX_REFLECTION_BOUNCES);

                    //gamma correction
                    double r = Math.pow(clamp(color.getX(), 0, 1), 0.45) * 255;
                    double g = Math.pow(clamp(color.getY(), 0, 1), 0.45) * 255;
                    double b = Math.pow(clamp(color.getZ(), 0, 1), 0.45) * 255;

                    int RGB = (0xFF << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
                    pixels[y * resX + x] = RGB;
                } else if (SHOW_SKYBOX) {
                    //TODO skybox
                    pixels[y * resX + x] = Color.BLACK.getRGB();
                } else {
                    pixels[y * resX + x] = Color.BLACK.getRGB();
                }
            }
        }
        return pixels;
    }

    //TODO move  reflect and refract method in ray class
    private static Vector3D calcColorAtHit(Intersect intersection, Scene scene, Ray ray, int bounces) {
        if(bounces <= 0) return new Vector3D(0, 0, 0);

        Vector3D hitPos = intersection.getPosition();
        Vector3D rayDir = ray.getDirection();
        Vector3D normal = intersection.getNormal();
        Vector3D V = ray.getDirection().scale(-1).normalize();

        double nv = Math.max(Vector3D.dot(normal, V), 0);

        Vector3D col;
        Vector3D Fresnel = new Vector3D(1, 1, 1);
        Vector3D one = new Vector3D(1.0, 1.0, 1.0);

        Object3D hitObject = intersection.getHitObject();
        Vector3D albedo = hitObject.material.getAlbedo();
        Vector3D albedoG = new Vector3D(Math.pow(albedo.getX(), 2.2), Math.pow(albedo.getY(), 2.2), Math.pow(albedo.getZ(), 2.2));
        double m = hitObject.material.getMetalness();
        double roughness = hitObject.material.getRoughness();
        double reflectivity = hitObject.material.getReflectivity();
        double transparency = hitObject.material.getTransparency();
        double ioR = hitObject.material.getIoR();
        Vector3D f0 = albedo.scale(m).add(one.scale(0.04).scale(1 - m));

        Vector3D colorReflection = new Vector3D(0, 0, 0);

        Ray reflectionRay = ray.reflect(hitPos, normal);
        Intersect reflectionIntersection = scene.RayData(reflectionRay);
        //TODO if reflectivvity != 0
        if (reflectionIntersection != null) {
            colorReflection = calcColorAtHit(reflectionIntersection, scene, reflectionRay, bounces-1);
        } else {
            //colS = scene.getSkybox().getColor(reflectionRay);
            colorReflection = albedoG;
            //?correct???
        }

        if (intersection.getHitObject().getTransparency() > 0) {

            //fresnel
            /*double w1 = rayDir.scale(-1).dot(normal);
            double w2 = rayDir.dot(normal);
            double fS = (i1  * w1 - i2 * w2)/ (i1 * w1 + i2 * w2);
            double fP = (i2 * w1 - i1 * w2) / (i2 * w1 + i1 * w2);
            double f = (fS + fP) / 2;
            Fresnel = one.subtract(new Vector3D(f));*/

            Fresnel = f0.add(one.subtract(f0).scale(Math.pow(1 - nv, 5)));
            Ray refractionRay = ray.refract(hitPos, normal, ioR);
            Intersect refractionIntersection = scene.RayData(refractionRay);
            if (refractionIntersection != null) {
                col = calcColorAtHit(refractionIntersection, scene, refractionRay, bounces - 1);
            }else{
                col = albedoG.scale((1 - transparency)).add(colorReflection.scale(transparency));
            }
        } else {
            Fresnel = f0.add(one.subtract(f0).scale(Math.pow(1 - nv, 5)));
            col = albedoG.scale((1 - reflectivity)).add(colorReflection.scale(reflectivity));
        }
        //TODO pass material to calcLight instead of so many vars
        return calcLight(scene, ray, intersection, Fresnel, colorReflection, roughness, col);
    }

    private static Vector3D calcFresnel(double ioR) {
        return null;
    }

    private static Vector3D calcDiffuse(Vector3D albedo, Vector3D colS, double roughness, Vector3D col) {
        return null;
    }

    //TODO nv doubled??
    private static Vector3D calcLight(Scene scene, Ray ray, Intersect intersection, Vector3D Fresnel, Vector3D colorReflection, double r, Vector3D col) {
        ArrayList<PointLight> lights = scene.getLights();

        Vector3D colorAtHit = new Vector3D(0);
        Vector3D V = ray.getDirection().scale(-1).normalize();
        Vector3D one = new Vector3D(1.0);

        for (PointLight light : lights) {
            Vector3D lightColor = light.getClampedColor();
            double lightIntensity = light.getIntensity();

            Vector3D lightDir = light.getPosition().subtract(intersection.getPosition()).normalize();
            Vector3D lightVector = lightDir.scale(-1);

            Vector3D H = V.add(lightDir).scale(0.5).normalize();

            double nr = Math.max(Vector3D.dot(intersection.getNormal(), lightVector), 0);
            double nh = Vector3D.dot(intersection.getNormal(), H);
            double nl = Math.max(Vector3D.dot(intersection.getNormal(), lightDir), 0);
            double nv = Math.max(Vector3D.dot(intersection.getNormal(), V), 0);

            double r2 = Math.pow(r, 2);
            double rHalf = r / 2;

            //Ergebnis += FarbeQ* IntensitätQ* N·R * F * ColS
            colorAtHit.add(lightColor.scale(lightIntensity).scale(nr).multiply(Fresnel).multiply(colorReflection));

            //*shadow
            Vector3D shadowRayOrigin = intersection.getPosition().add(lightVector.scale(-0.001F));
            Ray shadowRay = new Ray(shadowRayOrigin, lightVector.scale(-1));
            Intersect shadowIntersection = scene.RayData(shadowRay);

            double D = r2 / (Math.pow(Math.PI * (Math.pow(nh, 2) * (r2 - 1) + 1), 2));
            double G = nv / ((nv * (1.0 - rHalf)) + rHalf) * nl / ((nl * (1.0 - rHalf)) + rHalf);
            Vector3D specular = Fresnel.scale(D).scale(G);
            Vector3D cookTorrance = specular.add(col.multiply(one.subtract(Fresnel)));

            Vector3D color = colorAtHit.add(lightColor.scale(lightIntensity).scale(nl).multiply(cookTorrance));
            if (shadowIntersection == null) {
                colorAtHit = colorAtHit.add(color);
            } else {
                colorAtHit = colorAtHit.add(color.scale(0.03));
            }
        }

        return colorAtHit;
    }

    private static Vector3D cookTorrance(){
        return null;
    }

}
