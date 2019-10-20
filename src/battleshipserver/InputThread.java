/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class InputThread implements Runnable {

    public InputThread(DataInputStream _inputStream) {
        this.inputStream = _inputStream;
    }

    DataInputStream inputStream;

    @Override
    public void run() {
        try{
            String tempString;
            while((tempString = this.inputStream.readUTF()) != null) {
                System.out.println(tempString);
            }
        } catch (IOException ex) {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
