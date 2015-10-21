/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Christian
 */
public class showPicture extends JFrame {
    JLabel label;
    ImageIcon icon;
    
    public showPicture(){
        this.setVisible(true);
        this.setSize(640, 320);
        label = new JLabel();  
        this.add(label);
        label.setVisible(true);
    }
    public void show(BufferedImage img){
        icon = new ImageIcon();
        icon.setImage(img);
        label.setIcon(icon);
    }
}
