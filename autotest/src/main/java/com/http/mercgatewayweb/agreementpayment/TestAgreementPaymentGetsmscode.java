package com.http.mercgatewayweb.agreementpayment;

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

/**
 * Test merc-gateway-web interface
 * @author zgp
 * @version 2.0
 * Description: 协议支付-7.获取短信
 */
public class TestAgreementPaymentGetsmscode extends RequestMsgUtils {
	private static Logger logger = LoggerFactory.getLogger(TestAgreementPaymentGetsmscode.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/agreementpayment/getsmscode";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpayment/getsmscode";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+ DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private TestAgreementpaymentApplypay createorder =  new TestAgreementpaymentApplypay();

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100047");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID","800010000020019");//商户编号",Y",
		bodyMap.put("MERC_ORDER_NO", "");//商户订单号",Y",^\\w{1",32}$ 规则说明:仅支持数字、字母_
		bodyMap.put("MERC_ORDER_DATE","");//商户订单日期",Y",yyyyMMdd
		bodyMap.put("BANK_ACCOUNT_NAME","");//银行账户名称,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("BANK_MOBILE_NO","");//银行预留手机号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CARD_NO","");//卡号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("ID_NO","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXTEND1", "111");//备用域1
		bodyMap.put("EXTEND2", "222");//备用域2
		bodyMap.put("EXTEND3", "333");//备用域3
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
	 * 商户下发短信	800010000020019
	 * 实时结算-线上内扣收取-固定0.02，分账也是0.02
	 * 借记卡
	 * 普通支付
	 */
	@Test
	public void testGetSms()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		createorder.setUp();
		createorder.testAgreementpayCreateorderWithSms();
		String merc_id = createorder.bodyMap.get("MERCHANT_ID").toString();
		String order_no = createorder.bodyMap.get("MERC_ORDER_NO").toString();
		String order_date = createorder.bodyMap.get("MERC_ORDER_DATE").toString();
		String token = createorder.respMap.get("TRANS_JRN_NO").toString();
		logger.info("token："+token);
		String sql = "SELECT VERIFY_CODE FROM BASEADM.T_C_MERC_SMS_VERIFYCODEINF " +
				"WHERE PLAT_ORDER_NO IN (SELECT PLAT_ORDER_NO FROM BASEADM.T_B_MERC_PAYIN_ORDER " +
				"WHERE MERC_ID='"+merc_id+"' AND MERC_ORDER_NO='"+order_no+"' AND MERC_ORDER_DATE='"+order_date+"')";
		logger.info("获取db中短信sql="+sql);
		String verifyCode = DBConnectUtils.queryOne(sql ,"payadm").get("VERIFY_CODE");
		logger.info("短信："+verifyCode);

		bodyMap.put("MERCHANT_ID", createorder.bodyMap.get("MERCHANT_ID"));//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", createorder.bodyMap.get("MERC_ORDER_NO"));
		bodyMap.put("MERC_ORDER_DATE", createorder.bodyMap.get("MERC_ORDER_DATE"));
		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
		bodyMap.put("ID_NO", "321081198601042119");//证件号
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		bodyMap.put("CVV2","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("EXPIRE_DATE","");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		logger.info("重新获取的token："+respMap.get("TRANS_JRN_NO").toString());
		Assert.assertNotEquals(token, respMap.get("TRANS_JRN_NO").toString());
		String verifyCode1 = DBConnectUtils.queryOne(sql ,"payadm").get("VERIFY_CODE");
		logger.info("重新获取的短信："+verifyCode1);
//		Assert.assertNotEquals(verifyCode, verifyCode1);
		respMap.put("verifyCode",verifyCode1);
	}
}
