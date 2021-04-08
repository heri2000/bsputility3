package com.beepsoft.tablegoodies;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor implements ItemListener {
    
    private ButtonArray buttonArray;
    private JButton button;
    
    public ButtonEditor(JCheckBox cb) {
        super(cb);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value == null) {
            return null;
        }
        if (value.getClass().equals(ButtonArray.class)) {
            buttonArray = (ButtonArray) value;
            buttonArray.addItemListener(this);
            button = null;
            return buttonArray;
        } else if (value.getClass().equals(JButton.class)) {
            button = (JButton) value;
            button.addItemListener(this);
            buttonArray = null;
            return button;
        }
        return (Component) value;
    }
    
    @Override
    public Object getCellEditorValue() {
        if (button == null) {
            buttonArray.removeItemListener(this);
            return buttonArray;
        } else if (buttonArray == null) {
            button.removeItemListener(this);
            return button;
        }
        return null;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}
