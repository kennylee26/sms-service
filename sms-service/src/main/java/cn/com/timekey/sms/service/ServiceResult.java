/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 8, 2013-3:35:12 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.service;

/**
 * <b>类名称：</b>ServiceResult<br/>
 * <b>类描述：</b>封装结果信息<br/>
 * <b>创建时间：</b>Aug 8, 2013 3:35:12 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class ServiceResult {

	private String message;

	/**
	 * 创建一个新的实例 ServiceResult.
	 */
	public ServiceResult() {
		super();
	}

	/**
	 * 创建一个新的实例 ServiceResult.
	 * 
	 * @param message
	 */
	public ServiceResult(String message) {
		super();
		this.message = message;
	}

	/**
	 * message
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
