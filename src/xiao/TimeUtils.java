package xiao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * 时间工具类
 * @author xjl
 * 2019-01-30 14:56:37
 */
public class TimeUtils {
	/**
	 * 时间格式，年月日
	 */
	public static String FORMAT_DATE = "yyyy-MM-dd"; 
	/**
	 * 时分秒,24制
	 */
	public static String FORMAT_TIME24 = "HH:mm:ss"; 
	/**
	 * 时分秒，12制
	 */
	public static String FORMAT_TIME12 = "hh:mm:ss"; 
	/**
	 * 年月日，时分秒12
	 */
	public static String FORMAT_DATE_TIME12 = "yyyy-MM-dd hh:mm:ss"; 
	/**
	 * 年月日，时分秒24
	 */
	public static String FORMAT_DATE_TIME24 = "yyyy-MM-dd HH:mm:ss";
	
	
	private static DateFormat dateFormat ;
	
	/**
	 * 以固定格式显示时间
	 * 2019-01-30 15:02:02
	 * @param date 时间
	 * @param format 时间格式(可从本类获取，也可自定义)
	 * @return
	 */
	public static String getTimeByFormat(Date date,String format) {
		if(StringUtils.isEmpty(format)) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getTimeByFormat]  时间格式为空！");
			format = FORMAT_DATE_TIME12;
		}
		dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}
	
	/**
	 * 以固定格式获取当前时间
	 * 2019-01-30 15:01:26
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeByFormate(String format) {
		if(StringUtils.isEmpty(format)) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getCurrentTimeByFormate]  时间格式为空！");
			format = FORMAT_DATE_TIME12;
		}
		dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(new Date());
	}

	/**
	 * 以固定格式转成Date
	 * 2019-01-30 15:02:02
	 * @param time 时间
	 * @param format 时间格式(可从本类获取，也可自定义)
	 * @return
	 */
	public static Date getTimeByFormat(String time, String format) {
		// TODO Auto-generated method stub
		
		dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(time);
		} catch (ParseException e) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getTimeByFormat] 时间转换错误："+e.getMessage());
		}
		return null;
	}
}
