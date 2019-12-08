package ru.cft.focusstart.common.message;

import java.time.LocalDateTime;

public class NullMessage extends SimpleMessage {
    public NullMessage(){
        super("NUll", LocalDateTime.now(), "NULL");
    }
}
