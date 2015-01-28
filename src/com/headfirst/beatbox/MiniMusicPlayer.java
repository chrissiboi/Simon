package com.headfirst.beatbox;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 28.01.2015.
 */
public class MiniMusicPlayer {

    static JFrame f = new JFrame("My first Music Video");
    static MyDrawPanel ml;
    static int counter = 0;
    static int x = 50;
    static int y = 50;
    public JLabel myText;


    public static void main(String[] args) {

        MiniMusicPlayer mini = new MiniMusicPlayer();

        mini.go();

    }

    public void setUpGui() {

        ml = new MyDrawPanel();
        f.setContentPane(ml);

        f.setSize(300, 300);
        f.setVisible(true);

    }

    public void go() {

        setUpGui();

        try {

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            sequencer.addControllerEventListener(this.ml, eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for(int i = 5; i < 53; i += 4) {
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();

        } catch(Exception ex) {

            ex.printStackTrace();

        }

    }
    public static MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick) {

        MidiEvent event = null;

        try {

            ShortMessage a = new ShortMessage();
            a.setMessage(cmd, chan, one, two);
            event = new MidiEvent(a, tick);


        } catch(Exception ex){

            ex.printStackTrace();

        }

        return event;

    }

    public class MyDrawPanel extends JPanel implements ControllerEventListener{

        boolean msg = false;
        String text = "IloveyouMaus";


        @Override
        public void controlChange(ShortMessage event) {

            int ht = 350;
            int widht = 50;

            myText = new JLabel(text.charAt(counter) + "");
            myText.setBounds(x,y, ht, widht);
            f.add(myText);

            counter++;
            x += 10;
            //msg = true;
           repaint();

        }

        public void paintComponent(Graphics g) {

            if(msg) {

                Graphics2D g2 = (Graphics2D) g;

                int r = (int) (Math.random() * 255);
                int b = (int) (Math.random() * 255);
                int gr = (int) (Math.random() * 255);

                g.setColor(new Color(r, b, gr));

                int ht = (int) ((Math.random() * 120) + 10);
                int widht = (int) ((Math.random() * 120) + 10 );
                int x = (int) ((Math.random() * 180) + 10);
                int y = (int) ((Math.random() * 180) + 10);
                g.fillRect(x, y, ht, widht);
                msg = false;

            }

        }

    }
}
