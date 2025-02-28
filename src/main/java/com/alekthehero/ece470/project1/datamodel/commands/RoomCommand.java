package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.Room;
import org.slf4j.Logger;

public class RoomCommand extends Command {
    Logger logger = org.slf4j.LoggerFactory.getLogger(HomeCommand.class);

    private ResponsePacket response = new ResponsePacket(ResponseType.FAILURE, "Invalid request");


    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        RoomCommand roomCommand = new RoomCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { roomCommand.create(packet); }
            case DELETE -> { roomCommand.delete(packet); }
        }
        return roomCommand.response;
    }

    @Override
    public void toggle(RequestPacket packet) {}

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating Room");
        packet.getHome().addRoom(new Room(packet.getName()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Room created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting Room");
        packet.getHome().removeRoom(packet.getRoom());
        response = new ResponsePacket(ResponseType.SUCCESS, "Room deleted");
    }
}
