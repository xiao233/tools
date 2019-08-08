package xiao.function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import xiao.ApplicationInitial;
import xiao.CommonConfigs;
import xiao.CommonConstants;
import xiao.CommonSQLOperationUtils;
import xiao.StringUtils;
import xiao.TimeUtils;
import xiao.model.InformationSchemaColumns;
import xiao.model.InformationSchemaTables;

/**
 *  功能1.表转换实体类
 * @author xjl
 * 2019-06-06 15:14:17
 */
public class TableConvertToEntity {

	/**
	 * 存放java类型
	 */
	private Map<String,Class<?>> typeMap = new TreeMap<String,Class<?>>();
	/**
	 * 将表转换POJO实体类
	 * 2019-06-06 15:17:32
	 * @param isSeria   是否序列化
	 * @param allParams	是否创建全参的构造方法
	 * @param toString	是否需要重写toString
	 * @param tableNames 需要创建的表列表以","隔开,填"*"表则表示当前库所有
	 */
	public void convertToPOJO(boolean isSeria,boolean toString,boolean allParams,String tableNames) {
		
		String disDir= "user/entity/pojo";
		
		
		File dirFile = new File(disDir);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		if(StringUtils.isEmpty(tableNames) || tableNames.trim().equals("*")) {
			List<Object> tables = CommonSQLOperationUtils.getAllTblTablesALLObj();
			for (Object object : tables) {
				InformationSchemaTables tablesInf = (InformationSchemaTables)object;
				if(convertOneTableToPOJO(disDir,isSeria,toString,allParams,tablesInf.getTableName())) {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ：表"+tablesInf.getTableName()+"实体类创建成功！");
				}else {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ：表"+tablesInf.getTableName()+"实体类创建失败！");
				}
			}
			return;
		}
		
		String tableNms[] = tableNames.trim().split(",");
		
		for (String tableName : tableNms) {
			if(convertOneTableToPOJO(disDir,isSeria,toString,allParams,tableName)) {
				
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ：表"+tableName+"实体类创建成功！");
			}else {
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ：表"+tableName+"实体类创建失败！");
			}
		}
	}
	
	/**
	 * 将表转换POJO实体类
	 * 2019-06-06 15:17:32
	 * @param disDir	转换到的目的文件夹
	 * @param isSeria   是否序列化
	 * @param allParams	是否创建全参的构造方法
	 * @param toString	是否需要重写toString
	 * @param tableNames 需要创建的表
	 */
	public boolean convertOneTableToPOJO(String disDir,boolean isSeria, boolean toString, boolean allParams, String tableName) {
		// TODO Auto-generated method stub
		
		
		//查询表对应的字段信息
		List<Object> columnsInfs = CommonSQLOperationUtils.getTblColumnsALLObj(tableName);
		
		if(columnsInfs!=null &&columnsInfs.size()==0) {
			return false;
		}
		
		//实体名、文件名
		String entityName = StringUtils.changeName(tableName, true);
		
		
		
		//初始化文件内容
		BufferedWriter bw = fileContentInit(disDir,entityName,true);
		
		//以键-字段名，值-字段类型存储主键字段信息
		Map<String,String> keysMap = new HashMap<String, String>();
		
		/**
		 * 获取需要导入的包、及主键
		 */
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			if(!StringUtils.isEmpty(column.getColumnKey())
					&&"pri".equals(column.getColumnKey().trim().toLowerCase())) {
				keysMap.put(columnName, columnType);
			}
		}
		
		/**
		 * 导包
		 */
		writeLines(bw,null);
		if(isSeria) {
			writeLines(bw,"import java.io.Serializable;");
		}
		for(Entry<String, Class<?>> entry : typeMap.entrySet()) {
			writeLines(bw,"import "+entry.getValue().getName()+";");
		}
		writeLines(bw,null);
		
