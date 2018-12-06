package com.ericlam.socket.command;

import com.ericlam.socket.client.ConnectServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommandCtl extends Thread{
    @Override
    public void run() {
        try {
            Socket client = new ConnectServer().getSocket();
            if (client != null) {
                System.out.println(">> Connected successfully");
            } else {
                System.out.println(">> Cannot connect to that server");
                return;
            }
            BufferedReader reader;
            PrintWriter writer;
            while (true){
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new PrintWriter(client.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                String input = "";
                if (scanner.hasNextLine()) input = scanner.nextLine();
                if (!input.isEmpty()){
                    String[] arg = input.split(" ");
                    writer.println(input);
                    if (arg[0].equalsIgnoreCase("quit")){
                        System.out.println("client quited.");
                        writer.flush();
                        break;
                    }
                }else{
                    continue;
                }
                writer.flush();
                String output = reader.readLine();
                if (output == null){
                    System.out.println(">> Server disconnected");
                    break;
                }
                System.out.println(output);
                while (reader.ready()){
                    output = reader.readLine();
                    System.out.println("   "+output);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
        }

    }
}
