package xiao.main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import xiao.ApplicationInitial;
import xiao.CommonConfigs;
import xiao.StringUtils;
import xiao.function.DiskClean;
import xiao.function.RestoreBackFile;
import xiao.function.ScaleChangeEach;
import xiao.function.TableConvertToEntity;
import xiao.function.bean.RestoreBackFileBean;

public class BornEntity {
	
	private static Scanner cin ;
	
	private FunctionAccount functionAccount = new FunctionAccount();
	
	public static void main(String args[]) {
		
		before();
		
		menu();
		
		//new TableConvertToEntity().convertToHibernate(false, false, false,"*");
		
		/*Map<String,String> map = new ScaleChangeEach().scaleChangeEach("10111|2", "6");
		System.out.println(map.get("msg")+"--->"+map.get("rs"));
		map = new ScaleChangeEach().scaleChangeEach("13151|7", "9");
		System.out.println(map.get("msg")+"--->"+map.get("rs"));*/
		
		/*showListObj(
				new RestoreBackFile()
				.readRestorePointAll()
				);*/
		
		//System.out.println("--->\n"+new RestoreBackFile().readRestorePoint("point_1560929963172"));
		
	}
	
	/**
	 * ��Ŀ����֮ǰ
	 * 2019-06-06 14:33:44
	 */
	public  static void before() {
		ApplicationInitial.init();
		cin = new Scanner(System.in);
	}
	
	
	/**
	 * �˳�����
	 * 2019-06-06 14:47:14
	 */
	public  static void after() {
		System.err.println("\n\t���򼴽��˳������Ժ�......");
		try {
			cin.close();
			ApplicationInitial.closeSysLogBw();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
		}
		System.err.print("\t�����˳��ɹ�......");
		System.exit(0);
	}
	
	/**
	 * ���ò˵�
	 * 2019-06-06 14:36:21
	 */
	public  static void menu() {
		System.out.println("\n===================================================================================================");
		System.out.println("\t\t\t- - - - - - - - - ����ӭ��ʹ���� - - - - - - - - -");
		System.out.println("===================================================================================================");
		
		for(Entry<String, String> menu : CommonConfigs.FUNCTION_MAP.entrySet()) {
			System.out.println("|\t\t"+menu.getKey().toString().trim()+"."+menu.getValue().toString().trim());
		}
		System.out.println("|\t\t0.�˳�����");
		System.out.println("|\t\t#.�������˵�");
		System.out.println("===================================================================================================");
		
		while(true) {
			new BornEntity().choseFunction();
		}
	}
	
	/**
	 * ѡ����
	 * 2019-06-06 14:39:59
	 */
	public  void choseFunction() {
		
		
		String chose = null;
		System.out.print("\n\t����������Ҫ�Ĺ���: ");
		chose = cin.nextLine();
		
		if(!StringUtils.isEmpty(CommonConfigs.FUNCTION_MAP.get(chose))) {
			choseFunction(chose);
		}
		switch(chose) {

			case "1":
				functionAccount.function1Account();
				tableConvertToEntity();
				break;
			case "2":
				functionAccount.function2Account();
				scaleChangeEach();
				break;
			case "3":
				functionAccount.function3Account();
				break;
			case "4":
				functionAccount.function4Account();
				diskClean();
				break;
			case "5":
				functionAccount.function5Account();
				restoreBackFile();
				break;
			case "0":
				after();
				break;
			case "#":
				menu();
				break;
				default:
					if(!StringUtils.isEmpty(CommonConfigs.FUNCTION_MAP.get(chose))) {
						ApplicationInitial.writeErrorInfo("����  "+chose+"."+CommonConfigs.FUNCTION_MAP.get(chose)+"��δʵ�֣������ڴ���");
					}
					menu();
					break;
			
		}
	}
	

	private void choseFunction(String chose) {
		// TODO Auto-generated method stub
		
		ApplicationInitial.writeInfo("ѡ����  "+chose+"."+CommonConfigs.FUNCTION_MAP.get(chose));
	}

