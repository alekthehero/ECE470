package com.alekthehero.ece470.project1;

import com.alekthehero.ece470.project1.datamodel.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PacketSerializationTests {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(PacketSerializationTests.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testDeSerialization() throws Exception {
        // Given A Packet The Client Would Send To Server
        String json = "{\"requestType\":\"CREATE\",\"name\":\"1\",\"deviceType\":\"BLIND\",\"requestType\":\"TOGGLE\"}";

        RequestPacket packet = objectMapper.readValue(json, RequestPacket.class);

        assert packet.getName().equals("1");
        assert packet.getDeviceType().equals(DeviceType.BLIND);
        assert packet.getRequestType().equals(RequestType.TOGGLE);
        logger.info("Packet Deserialized Successfully");
    }

    @Test
    void testSerialization() throws Exception {
        //Given a packet that the server would send to the client
        ResponsePacket packet = new ResponsePacket();
        packet.setResponseType(ResponseType.SUCCESS);
        packet.setMessage("Device Turned On Successfully");

        String json = objectMapper.writeValueAsString(packet);

        assert json.contains("SUCCESS");
        assert json.contains("Device Turned On Successfully");
        logger.info("Packet Serialized Successfully");
    }

}
