package xiao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import xiao.model.InformationSchemaColumns;
import xiao.model.InformationSchemaTables;





/**
 * ͨ��sql����
 * @author xjl
 * 2018-12-25 09:15:22
 */
public class CommonSQLOperationUtils {
	
	
	/**
	 * ͨ�ò�ѯ����
	 * 2018-12-25 11:17:46
	 * @param tableName ����
	 * @param queryFields ��ѯ�ֶ�
	 * @param params ��ѯ����
	 * @param tableClass ��Ӧʵ�����
	 * @return
	 */
	public static  List<Object> query(String tableName,List<String> queryFields,
			Map<String,Object> params,Class<?> tableClass){
		
		// TODO Auto-generated method stub
		
		List<Map<String,Object>> queryList = queryMap(tableName, queryFields, params);
		List<Object> result = null;
		//����ѯ���ת������Ӧʵ����
		result = ConvertToObjectUtils.mapToObject(queryList,tableClass);
		
		return result;
	}

	/**
	 * ͨ�ò�ѯ������map����
	 * 2018-12-29 11:26:45
	 * @param tableName ����
	 * @param queryFields ��ѯ�ֶ�
	 * @param params ��ѯ����
	 * @return
	 */
	public static List<Map<String,Object>> queryMap(String tableName,List<String> queryFields,
			Map<String,Object> params){
		
		// TODO Auto-generated method stub
		
		List<Map<String,Object>> queryList = new ArrayList<Map<String,Object>>();
		Connection con = ConnectionManager.getConnection();
		
		StringBuilder sql = new StringBuilder("");
		
		sql.append("SELECT ");
		
		//��ȡ��ѯ�ֶ�
		for (int i = 0; i < queryFields.size(); i++) {
			sql.append(queryFields.get(i));
			if(i!=queryFields.size()-1) {
				sql.append(", ");
			}
		}
		sql.append(" FROM ");
		sql.append(tableName);
		sql.append(" WHERE 1=1 ");
		
		//��ȡ��ѯ������ռλ��
		List<Object> paramList = new ArrayList<Object>();
		Set<Entry<String, Object>> condition = params.entrySet();
		for (Entry<String, Object> entry : condition) {
			sql.append(" AND "+entry.getKey()+" = ?");
			paramList.add(entry.getValue());
		}
		
		ApplicationInitial.writeInfo("[CommonSQLOperationUtils.queryMap]  ��ѯ����: "+sql);
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql.toString());
			
			//ռλ
			for (int i = 0; i < paramList.size(); i++) {
				pst.setObject(i+1, paramList.get(i));
			}
			
			//ִ��
			rs = pst.executeQuery();
			
