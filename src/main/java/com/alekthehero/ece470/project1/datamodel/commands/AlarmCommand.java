package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.devices.Alarm;
import org.slf4j.Logger;

import java.util.Optional;

public class AlarmCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(AlarmCommand.class);

    private ResponsePacket response = new ResponsePacket(ResponseType.FAILURE, "Invalid request");

    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        AlarmCommand alarmCommand = new AlarmCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { alarmCommand.create(packet); }
            case DELETE -> { alarmCommand.delete(packet); }
            case TOGGLE -> { alarmCommand.toggle(packet); }
        }
        return alarmCommand.response;
    }

    @Override
    public void toggle(RequestPacket packet) {
        logger.info("Toggling alarm");
        boolean success;
        Optional<Alarm> alarmOptional = packet.getHome().getAlarm(packet.getName());
        if (alarmOptional.isEmpty()) {
            response = new ResponsePacket(ResponseType.FAILURE, "Alarm not found");
            return;
        }
        Alarm alarm = alarmOptional.get();
        if (alarm.isOn()) {
            success = alarm.codeTurnOff(packet.getCode());
        } else {
            success = alarm.codeTurnOn(packet.getCode());
        }
        if (!success) {
            response = new ResponsePacket(ResponseType.FAILURE, "Invalid code");
            return;
        }
        response = new ResponsePacket(ResponseType.SUCCESS, "Alarm toggled");
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating alarm");
        packet.getHome().addAlarm(new Alarm(packet.getName(), packet.getCode()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Alarm created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting alarm");
        packet.getHome().removeAlarm(packet.getName());
        response = new ResponsePacket(ResponseType.SUCCESS, "Alarm deleted");
    }
}
