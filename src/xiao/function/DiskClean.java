package xiao.function;

import java.io.File;

import xiao.ApplicationInitial;
import xiao.FileUtils;

/**
 * 磁盘清理
 * @author xjl
 * 2019-06-19 11:45:55
 */
public class DiskClean {

	/**
	 * 磁盘清理
	 * 2019-06-21 14:17:12
	 * @param opers
	 */
	public boolean clean(String[] opers) {
		// TODO Auto-generated method stub
		
		String oper = opers[0];
		String file = opers[1];
		
		ApplicationInitial.writeInfo("[DiskClean.clean] : 清理"+file+"开始...");
		
		String timeP = System.currentTimeMillis()+"";
		String backup =  "user/disk/"+new File(file).getName()+"_"+timeP;
		//备份
		if(!FileUtils.copyFileTo(file,backup)) {
			return false;
		}
		
		RestoreBackFile rbf = new RestoreBackFile();
		//设置还原点
		if(!rbf.setRestorePoint(timeP,"4", file, backup, "功能4.系统磁盘清理")) {
			rbf.destoryRestoreBackFile();
			return false;
		}
		
		if(oper.equals("c")) {
			FileUtils.delete(file);
		}else {
			FileUtils.deleteInBlankDir(file);
		}
		ApplicationInitial.writeInfo("[DiskClean.clean] : 清理"+file+"结束...");
		
		return true;
	}
	
	/**
	 * 清理本程序过期的备份文件、生成文件
	 * 2019-06-21 15:16:28
	 */
	public void cleanAppBackup() {
		
	}

}