			//������ѯ���
			while(rs.next()) {
				Map<String,Object> obj = new HashMap<String,Object>();
				for (String field : queryFields) {
					obj.put(field, rs.getObject(field));
				}
				queryList.add(obj);
			}
			
		} catch (SQLException e) {
			ApplicationInitial.writeErrorInfo("[CommonSQLOperationUtils.queryMap]  ��ѯ�쳣: "+e.getMessage());
		} finally {
			ConnectionManager.closeConnection(con, pst, rs);
		}
		return queryList;
	}
	
	
	/**
	 * ����sql����ѯ��sql�����ƴ�ӵ���ʽ,�����û�����ʹ�ã�ֻ��ѯ����������õ�sql
	 * 2019-06-04 16:56:28
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> queryMap(String sql){
		
		// TODO Auto-generated method stub
		
		List<Map<String,Object>> queryList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(sql)) {
			ApplicationInitial.writeErrorInfo("[CommonSQLOperationUtils.queryMap]  ��ѯ��sql��䲻��Ϊ�գ�");
			return queryList;
		}
		
		
		ApplicationInitial.writeInfo("[CommonSQLOperationUtils.queryMap]  ��ѯsql: "+sql);
		
		List<String> queryFields = getQueryFields(sql);
		
		String tableName = getQueryTableName(sql);
		
		Connection con = ConnectionManager.getConnection();
		
		
		
		ResultSet rs = null;
		Statement st = null;
		
		try {
			st =  con.createStatement();
			rs = st.executeQuery(sql);
			
			
			while(rs.next()) {
				if(queryFields.size()!=0) {
					Map<String,Object> obj = new HashMap<String,Object>();
					
					//�����ѯ�ֶ�Ϊ*��������
					if(queryFields.size()==1 && "*".equals(queryFields.get(0))) {
						List<String> columns = getTblColumns(tableName);
						for (String column : columns) {
							obj.put(column, rs.getObject(column));
						}
					}else {//ָ���˲�ѯ�ֶ�
						for (String field : queryFields) {
							obj.put(field, rs.getObject(field));
						}
					}
					
					queryList.add(obj);
				}
				
			}
		} catch (SQLException e) {
			ApplicationInitial.writeErrorInfo("[CommonSQLOperationUtils.queryMap]  ��ѯ�쳣: "+e.getMessage());
		} finally {
			ConnectionManager.closeConnection(con, st, rs);
		}
		
		return queryList;
	}

	/**
	 * ���ݱ�����ȡ����ֶ���
	 * 2019-06-05 10:58:55
	 * @param tableName ����
	 * @return
	 */
	public static List<String> getTblColumns( String tableName) {
		// TODO Auto-generated method stub
		
		
		List<String> queryFields = new ArrayList<String>();
		queryFields.add("column_name");
		

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("table_name", tableName);
		List<Map<String,Object>> rsList = getTblConfigs("COLUMNS",params,queryFields);
		
		List<String> columnsName = new ArrayList<String>();
		for (Map<String, Object> map : rsList) {
			columnsName.add((String)map.get("column_name"));
		}
		
		return columnsName;
	}
	
	/**
	 * ���ݱ�����ȡ��ǰ���ݿ�ı���ֶε�������Ϣ
	 * 2019-06-05 14:07:44
	 * @param table
	 * @return List<Object>
	 */
	public static List<Object> getTblColumnsALLObj(String tableName){
		List<Map<String,Object>> queryList =  getTblColumnsALL(tableName);
		
		List<Object> result = null;
		//����ѯ���ת������Ӧʵ����
		result = ConvertToObjectUtils.mapToObject(queryList,InformationSchemaColumns.class);
		return result;
	}
	/**
	 * ���ݱ�����ȡ��ǰ���ݿ�ı���ֶε�������Ϣ
	 * 2019-06-05 14:07:44
	 * @param table
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> getTblColumnsALL(String tableName){
		
		// TODO Auto-generated method stub
		List<String> queryFields = new ArrayList<String>();
	
		
		queryFields.add("table_catalog");
		queryFields.add("table_schema");
		queryFields.add("table_name");
		queryFields.add("column_name");
		queryFields.add("ordinal_position");
		queryFields.add("column_default");
		queryFields.add("is_nullable");
		queryFields.add("data_type");
		queryFields.add("character_maximum_length");
		queryFields.add("character_octet_length");
		queryFields.add("numeric_precision");
		queryFields.add("numeric_scale");
		queryFields.add("datetime_precision");
		queryFields.add("character_set_name");
		queryFields.add("collation_name");
		queryFields.add("column_type");
		queryFields.add("column_key");
		queryFields.add("extra");
		queryFields.add("privileges");
		queryFields.add("column_comment");
		queryFields.add("generation_expression");
		queryFields.add("srs_id");

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("table_name", tableName);
		//���ò�ѯ�����ݿ�
		params.put("table_schema", ConnectionManager.getCurrentDatabase());
		
		List<Map<String,Object>> rsList = getTblConfigs("COLUMNS",params,queryFields);
		
		return rsList;
	}
	
	/**
	 * ���ݱ�����ȡ��ǰ���ݿ�ı�����ñ���Ϣ
	 * 2019-06-05 14:07:44
	 * @param table
	 * @return List<Object>
	 */
	public static List<Object> getTblTablesALLObj(String tableName){
		List<Map<String,Object>> queryList =  getTblTablesALL(tableName);
		
		List<Object> result = null;
		//����ѯ���ת������Ӧʵ����
		result = ConvertToObjectUtils.mapToObject(queryList,InformationSchemaTables.class);
		return result;
	}
	
	/**
	 * ��ȡ��ǰ�⵱ǰ���ݿ�����б�����ñ���Ϣ
	 * 2019-06-06 11:31:53
	 * @return
	 */
	public static List<Object> getAllTblTablesALLObj(){
		List<Map<String,Object>> queryList =  getTblTablesALL(null);
		
		List<Object> result = null;
		//����ѯ���ת������Ӧʵ����
		result = ConvertToObjectUtils.mapToObject(queryList,InformationSchemaTables.class);
		return result;
	}
	/**
	 * ���ݱ�����ȡ��ǰ���ݿ�ı�����ñ���Ϣ ��Ϊ�����ѯ��ǰ�����б�����ñ���Ϣ
	 * 2019-06-05 14:07:44
	 * @param tableName ����
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> getTblTablesALL(String tableName){
		
		// TODO Auto-generated method stub
		List<String> queryFields = new ArrayList<String>();
	
		
		queryFields.add("table_catalog");
		queryFields.add("table_schema");
		queryFields.add("table_name");
		queryFields.add("table_type");
		queryFields.add("engine");
		queryFields.add("version");
		queryFields.add("row_format");
		queryFields.add("table_rows");
		queryFields.add("avg_row_length");
		queryFields.add("data_length");
		queryFields.add("max_data_length");
		queryFields.add("index_length");
		queryFields.add("data_free");
		queryFields.add("create_time");
		queryFields.add("update_time");
		queryFields.add("check_time");
		queryFields.add("table_collation");
		queryFields.add("checksum");
		queryFields.add("create_options");
		queryFields.add("table_comment");

		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(tableName)) {
			params.put("table_name", tableName);
		}
		//���ò�ѯ�����ݿ�
		params.put("table_schema", ConnectionManager.getCurrentDatabase());
		
		List<Map<String,Object>> rsList = getTblConfigs("TABLES",params,queryFields);
		
		return rsList;
	}
	
	/**
	 * ���ݱ�����ȡ������ñ��ֶ���Ϣ��
	 * 2019-06-05 11:39:01
	 * @param table	���ñ���Ϣ ��information_schema.COLUMNS
	 * @param params ���������table_nameΪ���ı���
	 * @param queryFields ��ѯ���ֶ�
	 * @return
	 */
	public static List<Map<String,Object>> getTblConfigs(String table,Map<String,Object> params,List<String> queryFields){
		// TODO Auto-generated method stub
		
		
		List<Map<String,Object>> queryList = queryMap("information_schema."+table, queryFields, params);
	
		return queryList;
	}
	
	

	/**
	 * ����sql��ȡ��ѯ����
	 * 2019-06-05 09:27:01
	 * @param sql
	 * @return
	 */
	private static String getQueryTableName(String sql) {
		// TODO Auto-generated method stub
		
		String sqlCon = null;
		sqlCon = sql.substring(sql.indexOf("from")+4);
		if(StringUtils.isEmpty(sqlCon)) {
			sqlCon = sql.substring(0,sql.indexOf("FROM")+4);
		}
		
		String inf[] = sqlCon.split(" ");
		if(inf!=null && inf.length>0) {
			for (String string : inf) {
				if(!StringUtils.isEmpty(string)) {
					return string.toUpperCase();
				}
			}
		}
		return null;
	}

	/**
	 * ����sql����ȡ��ѯ���ֶ�
	 * 2019-06-04 17:16:01
	 * @param sql
	 * @return
	 */
	private static List<String> getQueryFields(String sql) {
		// TODO Auto-generated method stub
		
		List<String> queryFields = new ArrayList<String>();
		String sqlCon = null;
		sqlCon = sql.substring(0,sql.indexOf("from"));
		if(StringUtils.isEmpty(sqlCon)) {
			sqlCon = sql.substring(0,sql.indexOf("FROM"));
		}
		
		sqlCon = sqlCon.toUpperCase();
		sqlCon = sqlCon.substring(sqlCon.indexOf("SELECT")+6);
		
		String sqlCons[] = sqlCon.split(",");
		if(sqlCons!=null && sqlCons.length>0) {
			for (String string : sqlCons) {
				queryFields.add(string.trim());
			}
		}
		
		return queryFields;
	}
}
