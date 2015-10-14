/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import static com.sun.glass.ui.Application.run;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

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

    public void makeServer() throws Exception {

//        server = new ServerSocket(4444);
//        sock = server.accept();
//
//        outputStream = sock.getOutputStream();
//        inputStream = sock.getInputStream();
//        System.out.println("Server Executed");
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
