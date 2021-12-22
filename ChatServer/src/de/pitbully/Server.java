package de.pitbully;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private final int serverPort;
    private ArrayList<ServerWorker> workerList = new ArrayList<>();

    // Constructor for the server instance
    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    // list of all connected
    public List<ServerWorker> getWorkerList() {
        return workerList;
    }

    // override the "run()" function because its needed for threads
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            // while loop handles the connections
            while(true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                // Start a new Thread for each
                ServerWorker worker = new ServerWorker(this, clientSocket);

                // adds client to online list
                workerList.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Disconect from the clients
    public void removeWorker(ServerWorker serverWorker) {
       workerList.remove(serverWorker);
    }
}

// die kurze tilt attacke @tbnnnnnnnnnnnnnnn
// bnhgjjb gvhjbh ngvjbh jngkbh jgnv bhjnvgb hgjvn bvghb zjub hjvgnhjb knmgvb hnvgjbhj nmgvbh jnmvgbhgv jzunhgbjv znb hjngvmjbhk ugvnzmugvbzhj tfhbvg zjnubjnhk umjknhbu mhbujknz igvhjnbku iuhbizjgk nvbhuzj igknvzubhigj tnvgbzhvutj ghzjvbtu fnbhj vngmkghvbjz utfbhjn ukzgvmbhj vgmknbgvzh ujtnb hjvgknmbh jgvknzub hgvjzunkb hvngj