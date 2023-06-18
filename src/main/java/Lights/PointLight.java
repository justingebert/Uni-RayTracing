package Lights;

import Objects.Object3D;
import math.Ray;
import math.Util;
import math.Vector3D;

import java.awt.*;

import static java.security.spec.RSAKeyGenParameterSpec.F0;
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

    public Vector3D getClampedColor(){
        return new Vector3D(clamp(color.getRed(), 0, 1), clamp(color.getGreen(), 0, 1), clamp(color.getBlue(), 0, 1));
    }

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


    public Vector3D cookTorranceLight (Vector3D point, Object3D object3D, Ray ray){
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




        Vector3D one = new Vector3D(1.0,1.0,1.0);
        Vector3D N = object3D.getNormalAt(point).normalize();
        Vector3D L = position.subtract(point).normalize();
        Vector3D V = ray.getDirection().scale(-1).normalize();

        Vector3D H = V.add(L).scale(0.5).normalize();

        Vector3D albedo = object3D.material.getAlbedo();
        Vector3D albedoG = new Vector3D(Math.pow(albedo.getX(),2.2), Math.pow(albedo.getY(),2.2), Math.pow(albedo.getZ(),2.2));
        double r = object3D.material.roughness;
        double m = object3D.material.metalness;
        Vector3D f0 =  albedo.scale(m).add(one.scale(0.04).scale(1-m));
        //Vector3D f0 = new Vector3D(0.04,0.04,0.04);

        double nv = Math.max(Vector3D.dot(N,V),0);
        double nh = Vector3D.dot(N,H);
        double nl = Math.max(Vector3D.dot(N,L),0);


        double r2 = Math.pow(r,2);
        double rHalf = r/2;
        double nh2 = Math.pow(nh,2);


        double D = Math.pow(r,2)/(Math.pow(Math.PI*(Math.pow(nh,2)*(Math.pow(r,2)-1)+1),2));
        double G = nv / ((nv * (1.0 - rHalf)) + rHalf) * nl / ((nl * (1.0 - rHalf)) + rHalf);
        Vector3D F = f0.add(one.subtract(f0).scale(Math.pow(1 - nv,5)));


        //entgammern

        //Vector3D kS = F.scale(D*G);
      /*  double kSr = clamp(f.getX()*D*G, 0, 1);
        double kSg = clamp(f.getY()*D*G, 0, 1);
        double kSb = clamp(f.getZ()*D*G, 0, 1);

        Vector3D kS = new Vector3D(kSr, kSg, kSb);*/

        Vector3D kS = F.scale(D*G);
        Vector3D kD = one.subtract(kS).scale(1 - m);

        Vector3D l1 = this.getClampedColor().scale(intensity * Vector3D.dot(N,L));
        Vector3D l2 = kD.multiply(albedoG).add(kS);
        //System.out.println(l1);
        //System.out.println(l2);
        //Vector3D light = l2.multiply(l1);
        Vector3D light = kS;
        //System.out.println(light);
        //Vector3D light = this.getClampedColor().scale(D*G);

        //gammarisieren
        double rC = Math.pow(clamp(light.getX(), 0, 1),0.45)*255;
        double gC = Math.pow(clamp(light.getY(), 0, 1),0.45)*255;
        double bC = Math.pow(clamp(light.getZ(), 0, 1),0.45)*255;

        //double rC = clamp(light.getX(), 0, 1);
        //double gC = clamp(light.getY(), 0, 1);
        //double bC = clamp(light.getZ(), 0, 1);
        //System.out.println("rC: " + rC + " gC: " + gC + " bC: " + bC);
        //rC = Math.pow(rC,1)*255;
        //gC = Math.pow(gC,1)*255;
        //bC = Math.pow(bC,1)*255;

        //rC = clamp(rC, 0, 255);
        //gC = clamp(gC, 0, 255);
        //bC = clamp(bC, 0, 255);


        //System.out.println("rC: " + rC + " gC: " + gC + " bC: " + bC);
        return new Vector3D(rC, gC, bC);
    }

}
