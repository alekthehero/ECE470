package com.alekthehero.ece470.project1;

import com.alekthehero.ece470.project1.data.Homes;
import com.alekthehero.ece470.project1.datamodel.*;
import com.alekthehero.ece470.project1.services.PacketController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataModelTests {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(DataModelTests.class);
    private static final String TOKEN = "UserPassword";

    @BeforeAll
    public static void testAuth() {

        // Send Create Account request
        RequestPacket requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.CREATE_ACCOUNT);
        requestPacket.setHomeName("Home");
        requestPacket.setName("User");
        requestPacket.setPassword("Password");
        PacketController.processPacket(requestPacket);
        assert Homes.getHome("Home").getUsers().size() == 1;
        assert Homes.getHome("Home").getTokens().getFirst() != null;

        // Send Logout request
        requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.LOGOUT);
        requestPacket.setHomeName("Home");
        requestPacket.setToken(Homes.getHome("Home").getTokens().getFirst());
        PacketController.processPacket(requestPacket);
        assert Homes.getHome("Home").getTokens().isEmpty();

        // Send Login request
        requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.LOGIN);
        requestPacket.setHomeName("Home");
        requestPacket.setName("User");
        requestPacket.setPassword("Password");
        PacketController.processPacket(requestPacket);
        assert Homes.getHome("Home").getTokens().getFirst() != null;
    }

    @Test
    public void testDataModel() {
        // Ran when user creates a new room
        RequestPacket requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.CREATE);
        requestPacket.setDeviceType(DeviceType.ROOM);
        requestPacket.setToken(TOKEN);
        requestPacket.setName("Room");
        requestPacket.setHomeName("Home");
        PacketController.processPacket(requestPacket);
        assert Homes.getHome("Home").getRoom("Room") != null;

        // Ran when user creates a new light
        requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.CREATE);
        requestPacket.setDeviceType(DeviceType.LIGHT);
        requestPacket.setToken(TOKEN);
        requestPacket.setName("LIGHT");
        requestPacket.setHomeName("Home");
        requestPacket.setRoomName("Room");
        PacketController.processPacket(requestPacket);
        assert Homes.getHome("Home").getRoom("Room").getDevices().size() == 1;

        // Ran when user turns on the light
        requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.TOGGLE);
        requestPacket.setDeviceType(DeviceType.LIGHT);
        requestPacket.setToken(TOKEN);
        requestPacket.setName("LIGHT");
        requestPacket.setHomeName("Home");
        requestPacket.setRoomName("Room");
        PacketController.processPacket(requestPacket);
        assert (Homes.getHome("Home").getRoom("Room").getDevices().getFirst()).isOn();
    }

    @AfterAll
    public static void testGeneralActions() {
        // Ran when user requests all devices
        RequestPacket requestPacket = new RequestPacket();
        requestPacket.setRequestType(RequestType.GET_ALL);
        requestPacket.setToken(TOKEN);
        requestPacket.setHomeName("Home");
        PacketController.processPacket(requestPacket);
        // Check for network response, for now check console.
    }
}
