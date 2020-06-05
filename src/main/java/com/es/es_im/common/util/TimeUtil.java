package com.es.es_im.common.util;

public final class TimeUtil {
	public static int currentTimeSecs(long current){
		return Integer.parseInt(String.valueOf(current/1000));
	}
	public static int currentTimeSecs(){
		return currentTimeSecs(System.currentTimeMillis());
	}
}
