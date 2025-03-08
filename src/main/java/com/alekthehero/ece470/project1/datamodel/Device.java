package com.alekthehero.ece470.project1.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public abstract class Device {
    private UUID id;
    private String name;
    private boolean isOn;
    private DeviceType type;

    public Device(String name) {
        this.id = UUID.nameUUIDFromBytes(name.getBytes());
        this.name = name;
        this.isOn = false;
    }

    public abstract void turnOn();
    public abstract void turnOff();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Device: ").append(name).append("\n");
        builder.append("     \\ Type: ").append(type).append("\n");
        builder.append("     \\ State: ").append(isOn ? "On" : "Off").append("\n");
        return builder.toString();
    }
}
