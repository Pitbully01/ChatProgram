package de.pitbully.ui;


public class WindowManager {

    public WindowManager() {
    }

    public void login() {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }

    public void register() {
        RegisterWindow registerwindow = new RegisterWindow();
        registerwindow.setVisible(true);
    }
}
