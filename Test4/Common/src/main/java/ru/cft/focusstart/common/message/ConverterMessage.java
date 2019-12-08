package ru.cft.focusstart.common.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.StringReader;

public class ConverterMessage {
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private enum CreatorMessage {
        SIMPLE_MESSAGE {
            @Override
            public Message createMessage(String typeMessage) throws JsonProcessingException {
                return mapper.readValue(typeMessage, SimpleMessage.class);
            }
        },
        MESSAGE_USER_CONNECT {
            @Override
            public Message createMessage(String typeMessage) throws JsonProcessingException {
                return mapper.readValue(typeMessage, MessageUserConnect.class);
            }
        },
        MESSAGE_USER_DISCONNECT {
            @Override
            public Message createMessage(String typeMessage) throws JsonProcessingException {
                return mapper.readValue(typeMessage, MessageUserDisconnect.class);
            }
        },
        MESSAGE_EXCEPTION {
            @Override
            public Message createMessage(String typeMessage) throws JsonProcessingException {
                return mapper.readValue(typeMessage, ErrorMessage.class);
            }
        },
        INITIAL_MESSAGE {
            @Override
            public Message createMessage(String typeMessage) throws JsonProcessingException {
                return mapper.readValue(typeMessage, InitialMessage.class);
            }
        },
        NULL_MESSAGE {
            @Override
            public Message createMessage(String typeMessage) {
                return new NullMessage();
            }
        };

        public abstract Message createMessage(String typeMessage) throws JsonProcessingException;
    }

    public static String toJSON(Message message) throws JsonProcessingException {
        return mapper.writeValueAsString(message);
    }

    public static Message toObjectMessage(String jsonString) throws IOException {
        String nameTypeOfMessage = mapper.readTree(new StringReader(jsonString)).get("@type").asText();
        switch (nameTypeOfMessage){
            case "SimpleMessage":
                return CreatorMessage.SIMPLE_MESSAGE.createMessage(jsonString);
            case "MessageUserConnect":
                return CreatorMessage.MESSAGE_USER_CONNECT.createMessage(jsonString);
            case "MessageUserDisconnect":
                return CreatorMessage.MESSAGE_USER_DISCONNECT.createMessage(jsonString);
            case "ErrorMessage":
                return CreatorMessage.MESSAGE_EXCEPTION.createMessage(jsonString);
            case "InitialMessage":
                return CreatorMessage.INITIAL_MESSAGE.createMessage(jsonString);
            default:
                return CreatorMessage.NULL_MESSAGE.createMessage(jsonString);
        }
    }

}
