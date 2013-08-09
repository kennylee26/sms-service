/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 5, 2013-5:08:52 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.service;

import java.util.List;

/**
 * <b>类名称：</b>SendSmsBean<br/>
 * <b>类描述：</b>发送短信的Bean<br/>
 * <b>创建时间：</b>Aug 5, 2013 5:08:52 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class SendSmsBean {

	private List<String> phones;
	private String message;

	/**
	 * 创建一个新的实例 SendSmsBean.
	 */
	public SendSmsBean() {
		super();
	}

	/**
	 * phones
	 * 
	 * @return the phones
	 */
	public List<String> getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 *            the phones to set
	 */
	public void setPhones(List<String> phones) {
		this.phones = phones;
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
