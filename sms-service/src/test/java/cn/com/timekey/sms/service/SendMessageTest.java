/**
 *  <b>项目名: </b> 系统项目<br/>
 *  <b>日期：</b>Aug 8, 2013-10:52:11 PM<br/>
 *  <b>Copyright (c)</b> 2013 广州天健软件有限公司<br/>
 */
package cn.com.timekey.sms.service;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * <b>类名称：</b>SendMessageTest<br/>
 * <b>类描述：</b>测试 SendMessage<br/>
 * <b>创建时间：</b>Aug 8, 2013 10:52:11 PM<br/>
 * <b>备注：</b><br/>
 * 
 * @author kennylee <br />
 * @version 1.0.0<br/>
 */
public class SendMessageTest {

	private static String endpointUrl = "http://192.168.1.222:8080/sms-service/";

	@Test
	public void testExecute() throws Exception {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
		SendSmsBean inputBean = new SendSmsBean();
		List<String> phones = Arrays.asList(new String[] { "13800138000" });
		inputBean.setMessage("测试短信啊啊啊啊啊是的圣诞树 圣诞树的");
		inputBean.setPhones(phones);
		WebClient client = WebClient.create(endpointUrl + "/send", providers);
		Response r = client.accept("application/json").type("application/json")
				.post(inputBean);
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
		MappingJsonFactory factory = new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream) r
				.getEntity());
		ServiceResult output = parser.readValueAs(ServiceResult.class);
		System.out.println("result message: " + output.getMessage());
		Assert.assertEquals(output.getMessage(), "success");
	}

}
