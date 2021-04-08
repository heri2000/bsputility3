package com.beepsoft.tablegoodies;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class ComboboxEditor extends DefaultCellEditor implements ItemListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JComboBox combo;

    public ComboboxEditor(JComboBox jcb) {
        super(jcb);
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null) {
            return null;
        }
        combo = (JComboBox) value;
        combo.addItemListener(this);
        return (Component) value;
    }

    public Object getCellEditorValue() {
        combo.removeItemListener(this);
        return combo;
    }

    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}
