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
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.opencv.core.Mat;

/**
 *
 * @author Christian-Heim
 */
public class Main implements Runnable {

    /**
     * @param args the command line arguments
     */
    public final BlockingQueue queue0 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue queue1 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue queue2 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue queue3 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue queue4 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue queue5 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue0 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue1 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue2 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue3 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue4 = new ArrayBlockingQueue(1, true);
    public final BlockingQueue noteReturnQueue5 = new ArrayBlockingQueue(1, true);
    private final BlockingQueue videoQueue = new ArrayBlockingQueue(1, true);
    public final BlockingQueue avspillingsQueue = new ArrayBlockingQueue(1, true);
    public final BlockingQueue guiQueue = new ArrayBlockingQueue(1, true);
    public final ArrayList<String> tilAvspilling = new ArrayList<>();
    private BlockingQueue guiStream = new ArrayBlockingQueue(1, true);
    public boolean startProcessing;
    public BufferedImage img;
    public boolean CLOSE;
    private final VideoCap Cap;
    private final TimerTask video;
    
   public String refTest0;
   public String refTest1;
   public String refTest2;
   public String refTest3;
   public String refTest4;
   public String refTest5;

    private boolean queueFinished = false;
    TimerTask t1;
    TimerTask t0;
    TimerTask t2;
    TimerTask t3;
    TimerTask t4;
    TimerTask t5;
    BildeSplit bs;
    JLabel label;
    JFrame frame;
    ImageIcon imgIcon;
    double startTime;
    double endTime;
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();
    static BufferedImage bildeTilSplitting;
    Timer tim0;
    public TimerTask spill;
    private boolean start;
    java.util.Timer timer6 = new java.util.Timer(true);
    
    public Main(BlockingQueue queue) throws IOException, InterruptedException {
        guiStream = queue;
        Cap = new VideoCap(videoQueue);
        video = Cap;
        bs = new BildeSplit();
        t0 = new OCR("1", queue0, noteReturnQueue0);
        t1 = new OCR("2", queue1, noteReturnQueue1);
        t2 = new OCR("3", queue2, noteReturnQueue2);
        t3 = new OCR("4", queue3, noteReturnQueue3);
        t4 = new OCR("5", queue4, noteReturnQueue4);
        t5 = new OCR("6", queue5, noteReturnQueue5);
        spill = new Avspilling(avspillingsQueue);
    }

    @Override
    public void run() {
        java.util.Timer timer7 = new java.util.Timer(true);
        timer7.scheduleAtFixedRate(video, 0, 33);
        
        java.util.Timer timer0 = new java.util.Timer(true);
        java.util.Timer timer1 = new java.util.Timer(true);
        java.util.Timer timer2 = new java.util.Timer(true);
        java.util.Timer timer3 = new java.util.Timer(true);
        java.util.Timer timer4 = new java.util.Timer(true);
        java.util.Timer timer5 = new java.util.Timer(true);
        
        timer0.scheduleAtFixedRate(t0, 0, 400);
        timer1.scheduleAtFixedRate(t1, 0, 400);
        timer2.scheduleAtFixedRate(t2, 0, 400);
        timer3.scheduleAtFixedRate(t3, 0, 400);
        timer4.scheduleAtFixedRate(t4, 0, 400);
        timer5.scheduleAtFixedRate(t5, 0, 400);


        timer6.scheduleAtFixedRate(spill, 0, 400);
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
            } catch ( InterruptedException | IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiStream.offer(img);
            startTime = System.currentTimeMillis();
            if (startProcessing) {
                if (!queueFinished) {
                    queue0.offer(bilderTilAnalyse.get(0));
                    queue1.offer(bilderTilAnalyse.get(1));
                    queue2.offer(bilderTilAnalyse.get(2));
                    queue3.offer(bilderTilAnalyse.get(3));
                    queue4.offer(bilderTilAnalyse.get(4));
                    queue5.offer(bilderTilAnalyse.get(5));
                    queueFinished = true;
//                  System.out.println("Queue finished");
                }

                try {
                    if (!noteReturnQueue0.isEmpty() && !noteReturnQueue1.isEmpty() && !noteReturnQueue2.isEmpty() && !noteReturnQueue3.isEmpty() && !noteReturnQueue4.isEmpty() && !noteReturnQueue5.isEmpty()) {
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
                            refTest0 = ret0;
                        }
                        if (!ret1.isEmpty()) {
                            tilAvspilling.add(ret1);
                            refTest1 = ret1;
                        }
                        if (!ret2.isEmpty()) {
                            tilAvspilling.add(ret2);
                            refTest2 = ret2;
                        }
                        if (!ret3.isEmpty()) {
                            tilAvspilling.add(ret3);
                            refTest3 = ret3;
                        }
                        if (!ret4.isEmpty()) {
                            tilAvspilling.add(ret4);
                            refTest4 = ret4;
                        }
                        if (!ret5.isEmpty()) {
                            tilAvspilling.add(ret5);
                            refTest5 = ret5;
                        }
//                        System.out.println("");
                        queueFinished = false;
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                avspillingsQueue.offer(tilAvspilling);
               
               
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

    public void startVideo() {
        Cap.startVideo();
    }

    public void stopVideo() {
        Cap.stopVideo();
    }

    public void setClosing() {
        Cap.close();
        CLOSE = true;
    }
}
