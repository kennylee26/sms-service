/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 5, 2013-5:16:23 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import cn.com.timekey.commons.core.collections.CollectionHelper;
import cn.com.timekey.commons.core.log.Log;
import cn.com.timekey.commons.core.log.LogFactory;
import cn.com.timekey.sms.business.SmsUtil;
import cn.com.timekey.sms.exception.ServiceException;

/**
 * <b>类名称：</b>SendMessage<br/>
 * <b>类描述：</b>发送短信的主体功能<br/>
 * <b>创建时间：</b>Aug 5, 2013 5:16:23 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
@Path("/send")
public class SendMessage {

	private static final String ACCESS_DENIED = "Access denied!";
	private static final String BAD_ARGUMENT = "bad argument";
	private static final String SUCCESS = "success";
	private static final String FAILED = "failed";

	private final Log logger = LogFactory.getLog(getClass());

	private SecurityUnit securityUnit;
	private SmsUtil smsUtil;

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response execute(SendSmsBean smsBean,
			@Context HttpServletRequest servletRequest) {
		logger.debug("SendMessage.execute()");
		String remoteIp = servletRequest.getRemoteAddr();
		if (smsBean == null || CollectionHelper.isEmpty(smsBean.getPhones())
				|| StringUtils.isBlank(smsBean.getMessage())) {
			// 参数有问题
			logger.warn(BAD_ARGUMENT);
			throw new ServiceException(BAD_ARGUMENT);
		}
		boolean isPass = securityUnit.checkTrustIps(servletRequest);
		if (isPass) {
			logger.warn("Remote client: %s want to send message.", remoteIp);
			List<String> phones = smsBean.getPhones();
			String phoneMessage = smsBean.getMessage();
			if (logger.isDebugEnabled()) {
				for (String phone : phones) {
					logger.debug(phone);
				}
				logger.debug("message: %s", phoneMessage);
			}
			boolean isSuccess = false;
			try {
				isSuccess = smsUtil.sendMessage(phones, phoneMessage);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ServiceException(e.getMessage());
			}
			if (isSuccess) {
				ServiceResult result = new ServiceResult(SUCCESS);
				logger.warn("send message finished.");
				return Response.ok().entity(result).build();
			} else {
				logger.error(FAILED);
				throw new ServiceException(FAILED);
			}
		} else {
			// IP拒绝
			logger.warn(ACCESS_DENIED);
			throw new ServiceException(ACCESS_DENIED);
		}
	}

	/**
	 * @param securityUnit
	 *            the securityUnit to set
	 */
	public void setSecurityUnit(SecurityUnit securityUnit) {
		this.securityUnit = securityUnit;
	}

	/**
	 * @param smsUtil
	 *            the smsUtil to set
	 */
	public void setSmsUtil(SmsUtil smsUtil) {
		this.smsUtil = smsUtil;
	}

}
