/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.io.IOException;

/**
 *
 * @author Christian
 */
public class sanntidvideo extends Thread{
    
    public static void main(String[] args) throws IOException{
//        VideoCap capture = new VideoCap();
//        capture.start();
//        Avspilling avspilling = new Avspilling();
//        Analyse analyse = new Analyse();
        Kommunikasjon com = new Kommunikasjon();
        try{
        com.makeServer();
        }catch(Exception e){}
        while(true){
        com.sendVideo();
        }
    }
}
