package com.example.demo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "id")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpecifcUser.class, name = "1"),
})
public abstract class TestUser {
    String userName;
    String phoneNumber;
    String id;
}
