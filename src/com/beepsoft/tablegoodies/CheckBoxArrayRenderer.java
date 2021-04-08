package com.beepsoft.tablegoodies;

import java.awt.Component;
import javax.swing.JCheckBox;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxArrayRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null)
            return null;
        if (value.getClass().equals(CheckBoxArray.class)) {
            CheckBoxArray v = (CheckBoxArray) value;
            if (isSelected) {
                v.setBackground(table.getSelectionBackground());
            } else {
                v.setBackground(table.getBackground());
            }
            return v;
        } else if (value.getClass().equals(JCheckBox.class)) {
            JCheckBox v = (JCheckBox) value;
            if (isSelected) {
                v.setBackground(table.getSelectionBackground());
            } else {
                v.setBackground(table.getBackground());
            }
        }
        return (Component) value;
    }
    
}