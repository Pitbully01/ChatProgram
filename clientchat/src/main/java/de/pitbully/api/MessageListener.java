package de.pitbully.api;

public interface MessageListener {
    public void onMessage(String fromLogin, String msgBody);
}
