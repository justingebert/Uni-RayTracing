import Lights.PointLight;
import Materials.Material;
import Objects.Object3D;
import math.Intersect;
import math.Ray;
import math.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static math.Util.clamp;

public class Renderer {
    private static final float GLOBAL_ILLUMINATION = 0.0003F;
    private static final int MAX_REFLECTION_BOUNCES = 4;
    private static final boolean SHOW_SKYBOX = true;
    private static boolean INDIRECT_LIGHTING = true;
    private static boolean REFLECTIONS = true;
    private static boolean REFRACTIONS = true;
    private static boolean SHADOWS = true;
    private static int SHADOW_RAYS = 5;
    private static int DIFFUSE_RAYS = 1;

    /*public static int[] renderImage(Scene scene, int resY, int resX) {
        int[] pixels = new int[resX * resY];
        for (int y = 0; y < resY; ++y) {
            for (int x = 0; x < resX; ++x) {

                Ray ray = scene.getActiveCamera().eyeToImage(x, y, resX, resY);
                Vector3D color = calcColorAtHit(scene, ray, MAX_REFLECTION_BOUNCES);
                //gamma correction
                double r = Math.pow(clamp(color.getX(), 0, 1), 0.45) * 255;
                double g = Math.pow(clamp(color.getY(), 0, 1), 0.45) * 255;
                double b = Math.pow(clamp(color.getZ(), 0, 1), 0.45) * 255;

                int RGB = (0xFF << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
                pixels[y * resX + x] = RGB;

            }
        }
        return pixels;
    }*/

    public static int[] renderImage(Scene scene, int resY, int resX, int numThreads) {
        int[] pixels = new int[resX * resY];
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int t = 0; t < numThreads; t++) {
            int startY = t * (resY / numThreads);
            int endY = (t + 1) * (resY / numThreads);

            Runnable renderTask = new RenderTask(scene, resX, resY, startY, endY, pixels);
            Future<Void> future = executor.submit(renderTask, null);
            futures.add(future);
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return pixels;
    }

    private static class RenderTask implements Runnable {
        private final Scene scene;
        private final int resX;
        private final int resY;
        private final int startY;
        private final int endY;
        private final int[] pixels;

        public RenderTask(Scene scene, int resX, int resY, int startY, int endY, int[] pixels) {
            this.scene = scene;
            this.resX = resX;
            this.resY = resY;
            this.startY = startY;
            this.endY = endY;
            this.pixels = pixels;
        }

        @Override
        public void run() {
            for (int y = startY; y < endY; ++y) {
                for (int x = 0; x < resX; ++x) {

                    Ray ray = scene.getActiveCamera().eyeToImage(x, y, resX, resY);
                    Vector3D color = calcColorAtHit(scene, ray, MAX_REFLECTION_BOUNCES);
                    //gamma correction
                    double r = Math.pow(clamp(color.getX(), 0, 1), 0.45) * 255;
                    double g = Math.pow(clamp(color.getY(), 0, 1), 0.45) * 255;
                    double b = Math.pow(clamp(color.getZ(), 0, 1), 0.45) * 255;
                    int RGB = (0xFF << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
                    pixels[y * resX + x] = RGB;
                }
            }
        }
    }

