/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.BlockingQueue;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_HEIGHT;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_WIDTH;

public class VideoCap implements Runnable {

    Mat img = new Mat();
    Mat imgGray = new Mat();
    private final VideoCapture cap;
    private BlockingQueue bq = null;
    public boolean CLOSE;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public VideoCap(BlockingQueue queue) {
        bq = queue;
        cap = new VideoCapture();
        cap.open(0);
        System.out.println(cap.get(CV_CAP_PROP_FRAME_WIDTH) + " " + cap.get(CV_CAP_PROP_FRAME_HEIGHT));

        System.out.println("Camera status: " + cap.isOpened());

    }

    @Override
    public void run() {
        while (true) {
            cap.read(img);
            bq.offer(toBufferedImage(img));
        }
        
    }
    public void close() {
        cap.release();
    }

    

    public Image toBufferedImage(Mat m) {
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
}
