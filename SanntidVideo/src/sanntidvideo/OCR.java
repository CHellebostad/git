/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import com.aspose.ocr.ImageStream;
import com.aspose.ocr.ImageStreamFormat;
import com.aspose.ocr.OcrEngine;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian-Heim
 */
public class OCR implements Runnable {

    String ThreadNr;
    OcrEngine ocr;
    ByteArrayOutputStream baos;
    InputStream is;
    BufferedImage image;

    private BlockingQueue bq = null;

    public OCR(String Thread, BlockingQueue queue) throws InterruptedException, IOException {
        ThreadNr = Thread;
        bq = queue;
        ocr = new OcrEngine();
        baos = new ByteArrayOutputStream();

    }

    @Override
    public void run() {
        while (true) {
            try {
                image = (BufferedImage) bq.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(OCR.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ImageIO.write(image, "png", baos);
            } catch (IOException ex) {
                Logger.getLogger(OCR.class.getName()).log(Level.SEVERE, null, ex);
            }
            is = new ByteArrayInputStream(baos.toByteArray());
            ocr.setImage(ImageStream.fromStream(is, ImageStreamFormat.Png));
            if (ocr.process()) {
                System.out.println("Thread nr: " + ThreadNr + "Resultat: " + ocr.getText());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

        }
    }
}
