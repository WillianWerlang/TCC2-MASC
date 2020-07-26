package org.example;

public class Pontos2 {
    String nome;
    String lat;
    String lon;
    String dd;

    Pontos2(String nome, String la, String lo, String d){
        this.setNome(nome);
        this.setLat(la);
        this.setLon(lo);
        this.setDd(d);
    }

    public String getDd() {
        return dd;
    }

    public String getId() {
        return nome+" "+dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
