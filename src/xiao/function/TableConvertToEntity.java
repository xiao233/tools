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
 *  ����1.��ת��ʵ����
 * @author xjl
 * 2019-06-06 15:14:17
 */
public class TableConvertToEntity {

	/**
	 * ���java����
	 */
	private Map<String,Class<?>> typeMap = new TreeMap<String,Class<?>>();
	/**
	 * ����ת��POJOʵ����
	 * 2019-06-06 15:17:32
	 * @param isSeria   �Ƿ����л�
	 * @param allParams	�Ƿ񴴽�ȫ�εĹ��췽��
	 * @param toString	�Ƿ���Ҫ��дtoString
	 * @param tableNames ��Ҫ�����ı��б���","����,��"*"�����ʾ��ǰ������
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
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ����"+tablesInf.getTableName()+"ʵ���ഴ���ɹ���");
				}else {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ����"+tablesInf.getTableName()+"ʵ���ഴ��ʧ�ܣ�");
				}
			}
			return;
		}
		
		String tableNms[] = tableNames.trim().split(",");
		
		for (String tableName : tableNms) {
			if(convertOneTableToPOJO(disDir,isSeria,toString,allParams,tableName)) {
				
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ����"+tableName+"ʵ���ഴ���ɹ���");
			}else {
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToPOJO] ����"+tableName+"ʵ���ഴ��ʧ�ܣ�");
			}
		}
	}
	
	/**
	 * ����ת��POJOʵ����
	 * 2019-06-06 15:17:32
	 * @param disDir	ת������Ŀ���ļ���
	 * @param isSeria   �Ƿ����л�
	 * @param allParams	�Ƿ񴴽�ȫ�εĹ��췽��
	 * @param toString	�Ƿ���Ҫ��дtoString
	 * @param tableNames ��Ҫ�����ı�
	 */
	public boolean convertOneTableToPOJO(String disDir,boolean isSeria, boolean toString, boolean allParams, String tableName) {
		// TODO Auto-generated method stub
		
		
		//��ѯ���Ӧ���ֶ���Ϣ
		List<Object> columnsInfs = CommonSQLOperationUtils.getTblColumnsALLObj(tableName);
		
		if(columnsInfs!=null &&columnsInfs.size()==0) {
			return false;
		}
		
		//ʵ�������ļ���
		String entityName = StringUtils.changeName(tableName, true);
		
		
		
		//��ʼ���ļ�����
		BufferedWriter bw = fileContentInit(disDir,entityName,true);
		
		//�Լ�-�ֶ�����ֵ-�ֶ����ʹ洢�����ֶ���Ϣ
		Map<String,String> keysMap = new HashMap<String, String>();
		
		/**
		 * ��ȡ��Ҫ����İ���������
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
		 * ����
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
		 * ��ע��
		 */
		writeLines(bw,"/**");
		writeLines(bw," * POJOʵ����");
		writeLines(bw," * "+TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24));
		writeLines(bw," */");
		
