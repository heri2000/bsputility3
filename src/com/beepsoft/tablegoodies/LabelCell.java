/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beepsoft.tablegoodies;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author heri
 */
public class LabelCell extends JLabel {

    private Color foregroundColor = null;
    private Color backgroundColor = null;

    public LabelCell() {
        foregroundColor = Color.black;
        backgroundColor = Color.white;
        setOpaque(true);
    }

    public LabelCell(Color foreground, Color background) {
        if (foreground != null) {
            this.foregroundColor = foreground;
        } else {
            this.foregroundColor = Color.black;
        }
        if (background != null) {
            this.backgroundColor = background;
        } else {
            this.foregroundColor = Color.white;
        }
        setForeground(this.foregroundColor);
        setBackground(this.backgroundColor);
        setOpaque(true);
    }

    public LabelCell(String text, Color foreground, Color background,
            int horizontalAlignment, Font font) {
        if (foreground != null) {
            this.foregroundColor = foreground;
        } else {
            this.foregroundColor = Color.black;
        }
        if (background != null) {
            this.backgroundColor = background;
        } else {
            this.foregroundColor = Color.white;
        }
        setForeground(this.foregroundColor);
        setBackground(this.backgroundColor);
        setOpaque(true);
        if (text != null) {
            setText(text);
        }
        setHorizontalAlignment(horizontalAlignment);
        if (font != null) {
            setFont(font);
        }
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
            repaint();
        }
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
            repaint();
        }
    }
    
}
