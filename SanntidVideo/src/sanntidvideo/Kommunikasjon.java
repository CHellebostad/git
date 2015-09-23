/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.io.*;
import java.net.*;

/**
 *
 * @author Christian
 */
public class Kommunikasjon {

    Socket socketSend;
    ObjectOutputStream videoFrame;

    public Kommunikasjon() {
       connect();
       try{
       videoFrame = new ObjectOutputStream(socketSend.getOutputStream());
       }
       catch(IOException e){
           System.out.println("No output object");
       }
       
       try{
       videoFrame.writeInt(10);
       }
       catch(IOException e){
       }
       
    }
    
    private void connect(){
         try{
        socketSend = new Socket("hostname",2015);
        }
        catch(IOException e){
            System.out.println("Connection failed");            
        }
    }
    
    private void close(){
        try{
        socketSend.close();
        }
        catch(IOException e){
            System.out.println("Close failed");
        }
    }
}
