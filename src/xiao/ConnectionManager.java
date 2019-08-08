package xiao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * ��ȡ�����ݿ������
 * @author xjl
 * 2018-12-25 08:58:45
 */
public class ConnectionManager {
	
	
	private static Connection conn ;
	
	static {
		try {
			Class.forName(CommonConfigs.DRIVER);
		} catch (ClassNotFoundException e) {
			ApplicationInitial.writeErrorInfo("[ConnectionManager]  ��ȡ����ʧ�ܣ� "+e.getMessage());
		}
	}
	
	
	public static Connection getConnection() {
		try {
			conn =  DriverManager.getConnection(CommonConfigs.URL, CommonConfigs.USERNAME, CommonConfigs.PASSWORD);
		}  catch (SQLException e) {
			ApplicationInitial.writeErrorInfo("[ConnectionManager]  ��ȡ����ʧ�ܣ�"+e.getMessage());
		}
		return conn;
	}
	
	public static void closeConnection(Connection con,Statement st,ResultSet rs) {
		
			try {
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e) {
				ApplicationInitial.writeErrorInfo("[ConnectionManager]  �ر����ݿ�����ʧ�ܣ� "+e.getMessage());
			}
			
			try {
				if(st!=null) {
					st.close();
				}
			} catch (SQLException e) {
				ApplicationInitial.writeErrorInfo("[ConnectionManager]  �ر����ݿ�����ʧ�ܣ� "+e.getMessage());
			}
			
			try {
				if(con!=null) {
					con.close();
				}
			} catch ( SQLException e) {
				ApplicationInitial.writeErrorInfo("[ConnectionManager]  �ر����ݿ�����ʧ�ܣ� "+e.getMessage());
			}
	}

	/**
	 * �������û�ȡ��ǰ���ݿ���
	 * 2019-06-06 11:47:04
	 * @return
	 */
	public static Object getCurrentDatabase() {
		// TODO Auto-generated method stub
		return CommonConfigs.URL.substring(CommonConfigs.URL.lastIndexOf("/")+1,CommonConfigs.URL.indexOf("?")).trim();
	}
	
}
