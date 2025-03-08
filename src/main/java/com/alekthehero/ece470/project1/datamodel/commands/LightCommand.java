package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.devices.Light;
import org.slf4j.Logger;

import java.awt.*;

public class LightCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(LightCommand.class);

    private ResponsePacket response = new ResponsePacket(ResponseType.FAILURE, "Invalid request");

    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        LightCommand lightCommand = new LightCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { lightCommand.create(packet); }
            case DELETE -> { lightCommand.delete(packet); }
            case TOGGLE -> { lightCommand.toggle(packet); }
            case CHANGE -> { lightCommand.change(packet); }
        }
        return lightCommand.response;
    }

    @Override
    public void toggle(RequestPacket packet) {
        logger.info("Toggling light");
        packet.getDevice().setOn(!packet.getDevice().isOn());
        response = new ResponsePacket(ResponseType.SUCCESS, "Light toggled");
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating light");
        packet.getRoom().addDevice(new Light(packet.getName()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Light created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting light");
        packet.getRoom().removeDevice(packet.getDevice());
        response = new ResponsePacket(ResponseType.SUCCESS, "Light deleted");
    }

    public void change(RequestPacket packet) {
        switch (packet.getChangeType()) {
            case BRIGHTNESS -> {
                logger.info("Changing brightness");
                ((Light) packet.getDevice()).setBrightness(packet.getBrightness());
                response = new ResponsePacket(ResponseType.SUCCESS, "Brightness changed");
            }
            case COLOR -> {
                logger.info("Changing color");
                ((Light) packet.getDevice()).setColor(new Color(packet.getColor()));
                response = new ResponsePacket(ResponseType.SUCCESS, "Color changed");
            }
        }
    }
}
