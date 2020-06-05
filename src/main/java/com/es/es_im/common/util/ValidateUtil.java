package com.es.es_im.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
	public static boolean validatePhone(String phone) {
		Pattern pattern = null;
		Matcher matcher = null;
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))[0-9]{8}$";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}
	public static boolean validateAccount(String account){
		Pattern pattern = null;
		Matcher matcher = null;
		String regex = "^[a-zA-Z]\\w{5,17}$";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(account);
		return matcher.matches();
	}
	public static boolean validatePassword(String password){
		Pattern pattern = null;
		Matcher matcher = null;
		String regex = "^[a-zA-Z]\\w{5,17}$";
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
	public static void main(String[] args) {
		System.out.println(validateAccount("asdasd韩"));
	}
	
}
