/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 8, 2013-11:52:40 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cn.com.timekey.sms.service.ServiceResult;

/**
 * <b>类名称：</b>ServiceException<br/>
 * <b>类描述：</b>自定义服务异常<br/>
 * <b>创建时间：</b>Aug 8, 2013 11:52:40 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class ServiceException extends WebApplicationException {

	private static final long serialVersionUID = 4833141265701621196L;

	public ServiceException(final String message) {
		super(Response.status(Status.BAD_REQUEST)
				.entity(new ServiceResult(message)).build());
	}

}
