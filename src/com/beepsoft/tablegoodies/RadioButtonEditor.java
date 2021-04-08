package com.beepsoft.tablegoodies;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

public class RadioButtonEditor extends DefaultCellEditor implements ItemListener {

    private RadioButtonArray buttonArray;
    private JRadioButton radioButton;
    
    public RadioButtonEditor(JCheckBox cb) {
        super(cb);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null)
            return null;
        if (value.getClass().equals(RadioButtonArray.class)) {
            buttonArray = (RadioButtonArray) value;
            buttonArray.addItemListener(this);
            radioButton = null;
        } else if (value.getClass().equals(JRadioButton.class)) {
            radioButton = (JRadioButton) value;
            radioButton.addItemListener(this);
            buttonArray = null;
        }
        return (Component) value;
    }
    
    @Override
    public Object getCellEditorValue() {
        if (radioButton == null) {
            buttonArray.removeItemListener(this);
            return buttonArray;
        } else if (buttonArray == null) {
            radioButton.removeItemListener(this);
            return radioButton;
        }
        return null;
    }
    
    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}
