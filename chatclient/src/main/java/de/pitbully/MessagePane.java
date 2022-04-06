package de.pitbully;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessagePane  extends JPanel implements MessageListener {

    private final ClientAPI clients;
    private final String logins;

    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField =new JTextField();

    public MessagePane(ClientAPI client, String login) {
        this.clients = client;
        this.logins = login;

        client.addMessageListener(this);

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText();
                    clients.msg(logins, text);
                    listModel.addElement("you: " + text);
                    inputField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        if (logins.equalsIgnoreCase(fromLogin)) {
            String line = fromLogin + ": " + msgBody;
            listModel.addElement(line);
        }
    }
}
