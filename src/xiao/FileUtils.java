package xiao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 文件操作工具类
 * @author xjl
 * 2019-06-19 17:45:55
 */
public class FileUtils {

	/**
	 * 复制文件或文件夹内的内容到指定文件夹
	 * 2019-06-19 17:47:34
	 * @param src 源文件名
	 * @param dis 目标文件名
	 */
	public static boolean copyFileTo(String src, String dis) {
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ： 源文件名不能为空!");
			return false;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ： 目标文件名不能为空!");
			return false;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ：源文件"+src+"不存在!");
			return false;
		}
		
		File disFile = new File(dis);
		
		try {
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileTo] : 文件"+src+"复制到文件夹"+dis+"开始...");
			copyFileTo(srcFile,disFile);
		} catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ："+e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * 复制文件或文件夹内的内容到指定文件夹
	 * 2019-06-19 17:47:34
	 * @param src 源文件
	 * @param dis 目标文件
	 */
	public static void copyFileTo(File src, File dis) {
		// TODO Auto-generated method stub
		
		File files[] = src.listFiles();
		if(files!=null&& files.length!=0) {
			for(File file : files) {
				if(file.isFile()) {
					copyFileToFileBinary(file,new File(dis.getAbsolutePath()+File.separator+file.getName()));
				}else {
					File temp = new File(dis.getAbsolutePath()+File.separator+file.getName());
					temp.mkdirs();
					copyFileTo(file,temp);
				}
			}
		}else {
			if(src.isFile()) {
				dis.mkdirs();
				copyFileToFileBinary(src,new File(dis.getAbsolutePath()+File.separator+src.getName()));
			}
		}
	}

	
	/**
	 * 将一个文件的内容复制到另一个文件,字节流
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileBinary(String src, String dis) {
		
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] ： 源文件名不能为空!");
			return ;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] ： 目标文件名不能为空!");
			return ;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] ：源文件"+src+"不存在!");
			return ;
		}
		
		File disFile = new File(dis);
		copyFileToFileBinary(srcFile,disFile);
	}
	
	/**
	 * 将一个文件的内容复制到另一个文件,字节流
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileBinary(File src, File dis) {
		// TODO Auto-generated method stub
		
		InputStream fis = null;
		OutputStream fos = null;
		ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileBinary] : 文件"
				+src.getAbsolutePath()+"复制到"+dis.getAbsolutePath()+"开始...");
		
		//定义字节缓冲区，加快文件读写速度
		byte []caches = new byte[1024];
		int len = 0;
		
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dis);
			
			while((len=fis.read(caches))>0) {
				fos.write(caches, 0, len);
				fos.flush();
			}
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileBinary] : 文件复制成功！");
		}catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] 文件复制失败: "+e.getMessage());
		}finally {
			try {
				if(fis!=null) {
					fis.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] 文件复制失败: "+e.getMessage());
			}
			
			try {
				if(fos!=null) {
					fos.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] 文件复制失败: "+e.getMessage());
			}
		}
	}
	
	/**
	 * 将一个文件的内容复制到另一个文件,字符流
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileStr(String src, String dis) {
		
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] ： 源文件名不能为空!");
			return ;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] ： 目标文件名不能为空!");
			return ;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] ：源文件"+src+"不存在!");
			return ;
		}
		
		File disFile = new File(dis);
		copyFileToFileBinary(srcFile,disFile);
	}
	
	/**
	 * 将一个文件的内容复制到另一个文件,字父流
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileStr(File src, File dis) {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileStr] : 文件"
				+src.getAbsolutePath()+"复制到"+dis.getAbsolutePath()+"开始...");
		
		String lines = null;
		
		try {
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(src)));
			bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(dis)));
			
			while((lines = br.readLine())!=null) {
				bw.write(lines);
				bw.newLine();
				bw.flush();
			}
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileStr] : 文件复制成功！");
		}catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] 文件复制失败: "+e.getMessage());
		}finally {
			try {
				if(br!=null) {
					br.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] 文件复制失败: "+e.getMessage());
			}
			
			try {
				if(bw!=null) {
					bw.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] 文件复制失败: "+e.getMessage());
			}
		}
	}
	
	/**
	 * 删除文件夹里面空的文件夹
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	public static void deleteInBlankDir(String src) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.deleteInBlackDir] ： 待删除的文件名不能为空!");
			return ;
		}
		
		File file = new File(src);
		deleteInBlankDir(file);
	}
	
	/**
	 * 删除文件夹里面空的文件夹
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	public static void deleteInBlankDir(File src) {
		File files[] = src.listFiles();
		ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : 删除文件夹里面的空文件夹"+src.getAbsolutePath()+"开始...");
		if(files==null) {
			ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : "+src.getAbsolutePath()+"是文件，不包含文件夹...");
		}else if(files.length!=0){
			deleteInBlankDircCircle(src);
			ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : 删除文件夹里面的空文件夹"+src.getAbsolutePath()+"结束...");
		}
	}
	
	/**
	 * 删除文件夹里面空的文件夹
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	private static void deleteInBlankDircCircle(File src) {
		// TODO Auto-generated method stub
		
		/**
		 * 文件返回null，文件夹返回文件或文件夹个数的文件数组，空文件夹返回大小为0的空数组
		 */
		File files[] = src.listFiles();
		if(files!=null && files.length>0) {
			for (File f : files) {
				if(f.isDirectory()) {
					deleteInBlankDircCircle(f);
					
					//如果删除了空文件夹的文件夹也是空文件夹，则删除
					//注：文件夹删除后，f.listFiles()返回就是null了
					if(  f.listFiles()!=null && f.listFiles().length==0) {
						f.delete();
					}
				}
			}
		}else if(files!=null){
			src.delete();//删除空的文件夹
		}
	}
	
	/**
	 * 删除文件或文件夹
	 * 2019-06-20 11:03:29
	 * @param src
	 */
	public static void delete(String src) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.delete] ： 待删除的文件名不能为空!");
			return ;
		}
		
		File file = new File(src);
		delete(file);
	}
	
	/**
	 * 删除文件或文件夹
	 * 2019-06-20 11:03:29
	 * @param src
	 */
	public static void delete(File file) {
		if(!file.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.delete] ： 待删除的文件"+file.getAbsolutePath()+"不存在!");
			return ;
		}
		
		ApplicationInitial.writeInfo("[RestoreBackFile.delete] : 删除文件"+file.getAbsolutePath()+"开始...");
		if(file.isFile()) {
			file.delete();
		}else {
			deleteIn(file);
			
			//删除最外层文件
			file.delete();
		}
		ApplicationInitial.writeInfo("[RestoreBackFile.delete] : 删除文件"+file.getAbsolutePath()+"成功...");
	}

	
	/**
	 * 删除文件夹里面的内容
	 * 2019-06-20 11:03:29
	 * @param file
	 */
	private static void deleteIn(File file) {
		// TODO Auto-generated method stub
		
		File files[] = file.listFiles();
		if(files!=null && files.length>0) {
			for (File f : files) {
				if(f.isDirectory()) {
					deleteIn(f);
					
				}
				f.delete();
			}
		}
	}
}
