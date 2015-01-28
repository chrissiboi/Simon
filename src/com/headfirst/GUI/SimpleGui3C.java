package com.headfirst.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 27.01.2015.
 */
public class SimpleGui3C implements ActionListener {

    JFrame frame;

    public static void main(String[] args){

        SimpleGui3C gui = new SimpleGui3C();
        gui.go();

    }

    public void go(){

        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Change color");
        button.addActionListener(this);

        MyDrawPanel myDrawPanel = new MyDrawPanel();

        this.frame.getContentPane().add(BorderLayout.SOUTH, button);
        this.frame.getContentPane().add(BorderLayout.CENTER, myDrawPanel);
        frame.setBackground(Color.white);
        this.frame.setSize(300, 300);
        this.frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.frame.repaint();

    }
}
