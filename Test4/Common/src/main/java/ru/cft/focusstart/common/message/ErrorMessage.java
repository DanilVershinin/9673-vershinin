package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ErrorMessage extends SimpleMessage {
    Exception exception;

    @JsonCreator
    public ErrorMessage(@JsonProperty(value = "time",required = true) LocalDateTime time,
                        @JsonProperty(value = "userName",required = true) String userName,
                        @JsonProperty(value = "exception",required = true) Exception exception) {
        super("Get Exception " + exception.getMessage(), time, userName);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
