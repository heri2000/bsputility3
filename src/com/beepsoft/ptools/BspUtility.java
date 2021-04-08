/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import com.beepsoft.tablegoodies.IndicatorCellRenderer;
import com.beepsoft.tablegoodies.MultilineHeaderRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author heri
 */
public class BspUtility {

    // Constants for operating system types
    public static final byte OS_LINUX = 1;
    public static final byte OS_OTHER = -1;
    public static final byte OS_WINDOWS = 0;
    // Constant for this package release date
    public static final long release_date = 20080507;

    // Functions
    public static String centerText(String val, int minChr) {
        if (val.length() >= minChr) {
            return val;
        }

        String tempStr = val;

        int n = (minChr - val.length()) / 2;
        for (int i = 0; i < n; i++) {
            tempStr = " " + tempStr + " ";
        }
        while (tempStr.length() < minChr) {
            tempStr += " ";
        }

        return tempStr;
    }

    public static void centerToScreen(Window window, Window parent) {
        centerToScreen((Component) window, parent);
    }

    public static void centerToScreen(Component component, Window parent) {
        Dimension window_size = component.getSize();
        Dimension screen_size;
        if (parent == null) {
            screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            screen_size = parent.getSize();
        }

        int x = (screen_size.width - window_size.width) / 2;
        int y = (screen_size.height - window_size.height) / 2 - 15;

        if (parent != null) {
            x += parent.getX();
            y += parent.getY();
        }
        component.setLocation(x, y);
    }
    
    public static String decryptOffset(String string, int offset) {
        String newString = "";
        String chars = "Nm2ByTgUfVeWd3xC4Dw5vE6Fu7tG8Hs9rI0JqOlPkQjRiS1zAKpLoMnhXcYbZa";
        int charsLength = chars.length();
        int stringLength = string.length();
        for (int i = 0; i < stringLength; i++) {
            char c = string.charAt(i);
            int position = chars.indexOf(c);
            if (position < 0 || c == ' ') {
                newString += c;
            } else {
                position -= offset;
                while (position < 0) {
                    position += charsLength;
                }
                newString += chars.charAt(position);
            }
        }
        return newString;
    }

    public static String encryptOffset(String string, int offset) {
        String newString = "";
        String chars = "Nm2ByTgUfVeWd3xC4Dw5vE6Fu7tG8Hs9rI0JqOlPkQjRiS1zAKpLoMnhXcYbZa";
        int charsLength = chars.length();
        int stringLength = string.length();
        for (int i = 0; i < stringLength; i++) {
            char c = string.charAt(i);  //substr($string, $i, 1);
            int position = chars.indexOf(c);
            if (position < 0 || c == ' ') {
                newString += c;
            } else {
                position += offset;
                while (position >= charsLength) {
                    position -= charsLength;
                }
                newString += chars.charAt(position);
            }
        }
        return newString;
    }
    
    public static String fixedStringLength(String str, int length) {
        if (str.length() > length) {
            str = str.substring(0, length);
        } else if (str.length() < length) {
            int difference = length - str.length();
            for (int i=0; i < difference; i++) {
                str += " ";
            }
        }
        return str;
    }
    
