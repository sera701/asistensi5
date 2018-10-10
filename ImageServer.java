/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no4;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author reyna
*/

public class ImageServer {
    public static final int SERVICE_PORT = 14;
    
    public static void main(String[] args) {
        BufferedImage image = null;
        File f = null;
        try {
            ServerSocket server = new ServerSocket(SERVICE_PORT);
            System.out.println("Server Started");
            
                Socket nextClient = server.accept();
                System.out.println("Received Image from "
                        + nextClient.getInetAddress() + " : "
                        + nextClient.getPort());
                InputStream is = nextClient.getInputStream();
                byte[] sizeAr = new byte[4];
                is.read(sizeAr);
                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                
                byte[] imageAr = new byte[size];
                is.read(imageAr);
                image = ImageIO.read(new ByteArrayInputStream(imageAr));
                
                JFrame frame=new JFrame();
                JLabel label=new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label, BorderLayout.CENTER);
                frame.pack();
                frame.setVisible(true);
                try{
                    f = new File("e:\\Output.jpg");
                    ImageIO.write(image, "jpg", f);
                }catch(IOException e){
                    System.out.println("Error: "+e);
                }
                nextClient.close();
            
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
