package com.beepsoft.tablegoodies;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LabelCellRenderer implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            return null;
        }
        if (value.getClass().equals(LabelCell.class)) {
            LabelCell v = (LabelCell) value;
            if (isSelected) {
                Color color = table.getSelectionBackground();
                Color labelColor = v.getBackgroundColor();
                int red = (color.getRed() + labelColor.getRed()) / 2;
                int green = (color.getGreen() + labelColor.getGreen()) / 2;
                int blue = (color.getBlue() + labelColor.getBlue()) / 2;
                color = new Color(red, green, blue);
                v.setBackground(color);
//                v.setBackground(table.getSelectionBackground());
            } else {
                v.setBackground(v.getBackgroundColor());
            }
        }
        return (Component) value;
    }
    
}