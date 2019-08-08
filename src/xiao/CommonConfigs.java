package xiao;

import java.util.Map;
import java.util.TreeMap;

/**
 * 配置信息
 * @author xjl
 * 2019-06-06 14:10:03
 */
public class CommonConfigs {

	/**
	 * 数据库驱动
	 */
	public static String DRIVER = "com.mysql.jdbc.Driver";
	
	/**
	 * url
	 */
	public static String URL = "jdbc:mysql://localhost:3306/userdb?useUnicode=true&characterEncoding=utf8";
	
	/**
	 * 数据库连接用户名
	 */
	public static String USERNAME = "root";
	
	/**
	 * 数据库连接密码
	 */
	public static String PASSWORD  = "123456";
	
	/**
	 * 初始化菜单
	 */
	public static Map<String,String> FUNCTION_MAP = new TreeMap<String,String>();
	
	/**
	 * 统一设置Hibernate实体类所在包名，若未设置，则默认在package user.entity.hb;下
	 */
	public static String ENTITIES_PACKAGE_HB = "user.entity.hb";
	
	/**
	 * 统一设置pojo实体类所在包名，若未设置，则默认在package user.entity.pojo;下
	 */
	public static String ENTITIES_PACKAGE_POJO = "user.entity.pojo";

	/**
	 * 备份文件留存天数
	 */
	public static int BACKFILE_RESERVE = 30;
}
