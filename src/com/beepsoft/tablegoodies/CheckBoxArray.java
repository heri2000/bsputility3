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
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Heri
 */
public class CheckBoxArray extends JPanel {

    private JCheckBox[] checkBoxes;

    public CheckBoxArray(String[] labels) {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(5);
        layout.setVgap(0);
        setLayout(layout);
        checkBoxes = new JCheckBox[labels.length];
        ButtonGroup bgroup = new ButtonGroup();
        for (int i = 0; i < labels.length; i++) {
            checkBoxes[i] = new JCheckBox(labels[i]);
            checkBoxes[i].setOpaque(false);
            //radioButtons[i].setMargin(new Insets(5, 5, 5, 5));
            bgroup.add(checkBoxes[i]);
            add(checkBoxes[i]);
        }
    }

    public void addItemListener(ItemListener itemListener) {
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].addItemListener(itemListener);
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (checkBoxes == null) {
            return;
        }
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].setFont(font);
        }
    }

    public void setSelected(int selection) {
        if (checkBoxes.length > 0 && selection >= 0 && selection < checkBoxes.length) {
            checkBoxes[selection].setSelected(true);
        }
    }

    public boolean[] getSelections() {
        boolean[] selections = new boolean[checkBoxes.length];
        for (int i = 0; i < checkBoxes.length; i++) {
            selections[i] = checkBoxes[i].isSelected();
        }
        return selections;
    }

    public JCheckBox[] getRadioButtons() {
        return checkBoxes;
    }

    public void removeItemListener(ItemListener itemListener) {
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i].removeItemListener(itemListener);
        }
    }
}
