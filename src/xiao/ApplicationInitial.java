package xiao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * ��Ŀ������ʼ��
 * @author xjl
 * 2019-06-06 14:13:08
 */
public class ApplicationInitial {
	
	/**
	 * ����ϵͳ��־��
	 */
	private static BufferedWriter SYS_LOG_BW = null;
	
	/**
	 * ϵͳ��־�ļ�
	 */
	private static String SYS_LOG_FILE = "user/app.log";
	
	/**
	 * ��Ŀ������ʼ��
	 * 2019-06-06 14:31:21
	 */
	public static void init() {
		
		initSysLog();
		
		initSQL();
		
		initMenu();
		
		initApp();
		
	}


	/**
	 * ��ʼ��Ӧ������
	 * 2019-06-17 13:52:43
	 */
	private static void initApp() {
		// TODO Auto-generated method stub
		try {
			Properties appConfigProperties = LoadPropertiesUtils.getConfigProperties("config/appConfig.properties");
			
			String hbPackage = appConfigProperties.getProperty("ENTITIES_PACKAGE_HB");
			if(!StringUtils.isEmpty(hbPackage)) {
				writeInfo("[ApplicationInitial.initApp]  ��ȡӦ������hb��(ENTITIES_PACKAGE_HB): "+hbPackage);
				CommonConfigs.ENTITIES_PACKAGE_HB = hbPackage;
			}
			
			String pojoPackage = appConfigProperties.getProperty("ENTITIES_PACKAGE_POJO");
			if(!StringUtils.isEmpty(pojoPackage)) {
				writeInfo("[ApplicationInitial.initApp]  ��ȡӦ������֮pojo��(ENTITIES_PACKAGE_POJO): "+pojoPackage);
				CommonConfigs.ENTITIES_PACKAGE_POJO=pojoPackage.trim();
			}
			
			String backFileReserve = appConfigProperties.getProperty("BACKFILE_RESERVE");
			if(!StringUtils.isEmpty(backFileReserve)) {
				writeInfo("[ApplicationInitial.initApp]  ��ȡӦ������֮�����ļ���������(BACKFILE_RESERVE): "+backFileReserve);
				CommonConfigs.BACKFILE_RESERVE=Integer.parseInt(backFileReserve);
			}
			
		} catch (IOException e) {
			writeErrorInfo("[ApplicationInitial.initApp]  ��ȡӦ������:"+e.getMessage());
		}
	}


	/**
	 * ��ʼ���˵�����
	 * 2019-06-17 13:50:41
	 */
	private static void initMenu() {
		// TODO Auto-generated method stub
		try {
			Properties menuProperties = LoadPropertiesUtils.getConfigProperties("config/menu.properties");
			Set<Entry<Object, Object>> menuSet = menuProperties.entrySet();
			for (Entry<Object, Object> menu : menuSet) {
				CommonConfigs.FUNCTION_MAP.put(menu.getKey().toString(), menu.getValue().toString());
				writeInfo("[ApplicationInitial.initMenu]  ��ȡ�˵�����: "
				+menu.getKey().toString().trim()+"."
						+menu.getValue().toString().trim());
			}
		} catch (IOException e) {
			writeErrorInfo("[ApplicationInitial.initMenu]  ��ȡ�˵�����:"+e.getMessage());
		}
	}

	/**
	 * ��ʼ�����ݿ�����
	 * 2019-06-17 13:49:14
	 */
	private static void initSQL() {
		// TODO Auto-generated method stub
		try {
			Properties properties = LoadPropertiesUtils.getConfigProperties("config/sql.properties");
			
			String driver = properties.getProperty("jdbc.driver");
			if(!StringUtils.isEmpty(driver)) {
				writeInfo("[ApplicationInitial.initSQL]  ��ȡ���ݿ�����(DRIVER): "+driver);
				CommonConfigs.DRIVER=driver.trim();
			}
			
			String url = properties.getProperty("jdbc.url");
			if(!StringUtils.isEmpty(url)) {
				writeInfo("[ApplicationInitial.initSQL]  ��ȡ���ݿ�����(URL): "+url);
				CommonConfigs.URL=url.trim();
			}
			
			String username = properties.getProperty("jdbc.username");
			if(!StringUtils.isEmpty(username)) {
				writeInfo("[ApplicationInitial.initSQL]  ��ȡ���ݿ�����(USERNAME): "+username);
				CommonConfigs.USERNAME=username.trim();
			}
			
			String password = properties.getProperty("jdbc.password");
			if(!StringUtils.isEmpty(password)) {
				writeInfo("[ApplicationInitial.initSQL]  ��ȡ���ݿ�����(PASSWORD): "+password);
				CommonConfigs.PASSWORD=password.trim();
			}
		} catch (IOException e) {
			writeErrorInfo("[ApplicationInitial.initSQL]  :"+e.getMessage());
		}
	}
	

	/**
	 * ��ʼ��Ӧ����־
	 * 2019-06-17 13:51:52
	 */
	private static void initSysLog() {
		// TODO Auto-generated method stub
		initSysLogBw();
		
		writeInfo(null);
		writeInfo(null);
		writeInfo("===============================�̡���������===============================");
		writeInfo("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t |");
		writeInfo("|							           ��ӭʹ��								 |");
		writeInfo("|\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t |");
		writeInfo("======================================================================");
	}

	/**
	 * ��ʼ��������־�ļ������
	 * 2019-06-14 15:48:57
	 */
	private  static void initSysLogBw() {
		// TODO Auto-generated method stub
		
		if(SYS_LOG_BW==null) {
			try {
				File sysLogFile = new File(SYS_LOG_FILE);
				if(!sysLogFile.exists()) {
					sysLogFile.createNewFile();
				}
				SYS_LOG_BW = new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(sysLogFile,true)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * ����־
	 * 2019-06-14 15:52:59
	 * @param msg
	 */
	public static void writeInfo(String msg) {
		
		try {
			if(!StringUtils.isEmpty(msg)) {
				SYS_LOG_BW.write(TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24)
						+"	INFO:	"+msg);
			}
			SYS_LOG_BW.newLine();
			SYS_LOG_BW.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �Ǵ�����־
	 * 2019-06-14 15:52:59
	 * @param msg
	 */
	public static void writeErrorInfo(String msg) {
		try {
			if(!StringUtils.isEmpty(msg)) {
				SYS_LOG_BW.write(TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24)
					+"	ERROR:	"+msg);
			}
			SYS_LOG_BW.newLine();
			SYS_LOG_BW.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �رճ�����־�ļ������
	 * 2019-06-14 15:50:38
	 */
	public static void closeSysLogBw() {
		if(SYS_LOG_BW!=null){
			try {
				SYS_LOG_BW.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
