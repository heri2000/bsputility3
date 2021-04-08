/*
 * NewJFrame.java
 *
 * Created on February 12, 2009, 2:11 PM
 */

package com.beepsoft.tablegoodies;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author  Heri
 */
public class NewJFrame extends javax.swing.JFrame {

    private NewJFrame me = this;
    
    /** Creates new form NewJFrame */
    public NewJFrame() {
        initComponents();

        LabelCellRenderer lcr = new LabelCellRenderer();
        dataTable.getColumnModel().getColumn(0).setCellRenderer(lcr);

        String[] labels = {"A", "B", "C"};
        String[] label = {"ABC"};
        CheckBoxArrayRenderer car = new CheckBoxArrayRenderer();
        CheckBoxArrayEditor cae = new CheckBoxArrayEditor(new JCheckBox());
        dataTable.getColumnModel().getColumn(1).setCellRenderer(car);
        dataTable.getColumnModel().getColumn(1).setCellEditor(cae);

        RadioButtonArrayRenderer rbr = new RadioButtonArrayRenderer();
        RadioButtonArrayEditor rbe = new RadioButtonArrayEditor(new JCheckBox());
        dataTable.getColumnModel().getColumn(2).setCellRenderer(rbr);
        dataTable.getColumnModel().getColumn(2).setCellEditor(rbe);

        ButtonRenderer br = new ButtonRenderer();
        ButtonEditor be = new ButtonEditor(new JCheckBox());
        dataTable.getColumnModel().getColumn(3).setCellRenderer(br);
        dataTable.getColumnModel().getColumn(3).setCellEditor(be);

        ProgressBarRenderer pbr = new ProgressBarRenderer();
        dataTable.getColumnModel().getColumn(4).setCellRenderer(pbr);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(me, e.getActionCommand());
            }
        };

        Color color = new Color(255, 230, 230);

        for (int i=0; i < 4; i++) {
            LabelCell labelCell = new LabelCell("Test 1 2 3",
                    Color.red, color, LabelCell.CENTER, dataTable.getFont());
            dataTable.setValueAt(labelCell, i, 0);

            CheckBoxArray cba = new CheckBoxArray(labels);
            cba.setFont(dataTable.getFont());
            dataTable.setValueAt(cba, i, 1);

            RadioButtonArray rba = new RadioButtonArray(labels, i);
            dataTable.setValueAt(rba, i, 2);

            ButtonArray ba = new ButtonArray(label, null);
            ba.addActionListener(actionListener);
            dataTable.setValueAt(ba, i, 3);

            JProgressBar progressBar = new JProgressBar();
            int val = (int) (Math.random() * 11);
            progressBar.setMaximum(10);
            progressBar.setMinimum(0);
            progressBar.setValue(val);
            progressBar.setStringPainted(true);
            progressBar.setString(String.valueOf(val));
            if (val > 5) {
                progressBar.setForeground(Color.green);
            } else if (val > 2) {
                progressBar.setForeground(Color.orange);
            } else {
                progressBar.setForeground(Color.red);
            }
            dataTable.setValueAt(progressBar, i, 4);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dataTable.setCellSelectionEnabled(true);
        dataTable.setRowHeight(22);
        jScrollPane1.setViewportView(dataTable);
        dataTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dataTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}