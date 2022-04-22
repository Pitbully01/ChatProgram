package de.pitbully.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame{
    private static JLabel loginLabel = new JLabel("User");
    private static JTextField loginField = new JTextField(20);
    private static JLabel passwordLabel = new JLabel("Password");
    private static JPasswordField passwordField = new JPasswordField(20);
    private static JButton loginButton = new JButton("Login");
    private static JButton registerButton = new JButton("Registrieren");
    public LoginWindow() {
        super("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);
        loginLabel.setBounds(10, 20, 80, 25);
        panel.add(loginLabel);
        loginField.setBounds(100, 20, 165, 25);
        panel.add(loginField);
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login with ");
            }
        });
        registerButton.setBounds(100, 80, 110, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager winMan = new WindowManager();
                winMan.register();
            }
        });
        getContentPane().add(panel);
    }
}