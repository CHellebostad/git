/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian-Heim
 */
public class Main implements Runnable {

    /**
     * @param args the command line arguments
     */
    private final BlockingQueue queue0 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue1 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue2 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue3 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue4 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue5 = new ArrayBlockingQueue(1, true);
    public BufferedImage img;
    Thread t1;
    Thread t0;
    Thread t2;
    Thread t3;
    Thread t4;
    Thread t5;
    BildeSplit bs;
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();
    static BufferedImage bildeTilSplitting;

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread start = new Thread(new Main());
        start.start();

    }

    public Main() throws IOException, InterruptedException {
        t0 = new Thread(new OCR("1", queue0));
        t1 = new Thread(new OCR("2", queue1));
        t2 = new Thread(new OCR("3", queue2));
        t3 = new Thread(new OCR("4", queue3));
        t4 = new Thread(new OCR("5", queue4));
        t5 = new Thread(new OCR("6", queue5));
        bs = new BildeSplit();
        

    }

    @Override
    public void run() {
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        try {
            img = ImageIO.read(new File("pic9.png"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bilderTilAnalyse = bs.Split(img);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                queue0.put(bilderTilAnalyse.get(0));
                queue1.put(bilderTilAnalyse.get(1));
                queue2.put(bilderTilAnalyse.get(2));
                queue3.put(bilderTilAnalyse.get(3));
                queue4.put(bilderTilAnalyse.get(4));
                queue5.put(bilderTilAnalyse.get(5));
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