		/**
		 * �Ƿ����л� 
		 */
		if(isSeria) {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" implements Serializable {");
		}else {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" {");
		}
		
		/**
		 * ���Լ����췽��
		 */
		List<String> constructParams = new ArrayList<String>();
		
		//ÿ�в���
		String lineParams = "";
		//ÿ�в�������
		int lineParamsCount = 0;
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			String columnComment = column.getColumnComment();
			
			writeLines(bw, null);
			if(keysMap.containsKey(columnName)) {
				writeLines(bw, "\t//...����... ");
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
				 * ȫ�εĹ��췽��ÿ3������
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
		 * ��Ҫȫ�εĹ��췽��
		 */
		if(allParams) {
			
			//�ȴ����޲εĹ��췽��
			writeLines(bw, null);
			writeLines(bw, "\tpublic "+entityName+"() {");
			writeLines(bw, null);
			writeLines(bw, "\t}");
			
			writeLines(bw, null);
			
			/**
			 * ����ȫ�εĹ��췽��
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
			
			//get����
			writeLines(bw, null);
			writeLines(bw,"\tpublic "+columnType+" get"+columnName+"() {");
			writeLines(bw,"\t\treturn this."+columnNameF+";");
			writeLines(bw,"\t}");
			
			
			//set����
			writeLines(bw, null);
			writeLines(bw,"\tpublic void set"+columnName
					+"("+columnType +" "+columnNameF+") {");
			writeLines(bw,"\t\t this."+columnNameF+" = "+columnNameF+";");
			writeLines(bw,"\t}");
		}
		
		if(toString) {
			/**
			 * toString����
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
		 * �ļ����Ĺرմ�����
		 */
		writeLines(bw,"}");
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.convertOneTableToPOJO]	��"+e.getMessage());
		}
		
		return true;
	}

	/**
	 * ��ȡָ�����ȵĿո�
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
	 * ��ȡ�ֶ�����
	 * 2019-06-11 09:50:28
	 * @param columnType
	 * @return
	 */
	private String getColumnType(String columnType) {
		// TODO Auto-generated method stub
		columnType = columnType.substring(0, columnType.length()-1).toLowerCase().replace('(', '_');
		String types[] = columnType.split("_");
		
		//���ݿ��ֶζ��峤��
		int columnLength = types.length>=2?Integer.parseInt(types[1]):0;
		if(types[0].indexOf("int")>=0) {//����
			
			if(columnLength>9) {
				//typeMap.put(CommonConstants._DATA_TYPE_LONG, Long.class);
				return CommonConstants._DATA_TYPE_LONG;
			}else {
				//typeMap.put(CommonConstants._DATA_TYPE_INTEGER, Integer.class);
				return CommonConstants._DATA_TYPE_INTEGER;
			}
			
		}else if(types[0].indexOf("float")>=0) {//�����ȸ���
			
			//typeMap.put(CommonConstants._DATA_TYPE_FLOAT, Float.class);
			return CommonConstants._DATA_TYPE_FLOAT;
			
		}else if(types[0].indexOf("double")>=0
				||types[0].indexOf("decimal")>=0) {//˫���ȸ���
			
			//typeMap.put(CommonConstants._DATA_TYPE_DOUBLE, Double.class);
			return CommonConstants._DATA_TYPE_DOUBLE;
			
		}else if(types[0].indexOf("date")>=0//ʱ������
				||types[0].indexOf("time")>=0
				||types[0].indexOf("year")>=0
				||types[0].indexOf("datetime")>=0
				||types[0].indexOf("timestamp")>=0) {
			
			typeMap.put(CommonConstants._DATA_TYPE_DATE, Date.class);
			return CommonConstants._DATA_TYPE_DATE;
			
		}else {//Ĭ���ַ�����
			
			//typeMap.put(CommonConstants._DATA_TYPE_STRING, String.class);
			return CommonConstants._DATA_TYPE_STRING;
		}
	}

	/**
	 * ��ʼ���ļ�����
	 * 2019-06-10 17:50:37
	 * @param fileName
	 * @param entityName
	 * @param isPojo �Ƿ���pojoʵ���� 
	 * @return �����ļ����������
	 */
	private BufferedWriter fileContentInit(String dirName, String entityName, boolean isPojo) {
		// TODO Auto-generated method stub
		
		//������Ӧ��ʵ���ļ�
		File file = new File(dirName+File.separator+entityName+CommonConstants.FILE_TYPE_JAVA);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.fileContentInit] ��"+e.getMessage());
		}
		
		/**
		 * �����ļ������
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
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.fileContentInit] ��"+e.getMessage());
		}
		return bw;
	}


	/**
	 * ����д
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
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.writeLines] ��"+e.getMessage());
		}
	}

	/**
	 * ����ת��hibernateʵ����
	 * 2019-06-06 15:17:32
	 * @param isSeria   �Ƿ����л�
	 * @param allParams	�Ƿ񴴽�ȫ�εĹ��췽��
	 * @param toString	�Ƿ���Ҫ��дtoString
	 * @param tableNames ��Ҫ�����ı��б�
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
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ����"+tablesInf.getTableName()+"ʵ���ഴ���ɹ���");
				}else {
					ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ����"+tablesInf.getTableName()+"ʵ���ഴ��ʧ�ܣ�");
				}
			}
			return;
		}
		
		String tableNms[] = tableNames.trim().split(",");
		
		for (String tableName : tableNms) {
			if(convertOneTableToHibernate(disDir,isSeria,toString,allParams,tableName)) {
				
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ����"+tableName+"ʵ���ഴ���ɹ���");
			}else {
				ApplicationInitial.writeInfo("[TableConvertToEntity.convertToHibernate] ����"+tableName+"ʵ���ഴ��ʧ�ܣ�");
			}
		}
	}

	/**
	 * 
	 * ����ת��hibernateʵ����
	 * 2019-06-14 15:38:17
	 * @param isSeria   �Ƿ����л�
	 * @param allParams	�Ƿ񴴽�ȫ�εĹ��췽��
	 * @param toString	�Ƿ���Ҫ��дtoString
	 * @param tableNames ��Ҫ�����ı�
	 * @return
	 */
	private boolean convertOneTableToHibernate(String disDir, boolean isSeria, boolean toString, boolean allParams,
			String tableName) {
		// TODO Auto-generated method stub
		//��ѯ���Ӧ���ֶ���Ϣ
		List<Object> columnsInfs = CommonSQLOperationUtils.getTblColumnsALLObj(tableName);
		
		if(columnsInfs!=null &&columnsInfs.size()==0) {
			return false;
		}
		
		//ʵ�������ļ���
		String entityName = StringUtils.changeName(tableName, true);
		
		
		
		//��ʼ���ļ�����
		BufferedWriter bw = fileContentInit(disDir,entityName,false);
		
		//�Լ�-�ֶ�����ֵ-�ֶ����ʹ洢�����ֶ���Ϣ
		Map<String,String> keysMap = new HashMap<String, String>();
		
		
		String schema = null;
		
		/**
		 * ��ȡ��Ҫ����İ���������
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
		 * ����
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
		 * ����ʵ����persistence��
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
		 * ��ע��
		 */
		writeLines(bw,"/**");
		writeLines(bw," * Hibernateʵ����");
		writeLines(bw," * "+TimeUtils.getCurrentTimeByFormate(TimeUtils.FORMAT_DATE_TIME24));
		writeLines(bw," */");
		
		writeLines(bw,"@Entity");
		writeLines(bw,"@Table(name = \""+tableName.toUpperCase()+"\", schema = \""+schema+"\")");
		
		/**
		 * �Ƿ����л� 
		 */
		if(isSeria) {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" implements Serializable {");
		}else {
			writeLines(bw,CommonConstants.ACCESS_PUBLIC+" class "+entityName +" {");
		}
		
		/**
		 * ���Լ����췽��
		 */
		List<String> constructParams = new ArrayList<String>();
		
		//ÿ�в���
		String lineParams = "";
		//ÿ�в�������
		int lineParamsCount = 0;
		for (Object object : columnsInfs) {
			
			InformationSchemaColumns column =(InformationSchemaColumns) object;
			String columnName = StringUtils.changeName(column.getColumnName(), false);;
			String columnType = getColumnType(column.getColumnType());
			String columnComment = column.getColumnComment();
			
			writeLines(bw, null);
			if(keysMap.containsKey(columnName)) {
				writeLines(bw, "\t//...����... ");
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
				 * ȫ�εĹ��췽��ÿ3������
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
		 * ��Ҫȫ�εĹ��췽��
		 */
		if(allParams) {
			
			//�ȴ����޲εĹ��췽��
			writeLines(bw, null);
			writeLines(bw, "\tpublic "+entityName+"() {");
			writeLines(bw, null);
			writeLines(bw, "\t}");
			
			writeLines(bw, null);
			
			/**
			 * ����ȫ�εĹ��췽��
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
			//�Ƿ��Ϊ��
			String nullable = "YES".equals(column.getIsNullable().trim())?"true":"false";
			
			//get����
			writeLines(bw, null);
			if(keysMap.containsKey(columnNameF)) {
				writeLines(bw, "\t@Id");
				writeLines(bw, "\t@Column(name = \""+column.getColumnName().toUpperCase()+"\", unique = true, nullable = "+nullable+")");
			}else {
				Long columnLength = column.getCharacterMaximumLength();
				if(columnLength==null) {
					if(column.getColumnType().indexOf("int")>=0) {
						columnLength = 11l;
					}else if(column.getColumnType().indexOf("date")>=0//ʱ������
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
			
			
			//set����
			writeLines(bw, null);
			writeLines(bw,"\tpublic void set"+columnName
					+"("+columnType +" "+columnNameF+") {");
			writeLines(bw,"\t\t this."+columnNameF+" = "+columnNameF+";");
			writeLines(bw,"\t}");
		}
		
		if(toString) {
			/**
			 * toString����
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
		 * �ļ����Ĺرմ�����
		 */
		writeLines(bw,"}");
		
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ApplicationInitial.writeErrorInfo("[TableConvertToEntity.convertOneTableToPOJO]	��"+e.getMessage());
		}
		
		return true;
	}
	
}
