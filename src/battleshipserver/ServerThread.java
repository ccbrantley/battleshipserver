package battleshipserver;

/**
 * @author Christopher Brantley
 * @email ccbrantley@gtcc.edu
 * Last Updated : 10/20/2019
 */

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread{

    private Socket socket = null;
    private final DataInputStream thisInput;
    private final DataOutputStream thisOutput;

    private Socket pairedSocket;
    private DataInputStream pairedInput;
    private DataOutputStream pairedOutput;

    private boolean isPaired = false;

    public ServerThread(Socket socket) throws IOException {
        super("ServerThread");
        this.socket = socket;
        System.out.println(this.socket.getLocalAddress());
        this.thisOutput = new DataOutputStream(this.socket.getOutputStream());
        this.thisInput = new DataInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {
        while(true){
            if (this.isPaired){
                String tempString;
                if(null != (tempString = this.readInputStream(thisInput))) {
                    this.writeOutputStream(this.pairedOutput, tempString);
                    this.sleepThread1000();
                }
            }
            else {
                this.writeOutputStream(this.thisOutput, "Waiting for Connection.");
                this.writeOutputStream(this.thisOutput, "...");
                this.sleepThread1000();
            }
        }
    }

    private void sleepThread1000() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeOutputStream(DataOutputStream _outputStream, String _outputLine) {
        try {
            _outputStream.writeUTF(_outputLine);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readInputStream(DataInputStream _inputStream) {
        String tempString = "";
        try {
             tempString = _inputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempString;
    }

//*********************** GETTERS **********************

    public boolean getMultiplayerStatus () {
        return this.isPaired;
    }

//*********************** SETTERS **********************

    public void setPairedThread(ServerThread _pairedThread) throws IOException {
        this.pairedSocket = _pairedThread.socket;
        this.pairedInput = new DataInputStream(this.pairedSocket.getInputStream());
        this.pairedOutput = new DataOutputStream(this.pairedSocket.getOutputStream());
        this.isPaired = true;
        this.writeOutputStream(this.thisOutput, "Connected");
    }
}
