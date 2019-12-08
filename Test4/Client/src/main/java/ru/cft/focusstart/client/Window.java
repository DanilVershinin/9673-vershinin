package ru.cft.focusstart.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.common.*;
import ru.cft.focusstart.common.message.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;

public class Window extends JFrame implements ConnectionListener {
    private Connection connection;
    private String userName;


    private JTextArea outputTextBox = new JTextArea(23, 49);
    private JTextField fieldForMessage = new JTextField("", 49);
    private JPanel panelForListUsers = new JPanel();
    private JList<Object> list;

    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        WindowConnection windowConnection = new WindowConnection(this);
        setSize(640, 480);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                disconnect(connection);
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
            SimpleMessage message = new SimpleMessage(str, LocalDateTime.now(), userName);
            try {
                String sMessage = ConverterMessage.toJSON(message);
                connection.sendMessage(sMessage);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }

        });
        panelForSendText.add(sendMessageButton);


        add(panelForListUsers, BorderLayout.EAST);
        String[] list1 = new String[]{"admin1", "admin2"};
        list = new JList<>(list1);
        list.setLayoutOrientation(JList.VERTICAL);
        panelForListUsers.add(list);

        setResizable(false);
        setVisible(false);
    }


    private synchronized void printMsg(Message message) {
        EventQueue.invokeLater(() -> {
            this.outputTextBox.append(message.toString() + "\n");
            this.outputTextBox.setCaretPosition(outputTextBox.getDocument().getLength());
        });
    }

    private synchronized void updateList(String[] newList) {
        SwingUtilities.invokeLater(() -> {
                    panelForListUsers.remove(list);
                    list = new JList<>(newList);
                    list.setLayoutOrientation(JList.VERTICAL);
                    panelForListUsers.add(list);
                    list.updateUI();
                }
        );
    }

    @Override
    public void receiveMessage(Connection connection) throws IOException {
        String str = connection.getIn().readLine();
        if (!str.contains("\"message\"")) {
            ObjectMapper mapper = new ObjectMapper();
            updateList(mapper.readValue(str, String[].class));
        } else {
            Message message = ConverterMessage.toObjectMessage(str);
            printMsg(message);
        }
    }

    @Override
    public void connectIsReady(Connection connection) throws IOException {
        new WindowForEnterUserName(this, connection);
    }

    @Override
    public void checkUserName(Connection connection) throws IOException, InterruptedException {
        Message answerFromServer = ConverterMessage.toObjectMessage(connection.getIn().readLine());
        if (!(answerFromServer instanceof ErrorMessage)) {
            connection.onConnection = true;
            setVisible(true);
            printMsg(answerFromServer);
        } else {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "This name already taken"));
            new WindowForEnterUserName(this, connection);
        }
    }

    @Override
    public void getException(Connection connection) throws IOException {
        Message str = connection.getMessage();
        printMsg(str);
    }

    @Override
    public void disconnect(Connection connection) {
        MessageUserDisconnect message = new MessageUserDisconnect(LocalDateTime.now(), userName);
        try {
            connection.sendMessage(ConverterMessage.toJSON(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
