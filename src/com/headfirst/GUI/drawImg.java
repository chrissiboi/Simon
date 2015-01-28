package com.headfirst.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by User on 27.01.2015.
 */
public class drawImg extends JPanel{

    public void paintComponent(Graphics g) {

        Image image = new ImageIcon("test.jpeg").getImage();
        g.drawImage(image, 3, 4, this);

    }

}
