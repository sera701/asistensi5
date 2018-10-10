/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reyna
 */
public class Server {
    public static final int SERVICE_PORT=8;
    private String message, response;
    
    public void serviceClients(List<Mahasiswa> mhs) {
        
        try {
            ServerSocket server = new ServerSocket(SERVICE_PORT);
            System.out.println("Server Started");
            for(;;) {
                Socket in = server.accept();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in.getInputStream()));
                System.out.println("Received Data from "
                        + in.getInetAddress() + " : "
                        + in.getPort());
                message = reader.readLine();
                String[] data= message.split("-");
                switch (data[0]) {
                    case "4":
                        response="exit";
                        return;
                    case "1":
                        mhs.add(new Mahasiswa(data[1], data[2], data[3], data[4]));
                        response="Data mahasiswa berhasil ditambahkan";
                        break;
                    case "2":
                        mhs.set(Integer.parseInt(data[5]), new Mahasiswa(data[1], data[2], data[3], data[4]));
                        response="Data mahasiswa berhasil diubah";
                        break;
                    case "3":
                        mhs.remove(Integer.parseInt(data[1]));
                        response="Data mahasiswa berhasil dihapus";
                        break;
                    default:
                        break;
                }
                in.close();
                Socket mout = server.accept();
                OutputStream out = mout.getOutputStream();
                PrintStream pout = new PrintStream(out);
                pout.print(response);
                
                out.flush();
                out.close();
                mout.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void main(String[] args) throws UnknownHostException {
        Server server= new Server();
        List<Mahasiswa> mhs = new ArrayList<>();
        server.serviceClients(mhs);
        Simpan ss = new Simpan();
        ss.serialize(mhs, "tcpDataMahasiswa.ser");
        List<Mahasiswa> newList = ss.deserialize("tcpDataMahasiswa.ser");
        System.out.println("Daftar mahasiswa : "+ newList);
    }
}
