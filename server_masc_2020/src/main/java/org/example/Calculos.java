package org.example;

public class Calculos {
    boolean verificaDistancia(double distancia, double lat, double lon, double lat2, double lon2){
        double distanciaCalculada;
        distanciaCalculada=distancia(lat,lon,lat2,lon2);
        System.out.println("Distancia =" +distanciaCalculada);
        if(distanciaCalculada<distancia)
            return true;
        else
            return false;
    }
    public static double distancia(double la1,double lo1,double la2,double lo2){
        //o raio da terra (em Km) é armazenado na variável rt
        int rt = 6371;
        double PI = Math.PI;
        int valorMetade = 90;
        double v1 = Math.cos(PI * (valorMetade - la2) / 180);
        double v2 = Math.cos((valorMetade - la1) * PI / 180);
        double v3 = Math.sin((valorMetade - la2) * PI / 180);
        double v4 = Math.sin((valorMetade - la1) * PI / 180);
        double v5 = Math.cos((lo1 - lo2) * PI / 180);
        double result = rt * Math.acos((v1 * v2) + (v3 * v4 * v5));
        //apresenta o resultado em metros, antes
        return result*1000;
    }
}
