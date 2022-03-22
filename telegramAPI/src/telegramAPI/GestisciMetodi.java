/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telegramAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 39334
 */
public class GestisciMetodi {

    private String urlBase;
    //vettore con tutti i messaggi inviati prelevati tramite GetUpdate
    private JSONArray VetMessaggi;
    //lista delle "Persone" che hanno scritto al bot
    private List<Persona> VetPersone;

    private GestisciFile gf;

    public GestisciMetodi() {
        urlBase = "https://api.telegram.org/mettilakeyS/";
        VetPersone = new ArrayList<Persona>();
        gf = new GestisciFile();
    }

    public void myGetUpdate() throws MalformedURLException, IOException {

        String urlParziale = urlBase + "getUpdates";
        //trascrivo la risposta su un file
        File DaLeggere = gf.ScriviSuFile(urlParziale, "Messaggi.txt");
        //leggo il file e lo transformo in stringa
        String Response = gf.LeggiDaFile(DaLeggere);
        //parso la stringa
        JSONObject obj = new JSONObject(Response);
        //VetMesssaggi contiene tutti i messaggi inviati al bot
        VetMessaggi = obj.getJSONArray("result");
    }

    //tolgo tutti i messaggi che non contengono il comando "/citta"
    public void myMsgCitta() {
        for (int i = 0; i < VetMessaggi.length(); i++) {

            JSONObject appoggio = new JSONObject(VetMessaggi.get(i).toString());
            JSONObject messaggio = appoggio.getJSONObject("message");

            if (!messaggio.getString("Text").contains("/citta")) {
                VetMessaggi.remove(i);
            }
        }
    }

    public boolean mySendMessageAll(String msg) throws MalformedURLException, IOException {
        gf.CaricaVetPersonaFromCSV();
        String urlParziale = urlBase + "sendMessage";
        boolean Sent = false;

        for (int i = 0; i < VetPersone.size(); i++) {
            //costruisco i vari URL
            urlParziale += "?chat_id=" + VetPersone.get(i).getIdChat() + "&text=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
            URL url = new URL(urlParziale);
            Scanner sc = new Scanner(url.openStream());
            sc.useDelimiter("\u001a");
            Sent = true;
        }
        return Sent;
    }

    public void mySendMessage(String msg, String idChat) throws MalformedURLException, IOException {
        String urlParziale = urlBase + "sendMessage";
        boolean Sent = false;

        //costruisco i vari URL
        urlParziale += "?chat_id=" + idChat + "&text=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
        URL url = new URL(urlParziale);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");
        Sent = true;

    }

    //trovo tuttii quelli che hanno scritto al bot
    public void myFindAll() throws ParserConfigurationException, SAXException, IOException {
        for (int i = 0; i < VetMessaggi.length(); i++) {
            //creo una variabilie di appoggio prelevando l'oggetto in posizione I presente in VetMessaggi
            JSONObject appoggio = new JSONObject(VetMessaggi.get(i).toString());
            JSONObject messaggio = appoggio.getJSONObject("message");
            String Indirizzo = messaggio.getString("Text");

            //tolgo "/citta " per avere l'inidirizzo 'pulito' (7 perché conto anche uno spazio)
            Indirizzo.substring(7);
            //trovo le coordinate relativo all'inidirizzo precendente
            float[] Coordinate = myGetLocation(Indirizzo);
            //ricavo gli altri dati necessari
            JSONObject chat = messaggio.getJSONObject("chat");
            String id_Chat = Integer.toString(chat.getInt("id"));
            String Nome = chat.getString("first_name");
            int id_Message = chat.getInt("message_id");

            VetPersone.add(new Persona(id_Chat, Nome, id_Message, Coordinate[0], Coordinate[1]));
        }

        //salvo tutti quelli che hanno scritto al bot su un file
        gf.VetPersonetoCSV(VetPersone);
    }

    //dato l'indirizzo ricava le coordinate
    public float[] myGetLocation(String Indirizzo) throws ParserConfigurationException, SAXException, IOException {
        float[] Coordinate = {0, 0};
        //genero l'URL e scrivo il risultato su file
        String urlParziale = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(Indirizzo, StandardCharsets.UTF_8) + "&format=xml&addressdetails=1";
        File RispostaSito = gf.ScriviSuFile(urlParziale, "RispostaSito.txt");
        System.out.println(urlParziale);
        //Parso il file XML scritto prima per ricavare gli oggetti
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, node;
        NodeList nodelist;

        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        document = builder.parse(RispostaSito);

        root = document.getDocumentElement();

        nodelist = root.getElementsByTagName("place");

        //prendo il primo risultato che mi viene restituito
        node = (Element) nodelist.item(0);
        Coordinate[0] = Float.parseFloat(node.getAttribute("lat"));
        Coordinate[1] = Float.parseFloat(node.getAttribute("lon"));

        return Coordinate;
    }

    public int CheckMessageID(int LastID) {
        int NewLastID = 0, i = 0;
        boolean trovato = false;

        while (i < VetMessaggi.length() && !trovato) {
            JSONObject appoggio = new JSONObject(VetMessaggi.get(i).toString());
            int id_Chat = getID(appoggio);

            if (LastID < id_Chat) {
                NewLastID = id_Chat;
            } else {
                VetMessaggi.remove(i);
            }
        }

        return NewLastID;
    }

    private int getID(JSONObject obj) {
        JSONObject messaggio = obj.getJSONObject("message");
        int id_Message = messaggio.getInt("message_id");
        return id_Message;
    }
}
