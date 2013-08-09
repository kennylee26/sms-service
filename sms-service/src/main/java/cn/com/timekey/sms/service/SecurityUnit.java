/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 5, 2013-5:32:49 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.com.timekey.commons.core.log.Log;
import cn.com.timekey.commons.core.log.LogFactory;

/**
 * <b>类名称：</b>SecurityUnit<br/>
 * <b>类描述：</b>安全模块<br/>
 * <b>创建时间：</b>Aug 5, 2013 5:32:49 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class SecurityUnit {

	private final Log logger = LogFactory.getLog(getClass());

	private Set<String> trustIps;

	private static final String IPV6_LOCALHOST = "0:0:0:0:0:0:0:1";
	private static final String IPV4_LOCALHOST = "127.0.0.1";

	/**
	 * <p>
	 * 检查请求是否信任来自IP。
	 * </p>
	 * 
	 * @param servletRequest
	 * @return true如果信任的话，否则返回false。
	 */
	public boolean checkTrustIps(HttpServletRequest servletRequest) {
		boolean isPass = false;
		// 客户端IP
		String remoteIp = servletRequest.getRemoteAddr();
		// 服务器IP
		String localIp = servletRequest.getLocalAddr();
		Set<String> trustIpList = trustIps;
		logger.info("Romote Ip:" + remoteIp);
		logger.info("Local Ip:" + localIp);
		if (StringUtils.equals(remoteIp, localIp)
				|| StringUtils.equals(IPV6_LOCALHOST, remoteIp)
				|| StringUtils.equals(IPV4_LOCALHOST, remoteIp)) {// 默认本地可以调用
			isPass = true;
		} else {
			if ((trustIpList != null) && (!trustIpList.isEmpty())) {
				if (trustIpList.contains(remoteIp)) {
					isPass = true;
				}
			}
		}
		return isPass;
	}

	/**
	 * @param trustIps
	 *            the trustIps to set
	 */
	public void setTrustIps(Set<String> trustIps) {
		this.trustIps = trustIps;
	}

}
