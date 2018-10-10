/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no5;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author reyna
 */

public class AudioServer {
    public static final int SERVICE_PORT = 14;
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVICE_PORT);
            System.out.println("Server Started");
            
                Socket nextClient = server.accept();
                System.out.println("Received Audio from "
                        + nextClient.getInetAddress() + " : "
                        + nextClient.getPort());
                byte[] fileAr = new byte[102400000];
                InputStream is = nextClient.getInputStream();
                FileOutputStream fos = new FileOutputStream("D:/AudioOutput.wav");
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int bytesRead = is.read(fileAr, 0, fileAr.length);
                bos.write(fileAr, 0, bytesRead);
                bos.close();
                nextClient.close();
                InputStream in = new FileInputStream("D:/AudioOutput.wav");
                AudioStream as = new AudioStream(in);
                AudioPlayer.player.start(as);
            
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
