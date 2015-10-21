package sanntidvideo;

import java.util.ArrayList;
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

    public boolean RefreshArrays(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
        octaves.clear();
        notes.clear();
        volumes.clear();
        octaves = b;
        notes = c;
        volumes = a;
        return (true);
    }

    public void ConvertToMidiCodes() {
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

    public void MidiCodesToStart() {
        if (ConsistencyCheck(midiCodes, volumes)) {
            midiToStart.clear();
            for (int i = 0; i < midiCodes.size(); i++) {
                if (!lastMidiCodes.contains(midiCodes.get(i))) {
                    midiToStart.add(midiCodes.get(i));
                }
            }
        } else {
            fuckUp++;
        }
    }

    public void MidiCodesToStop() {
        if (ConsistencyCheck(midiCodes, volumes)) {
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
        else{
            fuckUp++;
        }
    }

    public boolean ConsistencyCheck(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c) {
        if (a.size() == b.size() && a.size() == c.size()) {
            return (true);
        }
        return (false);
    }

    public boolean ConsistencyCheck(ArrayList<Integer> a, ArrayList<Integer> b) {
        if (a.size() == b.size()) {
            return (true);
        }
        return (false);
    }

    public void StartNote() {
        for (int i : midiToStart) {
            channels[channel].noteOn(midiToStart.get(i), volumesToStart.get(i));
        }
    }

    public void EndNote(ArrayList<Integer> midiCode) {
        for (int i : midiCode) {
            channels[channel].noteOff(midiCode.get(i));
        }
    }

    public void EndAllNotes() {
        channels[channel].allNotesOff();
    }
}
