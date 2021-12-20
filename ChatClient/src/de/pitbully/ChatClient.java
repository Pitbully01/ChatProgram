package de.pitbully;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    private ArrayList<UserStatusListener> userStatusListener = new ArrayList<>();
    private ArrayList<MessageListener> messageListener = new ArrayList<>();

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", 8818);
        client.addUserStatusListener(new UserStatusListener() {
            @Override
            public void online(String login) {
                System.out.println("ONLINE: " + login);
            }

            @Override
            public void offline(String login) {
                System.out.println("OFFLINE: " + login);

            }
        });

        client.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msgBody) {
                System.out.println("You got a Message from " + fromLogin + " ====> " + msgBody);
            }
        });
        if (!client.connect()) {
            System.err.println("connection failed.");
        } else {
            System.out.println("Connected successfull");
            if (client.login("guest", "guest")) {
                System.out.println("Login successful");

                client.msg("yee", "Hello, World!");
            } else {
                System.err.println("Login failed");
            }

            //client.logoff();
        }
    }

    private void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + " " + System.lineSeparator();
        serverOut.write(cmd.getBytes());
    }

    private boolean login(String login, String password) throws IOException {
        String cmd = "login " + login + " " + password + System.lineSeparator();
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Response Line: " + response + System.lineSeparator());
        if ("ok login".equalsIgnoreCase(response)) {
            startMessageReader();
            return true;
        } else {
            return false;
        }
    }

    private void logoff() throws IOException {
        String cmd = "logoff " + System.lineSeparator();
        serverOut.write(cmd.getBytes());
    }

    private void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    } else if("offline".equalsIgnoreCase(cmd)) {
                        handleOffline(tokens);
                    } else if("msg".equalsIgnoreCase(cmd)){
                        String[] tokensMsg = StringUtils.split(line, null, 3);
                        handleMessage(tokensMsg);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String[] tokensMsg) {
        String login = tokensMsg[1];
        String msgBody = tokensMsg[2];

        for(MessageListener listener : messageListener) {
            listener.onMessage(login, msgBody);
        }
    }

    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for(UserStatusListener listener : userStatusListener) {
            listener.offline(login);
        }
    }

    private void handleOnline(String[] tokens) {
        String login = tokens[1];
        for(UserStatusListener listener : userStatusListener) {
            listener.online(login);
        }
    }

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client Port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addUserStatusListener(UserStatusListener listener){
        userStatusListener.add(listener);
    }
    public void removeUserStatusListener(UserStatusListener listener){
        userStatusListener.remove(listener);
    }

    public void addMessageListener(MessageListener listener){
        messageListener.add(listener);
    }
    public void removeMessageListener(MessageListener listener) {
        messageListener.remove(listener);
    }
}
