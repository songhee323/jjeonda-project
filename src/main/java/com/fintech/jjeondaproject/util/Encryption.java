package com.fintech.jjeondaproject.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Encryption {

	private static String SALT;

	@Value("${encrypt.salt}")
	public void setSalt(String salt) {
		SALT = salt;
	}

	public static String encryptSHA512(String password){
		try {
			if(password != null && SALT != null) {
				MessageDigest md = MessageDigest.getInstance("SHA-512");
				md.reset();
				
				md.update(SALT.getBytes());
				
				byte[] digested = md.digest(password.getBytes());
				
				return String.format("%0128x", new BigInteger(1, digested));
			} else {
				return "";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static boolean comparePwd(String pwd, String dbPwd) {
		
		return encryptSHA512(pwd).equals(dbPwd);
	}
}
