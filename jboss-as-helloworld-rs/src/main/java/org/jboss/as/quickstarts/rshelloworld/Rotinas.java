/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aaaaa.utilidades;

import aaaaa.testerest.Pontos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author marcelotelles
 */
public class Rotinas {

    //Coordinate[-29.759615772705885, -51.141354739665985] 1
    //Coordinate[-29.769159705540563, -51.14135205745697]  2
    //Coordinate[-29.76920161395053, -51.15232229232788]   3
    double lat;
    double lon;
    double lat1;
    double lon1;
    double latCentro;
    double lonCentro;
    
    int raio;
    
    public ArrayList<Pontos> pontos;
    public Pontos p;

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }
    
    

    public String getLat() {
        return String.valueOf(lat);
    }

    public String getLon() {
        return String.valueOf(lon);
    }

    public String getLat1() {
        return String.valueOf(lat1);
    }

    public String getLon1() {
        return String.valueOf(lon1);
    }

    public double getLatCentro() {
        return latCentro;
    }

    public void setLatCentro(double latCentro) {
        this.latCentro = latCentro;
    }

    public double getLonCentro() {
        return lonCentro;
    }

    public void setLonCentro(double lonCentro) {
        this.lonCentro = lonCentro;
    }
    
    
    
    
    public double distancia(double la1,double lo1,double la2,double lo2){
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
    
    public void ponto(double la1,double lo1,int raio){
        //o raio da terra (em Km) é armazenado na variável rt
        double lat_, lon_;
        lat_ = la1+0.001;
        lon_ = lo1+0.001;
        
        while (raio>distancia(la1, lo1, lat_, lon_)){
            lat_ = lat_+0.001;
            lon_ = lon_+0.001;     
        }
        this.lat=lat_;
        this.lon=lon_;  
    }
    public void ponto2(double la1,double lo1,int raio){
        //o raio da terra (em Km) é armazenado na variável rt
        double lat_, lon_;
        lat_ = la1-0.001;
        lon_ = lo1-0.001;
        
        while (raio>distancia(la1, lo1, lat_, lon_)){
            lat_ = lat_-0.001;
            lon_ = lon_-0.001;     
        }
        this.lat1=lat_;
        this.lon1=lon_;  
    }
    
    public void ps(){
        pontos = new ArrayList<Pontos>();
        int q=0;
        Random r;
        r = new Random();
        
        while(q<30){
            
            //rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            //lat=r.nextDouble()-51.15232229232788;
            //lon=r.nextDouble()-29.76920161395053;
            
            lat = -29.769159705540563+(-29.759615772705885 - (-29.769159705540563))*r.nextDouble();
            lon = -51.15232229232788+(-51.141354739665985 - (-51.15232229232788))*r.nextDouble();
            System.out.println("lat"+lat);
            System.out.println("lon"+lon);
            
            double dd = distancia(this.getLatCentro(), this.getLonCentro(), lat, lon);
            int ddd;
            ddd=(int) dd;
            System.out.println("ddd="+ddd);
            System.out.println("RRR="+this.getRaio());
            if ( (ddd<(this.getRaio()+5)) && (ddd>(this.getRaio()-5)) ){
               
                p=new Pontos(q,String.valueOf(lat),String.valueOf(lon));
                System.out.println(q+"............................");
                pontos.add(p);
                q++;
            }
        }
        ComparaDadosPorLatitude cpn;
        cpn = new ComparaDadosPorLatitude();
        Collections.sort(pontos, cpn);
        
        ComparaDadosPorLongitude cpn2;
        cpn2 = new ComparaDadosPorLongitude();
        Collections.sort(pontos, cpn2);

        
    }
    
    
    
    
    
}
