package com.alekthehero.ece470.project1.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Room {
    private String name;
    private List<Device> devices = new LinkedList<>();

    public Room(String name) {
        this.name = name;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }

    public Device getDevice(UUID id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                return device;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" Room: ").append(name).append("  |  Devices: ").append(devices.size()).append("\n");
        return builder.toString();
    }
}
