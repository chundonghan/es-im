package com.es.test;

import org.junit.Test;

public class CommonTest {

	@Test
	public void strTest() {
		String a = "xiaoming@xiaohuang";
		System.out.println(a.startsWith("xiaoming@"));
		String message = "a@b/g:asd";
		
		String from = message.substring(0,message.indexOf("@"));
		String to = message.substring(message.indexOf("@")+1,message.indexOf(":"));
		String msg = message.substring(message.indexOf(":")+1);
		System.out.println(from);
		System.out.println(to);
		System.out.println(msg);
	}
	
}
