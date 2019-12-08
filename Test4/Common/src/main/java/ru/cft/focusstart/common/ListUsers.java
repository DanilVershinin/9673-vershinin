package ru.cft.focusstart.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.Arrays;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ListUsers.class, name = "ListUsers"),
})
public class ListUsers {

    private ArrayList<String> usernameList;

    @JsonCreator
    public ListUsers(@JsonProperty(value = "time", required = true) ArrayList<String> usernameList) {
        this.usernameList = usernameList;
    }

    @JsonCreator
    public ListUsers(@JsonProperty(value = "time", required = true) String[] usernameList) {
        this.usernameList = (ArrayList<String>) Arrays.asList(usernameList);
    }

    public String[] toArray() {
        return (String[]) usernameList.toArray();
    }
}
