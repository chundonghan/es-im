package com.es.es_im.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin {
	/**
	 * 得到 全拼
	 * 
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {

		char[] t1 = null;
		t1 = src.toCharArray();
		// 将数字排在后面
		if (t1[0] <= 57) {
			t1 = ("{" + src).toCharArray();
		}
		
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 得到中文首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {

		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	public static char getInitialChar(String str) {
		char word = str.charAt(0);
		String[] pinyinArray = null;
		try{
			pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			return pinyinArray[0].charAt(0);
		}catch( Exception e){
		}
		return word;
		
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i]&0xff));
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {

		String cnStr = "韩东春";
		System.out.println(getPingYin(cnStr));
		System.out.println(getPinYinHeadChar(cnStr));
		int i = getPinYinHeadChar(cnStr).compareTo("handongchun");
		System.out.println(i);
		List<String> strList = Arrays.asList("韩东春", "寒冬产", "a", "aa", "ab", "zz", "zzz", "#", "22", "焊东西");
		Collections.sort(strList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				return getPingYin(o1).compareTo(getPingYin(o2));
			}
		});
		System.out.println(strList.toString());
		System.out.println(getInitialChar("逼格"));

	}
}
