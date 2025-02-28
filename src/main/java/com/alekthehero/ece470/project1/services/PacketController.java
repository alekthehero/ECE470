package com.alekthehero.ece470.project1.services;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.alekthehero.ece470.project1.datamodel.ResponseType;
import com.alekthehero.ece470.project1.datamodel.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacketController {
    static Logger logger = LoggerFactory.getLogger(PacketController.class);

    public static ResponsePacket processPacket(RequestPacket packet) {
        // Check for required fields
        if (packet.getHomeName() == null || packet.getRequestType() == null) {
            logger.info("Received a null or empty home name, throwing out packet");
            return new ResponsePacket(ResponseType.FAILURE, "Missing required fields");
        }

        // Authenticate
        switch (packet.getRequestType()){
            case LOGIN -> {
                Optional<String> token = Authentication.login(packet.getHomeName(), packet.getName(), packet.getPassword());
                if (token.isEmpty()) {
                    return new ResponsePacket(ResponseType.FAILURE, "Login failed");
                } else {
                    return new ResponsePacket(ResponseType.SUCCESS, token.get());
                }
            }
            case CREATE_ACCOUNT -> {
               Optional<String> token = Authentication.createAccount(packet.getHomeName(), packet.getName(), packet.getPassword());
                if (token.isEmpty()) {
                     return new ResponsePacket(ResponseType.FAILURE, "Account creation failed");
                } else {
                     return new ResponsePacket(ResponseType.SUCCESS, token.get());
                }

            }
            case LOGOUT -> {
                Authentication.logout(packet.getHomeName(), packet.getToken());
                return new ResponsePacket(ResponseType.SUCCESS, "Logout successful");
            }
        }

        // Authenticate Token
        if (!Authentication.authenticate(packet.getHomeName(), packet.getToken())) {
            return new ResponsePacket(ResponseType.FAILURE, "Authentication failed");
        }

        // General Actions
        switch (packet.getRequestType()) {
            case GET_ALL -> { return Homes.GetDetails(packet); }
        }

        // Process Device Actions
        if (packet.getDeviceType() == null) {
            return new ResponsePacket(ResponseType.FAILURE, "Missing required fields");
        }
        switch (packet.getDeviceType()){
            case LIGHT -> { return LightCommand.ProcessCommand(packet); }
            case BLIND -> { return BlindCommand.ProcessCommand(packet); }
            case HOME -> { return HomeCommand.ProcessCommand(packet); }
            case ROOM -> { return RoomCommand.ProcessCommand(packet); }
            case LOCK -> { return LockCommand.ProcessCommand(packet); }
            case ALARM -> { return AlarmCommand.ProcessCommand(packet); }
        }

        return new ResponsePacket(ResponseType.FAILURE, "Invalid request");
    }
}
