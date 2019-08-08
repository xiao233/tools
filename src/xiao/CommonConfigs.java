package xiao;

import java.util.Map;
import java.util.TreeMap;

/**
 * ������Ϣ
 * @author xjl
 * 2019-06-06 14:10:03
 */
public class CommonConfigs {

	/**
	 * ���ݿ�����
	 */
	public static String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * url
	 */
	public static String URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8";
	
	/**
	 * ���ݿ������û���
	 */
	public static String USERNAME = "root";
	
	/**
	 * ���ݿ���������
	 */
	public static String PASSWORD  = "123456";
	
	/**
	 * ��ʼ���˵�
	 */
	public static Map<String,String> FUNCTION_MAP = new TreeMap<String,String>();
	
	/**
	 * ͳһ����Hibernateʵ�������ڰ�������δ���ã���Ĭ����package user.entity.hb;��
	 */
	public static String ENTITIES_PACKAGE_HB = "user.entity.hb";
	
	/**
	 * ͳһ����pojoʵ�������ڰ�������δ���ã���Ĭ����package user.entity.pojo;��
	 */
	public static String ENTITIES_PACKAGE_POJO = "user.entity.pojo";

	/**
	 * �����ļ���������
	 */
	public static int BACKFILE_RESERVE = 30;
}
