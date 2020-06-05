package com.es.es_im.common.util;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommonFunc {
	private static final Random rand = new Random();

	public static String retJsonString(String key, String value) {
		return "{\"" + key + "\":\"" + value + "\"}";
	}

	public static String generateSmsCode() {
		int i = 5;
		StringBuilder smsCode = new StringBuilder();
		while (i >= 0) {
			smsCode.append(rand.nextInt(10));
			i--;
		}
		return smsCode.toString();
	}

	public static int diffTomorrow() {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long now = new Date().getTime() / 1000;
		long tomorrow = calendar.getTime().getTime() / 1000;
		return (int) (tomorrow - now);
	}

	/**
	 * 生成短信验证码 键
	 * 
	 * @param phone
	 * @return
	 */
	public static String createPhoneSmsKey(String phone) {
		return "sms_" + phone;
	}

	/**
	 * 生成发送短信期间操作次数限制 键
	 * 
	 * @param phone
	 * @return
	 */
	public static String createPhoneSmsKeyNum(String phone) {
		return "sms_" + phone + "_num";
	}

	/**
	 * 生成每天发送短信次数限制 键
	 * 
	 * @param phone
	 * @return
	 */
	public static String createPhoneSmsEachDayCount(String phone) {
		return "sms_each_day_" + phone + "_count";
	}

	public static String encryptPassword(String password) {
		return MD5.md5(password + Constants.PASSWORD_SALT);
	}

	public static String nullToEmpty(String arg) {
		return arg == null ? "" : arg;
	}
	public static void sortList(List<Map<String,Object>> objList){
		Collections.sort(objList,new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String,Object> o1, Map<String,Object> o2) {
				String nick1 = (String) o1.get("nickname");
				String nick2 = (String) o2.get("nickname");
				
				return Pinyin.getPingYin(nick1).compareTo(Pinyin.getPingYin(nick2));
			}
		});
	}
	
	public static void main(String[] args) {
		System.out.println("".equals(null));
	}
	
}
