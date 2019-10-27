package battleshipserver;  

/**
 * @author Christopher Brantley
 * @email ccbrantley@gtcc.edu
 * Last Updated : 10/20/2019
 */

import java.io.DataInputStream;

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
