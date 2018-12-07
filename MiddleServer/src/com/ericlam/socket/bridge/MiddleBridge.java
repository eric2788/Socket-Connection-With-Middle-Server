package com.ericlam.socket.bridge;

import com.ericlam.socket.server.CreateServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MiddleBridge extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(">> Waiting for server connect...");
                CreateServer create = CreateServer.call();
                Socket server = create.getServer();
                if (server == null) {
                    System.out.println(">> Connection failed");
                    break;
                } else {
                    System.out.println(">> Server Connected. Waiting for client....");
                }
                Socket client = create.getClient();
                if (client == null) {
                    System.out.println(">> Connection failed");
                    break;
                } else {
                    System.out.println(">> Client Connected. Establishing Connection...");
                }
                create.checkClient();
                while (true) {
                    BufferedReader serverReader, clientReader;
                    PrintWriter serverWriter, clientWriter;
                    clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    clientWriter = new PrintWriter(client.getOutputStream());
                    serverReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    serverWriter = new PrintWriter(server.getOutputStream());
                    String serverTxt, clientTxt;
                    while (clientReader.ready()) {
                        clientTxt = clientReader.readLine();
                        if (clientTxt == null || clientTxt.equalsIgnoreCase("quit")){
                            System.out.println("closing client.....");
                            client.close();
                            break;
                        }
                            if (clientTxt.equalsIgnoreCase("ping")){
                                clientWriter.println("pong");
                                clientWriter.flush();
                                continue;
                            }
                            System.out.println("Receive client msg "+clientTxt);
                            serverWriter.println(clientTxt);
                            if (serverWriter.checkError()) {
                                server.close();
                                break;
                            }
                        serverWriter.flush();
                        System.out.println("Flushed to server.");
                    }

                    if (serverReader.ready()) {
                        serverTxt = serverReader.readLine();
                        if (serverTxt == null){
                            System.out.println("server side down");
                            server.close();
                            break;
                        }
                            if (serverTxt.equalsIgnoreCase("ping")){
                                serverWriter.println("pong");
                                serverWriter.flush();
                                continue;
                            }
                            System.out.println("Receive server msg "+serverTxt);
                            clientWriter.println(serverTxt);
                        if (!clientWriter.checkError()) {
                            clientWriter.flush();
                            System.out.println("Flushed to client.");
                        } else {
                            server.close();
                            break;
                        }
                    }

                    if (!create.isClientalive()) {
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error: "+e.getMessage());
            }
        }

    }

}
