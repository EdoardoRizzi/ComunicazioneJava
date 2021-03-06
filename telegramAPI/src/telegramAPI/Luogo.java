/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telegramAPI;

/**
 *
 * @author rizzi_edoardo
 */
public class Luogo {
    private double lat, lon; 

    public Luogo() {
        this.lat = 0.0;
        this.lon = 0.0;
    }
    
    public double CalcolaDistanza(Luogo l){
        double distanza = 0.0;
        
        distanza = Math.acos(Math.cos(Math.toRadians(90-this.lat))*Math.cos(Math.toRadians(90-l.lat))+
                   Math.sin(Math.toRadians(90-this.lat))*Math.sin(Math.toRadians(90-l.lat))*Math.cos(Math.toRadians(this.lon-l.lon)))*6371;
        
        return distanza;
    }

    public Luogo(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        String s = "";
        s = "Latitudine: " + Double.toString(lat) + "Longitudine: " + Double.toString(lon);
        return s;
    }
}
