package com.beepsoft.tablegoodies;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckboxRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value.getClass().equals(JCheckBox.class)) {
            JCheckBox checkBox = (JCheckBox) value;
            if (!isSelected) {
                checkBox.setBackground(table.getBackground());
            } else {
                checkBox.setBackground(table.getSelectionBackground());
            }
        }
        if (value == null) {
            return null;
        }
        return (Component) value;
    }
}
