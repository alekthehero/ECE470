package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.devices.Light;
import org.slf4j.Logger;

public class LightCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(LightCommand.class);

    public static void ProcessCommand(RequestPacket packet) {
        LightCommand lightCommand = new LightCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { lightCommand.create(packet); }
            case DELETE -> { lightCommand.delete(packet); }
            case TOGGLE -> { lightCommand.toggle(packet); }
            case CHANGE -> { lightCommand.change(packet); }
        }

    }

    @Override
    public void toggle(RequestPacket packet) {
        logger.info("Toggling light");
        packet.getDevice().setOn(!packet.getDevice().isOn());

    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating light");
        packet.getRoom().addDevice(new Light(packet.getName()));
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting light");
        packet.getRoom().removeDevice(packet.getDevice());
    }

    public void change(RequestPacket packet) {
        System.out.println("Changing light");
    }
}
