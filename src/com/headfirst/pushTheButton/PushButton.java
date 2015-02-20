package com.headfirst.pushTheButton;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.awt.*;

/**
 * Created by User on 28.01.2015.
 */
public class PushButton extends Button{

    private Color color;
    private String name;
    private int buttonID;
    int instrument;

    public PushButton(Color color, String name, int instrument) {
        super(name);

        this.color = color;
        this.setBackground(color);
        this.name = name;
        this.instrument = instrument;

    }

    public void buttonPressed(Sequencer sequencer, Track track, Sequence seq){
        try {

            track.add(makeEvent(144, 8, instrument, 100, 1));
            sequencer.setSequence(seq);

        } catch(InvalidMidiDataException e) {

            e.printStackTrace();

        }

        sequencer.start();
        brightButton();
        sequencer.stop();

    }

    public void brightButton() {

        this.setBackground(this.color.darker());

        try {
            Thread.sleep(200);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        this.setBackground(this.color.brighter());
        try {
            Thread.sleep(200);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public MidiEvent makeEvent (int comd, int chan, int one, int two, int tick){

        MidiEvent event = null;
        try{

            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        }catch(Exception ex) {

            ex.printStackTrace();

        }

        return event;

    }

    public String getName(){

        return this.name;

    }

    public int getButtonID(){

        return Integer.parseInt(this.name.substring(this.name.length()-1));

    }
}
