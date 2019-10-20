package battleshipserver;


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Set;
public class BattleshipServer {
    public static void main(String[] args) throws IOException {
    ArrayList<ServerThread> serverThreads = new ArrayList();
    Set<Thread> systemThreads;
    if (args.length != 1) {
        System.err.println("Usage: BattleShip <23>");
        System.exit(1);
    }
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new ServerThread(serverSocket.accept()).start();
                systemThreads = ServerThread.getAllStackTraces().keySet();
                systemThreads.forEach(child -> {
                    if((child.getName().equals("ServerThread"))) {
                        ServerThread serverThread = (ServerThread)child;
                        if(!(serverThreads.contains(serverThread))) {
                            serverThreads.add(serverThread);
                        }
                    }
                });
                if(serverThreads.size() > 1) {
                    int lastThreadIndex = serverThreads.size()-2;
                    int curThreadIndex = serverThreads.size()-1;
                    ServerThread lastThread = serverThreads.get(lastThreadIndex);
                    ServerThread curThread = serverThreads.get(curThreadIndex);
                    if(lastThread.multiplayerStatus() == false && curThread.multiplayerStatus() == false) {
                        lastThread.setPairedThread(curThread);
                        curThread.setPairedThread(lastThread);
                    }
                }
                System.out.println("Active Count: " + (ServerThread.activeCount()-1));
                serverThreads.forEach((ServerThread child) -> {
                    System.out.println("Paired Status: " + child.multiplayerStatus());
                });
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
