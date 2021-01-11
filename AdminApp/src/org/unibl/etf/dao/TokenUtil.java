package org.unibl.etf.dao;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.unibl.etf.dto.Token;
//Apache commons-lang does have a class called RandomStringUtils, which allows you to generate Random alphanumeric

//Read more: https://www.java67.com/2018/01/how-to-create-random-alphabetic-or-alphanumeric-string-java.html#ixzz6JOhvzO5U

public class TokenUtil {
	
	public static String generateToken(int length, boolean letters, boolean numbers) {
		return RandomStringUtils.random(length,letters,numbers);
	}
	
	public static boolean isValid(Token token) {
		Date date = new Date();
		return date.before(token.getExpirationTime()) && date.after(token.getCreationTime());
	}
}