    private static Vector3D calcColorAtHit(Scene scene, Ray ray, int bounces) {
        if(bounces <= 0) return new Vector3D(0, 0, 0);

        Intersect intersection = scene.RayData(ray);
        if (intersection == null) {
            if(SHOW_SKYBOX){
                return scene.getSkyBox().getColor(ray);
                //return new Vector3D(0, 0, 0);
            }else{
                return new Vector3D(0, 0, 0);
            }
        }

        Vector3D hitPos = intersection.getPosition();
        Vector3D rayDir = ray.getDirection();
        Vector3D normal = intersection.getNormal();
        Vector3D V = ray.getDirection().scale(-1).normalize();
        double nv = Math.max(Vector3D.dot(normal, V), 0);

        Vector3D col = new Vector3D(0.0);
        Vector3D one = new Vector3D(1.0);

        Object3D hitObject = intersection.getHitObject();
        Material material = hitObject.material;
        Vector3D albedo = hitObject.material.getAlbedo();
        Vector3D albedoG = new Vector3D(Math.pow(albedo.getX(), 2.2), Math.pow(albedo.getY(), 2.2), Math.pow(albedo.getZ(), 2.2));
        double m = hitObject.material.getMetalness();
        double reflectivity = hitObject.material.getReflectivity();
        double transparency = hitObject.material.getTransparency();
        double ioR = hitObject.material.getIoR();
        Vector3D f0 = albedo.scale(m).add(one.scale(0.04).scale(1 - m));
        Vector3D Fresnel = f0.add(one.subtract(f0).scale(Math.pow(1 - nv, 5)));


        Vector3D colorReflection = new Vector3D(0);
        if(REFLECTIONS){
            Ray reflectionRay = ray.reflect(hitPos, normal);
            Intersect reflectionIntersection = scene.RayData(reflectionRay);
            if (reflectionIntersection != null) {
                colorReflection = calcColorAtHit(scene, reflectionRay, bounces-1);
            } else {
                colorReflection = scene.getSkyBox().getColor(reflectionRay);
            }
        }

        if(REFRACTIONS){
            if (material.getTransparency() > 0) {
                Ray refractionRay = ray.refract(hitPos, normal, ioR);
                Intersect refractionIntersection = scene.RayData(refractionRay);
                if (refractionIntersection != null) {
                    col = col.add(calcColorAtHit(scene, refractionRay, bounces - 1)).scale(0.2);
                }else{
                    //col = albedoG.scale((1 - transparency)).add(colorReflection.scale(transparency)).scale(0.01);
                    col = scene.getSkyBox().getColor(refractionRay).scale((1 - transparency)).add(colorReflection.scale(transparency)).scale(0.1);
                }
            } else {
                col = albedoG.scale((1 - reflectivity)).add(colorReflection.scale(reflectivity));
            }
        }

        if (INDIRECT_LIGHTING) {
            Vector3D indirectLight = new Vector3D(0);
            for(int i = 0; i < DIFFUSE_RAYS; i++){
                Ray randomRay = intersection.generateRandomRay();
                Intersect randomIntersection = scene.RayData(randomRay);
                if (randomIntersection != null) {
                    double rn = randomRay.getDirection().dot(intersection.getNormal());
                    indirectLight = indirectLight.add(randomIntersection.getMaterial().getAlbedo().scale(rn));
                }
            }
            indirectLight = indirectLight.scale(1.0 / DIFFUSE_RAYS);
            col = col.add(indirectLight);
        }

        //TODO pass material to calcLight instead of so many vars
        //reflected color should not go thorugh cook torance again (calcLight)
        return calcLight(scene, ray, intersection, Fresnel, colorReflection, col, material, SHADOW_RAYS);
    }


    private static Vector3D calcLight(Scene scene, Ray ray, Intersect intersection, Vector3D Fresnel, Vector3D colorReflection, Vector3D col, Material material, int shadowRays) {
        ArrayList<PointLight> lights = scene.getLights();

        Vector3D colorAtHit = new Vector3D(0);
        Vector3D V = ray.getDirection().scale(-1).normalize();
        Vector3D one = new Vector3D(1.0);
        double r = material.getRoughness();

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

            double D = r2 / (Math.pow(Math.PI * (Math.pow(nh, 2) * (r2 - 1) + 1), 2));
            double G = nv / ((nv * (1.0 - rHalf)) + rHalf) * nl / ((nl * (1.0 - rHalf)) + rHalf);
            Vector3D specular = Fresnel.scale(D).scale(G);
            Vector3D cookTorrance = specular.add(col.multiply(one.subtract(Fresnel)));

            Vector3D color = colorAtHit.add(lightColor.scale(lightIntensity).scale(nl).multiply(cookTorrance));

            //*SHADOWS*
            double shadowFactor = 0;
            for(int i = 0; i < shadowRays; i++){
                Vector3D randomPointOnLight = light.getRandomPointOnSurface();
                Vector3D shadowRayDirection = randomPointOnLight.subtract(intersection.getPosition()).normalize();
                Vector3D shadowRayOrigin = intersection.getPosition().add(shadowRayDirection.scale(0.001F));
                Ray shadowRay = new Ray(shadowRayOrigin, shadowRayDirection);
                Intersect shadowIntersection = scene.RayData(shadowRay);
                if (shadowIntersection == null) {
                    shadowFactor += 1.0 / shadowRays;
                }
            }
            shadowFactor = Math.min(shadowFactor, 1);
            colorAtHit = colorAtHit.add(color.scale(shadowFactor));
        }

        return colorAtHit;
    }


    //TODO
    private static Vector3D hardShadows(Scene scene, Intersect intersection, Ray ray, int bounces, int numRays) {return new Vector3D(0);}
    private static Vector3D softShadows(Scene scene, Intersect intersection, Ray ray, int bounces, int numRays) {return new Vector3D(0);}
    private static Vector3D pathTraceIndirectLight(Scene scene,Intersect intersection, Ray ray, int bounces, int numRays) {return new Vector3D(0);}
    private static Vector3D cookTorrance(){
        return null;
    }
    private static Vector3D calcFresnel(double ioR) {
        return null;
    }
    private static Vector3D calcDiffuse(Vector3D albedo, Vector3D colS, double roughness, Vector3D col) {
        return null;
    }


}
