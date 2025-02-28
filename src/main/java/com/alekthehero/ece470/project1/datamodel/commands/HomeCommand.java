package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.*;

import org.slf4j.Logger;


public class HomeCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(HomeCommand.class);

    private ResponsePacket response;

    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        HomeCommand homeCommand = new HomeCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { homeCommand.create(packet); }
            case DELETE -> { homeCommand.delete(packet); }
        }
        return homeCommand.response;
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating home");
        Homes.addHome(new Home(packet.getName()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Home created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting home");
        Homes.removeHome(packet.getName());
        response = new ResponsePacket(ResponseType.SUCCESS, "Home deleted");
    }

    @Override
    public void toggle(RequestPacket packet) {}

}
