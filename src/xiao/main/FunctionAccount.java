package xiao.main;

import xiao.CommonConfigs;

/**
 * ��������˵��
 * @author xjl
 * 2019-06-06 14:56:54
 */
public class FunctionAccount{

	/**
	 * ����1.��ת��ʵ����˵��
	 * 2019-06-06 14:57:24
	 */
	public  void function1Account() {
		// TODO Auto-generated method stub
		functionStart();
		
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
		System.err.println("\t\ta).�ù������ڸ������ݿ�ı�������Ӧ��ʵ����;");
		System.err.println("\t\tb).���롮h/H������Hibernateʵ��;");
		System.err.println("\t\tc).���롮p/P������POJOʵ��,Ĭ������POJOʵ��;");
		System.err.println("\t\td).�������ʱ������������','������������'*'��ʾΪ��ǰ���ݿ����б�����ʵ��;");
		System.err.println("\t\te).����·��Ϊ����user/entity�ļ�����;");
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
	}
	
	/**
	 * ����2.����ת��
	 * 2019-06-17 14:24:02
	 */
	public void function2Account() {
		// TODO Auto-generated method stub
		functionStart();
		
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
		System.err.println("\t\ta).�ù������ڸ���2-16����֮���ת��;");
		//System.err.println("\t\tb).�����������ԡ�b��B����β(Binary);");
		//System.err.println("\t\tc).�˽��������ԡ�o��O����β(Octal);");
		//System.err.println("\t\td).ʮ�����������ԡ�h��H����β(Hexadecimal);");
		System.err.println("\t\tb).Ĭ��ʮ���ƣ��ԡ������׺��d��D����β(Decimal);");
		System.err.println("\t\tc).ͨ�ý��ƣ��ԡ�|���ơ���β������������12|3=ʮ������5;");
		System.err.println("\t\td).ͨ������2-16��������2Ϊ������;");
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
	}

	/**
	 * ����3.�ļ����ҡ��滻
	 * 2019-06-19 10:36:08
	 */
	public void function3Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
		System.err.println("\t\ta).�ù��������ļ����ļ����ݲ��ң��Լ��ļ������滻;");
		System.err.println("\t\tb).�ļ����ң��ҳ�����Ҫ����ļ��б�ͳ�����ļ���;");
		System.err.println("\t\tc).�ļ����ݲ��ң��ҳ�����Ҫ����ļ��б��ȶԸ����ļ���Ϣ;");
		System.err.println("\t\td).�ļ������滻���ҳ�����Ҫ����ļ��б��ȶԸ����ļ���Ϣ���滻����;");
		System.err.println("\t\te).ִ���ļ������滻���ᱸ����Ӧ�ļ����ݵ�user/file/backup�ļ�����("+CommonConfigs.BACKFILE_RESERVE+"��);");
		System.err.println("\t\tf).������Ӧ�����󣬿���"+CommonConfigs.BACKFILE_RESERVE+"���ڽ��л�ԭ;");
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
	}

	/**
	 * ����4.ϵͳ��������ɾ���󲻿ɻ�ԭ�������أ���
	 * 2019-06-19 10:36:22
	 */
	public void function4Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
		System.err.println("\t\ta).�ù������ڴ����ļ�����;");
		System.err.println("\t\tb).����ָ���ļ����ļ��У�������ʽ��c|�ļ�����;");
		System.err.println("\t\tc).������ļ�����ļ��У�������ʽ��cb|�ļ�����;");
		System.err.println("\t\td).�ù��ܣ��ᱸ����Ӧ�ļ����ݵ�user/disk�ļ�����("+CommonConfigs.BACKFILE_RESERVE+"��);");
		System.err.println("\t\te).������Ӧ�����󣬿���"+CommonConfigs.BACKFILE_RESERVE+"���ڽ��л�ԭ;");
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
	}
	
	/**
	 * ����5.�ļ���ԭ
	 * 2019-06-19 10:36:22
	 */
	public void function5Account() {
		// TODO Auto-generated method stub
		
		functionStart();
		
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
		System.err.println("\t\ta).�ù��������ڱ���������Ҳ���ʱ����"+CommonConfigs.BACKFILE_RESERVE+"���ڵ��ļ���ԭ;");
		System.err.println("\t\tb).����ԭ���ļ�����ǰ��״̬��λ��;");
		System.err.println("\t\tc).��ԭ�ɹ����ɾ����Ӧ��������;");
		System.err.println("\t\td).���롮*���鿴���л�ԭ����Ϣ;");
		System.err.println("\t\te).���롮point|���ӡ��ڵ㡯�ӡ�|f����ʽ�鿴ָ���ڵ���Ϣ;");
		System.err.println("\t\tf).���롮point|���ӡ��ڵ㡯�ӡ�|b����ʽ��ԭ�ڵ���Ϣ��Ӧ����;");
		System.err.println("\t����������������������������������������������������������������������������������������������������������������������������������������������������");
	}
	
	/**
	 * ĳ������ִ�н���
	 * 2019-06-06 15:41:12
	 */
	public  void functionEnd() {
		// TODO Auto-generated method stub
		System.err.println("\n\n\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~�١������ᡤ��~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
	}
	
	/**
	 * ĳ������ִ�п�ʼ
	 * 2019-06-06 15:41:12
	 */
	public  void functionStart() {
		// TODO Auto-generated method stub
		System.err.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~�١���������ʼ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
	}


}
