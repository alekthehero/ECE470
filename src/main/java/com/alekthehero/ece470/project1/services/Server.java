package com.alekthehero.ece470.project1.services;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;
import com.alekthehero.ece470.project1.datamodel.ResponsePacket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Server {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Server.class);

    private static final int PORT = 8080;
    private ServerSocket serverSocket;
    // Multithreading is pretty easy in Java so might as well add it
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private boolean running;


    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            running = true;
            threadPool.execute(this::establishConnections);
        } catch (Exception e) {
            logger.error("Error starting server", e);
        }
        logger.info("Server started on port " + PORT);
    }

    public void establishConnections() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                logger.info("Established connection from " + socket.getInetAddress().getHostAddress());
                threadPool.execute(() -> handleClient(socket));
            } catch (Exception e) {
                if (!running) {
                    break;
                }
                logger.error("Error accepting connection", e);
            }
        }
    }

    public void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    RequestPacket packet = objectMapper.readValue(line, RequestPacket.class);
                    ResponsePacket response = PacketController.processPacket(packet);
                    out.println(objectMapper.writeValueAsString(response));
                } catch (Exception e) {
                    logger.error("Error processing packet", e);
                    out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Error handling client", e);
        }
    }

    public void stop() {
        running = false;
        threadPool.shutdown();
        try {
            serverSocket.close();
        } catch (Exception e) {
            logger.error("Error stopping server", e);
        }
    }

}
