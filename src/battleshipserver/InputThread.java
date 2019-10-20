/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipserver;

import java.io.DataInputStream;

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
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }
}
