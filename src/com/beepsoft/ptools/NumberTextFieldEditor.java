/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Heri
 */
public class NumberTextFieldEditor {

    private NumberTextFieldEditor me = this;
    private DecimalFormat decimalFormat = null;
    private DecimalFormatSymbols decimalFormatSymbol = null;
    private KeyAdapter keyAdapter = null;
    private char groupingSeparator = '.';
    private char decimalSeparator = ',';
    private KeyEvent keyEvent = null;
    private JTextField field = null;
    private String validString = null;

    /**
     * Creates text field formatter for editing number.
     * @param useGroupSeparator specifies whether the displayed numbers are
     * grouped by thousands
     * @param minFraction minimum fraction digits
     * @param maxFraction maximum fraction digits
     */
    public NumberTextFieldEditor(boolean useGroupSeparator, int minFraction, int maxFraction) {
        // Tentukan format penampilan bilangan
        decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormatSymbol = decimalFormat.getDecimalFormatSymbols();
        // Tentukan separator-separator
        decimalFormatSymbol.setGroupingSeparator(groupingSeparator);
        decimalFormatSymbol.setDecimalSeparator(decimalSeparator);
        decimalFormat.setGroupingUsed(useGroupSeparator);
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        decimalFormat.setMinimumFractionDigits(minFraction);
        decimalFormat.setMaximumFractionDigits(maxFraction);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
        initKeyAdapter();
    }

    /**
     * Adds a text field to listen by this NumberTextFieldEditor
     * @param field the text field to listen
     */
    public void listenToTextField(JTextField field) {
        field.addKeyListener(keyAdapter);
    }

    public String getValidNumberString(String txt) throws Exception {
        int txtLength = txt.length();
        // If there is decimal separator, secure some trailing zeros
        boolean addDecimalSeparator = false;
        String securedZeros = "";
        int decimalSeparatorIndex = txt.lastIndexOf(decimalSeparator);
        int maximumFractionDigits = decimalFormat.getMaximumFractionDigits();
        if (decimalSeparatorIndex >= 0) {
            int i = txtLength - 1;
            if (i - decimalSeparatorIndex > maximumFractionDigits) {
                i = decimalSeparatorIndex + maximumFractionDigits;
            }
            while (i > decimalSeparatorIndex) {
                if (txt.charAt(i) == '0') {
                    securedZeros += "0";
                } else {
                    break;
                }
                i--;
            }
            addDecimalSeparator = i == decimalSeparatorIndex;
        }
        // Check characters
        for (int i = 0; i < txtLength; i++) {
            char chr = txt.charAt(i);
            if (chr != '-' && chr != groupingSeparator
                    && chr != decimalSeparator && !Character.isDigit(chr)) {
                throw new Exception("Invalid number");
            }
        }
        if (txt.length() == 0) {
            return "";
        }
        if (txt.equals("-")) {
            return txt;
        }
        Number number = null;
        try {
            number = decimalFormat.parse(txt);
        } catch (ParseException parseException) {
            throw parseException;
        }
        String newTxt = decimalFormat.format(number);
        if (addDecimalSeparator) {
            newTxt += decimalSeparator;
        }
        newTxt += securedZeros;
        return newTxt;
    }

    /**
     * Initialize a key listener
     */
    private void initKeyAdapter() {
        keyAdapter = new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                me.keyEvent = e;
                Object object = e.getComponent();
                if (object == null || !(object instanceof JTextField)) {
                    return;
                }
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        checkNumberField();
                    }
                };
                thread.start();
            }
        };
    }

    private void checkNumberField() {
        field = (JTextField) keyEvent.getComponent();
        int keyCode = keyEvent.getKeyCode();
        char keyChar = keyEvent.getKeyChar();
        boolean ctrl = keyEvent.getModifiers() == KeyEvent.VK_CONTROL;
        if (ctrl) {
            switch (keyCode) {
                case KeyEvent.VK_V:
                case KeyEvent.VK_X:
                    checkNumberString();
                    break;
            }
        } else {
            if (keyCode == KeyEvent.VK_BACK_SPACE
                    || keyCode == KeyEvent.VK_DELETE
                    || Character.isLetterOrDigit(keyChar)
                    || keyChar == '-'
                    || keyChar == ','
                    || keyChar == '.') {
                if (keyChar == groupingSeparator) {
                    String txt = field.getText();
                    int caretPosition = field.getCaretPosition();
                    int txtLength = txt.length();
                    String newTxt = null;
                    try {
                        newTxt = txt.substring(0, caretPosition - 1)
                                + decimalSeparator
                                + txt.substring(caretPosition, txtLength);
                    } catch (Exception e) {
                        Logger.getLogger(NumberTextFieldEditor.class.getName()).log(Level.SEVERE, null, e);
                    }
                    field.setText(newTxt);
                    field.setCaretPosition(caretPosition);
                }
                checkNumberString();
            }
        }
        field.repaint();
    }

    private void checkNumberString() {
        try {
            int caretPosition = field.getCaretPosition();
            String initialString = field.getText();
            // Get digit index
            int digitIndex = 0;
            for (int i=0; i < caretPosition; i++) {
                char character = initialString.charAt(i);
                if (Character.isDigit(character) ||
                        character == decimalSeparator ||
                        character == '-') {
                    digitIndex++;
                }
            }
            validString = getValidNumberString(initialString);
            field.setText(validString);
            // Find digit according to digitIndex
            int i=0;
            int validStringLength = validString.length();
            while (digitIndex > 0) {
                if (i >= validStringLength) {
                    break;
                }
                char character = validString.charAt(i);
                if (Character.isDigit(character) ||
                        character == decimalSeparator ||
                        character == '-') {
                    digitIndex--;
                }
                i++;
            }
            field.setCaretPosition(i);
            field.setForeground(Color.black);
        } catch (Exception ex) {
            Logger.getLogger(NumberTextFieldEditor.class.getName()).log(Level.SEVERE, null, ex);
            field.setForeground(Color.red);
        }

    }

    /**
     * Gets the character in use for decimal separator
     * @return the character for decimal separator
     */
    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Sets the character in use for decimal separator
     * @param decimalSeparator the character for decimal separator
     */
    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        decimalFormatSymbol.setDecimalSeparator(decimalSeparator);
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
    }

    /**
     * Gets the character in use for grouping separator
     * @return the character for grouping separator
     */
    public char getGroupingSeparator() {
        return groupingSeparator;
    }

    /**
     * Sets the character in use for grouping separator
     * @param groupingSeparator the character for grouping separator
     */
    public void setGroupingSeparator(char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
        decimalFormatSymbol.setGroupingSeparator(decimalSeparator);
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbol);
    }

    /**
     * Gets the DecimalFormat used to format the text field
     * @return the DecimalFormat used to format the text field
     */
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    /**
     * Sets the minimum number of fraction digits
     * @param digits the minimum number of fraction digits
     */
    public void setMinimumFractionDigits(int digits) {
        decimalFormat.setMinimumFractionDigits(digits);
    }

    /**
     * Sets the maximum number of fraction digits
     * @param digits the maximum number of fraction digits
     */
    public void setMaximumFractionDigits(int digits) {
        decimalFormat.setMaximumFractionDigits(digits);
    }
}
