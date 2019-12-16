package ru.cft.focusstart.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.client.view.ClientWindow;
import ru.cft.focusstart.client.view.ViewObserved;
import ru.cft.focusstart.client.view.WindowConnection;
import ru.cft.focusstart.client.view.WindowForEnterUserName;
import ru.cft.focusstart.common.Connection;
import ru.cft.focusstart.common.ConnectionListener;
import ru.cft.focusstart.common.message.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;

public class Model implements ConnectionListener, ViewObserver {
    private Connection connection;
    private String userName;
    private ViewObserved observed;

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    public static void main(String[] args) {
        new Model();
    }

    private Model(){
        new WindowConnection(this);
    }

    @Override
    public void receiveMessage(Connection connection) throws IOException {
        String str = connection.getIn().readLine();
        if (!str.contains("\"message\"")) {
            ObjectMapper mapper = new ObjectMapper();
            observed.updateUsernameList(mapper.readValue(str, String[].class));
        } else {
            Message message = ConverterMessage.toObjectMessage(str);
            observed.update(message);
        }
    }

    @Override
    public void connectIsReady(Connection connection) throws IOException {
        new WindowForEnterUserName(this);
    }

    @Override
    public void checkUserName(Connection connection) throws IOException, InterruptedException {
        Message answerFromServer = ConverterMessage.toObjectMessage(connection.getIn().readLine());
        if (!(answerFromServer instanceof ErrorMessage)) {
            connection.onConnection = true;
            this.observed = new ClientWindow(this);
            observed.update(answerFromServer);
        } else {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "This name already taken"));
            new WindowForEnterUserName(this);
        }
    }

    @Override
    public void getException(Connection connection, Exception e) {
        log.error(e.getMessage());
        connection.close();
    }

    @Override
    public void disconnect(Connection connection) {
        MessageUserDisconnect message = new MessageUserDisconnect(LocalDateTime.now(), userName);
        try {
            connection.sendMessage(ConverterMessage.toJSON(message));
        } catch (JsonProcessingException e) {
            getException(connection, e);
        }
        connection.close();
    }

    @Override
    public String onConnect(String ip, int port) {
        try {
            this.connection = new Connection(this, ip, port);
            return "READY";
        } catch (IOException e) {
            return "NOT READY";
        }
    }

    @Override
    public void onDisconnect() {
        disconnect(this.connection);
    }

    @Override
    public void sendUserName(String userName) {
        MessageUserConnect message = new MessageUserConnect(LocalDateTime.now(), userName);
        String str = null;
        try {
            str = ConverterMessage.toJSON(message);
        } catch (JsonProcessingException e) {
            getException(connection, e);
        }
        connection.sendMessage(str);
        this.userName = userName;
    }

    @Override
    public void sendMessage(String str) {
        SimpleMessage message = new SimpleMessage(str, LocalDateTime.now(), userName);
        try {
            String sMessage = ConverterMessage.toJSON(message);
            connection.sendMessage(sMessage);
        } catch (JsonProcessingException ex) {
            getException(connection, ex);
        }
    }
}
