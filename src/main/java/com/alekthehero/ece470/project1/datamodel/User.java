package com.alekthehero.ece470.project1.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class User {
    private String username;
    private String password;
    private UUID id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = UUID.nameUUIDFromBytes(username.getBytes());
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
