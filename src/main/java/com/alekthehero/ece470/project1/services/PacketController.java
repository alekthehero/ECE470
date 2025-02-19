package com.alekthehero.ece470.project1.services;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.commands.HomeCommand;
import com.alekthehero.ece470.project1.datamodel.commands.LightCommand;
import com.alekthehero.ece470.project1.datamodel.commands.RoomCommand;
import org.springframework.stereotype.Service;

//!!! Temporary class to simulate the controller logic which will be in the tcp server to store the devices in memory
@Service
public class PacketController {
    public static void processPacket(RequestPacket packet) {

        // Authenticate
        switch (packet.getRequestType()){
            case LOGIN -> { Authentication.login(packet.getHomeName(), packet.getName(), packet.getPassword()); return; }
            case CREATE_ACCOUNT -> { Authentication.createAccount(packet.getHomeName(), packet.getName(), packet.getPassword()); return; }
            case LOGOUT -> { Authentication.logout(packet.getHomeName(), packet.getToken()); return; }
        }

        // Authenticate Token
        if (!Authentication.authenticate(packet.getHomeName(), packet.getToken())) {
            /// Send fail to client
            return;
        }

        // General Actions
        switch (packet.getRequestType()) {
            case GET_ALL -> { HomeCommand.ProcessCommand(packet); return;}
        }

        // Process Device Actions
        switch (packet.getDeviceType()){
            case LIGHT -> { LightCommand.ProcessCommand(packet); }
            case HOME -> { HomeCommand.ProcessCommand(packet); }
            case ROOM -> { RoomCommand.ProcessCommand(packet); }
        }
    }
}
