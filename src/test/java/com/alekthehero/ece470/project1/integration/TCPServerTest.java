package com.alekthehero.ece470.project1.integration;

import com.alekthehero.ece470.project1.datamodel.DeviceType;
import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.RequestType;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest()
@Tag("Integration")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TCPServerTest {

    private static final int PORT = 8080;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Socket client;
    private static PrintWriter out;
    private static BufferedReader in;

    private static String token;

    @Test
    @Order(1)
    public void setUpAndCreateAccount() throws IOException {
        client = new Socket("localhost", PORT);
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // Create an account for testing
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.CREATE_ACCOUNT);
        packet.setHomeName("TestHome");
        packet.setName("TestUser");
        packet.setPassword("TestPass");
        out.println(objectMapper.writeValueAsString(packet));

        // Get and verify response
        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assertNotNull(responsePacket.getMessage());

        token = responsePacket.getMessage();
    }

    @Test
    @Order(2)
    public void testInvalidCommands() throws Exception {
        // Test invalid login
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.LOGIN);
        packet.setHomeName("TestHome");
        packet.setName("TestUser");
        packet.setPassword("InvalidPass");
        out.println(objectMapper.writeValueAsString(packet));

        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Login failed");

        // Test invalid authentication
        packet.setRequestType(RequestType.GET_ALL);
        packet.setToken("InvalidToken");
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Authentication failed");

        // Test missing required fields
        packet.setRequestType(RequestType.CREATE);
        packet.setToken(token);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Missing required fields");
    }

    @Test
    @Order(3)
    public void testRoomCommands() throws Exception {
        // Success to Create a room
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.CREATE);
        packet.setToken(token);
        packet.setHomeName("TestHome");
        packet.setName("TestRoom");
        packet.setDeviceType(DeviceType.ROOM);
        out.println(objectMapper.writeValueAsString(packet));

        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Room created");

        // Success to Delete the room
        packet.setRequestType(RequestType.DELETE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Room deleted");

        // Create another room
        packet.setRequestType(RequestType.CREATE);
        packet.setName("TestRoom2");
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Room created");
    }

    @Test
    @Order(4)
    public void testCodedDeviceCommands() throws Exception {
        // Success to Create a lock
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.CREATE);
        packet.setToken(token);
        packet.setHomeName("TestHome");
        packet.setRoomName("TestRoom2");
        packet.setName("TestLock");
        packet.setDeviceType(DeviceType.LOCK);
        packet.setCode((short) 1234);
        out.println(objectMapper.writeValueAsString(packet));

        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Lock created");

        // Success to Toggle the lock
        packet.setRequestType(RequestType.TOGGLE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Lock toggled");

        // Success to Delete the lock
        packet.setRequestType(RequestType.DELETE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Lock deleted");

        // Create an alarm
        packet.setRequestType(RequestType.CREATE);
        packet.setName("TestAlarm");
        packet.setDeviceType(DeviceType.ALARM);
        packet.setCode((short) 1234);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Alarm created");

        // Success to Toggle the alarm
        packet.setRequestType(RequestType.TOGGLE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Alarm toggled");

        // Success to Delete the alarm
        packet.setRequestType(RequestType.DELETE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Alarm deleted");
    }

    @Test
    @Order(5)
    public void testDeviceCommands() throws Exception {
        // Success to Create a light
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.CREATE);
        packet.setToken(token);
        packet.setHomeName("TestHome");
        packet.setRoomName("TestRoom2");
        packet.setName("TestLight");
        packet.setDeviceType(DeviceType.LIGHT);
        out.println(objectMapper.writeValueAsString(packet));

        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Light created");

        // Success to Toggle the light
        packet.setRequestType(RequestType.TOGGLE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Light toggled");

        // Success to Delete the light
        packet.setRequestType(RequestType.DELETE);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Light deleted");

        // Create a blind
        packet.setRequestType(RequestType.CREATE);
        packet.setName("TestBlind");
        packet.setDeviceType(DeviceType.BLIND);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Blind created");

    }

    @Test
    @Order(6)
    public void testDataAccess() throws Exception {
        // Test getting all data
        RequestPacket packet = new RequestPacket();
        packet.setRequestType(RequestType.GET_ALL);
        packet.setToken(token);
        packet.setHomeName("TestHome");
        packet.setDeviceType(DeviceType.HOME);
        out.println(objectMapper.writeValueAsString(packet));

        String response = in.readLine();
        ResponsePacket responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assertNotNull(responsePacket.getMessage());
        System.out.println(responsePacket.getMessage());

        // Test getting all rooms
        packet.setDeviceType(DeviceType.ROOM);
        packet.setRoomName("TestRoom2");
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assertNotNull(responsePacket.getMessage());
        System.out.println(responsePacket.getMessage());

        // Test getting all blinds
        packet.setDeviceType(DeviceType.BLIND);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assertNotNull(responsePacket.getMessage());
        System.out.println(responsePacket.getMessage());

        // Test logging out
        packet.setRequestType(RequestType.LOGOUT);
        out.println(objectMapper.writeValueAsString(packet));

        response = in.readLine();
        responsePacket = objectMapper.readValue(response, ResponsePacket.class);
        assertNotNull(responsePacket);
        assert responsePacket.getMessage().equals("Logout successful");
    }

}
