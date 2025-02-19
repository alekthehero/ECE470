package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.Home;
import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import org.slf4j.Logger;

public class HomeCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(HomeCommand.class);

    public static void ProcessCommand(RequestPacket packet) {
        HomeCommand homeCommand = new HomeCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { homeCommand.create(packet); }
            case DELETE -> { homeCommand.delete(packet); }
            case GET_ALL -> { homeCommand.getAll(packet); }
        }
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating home");
        Homes.addHome(new Home(packet.getName()));
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting home");
        Homes.removeHome(packet.getName());
    }

    @Override
    public void toggle(RequestPacket packet) {}

    public void getAll(RequestPacket packet) {
        logger.info("Home: {}", Homes.getHome(packet.getHomeName()).toString());
        Homes.getHome(packet.getHomeName()).getRooms().forEach((room) -> logger.info("  | Room: {}", room.toString()));
        // Send to client
    }
}
