package com.headfirst.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 27.01.2015.
 */
public class MyDrawPanel extends JPanel{

    @Override
    public void paintComponent(Graphics g){

        int red = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);

        Color color = new Color(red, blue, green);

        Graphics2D graphics2D = (Graphics2D) g;
        GradientPaint gradientPaint = new GradientPaint(70, 70, color,  150, 150, Color.orange);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillOval(70, 70, 100, 100);
    }

}
