package sanntidvideo;

import java.util.Iterator;
import java.util.ArrayList;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author Ivar
 */
public class Avspilling {

    public ArrayList<Integer> octaves = new ArrayList<>();
    public ArrayList<Integer> notes = new ArrayList<>();
    public ArrayList<Integer> volumes = new ArrayList<>();
    public ArrayList<Integer> volumesToStart = new ArrayList<>();
    public final ArrayList<Integer> midiCodes = new ArrayList<>();
    public ArrayList<Integer> lastMidiCodes = new ArrayList<>();
    public ArrayList<Integer> midiToStop = new ArrayList<>();
    public ArrayList<Integer> midiToStart = new ArrayList<>();
    public int fuckUp = 0;
    int channel = 1;

    private static MidiChannel[] channels;

    static {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void MidiCycle(ArrayList<ArrayList<Integer>> ar) {
        retireMidiCodes();
        RefreshArrays(ar);
        ConvertToMidiCodes();
        MidiCodesToStop();
        MidiCodesToStart();
        EndNote();
        StartNote();
    }
    
    private void RefreshArrays(ArrayList<ArrayList<Integer>> ar) {
        octaves.clear();
        notes.clear();
        volumes.clear();
        for (ArrayList<Integer> arr : ar) {
            volumes.add(arr.get(0));
            octaves.add(arr.get(1));
            notes.add(arr.get(2));
        }
        
    }

    private void ConvertToMidiCodes() {
        if (ConsistencyCheck(volumes, notes, octaves)) {
            lastMidiCodes.clear();
            lastMidiCodes = midiCodes;
            midiCodes.clear();
            for (int i = 0; i < notes.size(); i++) {
                int j = 24 + (octaves.get(i) * 12) + notes.get(i);
                midiCodes.add(j);
            }
        } else {
            fuckUp++;
        }
    }

    private void MidiCodesToStart() {
            midiToStart.clear();
            for (int i = 0; i < midiCodes.size(); i++) {
                if (!lastMidiCodes.contains(midiCodes.get(i))) {
                    midiToStart.add(midiCodes.get(i));
                }
            }
        } 

    private void MidiCodesToStop() {
            midiToStop.clear();
            midiToStop = midiCodes;
            Iterator<Integer> iterator = midiToStop.iterator();
            while (iterator.hasNext()) {
                int integ = iterator.next();
                if (lastMidiCodes.contains(integ)) {
                    iterator.remove();
                }
            }
        } 

    private void retireMidiCodes() {
        lastMidiCodes = midiCodes;
    }

    private boolean ConsistencyCheck(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
        if (a.size() == b.size() && a.size() == c.size()) {
            return (true);
        }
        return (false);
    }

    private void StartNote() {
        for (int i : midiToStart) {
            channels[channel].noteOn(midiToStart.get(i), volumesToStart.get(i));
        }
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
}
