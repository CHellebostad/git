/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
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
public class Kommunikasjon extends Thread {

    ServerSocket server;
    Socket sock;
    OutputStream outputStream;
    InputStream inputStream;
    ByteArrayOutputStream bytearray;
    BufferedOutputStream bos = null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    VideoCap cap;

    public void Kommunikasjon() throws Exception {
        cap = new VideoCap();
        test();
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

    public void test() throws Exception {
        Socket socket = new Socket("localhost", 13085);
        OutputStream outputStream = socket.getOutputStream();
        while (true) {
            BufferedImage image = cap.getOneFrame();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            System.out.println("Flushed: " + System.currentTimeMillis());

            Thread.sleep(2000);
            System.out.println("Closing: " + System.currentTimeMillis());
            socket.close();
        }
    }

    public void sendVideo() throws IOException {
        bos = new BufferedOutputStream(sock.getOutputStream());
        BufferedImage frame = cap.getOneFrame();
        ImageIO.write(frame, "JPG", baos);
        bos.write(baos.toByteArray());
        bos.flush();

    }

    public void sendInt() {

    }

    public void recievePicture() {

    }

    public void recieveBool() {

    }
}
