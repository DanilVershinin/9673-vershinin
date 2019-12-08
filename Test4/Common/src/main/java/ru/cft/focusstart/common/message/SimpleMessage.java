package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SimpleMessage implements Message {
    private String message;
    private LocalDateTime time;
    private String userName;

    @JsonCreator
    public SimpleMessage(@JsonProperty(value = "message", required = true) String message,
                         @JsonProperty(value = "time", required = true) LocalDateTime time,
                         @JsonProperty(value = "userName", required = true) String userName) {
        this.message = message;
        this.time = time;
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(time) + " <" + userName + ">: " + message + "\n";
    }
}
