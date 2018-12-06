package com.ericlam.socket.resources;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class ConfigManager {
    public static String host;
    public static int port;
    public static boolean first = false;
    public ConfigManager(){
        InputStream config = getClass().getResourceAsStream("/com/ericlam/socket/resources/config.yml");
        File dstFile = new File("config.yml");
         try {
             if (!dstFile.exists()) {
                 first = true;
                 FileUtils.copyInputStreamToFile(config, dstFile);
                 return;
             }
             BufferedReader reader = new BufferedReader(new FileReader(dstFile));
             while (reader.ready()){
                 String[] Map = reader.readLine().split(": ");
                 if (Map[0].equals("host")) host = Map[1];
                 if (Map[0].equals("port")) port = Integer.parseInt(Map[1]);
             }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                System.out.println("port in config.yml is not a number!");
            }
        }
}
