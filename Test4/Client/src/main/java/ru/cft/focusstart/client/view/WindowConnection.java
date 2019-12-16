package ru.cft.focusstart.client.view;

import ru.cft.focusstart.client.model.ViewObserver;

import javax.swing.*;
import java.awt.*;

public class WindowConnection extends JFrame {

    public WindowConnection(ViewObserver observer) {
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelForInput = new JPanel();
        add(panelForInput, BorderLayout.CENTER);
        panelForInput.setLayout(new BoxLayout(panelForInput, BoxLayout.Y_AXIS));

        JLabel labelIp = new JLabel("IP address:");
        labelIp.setAlignmentX(CENTER_ALIGNMENT);
        panelForInput.add(labelIp);
        JTextField ipField = new JTextField("127.0.0.1");
        panelForInput.add(ipField);

        JLabel labelPort = new JLabel("Port:");
        labelPort.setAlignmentX(CENTER_ALIGNMENT);
        panelForInput.add(labelPort);
        JTextField portField = new JTextField("8643");
        panelForInput.add(portField);

        JPanel panelForButton = new JPanel();
        add(panelForButton, BorderLayout.SOUTH);
        panelForButton.setLayout(new BoxLayout(panelForButton, BoxLayout.X_AXIS));

        JButton connectButton = new JButton("Connect");
        Dimension BUTTON_SIZE = new Dimension(50, 50);
        connectButton.setPreferredSize(BUTTON_SIZE);
        connectButton.addActionListener(e -> {
            if (observer.onConnect(ipField.getText(), Integer.parseInt(portField.getText())).equals("READY")) {
                dispose();
            } else {
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

}
