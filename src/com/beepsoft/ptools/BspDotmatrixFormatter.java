/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

/**
 *
 * @author User
 */
public class BspDotmatrixFormatter {
    
    int lineCharacterCount;
    private String printerData;
    
    public BspDotmatrixFormatter(int lineCharacterCount) {
        this.lineCharacterCount = lineCharacterCount;
        printerData = "\r";
    }
    
    public void addCenterText(String str) {
        addCenterText(str, lineCharacterCount);
    }
    
    public void addCenterText(String str, int width) {
        if (str.length() > width) {
            str = str.substring(0, width);
        }
        if (str.length() == width) {
            printerData += str;
        } else {
            int difference = (width - str.length()) / 2;
            for (int i=0; i < difference; i++) {
                printerData += " ";
            }
            printerData += str;
        }
    }
    
    public void addLineBreak() {
        printerData += "\r\n";
    }
    
    public void addLineSeparator() {
        addLineSeparator(lineCharacterCount);
    }
    
    public void addLineSeparator(int width) {
        printerData += "";
        for (int i=0; i < width; i++) {
            printerData += "-";
        }
    }
    
    public void addRightText(String str) {
        addRightText(str, lineCharacterCount);
    }
    
    public void addRightText(String str, int width) {
        if (str.length() > width) {
            str = str.substring(0, width);
        }
        if (str.length() == width) {
            printerData += str;
        } else {
            int difference = (width - str.length());
            for (int i=0; i < difference; i++) {
                printerData += " ";
            }
            printerData += str;
        }
    }

    public void addText(String str) {
        printerData += str;
    }
    
    public void addText(String str, int space) {
        if (str.length() > space) {
            str = str.substring(0, space);
        }
        printerData += str;
    }
    
    public void addTextFixedSpace(String str, int width) {
        if (str.length() > width) {
            str = str.substring(0, width);
        }
        if (str.length() == width) {
            printerData += str;
        } else {
            printerData += str;
            int difference = (width - str.length());
            for (int i=0; i < difference; i++) {
                printerData += " ";
            }
        }
    }
    
    public String getPrinterData() {
        return printerData;
    }
    
}
