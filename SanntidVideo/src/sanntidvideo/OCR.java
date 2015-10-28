/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import com.aspose.ocr.IRecognizedPartInfo;
import com.aspose.ocr.IRecognizedText;
import com.aspose.ocr.IRecognizedTextPartInfo;
import com.aspose.ocr.ImageStream;
import com.aspose.ocr.ImageStreamFormat;
import com.aspose.ocr.OcrEngine;
import com.aspose.ocr.internal.is;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian-Heim
 */
public class OCR implements Runnable {

    public final ArrayList<Integer> note = new ArrayList<>();
    public final ArrayList<String> faultyNotes = new ArrayList<>();
    static ArrayList<BufferedImage> bilderTilAnalyse = new ArrayList<>();
    public int badNotes = 0;
    private boolean search = false;
    static String result;
    static String lastResult;

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

    private BlockingQueue imageInputQueue = null;
    private BlockingQueue noteReturnQueue;

    public OCR(String Thread, BlockingQueue queue, BlockingQueue queue1) throws InterruptedException, IOException {
        ThreadNr = Thread;
        imageInputQueue = queue;
        noteReturnQueue = queue1;
        ocr = new OcrEngine();
        showPictures = new showPicture();

    }

    @Override
    public void run() {
        while (true) {
            if (noteReturnQueue.isEmpty()) {
                search = true;
            }
            while (search = true) {
                try {
//                    millis1 = System.currentTimeMillis();
                    image = (BufferedImage) imageInputQueue.take();
                    showPictures.show(ImageIO.read(convert(image)));
                    ocr.setImage(ImageStream.fromStream(convert(image), ImageStreamFormat.Png));
                    if (ocr.process()) {
                        result = ocr.getText().toString();
                        result = result.replace("\n", "").replace("\r", "");
                        System.out.println("Thread nr: " + ThreadNr + " Resultat: " + result);
//                        millis2 = System.currentTimeMillis();
//                        System.out.println("Prosessering " + (millis2 - millis1) + " ms");
                    }
                    note.clear();
                    stringToNotes();
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(OCR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            search = true;
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

    private void stringToNotes() throws InterruptedException {

        String[] s = result.split("-");
        if (noteSwitch(s[2]) == 12) {
            faultyNotes.add(result);
            badNotes++;
        } else if (Integer.parseInt(s[1]) < 0 || Integer.parseInt(s[1]) > 8) {
            faultyNotes.add(result);
            badNotes++;
        } else if (Integer.parseInt(s[0]) < 0 || Integer.parseInt(s[0]) > 127) {
            faultyNotes.add(result);
            badNotes++;
        } else if (s.length == 3) {
            note.add(Integer.parseInt(s[0]));
            note.add(Integer.parseInt(s[1]));
            note.add(noteSwitch(s[2]));
        } else {
            faultyNotes.add(result);
            badNotes++;
        }
        noteReturnQueue.put(note);
        search = false;
    }

    private int noteSwitch(String s) {
        switch (s.toLowerCase()) {
            case "c":
                return (0);
            case "c#":
                return (1);
            case "d":
                return (2);
            case "d#":
                return (3);
            case "e":
                return (4);
            case "f":
                return (5);
            case "f#":
                return (6);
            case "g":
                return (7);
            case "g#":
                return (8);
            case "a":
                return (9);
            case "a#":
                return (10);
            case "h":
                return (11);
        }
        return (12);
    }

}
