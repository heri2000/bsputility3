/*
 * BspDateCalculator.java
 *
 * Created on September 5, 2008, 6:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.beepsoft.ptools;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author Heri
 */
public class BspDateCalculator {
    
    // Object untuk memformat tampilan tanggal
    private SimpleDateFormat sdf;
    
    /**
     * Creates a new instance of BspDateCalculator
     */
    public BspDateCalculator() {
        sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyy-M-d");
    }
    
    /**
     * Hitung tanggal.
     * @param tgl tanggal di mana perhitungan dimulai (tanggal awal)
     * @param hari selisih hari yang diinginkan
     * @return String berisi tanggal dengan format SQL
     */
    public String tambahHari(String tgl, int hari) {
        String hasil = "";
        
        // Buat instance object baru GregorianCalendar utk hari yg diinginkan
        GregorianCalendar gcal = new GregorianCalendar(
                BspDate.getPartYearSql(tgl),
                BspDate.getPartMonthSql(tgl)-1,
                BspDate.getPartDaySql(tgl));
        
        // Tambahkan object GregorianCalendar itu dgn jumlah hari tertentu
        gcal.add(GregorianCalendar.DAY_OF_MONTH, hari);
        
        // Format hasil penambahannya ke bentuk String
        hasil = sdf.format(gcal.getTime());
        
        return hasil;
    }
    
}
