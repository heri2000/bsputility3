/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beepsoft.ptools;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author heri
 */
public class BspCipher {
    private static Cipher encryptor, decryptor;

    public BspCipher(String password) {
	byte[] salt8Byte = { 0x10, 0x10, 0x01, 0x04, 0x01, 0x01, 0x01, 0x02 };
	int jmlIterasi = 7;
	try {
	    KeySpec keySpecification = new PBEKeySpec(password.toCharArray(), salt8Byte, jmlIterasi);
	    SecretKey secretKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpecification);
	    encryptor = Cipher.getInstance(secretKey.getAlgorithm());
	    decryptor = Cipher.getInstance(secretKey.getAlgorithm());
	    AlgorithmParameterSpec paramSpecification = new PBEParameterSpec(salt8Byte, jmlIterasi);
	    encryptor.init(Cipher.ENCRYPT_MODE, secretKey, paramSpecification);
	    decryptor.init(Cipher.DECRYPT_MODE, secretKey, paramSpecification);
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (InvalidKeySpecException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NoSuchPaddingException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (InvalidKeyException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (InvalidAlgorithmParameterException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public byte[] encrypt(String str) {
	byte[] source = str.getBytes();
	try {
	    return encryptor.doFinal(source);
	} catch (IllegalBlockSizeException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (BadPaddingException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }

    public String decrypt(byte[] input) {
	try {
	    byte[] result = decryptor.doFinal(input);
	    return String.valueOf(result);
	} catch (IllegalBlockSizeException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	} catch (BadPaddingException ex) {
	    Logger.getLogger(BspCipher.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
}
