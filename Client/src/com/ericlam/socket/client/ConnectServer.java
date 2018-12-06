package com.ericlam.socket.client;

import com.ericlam.socket.resources.ConfigManager;

import java.io.IOException;
import java.net.Socket;

public class ConnectServer {

    public Socket getSocket() throws IOException {
        Socket socket =  new Socket(ConfigManager.host, ConfigManager.port);
        socket.setKeepAlive(true);
        socket.setSoTimeout(10000);
        return socket;
    }
}
