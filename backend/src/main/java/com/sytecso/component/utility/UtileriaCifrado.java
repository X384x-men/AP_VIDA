package com.sytecso.component.utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;

import com.sytecso.config.logger.SytecsoLogger;

public class UtileriaCifrado {
	private UtileriaCifrado() {
		throw new IllegalStateException("Utility class");
	}

	public static String getMD5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));

			StringBuilder sb = new StringBuilder();
			for (byte b : hashInBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			SytecsoLogger.error("Ocurrio un error al generar el paassword", e);
			return null;
		}
	}

	public static boolean validaPassword(String password, String userPassword) {
		return comparePassword(password, userPassword);
	}

	public static String[] getMD5Authorities(Collection<? extends GrantedAuthority> strs) {
		String[] data = new String[strs.size()];
		try {
			int i = 0;
			for (Iterator<? extends GrantedAuthority> iterator = strs.iterator(); iterator.hasNext();) {
				GrantedAuthority grantedAuthority = (GrantedAuthority) iterator.next();
				data[i] = getMD5(grantedAuthority.getAuthority());
				i += 1;
			}
		} catch (Exception e) {
			SytecsoLogger.error("Ocurrio un error al cifrar el dato: ".concat(""), e);
		}
		return data;
	}

	private static boolean comparePassword(String password, String userPassword) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(StandardCharsets.UTF_8), 0, password.length());
			String hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return userPassword.equals(hashedPass);
		} catch (Exception ex) {
			SytecsoLogger.error("Error.", ex);
			return false;
		}
	}
}
