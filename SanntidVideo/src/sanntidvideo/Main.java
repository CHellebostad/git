/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import org.opencv.core.Mat;

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
    private final BlockingQueue noteReturnQueue0 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue noteReturnQueue1 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue noteReturnQueue2 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue noteReturnQueue3 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue noteReturnQueue4 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue noteReturnQueue5 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue videoQueue = new ArrayBlockingQueue(1, true);
    private final ArrayList<String> tilAvspilling = new ArrayList<>();
    private BlockingQueue guiStream = new ArrayBlockingQueue(1, true);
    public boolean startProcessing;
    public BufferedImage img;
    public boolean CLOSE;
    private final VideoCap video;
    private boolean queueFinished = false;
    Thread t1;
    Thread t0;
    Thread t2;
    Thread t3;
    Thread t4;
    Thread t5;
    BildeSplit bs;
    Thread Cap;
    JLabel label;
    JFrame frame;
    ImageIcon imgIcon;
    double startTime;
    double endTime;
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();
    static BufferedImage bildeTilSplitting;
    Timer tim0;
    Avspilling spill = new Avspilling();

    public Main(BlockingQueue queue) throws IOException, InterruptedException {
        guiStream = queue;
        video = new VideoCap(videoQueue);
        Cap = new Thread(video);
        bs = new BildeSplit();
        t0 = new Thread(new OCR("1", queue0, noteReturnQueue0));
        t1 = new Thread(new OCR("2", queue1, noteReturnQueue1));
        t2 = new Thread(new OCR("3", queue2, noteReturnQueue2));
        t3 = new Thread(new OCR("4", queue3, noteReturnQueue3));
        t4 = new Thread(new OCR("5", queue4, noteReturnQueue4));
        t5 = new Thread(new OCR("6", queue5, noteReturnQueue5));

    }

    @Override
    public void run() {
        Cap.start();
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
//        Laster inn bildet manuelt
//        try {
//            img = ImageIO.read(new File("pic13.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

        while (true) {
            try {
                img = (BufferedImage) videoQueue.take();
                bilderTilAnalyse = bs.Split(img);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiStream.offer(img);
            startTime = System.currentTimeMillis();
            if (startProcessing) {
                while (!queueFinished) {
                    queue0.offer(bilderTilAnalyse.get(0));
                    queue1.offer(bilderTilAnalyse.get(1));
                    queue2.offer(bilderTilAnalyse.get(2));
                    queue3.offer(bilderTilAnalyse.get(3));
                    queue4.offer(bilderTilAnalyse.get(4));
                    queue5.offer(bilderTilAnalyse.get(5));
                    if (queue0.size() > 0 && queue1.size() > 0 && queue2.size() > 0 && queue3.size() > 0 && queue4.size() > 0 && queue5.size() > 0) {
                        queueFinished = true;
//                        System.out.println("Queue finished");
                    }
                }

                try {
//                    System.out.println("Tar Array");
                    String ret0 = (String) noteReturnQueue0.take();
                    String ret1 = (String) noteReturnQueue1.take();
                    String ret2 = (String) noteReturnQueue2.take();
                    String ret3 = (String) noteReturnQueue3.take();
                    String ret4 = (String) noteReturnQueue4.take();
                    String ret5 = (String) noteReturnQueue5.take();
//                    System.out.println("Thread0: "+ret0);
//                    System.out.println("Thread1: "+ret1);
//                    System.out.println("Thread2: "+ret2);
//                    System.out.println("Thread3: "+ret3);
//                    System.out.println("Thread4: "+ret4);
//                    System.out.println("Thread5: "+ret5);
                    if (!ret0.isEmpty()) {
                        tilAvspilling.add(ret0);
                    }
                    if (!ret1.isEmpty()) {
                        tilAvspilling.add(ret1);
                    }
                    if (!ret2.isEmpty()) {
                        tilAvspilling.add(ret2);
                    }
                    if (!ret3.isEmpty()) {
                        tilAvspilling.add(ret3);
                    }
                    if (!ret4.isEmpty()) {
                        tilAvspilling.add(ret4); 
                    }
                    if (!ret5.isEmpty()) {
                        tilAvspilling.add(ret5);
                    }

                    queueFinished = false;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                spill.MidiCycle(tilAvspilling);
            }

        }
    }

    public BufferedImage toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public void setStartProcessing(boolean var) {
        startProcessing = var;
    }

    public void setClosing() {
        video.close();
        CLOSE = true;

    }

    public void setStop() {
        spill.EndAllNotes();
    }

}
