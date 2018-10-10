/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author reyna
 */
public class AplikasiClient {
    public static final int SERVICE_PORT=8;
    public static String nim, nama, asal, kelas;
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        AplikasiClient ac = new AplikasiClient();
        try {
            String hostname = "localhost";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for(;;) {
                
                Socket mout = new Socket(hostname, SERVICE_PORT);
                String message;
                System.out.println("Choose an action : \n"
                        + "1. Insert \n"
                        + "2. Update \n"
                        + "3. Delete \n"
                        + "4. Exit");
                String text=reader.readLine();
                if(text.equals("4")){
                    message=text+"-a";
                    System.exit(0);
                }else if(text.equals("1")){
                    ac.modify();
                    message=text+"-"+nim+"-"+nama+"-"+asal+"-"+kelas+"-";
                }else {
                    System.out.print("Index data mahasiswa: ");
                    BufferedReader bfedit = new BufferedReader(reader);
                    int ind = Integer.parseInt(bfedit.readLine());
                    if(text.equals("2"))
                    {
                        ac.modify();
                        message=text+"-"+nim+"-"+nama+"-"+asal+"-"+kelas+"-"+ind+"-";
                    }else
                    {
                        message=text+"-"+ind+"-";
                    }
                }
                mout.setSoTimeout(2000);
                OutputStream out = mout.getOutputStream();
                PrintStream pout = new PrintStream(out);
                pout.print(message);
                System.out.println();
                out.flush();
                out.close();
                
                Socket min = new Socket(hostname, SERVICE_PORT);
                BufferedReader reader2 = new BufferedReader(
                        new InputStreamReader(min.getInputStream()));
                System.out.println(reader2.readLine());
                min.close();
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    public void modify()
    {
        try {
            InputStream input = System.in;
            InputStreamReader reader = new InputStreamReader(input);
            System.out.print("Nim: ");
            BufferedReader bfNim = new BufferedReader(reader);
            this.nim = bfNim.readLine();
            System.out.print("Nama: ");
            BufferedReader bfNama = new BufferedReader(reader);
            this.nama = bfNama.readLine();
            System.out.print("Asal: ");
            BufferedReader bfAsal = new BufferedReader(reader);
            this.asal = bfAsal.readLine();
            System.out.print("Kelas: ");
            BufferedReader bfKelas = new BufferedReader(reader);
            this.kelas = bfKelas.readLine();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
