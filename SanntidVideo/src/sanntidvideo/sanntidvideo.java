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
import org.opencv.core.Core;
import org.opencv.core.Mat;

/**
 *
 * @author Christian
 */
public class sanntidvideo implements Runnable {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static BildeSplit bs;
    static Thread vc;
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();
    static BufferedImage bildeTilSplitting;
    static Thread main;
    private final BlockingQueue queue0 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue1 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue2 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue3 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue4 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue queue5 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue videoQueue = new ArrayBlockingQueue(1, true);

    public sanntidvideo() {
        System.out.println("Sanntid Running");
        bs = new BildeSplit();
    }

    public static void main(String[] args) {
        main = new Thread(new sanntidvideo());
        main.start();

    }

    @Override
    public void run() {

        vc = new Thread(new VideoCap(videoQueue));
        vc.start();
        Thread t0 = new Thread(new Analyse(queue0,"0"));
        t0.start();
        Thread t1 = new Thread(new Analyse(queue1,"1"));
        t1.start();
        Thread t2 = new Thread(new Analyse(queue2,"2"));
        t2.start();
        Thread t3 = new Thread(new Analyse(queue3,"3"));
        t3.start();
        Thread t4 = new Thread(new Analyse(queue4,"4"));
        t4.start();
        Thread t5 = new Thread(new Analyse(queue5,"5"));
        t5.start();
        while (true) {
            while (videoQueue.size() > 0) {
                try {
//                    bildeTilSplitting = ImageIO.read(new File("pic9.png"));
//                    bildeTilSplitting = (BufferedImage) videoQueue.take();
//                    System.out.println(bildeTilSplitting.getHeight() + " " + bildeTilSplitting.getWidth());

                    bildeTilSplitting = ImageIO.read(new File("pic9.png"));// Leser inn fra fil og ikke Webkamera

                    bilderTilAnalyse = bs.Split(bildeTilSplitting);// Splitter bildet

                    queue0.put(bilderTilAnalyse.get(0));// Sender biter av bildet til hver sin tråd
                    queue1.put(bilderTilAnalyse.get(1));
                    queue2.put(bilderTilAnalyse.get(2));
                    queue3.put(bilderTilAnalyse.get(3));
                    queue4.put(bilderTilAnalyse.get(4));
                    queue5.put(bilderTilAnalyse.get(5));

//                    ImageIO.write(bilderTilAnalyse.get(2), "png", new File("pic4.png"));
////                    Skriver ut inholdet ti BilderTilAnalyseArrayet
//                    System.out.println("Bilde 1: " + bilderTilAnalyse.get(0).getWidth() + " " + bilderTilAnalyse.get(0).getHeight());
//                    System.out.println("Bilde 2: " + bilderTilAnalyse.get(1).getWidth() + " " + bilderTilAnalyse.get(1).getHeight());
//                    System.out.println("Bilde 3: " + bilderTilAnalyse.get(2).getWidth() + " " + bilderTilAnalyse.get(2).getHeight());
//                    System.out.println("Bilde 4: " + bilderTilAnalyse.get(3).getWidth() + " " + bilderTilAnalyse.get(3).getHeight());
//                    System.out.println("Bilde 5: " + bilderTilAnalyse.get(4).getWidth() + " " + bilderTilAnalyse.get(4).getHeight());
//                    System.out.println("Bilde 6: " + bilderTilAnalyse.get(5).getWidth() + " " + bilderTilAnalyse.get(5).getHeight());
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                } catch (IOException ex) {
                    System.out.println("Nothing in queue");
                }
            }
        }

    }

}
