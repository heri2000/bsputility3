/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beepsoft.ptools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author heri
 */
public class FileSystem {

    /**
     * List all directories within the given path. Only directories are listed,
     * not the files.
     * @param path the path where the scan should begin
     * @param directoryList a LinkedList<String> which holds scanned path
     * @throws NullPointerException if either the path or the directoryList is null
     * @throws FileNotFoundException if the given path does not exist or it is not
     * a directory
     */
    public static LinkedList<File> listAllDirectories(File parentDirectory)
            throws NullPointerException, FileNotFoundException {
        LinkedList<File> fileList = new LinkedList<File>();
        if (parentDirectory.isDirectory()) {
            try {
                listAllDirectories(parentDirectory, fileList);
            } catch (NullPointerException nullPointerException) {
                throw nullPointerException;
            } catch (FileNotFoundException fileNotFoundException) {
                throw fileNotFoundException;
            }
        }
        return fileList;
    }

    /**
     * List all directories within the given path. Only directories are listed,
     * not the files.
     * @param path the path where the scan should begin
     * @param directoryList a LinkedList<String> which holds scanned path
     * @throws NullPointerException if either the path or the directoryList is null
     * @throws FileNotFoundException if the given path does not exist or it is not
     * a directory
     */
    private static void listAllDirectories(File parentDirectory, LinkedList<File> fileList)
            throws NullPointerException, FileNotFoundException {
        if (parentDirectory == null || fileList == null) {
            throw new NullPointerException();
        }
        if (!parentDirectory.exists() || !parentDirectory.isDirectory()) {
            throw new FileNotFoundException();
        }
        File[] chileFileList = parentDirectory.listFiles();
        for (int i = 0; i < chileFileList.length; i++) {
            File childFile = chileFileList[i];
            if (childFile.isDirectory()) {
                fileList.add(childFile);
                listAllDirectories(childFile, fileList);
            }
        }
    }

    /**
     * Find a file which corresponds the given file name
     * @param parentDirectory the directory where the search begins
     * @param fileName the name of the file to find
     * @param includeSubdirectories the flag that tells whether the search should
     * include subdirectories or not
     * @return the first File object which name matches the given file name.
     * Returns null if no file has name equals the given name.
     */
    public static File findFile(File parentDirectory, String fileName,
            boolean includeSubdirectories) {
        File file = null;
        try {
            LinkedList<File> directories = null;
            // List all directories and subdirectories
            if (includeSubdirectories) {
                directories = listAllDirectories(parentDirectory);
            } else {
                directories = new LinkedList<File>();
            }
            directories.addFirst(parentDirectory);
            int listSize = directories.size();
            for (int i=0; i < listSize; i++) {
                File directory = directories.get(i);
                File[] files = directory.listFiles();
                for (int j=0; j < files.length; j++) {
                    if (files[j].getName().equalsIgnoreCase(fileName)) {
                        file = files[j];
                        break;
                    }
                }
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipArchiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

}
