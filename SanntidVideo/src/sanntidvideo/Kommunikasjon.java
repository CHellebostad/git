/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Christian
 */
public class Kommunikasjon extends Thread {

    private ServerSocket server;
    private Socket sock;
    private OutputStream outputStream;
    InputStream inputStream;
    ByteArrayOutputStream baos;
    VideoCap cap;
    BufferedImage img;
    InputStream input;
    OutputStream os;

    public Kommunikasjon() {
        cap = new VideoCap();

//        InputStreamReader IR = new InputStreamReader(sock.getInputStream());
//        BufferedReader BR = new BufferedReader(IR);
//        String Message = BR.readLine();
//        System.out.println(Message);
//        if (Message != null) {
//            PrintStream PS = new PrintStream(sock.getOutputStream());
//            PS.println("Message Recived");
//        }
    }
    
    public void run() {
        try {
            server = new ServerSocket(25000);
            System.out.println("Server running: " + server.isBound());
            sock = server.accept();
            img = cap.getOneFrame();
            os = sock.getOutputStream();
        
            
            
//                ImageIO.write(img, "jpg", sock.getOutputStream());
            //sock.close();
        } catch (IOException e) {
        }
        System.out.println("Server failed");

    }
}
