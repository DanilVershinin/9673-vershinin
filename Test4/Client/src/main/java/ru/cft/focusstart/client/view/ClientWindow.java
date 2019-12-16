package ru.cft.focusstart.client.view;

import ru.cft.focusstart.client.model.ViewObserver;
import ru.cft.focusstart.common.message.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientWindow extends JFrame implements ViewObserved {
    private JTextArea outputTextBox = new JTextArea(23, 49);
    private JTextField fieldForMessage = new JTextField("", 49);
    private JPanel panelForListUsers = new JPanel();
    private JList<Object> list;

    public ClientWindow(ViewObserver observer) {
        setSize(640, 480);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                observer.onDisconnect();
                dispose();
                System.exit(0);
            }
        });

        JPanel panelForOutputTest = new JPanel();
        add(panelForOutputTest, BorderLayout.CENTER);
        this.outputTextBox.setSize(600, 400);
        this.outputTextBox.setLineWrap(true);
        this.outputTextBox.setEditable(false);
        panelForOutputTest.add(new JScrollPane(outputTextBox));

        JPanel panelForSendText = new JPanel();
        add(panelForSendText, BorderLayout.SOUTH);
        panelForSendText.setLayout(new BoxLayout(panelForSendText, BoxLayout.X_AXIS));

        panelForSendText.add(fieldForMessage);

        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setPreferredSize(new Dimension(75, 50));
        sendMessageButton.addActionListener(e -> {
            String str = fieldForMessage.getText();
            if (str.equals("")) {
                return;
            }
            fieldForMessage.setText("");
            observer.sendMessage(str);
        });
        panelForSendText.add(sendMessageButton);


        add(panelForListUsers, BorderLayout.EAST);
        String[] list1 = new String[]{"admin1", "admin2"};
        list = new JList<>(list1);
        list.setLayoutOrientation(JList.VERTICAL);
        panelForListUsers.add(list);

        setResizable(false);
        setVisible(true);
    }


    private synchronized void printMsg(Message message) {
        this.outputTextBox.append(message.toString() + "\n");
        this.outputTextBox.setCaretPosition(outputTextBox.getDocument().getLength());

    }

    private synchronized void updateList(String[] newList) {
        panelForListUsers.remove(list);
        list = new JList<>(newList);
        list.setLayoutOrientation(JList.VERTICAL);
        panelForListUsers.add(list);
        list.updateUI();

    }

    @Override
    public void update(Message update) {
        printMsg(update);
    }

    @Override
    public void updateUsernameList(String[] update) {
        updateList(update);
    }
}
