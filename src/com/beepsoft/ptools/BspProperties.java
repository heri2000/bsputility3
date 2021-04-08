/*
 * BspProperties.java
 *
 * Created on January 14, 2008, 9:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author heri
 */
public class BspProperties {

    public static Properties loadProperties(String sFile) {
        Properties p = new Properties();
        try {
            FileInputStream in = new FileInputStream(sFile);
            p.load(in);
            in.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }

    public static boolean saveProperties(Properties p, String sFile, String prgName) {
        try {
            FileOutputStream out = new FileOutputStream(sFile);
            p.store(out, "Configuration file for\n" + prgName);
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
