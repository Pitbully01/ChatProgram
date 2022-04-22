package de.pitbully.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class ClientApi {
    private final String serverAdress;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;
    private ArrayList<UserStatusListener> userStatusListener = new ArrayList<>();
    private ArrayList<MessageListener> messageListener = new ArrayList<>();

    public ClientApi(String serverAdress, int serverPort) {
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
    }
    
    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + " " + System.lineSeparator();
        serverOut.write(cmd.getBytes());
    }

    public boolean login(String login, String password) throws IOException {
        String passwordmd5 = pwToMd5(password);
        System.out.println("you are trying to login with " + login + " and the password: " +passwordmd5);
        String cmd = "login " + login + " " + passwordmd5 + System.lineSeparator();
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

    // encrypts login password with md5 hash
    public String pwToMd5(String password){
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public void logoff() throws IOException {
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

    public boolean connect() {
        try {
            this.socket = new Socket(serverAdress, serverPort);
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