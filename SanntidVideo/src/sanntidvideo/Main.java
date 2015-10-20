/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian-Heim
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private final BlockingQueue queue0 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue1 = new ArrayBlockingQueue(1, true);
    public BufferedImage img;

    public static void main(String[] args) throws InterruptedException, IOException {
        new Main();

    }

    public Main() throws IOException, InterruptedException {
        Thread t0 = new Thread(new OCR("1", queue0));
        Thread t1 = new Thread(new OCR("2", queue1));
        t0.start();
        t1.start();
        img = ImageIO.read(new File("pic2.png"));
        while (true) {
            queue0.put(img);
            queue1.put(img);
        }
    }
}
