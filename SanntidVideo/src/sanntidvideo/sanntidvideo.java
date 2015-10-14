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
import org.opencv.core.Core;

/**
 *
 * @author Christian
 */
public class sanntidvideo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    static BildeSplit bs = new BildeSplit();
    static VideoCap vc = new VideoCap();
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();

    public static void main(String[] args) throws IOException, NullPointerException {
        //new Thread(new Kommunikasjon()).start();

        //BufferedImage bildeTilSplitting = vc.getOneFrame();
        File bildeTilSplitting = new File("img3.jpg");
        
        bilderTilAnalyse = bs.Split(bildeTilSplitting);

            
            Thread t0 = new Thread(new Analyse(bilderTilAnalyse.get(0)));
            t0.start();
            Thread t1 = new Thread(new Analyse(bilderTilAnalyse.get(1)));
            t1.start();
            Thread t2 = new Thread(new Analyse(bilderTilAnalyse.get(2)));
            t2.start();
            Thread t3 = new Thread(new Analyse(bilderTilAnalyse.get(3)));
            t3.start();
            Thread t4 = new Thread(new Analyse(bilderTilAnalyse.get(4)));
            t4.start();
            Thread t5 = new Thread(new Analyse(bilderTilAnalyse.get(5)));
            t5.start();
        

    }

    

}
