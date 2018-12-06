package com.ericlam.socket.command;

import com.ericlam.socket.serverclient.ConnectServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Response extends Thread{
    @Override
    public void run() {
        try {
            Socket client = new ConnectServer().getSocket();
            if (client == null) {
                System.out.println(">> Cannot connect to that server");
                return;
            }
            System.out.println(">> Connected successfully");
            BufferedReader reader;
            PrintWriter writer;
            while (true){
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream());
                while (reader.ready()) {
                   String input = reader.readLine();
                    writer.println(input + " [Server Response]");
                }
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
            System.out.println("Reconnect after 10 sec");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Response.this.run();
                }
            }, 10000);
        }
    }
}
