package com.headfirst.GUI;

import javax.swing.*;

/**
 * Created by User on 26.01.2015.
 */
public class SimpleGui {

    public static void main (String[] args){

        JFrame frame = new JFrame();
        JButton button = new JButton("test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

}
