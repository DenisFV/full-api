package ru.denisfv.fullapi.spring.upis.good;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class ColorFrame extends JFrame {

    protected abstract Color getColor();

    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1800), random.nextInt(700));
        getContentPane().setBackground(getColor());
        repaint();
    }
}
