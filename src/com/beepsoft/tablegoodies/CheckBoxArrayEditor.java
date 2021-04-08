package com.beepsoft.tablegoodies;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CheckBoxArrayEditor extends DefaultCellEditor implements ItemListener {

    private CheckBoxArray checkBoxArray;
    private JCheckBox checkBox;
    
    public CheckBoxArrayEditor(JCheckBox cb) {
        super(cb);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null)
            return null;
        if (value.getClass().equals(CheckBoxArray.class)) {
            checkBoxArray = (CheckBoxArray) value;
            checkBoxArray.addItemListener(this);
            checkBox = null;
        } else if (value.getClass().equals(JCheckBox.class)) {
            checkBox = (JCheckBox) value;
            checkBox.addItemListener(this);
            checkBoxArray = null;
        }
        return (Component) value;
    }
    
    @Override
    public Object getCellEditorValue() {
        if (checkBox == null) {
            checkBoxArray.removeItemListener(this);
            return checkBoxArray;
        } else if (checkBoxArray == null) {
            checkBox.removeItemListener(this);
            return checkBox;
        }
        return null;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}