		/**
		 * 类注解
		 */
		writeLines(bw,"/**");
		writeLines(bw," * POJO实体类");
		writeLines(bw," * "+TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24));
		writeLines(bw," */");
		
		/**
		 * 是否序列化 
		 */
		if(isSeria) {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" implements Serializable {");
		}else {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" {");
		}
		
		/**
		 * 属性及构造方法
		 */
		List<String> constructParams = new ArrayList<String>();
		
		//每行参数
		String lineParams = "";
		//每行参数个数
		int lineParamsCount = 0;
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			String columnComment = column.getColumnComment();
			
			writeLines(bw, null);
			if(keysMap.containsKey(columnName)) {
				writeLines(bw, "\t//...主键... ");
			}
			String lines = "\t"+CommonConstants.ACCESS_PRIVATE
					+" "+columnType
					+" "+columnName+" = null;";
			if(!StringUtils.isEmpty(columnComment)) {
				lines+=" //"+columnComment;
			}
			writeLines(bw, lines);
			
			if(allParams) {
				/**
				 * 全参的构造方法每3个换行
				 */
				if(lineParamsCount>=3) {
					constructParams.add(lineParams);
					lineParams = "";
					lineParamsCount = 0;
				}
				lineParams += columnType+" "+columnName+", ";
				lineParamsCount++;
			}
		}
		constructParams.add(lineParams);
		
		/**
		 * 需要全参的构造方法
		 */
		if(allParams) {
			
			//先创建无参的构造方法
			writeLines(bw, null);
			writeLines(bw, "\tpublic "+entityName+"() {");
			writeLines(bw, null);
			writeLines(bw, "\t}");
			
			writeLines(bw, null);
			
			/**
			 * 创建全参的构造方法
			 */
			int blankLength = 0;
			for (int i=0;i< constructParams.size();i++) {
				if(i==0) {
					blankLength = ("\tpublic "+entityName+"(").length();
					writeLines(bw, "\tpublic "+entityName+"("+constructParams.get(i));
				}else if(constructParams.size()-1==i){
					writeLines(bw, getBlank(blankLength)
							+constructParams.get(i).substring(0, constructParams.get(i).length()-2)+") {");
				}else {
					writeLines(bw, getBlank(blankLength)+constructParams.get(i));
				}
			}
			
			
			for (Object object : columnsInfs) {
				
				InformationSchemaColumns column =(InformationSchemaColumns) object;
				String columnName = StringUtils.changeName(column.getColumnName(), false);;
				writeLines(bw,"\t\tthis."+columnName+" = "+columnName+";");
			}
			
			writeLines(bw,"\t}");
			
			
		}
		
	
		/**
		 * get/set
		 */
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), true);
			String columnType = getColumnType(column.getColumnType());
			String columnNameF = StringUtils.changeName(column.getColumnName(), false);
			
			//get方法
			writeLines(bw, null);
			writeLines(bw,"\tpublic "+columnType+" get"+columnName+"() {");
			writeLines(bw,"\t\treturn this."+columnNameF+";");
			writeLines(bw,"\t}");
			
			
			//set方法
			writeLines(bw, null);
			writeLines(bw,"\tpublic void set"+columnName
					+"("+columnType +" "+columnNameF+") {");
			writeLines(bw,"\t\t this."+columnNameF+" = "+columnNameF+";");
			writeLines(bw,"\t}");
		}
		
		if(toString) {
			/**
			 * toString方法
			 */
			writeLines(bw, null);
			writeLines(bw, "\t@Override");
			writeLines(bw, "\tpublic String toString() {");
			int blankLength = 0;
			for (int i=0;i<columnsInfs.size();i++) {
				
				InformationSchemaColumns column =(InformationSchemaColumns) columnsInfs.get(i);
				String columnName = StringUtils.changeName(column.getColumnName(), false);
				if(i==0) {
					blankLength = ("\t\treturn\""+entityName+"[").length();
					writeLines(bw,"\t\treturn\""+entityName+"["+columnName+" = \"+"+columnName+"+\", \"");
				}
				else if(i<columnsInfs.size()-1) {
					writeLines(bw,getBlank(blankLength)+"+ \""+columnName+" = \"+"+columnName+"+\", \"");
				}else {
					writeLines(bw,getBlank(blankLength)+"+ \""+columnName+" = \"+"+columnName+"+\"];\"");
				}
			}
			writeLines(bw,"\t}");
			
		}
		
		/**
		 * 文件最后的关闭大括号
		 */
		writeLines(bw,"}");
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.convertOneTableToPOJO]	："+e.getMessage());
		}
		
		return true;
	}

	/**
	 * 获取指定长度的空格
	 * 2019-06-11 11:38:30
	 * @param blankLength
	 * @return
	 */
	private String getBlank(int blankLength) {
		// TODO Auto-generated method stub
		
		String blank = "";
		for (int i = 0; i < blankLength; i++) {
			blank+=" ";
		}
		return blank;
	}

	/**
	 * 获取字段类型
	 * 2019-06-11 09:50:28
	 * @param columnType
	 * @return
	 */
	private String getColumnType(String columnType) {
		// TODO Auto-generated method stub
		columnType = columnType.substring(0, columnType.length()-1).toLowerCase().replace('(', '_');
		String types[] = columnType.split("_");
		
		//数据库字段定义长度
		int columnLength = types.length>=2?Integer.parseInt(types[1]):0;
		if(types[0].indexOf("int")>=0) {//整型
			
			if(columnLength>9) {
				//typeMap.put(CommonConstants._DATA_TYPE_LONG, Long.class);
				return CommonConstants._DATA_TYPE_LONG;
			}else {
				//typeMap.put(CommonConstants._DATA_TYPE_INTEGER, Integer.class);
				return CommonConstants._DATA_TYPE_INTEGER;
			}
			
		}else if(types[0].indexOf("float")>=0) {//单精度浮点
			
			//typeMap.put(CommonConstants._DATA_TYPE_FLOAT, Float.class);
			return CommonConstants._DATA_TYPE_FLOAT;
			
		}else if(types[0].indexOf("double")>=0
				||types[0].indexOf("decimal")>=0) {//双精度浮点
			
			//typeMap.put(CommonConstants._DATA_TYPE_DOUBLE, Double.class);
			return CommonConstants._DATA_TYPE_DOUBLE;
			
		}else if(types[0].indexOf("date")>=0//时间类型
				||types[0].indexOf("time")>=0
				||types[0].indexOf("year")>=0
				||types[0].indexOf("datetime")>=0
				||types[0].indexOf("timestamp")>=0) {
			
			typeMap.put(CommonConstants._DATA_TYPE_DATE, Date.class);
			return CommonConstants._DATA_TYPE_DATE;
			
		}else {//默认字符类型
			
			//typeMap.put(CommonConstants._DATA_TYPE_STRING, String.class);
			return CommonConstants._DATA_TYPE_STRING;
		}
	}

	/**
	 * 初始化文件内容
	 * 2019-06-10 17:50:37
	 * @param fileName
	 * @param entityName
	 * @param isPojo 是否是pojo实体类 
	 * @return 返回文件输出缓存流
	 */
	private BufferedWriter fileContentInit(String dirName, String entityName, boolean isPojo) {
		// TODO Auto-generated method stub
		
		//创建对应的实体文件
		File file = new File(dirName+File.separator+entityName+CommonConstants.FILE_TYPE_JAVA);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.fileContentInit] ："+e.getMessage());
		}
		
		/**
		 * 创建文件输出流
		 */
		BufferedWriter bw = null;
		String lines = "";
		
		try {
			bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(file)));
			lines = "package "+(isPojo?CommonConfigs.ENTITIES_PACKAGE_POJO:CommonConfigs.ENTITIES_PACKAGE_HB)+";";
			writeLines(bw,lines);
					 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.fileContentInit] ："+e.getMessage());
		}
		return bw;
	}


	/**
	 * 逐行写
	 * 2019-06-10 17:57:33
	 * @param bw
	 * @param lines
	 */
	private void writeLines(BufferedWriter bw, String lines) {
		// TODO Auto-generated method stub
		try {
			if(!StringUtils.isEmpty(lines)){
				bw.write(lines);
			}
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.writeLines] ："+e.getMessage());
		}
	}

	/**
	 * 将表转换hibernate实体类
	 * 2019-06-06 15:17:32
	 * @param isSeria   是否序列化
	 * @param allParams	是否创建全参的构造方法
	 * @param toString	是否需要重写toString
	 * @param tableNames 需要创建的表列表
	 */
	public void convertToHibernate(boolean isSeria,boolean toString,boolean allParams,String tableNames) {
		String disDir= "user/entity/hb";
		
		
		File dirFile = new File(disDir);
		if(!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		if(StringUtils.isEmpty(tableNames) || tableNames.trim().equals("*")) {
			List<Object> tables = CommonSQLOperationUtils.getAllTblTablesALLObj();
			for (Object object : tables) {
				InformationSchemaTables tablesInf = (InformationSchemaTables)object;
				if(convertOneTableToHibernate(disDir,isSeria,toString,allParams,tablesInf.getTableName())) {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ：表"+tablesInf.getTableName()+"实体类创建成功！");
				}else {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ：表"+tablesInf.getTableName()+"实体类创建失败！");
				}
			}
			return;
		}
		
		String tableNms[] = tableNames.trim().split(",");
		
		for (String tableName : tableNms) {
			if(convertOneTableToHibernate(disDir,isSeria,toString,allParams,tableName)) {
				
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ：表"+tableName+"实体类创建成功！");
			}else {
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ：表"+tableName+"实体类创建失败！");
			}
		}
	}

	/**
	 * 
	 * 将表转换hibernate实体类
	 * 2019-06-14 15:38:17
	 * @param isSeria   是否序列化
	 * @param allParams	是否创建全参的构造方法
	 * @param toString	是否需要重写toString
	 * @param tableNames 需要创建的表
	 * @return
	 */
	private boolean convertOneTableToHibernate(String disDir, boolean isSeria, boolean toString, boolean allParams,
			String tableName) {
		// TODO Auto-generated method stub
		//查询表对应的字段信息
		List<Object> columnsInfs = CommonSQLOperationUtils.getTblColumnsALLObj(tableName);
		
		if(columnsInfs!=null &&columnsInfs.size()==0) {
			return false;
		}
		
		//实体名、文件名
		String entityName = StringUtils.changeName(tableName, true);
		
		
		
		//初始化文件内容
		BufferedWriter bw = fileContentInit(disDir,entityName,false);
		
		//以键-字段名，值-字段类型存储主键字段信息
		Map<String,String> keysMap = new HashMap<String, String>();
		
		
		String schema = null;
		
		/**
		 * 获取需要导入的包、及主键
		 */
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			if(schema==null) {
				schema = column.getTableSchema();
			}
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			if(!StringUtils.isEmpty(column.getColumnKey())
					&&"pri".equals(column.getColumnKey().trim().toLowerCase())) {
				keysMap.put(columnName, columnType);
			}
		}
		
		/**
		 * 导包
		 */
		writeLines(bw,null);
		if(isSeria) {
			writeLines(bw,"import java.io.Serializable;");
		}
		for(Entry<String, Class<?>> entry : typeMap.entrySet()) {
			writeLines(bw,"import "+entry.getValue().getName()+";");
		}
		writeLines(bw,null);
		
		/**
		 * 导入实体类persistence包
		 */
		writeLines(bw,"import javax.persistence.Entity;");
		writeLines(bw,"import javax.persistence.Table;");
		writeLines(bw,"import javax.persistence.Column;");
		if(keysMap.size()>0) {
			writeLines(bw,"import javax.persistence.Id;");
		}
		if(typeMap.containsKey(CommonConstants._DATA_TYPE_DATE)) {
			writeLines(bw,"import javax.persistence.Temporal;");
			writeLines(bw,"import javax.persistence.TemporalType;");
		}
		
		/**
		 * 类注解
		 */
		writeLines(bw,"/**");
		writeLines(bw," * Hibernate实体类");
		writeLines(bw," * "+TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24));
		writeLines(bw," */");
		
		writeLines(bw,"@Entity");
		writeLines(bw,"@Table(name = \""+tableName.toUpperCase()+"\", schema = \""+schema+"\")");
		
		/**
		 * 是否序列化 
		 */
		if(isSeria) {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" implements Serializable {");
		}else {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" {");
		}
		
		/**
		 * 属性及构造方法
		 */
		List<String> constructParams = new ArrayList<String>();
		
		//每行参数
		String lineParams = "";
		//每行参数个数
		int lineParamsCount = 0;
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			String columnComment = column.getColumnComment();
			
			writeLines(bw, null);
			if(keysMap.containsKey(columnName)) {
				writeLines(bw, "\t//...主键... ");
			}
			String lines = "\t"+CommonConstants.ACCESS_PRIVATE
					+" "+columnType
					+" "+columnName+" = null;";
			if(!StringUtils.isEmpty(columnComment)) {
				lines+=" //"+columnComment;
			}
			writeLines(bw, lines);
			
			if(allParams) {
				/**
				 * 全参的构造方法每3个换行
				 */
				if(lineParamsCount>=3) {
					constructParams.add(lineParams);
					lineParams = "";
					lineParamsCount = 0;
				}
				lineParams += columnType+" "+columnName+", ";
				lineParamsCount++;
			}
		}
		constructParams.add(lineParams);
		
		/**
		 * 需要全参的构造方法
		 */
		if(allParams) {
			
			//先创建无参的构造方法
			writeLines(bw, null);
			writeLines(bw, "\tpublic "+entityName+"() {");
			writeLines(bw, null);
			writeLines(bw, "\t}");
			
			writeLines(bw, null);
			
			/**
			 * 创建全参的构造方法
			 */
			int blankLength = 0;
			for (int i=0;i< constructParams.size();i++) {
				if(i==0) {
					blankLength = ("\tpublic "+entityName+"(").length();
					writeLines(bw, "\tpublic "+entityName+"("+constructParams.get(i));
				}else if(constructParams.size()-1==i){
					writeLines(bw, getBlank(blankLength)
							+constructParams.get(i).substring(0, constructParams.get(i).length()-2)+") {");
				}else {
					writeLines(bw, getBlank(blankLength)+constructParams.get(i));
				}
			}
			
			
			for (Object object : columnsInfs) {
				
				InformationSchemaColumns column =(InformationSchemaColumns) object;
				String columnName = StringUtils.changeName(column.getColumnName(), false);;
				writeLines(bw,"\t\tthis."+columnName+" = "+columnName+";");
			}
			
			writeLines(bw,"\t}");
			
			
		}
		
	
		/**
		 * get/set
		 */
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), true);
			String columnType = getColumnType(column.getColumnType());
			String columnNameF = StringUtils.changeName(column.getColumnName(), false);
			//是否可为空
			String nullable = "YES".equals(column.getIsNullable().trim())?"true":"false";
			
			//get方法
			writeLines(bw, null);
			if(keysMap.containsKey(columnNameF)) {
				writeLines(bw, "\t@Id");
				writeLines(bw, "\t@Column(name = \""+column.getColumnName().toUpperCase()+"\", unique = true, nullable = "+nullable+")");
			}else {
				Long columnLength = column.getCharacterMaximumLength();
				if(columnLength==null) {
					if(column.getColumnType().indexOf("int")>=0) {
						columnLength = 11l;
					}else if(column.getColumnType().indexOf("date")>=0//时间类型
							||column.getColumnType().indexOf("time")>=0
							||column.getColumnType().indexOf("year")>=0
							||column.getColumnType().indexOf("datetime")>=0
							||column.getColumnType().indexOf("timestamp")>=0) {
						columnLength = 26l;
						writeLines(bw, "\t@Temporal(TemporalType = TIMESTAMP)");
					}
				}
				writeLines(bw, "\t@Column(name = \""+column.getColumnName().toUpperCase()+"\", nullable =  "+nullable+", length = "+columnLength.toString()+")");
			}
			writeLines(bw,"\tpublic "+columnType+" get"+columnName+"() {");
			writeLines(bw,"\t\treturn this."+columnNameF+";");
			writeLines(bw,"\t}");
			
			
			//set方法
			writeLines(bw, null);
			writeLines(bw,"\tpublic void set"+columnName
					+"("+columnType +" "+columnNameF+") {");
			writeLines(bw,"\t\t this."+columnNameF+" = "+columnNameF+";");
			writeLines(bw,"\t}");
		}
		
		if(toString) {
			/**
			 * toString方法
			 */
			writeLines(bw, null);
			writeLines(bw, "\t@Override");
			writeLines(bw, "\tpublic String toString() {");
			int blankLength = 0;
			for (int i=0;i<columnsInfs.size();i++) {
				
				InformationSchemaColumns column =(InformationSchemaColumns) columnsInfs.get(i);
				String columnName = StringUtils.changeName(column.getColumnName(), false);
				if(i==0) {
					blankLength = ("\t\treturn\""+entityName+"[").length();
					writeLines(bw,"\t\treturn\""+entityName+"["+columnName+" = \"+"+columnName+"+\", \"");
				}
				else if(i<columnsInfs.size()-1) {
					writeLines(bw,getBlank(blankLength)+"+ \""+columnName+" = \"+"+columnName+"+\", \"");
				}else {
					writeLines(bw,getBlank(blankLength)+"+ \""+columnName+" = \"+"+columnName+"+\"];\"");
				}
			}
			writeLines(bw,"\t}");
			
		}
		
		/**
		 * 文件最后的关闭大括号
		 */
		writeLines(bw,"}");
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.convertOneTableToPOJO]	："+e.getMessage());
		}
		
		return true;
	}
	
}
