/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanntidvideo;

import java.io.IOException;
import org.opencv.core.Core;

/**
 *
 * @author Christian
 */
public class sanntidvideo {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {

        Kommunikasjon com = new Kommunikasjon();
        com.start();

    }
}
