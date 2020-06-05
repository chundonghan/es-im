package com.es.es_im.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd";//

    public static final String DEFAULT_FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";//

    /**
     * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
     * 
     * @param str 字符串
     * @param format 日期格式
     * @return 日期
     * @throws java.text.ParseException
     */
    public static Date strDate(String str, String format)
    {
        if (null == str || "".equals(str))
        {
            return null;
        }
        // 如果没有指定字符串转换的格式，则用默认格式进行转换
        if (null == format || "".equals(format))
        {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try
        {
            date = sdf.parse(str);
            return date;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static java.sql.Date strToSqlDate(String str, String format)
    {
        if (null == str || "".equals(str))
        {
            return null;
        }
        // 如果没有指定字符串转换的格式，则用默认格式进行转换
        if (null == format || "".equals(format))
        {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try
        {
            date = sdf.parse(str);
            return new java.sql.Date(date.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2String(Date currentDate, String formate)
    {
        String result = null;
        SimpleDateFormat formatdater = new SimpleDateFormat(formate);
        result = formatdater.format(currentDate);
        return result;
    }

    /**
     * 字符串转换时间戳
     * 
     * @param str
     * @return
     */

    public static Timestamp strTimestampDefault(String str)
    {
        Date date = strDate(str, DEFAULT_FORMAT_TIMESTAMP);
        return new Timestamp(date.getTime());
    }


    /**
     * 时间转换为字符串
     * 
     * @param date 时间
     * @param format 日期格式
     * @return 字符串
     */
    public static String timestampStr(Date date, String format)
    {
        if (null == date)
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String timestampStr(Date date)
    {
        return timestampStr(date, DEFAULT_FORMAT_TIMESTAMP);
    }

    /**
     * 检验输入是否为正确的日期格式(不含秒的任何情况),严格要求日期正确性,格式:yyyy-MM-dd HH:mm
     * 
     * @param sourceDate
     * @return
     */
    public static boolean checkDate(String sourceDate)
    {
        if (sourceDate == null)
        {
            return false;
        }
        try
        {
            sourceDate = sourceDate.replaceAll("-", "/");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        }
        catch (Exception e)
        {
        }
        return false;
    }

    /**
     * 获取字符串型的 某月份/日期
     * 
     * @param start 指定日期或月份
     * @param value 需要增减的月份数或天数
     * @return 字符串
     */
    public static String getVorTime(String start, int value)
    {
        if (start != null)
        {
            start = start.replaceAll("-", "/");
        }
        else
        {
            return "";
        }
        int mode = 2;
        if (start.length() == 7)
            mode = 1;
        else if (start.length() == 10)
            mode = 2;
        else
        {
            // 我处理不了 你回去吧
            return "";
        }
        SimpleDateFormat simpleDateFormat = null;
        if (mode == 1)
            simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        if (mode == 2)
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try
        {
            Date date = simpleDateFormat.parse(start);
            Calendar calender = Calendar.getInstance();
            calender.setTime(date);
            if (mode == 1)
                calender.add(Calendar.MONTH, value);
            if (mode == 2)
                calender.add(Calendar.DAY_OF_YEAR, value);
            simpleDateFormat.format(calender.getTime());
            return simpleDateFormat.format(calender.getTime()).toString();
        }
        catch (Exception e)
        {
            // System.out.print("DateUtil error:getVorTime()"+e.getMessage());
        }
        return start;
    }

    /**
     * 2个字符串类型的月份之间差
     * 
     * @param datestr1
     * @param datestr2
     * @return 数值
     * @throws ParseException
     */
    public static int getMonthNum(String datestr1, String datestr2) throws ParseException
    {
        datestr1 = datestr1.replaceAll("-", "").replaceAll("/", "").replaceAll("\\.", "");
        datestr2 = datestr2.replaceAll("-", "").replaceAll("/", "").replaceAll("\\.", "");
        // System.out.println (datestr1+","+datestr2);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date1 = format.parse(datestr1);
        Date date2 = format.parse(datestr2);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal2.get(1) - cal1.get(1)) * 12 + (cal2.get(2) - cal1.get(2));
    }

    /**
     * 得当前时间
     */
    public static String getStrDateTime()
    {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_TIMESTAMP);
        return df.format(date);
    }

    /**
     * 得当前时间前后几小时
     */
    public static String getStrDateTimeAdd(int hour)
    {
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) + hour);

        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_TIMESTAMP);
        return df.format(now.getTime());
    }

    /**
     * 得当前日期
     */
    public static String getStrDate()
    {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);
        return df.format(date);
    }

    /**
     * 毫秒值转年月日时分秒毫秒
     */
    public static String getDateTime(Long time)
    {
        Date date;
        if (time == null)
        {
            date = new Date();
        }
        else
        {
            date = new Date(time);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return df.format(date);
    }
    
    public static String getDateTime(Long time,String dateFormat)
    {
        Date date;
        if (time == null)
        {
            date = new Date();
        }
        else
        {
            date = new Date(time);
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(date);
    }
    /**
     * @Title: getDayOfWeek
     * @Description: 得星期几（周一是1，周日是7）
     * @param date
     * @return 
     * @author: sunlq
     * @time:2017年4月13日 下午4:21:19
     * history:
     * 1、2017年4月13日 sunlq 创建方法
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar c = Calendar.getInstance();
        if (date == null)
        {
            c.setTime(new Date());
        }
        else
        {
            c.setTime(date);
        }
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
        if (isFirstSunday)
        {
            weekday = weekday - 1;

            if (weekday == 0)
            {
                weekday = 7;
            }
        }
        return weekday;
    }
    public static String transformTimeStamp(Timestamp timestamp) {
    	
    	String time_str = "";
    	long currentTimestamp = System.currentTimeMillis();
    	
    	long todayZeroTimestamp = currentTimestamp - (currentTimestamp + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
    	
    	long oneDayTimestamps= Long.valueOf(60*60*24*1000);
    	
    	long createTimestamp = timestamp.getTime();
    	
    	
    	long diff = (currentTimestamp - createTimestamp);
    	
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.setFirstDayOfWeek(Calendar.MONDAY);
    	calendar.setTime(new Date(currentTimestamp));
    	int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
    	calendar.setTime(new Date(createTimestamp));
    	int createWeek = calendar.get(Calendar.WEEK_OF_YEAR);
    	if(diff< oneDayTimestamps && currentTimestamp>=todayZeroTimestamp) {//当天
    		time_str = getDateTime(createTimestamp, "HH:mm");
    	}else if((createTimestamp >= (todayZeroTimestamp-oneDayTimestamps) 
    			&& createTimestamp<todayZeroTimestamp)) {//昨天
    		time_str = "昨天 " + getDateTime(createTimestamp, "HH:mm");
    	}else if(createTimestamp < (todayZeroTimestamp-oneDayTimestamps)
    			&& (currentWeek-createWeek) == 0) {//本周
    		String week = "";
    		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
    		switch(day_of_week) {
    			case 2:
    				week = "星期一";
    				break;
    			case 3:
    				week = "星期二";
    				break;
    			case 4:
    				week = "星期三";
    				break;
    			case 5:
    				week = "星期四";
    				break;
    			case 6:
    				week = "星期五";
    				break;
    			case 7:
    				week = "星期六";
    				break;
    			case 1:
    				week = "星期日";
    				break;
    		}
    		time_str = week + getDateTime(createTimestamp, "HH:mm");
    	}else {//其他日期显示
    		time_str = getDateTime(createTimestamp, "yyyy年MM月dd日 HH:mm");
    	}
    	return time_str;
    }
    
    public static void main(String[] args) {
//    	transformTimeStamp(Timestamp.valueOf("2019-9-2 11:10:10"));
//    	
//    	Calendar calendar = Calendar.getInstance(); 
//    	calendar.setFirstDayOfWeek(Calendar.MONDAY);
//    	calendar.setTime(new Date(Timestamp.valueOf("2019-8-31 11:10:10").getTime()));
//    	int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
//    	System.out.println(day_of_week);
    	System.out.println(date2String(new Date(),"mm:ss"));
	}
}
