package ru.cft.focusstart.server;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void receiveMessage(Connection connection) throws IOException {
        Message message = ConverterMessage.toObjectMessage(connection.getIn().readLine());
        if (message instanceof MessageUserDisconnect) {
            usernameList.remove(message.getUserName());
            connectionList.remove(connection);
            connection.close();
            sendUpdateListOfUsers();
        }
        sendMessageToAllConnections(message);
    }

    @Override
    public void connectIsReady(Connection connection) throws IOException {
    }

    @Override
    public void checkUserName(Connection connection) throws IOException {
        MessageUserConnect initialMessage = (MessageUserConnect) connection.getMessage();
        if (!usernameList.contains(initialMessage.getUserName())) {
            connection.onConnection = true;
            usernameList.add(initialMessage.getUserName());
            connectionList.add(connection);
            sendMessageToAllConnections(initialMessage);
            sendUpdateListOfUsers();
        } else {
            ErrorMessage message = new ErrorMessage(LocalDateTime.now(), serverName, new IllegalArgumentException("This name is already taken"));
            System.out.println(Thread.currentThread());
            connection.sendMessage(ConverterMessage.toJSON(message));
        }
    }


    @Override
    public void getException(Connection connection) {
    }

    @Override
    public void disconnect(Connection connection) {
        connectionList.remove(connection);
    }

    private void sendUpdateListOfUsers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String sList = mapper.writeValueAsString(usernameList);
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