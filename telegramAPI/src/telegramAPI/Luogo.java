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
    
    
    
}
