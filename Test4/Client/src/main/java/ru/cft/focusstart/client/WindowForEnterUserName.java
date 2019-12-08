package ru.cft.focusstart.client;

import ru.cft.focusstart.common.Connection;
import ru.cft.focusstart.common.message.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;

public class WindowForEnterUserName extends JFrame {
    private Dimension BUTTON_SIZE = new Dimension(50, 50);
    private String userName;

    public WindowForEnterUserName(Window window, Connection connection) {

        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelForInput = new JPanel();
        add(panelForInput, BorderLayout.CENTER);
        panelForInput.setLayout(new BoxLayout(panelForInput, BoxLayout.Y_AXIS));


        JLabel labelName = new JLabel("Username:");
        labelName.setAlignmentX(panelForInput.CENTER_ALIGNMENT);
        panelForInput.add(labelName);
        JTextField nameField = new JTextField("admin");
        panelForInput.add(nameField);

        JPanel panelForButton = new JPanel();
        add(panelForButton, BorderLayout.SOUTH);
        panelForButton.setLayout(new BoxLayout(panelForButton, BoxLayout.X_AXIS));

        JButton connectButton = new JButton("Enter");
        connectButton.setPreferredSize(BUTTON_SIZE);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                try {
                    window.setUserName(nameField.getText());
                    MessageUserConnect message = new MessageUserConnect(LocalDateTime.now(), nameField.getText());
                    String str = ConverterMessage.toJSON(message);
                    connection.sendMessage(str);
                    window.setConnection(connection);
                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Connection isn't set. Try again");
                }
            }
        });
        panelForButton.add(connectButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            connection.close();
            System.exit(0);
        });
        panelForButton.add(exitButton);

        setResizable(false);
        setVisible(true);
    }

    public String getUserName() {
        return userName;
    }
}
