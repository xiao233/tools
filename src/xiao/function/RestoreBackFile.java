package xiao.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xiao.ApplicationInitial;
import xiao.ConvertToObjectUtils;
import xiao.FileUtils;
import xiao.TimeUtils;
import xiao.function.bean.RestoreBackFileBean;

/**
 * �ļ���ԭ�����ü��ļ���ԭ
 * @author xjl
 * 2019-06-19 11:44:01
 */
public class RestoreBackFile {
	
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	private String fileName = "config/restore_point.txt";
	
	private String _FUNCTION = "function";
	private String _TIME = "time";
	private String _RESORCE = "resource";
	private String _BACKUP = "backup";
	private String _REMARK = "remark";
	private String _POINT = "point_";
	
	/**
	 * ��Ż�ԭ����Ϣ
	 */
	private List<Object> list;
	
	public RestoreBackFile() {
		 try {
			br = new BufferedReader(
					 new InputStreamReader(
							 new FileInputStream(
									 new File(fileName))));
			
			bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(
									new File(fileName),true)));
			
		} catch (FileNotFoundException e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.RestoreBackFile] �� "+e.getMessage());
		}
	}
	
	/**
	 * �ر��ļ���
	 * 2019-06-19 15:33:31
	 */
	public void destoryRestoreBackFile() {
		// TODO Auto-generated method stub
		
		if(bw!=null) {
			try {
				bw.close();
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.destoryRestoreBackFile] �� "+e.getMessage());
			}
		}
		
		if(br!=null) {
			try {
				bw.close();
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.destoryRestoreBackFile] �� "+e.getMessage());
			}
		}
	}
	
	/**
	 * ���û�ԭ��
	 * 2019-06-19 11:47:51
	 * @param time		ʱ���
	 * @param function ѡ��Ĺ���
	 * @param resource	Դ�ļ�λ��
	 * @param backup	�����ļ�λ��
	 * @param remark	��ע
	 */
	public boolean setRestorePoint(String timeP,String function,String resource,String backup,String remark) {
		// TODO Auto-generated method stub
		
		String time = TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24);
		String point = _POINT+timeP+"="
						+_FUNCTION+":"+function+"|"
						+_TIME+":"+time+"|"
						+_RESORCE+":"+resource+"|"
						+_BACKUP+":"+backup+"|"
						+_REMARK+":"+remark;
		
		try {
			bw.newLine();
			bw.write(point);
			bw.flush();
			ApplicationInitial.writeInfo("[RestoreBackFile.setRestorePoint] ���û�ԭ�㣺 "+point);
		} catch (IOException e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.setRestorePoint] �� "+e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * ���ڵ㻹ԭ�ļ�
	 * 2019-06-19 11:49:03
	 * @param point
	 */
	public void restoreFile(String point) {
		// TODO Auto-generated method stub
		
		RestoreBackFileBean bean = readRestorePoint(point);
		String dis = bean.getResource();
		String src = bean.getBackup();
		
		ApplicationInitial.writeInfo("[RestoreBackFile.restoreFile] ��ԭ�� "+point+"��ԭ����Ϣ��ʼ��");
		if(FileUtils.copyFileTo(src,dis)) {
			ApplicationInitial.writeInfo("[RestoreBackFile.restoreFile] ��ԭ�� "+point+"��ԭ����Ϣ��������ԭ�ɹ���");
		}else {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.restoreFile] ��ԭ�� "+point+"��ԭ����Ϣ��������ԭʧ�ܣ�");
		}
	}
	
	/**
	 * ���ڵ��ȡ��ԭ����Ϣ
	 * 2019-06-19 17:04:27
	 * @param point
	 * @return
	 */
	public RestoreBackFileBean readRestorePoint(String point) {
		// TODO Auto-generated method stub
		if(list==null) {
			readRestorePointAll();
		}
		
		RestoreBackFileBean bean = null;
		
		for (Object object : list) {
			bean = (RestoreBackFileBean)object;
			if(bean.getPoint().equals(point)) {
				 break;
			}
		}
		
		if(bean!=null) {
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePoint] ��ȡ��+"+point+"�Ļ�ԭ����Ϣ�� "+bean);
		}else {
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePoint] δ��ȡ��+"+point+"�Ļ�ԭ����Ϣ�� ");

		}
		
		return bean;
	}
	/**
	 * ��ȡ���л�ԭ��
	 * 2019-06-19 16:16:19
	 * @return
	 */
	public List<Object> readRestorePointAll(){
		// TODO Auto-generated method stub
		
		if(list==null) {
			list = new ArrayList<Object>();
		}
		
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		
		//���ж�ȡ
		String lines  = null;
		try {
			
			while((lines = br.readLine())!=null) {
				if(lines.indexOf(_POINT)==0) {
					Map<String,Object> linesMap = linesToMap(lines);
					listMap.add(linesMap);
				}
			}
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePointAll] ����ȡ���� "+listMap.size()+"����ԭ����Ϣ��");
		} catch (IOException e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.readRestorePointAll] �� "+e.getMessage());
		}
		
		list =  ConvertToObjectUtils.mapToObject(listMap, RestoreBackFileBean.class);
		return list;
	}
	
	/**
	 * ���ļ���ȡ����Ϣ����map
	 * 2019-06-19 16:23:49
	 * @param lines
	 * @return
	 */
	 private Map<String, Object> linesToMap(String lines) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		String v1[] = lines.split("=");
		map.put("point", v1[0]);
		
		String v2[] = v1[1].split("\\|");
		for (String str : v2) {
			String temp = str;
			String key = temp.substring(0,temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);
			map.put(key, value);
		}
		return map;
	}

	
}
