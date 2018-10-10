/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no5;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.audio.AudioStream;

/**
 *
 * @author reyna
 */

public class AudioClient {
    public static final int SERVICE_PORT = 14;
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV file", "wav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System. out.println("You chose to open this file: " +
            chooser. getSelectedFile().getName());
        }
        File f = new File(chooser. getSelectedFile().getAbsolutePath());
        try {
            String hostname = "localhost";
            System.out.println("Connection Estabilished");
            for(;;) {
                Socket client = new Socket(hostname, SERVICE_PORT);
                byte[] fileAr = new byte[(int) f.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                bis.read(fileAr, 0, fileAr.length);
                OutputStream out = client.getOutputStream();
                out.write(fileAr, 0, fileAr.length);
                out.flush();
                out.close();
                client.close();
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
