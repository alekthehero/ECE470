package com.alekthehero.ece470.project1.services;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.Home;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Authentication {

    public static boolean authenticate(String home, String token) {
        return Homes.getHome(home).getTokens().contains(token);
    }

    public static void login(String home, String name, String password) {
        Homes.getHome(home).getUsers().forEach((key, value) -> {
            if (key.equals(name) && value.equals(password)) {
                String token = name + password;
                Homes.getHome(home).getTokens().add(token);
                /// SEND TOKEN BACK TO CLIENT
            }
        });

    }

    public static void createAccount(String home,String name, String password) {
        // Create a home for the user if one does not exist
        if (Homes.getHome(home) == null) {
            Homes.addHome(new Home(home));
        }
        Homes.getHome(home).getUsers().put(name, password);
        String token = name + password;
        Homes.getHome(home).getTokens().add(token);
        /// SEND TOKEN BACK TO CLIENT;
    }

    public static void logout(String home, String token) {
        Homes.getHome(home).getTokens().remove(token);
    }
}
