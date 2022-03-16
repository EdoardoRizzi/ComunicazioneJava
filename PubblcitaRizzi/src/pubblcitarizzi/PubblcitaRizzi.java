/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pubblcitarizzi;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import telegramAPI.*;

/**
 *
 * @author rizzi_edoardo
 */
public class PubblcitaRizzi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, MalformedURLException, ParserConfigurationException, SAXException {
        // TODO code application logic here
        GestisciMetodi gm = new GestisciMetodi();
        
        if(gm.mySendMessageAll("funziona?")){
            System.out.println("messaggio inviato correttamente");
        } else {
            System.out.println("errore nel invio del messaggio");
        }
        
        gm.myGetLocation("Via Mazzini 12", "571591485", "34121");
    }
    
}
