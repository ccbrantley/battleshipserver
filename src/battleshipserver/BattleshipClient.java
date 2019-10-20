package battleshipserver;

import java.io.*;
import java.net.*;

public class BattleshipClient {
    public static void main(String[] args) throws IOException {
        String[] newString = new String[2];
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
            Socket echoSocket = new Socket(hostName, portNumber);
            DataOutputStream out =
                new DataOutputStream(echoSocket.getOutputStream());
            DataInputStream in =
                new DataInputStream(echoSocket.getInputStream());
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
     } catch (UnknownHostException e) {
         System.err.println("Don't know about host " + hostName);
         System.exit(1);
     } catch (IOException e) {
         System.err.println("Couldn't get I/O for the connection to " + hostName);
         System.exit(1);
     }
     System.out.println("do we ever exit program in here");
    }
}
