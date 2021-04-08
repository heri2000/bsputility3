/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.JTextField;

/**
 *
 * @author Heri
 */
public class DateTextFieldEditor {

    private SimpleDateFormat simpleDateFormat;
    private GregorianCalendar gCal;
    private KeyAdapter keyAdapter;

    /**
     * Creates text field formatter for editing date
     * @param simpleDateFormat the SimpleDateFormat for the displayed date
     */
    public DateTextFieldEditor(SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat != null) {
            this.simpleDateFormat = simpleDateFormat;
        }
        gCal = (GregorianCalendar) GregorianCalendar.getInstance();
        initKeyAdapter();
    }

    /**
     * Creates text field formatter for editing date
     * @param formatString a String pattern for the formatted date
     */
    public DateTextFieldEditor(String formatString) {
        if (formatString != null) {
            this.simpleDateFormat = new SimpleDateFormat(formatString);
        }
        gCal = (GregorianCalendar) GregorianCalendar.getInstance();
        initKeyAdapter();
    }

    /**
     * Adds a text field to listen by this DateTextFieldEditor
     * @param field the text field to listen
     */
    public void listenToTextField(JTextField field) {
        field.addKeyListener(keyAdapter);
    }

    /**
     * Checks whether the inserted text is a valid date
     * @param field the text field to check
     * @return true if text field contains a valid date
     * @throws Exception if the text's format contains error
     */
    public boolean isValid(JTextField field) throws Exception {
        boolean valid = true;
        try {
            String txt = field.getText().trim();
            int txtLen = txt.length();
            for (int i = 0; i < txtLen; i++) {
                char chr = txt.charAt(i);
                if (!Character.isDigit(chr) &&
                        chr != '-') {
                    throw new Exception("Masukan tanggal hanya boleh berupa angka dan tanda (-)");
                }
            }
            String[] arrStr = txt.split("-");
            if (arrStr == null || arrStr.length != 3) {
                throw new Exception("Jumlah kelompok angka harus 3");
            }
            if (arrStr[0].length() < 1 || arrStr[0].length() > 2) {
                throw new Exception("Jumlah digit tanggal harus 1 atau 2");
            }
            if (arrStr[1].length() < 1 || arrStr[1].length() > 2) {
                throw new Exception("Jumlah digit bulan harus 1 atau 2");
            }
            if (arrStr[2].length() < 1 || arrStr[2].length() > 4) {
                throw new Exception("Jumlah digit tahun minimal 1 dan maksimal 4");
            }
            int thn = 0, bln = 0, hari = 0;
            try {
                thn = Integer.parseInt(arrStr[2]);
            } catch (NumberFormatException ex) {
                throw new Exception("Angka tahun tidak valid");
            }
            try {
                bln = Integer.parseInt(arrStr[1]);
            } catch (NumberFormatException ex) {
                throw new Exception("Angka bulan tidak valid");
            }
            try {
                hari = Integer.parseInt(arrStr[0]);
            } catch (NumberFormatException ex) {
                throw new Exception("Angka tanggal/hari tidak valid");
            }
            gCal.set(GregorianCalendar.YEAR, thn);
            if (bln < 1 || bln > 12) {
                throw new Exception("Bulan hanya boleh bernilai 1 sampai 12");
            }
            gCal.set(GregorianCalendar.MONTH, bln - 1);
            int max = gCal.getActualMaximum(GregorianCalendar.DATE);
            if (hari < 1 || hari > max) {
                throw new Exception("Tanggal/hari hanya boleh bernilai 1 sampai " +
                        max + (bln == 2 ? " (khusus bulan Pebruari " + thn + ")" : ""));
            }
            field.setForeground(Color.BLACK);
        } catch (Exception ex) {
            valid = false;
            field.setForeground(Color.RED);
            throw ex;
        }
        field.repaint();
        return valid;
    }

    /**
     * Initialize a key listener
     */
    private void initKeyAdapter() {
        keyAdapter = new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                Object obj = e.getComponent();
                if (obj == null || !(obj instanceof JTextField)) {
                    return;
                }
                JTextField field = (JTextField) obj;
                try {
                    isValid(field);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }

    /**
     * Gets SimpleDateFormat for this text field editor
     * @return the SimpleDateFormat
     */
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }
}
