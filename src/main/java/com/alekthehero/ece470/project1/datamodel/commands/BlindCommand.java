package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.devices.Blind;
import com.alekthehero.ece470.project1.datamodel.devices.Light;
import org.slf4j.Logger;

public class BlindCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(BlindCommand.class);

    private ResponsePacket response = new ResponsePacket(ResponseType.FAILURE, "Invalid request");

    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        BlindCommand blindCommand = new BlindCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { blindCommand.create(packet); }
            case DELETE -> { blindCommand.delete(packet); }
            case TOGGLE -> { blindCommand.toggle(packet); }
        }
        return blindCommand.response;
    }

    @Override
    public void toggle(RequestPacket packet) {
        logger.info("Toggling blind");
        packet.getDevice().setOn(!packet.getDevice().isOn());
        response = new ResponsePacket(ResponseType.SUCCESS, "Blind toggled");
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating blind");
        packet.getRoom().addDevice(new Blind(packet.getName()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Blind created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting blind");
        packet.getRoom().removeDevice(packet.getDevice());
        response = new ResponsePacket(ResponseType.SUCCESS, "Blind deleted");
    }
}
