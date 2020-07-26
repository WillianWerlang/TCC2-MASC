package org.example;

public class Pontos {
    int id;
    String lat;
    String lon;

    public Pontos(int i, String la, String lo){
        this.setId(i);
        this.setLat(la);
        this.setLon(lo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


}
