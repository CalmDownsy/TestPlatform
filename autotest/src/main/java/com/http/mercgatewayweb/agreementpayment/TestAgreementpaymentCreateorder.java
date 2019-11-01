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
public class TestAgreementpaymentCreateorder extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestAgreementpaymentCreateorder.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/agreementpayment/createorder";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"agreementpayment/createorder";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100044");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID","800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",orderNo);
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());
		bodyMap.put("PLACE_ORDER_IP","127.0.0.1");
		bodyMap.put("NOTIFY_URL",notifyUrl);
		bodyMap.put("TRANS_AMT","10");
		bodyMap.put("BUSINESS_CATEGORY","110001");//业务种类,Y,110001水电煤缴费
		bodyMap.put("SIGN_AGREEMENT_NO","");//签约协议号,N,与银行卡四要素不可同时为空
		bodyMap.put("CURRENCY","CNY");
		bodyMap.put("ORDER_EXP_DATE","20281012141034");//订单过期时间,N,
		bodyMap.put("SPLIT_FLAG","2");// 分账标志,N,1：分账 2：不分账（默认）
		bodyMap.put("SPLIT_FORMULA","");
		bodyMap.put("ASSURE_FLAG","2");// 担保标志,N,1：担保	2：不担保（默认）
		bodyMap.put("ORDER_DETAILS","3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");
		bodyMap.put("CARD_NO","");//卡号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("MERC_USER_NO","");//商户用户编号,Y
		bodyMap.put("EXTEND1","111");
		bodyMap.put("EXTEND2","222");
		bodyMap.put("EXTEND3","222");
		bodyMap.put("REMARK","222");

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
	 *
	 */
	@Test
	public void testAgreementpayCreateorder()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		bodyMap.put("MERC_USER_NO","AT20181024212042494");//商户用户编号,Y
		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC

//		bodyMap.put("SIGN_AGREEMENT_NO","20181024212618947100010010");//签约协议号 ICBC
//		bodyMap.put("MERC_USER_NO","AT20181024212042494");//商户用户编号,Y
//		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
//		bodyMap.put("BANK_MOBILE_NO", "13811288947");//银行预留手机号
//		bodyMap.put("ID_NO", "321081198601042119");//证件号
//		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC

		logger.info("MERC_ORDER_NO= "+ orderNo);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}

}
