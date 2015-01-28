package com.headfirst.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 27.01.2015.
 */
public class AnimationBall implements ActionListener{

    JFrame frame    =   new JFrame();
    int     x       =   40;
    int     y       =   40;

    public static void main(String[] args) {

        AnimationBall animationBall = new AnimationBall();
        animationBall.go();

    }

    public void go() {

        MyDrawPanel drawingBall = new MyDrawPanel();
        Button button = new Button("stop");

        button.addActionListener(this);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().add(drawingBall);
        this.frame.getContentPane().add(BorderLayout.SOUTH, button);

        this.frame.setSize(300, 300);
        this.frame.setVisible(true);

        for (int i = 0; i < 130; i++) {
            x = i;
            y = x * x;

            drawingBall.repaint();

            try{

                Thread.sleep(50);

            } catch(Exception ex){

                ex.printStackTrace();

            }

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.x = 140;
        this.y = 140;

    }

    class MyDrawPanel extends JPanel{

        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.white);
            g.fillOval(x-1, y-1, 2, 2);
            g.setColor(Color.black);
            g.fillOval(x, y, 2, 2);
        }

    }
}
