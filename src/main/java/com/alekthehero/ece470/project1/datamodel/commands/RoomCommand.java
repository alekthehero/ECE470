package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.Room;

public class RoomCommand extends Command {
    public static void ProcessCommand(RequestPacket packet) {
        RoomCommand roomCommand = new RoomCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { roomCommand.create(packet); }
            case DELETE -> { roomCommand.delete(packet); }
        }
    }

    @Override
    public void toggle(RequestPacket packet) {}

    @Override
    public void create(RequestPacket packet) {
        packet.getHome().addRoom(new Room(packet.getName()));
    }

    @Override
    public void delete(RequestPacket packet) {
        packet.getHome().removeRoom(packet.getRoom());
    }
}
