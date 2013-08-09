/**
 *  <b>日期：</b>2013-7-18-下午06:37:51<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.business;

import java.util.List;

/**
 * <b>类名称：</b>Sms<br/>
 * <b>类描述：</b>短信处理接口<br/>
 * <b>创建时间：</b>2013-7-18 下午06:37:51<br/>
 * <b>备注：</b><br/>
 * 
 * @author dengt <br />
 * @version 1.0.0<br/>
 */
public interface SmsUtil {

	/**
	 * 
	 * <p>
	 * 发送短信
	 * </p>
	 * 
	 * @param phoneNo
	 *            手机号码
	 * @param message
	 *            短信内容
	 */
	public boolean sendMessage(String phoneNo, String message);

	/**
	 * 
	 * <p>
	 * 多个号码发送短信
	 * </p>
	 * 
	 * @param phoneNos
	 *            List <String> 手机号码集合
	 * @param message
	 *            短信内容
	 */
	public boolean sendMessage(List<String> phoneNos, String message);
}
