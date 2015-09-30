package sanntidvideo;

import java.util.ArrayList;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author Ivar
 */
public class Midi {

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

    public void StartNote(ArrayList<Integer> midiCode, ArrayList<Integer> volume) {
        for (int i : midiCode) {
            channels[channel].noteOn(midiCode.get(i), volume.get(i));
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
