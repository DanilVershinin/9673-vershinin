package ru.cft.focusstart.client;

import ru.cft.focusstart.common.Connection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WindowConnection extends JFrame {
    private Connection connection;
    private Dimension BUTTON_SIZE = new Dimension(50, 50);

    public WindowConnection(Window window){
//        JFrame currentWindow = new JFrame();
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelForInput = new JPanel();
        add(panelForInput, BorderLayout.CENTER);
        panelForInput.setLayout(new BoxLayout(panelForInput, BoxLayout.Y_AXIS));

        JLabel labelIp = new JLabel("IP address:");
        labelIp.setAlignmentX(panelForInput.CENTER_ALIGNMENT);
        panelForInput.add(labelIp);
        JTextField ipField = new JTextField("127.0.0.1");
        panelForInput.add(ipField);

        JLabel labelPort = new JLabel("Port:");
        labelPort.setAlignmentX(panelForInput.CENTER_ALIGNMENT);
        panelForInput.add(labelPort);
        JTextField portField = new JTextField("8643");
        panelForInput.add(portField);

        JPanel panelForButton = new JPanel();
        add(panelForButton, BorderLayout.SOUTH);
        panelForButton.setLayout(new BoxLayout(panelForButton, BoxLayout.X_AXIS));

        JButton connectButton = new JButton("Connect");
        connectButton.setPreferredSize(BUTTON_SIZE);
        connectButton.addActionListener(e -> {
            try {
                connection = new Connection(window, ipField.getText(), Integer.parseInt(portField.getText()));
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Connection isn't set. Try again");
            }
        });
        panelForButton.add(connectButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(BUTTON_SIZE);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        panelForButton.add(exitButton);

        setResizable(false);
        setVisible(true);
    }

    public Connection getConnection() {
        return connection;
    }
}
