package xiao.main;

import xiao.CommonConfigs;

/**
 * 各个功能说明
 * @author xjl
 * 2019-06-06 14:56:54
 */
public class FunctionAccount{

	/**
	 * 功能1.表转换实体类说明
	 * 2019-06-06 14:57:24
	 */
	public  void function1Account() {
		// TODO Auto-generated method stub
		functionStart();
		
		System.err.println("\t··········································································");
		System.err.println("\t\ta).该功能用于根据数据库的表生成相应的实体类;");
		System.err.println("\t\tb).输入‘h/H’生成Hibernate实体;");
		System.err.println("\t\tc).输入‘p/P’生成POJO实体,默认生成POJO实体;");
		System.err.println("\t\td).输入表名时，各个表名以','隔开，若输入'*'表示为当前数据库所有表生成实体;");
		System.err.println("\t\te).导出路劲为程序user/entity文件夹下;");
		System.err.println("\t··········································································");
	}
	
	/**
	 * 功能2.进制转换
	 * 2019-06-17 14:24:02
	 */
	public void function2Account() {
		// TODO Auto-generated method stub
		functionStart();
		
		System.err.println("\t··········································································");
		System.err.println("\t\ta).该功能用于各种2-16进制之间的转换;");
		//System.err.println("\t\tb).二进制数字以‘b或B’结尾(Binary);");
		//System.err.println("\t\tc).八进制数字以‘o或O’结尾(Octal);");
		//System.err.println("\t\td).十六进制数字以‘h或H’结尾(Hexadecimal);");
		System.err.println("\t\tb).默认十进制，以‘不填后缀或d或D’结尾(Decimal);");
		System.err.println("\t\tc).通用进制，以‘|进制’结尾，如三进制数12|3=十进制数5;");
		System.err.println("\t\td).通用类型2-16，如类型2为二进制;");
		System.err.println("\t··········································································");
	}

	/**
	 * 功能3.文件查找、替换
	 * 2019-06-19 10:36:08
	 */
	public void function3Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t··········································································");
		System.err.println("\t\ta).该功能用于文件、文件内容查找，以及文件内容替换;");
		System.err.println("\t\tb).文件查找，找出满足要求的文件列表及统计总文件数;");
		System.err.println("\t\tc).文件内容查找，找出满足要求的文件列表及比对各个文件信息;");
		System.err.println("\t\td).文件内容替换，找出满足要求的文件列表，比对各个文件信息并替换内容;");
		System.err.println("\t\te).执行文件内容替换，会备份相应文件内容到user/file/backup文件夹下("+CommonConfigs.BACKFILE_RESERVE+"天);");
		System.err.println("\t\tf).进行相应操作后，可在"+CommonConfigs.BACKFILE_RESERVE+"天内进行还原;");
		System.err.println("\t··········································································");
	}

	/**
	 * 功能4.系统磁盘清理（删除后不可还原，请慎重！）
	 * 2019-06-19 10:36:22
	 */
	public void function4Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t··········································································");
		System.err.println("\t\ta).该功能用于磁盘文件清理;");
		System.err.println("\t\tb).清理指定文件或文件夹，命令形式‘c|文件名’;");
		System.err.println("\t\tc).清理空文件或空文件夹，命令形式‘cb|文件名’;");
		System.err.println("\t\td).该功能，会备份相应文件内容到user/disk文件夹下("+CommonConfigs.BACKFILE_RESERVE+"天);");
		System.err.println("\t\te).进行相应操作后，可在"+CommonConfigs.BACKFILE_RESERVE+"天内进行还原;");
		System.err.println("\t··········································································");
	}
	
	/**
	 * 功能5.文件还原
	 * 2019-06-19 10:36:22
	 */
	public void function5Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t··········································································");
		System.err.println("\t\ta).该功能用于在本程序操作且操作时间在"+CommonConfigs.BACKFILE_RESERVE+"天内的文件还原;");
		System.err.println("\t\tb).将还原到文件操作前的状态及位置;");
		System.err.println("\t\tc).还原成功后会删除相应备份内容;");
		System.err.println("\t\td).输入‘*’查看所有还原点信息;");
		System.err.println("\t\te).输入‘point|’加‘节点’加‘|f’形式查看指定节点信息;");
		System.err.println("\t\tf).输入‘point|’加‘节点’加‘|b’形式还原节点信息对应内容;");
		System.err.println("\t··········································································");
	}
	
	/**
	 * 某个功能执行结束
	 * 2019-06-06 15:41:12
	 */
	public  void functionEnd() {
		// TODO Auto-generated method stub
		System.err.println("\n\n\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~操·作·结·束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
	}
	
	/**
	 * 某个功能执行开始
	 * 2019-06-06 15:41:12
	 */
	public  void functionStart() {
		// TODO Auto-generated method stub
		System.err.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~操·作·开·始~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
	}


}
