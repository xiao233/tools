package xiao;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * 将不同数据格式转成相应对象
 * @author xjl
 * 2018-12-25 13:44:07
 */
public class ConvertToObjectUtils {
	

	/**
	 * 将从数据库查询的数据转成相应对象
	 * 2018-12-25 13:44:53
	 * @param queryList 数据库查询内容
	 * @param tableClass 对象类名
	 * @return
	 */
	public static  List<Object> mapToObject(List<Map<String, Object>> queryList, Class<?> tableClass) {
		List<Object> result = new ArrayList<Object>();
		
		Field []fields = tableClass.getDeclaredFields();
		
		
		for (Map<String, Object> map : queryList) {
			try {
				Object temp = tableClass.newInstance();
				Set<Entry<String, Object>> entrys = map.entrySet();
				for (Entry<String, Object> entry : entrys) {
					String fieldName = StringUtils.changeName(entry.getKey(),false);
					for (Field field : fields) {
						if(field.getName().equals(fieldName)) {
							field.setAccessible(true);
							String type = field.getGenericType().getTypeName();
							String value =  "";
							if(entry.getValue() instanceof Timestamp) {
								Timestamp time = (Timestamp) entry.getValue();
								value = time.toString().substring(0, time.toString().indexOf("."));
							}else {
								value += entry.getValue();
							}
							if(!StringUtils.isEmpty(value)&&!value.trim().equals("null")) {
								if(type.indexOf("String")>=0) {
									field.set(temp, value);
								}else if(type.indexOf("Integer")>=0) {
									field.set(temp, Integer.parseInt(value));
								}else if(type.indexOf("Long")>=0) {
									field.set(temp, Long.parseLong(value));
								}else if(type.indexOf("Float")>=0) {
									field.set(temp, Float.parseFloat(value));
								}else if(type.indexOf("Double")>=0) {
									field.set(temp, Double.parseDouble(value));
								}else if(type.indexOf("Timestamp")>=0) {
									field.set(temp,Timestamp.valueOf(value));
								}else if(type.indexOf("java.util.Date")>=0) {
									field.set(temp,TimeUtils.getTimeByFormat(value, TimeUtils.FORMAT_DATE_TIME24));
								}
								
							}else if(value.trim().equals("null")){
								field.set(temp, null);
							}else {
								field.set(temp, value);
							}
							break;
						}
					}
				}
				result.add(temp);
			} catch (InstantiationException | IllegalAccessException e) {
				ApplicationInitial.writeErrorInfo("[ConvertToObjectUtils.mapToObject] ："+e.getMessage());
			}
		}
		return result;
	}

}
