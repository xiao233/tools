package xiao.main;

import org.junit.Before;
import org.junit.Test;

import xiao.ApplicationInitial;
import xiao.FileUtils;

/**
 * π§æﬂ¿‡≤‚ ‘
 * @author xjl
 * 2019-06-20 10:03:05
 */
public class junitUtilsTest {
	
	@Before
	public void init() {
		ApplicationInitial.init();
	}
	
	@Test
	public void testFileUtils() {
		String src = "C:\\Users\\Administrator\\Desktop\\java_1.0";
		String dis = "C:\\Users\\Administrator\\Desktop\\IP";
		//src = "C:\\Users\\Administrator\\Desktop\\IP\\error.log";
		
		//FileUtils.copyFileTo(src, dis);
		
		//FileUtils.copyFileToFileBinary(src, dis);
		
		dis = "C:\\Users\\Administrator\\Desktop\\IPChange1.exe";
		//FileUtils.copyFileToFileStr(src, dis);
		
		dis = "C:\\Users\\Administrator\\Desktop\\IP";
		//FileUtils.delete(dis);
		
		FileUtils.deleteInBlankDir(dis);
	}
}
