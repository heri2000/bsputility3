/*
 * RadioButtonArray.java
 *
 * Created on January 7, 2009, 10:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.beepsoft.tablegoodies;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Heri
 */
public class ButtonArray extends JPanel {

    private JButton[] buttons;

    public ButtonArray(String[] labels, ActionListener actionListener) {
        GridLayout layout = new GridLayout(1, labels.length);
        setLayout(layout);
        buttons = new JButton[labels.length];
        ButtonGroup bgroup = new ButtonGroup();
        for (int i = 0; i < labels.length; i++) {
            buttons[i] = new JButton(labels[i]);
            buttons[i].setFont(getFont().deriveFont((float) getFont().getSize() - 1));
            buttons[i].setMargin(new Insets(1, 1, 1, 1));
            buttons[i].addActionListener(actionListener);
            buttons[i].setFocusPainted(false);
            bgroup.add(buttons[i]);
            add(buttons[i]);
        }
    }

    public void addActionListener(ActionListener actionListener) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(actionListener);
        }
    }

    public void addItemListener(ItemListener itemListener) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addItemListener(itemListener);
        }
    }

    public void removeItemListener(ItemListener itemListener) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].removeItemListener(itemListener);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (buttons == null) {
            return;
        }
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(font);
        }
    }
}
