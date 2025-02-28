package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.devices.Lock;
import org.slf4j.Logger;

import java.util.Optional;

public class LockCommand extends Command {

    Logger logger = org.slf4j.LoggerFactory.getLogger(LockCommand.class);

    private ResponsePacket response = new ResponsePacket(ResponseType.FAILURE, "Invalid request");

    public static ResponsePacket ProcessCommand(RequestPacket packet) {
        LockCommand lockCommand = new LockCommand();
        switch (packet.getRequestType()) {
            case CREATE -> { lockCommand.create(packet); }
            case DELETE -> { lockCommand.delete(packet); }
            case TOGGLE -> { lockCommand.toggle(packet); }
        }
        return lockCommand.response;
    }

    @Override
    public void toggle(RequestPacket packet) {
        logger.info("Toggling lock");
        boolean success;
        Optional<Lock> lockOptional = packet.getHome().getLock(packet.getName());
        if (lockOptional.isEmpty()) {
            response = new ResponsePacket(ResponseType.FAILURE, "Lock not found");
            return;
        }
        Lock lock = lockOptional.get();
        if (lock.isOn()) {
            success = lock.codeTurnOff(packet.getCode());
        } else {
            success = lock.codeTurnOn(packet.getCode());
        }
        if (!success) {
            response = new ResponsePacket(ResponseType.FAILURE, "Invalid code");
            return;
        }
        response = new ResponsePacket(ResponseType.SUCCESS, "Lock toggled");
    }

    @Override
    public void create(RequestPacket packet) {
        logger.info("Creating lock");
        packet.getHome().addLock(new Lock(packet.getName(), packet.getCode()));
        response = new ResponsePacket(ResponseType.SUCCESS, "Lock created");
    }

    @Override
    public void delete(RequestPacket packet) {
        logger.info("Deleting lock");
        packet.getHome().removeLock(packet.getName());
        response = new ResponsePacket(ResponseType.SUCCESS, "Lock deleted");
    }
}
