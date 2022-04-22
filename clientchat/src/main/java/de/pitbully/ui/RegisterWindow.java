package de.pitbully.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JFrame{
    private static JLabel loginLabel = new JLabel("User");
    private static JTextField loginField = new JTextField(20);
    private static JLabel passwordLabel = new JLabel("Password");
    private static JPasswordField passwordField = new JPasswordField(20);
    private static JLabel password2Label = new JLabel("Wiederholen");
    private static JPasswordField password2Field = new JPasswordField(20);
    private static JButton registerButton = new JButton("Register");
    public RegisterWindow() {
        super("register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
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
        password2Label.setBounds(10, 80, 80, 25);
        panel.add(password2Label);
        password2Field.setBounds(100, 80, 165, 25);
        panel.add(password2Field);
        registerButton.setBounds(10, 110, 90, 25);
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Register");
            }
        });
        getContentPane().add(panel);
    }
}
