package xiao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;



/**
 * 加载配置文件工具类
 * @author xjl
 * 2018-08-25 11:31:14
 */
public class LoadPropertiesUtils {
	
	public  static Properties getConfigProperties(String filename) throws IOException {
		Properties properties = new Properties();
		
		FileInputStream inputStream = null;
		
		/**
		 * 读取外部配置文件
		 */
		try {
			File file = new File(filename);
			inputStream = new FileInputStream(file);
			properties.load(new InputStreamReader(inputStream,"UTF-8"));
		}catch(Exception e) {
			System.err.println("[LoadPropertiesUtils.getConfigProperties]  :"+e.getMessage());
		}
		
		
		
		return properties;
	}
	
}
