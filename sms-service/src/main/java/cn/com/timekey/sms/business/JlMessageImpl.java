/**
 *  <b>日期：</b>2013-7-18-下午06:31:42<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.business;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.util.CollectionUtils;

import cn.com.timekey.commons.core.log.Log;
import cn.com.timekey.commons.core.log.LogFactory;
import cn.sendsms.AGateway.Protocols;
import cn.sendsms.GatewayException;
import cn.sendsms.Library;
import cn.sendsms.OutboundMessage;
import cn.sendsms.Service;
import cn.sendsms.TimeoutException;
import cn.sendsms.modem.SerialModemGateway;

/**
 * <b>类名称：</b>JlMessageUtils<br/>
 * <b>类描述：</b>金笛发送短信工具包<br/>
 * <b>创建时间：</b>2013-08-09<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class JlMessageImpl implements SmsUtil {

	private static final Log LOOGER = LogFactory.getLog(JlMessageImpl.class);

	private String id;
	// 端口号
	private String comPort;
	// 端口频率
	private String baudRate;
	private String manufacturer;
	private String model;

	private SendMessage app = new SendMessage();

	private boolean isInit = false;
	private boolean isSingleton = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gov.pw.zjwfbes.sms.Sms#sendMessage(java.lang.String,
	 * java.lang.String)
	 */
	public boolean sendMessage(final String phoneNo, final String message) {
		return this.sendMessage(Arrays.asList(phoneNo), message);
	}

	private void print(String msg) {
		LOOGER.debug(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gov.pw.zjwfbes.sms.Sms#sendMessage(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public boolean sendMessage(List<String> phoneNos, String message) {
		if (CollectionUtils.isEmpty(phoneNos) || StringUtils.isBlank(message)) {
			LOOGER.warn("phoneNos is " + phoneNos.toString());
			LOOGER.warn("message is " + message);
			LOOGER.warn("bad parameters..skip");
			return true;
		}
		synchronized (this) {
			try {
				if (isSingleton == false
						|| (isSingleton == true && (isInit == false))) {
					// 非单例，或者单例但没初始化的时候，启动服务。
					app.start();
					this.isInit = true;
				}
				for (String phoneNo : phoneNos) {
					app.sendMessage(phoneNo, message);
				}
			} catch (Exception e) {
				LOOGER.error(e.getMessage(), e);
				return false;
			} finally {
				if (isSingleton == false) {
					app.close();
				}
			}
			print("send message finlshed");
		}
		return true;
	}

	private class SendMessage {

		private Service srv = Service.getInstance();
		private SerialModemGateway gateway;

		/**
		 * 创建一个新的实例 SendMessage.
		 */
		public SendMessage() {
			super();
		}

		/**
		 * <p>
		 * 构造短信网关
		 * </p>
		 */
		private void generateGSMGateway() {
			// OutboundNotification outboundNotification = new
			// OutboundNotification();
			print(Library.getLibraryDescription());
			print("版本: " + Library.getLibraryVersion());

			// 有时由于信号问题,可能会引起超时,运行时若出现No Response 请把这句注释打开
			// System.setProperty("sendsms.nocops", new String());

			// 使用时请修改端口号和波特率,如果不清楚,可以去www.sendsms.com.cn下载金笛设备检测工具检测一下
			// SerialModemGateway gateway = new
			// SerialModemGateway("jindi",
			// "COM3", 115200, "Wavecom", null);
			print("comPort: " + comPort);
			print("baudRate: " + baudRate);
			print("manufacturer: " + manufacturer);
			if (gateway == null) {
				gateway = new SerialModemGateway(id, comPort,
						Integer.valueOf(baudRate), manufacturer, model);

				// 设置通道gateway是否处理接受到的短信
				// gateway.setInbound(true);

				// 设置是否可发送短信
				gateway.setOutbound(true);

				gateway.setSimPin("0000");

				// 设置短信编码格式，默认为 PDU (如果只发送英文，请设置为TEXT)。CDMA设备只支持TEXT协议
				gateway.setProtocol(Protocols.PDU);
				// 添加Gateway到Service对象，如果有多个Gateway，都要一一添加。
				try {
					srv.addGateway(gateway);
				} catch (GatewayException e) {
					LOOGER.error(e.getMessage(), e);
				}
			}
		}

		/**
		 * 
		 * <p>
		 * 开启短信服务
		 * </p>
		 */
		private void start() {
			print("JlMessageImpl.start()........");
			StopWatch clock = new StopWatch();
			try {
				generateGSMGateway();
				LOOGER.warn("goto start SMS service ...");
				clock.start();
				// 启动服务
				srv.startService();
				debugGatewayInfo();
				LOOGER.warn("start service success...");
			} catch (Exception e) {
				LOOGER.error(e.getMessage(), e);
				LOOGER.warn("start service failed...");
			} finally {
				LOOGER.warn("spent time: %d s", clock.getTime() / 1000);
			}
		}

		private void debugGatewayInfo() throws TimeoutException,
				GatewayException, IOException, InterruptedException {
			print("**********************");
			print("设备信息:");
			print("  厂  商: " + gateway.getManufacturer());
			print("  型  号: " + gateway.getModel());
			print("  序列号: " + gateway.getSerialNo());
			print("  IMSI号: " + gateway.getImsi());
			print("  信  号: " + gateway.getSignalLevel() + "%");
			print("  电  池: " + gateway.getBatteryLevel() + "%");
			print("**********************");
		}

		/**
		 * 
		 * <p>
		 * 发送短信
		 * </p>
		 * 
		 * @param phoneNo
		 * @param message
		 * @return boolean 是否发送成功
		 */
		public boolean sendMessage(String phoneNo, String message) {
			return doIt(phoneNo, message);
		}

		private boolean doIt(String phoneNo, String message) {
			print("send message doIt()...");
			// 发送短信
			OutboundMessage msg = new OutboundMessage(phoneNo, message);
			msg.setEncoding(OutboundMessage.MessageEncodings.ENCUCS2);
			msg.setStatusReport(true);
			boolean flag = false;
			try {
				LOOGER.debug(msg);
				flag = srv.sendMessage(msg);
			} catch (TimeoutException e) {
				LOOGER.error(e.getMessage(), e);
				return false;
			} catch (GatewayException e) {
				LOOGER.error(e.getMessage(), e);
				return false;
			} catch (IOException e) {
				LOOGER.error(e.getMessage(), e);
				return false;
			} catch (InterruptedException e) {
				LOOGER.error(e.getMessage(), e);
				return false;
			}
			print("send message doIt() end");
			return flag;

		}

		/**
		 * 
		 * <p>
		 * 关闭服务
		 * </p>
		 */
		private void close() {
			try {
				LOOGER.info("Go to Stop message Service ... ");
				srv.stopService();
				print("Stop Service finished..");
			} catch (Exception e) {
				LOOGER.info(e.getMessage(), e);
			}
		}
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param comPort
	 *            the comPort to set
	 */
	public void setComPort(String comPort) {
		this.comPort = comPort;
	}

	/**
	 * @param baudRate
	 *            the baudRate to set
	 */
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}

	/**
	 * @param manufacturer
	 *            the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @param isSingleton
	 *            the isSingleton to set
	 */
	public void setSingleton(boolean isSingleton) {
		this.isSingleton = isSingleton;
	}

}
