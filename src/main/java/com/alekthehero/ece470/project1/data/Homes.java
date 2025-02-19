package com.alekthehero.ece470.project1.data;

import com.alekthehero.ece470.project1.datamodel.Home;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Homes {
    @Getter
    private static final Map<String, Home> homes = new HashMap<>();

    public static void addHome(Home home) {
        homes.put(home.getName(), home);
    }

    public static Home getHome(String name) {
        return homes.get(name);
    }

    public static void removeHome(String name) {
        homes.remove(name);
    }
}
