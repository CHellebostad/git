package sanntidvideo;

import java.io.File;

import net.sourceforge.tess4j.*;
import java.util.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import jdk.nashorn.internal.ir.CatchNode;


/**
 *
 * @author Ivar
 */
public class Analyse {

    private final ArrayList<String> splittedString = new ArrayList<>();
    private final ArrayList<String> volumes = new ArrayList<>();
    private final ArrayList<String> octaves = new ArrayList<>();
    private final ArrayList<String> notesString = new ArrayList<>();
    private final ArrayList<Integer> notes = new ArrayList<>();
    private final ArrayList<String> faultyNotes = new ArrayList<>();
    public int badNotes = 0;
    static String result;
    static String lastResult;

    public void picToNotes(BufferedImage image) throws TesseractException {

        Tesseract instance = Tesseract.getInstance();
        result = instance.doOCR(image);
        result = result.replace("\n", "").replace("\r", "");
        if (result == null ? lastResult != null : !result.equals(lastResult)) {
            splittedString.clear();
            volumes.clear();
            octaves.clear();
            notes.clear();
            picToString();
            stringToNotes();
            stringToInt();
        }
        lastResult = result;
    }

    private void picToString() {
        String[] strings = result.split("@");
        splittedString.addAll(Arrays.asList(strings));
    }

    private void stringToNotes() {
        for (Iterator<String> iterator = splittedString.iterator(); iterator.hasNext();) {
            String string = iterator.next();
            String[] s = string.split("-");
            if (s.length == 3) {
                // Remove the current element from the iterator and the list.
                volumes.add(s[0]);
                octaves.add(s[1]);
                notesString.add(s[2]);
            }
            else if(noteSwitch(s[2]) == 12){
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
            else if(noteSwitch(s[1]) < 0 || noteSwitch(s[1]) > 8){
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
            else if(noteSwitch(s[0]) < 0 || noteSwitch(s[0]) > 127){
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
            else {
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
        }
    }

    private void stringToInt() {
        for (String s : notesString) {
            notes.add(noteSwitch(s));
        }
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
