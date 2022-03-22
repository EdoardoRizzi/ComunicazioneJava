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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 39334
 */
public class GestisciFile {

    public GestisciFile() {
    }

    public void VetPersonetoCSV(List<Persona> VetPersone) {
        try {

            FileWriter fw = new FileWriter("Persone.csv");

            for (int i = 0; i < VetPersone.size(); i++) {
                String p = VetPersone.get(i).toCSV() + "\r\n";
                fw.write(p);
            }

            fw.flush();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(GestisciMetodi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Persona> CaricaVetPersonaFromCSV() {
        List<Persona> VetPersone = new ArrayList<Persona>();
        
        try {
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
            Logger.getLogger(GestisciMetodi.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(GestisciMetodi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        return VetPersone;
    }
    
      public File ScriviSuFile(String urlParziale, String NomeFile) throws MalformedURLException, IOException {
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

    public String LeggiDaFile(File f) throws FileNotFoundException, IOException {
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
