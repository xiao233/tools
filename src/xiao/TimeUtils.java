package xiao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * ʱ�乤����
 * @author xjl
 * 2019-01-30 14:56:37
 */
public class TimeUtils {
	/**
	 * ʱ���ʽ��������
	 */
	public static String FORMAT_DATE = "yyyy-MM-dd"; 
	/**
	 * ʱ����,24��
	 */
	public static String FORMAT_TIME24 = "HH:mm:ss"; 
	/**
	 * ʱ���룬12��
	 */
	public static String FORMAT_TIME12 = "hh:mm:ss"; 
	/**
	 * �����գ�ʱ����12
	 */
	public static String FORMAT_DATE_TIME12 = "yyyy-MM-dd hh:mm:ss"; 
	/**
	 * �����գ�ʱ����24
	 */
	public static String FORMAT_DATE_TIME24 = "yyyy-MM-dd HH:mm:ss";
	
	
	private static DateFormat dateFormat ;
	
	/**
	 * �Թ̶���ʽ��ʾʱ��
	 * 2019-01-30 15:02:02
	 * @param date ʱ��
	 * @param format ʱ���ʽ(�ɴӱ����ȡ��Ҳ���Զ���)
	 * @return
	 */
	public static String getTimeByFormat(Date date,String format) {
		if(StringUtils.isEmpty(format)) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getTimeByFormat]  ʱ���ʽΪ�գ�");
			format = FORMAT_DATE_TIME12;
		}
		dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(date);
	}
	
	/**
	 * �Թ̶���ʽ��ȡ��ǰʱ��
	 * 2019-01-30 15:01:26
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeByFormate(String format) {
		if(StringUtils.isEmpty(format)) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getCurrentTimeByFormate]  ʱ���ʽΪ�գ�");
			format = FORMAT_DATE_TIME12;
		}
		dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(new Date());
	}

	/**
	 * �Թ̶���ʽת��Date
	 * 2019-01-30 15:02:02
	 * @param time ʱ��
	 * @param format ʱ���ʽ(�ɴӱ����ȡ��Ҳ���Զ���)
	 * @return
	 */
	public static Date getTimeByFormat(String time, String format) {
		// TODO Auto-generated method stub
		
		dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(time);
		} catch (ParseException e) {
			ApplicationInitial.writeErrorInfo("[TimeUtils.getTimeByFormat] ʱ��ת������"+e.getMessage());
		}
		return null;
	}
}
