/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pubblcitarizzi;

import java.io.IOException;
import telegramAPI.GestisciMetodi;

/**
 *
 * @author rizzi_edoardo
 */
public class ThreadElabora extends Thread{
    private GestisciMetodi gm = new GestisciMetodi();
    
    private int LastMessageID;

    
    public ThreadElabora() {
        this.LastMessageID = 0;
    }
    
    public void Run() throws IOException{
        while(true){
            gm.myGetUpdate();
            if(){
                
            }
        }
    }
    
    
    
    
    
    
    
}
