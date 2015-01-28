package com.headfirst.beatbox;

import javax.sound.midi.*;

/**
 * Created by User on 26.01.2015.
 */
public class MusicTest1 {

    public void play(int instrument, int note) {

        try {

            Sequencer player    = MidiSystem.getSequencer();
            player.open();
            Sequence seq        = new Sequence(Sequence.PPQ, 4);
            Track t             = seq.createTrack();

            MidiEvent event     = null;

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 10, instrument, 0);
            MidiEvent changeInstrument = new MidiEvent(first, 1);
            t.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(128, 10, note, 100);
            MidiEvent noteOn = new MidiEvent(a, 3);
            t.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, note, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            t.add(noteOff);

            player.setSequence(seq);
            player.start();

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        MusicTest1 test = new MusicTest1();
        int instr = Integer.parseInt(args[0]);
        int note  = Integer.parseInt(args[1]);
        test.play(instr, note);
    }
}
