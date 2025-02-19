package com.alekthehero.ece470.project1.datamodel;

import com.alekthehero.ece470.project1.datamodel.devices.Alarm;
import com.alekthehero.ece470.project1.datamodel.devices.Lock;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter @Setter @ToString
public class Home {
    private String name;
    private Alarm alarm;
    private List<Room> rooms = new LinkedList<>();
    private List<Lock> locks = new LinkedList<>();
    private Map<String, String> users = new HashMap<>();
    private List<String> tokens = new LinkedList<>();
    private User currentUser;

    public Home(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user.getPassword());
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoom(String name) {
        for (Room room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    public void addLock(Lock lock) {
        locks.add(lock);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }
}
