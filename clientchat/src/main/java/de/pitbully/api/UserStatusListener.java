package de.pitbully.api;

public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}
