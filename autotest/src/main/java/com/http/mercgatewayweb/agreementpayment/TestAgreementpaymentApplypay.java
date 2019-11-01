package com.http.mercgatewayweb.agreementpayment;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Test merc-gateway-web interface 
 * @author zgp
 * @version 2.0
 * Description: 2.0系统，协议支付-身份认证
 */
public class TestAgreementpaymentApplypay extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestAgreementpaymentApplypay.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/agreementpayment/applypay";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"agreementpayment/applypay";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private TestAgreementpaymentCreateorder crateOrder = new TestAgreementpaymentCreateorder();

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100045");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID","800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",orderNo);
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());
		bodyMap.put("BANK_ACCOUNT_NAME","");//银行账户名称,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("BANK_MOBILE_NO","");//银行预留手机号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CARD_NO","");//卡号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("ID_NO","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("VALID_MODE","");//验证方式,Y,1:短信校验	2:支付密码校验	3:无需校验
		bodyMap.put("PAY_PASSWORD","");//支付密码,N
		bodyMap.put("EXTEND1","111");
		bodyMap.put("EXTEND2","222");
		bodyMap.put("EXTEND3","222");
	}
	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
		initReqMap();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}
	/**
	 * 不短信
	 */
//	@Test
	public void testAgreementpayCreateorderNoSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
		crateOrder.setUp();
		crateOrder.testAgreementpayCreateorder();
		bodyMap.put("MERCHANT_ID",crateOrder.bodyMap.get("MERCHANT_ID"));//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",crateOrder.bodyMap.get("MERC_ORDER_NO"));
		bodyMap.put("MERC_ORDER_DATE",crateOrder.bodyMap.get("MERC_ORDER_DATE"));
//		bodyMap.put("MERC_USER_NO","AT20181024212042494");//商户用户编号,Y
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("VALID_MODE","3");//验证方式,Y,1:短信校验	2:支付密码校验	 3:无需校验

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		Assert.assertEquals("2", respMap.get("SMS_SEND_FLAG"));//不发短信
	}

	/**
	 * 发短信
	 */
	@Test
	public void testAgreementpayCreateorderWithSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
		crateOrder.setUp();
		crateOrder.testAgreementpayCreateorder();
		bodyMap.put("MERCHANT_ID",crateOrder.bodyMap.get("MERCHANT_ID"));//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",crateOrder.bodyMap.get("MERC_ORDER_NO"));
		bodyMap.put("MERC_ORDER_DATE",crateOrder.bodyMap.get("MERC_ORDER_DATE"));
//		bodyMap.put("MERC_USER_NO","AT20181024212042494");//商户用户编号,Y
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("VALID_MODE","1");//验证方式,Y,1:短信校验	2:支付密码校验	 3:无需校验

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		Assert.assertEquals("1", respMap.get("SMS_SEND_FLAG"));//发短信
	}

}
