package com.beepsoft.tablegoodies;

import java.awt.Component;
import javax.swing.JProgressBar;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressBarRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value.getClass().equals(JProgressBar.class)) {
            JProgressBar progressBar = (JProgressBar) value;
            if (!isSelected) {
                progressBar.setBackground(table.getBackground());
            } else {
                progressBar.setBackground(table.getSelectionBackground());
            }
        }
        if (value == null) {
            return null;
        }
        return (Component) value;
    }
}
