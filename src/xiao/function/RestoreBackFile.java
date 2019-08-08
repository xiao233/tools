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
 * 文件还原点设置及文件还原
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
	 * 存放还原点信息
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
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.RestoreBackFile] ： "+e.getMessage());
		}
	}
	
	/**
	 * 关闭文件流
	 * 2019-06-19 15:33:31
	 */
	public void destoryRestoreBackFile() {
		// TODO Auto-generated method stub
		
		if(bw!=null) {
			try {
				bw.close();
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.destoryRestoreBackFile] ： "+e.getMessage());
			}
		}
		
		if(br!=null) {
			try {
				bw.close();
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.destoryRestoreBackFile] ： "+e.getMessage());
			}
		}
	}
	
	/**
	 * 设置还原点
	 * 2019-06-19 11:47:51
	 * @param time		时间戳
	 * @param function 选择的功能
	 * @param resource	源文件位置
	 * @param backup	备份文件位置
	 * @param remark	备注
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
			ApplicationInitial.writeInfo("[RestoreBackFile.setRestorePoint] 设置还原点： "+point);
		} catch (IOException e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.setRestorePoint] ： "+e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 按节点还原文件
	 * 2019-06-19 11:49:03
	 * @param point
	 */
	public void restoreFile(String point) {
		// TODO Auto-generated method stub
		
		RestoreBackFileBean bean = readRestorePoint(point);
		String dis = bean.getResource();
		String src = bean.getBackup();
		
		ApplicationInitial.writeInfo("[RestoreBackFile.restoreFile] 还原： "+point+"还原点信息开始！");
		if(FileUtils.copyFileTo(src,dis)) {
			ApplicationInitial.writeInfo("[RestoreBackFile.restoreFile] 还原： "+point+"还原点信息结束，还原成功！");
		}else {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.restoreFile] 还原： "+point+"还原点信息结束，还原失败！");
		}
	}
	
	/**
	 * 按节点读取还原点信息
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
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePoint] 读取到+"+point+"的还原点信息： "+bean);
		}else {
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePoint] 未读取到+"+point+"的还原点信息！ ");

		}
		
		return bean;
	}
	/**
	 * 读取所有还原点
	 * 2019-06-19 16:16:19
	 * @return
	 */
	public List<Object> readRestorePointAll(){
		// TODO Auto-generated method stub
		
		if(list==null) {
			list = new ArrayList<Object>();
		}
		
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		
		//逐行读取
		String lines  = null;
		try {
			
			while((lines = br.readLine())!=null) {
				if(lines.indexOf(_POINT)==0) {
					Map<String,Object> linesMap = linesToMap(lines);
					listMap.add(linesMap);
				}
			}
			ApplicationInitial.writeInfo("[RestoreBackFile.readRestorePointAll] 共读取到： "+listMap.size()+"条还原点信息！");
		} catch (IOException e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.readRestorePointAll] ： "+e.getMessage());
		}
		
		list =  ConvertToObjectUtils.mapToObject(listMap, RestoreBackFileBean.class);
		return list;
	}
	
	/**
	 * 将文件读取的信息存入map
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
