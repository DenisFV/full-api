package ru.denisfv.fullapi.spring.upis.bad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@Component
public class ColorFrame extends JFrame {

    @Autowired
    private ApplicationContext context; // инжектиться контекст, и т.о. бин становтися зависм от всего контекста

    public ColorFrame() {
        setSize(200, 200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void showOnRandomPlace() {
        Random random = new Random();
        setLocation(random.nextInt(1800), random.nextInt(700));
        getContentPane().setBackground(context.getBean(Color.class)); // и все из-за 1 бина
        repaint();
    }
}
