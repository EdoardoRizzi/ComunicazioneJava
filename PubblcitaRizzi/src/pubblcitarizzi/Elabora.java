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
import telegramAPI.GestisciMetodi;

/**
 *
 * @author 39334
 */
public class Elabora extends Thread {

    private GestisciMetodi gm;
    private int LastID;

    public Elabora() {
        gm = new GestisciMetodi();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //controllo se arrivano nuovi messaggi
                gm.myGetUpdate();
            } catch (IOException ex) {
                Logger.getLogger(Elabora.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //controllo se ci sono messaggi "/citta"
            gm.myMsgCitta();
            
            
            
        }
    }

}
