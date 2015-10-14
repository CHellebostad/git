package sanntidvideo;

import net.sourceforge.tess4j.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Ivar
 */
public class Analyse implements Runnable {

    private final ArrayList<String> splittedString = new ArrayList<>();
    public final ArrayList<String> volumesString = new ArrayList<>();
    public final ArrayList<String> octavesString = new ArrayList<>();
    private final ArrayList<String> notesString = new ArrayList<>();
    public final ArrayList<Integer> octaves = new ArrayList<>();
    public final ArrayList<Integer> notes = new ArrayList<>();
    public final ArrayList<Integer> volumes = new ArrayList<>();
    public final ArrayList<String> faultyNotes = new ArrayList<>();
    public int badNotes = 0;
    static String result;
    static String lastResult;
    static BufferedImage image;

    Analyse(BufferedImage im) {
        image=im;
    }
   
    @Override
    public void run() {
        
    }
    
    public void picToNotes() throws TesseractException {
        Tesseract instance = Tesseract.getInstance();
        result = instance.doOCR(image);
        result = result.replace("\n", "").replace("\r", "");
        if (result == null ? lastResult != null : !result.equals(lastResult)) {
            splittedString.clear();
            volumesString.clear();
            octavesString.clear();
            notes.clear();
            picToString();
            stringToNotes();
            noteStringToInt();
            octaveStringToInt();
            volumeStringToInt();
        }
        lastResult = result;
    }

    private void picToString() {
        String[] strings = result.split("@");
        splittedString.addAll(Arrays.asList(strings));
    }

    private void stringToNotes() {
        Iterator<String> iterator = splittedString.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            String[] s = string.split("-");
            if (noteSwitch(s[2]) == 12) {
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            } else if (Integer.parseInt(s[1]) < 0 || Integer.parseInt(s[1]) > 8) {
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            } else if (Integer.parseInt(s[0]) < 0 || Integer.parseInt(s[0]) > 127) {
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
            else if (s.length == 3) {
                volumesString.add(s[0]);
                octavesString.add(s[1]);
                notesString.add(s[2]);
            }
            else {
                faultyNotes.add(string);
                badNotes++;
                iterator.remove();
            }
        }
    }

    private void noteStringToInt() {
        for (String s : notesString) {
            notes.add(noteSwitch(s));
        }
    }
    private void octaveStringToInt() {
        for (String s : octavesString) {
            octaves.add(Integer.parseInt(s));
        }
    }
    
    private void volumeStringToInt() {
        for (String s : volumesString) {
            volumes.add(Integer.parseInt(s));
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

    public ArrayList<Integer> getVolumes() {
        return volumes;
    }
    public ArrayList<Integer> getOctaves() {
        return octaves;
    }
    public ArrayList<Integer> getNotes() {
        return notes;
    }

    
}
