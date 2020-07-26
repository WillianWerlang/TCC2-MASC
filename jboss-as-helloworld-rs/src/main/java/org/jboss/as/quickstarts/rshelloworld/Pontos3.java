/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aaaaa.testerest;

/**
 *
 * @author marcelotelles
 */
public class Pontos3 {
    String id;
    String mail;
    String lat;
    String lon;
    String dd;

    Pontos3(String id, String mail, String la, String lo, String d){
        this.setId(id);
        this.setMail(mail);
        this.setLat(la);
        this.setLon(lo);
        this.setDd(d);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    
    
    
}
