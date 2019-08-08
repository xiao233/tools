package xiao.function;

import java.util.HashMap;
import java.util.Map;

import xiao.ApplicationInitial;
import xiao.CommonConstants;
import xiao.StringUtils;

/**
 * 进制转换
 * @author xjl
 * 2019-06-17 16:44:45
 */
public class ScaleChangeEach {
	

	/**
	 * 各种进制之间相互转换,仅限于2-16进制
	 * 2019-06-17 16:45:38
	 * @param src	要转换的数
	 * @param type	转换类型2-16
	 * @return
	 */
	public Map<String,String> scaleChangeEach(String src,String type) {
		// TODO Auto-generated method stub
		
		Map<String,String> operRs = checkDataSrcType(src,type);
		
		String rs = src;
		
		if(operRs.get("code").equals("succ")) {
			String decimal = eachScaleChangeToDecimal(src);
			rs += ","+decimal+"|10";
			String eachScale = decimalChangeToEachScale(decimal,type);
			rs += ","+ eachScale+"|"+type;
			operRs.put("rs", rs);
			ApplicationInitial.writeInfo("[ScaleChangeEach.scaleChangeEach] ：code = "
					+operRs.get("code")+", msg = "+operRs.get("msg")+", rs = "+rs);
		}else {
			ApplicationInitial.writeErrorInfo("[ScaleChangeEach.scaleChangeEach] ：code = "
					+operRs.get("code")+", msg = "+operRs.get("msg")+", rs = "+rs);
		}
		
		
		return operRs;
	}

	/**
	 * 将十进制转为2-16进制
	 * 2019-06-19 09:43:35
	 * @param decimal
	 * @param type
	 * @return
	 */
	private String decimalChangeToEachScale(String decimal, String type) {
		// TODO Auto-generated method stub
		
		if("10".equals(type)) {
			return decimal;
		}
		
		int scale = Integer.parseInt(type);
		StringBuilder rs = new StringBuilder("");
		
		long value = Long.parseLong(decimal);
		while(value!=0l) {
			rs.append(value%scale);
			value /= scale;
		}
		return rs.reverse().toString();
	}

	/**
	 * 将2-16进制转为十进制
	 * 2019-06-19 09:41:34
	 * @param src
	 * @return
	 */
	private String eachScaleChangeToDecimal(String src) {
		// TODO Auto-generated method stub
		
		String srcs[] = src.trim().split("\\|");
		
		int scaleType = 0;
		if(srcs.length<2||"10".equals(srcs[1])) {
			return srcs[0];
		}else {
			scaleType = Integer.parseInt(srcs[1]);
		}
		
		int sum = 0;
		//因子
		int mul = 1;
		char bitvalues[] = new StringBuilder(srcs[0]).reverse().toString().toCharArray();
		for (int i = 0; i < bitvalues.length; i++) {
			
			int meanValue = 0;
			if(bitvalues[i]>='0'&&bitvalues[i]<='9') {
				meanValue = bitvalues[i] - '0';
			}
			
			if(bitvalues[i]>='A'&&bitvalues[i]<='Z') {
				meanValue = bitvalues[i] - 'A'+10;
			}
			
			if(bitvalues[i]>='a'&&bitvalues[i]<='z') {
				meanValue = bitvalues[i] - 'a'+10;
			}
			sum += meanValue*mul;
			mul *= scaleType;
		}
		
		return sum+"";
	}

	/**
	 * 校验输入的内容
	 * 2019-06-19 09:36:19
	 * @param src
	 * @param type
	 * @return
	 */
	public Map<String,String> checkDataSrcType(String src,String type){
		// TODO Auto-generated method stub
		Map<String,String> operRs = new HashMap<String,String>();
		
		String code = "succ";
		String msg = "转换成功！";
		
		
		if(StringUtils.isEmpty(src)) {
			msg = "要转换的数为空，转换失败！";
			code = "fail";
		}else if(!checkDataSrc(src)) {
			msg = "要转换的数"+src+"不符合格式要求，转换失败！";
			code = "fail";
		}else {
			if(Integer.parseInt(type)<2||Integer.parseInt(type)>16) {
				msg = "转换的类型"+type+"不符，转换失败！";
				code = "fail";
			}
		}
		operRs.put("code", code);
		operRs.put("msg", msg);
		return operRs;
	}
	/**
	 * 校验要转换的源数据
	 * 2019-06-17 17:06:42
	 * @param src
	 * @return
	 */
	private boolean checkDataSrc(String src) {
		// TODO Auto-generated method stub
		String srcs[] = src.trim().split("\\|");
		if(!StringUtils.checkSrcByPattern(srcs[0],CommonConstants.SCALE_TWO_SIXTY_PATTERN)) {
			return false;
		}
		
		//当前进制
		int scaleType = 0;
		if(srcs.length<2) {
			scaleType = 10;
		}else {
			scaleType = Integer.parseInt(srcs[1]);
		}
		
		//当前进制只能在2-16之间
		if(scaleType<2||scaleType>16) {
			return false;
		}
		
		return checkDataSrc(srcs[0],scaleType);
	}

	/**
	 * 校验要转换的数的每位是否小于进制数
	 * 2019-06-17 17:48:04
	 * @param src
	 * @param scaleType
	 * @return
	 */
	private boolean  checkDataSrc(String src, int scaleType) {
		// TODO Auto-generated method stub
		
		String temp = src.toLowerCase();
		for (int i = 0; i < temp.length(); i++) {
			char index = temp.charAt(i);
			int value = 0;
			if(index>='0'&&index<='9') {
				value = index-'0';
			}else if(index>='a'&&index<='f') {
				value = index-'a'+10;
			}else {
				return false;
			}
			if(value>=scaleType) {
				return false;
			}
		}
		return true;
	}
}
