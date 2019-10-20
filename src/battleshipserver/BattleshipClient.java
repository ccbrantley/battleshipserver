package battleshipserver;

import java.io.*;
import java.net.*;

public class BattleshipClient {
    public static void main(String[] args) throws IOException {
        String[] newString = new String[2];
        // Put IP in newString[0]
        newString[0] = "###.###.#.##";
        newString[1] = "23";
        if (newString.length != 2) {
            System.err.println(
                "Usage: java EchoClient <Battleship> <23>");
            System.exit(1);
        }
        String hostName = newString[0];
        int portNumber = Integer.parseInt(newString[1]);
     try (
            Socket clientSocket = new Socket(hostName, portNumber);
            DataOutputStream out =
                new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream in =
                new DataInputStream(clientSocket.getInputStream());
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
         new Thread(new InputThread(in)).start();
         String tempString;
         do {
             if(((tempString) = stdIn.readLine()) != null) {
                 out.writeUTF(tempString);
             }
         } while (true);
     } catch (Exception e) {
         System.err.println(e.toString());
         System.exit(1);
     }
    }
}