    public static String formatCurr(double curr) {
        DecimalFormat df =
                (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        dfs.setCurrencySymbol("");
        df.setDecimalFormatSymbols(dfs);
        df.setDecimalSeparatorAlwaysShown(false);
        df.setMinimumFractionDigits(0);
        String scurr = "n/a";
        try {
            scurr = df.format(curr);
        } catch (Exception e) {
        }
        return scurr;
    }

    public static String formatGeneralNumericDisplay(double val) {
        if (val == 0) {
            return "0";
        }
        DecimalFormat df =
                (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(5);
        String sdisp = "n/a";
        try {
            sdisp = df.format(val);
        } catch (Exception e) {
        }
        return sdisp;
    }

    public static String formatNumericDisplay_1DigitsFraction(double val) {
        if (val == 0) {
            return "";
        }
        DecimalFormat df =
                (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(1);
        String sdisp = "n/a";
        try {
            sdisp = df.format(val);
        } catch (Exception e) {
        }
        return sdisp;
    }

    public static String formatNumericDisplay_3DigitsFraction(double val) {
        if (val == 0) {
            return "";
        }
        DecimalFormat df =
                (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(3);
        String sdisp = "n/a";
        try {
            sdisp = df.format(val);
        } catch (Exception e) {
        }
        return sdisp;
    }

    public static String formatNumericInput(double val) {
        if (val == 0) {
            return "";
        }
        DecimalFormat df =
                (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setCurrencySymbol("");
        df.setDecimalFormatSymbols(dfs);
        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(0);
        String sdisp = "n/a";
        try {
            sdisp = df.format(val);
        } catch (Exception e) {
        }
        return sdisp;
    }

    public static String formatQtyComplete(double n, double conversion,
            String unit1, String unit2) {
        String tempString = "";
        double u;
        int u1;
        double u2;
        if (n != 0) {
            if (conversion != 0) {
                u = (n / conversion);
                if (unit2 != null) {
                    if (!unit2.equals("") && (u >= 1)) {

                        tempString = formatNumericDisplay_3DigitsFraction(u) + " " + unit2;

                        u1 = (int) (n / conversion);
                        u2 = n - u1 * conversion;

                        if (u2 != 0) {
                            tempString += "  (" + u1 + " " + unit2
                                    + ", " + formatNumericDisplay_3DigitsFraction(u2) + " " + unit1 + ")";
                        }

                    } else {
                        tempString = formatNumericDisplay_3DigitsFraction(n) + " " + unit1;
                    }
                } else {
                    tempString = formatNumericDisplay_3DigitsFraction(n) + " " + unit1;
                }
            }
        } else {
            tempString = "0";
        }
        return tempString;
    }
    
    public static LinkedList<Component> getAllComponents(Container container) {
        LinkedList<Component> componentList = new LinkedList<Component>();
        Component[] components = container.getComponents();
        for (int i=0; i < components.length; i++) {
            componentList.add(components[i]);
            if (components[i] instanceof Container) {
                LinkedList<Component> childComponentList = getAllComponents((Container) components[i]);
                componentList.addAll(childComponentList);
            }
        }
        return componentList;
    }

    public static int getOsType() {
        if (System.getProperty("os.name").indexOf("Windows") != -1) {
            return OS_WINDOWS;
        } else if (System.getProperty("os.name").indexOf("Linux") != -1) {
            return OS_LINUX;
        } else {
            return OS_OTHER;
        }
    }

    public static String leadingSpace(String val, int minChr) {
        String strTemp = val;

        while (strTemp.length() < minChr) {
            strTemp = " " + strTemp;
        }
        return strTemp;
    }

    public static String leadingZero(int val, int minChr) {
        return leadingZero((long) val, minChr);
    }

    public static String leadingZero(long val, int minChr) {
        String strTemp = String.valueOf(val);

        while (strTemp.length() < minChr) {
            strTemp = "0" + strTemp;
        }
        return strTemp;
    }

    public static String leadingZero(String val, int minChr) {
        return leadingZero(Integer.parseInt(val), minChr);
    }
    
    public static String limitStringLength(String str, int length) {
        if (str.length() > length) {
            str = str.substring(0, length);
        }
        return str;
    }

    public static String makeParsable(String str) {
        String tempStr = "";

        if (str == null) {
            return tempStr;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                tempStr += '.';
            } else if (str.charAt(i) != '.') {
                tempStr += str.charAt(i);
            }
        }

        return tempStr;
    }
    
    public static String normalizeStringForSql(String str) {
        return str.replaceAll("'", "''");
    }

    public static byte parseByte(String str_val) {
        byte val = 0;
        if (str_val == null) {
            return val;
        } else if (str_val.length() == 0) {
            return val;
        }
        try {
            val = Byte.parseByte(str_val);
        } catch (NumberFormatException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            val = 0;
        }
        return val;
    }

    public static double parseDouble(String str_val) {
        double val = 0;
        if (str_val == null) {
            return val;
        } else if (str_val.length() == 0) {
            return val;
        }
        try {
            val = Double.parseDouble(str_val);
        } catch (NumberFormatException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            val = 0;
        }
        return val;
    }

    public static float parseFloat(String str_val) {
        float val = 0;
        if (str_val == null) {
            return val;
        } else if (str_val.length() == 0) {
            return val;
        }
        try {
            val = Float.parseFloat(str_val);
        } catch (NumberFormatException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            val = 0;
        }
        return val;
    }

    public static int parseInt(String str_val) {
        int val = 0;
        if (str_val == null) {
            return val;
        } else if (str_val.length() == 0) {
            return val;
        }
        try {
            val = Integer.parseInt(str_val);
        } catch (NumberFormatException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            val = 0;
        }
        return val;
    }

    public static long parseLong(String str_val) {
        long val = 0;
        if (str_val == null) {
            return val;
        } else if (str_val.length() == 0) {
            return val;
        }
        try {
            val = Long.parseLong(str_val);
        } catch (NumberFormatException ex) {
            Logger.getLogger(BspDb.class.getName()).log(Level.SEVERE, null, ex);
            val = 0;
        }
        return val;
    }

    public static void tableClearContents(JTable table) {
        DefaultTableModel table_model = (DefaultTableModel) table.getModel();
        int row_count = table_model.getRowCount();
        while (row_count > 0) {
            table_model.removeRow(--row_count);
        }
    }

    public static void tableRemoveExceededRows(
            DefaultTableModel model, int length) {
        int row_count = model.getRowCount();
        while (row_count > length) {
            model.removeRow(--row_count);
        }
    }

    public static void tableRemoveRow(JTable table, int row) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.removeRow(row);
    }

    public static int[] tableGetColumnWidths(JTable table) {
        int[] widths = new int[table.getColumnCount()];

        for (int i = 0; i < widths.length; i++) {
            widths[i] = table.getColumnModel().getColumn(i).getWidth();
        }

        return widths;
    }

    public static Object[] tableGetColumnIdentifiers(JTable table) {
        Object[] identifiers = new Object[table.getColumnCount()];

        for (int i = 0; i < identifiers.length; i++) {
            identifiers[i] = table.getColumnModel().getColumn(i).getHeaderValue();
        }

        return identifiers;
    }

    public static void tableSetIndicatorCellRenderer(JTable table, String col) {
        // Prepare indicator column
        IndicatorCellRenderer renderer = new IndicatorCellRenderer(0, 150);
        renderer.setStringPainted(false);
        renderer.setBackground(table.getBackground());
        Hashtable<Integer, Color> limit_colors = new Hashtable<Integer, Color>();
        limit_colors.put(new Integer(0), Color.RED);
        limit_colors.put(new Integer(20), new Color(0, 190, 0));
        limit_colors.put(new Integer(120), Color.ORANGE);
        renderer.setLimits(limit_colors);
        table.getColumn(col).setCellRenderer(renderer);
    }

    public static void tableSetMultilineHeaderRenderer(JTable table) {
        MultilineHeaderRenderer header_renderer = new MultilineHeaderRenderer();
        Enumeration e = table.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
            ((TableColumn) e.nextElement()).setHeaderRenderer(header_renderer);
        }
    }

    public static void tableSetColumnWidths(JTable table, int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    public static void tableSetRowValues(Object[] row_data, int row,
            DefaultTableModel model) {
        int row_count = model.getRowCount();
        if (row < row_count) {
            for (int i = 0; i < row_data.length; i++) {
                model.setValueAt(row_data[i], row, i);
            }
        } else if (row == row_count) {
            model.addRow(row_data);
        } else {
            Object[] temp = new Object[row_data.length];
            row_count--;
            while (row > row_count) {
                model.addRow(temp);
                row_count++;
            }
            model.addRow(row_data);
        }
    }

    public static String trailingSpace(String val, int minChr) {
        String strTemp = val;

        while (strTemp.length() < minChr) {
            strTemp += " ";
        }
        return strTemp;
    }

    public static String sqlString(String str) {
        String temp_str = "";
        str = str.trim();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == '\'') {
                temp_str += "\'\'";
            } else {
                temp_str += str.charAt(i);
            }
        }
        return temp_str;
    }

    public static String stripHTMLTags(String str) {
        if (str.indexOf("<") == -1) {
            return str;
        }

        String tempStr = "";
        boolean inside_tag = false;
        char tmpChar;

        for (int i = 0; i < str.length(); i++) {
            tmpChar = str.charAt(i);
            if (tmpChar == '<') {
                inside_tag = true;
            } else if (tmpChar == '>') {
                inside_tag = false;
            } else if (!inside_tag) {
                tempStr += String.valueOf(tmpChar);
            }
        }

        return tempStr;
    }

    public static String terbilang(String value) {
        String strValue, strTerbilang;
        int ptr1, ptr2, test, length, tempPtr;
        boolean zero;

        strValue = value;

        length = strValue.length();
        ptr1 = 0;
        strTerbilang = "";
        zero = true;

        while (ptr1 < length) {
            ptr2 = (length - 1 - ptr1) % 3;
            test = parseInt(strValue.substring(ptr1, ptr1 + 1));
            if (ptr2 == 0) {
                if (ptr1 > 0) {
                    if (parseInt(strValue.substring(ptr1 - 1, ptr1)) == 1) {
                        if (test > 0) {
                            zero = false;
                        }
                        switch (test) {
                            case 1:
                                strTerbilang += "sebelas ";
                                break;
                            case 2:
                                strTerbilang += "dua belas ";
                                break;
                            case 3:
                                strTerbilang += "tiga belas ";
                                break;
                            case 4:
                                strTerbilang += "empat belas ";
                                break;
                            case 5:
                                strTerbilang += "lima belas ";
                                break;
                            case 6:
                                strTerbilang += "enam belas ";
                                break;
                            case 7:
                                strTerbilang += "tujuh belas ";
                                break;
                            case 8:
                                strTerbilang += "delapan belas ";
                                break;
                            case 9:
                                strTerbilang += "sembilan belas ";
                                break;
                        }
                    } else {
                        if (test > 0) {
                            zero = false;
                        }
                        switch (test) {
                            case 1:
                                if (length - ptr1 == 4) {
                                    tempPtr = ptr1 - 1;
                                    test = 0;
                                    while (tempPtr >= 0) {
                                        test = parseInt(strValue.substring(tempPtr, tempPtr + 1));
                                        tempPtr--;
                                    }
                                    if (test == 0) {
                                        strTerbilang += "se";
                                    } else {
                                        strTerbilang += "satu ";
                                    }
                                } else {
                                    strTerbilang += "satu ";
                                }
                                break;
                            case 2:
                                strTerbilang += "dua ";
                                break;
                            case 3:
                                strTerbilang += "tiga ";
                                break;
                            case 4:
                                strTerbilang += "empat ";
                                break;
                            case 5:
                                strTerbilang += "lima ";
                                break;
                            case 6:
                                strTerbilang += "enam ";
                                break;
                            case 7:
                                strTerbilang += "tujuh ";
                                break;
                            case 8:
                                strTerbilang += "delapan ";
                                break;
                            case 9:
                                strTerbilang += "sembilan ";
                                break;
                        }
                    }
                } else {
                    if (test > 0) {
                        zero = false;
                    }
                    switch (test) {
                        case 1:
                            if (length - ptr1 == 4) {
                                strTerbilang += "se";
                            } else {
                                strTerbilang += "satu ";
                            }
                            break;
                        case 2:
                            strTerbilang += "dua ";
                            break;
                        case 3:
                            strTerbilang += "tiga ";
                            break;
                        case 4:
                            strTerbilang += "empat ";
                            break;
                        case 5:
                            strTerbilang += "lima ";
                            break;
                        case 6:
                            strTerbilang += "enam ";
                            break;
                        case 7:
                            strTerbilang += "tujuh ";
                            break;
                        case 8:
                            strTerbilang += "delapan ";
                            break;
                        case 9:
                            strTerbilang += "sembilan ";
                            break;
                    }
                }
                if (!zero) {
                    if (length - ptr1 == 4) {
                        strTerbilang += "ribu ";
                    } else if (length - ptr1 == 7) {
                        strTerbilang += "juta ";
                    } else if (length - ptr1 == 10) {
                        strTerbilang += "miliar ";
                    } else if (length - ptr1 == 13) {
                        strTerbilang += "triliun ";
                    }
                }
                zero = true;
            } else if (ptr2 == 1) {
                if (test > 0) {
                    zero = false;
                }
                switch (test) {
                    case 1:
                        if (parseInt(strValue.substring(ptr1 + 1, ptr1 + 2)) == 0) {
                            strTerbilang += "sepuluh ";
                        }
                        break;
                    case 2:
                        strTerbilang += "dua puluh ";
                        break;
                    case 3:
                        strTerbilang += "tiga puluh ";
                        break;
                    case 4:
                        strTerbilang += "empat puluh ";
                        break;
                    case 5:
                        strTerbilang += "lima puluh ";
                        break;
                    case 6:
                        strTerbilang += "enam puluh ";
                        break;
                    case 7:
                        strTerbilang += "tujuh puluh ";
                        break;
                    case 8:
                        strTerbilang += "delapan puluh ";
                        break;
                    case 9:
                        strTerbilang += "sembilan puluh ";
                        break;
                }
            } else if (ptr2 == 2) {
                if (test > 0) {
                    zero = false;
                }
                switch (test) {
                    case 1:
                        strTerbilang += "seratus ";
                        break;
                    case 2:
                        strTerbilang += "dua ratus ";
                        break;
                    case 3:
                        strTerbilang += "tiga ratus ";
                        break;
                    case 4:
                        strTerbilang += "empat ratus ";
                        break;
                    case 5:
                        strTerbilang += "lima ratus ";
                        break;
                    case 6:
                        strTerbilang += "enam ratus ";
                        break;
                    case 7:
                        strTerbilang += "tujuh ratus ";
                        break;
                    case 8:
                        strTerbilang += "delapan ratus ";
                        break;
                    case 9:
                        strTerbilang += "sembilan ratus ";
                        break;
                }
            }
            ptr1++;
        }

        return (strTerbilang);
    }
}
