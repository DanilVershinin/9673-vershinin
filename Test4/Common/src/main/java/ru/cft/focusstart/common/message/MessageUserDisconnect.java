package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageUserDisconnect extends SimpleMessage {

    @JsonCreator
    public MessageUserDisconnect(@JsonProperty(value = "time",required = true) LocalDateTime time,
                                 @JsonProperty(value = "userName",required = true) String userName) {
        super("<" + userName + "> disconnect from chat", time, userName);
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(this.getTime()) +  ": <" + this.getUserName() +"> disconnect from chat";
    }
}
