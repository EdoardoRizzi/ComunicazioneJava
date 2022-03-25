/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pubblcitarizzi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import telegramAPI.*;

/**
 *
 * @author 39334
 */
public class Elabora extends Thread {

    private GestisciMetodi gm;
    private GestisciFile gf;
    private int LastID;

    public Elabora() {
        gm = new GestisciMetodi();
        gf = new GestisciFile();
    }

    @Override
    public void run() {
        //gm.CaricaVetPersone(gf.CaricaVetPersonaFromCSV("Persone.csv"));
        while (true) {
            try {
                //controllo se arrivano nuovi messaggi
                gm.myGetUpdate();
            } catch (IOException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            }

            //controllo se ci sono messaggi "/citta"
            gm.myMsgCitta();

            try {
                //prendo il 'contatto' di chi ha inserito la citta e li salvo su file
                gm.myFindAll();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(100000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
