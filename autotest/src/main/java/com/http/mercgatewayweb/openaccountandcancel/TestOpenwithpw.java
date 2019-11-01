package com.http.mercgatewayweb.openaccountandcancel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;

/**
 * Test merc-gateway-web interface
 * 
 * @author Wendy 
 * Description: 个人用户开户（传入密码）（100058）
 */
public class TestOpenwithpw extends RequestMsgUtils {
	private static Logger logger = LoggerFactory.getLogger(TestOpenwithpw.class);
	private HttpSendUtils http = new HttpSendUtils();
	private RandomUtils randomUtils = new RandomUtils();
	private String reqUrlOpenwithpw = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
			+ "openaccount/openwithpw";

	// private String reqUrlTestOpenwithpw =
	// randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") +
	// "agreementpay/sign";
	// private String reqUrlUnsign =
	// randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") +
	// "agreementpay/unsign";
	// 初始化数据
	public void getOpenwithpwInit() {
		mapInit();
		infoMap.put("TRX_CODE", "100058");// Y 交易代码
		infoMap.put("VERSION", "2.0");// Y 版本
		infoMap.put("DATA_TYPE", "0");// N 数据格式 0：xml格式
		infoMap.put("REQ_SN", randomUtils.getRandomTime(14));// Y 请求流水号
		infoMap.put("SIGNED_MSG", "signedMsg");// Y 签名信息
		// body
		bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);// Y 商户编号（联调回归商户）
		bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));// 商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());// 商户订单日期
		bodyMap.put("MERC_USER_NO","U" + randomUtils.getDateTime17());// 商户用户号
		bodyMap.put("RN_MERCHANT_ID", AutoConstant.MERC_ID_40001);// 实名/签约时的商户编号 发起方
		bodyMap.put("AUTH_TYPE", "1");// 实名认证类型
		bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());// 银行账户名称
		bodyMap.put("ID_TYPE", "00");// 开户证件类型  00：身份证
		bodyMap.put("ID_NO", randomUtils.getIdNo());// 证件号
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());// 证件号

		
//		bodyMap.put("RN_MERC_ORDER_NO", "1234567890cx");// 实名/签约时的商户订单号
//		bodyMap.put("RN_MERC_ORDER_DATE", "12345678");// 实名/签约时的商户订单日期
//		bodyMap.put("REG_MOBILE_NO", "15652563221");// 注册手机号
//		bodyMap.put("PAY_PASSWORD", "123456789009876543211234567890");// 支付密码
		bodyMap.put("EXTEND1", "111");// 备用域1
		bodyMap.put("EXTEND2", "222");// 备用域2
		bodyMap.put("EXTEND3", "222");// 备用域3
	}

	@Test
	public void testOpenwithpw() throws Exception {
		getOpenwithpwInit();
		bodyMap.put("MERC_USER_NO","U"+randomUtils.getDateTime17());// 商户用户号
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlOpenwithpw);

		logger.info("响应：{}", respMap.toString());
		logger.info("判断签约获取短验是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");

	}
}
