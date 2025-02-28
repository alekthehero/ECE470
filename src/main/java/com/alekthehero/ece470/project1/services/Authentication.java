package com.alekthehero.ece470.project1.services;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.Home;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Authentication {
    static Logger logger = LoggerFactory.getLogger(Authentication.class.getName());

    public static boolean authenticate(String home, String token) {
        logger.info("Authenticating   home: " + home + " |  token: " + token);
        return Homes.getHome(home).getTokens().contains(token);
    }

    public static Optional<String> login(String home, String name, String password) {
        logger.info("Authenticating    home: " + home + " |  name: " + name + " | password: " + password);
        Home homeObj = Homes.getHome(home);
        if (homeObj != null && homeObj.getUsers().containsKey(name) &&
                homeObj.getUsers().get(name).equals(password)) {
            String token = name + password;
            homeObj.getTokens().add(token);
            return Optional.of(token);
        }
        return Optional.empty();
    }

    public static Optional<String> createAccount(String home,String name, String password) {
        logger.info("Creating account      home: " + home + " |  name: " + name + "   |  password: " + password);
        // Create a home for the user if one does not exist
        if (Homes.getHome(home) == null) {
            Homes.addHome(new Home(home));
        }
        Homes.getHome(home).getUsers().put(name, password);
        String token = name + password;
        Homes.getHome(home).getTokens().add(token);
        return Optional.of(token);
    }

    public static void logout(String home, String token) {
        logger.info("Logging out home " + home + " and token " + token);
        Homes.getHome(home).getTokens().remove(token);
    }
}
