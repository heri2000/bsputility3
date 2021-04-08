/*
 * RadioButtonArray.java
 *
 * Created on January 7, 2009, 10:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.beepsoft.tablegoodies;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Heri
 */
public class RadioButtonArray extends JPanel {

    private JRadioButton[] radioButtons;

    public RadioButtonArray(String[] labels, int value) {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        layout.setVgap(0);
        setLayout(layout);
        radioButtons = new JRadioButton[labels.length];
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < labels.length; i++) {
            radioButtons[i] = new JRadioButton(labels[i]);
            radioButtons[i].setOpaque(false);
            radioButtons[i].setFont(new Font("Lucida Sans", Font.PLAIN, 12));
            //radioButtons[i].setMargin(new Insets(5, 5, 5, 5));
            buttonGroup.add(radioButtons[i]);
            add(radioButtons[i]);
        }
        if (radioButtons.length > 0 && value >= 0
                && value < radioButtons.length) {
            radioButtons[value].setSelected(true);
        } else {
            for (int i = 0; i < radioButtons.length; i++) {
                radioButtons[i].setSelected(false);
            }
        }
    }

    public void addItemListener(ItemListener itemListener) {
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].addItemListener(itemListener);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (radioButtons == null) {
            return;
        }
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].setFont(font);
        }
    }

    @Deprecated
    public void setValue(int value) {
        setSelected(value);
    }

    public void setSelected(int selection) {
        if (radioButtons.length > 0 && selection >= 0 && selection < radioButtons.length) {
            radioButtons[selection].setSelected(true);
        } else {
            for (int i = 0; i < radioButtons.length; i++) {
                radioButtons[i].setSelected(false);
            }
        }
    }

    @Deprecated
    public int getValue() {
        return getSelection();
    }

    public int getSelection() {
        int val = -1;

        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isSelected()) {
                val = i;
            }
        }

        return val;
    }

    public JRadioButton[] getRadioButtons() {
        return radioButtons;
    }

    public void removeItemListener(ItemListener itemListener) {
        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i].removeItemListener(itemListener);
        }
    }
}
