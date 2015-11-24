package sanntidvideo;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author Ivar
 */
public class Avspilling extends TimerTask{

    public ArrayList<Integer> octaves = new ArrayList<>();
    public ArrayList<Integer> notes = new ArrayList<>();
    public ArrayList<Integer> volumes = new ArrayList<>();
    public ArrayList<Integer> volumesToStart = new ArrayList<>();
    public final ArrayList<Integer> midiCodes = new ArrayList<>();
    private final ArrayList<Integer> lastMidiCodes = new ArrayList<>(Collections.nCopies(7, 0));
    private final ArrayList<Integer> midiToStop = new ArrayList<>(Collections.nCopies(7, 0));
    public ArrayList<Integer> midiToStart = new ArrayList<>();
    public int fuckUp = 0;
    int channel = 1;
    public final ArrayList<String> faultyNotes = new ArrayList<>();
    public int badNotes = 0;
    private static MidiChannel[] channels;
    BlockingQueue bq;
    private ArrayList<String> inputQueue = new ArrayList<>();

    static {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public Avspilling(BlockingQueue queue){
        bq = queue;
    }

    @Override
    public void run() {
        try {
            inputQueue = (ArrayList<String>) bq.take();
            stringToNotes(inputQueue);
            inputQueue.clear();
            ConvertToMidiCodes();
            MidiCodesToStart();
//            MidiCodesToStop();
//            EndNote();
            StartNote();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
    }

    private void ConvertToMidiCodes() {
        midiCodes.clear();
        for (int i = 0; i < notes.size(); i++) {
            int j = 24 + (octaves.get(i) * 12) + notes.get(i);
            midiCodes.add(j);
        }
//        System.out.println("Curr: " + midiCodes);
//        System.out.println("Last: "+lastMidiCodes);
    }

    private void MidiCodesToStart() {
      
        for(int i=0;i<midiCodes.size();i++){
            
            if (!Objects.equals(midiCodes.get(i), lastMidiCodes.get(i))) {
//                System.out.println("Midicode: " + midiCodes.get(i));
                midiToStart.add(midiCodes.get(i));
//                System.out.println("MidiToStart: " + midiToStart.get(i));
            }
            lastMidiCodes.set(i, midiCodes.get(i));
        }
//        for(int y = 0;y<midiCodes.size();y++){
//          lastMidiCodes.add(y, midiCodes.get(y));
//        }


    }

    private void MidiCodesToStop() {
        midiToStop.clear();
        for (int i=0;i<midiCodes.size();i++) {
            System.out.println(midiCodes.size());
        midiToStop.add(midiCodes.get(i));
    }
        Iterator<Integer> iterator = midiToStop.iterator();
        while (iterator.hasNext()) {
            int integ = iterator.next();
            if (lastMidiCodes.contains(integ)) {
                iterator.remove();
            }
        }

    }

//    private boolean ConsistencyCheck(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
//        if (a.size() == b.size() && a.size() == c.size()) {
//            return (true);
//        }
//        return (false);
//    }
    private void StartNote() {
        if (!midiCodes.isEmpty()) {
            for (int i = 0; i < midiToStart.size(); i++) {
                channels[channel].noteOn(midiToStart.get(i), volumes.get(i));
            }
        }
        midiToStart.clear();
    }

    private void EndNote() {
        if (midiCodes.isEmpty()) {
            EndAllNotes();
        } else {
            for (int i : midiToStop) {
                channels[channel].noteOff(midiToStop.get(i));
            }
        }
    }

    public void EndAllNotes() {
        channels[channel].allNotesOff();
    }

    private void stringToNotes(ArrayList<String> ar) throws InterruptedException {
        volumes.clear();
        octaves.clear();
        notes.clear();
        for (String result : ar) {
            
            String[] s = result.split("-");
//            System.out.println(replacedResult);
//            System.out.println(s.length);
            try{
            if (s.length > 2) {
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
                    volumes.add(Integer.parseInt(s[0]));
                    octaves.add(Integer.parseInt(s[1]));
                    notes.add(noteSwitch(s[2]));
                } else {
                    faultyNotes.add(result);
                    badNotes++;
                }
            } else {
//                System.out.println("Incomplete text");

            }
            }catch(Exception e){
                System.out.println(e);
            }
        }
//        System.out.println(volumes);
//        System.out.println(octaves);
//        System.out.println(notes);
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
            case "b":
                return (11);
        }
        return (12);
    }

    

    
}
