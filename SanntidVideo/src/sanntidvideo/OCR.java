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
    InputStream is;
    BufferedImage image;
    double millis1;
    double millis2;
    double millis3;
    double millis4;
    String Result;
    showPicture showPictures;
    public boolean CLOSE;

    private BlockingQueue bq = null;

    public OCR(String Thread, BlockingQueue queue) throws InterruptedException, IOException {
        ThreadNr = Thread;
        bq = queue;
        ocr = new OcrEngine();

    }

    @Override
    public void run() {
        showPictures = new showPicture();
        while (true) {
            try {
                millis1 = System.currentTimeMillis();
                image = (BufferedImage) bq.take();
                showPictures.show(ImageIO.read(convert(image)));
                ocr.setImage(ImageStream.fromStream(convert(image), ImageStreamFormat.Png));
                if (ocr.process()) {
                    System.out.println("Thread nr: " + ThreadNr + " Resultat: " + ocr.getText());
                    millis2 = System.currentTimeMillis();
                    System.out.println("Prosessering " + (millis2 - millis1) + " ms");
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(OCR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private InputStream convert(BufferedImage img) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            is = new ByteArrayInputStream(baos.toByteArray());

        } catch (IOException ex) {
        }
        return is;
    }
}
