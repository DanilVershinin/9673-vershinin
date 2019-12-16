package ru.cft.focusstart.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.focusstart.common.Connection;
import ru.cft.focusstart.common.ConnectionListener;
import ru.cft.focusstart.common.message.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class Server implements ConnectionListener {
    private String serverName = "server";

    public static void main(String[] args) throws IOException {
        new Server();
    }

    private final ArrayList<Connection> connectionList = new ArrayList<>();
    private final ArrayList<String> usernameList = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private Server() throws IOException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getResourceAsStream("/server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        }
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")))) {
            while (true) {
                Connection connection = new Connection(this, serverSocket.accept());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void receiveMessage(Connection connection)  {
        try {
            Message message = ConverterMessage.toObjectMessage(connection.getIn().readLine());
            if (message instanceof MessageUserDisconnect) {
                disconnect(connection);
            }
            sendMessageToAllConnections(message);
        } catch (IOException e){
            getException(connection, e);
        }
    }

    @Override
    public void connectIsReady(Connection connection) {
    }

    @Override
    public void checkUserName(Connection connection) throws IOException {
        MessageUserConnect initialMessage = (MessageUserConnect) connection.getMessage();
        if (!usernameList.contains(initialMessage.getUserName())) {
            connection.onConnection = true;
            connection.setUsername(initialMessage.getUserName());
            usernameList.add(initialMessage.getUserName());
            connectionList.add(connection);
            sendMessageToAllConnections(initialMessage);
            sendUpdateListOfUsers();
        } else {
            ErrorMessage message = new ErrorMessage(LocalDateTime.now(), serverName, new IllegalArgumentException("This name is already taken"));
            connection.sendMessage(ConverterMessage.toJSON(message));
        }
    }


    @Override
    public void getException(Connection connection, Exception e) {
        log.error(e.getMessage());
        disconnect(connection);
    }

    @Override
    public void disconnect(Connection connection) {
        connectionList.remove(connection);
        usernameList.remove(connection.getUsername());
        connection.close();
        sendUpdateListOfUsers();
    }

    private void sendUpdateListOfUsers(){
        ObjectMapper mapper = new ObjectMapper();
        String sList = null;
        try {
            sList = mapper.writeValueAsString(usernameList);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        for (Connection connection : connectionList) {
            connection.sendMessage(sList);
        }
    }

    private void sendMessageToAllConnections(Message message) throws IOException {
        String sMessage = ConverterMessage.toJSON(message);
        for (Connection connection : connectionList) {
            connection.sendMessage(sMessage);
        }
    }

}