/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beepsoft.ptools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author heri
 */
public class ZipArchiver {

    private static final int BUFFER = 2048;

    public static boolean extract(File zipFile, File tempDir) {
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            fileInputStream = new FileInputStream(zipFile);
            zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream));
            ZipEntry zipEntry;
            tempDir.mkdir();
            int entryCount = 0;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                entryCount++;
                String zipEntryName = zipEntry.getName();
                System.out.println("Extracting \"" + zipEntryName + "\"");
                zipEntryName = tempDir.getAbsolutePath() + "/" +  zipEntryName;
                if (zipEntryName.endsWith("/")) {
                    new File(zipEntryName).mkdir();
                } else {
                    int count;
                    byte[] data = new byte[BUFFER];
                    FileOutputStream fileOutputStream = new FileOutputStream(zipEntryName);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER);
                    while ((count = zipInputStream.read(data, 0, BUFFER)) != -1) {
                        bufferedOutputStream.write(data, 0, count);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
            }
            System.out.println("Total " + entryCount + (entryCount == 1 ? " entry" : " entries") + " extracted");
        } catch (FileNotFoundException fileNotFoundException) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, fileNotFoundException);
            return false;
        } catch (IOException iOException) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, iOException);
            return false;
        } finally {
            try {
                if (zipInputStream != null) {
                    zipInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException iOException) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compress
     * @param parentDirectory
     * @param includeParentDirectory
     * @return
     */
    public static boolean compress(File parentDirectory, boolean includeParentDirectory,
            File resultFile) {
        byte[] buf = new byte[BUFFER];
        if (parentDirectory.exists()) {
            try {
                resultFile.delete();
            } catch (Exception e) {
                Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
        }
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            int listSize = 0;
            LinkedList<File> fileList = null;
            if (parentDirectory.isDirectory()) {
                // Collect all directories
                fileList = FileSystem.listAllDirectories(parentDirectory);
                // Collect all files in all directories
                listSize = fileList.size();
                for (int i=0; i < listSize; i++) {
                    File file = fileList.get(i);
                    if (file.isDirectory()) {
                        File[] files = file.listFiles();
                        for (int j=0; j < files.length; j++) {
                            if (files[j].isFile()) {
                                fileList.add(files[j]);
                            }
                        }
                    }
                }
            } else {
                fileList = new LinkedList<File>();
                fileList.add(parentDirectory);
            }
            // Start compressing
            fileOutputStream = new FileOutputStream(resultFile.getAbsolutePath());
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            listSize = fileList.size();
            System.out.println("Total " + listSize + (listSize == 1 ? " file" : " entries") + " to compress");
            for (int i=0; i < listSize; i++) {
                File file = fileList.get(i);
                String zipEntryName = file.getAbsolutePath().replaceAll("\\\\", "/") + (file.isDirectory() ? "/" : "");
                if (parentDirectory.isDirectory()) {
                    zipEntryName = zipEntryName.substring(parentDirectory.getAbsolutePath().length()+1);
                } else {
                    zipEntryName = file.getName();
                }
                if (includeParentDirectory && parentDirectory.isDirectory()) {
                    zipEntryName = parentDirectory.getName() + "/" + zipEntryName;
                }
                System.out.println("Compressing \"" + zipEntryName + "\"");
                // Add ZIP entry to output stream.
                zipOutputStream.putNextEntry(new ZipEntry(zipEntryName));
                if (!zipEntryName.endsWith("/")) {
                    FileInputStream fileInputStream = null;
                    if (parentDirectory.isDirectory()) {
                        fileInputStream = new FileInputStream(
                                parentDirectory.getAbsolutePath() + "/" + zipEntryName);
                    } else {
                        fileInputStream = new FileInputStream(parentDirectory);
                    }
                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = fileInputStream.read(buf)) > 0) {
                        zipOutputStream.write(buf, 0, len);
                    }
                    // Complete the entry
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                }
            }
            // Complete the ZIP file
            zipOutputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException iOException) {
                return false;
            }
        }
        return true;
    }

    public static boolean removeDirectoriesAndFiles(File file) {
        try {
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        files[i].delete();
                    } else {
                        boolean result = removeDirectoriesAndFiles(files[i]);
                        if (result == false) {
                            return false;
                        }
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, e);
        }
        return true;
    }

}
