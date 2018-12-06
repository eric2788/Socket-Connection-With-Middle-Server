package com.ericlam.socket.server;

import com.ericlam.socket.resources.ConfigManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateServer {
    private ServerSocket server, client;
    private Socket serverS, clientS;
    private static CreateServer create;

    public static CreateServer call() {
        if (create == null) create = new CreateServer();
        return create;
    }

    private CreateServer(){
        try {
            server = new ServerSocket(ConfigManager.serverPort);
            client = new ServerSocket(ConfigManager.clientPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClient() throws IOException {
        if (clientS == null || clientS.isClosed()) clientS = client.accept();
        clientS.setSoTimeout(10000);
        return clientS;
    }

    public Socket getServer() throws IOException {
        if (serverS == null || serverS.isClosed()) serverS = server.accept();
        return serverS;
    }
}
