package com.websiteName.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncryption {

	/**
	 * key MUST be 128 bit initVector MUST be 16 byte (16 characters)
	 * 
	 * 128 bit key:
	 * 
	 * For base64 (in our class): 6-bits per character base-64 encoded
	 * 
	 * For base256 (not in our class): 8-bits per character raw (base-256, i.e.
	 * where a character can take any byte value)
	 * 
	 */

	private static String key = "ABCD1234abcd4321"; // 128 bit (16 characters)
	private static String initVector = "8z7b6G5J4L3K2i1N"; // 16 byte (16
															// characters)

	// Check if all works
	public static void main(String[] args) {

		String encrypted;

		encrypted = new PasswordEncryption().encryptOtherText("amelDev89");
		System.out.println(encrypted);
		System.out.println(new PasswordEncryption().decryptOtherText(encrypted));

		System.out.println();
		System.out.println();

		String mixedPassword = new PasswordEncryption().mixPasswordText("amelDev89");
		System.out.println(mixedPassword);
		System.out.println(new PasswordEncryption().encryptPassword(mixedPassword));
	}

	// Method
	public String mixPasswordText(String password) {

		StringBuilder stringBuilder = new StringBuilder();

		String passwordRevert = new StringBuilder(password).reverse().toString();
		char[] passwordRevertArray = passwordRevert.toCharArray();
		char[] newPasswordRevertArray = new char[passwordRevertArray.length];

		// zamijeni mjesta (parno na neparno)
		if (passwordRevertArray.length % 2 == 0) {
			for (int i = 0; i < passwordRevertArray.length; i = i + 2) {
				newPasswordRevertArray[i] = passwordRevertArray[i + 1];
				newPasswordRevertArray[i + 1] = passwordRevertArray[i];
			}
		} else {
			for (int i = 0; i < passwordRevertArray.length - 1; i = i + 2) {
				newPasswordRevertArray[i] = passwordRevertArray[i + 1];
				newPasswordRevertArray[i + 1] = passwordRevertArray[i];
			}
			int arrayLenght = passwordRevertArray.length;
			newPasswordRevertArray[arrayLenght - 1] = passwordRevertArray[arrayLenght - 1];
		}

		// convert array to string
		for (int i = 0; i < newPasswordRevertArray.length; i++) {
			stringBuilder.append(newPasswordRevertArray[i]);
		}

		String newPassword = stringBuilder.toString();

		return newPassword;
	}

	// Method
	public String encryptPassword(String password) {

		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA-256");
			byte[] result = mDigest.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";

	}

	// Method
	public String encryptOtherText(String text) {

		IvParameterSpec iv;
		try {
			iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(text.getBytes());

			return Base64.encodeBase64String(encrypted);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return "";

	}

	// Method
	public String decryptOtherText(String encryptedText) {

		IvParameterSpec iv;
		try {
			iv = new IvParameterSpec(initVector.getBytes("UTF-8"));

			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encryptedText));

			return new String(original);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return "";

	}

}
