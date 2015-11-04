package sanntidvideo;

import javafx.scene.media.*;

public class VideoPlayer{
    
    private final MediaPlayer player;
    private final Media file = new Media("video.wmv");
    
    
    
    public VideoPlayer(){
        player = new MediaPlayer(file);
        System.out.println(player.getVolume());
        
        
    }
    
    
    
    
    
    
}