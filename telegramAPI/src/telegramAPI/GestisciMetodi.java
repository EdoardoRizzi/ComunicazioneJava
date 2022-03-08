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
import java.util.Scanner;
import org.json.*;


/**
 *
 * @author 39334
 */
public class GestisciMetodi {

    private String urlBase = "https://api.telegram.org/bot5232168151:AAFh9DG1YcHKKmvHE9_kigEyjdoy7tKZwq4/";
    private JSONArray VetMessaggi;

    public void myGetUpdate() throws MalformedURLException, IOException {

        String urlParziale = urlBase + "getUpdates";
        //trascrivo la risposta su un file
        File DaLeggere = ScriviSuFile(urlParziale);
        //leggo il file e lo transformo in stringa
        String Response = LeggiDaFile(DaLeggere);
        //parso la stringa
        JSONObject obj = new JSONObject(Response);

        if (obj.getString("ok").equals("true")) {
           VetMessaggi = obj.getJSONArray("messaggi");
            
        }

    }

    private File ScriviSuFile(String urlParziale) throws MalformedURLException, IOException {
        URL url = new URL(urlParziale);
        Scanner sc = new Scanner(url.openStream());
        sc.useDelimiter("\u001a");

        File file = new File("prova.txt");
        FileWriter fw = new FileWriter(file);

        fw.write(sc.next());
        fw.flush();
        fw.close();

        return file;
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
}
