package com.alekthehero.ece470.project1.datamodel;

import com.alekthehero.ece470.project1.data.Homes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RequestPacket {
    private String token;
    private RequestType requestType;
    private String name;
    private String password;
    private String homeName;
    private String roomName;
    private DeviceType deviceType;
    private short code;

    public Home getHome() {
        return Homes.getHome(homeName);
    }
    public Room getRoom() {
        return Homes.getHome(homeName).getRoom(roomName);
    }
    public Device getDevice() {
        List<Device> devices = Homes.getHome(homeName).getRoom(roomName).getDevices();
        for (Device device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }
}
