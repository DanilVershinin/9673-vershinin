package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleMessage.class, name = "SimpleMessage"),
        @JsonSubTypes.Type(value = MessageUserConnect.class, name = "MessageUserConnect"),
        @JsonSubTypes.Type(value = MessageUserDisconnect.class, name = "MessageUserDisconnect"),
        @JsonSubTypes.Type(value = ErrorMessage.class, name = "ErrorMessage"),
        @JsonSubTypes.Type(value = InitialMessage.class, name = "InitialMessage"),
})
public interface Message {
    @Override
    String toString();
    String getMessage();
    LocalDateTime getTime();
    String getUserName();
}
