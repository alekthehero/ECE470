package com.alekthehero.ece470.project1.data;

import com.alekthehero.ece470.project1.datamodel.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static ResponsePacket GetDetails(RequestPacket packet) {
        switch (packet.getDeviceType()) {
            case HOME -> {
                return GetAllHomes(packet);
            }
            case ROOM -> {
                return GetAllRooms(packet);
            }
            case null, default -> {
                if (packet.getDeviceType() != null) {
                    return GetAllDevices(packet);
                }
            }
        }
        return new ResponsePacket(ResponseType.FAILURE, "Invalid request type");
    }

    private static ResponsePacket GetAllDevices(RequestPacket packet) {
        StringBuilder deviceData = new StringBuilder();
        Home home = packet.getHome();

        for (Room room : home.getRooms()) {
            try {
                for (Device device : room.getDevices()) {
                    if (device.getType() == packet.getDeviceType()) {
                        deviceData.append(" - ").append(device).append("\n");
                        deviceData.append(" - ").append(room).append("\n");
                    }
                }
            } catch (Exception e) {
                logger.error("Error getting device data", e);
                return new ResponsePacket(ResponseType.FAILURE, "Failed to retrieve device data");
            }
        }
        return new ResponsePacket(ResponseType.SUCCESS, deviceData.toString());
    }

    private static ResponsePacket GetAllRooms(RequestPacket packet) {
        StringBuilder roomData = new StringBuilder();
        Home home = packet.getHome();

        for (Room room : home.getRooms()) {
            try {
                roomData.append("Room: ").append(room.toString()).append("\n");

                // Add devices in room
                roomData.append(" Devices:\n");
                for (Device device : room.getDevices()) {
                    roomData.append(" - ").append(device.toString()).append("\n");
                }
            } catch (Exception e) {
                logger.error("Error getting room data", e);
                return new ResponsePacket(ResponseType.FAILURE, "Failed to retrieve room data");
            }
        }
        return new ResponsePacket(ResponseType.SUCCESS, roomData.toString());
    }

    private static ResponsePacket GetAllHomes(RequestPacket packet) {
        StringBuilder homeData = new StringBuilder();

        for (Home home : homes.values()) {
            try {
                homeData.append("Home: ").append(home.toString()).append("\n");

                // Add rooms data
                homeData.append("   Rooms:\n");
                for (Room room : home.getRooms()) {
                    homeData.append("- ").append(room.toString()).append("\n");

                    // Add devices in room
                    homeData.append("   Devices:\n");
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
