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

    private String urlBase = "https://api.telegram.org/key/";
    private JSONArray VetMessaggi;
    private List<Persona> VetPersone = new ArrayList<Persona>();

    public void myGetUpdate() throws MalformedURLException, IOException {

        String urlParziale = urlBase + "getUpdates";
        //trascrivo la risposta su un file
        File DaLeggere = ScriviSuFile(urlParziale, "Messaggi.txt");
        //leggo il file e lo transformo in stringa
        String Response = LeggiDaFile(DaLeggere);
        //parso la stringa
        JSONObject obj = new JSONObject(Response);
        //VetMesssaggi contiene tutti i messaggi inviati al bot
        VetMessaggi = obj.getJSONArray("result");
    }

    public boolean mySendMessageAll(String msg) throws MalformedURLException, IOException {
        myFindAllId();
        String urlParziale = urlBase + "sendMessage";
        boolean Sent = false;

        for (int i = 0; i < VetPersone.size(); i++) {
            //costruisco i vari URL
            urlParziale += "?chat_id=" + VetPersone.get(i).getIdChat() + "&text=" + URLEncoder.encode(msg, StandardCharsets.UTF_8);
            System.out.println(urlParziale);
            URL url = new URL(urlParziale);
            Scanner sc = new Scanner(url.openStream());
            sc.useDelimiter("\u001a");
            Sent = true;
        }
        return Sent;
    }

    //trovo tutti gli ID delle chat che hanno scritto al bot
    public void myFindAllId() throws IOException {
        //sono sicuro che VetMessaggi si inizializzato
        myGetUpdate();
        //lista con tutti gli ID delle chat che hanno scritto al bot

        for (int i = 0; i < VetMessaggi.length(); i++) {
            JSONObject appoggio = new JSONObject(VetMessaggi.get(i).toString());
            JSONObject messaggio = appoggio.getJSONObject("message");
            JSONObject chat = messaggio.getJSONObject("chat");
            String ID = Integer.toString(chat.getInt("id"));
            String Nome = chat.getString("first_name");
            //controllo che l'id non sia già stato inserito, se manca lo aggiungo alla lista
            if (!VetPersone.contains(ID.toString())) {
                VetPersone.add(new Persona(ID, Nome, 0.0f, 0.0f));
            }
        }

        VetPersonetoCSV();
    }

    public void myGetLocation(String Indirizzo, String Id, String CAP) throws ParserConfigurationException, SAXException, IOException {
        //genero l'URL e scrivo il risultato su file
        String urlParziale = "https://nominatim.openstreetmap.org/search?q=" + URLEncoder.encode(Indirizzo, StandardCharsets.UTF_8) + "&format=xml&addressdetails=1";
        File RispostaSito = ScriviSuFile(urlParziale, "RispostaSito.txt");
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

        //ciclo la lista per trovare la persona con lo stesso ID passato
        myFindAllId();
        int i = 0;
        boolean trovato = false, capTrovato = false;
        while (!trovato && i < VetPersone.size()) {
            if (VetPersone.get(i).getIdChat().equals(Id)) {
                for (int j = 0; j < nodelist.getLength(); j++) {
                    node = (Element) nodelist.item(j);
                    String strcap = trovaCAP(node.getAttribute("display_name"));
                    if (CAP.equals(strcap)){
                        VetPersone.get(i).setLat(Float.parseFloat(node.getAttribute("lat")));
                        VetPersone.get(i).setLon(Float.parseFloat(node.getAttribute("lon")));
                        capTrovato = true;
                    }
                }
                trovato = true;
            }
        }
        if (capTrovato) {
            VetPersonetoCSV();
            System.out.println("Poszione trovata, CSV aggiornato");
        } else {
            System.out.println("Posizione non trovata");
        }
        
    }

    private String trovaCAP(String displayname) {
        String strCAP = "";
        if(displayname != null) {
            String[] elementi = displayname.split(",");
            strCAP = elementi[elementi.length - 2];
            strCAP = strCAP.substring(1);
            int i = Integer.parseInt(strCAP);
        }
        return strCAP;
    }

    private File ScriviSuFile(String urlParziale, String NomeFile) throws MalformedURLException, IOException {
        URL url = new URL(urlParziale);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");

        File f = new File(NomeFile);
        FileWriter fw = new FileWriter(f);

        fw.write(sc.next());
        fw.flush();
        fw.close();

        return f;
    }

    private String LeggiDaFile(File f) throws FileNotFoundException, IOException {
        //variabile di appoggio
        String jsString = "";
        BufferedReader br = new BufferedReader(new FileReader(f));

        String line = br.readLine();
        while (line != null) {
            jsString += line;
            line = br.readLine();
        }

        return jsString;
    }

    private void VetPersonetoCSV() {
        try {

            FileWriter fw = new FileWriter("Persone.csv");

            for (int i = 0; i < VetPersone.size(); i++) {
                String p = VetPersone.get(i).toCSV() + "\r\n";
                fw.write(p);
            }

            fw.flush();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(GestisciMetodi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void CaricaVetPersonaFromCSV() {
        try {

            VetPersone.clear();

            FileReader fr = new FileReader("Persone.csv");
            BufferedReader br = new BufferedReader(fr);

            String riga;
            while ((riga = br.readLine()) != null) {
                String[] elementi = riga.split(";");

                String id = elementi[0];
                String nome = elementi[1];
                Float lat = Float.parseFloat(elementi[2]);
                Float lon = Float.parseFloat(elementi[3]);

                Persona p = new Persona(id, nome, lat, lon);
                VetPersone.add(p);
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestisciMetodi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestisciMetodi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
