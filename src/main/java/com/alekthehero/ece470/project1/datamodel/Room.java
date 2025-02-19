package com.alekthehero.ece470.project1.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
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
}
