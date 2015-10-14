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
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian
 */
public class Kommunikasjon implements Runnable {

    private ServerSocket server;
    private Socket sock;
    private OutputStream outputStream;
    InputStream inputStream;
    ByteArrayOutputStream baos;
    VideoCap cap;
    BufferedImage img;
    InputStream input;
    OutputStream os;
    byte[] byteimg;

    public Kommunikasjon() {
        cap = new VideoCap();
    }

    public void run() {
        try {
            server = new ServerSocket(25000);
            System.out.println("Server running: " + server.isBound());
            int i=0;
            while (true) {
                sock = server.accept();
                os = sock.getOutputStream();
                img = cap.getOneFrame();
                baos = new ByteArrayOutputStream();

                ImageIO.write(img, "jpg", baos);
                byte[] size = ByteBuffer.allocate(4).putInt(baos.size()).array();
                os.write(size);
                System.out.println(baos.size());
                os.write(baos.toByteArray());
                os.flush();
            }
        } catch (IOException e) {
        }
        System.out.println("Server failed");
    }
}