	/**
	 * �����ݿ��ת���ɶ�Ӧ��ʵ����
	 * 2019-06-06 14:54:48
	 */
	private  void tableConvertToEntity() {
		// TODO Auto-generated method stub
		
		TableConvertToEntity tcte = new TableConvertToEntity();
		
		System.out.print("\t\t  �Ƿ���Ҫ���л�(y/n)�� ");
		String isSeria= cin.nextLine();
		
		System.out.print("\t\t  �Ƿ���Ҫ��дtoString����(y/n)�� ");
		String toString= cin.nextLine();
		
		System.out.print("\t\t  �Ƿ���Ҫȫ�εĹ��췽��(y/n)�� ");
		String allParams= cin.nextLine();
		
		System.out.print("\t\t  ��������Ҫ���ɵı����� ");
		String tableNames= cin.nextLine();
		
		String chose = null;
		System.out.print("\t\t  ������ѡ���ʵ������: ");
		chose = cin.nextLine();
		
		if("h".equals(chose)||"H".equals(chose)) {
			tcte.convertToHibernate("y".equals(isSeria),
					"y".equals(toString),
					"y".equals(allParams),
					tableNames);
		}else {
			tcte.convertToPOJO("y".equals(isSeria),
					"y".equals(toString),
					"y".equals(allParams),
					tableNames);
		}
		
	}

	/**
	 * 2��8��10��16��������ת��
	 * 2019-06-17 16:18:55
	 */
	private void scaleChangeEach() {
		// TODO Auto-generated method stub
		
		System.out.print("\t\t  ��������Ҫת�������� ");
		String data= cin.nextLine();
		System.out.print("\t\t  ������ת�������ͣ� ");
		String type= cin.nextLine();
		Map<String,String> map =new ScaleChangeEach().scaleChangeEach(data, type);
		
		System.err.println("\t\t ��������"+map.get("msg"));
		if("succ".equals(map.get("code"))) {
			System.err.println("\t\t ת�������"+map.get("rs"));
		}
		
	}
	
	/**
	 * ��������
	 * 2019-06-21 14:05:09
	 */
	private void diskClean() {
		// TODO Auto-generated method stub
		
		DiskClean diskClean = new DiskClean();
		System.out.print("\t\t ������ָ� ");
		String cmd = cin.nextLine();
		
		String opers[] = cmd.trim().split("\\|");
		if(opers!=null && opers.length==2
				&&(opers[0].equals("c")||opers[0].equals("cb"))) {
			diskClean.clean(opers);
		}else {
			System.out.println("\t\t ����ʧ�ܣ�ָ���ʽ���ԣ�");
			ApplicationInitial.writeErrorInfo("[BornEntity.diskClean] ָ���ʽ���ԣ�");
		}
	}
	
	/**
	 * ��ԭ�ļ���Ϣ
	 * 2019-06-19 17:16:51
	 */
	private void restoreBackFile() {
		// TODO Auto-generated method stub
		
		RestoreBackFile restoreBackFile = new RestoreBackFile();
		
		System.out.print("\t\t ��������������� ");
		String oper= cin.nextLine();
		
		if("*".equals(oper)) {
			showListObj(restoreBackFile.readRestorePointAll());
		}else {
			String opers[] = oper.split("\\|");
			if(opers.length==3&&opers[2].equals("f")) {
				
				RestoreBackFileBean bean = restoreBackFile.readRestorePoint(opers[1]);
				System.out.println(bean);
				
			}else if(opers.length==3&&opers[2].equals("b")) {
				
				restoreBackFile.restoreFile(opers[1]);
				
			}else{
				System.out.println("\t\t ����ʧ�ܣ��������ݲ����Ϲ淶��");
				ApplicationInitial.writeErrorInfo("[BornEntity.restoreBackFile] ����ʧ�ܣ��������ݲ����Ϲ淶��");
			}
		}
		restoreBackFile.destoryRestoreBackFile();
	}
	
	private static void showListObj(List<Object> tblConfigsObject) {
		// TODO Auto-generated method stub
		for (Object object : tblConfigsObject) {
			System.out.println(object.toString());
			System.out.println("\t----------------------------------------------------------------------\n");
		}
	}
	
}
