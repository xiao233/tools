package xiao.function.bean;
/**
 * �ļ���ԭ��bean
 * @author xjl
 * 2019-06-19 15:55:05
 */
public class RestoreBackFileBean {

	private String point;
	private String function;
	private String time;
	private String resource;
	private String backup;
	private String remark;
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getBackup() {
		return backup;
	}
	public void setBackup(String backup) {
		this.backup = backup;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		return "\t\tpoint: " + point 
				+ "\n\t\t����: " + function 
				+ "\n\t\t����ʱ��: " + time 
				+ "\n\t\t�ļ�λ��: " + resource 
				+ "\n\t\t����λ��: " + backup
				+ "\n\t\t ˵��: " + remark;
	}
}
