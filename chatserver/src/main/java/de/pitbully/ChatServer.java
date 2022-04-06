package de.pitbully;

public class ChatServer 
{
    public static void main(String[] args) {
        
        // Set the port the server is running with
        int port = 8818;

        // Start the server instance
        Server server = new Server(port);
        server.start();
    }
}
