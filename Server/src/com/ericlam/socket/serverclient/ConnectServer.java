package com.ericlam.socket.serverclient;

import com.ericlam.socket.resources.ConfigManager;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConnectServer {

    public Socket getSocket() throws IOException {
        Socket socket = new Socket(ConfigManager.host, ConfigManager.port);
        socket.setKeepAlive(true);
        socket.setSoTimeout(1000000);
        return socket;
    }
}
