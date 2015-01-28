package com.headfirst.beatbox;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 28.01.2015.
 */
public class MyDrawPanel extends JPanel implements ControllerEventListener{

    boolean msg = false;

    @Override
    public void controlChange(ShortMessage event) {

        msg = true;
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
            int x = (int) ((Math.random() * 40) + 10);
            int y = (int) ((Math.random() * 40) + 10);
            g.fillRect(x, y, ht, widht);
            msg = false;

        }

    }

}
