package com.alekthehero.ece470.project1.data;

import com.alekthehero.ece470.project1.datamodel.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Homes {
    static Logger logger = LoggerFactory.getLogger(Homes.class);

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

    public static ResponsePacket GetAllHomes(RequestPacket packet) {
        logger.info("Getting all data for all homes");

        StringBuilder homeData = new StringBuilder();

        for (Home home : homes.values()) {
            try {
                homeData.append("Home: ").append(home.toString()).append("\n");

                // Add rooms data
                homeData.append(" Rooms:\n");
                for (Room room : home.getRooms()) {
                    homeData.append("- ").append(room.toString()).append("\n");

                    // Add devices in room
                    homeData.append("  Devices:\n");
                    for (Device device : room.getDevices()) {
                        homeData.append("  - ").append(device.toString()).append("\n");
                    }
                }
            } catch (Exception e) {
                logger.error("Error getting home data", e);
                return new ResponsePacket(ResponseType.FAILURE, "Failed to retrieve home data");
            }
        }
        return new ResponsePacket(ResponseType.SUCCESS, homeData.toString());
    }
}
