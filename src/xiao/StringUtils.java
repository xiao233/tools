package xiao;

import java.util.Random;

public class StringUtils {
	/**
	 * �ж��ַ����Ƿ�Ϊ��,<br/>
	 * ���ַ���Ϊnull����""ʱ��ΪΪ��
	 * 2018-09-13 10:12:39
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return string==null||string.equals("")?true:false;
	}

	/**
	 * �����ȡָ�������ַ���<br/>
	 * 2018-09-14 13:49:37
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		
		Random random = new Random();
		String str = "";
		while(true) {
			int temp = random.nextInt('z'-'0')+'0';
			if(temp>='0'&&temp<='9'
					||temp>='A'&&temp<='Z'
					||temp>='a'&&temp<='z') {
				str+=(char)temp;
			}
			if(str.length()==i) {
				break;
			}
		}
		return str;
	}

	/**
	 * ���»���������תΪ�շ���������
	 * 2018-12-25 13:53:22
	 * @param key
	 * @param firtstUp �Ƿ��׸���ĸ��д
	 * @return
	 */
	public static String changeName(String key,boolean firtstUp) {
		
		if(isEmpty(key)) {
			return key;
		}
		
		key = key.toLowerCase();
		
		String []strs = key.split("_");
		String newKey = "";
		for (int i = 0; i < strs.length; i++) {
			if(i==0) {
				if(firtstUp) {
					String temp = strs[i].substring(1, strs[i].length());
					newKey+=(char)(strs[i].charAt(0)-32)+temp;
				}else {
					newKey+=strs[i];
				}
			}else {
				String temp = strs[i].substring(1, strs[i].length());
				newKey+=(char)(strs[i].charAt(0)-32)+temp;
			}
		}
		return newKey;
	}

	/**
	 * У�������Ƿ�ƥ��ָ�����������
	 * 2019-06-17 17:25:37
	 * @param src
	 * @param rex
	 * @return
	 */
	public static boolean checkSrcByPattern(String src, String rex) {
		// TODO Auto-generated method stub
		
		return src.matches(rex);
	}
}
