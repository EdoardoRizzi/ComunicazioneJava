/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telegramAPI;

/**
 *
 * @author 39334
 */
public class Persona {

    private String IdChat, First_Name, Indirizzo;
    private int IdMessage;
    private Luogo Coordinate = new Luogo();

    public Persona() {
    }

    public Persona(Persona p) {
        this.IdChat = p.getIdChat();
        this.First_Name = p.getFirst_Name();
        this.Coordinate.setLat(p.getLat());
        this.Coordinate.setLon(p.getLon());
    }

    public Persona(String IdChat, String First_Name, Luogo l) {
        this.IdChat = IdChat;
        this.First_Name = First_Name;
        this.Coordinate.setLat(l.getLat());
        this.Coordinate.setLon(l.getLon());
    }

    public Persona(String IdChat, String First_Name, int IdMessage, Luogo l) {
        this.IdChat = IdChat;
        this.First_Name = First_Name;
        this.IdMessage = IdMessage;
        this.Coordinate.setLat(l.getLat());
        this.Coordinate.setLon(l.getLon());
    }
    
    public void setLuogo(Luogo l){
        this.Coordinate.setLat(l.getLat());
        this.Coordinate.setLon(l.getLon());
    }

    public String getIdChat() {
        return IdChat;
    }

    public void setIdChat(String IdChat) {
        this.IdChat = IdChat;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = First_Name;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String Indirizzo) {
        this.Indirizzo = Indirizzo;
    }

    public Double getLat() {
        return Coordinate.getLat();
    }

    public void setLat(Double lat) {
        this.Coordinate.setLat(lat);
    }

    public double getLon() {
        return Coordinate.getLon();
    }

    public void setLon(Double lon) {
        this.Coordinate.setLat(lon);
    }
    
    public Luogo getCoordinate(){
        return this.Coordinate;
    }

    @Override
    public String toString() {
        String s = "Nome:" + First_Name + "-IdChat:" + IdChat + "-Indirizzo:" + Indirizzo + "-Latitudine:" + Double.toString(Coordinate.getLat()) + "-Longitudine:" + Double.toString(Coordinate.getLon());
        return s;
    }

    public String toCSV() {
        String s = First_Name + ";" + IdChat + ";" + Double.toString(Coordinate.getLat()) + ";" + Double.toString(Coordinate.getLon());
        return s;
    }
}
