package com.beepsoft.tablegoodies;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            return null;
        }
        if (value.getClass().equals(ButtonArray.class)) {
            return (ButtonArray) value;
        }
        return (Component) value;
    }
    
}