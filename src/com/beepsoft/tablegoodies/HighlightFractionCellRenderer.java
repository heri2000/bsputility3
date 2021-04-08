/*
 * Created on Sep 12, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.beepsoft.tablegoodies;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Her
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HighlightFractionCellRenderer extends DefaultTableCellRenderer {

    private JLabel label;
    
    private Color bgColor, selectedBgColor;
    
    public HighlightFractionCellRenderer(int integer, int fraction, int align) {
        this.integer = integer; // maximum integer digits
        this.fraction = fraction; // exact number of fraction digits
        this.align = align; // alignment (LEFT, CENTER, RIGHT)
        label = new JLabel();
        label.setOpaque(true);
        bgColor = new Color(255, 255, 180);
        selectedBgColor = new Color(100, 255, 100);
        label.setHorizontalAlignment(align);
    }
    
    protected int integer;
    
    protected int fraction;
    
    protected int align;
    
    protected static DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && value instanceof Number) {
            DecimalFormatSymbols dfs= new DecimalFormatSymbols();
            dfs.setDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            formatter.setDecimalFormatSymbols(dfs);
            formatter.setMaximumIntegerDigits(integer);
            formatter.setMaximumFractionDigits(fraction);
            formatter.setMinimumFractionDigits(fraction);
            label.setText(formatter.format(((Number) value).doubleValue()));
        } else if (value instanceof String) {
            label.setText((String) value);
        } else {
            label.setText("");
        }
        if (!isSelected) {
            label.setBackground(bgColor);
        } else {
            label.setBackground(selectedBgColor);
        }
        label.setFont(table.getFont());
        return (Component) label;
    }
}
