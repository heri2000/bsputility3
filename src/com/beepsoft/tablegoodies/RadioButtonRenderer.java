package com.beepsoft.tablegoodies;

import java.awt.Component;
import javax.swing.JRadioButton;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RadioButtonRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null)
            return null;
        if (value.getClass().equals(RadioButtonArray.class)) {
            RadioButtonArray v = (RadioButtonArray) value;
            if (isSelected) {
                v.setBackground(table.getSelectionBackground());
            } else {
                v.setBackground(table.getBackground());
            }
            return v;
        } else if (value.getClass().equals(JRadioButton.class)) {
            JRadioButton v = (JRadioButton) value;
            if (isSelected) {
                v.setBackground(table.getSelectionBackground());
            } else {
                v.setBackground(table.getBackground());
            }
        }
        return (Component) value;
    }
    
}