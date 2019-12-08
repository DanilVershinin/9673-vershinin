package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageUserConnect extends SimpleMessage {

    @JsonCreator
    public MessageUserConnect(@JsonProperty(value = "time",required = true) LocalDateTime time,
                              @JsonProperty(value = "userName",required = true) String userName) {
        super("<" + userName +"> connect to chat", time, userName);
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(this.getTime()) +  ": <" + this.getUserName() +"> connect to chat";
    }
}
