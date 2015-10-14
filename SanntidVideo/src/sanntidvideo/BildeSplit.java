package sanntidvideo;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Ivar
 */
public class BildeSplit {
    
    public ArrayList<BufferedImage> Split(BufferedImage orgBilde) throws IOException {
        
        ArrayList<BufferedImage> bilder = new ArrayList<>();
        
        int rader = 3;
        int kolonner = 2;
        int bildeBit = rader * kolonner;
        
        int splittetBredde = orgBilde.getWidth() / kolonner;        
        int splittetHøyde = orgBilde.getHeight() / rader;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[bildeBit];        
        for (int x = 0; x < rader; x++) {
            for (int y = 0; y < kolonner; y++) {
                imgs[count] = new BufferedImage(splittetBredde, splittetHøyde, orgBilde.getType());
                
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(orgBilde, 0, 0, splittetBredde, splittetHøyde, splittetBredde * y, splittetHøyde * x, splittetBredde * y + splittetBredde, splittetHøyde * x + splittetHøyde, null);
                gr.dispose();
            }
        }
        for (int i = 0; i < imgs.length; i++) {            
            bilder.add(imgs[i]);            
        }
        return bilder;
    }
    
}
