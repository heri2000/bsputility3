package com.beepsoft.ptools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class SubPanel {
    
    public static void paintSubPanel(JPanel panel, Graphics g, boolean bottomBevel) {
        Dimension d = panel.getSize();
        int red = panel.getBackground().getRed();
        int green = panel.getBackground().getGreen();
        int blue = panel.getBackground().getBlue();
        g.setColor(new Color((int)(red*0.7), (int)(green*0.7), (int)(blue*0.7)));
        // Gambar background
        g.fillRect(0, 0, 10, d.height);
        g.fillRect(d.width-9, 0, 9, d.height);
        g.fillRect(0, d.height-10, d.width, 10);
        // Gambar sudut-sudut
        g.drawLine(10, d.height-11, 10, d.height-11);
        g.drawLine(10, d.height-12, 11, d.height-11);
        g.drawLine(d.width-10, d.height-11, d.width-10, d.height-11);
        g.drawLine(d.width-10, d.height-12, d.width-11, d.height-11);
        
        // Gambar drop-shadow di background
        g.setColor(new Color((int)(red*0.4), (int)(green*0.4), (int)(blue*0.4)));
        g.fillRect(0, 0, 10, 2);
        g.fillRect(d.width-10, 0, 10, 2);
        g.setColor(new Color((int)(red*0.5), (int)(green*0.5), (int)(blue*0.5)));
        g.fillRect(0, 2, 10, 2);
        g.fillRect(d.width-10, 2, 10, 2);
        g.setColor(new Color((int)(red*0.6), (int)(green*0.6), (int)(blue*0.6)));
        g.fillRect(0, 4, 10, 2);
        g.fillRect(d.width-10, 4, 10, 2);
        if (bottomBevel) {
            g.drawLine(0, d.height-3, d.width, d.height-3);
        }
        
        // Gambar drop-shadow di foreground
        g.setColor(new Color((int)(red*0.7), (int)(green*0.7), (int)(blue*0.7)));
        g.drawLine(10, 0, d.width-11, 0);
        g.setColor(new Color((int)(red*0.8), (int)(green*0.8), (int)(blue*0.8)));
        g.drawLine(10, 1, d.width-11, 1);
        g.setColor(new Color((int)(red*0.9), (int)(green*0.9), (int)(blue*0.9)));
        g.drawLine(10, 2, d.width-11, 2);

        // Gambar dark-bevel
        g.setColor(new Color((int)(red*0.5), (int)(green*0.5), (int)(blue*0.5)));
        g.drawLine(d.width-10, 0, d.width-10, d.height-13);
        g.drawLine(d.width-10, d.height-13, d.width-12, d.height-11);
        g.drawLine(12, d.height-11, d.width-12, d.height-11);
        g.setColor(new Color((int)(red*0.7), (int)(green*0.7), (int)(blue*0.7)));
        g.drawLine(d.width-11, 0, d.width-11, d.height-13);
        g.drawLine(d.width-11, d.height-14, d.width-12, d.height-12);
        g.drawLine(11, d.height-12, d.width-12, d.height-12);
        
        // Gambar light-bevel
        red += 80;
        green += 80;
        blue += 80;
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;
        g.setColor(new Color(red, green, blue));
        g.drawLine(11, 3, 11, d.height-14);
        if (bottomBevel) {
            g.drawLine(0, d.height-1, d.width, d.height-1);
        }

        red += 80;
        green += 80;
        blue += 80;
        if (red > 255) red = 255;
        if (green > 255) green = 255;
        if (blue > 255) blue = 255;
        g.setColor(new Color(red, green, blue));
        g.drawLine(10, 3, 10, d.height-15);
        g.drawLine(10, d.height-13, 11, d.height-12);
        if (bottomBevel) {
            g.drawLine(0, d.height-2, d.width, d.height-2);
        }
    }
    
}
