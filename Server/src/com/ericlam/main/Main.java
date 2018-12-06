package com.ericlam.main;

import com.ericlam.socket.command.Response;
import com.ericlam.socket.resources.ConfigManager;

public class Main {
    public static void main(String[] args) {
        new ConfigManager(); //initialize config
        if (ConfigManager.first) {
            System.out.println("Config initialized. Edit the config and start the client again.");
            return;
        }
        new Response().run();
    }
}
