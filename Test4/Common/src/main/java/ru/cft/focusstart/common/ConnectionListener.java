package ru.cft.focusstart.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.cft.focusstart.common.message.Message;

import java.io.IOException;

public interface ConnectionListener {
    void receiveMessage(Connection connection) throws IOException;

    void connectIsReady(Connection connection) throws IOException;

    void checkUserName(Connection connection) throws IOException, InterruptedException;

    void getException(Connection connection, Exception e);

    void disconnect(Connection connection);
}
