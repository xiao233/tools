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
 * �ļ�����������
 * @author xjl
 * 2019-06-19 17:45:55
 */
public class FileUtils {

	/**
	 * �����ļ����ļ����ڵ����ݵ�ָ���ļ���
	 * 2019-06-19 17:47:34
	 * @param src Դ�ļ���
	 * @param dis Ŀ���ļ���
	 */
	public static boolean copyFileTo(String src, String dis) {
		// TODO Auto-generated method stub
		
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] �� Դ�ļ�������Ϊ��!");
			return false;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] �� Ŀ���ļ�������Ϊ��!");
			return false;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ��Դ�ļ�"+src+"������!");
			return false;
		}
		
		File disFile = new File(dis);
		
		try {
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileTo] : �ļ�"+src+"���Ƶ��ļ���"+dis+"��ʼ...");
			copyFileTo(srcFile,disFile);
		} catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileTo] ��"+e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * �����ļ����ļ����ڵ����ݵ�ָ���ļ���
	 * 2019-06-19 17:47:34
	 * @param src Դ�ļ�
	 * @param dis Ŀ���ļ�
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
	 * ��һ���ļ������ݸ��Ƶ���һ���ļ�,�ֽ���
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileBinary(String src, String dis) {
		
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] �� Դ�ļ�������Ϊ��!");
			return ;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] �� Ŀ���ļ�������Ϊ��!");
			return ;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] ��Դ�ļ�"+src+"������!");
			return ;
		}
		
		File disFile = new File(dis);
		copyFileToFileBinary(srcFile,disFile);
	}
	
	/**
	 * ��һ���ļ������ݸ��Ƶ���һ���ļ�,�ֽ���
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileBinary(File src, File dis) {
		// TODO Auto-generated method stub
		
		InputStream fis = null;
		OutputStream fos = null;
		ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileBinary] : �ļ�"
				+src.getAbsolutePath()+"���Ƶ�"+dis.getAbsolutePath()+"��ʼ...");
		
		//�����ֽڻ��������ӿ��ļ���д�ٶ�
		byte []caches = new byte[1024];
		int len = 0;
		
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(dis);
			
			while((len=fis.read(caches))>0) {
				fos.write(caches, 0, len);
				fos.flush();
			}
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileBinary] : �ļ����Ƴɹ���");
		}catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] �ļ�����ʧ��: "+e.getMessage());
		}finally {
			try {
				if(fis!=null) {
					fis.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] �ļ�����ʧ��: "+e.getMessage());
			}
			
			try {
				if(fos!=null) {
					fos.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileBinary] �ļ�����ʧ��: "+e.getMessage());
			}
		}
	}
	
	/**
	 * ��һ���ļ������ݸ��Ƶ���һ���ļ�,�ַ���
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileStr(String src, String dis) {
		
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] �� Դ�ļ�������Ϊ��!");
			return ;
		}
		
		if(StringUtils.isEmpty(dis)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] �� Ŀ���ļ�������Ϊ��!");
			return ;
		}
		
		File srcFile = new File(src);
		if(!srcFile.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] ��Դ�ļ�"+src+"������!");
			return ;
		}
		
		File disFile = new File(dis);
		copyFileToFileBinary(srcFile,disFile);
	}
	
	/**
	 * ��һ���ļ������ݸ��Ƶ���һ���ļ�,�ָ���
	 * 2019-06-20 09:37:39
	 * @param src
	 * @param dis
	 */
	public static void copyFileToFileStr(File src, File dis) {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileStr] : �ļ�"
				+src.getAbsolutePath()+"���Ƶ�"+dis.getAbsolutePath()+"��ʼ...");
		
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
			ApplicationInitial.writeInfo("[RestoreBackFile.copyFileToFileStr] : �ļ����Ƴɹ���");
		}catch (Exception e) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] �ļ�����ʧ��: "+e.getMessage());
		}finally {
			try {
				if(br!=null) {
					br.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] �ļ�����ʧ��: "+e.getMessage());
			}
			
			try {
				if(bw!=null) {
					bw.close();
				}
			} catch (IOException e) {
				ApplicationInitial.writeErrorInfo("[RestoreBackFile.copyFileToFileStr] �ļ�����ʧ��: "+e.getMessage());
			}
		}
	}
	
	/**
	 * ɾ���ļ�������յ��ļ���
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	public static void deleteInBlankDir(String src) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.deleteInBlackDir] �� ��ɾ�����ļ�������Ϊ��!");
			return ;
		}
		
		File file = new File(src);
		deleteInBlankDir(file);
	}
	
	/**
	 * ɾ���ļ�������յ��ļ���
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	public static void deleteInBlankDir(File src) {
		File files[] = src.listFiles();
		ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : ɾ���ļ�������Ŀ��ļ���"+src.getAbsolutePath()+"��ʼ...");
		if(files==null) {
			ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : "+src.getAbsolutePath()+"���ļ����������ļ���...");
		}else if(files.length!=0){
			deleteInBlankDircCircle(src);
			ApplicationInitial.writeInfo("[RestoreBackFile.deleteInBlankDir] : ɾ���ļ�������Ŀ��ļ���"+src.getAbsolutePath()+"����...");
		}
	}
	
	/**
	 * ɾ���ļ�������յ��ļ���
	 * 2019-06-20 13:58:35
	 * @param src
	 */
	private static void deleteInBlankDircCircle(File src) {
		// TODO Auto-generated method stub
		
		/**
		 * �ļ�����null���ļ��з����ļ����ļ��и������ļ����飬���ļ��з��ش�СΪ0�Ŀ�����
		 */
		File files[] = src.listFiles();
		if(files!=null && files.length>0) {
			for (File f : files) {
				if(f.isDirectory()) {
					deleteInBlankDircCircle(f);
					
					//���ɾ���˿��ļ��е��ļ���Ҳ�ǿ��ļ��У���ɾ��
					//ע���ļ���ɾ����f.listFiles()���ؾ���null��
					if(  f.listFiles()!=null && f.listFiles().length==0) {
						f.delete();
					}
				}
			}
		}else if(files!=null){
			src.delete();//ɾ���յ��ļ���
		}
	}
	
	/**
	 * ɾ���ļ����ļ���
	 * 2019-06-20 11:03:29
	 * @param src
	 */
	public static void delete(String src) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(src)) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.delete] �� ��ɾ�����ļ�������Ϊ��!");
			return ;
		}
		
		File file = new File(src);
		delete(file);
	}
	
	/**
	 * ɾ���ļ����ļ���
	 * 2019-06-20 11:03:29
	 * @param src
	 */
	public static void delete(File file) {
		if(!file.exists()) {
			ApplicationInitial.writeErrorInfo("[RestoreBackFile.delete] �� ��ɾ�����ļ�"+file.getAbsolutePath()+"������!");
			return ;
		}
		
		ApplicationInitial.writeInfo("[RestoreBackFile.delete] : ɾ���ļ�"+file.getAbsolutePath()+"��ʼ...");
		if(file.isFile()) {
			file.delete();
		}else {
			deleteIn(file);
			
			//ɾ��������ļ�
			file.delete();
		}
		ApplicationInitial.writeInfo("[RestoreBackFile.delete] : ɾ���ļ�"+file.getAbsolutePath()+"�ɹ�...");
	}

	
	/**
	 * ɾ���ļ������������
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
