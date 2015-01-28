package com.headfirst.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 26.01.2015.
 */
public class drawWidget extends JPanel{

    public static void main(String[] args){

        JFrame frame = new JFrame();
        frame.setSize(300,300);
        frame.setVisible(true);
        JPanel panel = new JPanel(new GridLayout(0,2));
        panel.add(new drawWidget());

    }

    @Override
    public void paintComponent(Graphics g){

        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);

        Color color = new Color(red, green, blue);

        red = (int) (Math.random() * 255);
        green = (int) (Math.random() * 255);
        blue = (int) (Math.random() * 255);

        Color secondColor = new Color(red, green, blue);

        GradientPaint gradientPaint = new GradientPaint(70, 70, color, 150, 150, secondColor);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillOval(70, 70, 100, 100);

    }
}
