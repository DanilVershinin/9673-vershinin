package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class InitialMessage extends SimpleMessage {
    @JsonCreator
    public InitialMessage(@JsonProperty(value = "userName",required = true) String name){
        super("", LocalDateTime.now(), name);
    }
}
