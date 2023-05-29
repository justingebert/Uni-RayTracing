package Lights;

import Objects.Object3D;
import math.Ray;
import math.Vector3D;

import java.awt.*;

import static math.Util.clamp;


//TODO make abtract to implemnt area an spot lights
public class PointLight {

    Vector3D position;
    public Color color;
    public double intensity;

    public PointLight(Vector3D position, Color color, double intensity){
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }
    Vector3D getPosition(){
        return position;
    };

    //TODO cook toorance model
    public Vector3D diffLight(Vector3D point, Object3D object){
        Vector3D normal = object.getNormalAt(point);
        Vector3D lightDir = position.subtract(point).normalize();

        //TODO distance quadratisch verrechnen?
        double distance = lightDir.length();

        double angle = Vector3D.dot(lightDir.normalize(), normal);

        Vector3D light = object.getColVec();
        //proportionalität zwischen angle und intensity
        //dir*normal = |dir|*|normal|*cos(angle)
        //objcetColor * (dir*normal)* intensity/dir.length^2
        light = light.scale(angle * (intensity / (distance * distance) + 1 ));


        //clamp values to RGB range
        double x = clamp(light.getX(), 0, 255);
        double y = clamp(light.getY(), 0, 255);
        double z = clamp(light.getZ(), 0, 255);

        return new Vector3D(x, y, z);
    }


    public Vector3D cookTorrance (Object3D object3D, Vector3D point, Ray ray){
        //D*F*G / 4*dot(N,L)*dot(N,V)
        //D = distribution function

        //D = N*H / (alpha^2 * (N*H)^2 - 1)^2

        //F = fresnel
        //F = F0 + (1 - F0) * (1 - dot(N,L))^5
        //G = geometry
        //G = min(1, 2 * dot(N,H) * dot(N,V) / dot(V,H), 2 * dot(N,H) * dot(N,L) / dot(V,H))

        //H = half vector
        //H = L + V / |L + V|
        //L = light vector
        //V = view vector
        //N = normal vector
        //alpha = roughness
        //F0 = fresnel reflectance at normal incidence
        //F0 = (n1 - n2 / n1 + n2)^2
        //n1 = index of refraction of medium the ray is coming from
        //n2 = index of refraction of medium the ray is entering
        //n1 = 1
        //n2 = 1.5
        //TODO implement
        double r = 0.5;
        //n? w?
        double D = Math.pow(r,2)/Math.PI*Math.pow((Math.pow(N*w)*(Math.pow(r,2)-1)+1),2);

        return null;
    }

}
