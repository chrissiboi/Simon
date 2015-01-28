package com.headfirst.pushTheButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 28.01.2015.
 */
public class Main {

    JFrame frame = new JFrame();

    public static void main(String[] args) {

        Main main = new Main();
        main.start();

    }

    private void start() {

        PushButton pushButton1 = new PushButton(Color.red, "button1", 50, 50 );
        PushButton pushButton2 = new PushButton(Color.red, "button2", 50, 50 );
        PushButton pushButton3 = new PushButton(Color.red, "button3", 50, 50 );
        PushButton pushButton4 = new PushButton(Color.red, "button4", 50, 50 );

        Button button = new Button("oush");
        button.setSize(20, 20);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setVisible(true);

    }

}
