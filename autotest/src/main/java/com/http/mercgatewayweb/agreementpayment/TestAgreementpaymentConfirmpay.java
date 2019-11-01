package com.http.mercgatewayweb.agreementpayment;

import com.alibaba.fastjson.JSONObject;
import com.interfacetest.commonutils.DBConnectUtils;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Test merc-gateway-web interface 
 * @author zgp
 * @version 2.0
 * Description: 2.0系统，协议支付-身份认证
 */

public class TestAgreementpaymentConfirmpay extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestAgreementpaymentConfirmpay.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/agreementpayment/confirmpay";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpayment/confirmpay";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private TestAgreementpaymentApplypay createorder =  new TestAgreementpaymentApplypay();

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100046");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", "");
		bodyMap.put("MERC_ORDER_DATE", "");
		bodyMap.put("MBL_CAPTCHA", "");//短信验证码
		bodyMap.put("TRANS_JRN_NO","");//请求TOKEN",Y",下单接口或获取短信接口返回的请求TOKEN
		bodyMap.put("TERMINAL_TYPE", "04");//终端类型
		bodyMap.put("TERMINALID", "TERMINALID");
		bodyMap.put("DEVICE_INFO", "ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");
		bodyMap.put("EXTEND1", "111");
		bodyMap.put("EXTEND2", "222");
		bodyMap.put("EXTEND3", "222");
			Map<String, String> riskMsg = new HashMap<String, String>();
			riskMsg.put("subMercNo","1000001");
			riskMsg.put("subMercName", "小霸王");
			riskMsg.put("subMercTradeCategory", "");
			riskMsg.put("mercUserRegDate", "100");
			riskMsg.put("mercUserActiveDays", "99");
			riskMsg.put("mercUserActiveLevel", "1");
			riskMsg.put("mercUserRegMobile", "13800138000");
			riskMsg.put("mercUserRegEmail", "13800138000@139.com");
			riskMsg.put("mercUserIdNo", "1234567890");
			riskMsg.put("mercUserRegIp", "127.0.0.1");
			riskMsg.put("mercUserRegDeviceId", "1234567890");
			riskMsg.put("mercUserRealNameFlag", "1");
			riskMsg.put("orderRealFlag", "1");
			riskMsg.put("orderRealIdNo", "1234567890");
			riskMsg.put("rechargeMobile", "13800138000");
			riskMsg.put("receiveUserName", "小熊猫");
			riskMsg.put("receiveUserMobile", "13800138000");
			riskMsg.put("receiveAddrProv", "北京");
			riskMsg.put("receiveAddrCity", "北京");
			riskMsg.put("receiveAddrArea", "海淀");
			riskMsg.put("receiveAddrDetail", "四季青桥");
			riskMsg.put("payFrontUrlReferrer", "www.baidu.com");
		bodyMap.put("RISK_INFO", JSONObject.toJSONString(riskMsg));//风控大字段"
		bodyMap.put("BANK_ACCOUNT_NAME","");//银行账户名称,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("BANK_MOBILE_NO","");//银行预留手机号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CARD_NO","");//卡号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("ID_NO","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
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
	 * 不发短信
	 */
//	@Test
	public void testAgreementpayConfirmpayNoSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
		createorder.setUp();
		createorder.testAgreementpayCreateorderNoSms();
		bodyMap.put("MERCHANT_ID", createorder.bodyMap.get("MERCHANT_ID"));//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", createorder.bodyMap.get("MERC_ORDER_NO"));
		bodyMap.put("MERC_ORDER_DATE", createorder.bodyMap.get("MERC_ORDER_DATE"));
		bodyMap.put("MBL_CAPTCHA", "");//短信验证码
		bodyMap.put("TRANS_JRN_NO",createorder.respMap.get("TRANS_JRN_NO"));//请求TOKEN",Y",下单接口或获取短信接口返回的请求TOKEN
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}
	/**
	 * 下发短信
	 */
//	@Test
	public void testAgreementpayConfirmpayWithSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
		createorder.setUp();
		createorder.testAgreementpayCreateorderWithSms();
		String merc_id = createorder.bodyMap.get("MERCHANT_ID").toString();
		String order_no = createorder.bodyMap.get("MERC_ORDER_NO").toString();
		String order_date = createorder.bodyMap.get("MERC_ORDER_DATE").toString();
		String token = createorder.respMap.get("TRANS_JRN_NO").toString();
		String sql = "SELECT VERIFY_CODE FROM BASEADM.T_C_MERC_SMS_VERIFYCODEINF " +
				"WHERE PLAT_ORDER_NO IN (SELECT PLAT_ORDER_NO FROM BASEADM.T_B_MERC_PAYIN_ORDER " +
				"WHERE MERC_ID='"+merc_id+"' AND MERC_ORDER_NO='"+order_no+"' AND MERC_ORDER_DATE='"+order_date+"')";
		logger.info("获取db中短信sql="+sql);
		String verifyCode = DBConnectUtils.queryOne(sql ,"payadm").get("VERIFY_CODE");
		logger.info("短信："+verifyCode);

		bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
		bodyMap.put("MBL_CAPTCHA", verifyCode);//短信验证码
		bodyMap.put("TRANS_JRN_NO",token);//请求TOKEN",Y",下单接口或获取短信接口返回的请求TOKEN
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}
	/**
	 * 重新下发短信
	 */
	@Test
	public void testAgreementpayConfirmpayRepeatSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestAgreementPaymentGetsmscode getSms = new TestAgreementPaymentGetsmscode();
		getSms.setUp();
		getSms.testGetSms();
		String merc_id = getSms.bodyMap.get("MERCHANT_ID").toString();
		String order_no = getSms.bodyMap.get("MERC_ORDER_NO").toString();
		String order_date = getSms.bodyMap.get("MERC_ORDER_DATE").toString();
		String token = getSms.respMap.get("TRANS_JRN_NO").toString();
		String verifyCode = getSms.respMap.get("verifyCode");
//		logger.info("短信："+verifyCode);

		bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
		bodyMap.put("MBL_CAPTCHA", verifyCode);//短信验证码
		bodyMap.put("TRANS_JRN_NO",token);//请求TOKEN",Y",下单接口或获取短信接口返回的请求TOKEN
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}
	
}
