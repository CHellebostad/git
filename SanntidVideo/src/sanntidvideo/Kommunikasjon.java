/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian
 */
public class Kommunikasjon {

    ServerSocket server;
    Socket sock;
    OutputStream outputStream;
    InputStream inputStream;
    ByteArrayOutputStream bytearray;
    public void Kommunikasjon() throws Exception {
        
//        InputStreamReader IR = new InputStreamReader(sock.getInputStream());
//        BufferedReader BR = new BufferedReader(IR);
//        String Message = BR.readLine();
//        System.out.println(Message);
//        if (Message != null) {
//            PrintStream PS = new PrintStream(sock.getOutputStream());
//            PS.println("Message Recived");
//        }
        

    }
    public void makeServer() throws Exception{
        server = new ServerSocket(4444);
        sock = server.accept();
        
        outputStream = sock.getOutputStream();
        inputStream = sock.getInputStream();
    }
    
    public void test(){
        System.out.println();
    }

    public void sendVideo() throws IOException {
        BufferedImage image = ImageIO.read(new File("pic1.jpg"));
        bytearray = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bytearray);
        byte[] size = ByteBuffer.allocate(4).putInt(bytearray.size()).array();
        outputStream.write(size);
        outputStream.write(bytearray.toByteArray());
        outputStream.flush();
        
    }

    public void sendInt() {

    }

    public void recievePicture() {

    }

    public void recieveBool() {

    }
}
