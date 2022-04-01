package de.pitbully;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private final int serverPort;
    private ArrayList<ServerWorker> workerList = new ArrayList<>();

    // Constructor for the server Instance
    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    // List of all connected
    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    // Overwrite the "run()" function because its needed for threads
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            // While loop handles the connections
            while(true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                // Start a new Thread for each client
                ServerWorker worker = new ServerWorker(this, clientSocket);

                // Adds client to online list
                workerList.add(worker);
                worker.start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Disconnect from the Clients
    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }
}
