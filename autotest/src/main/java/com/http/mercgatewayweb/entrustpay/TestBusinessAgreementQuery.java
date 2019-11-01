package com.http.mercgatewayweb.entrustpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 业务协议查询
 * zxb
 */
public class TestBusinessAgreementQuery extends RequestMsgUtils{
	private static final Logger logger = LoggerFactory.getLogger(TestBusinessAgreementQuery.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "entrustpay/businessagreementquery";


	
	public void initReqMap() throws Exception {
		mapInit();

		infoMap.put("TRX_CODE","200032");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
		bodyMap.put("MERC_USER_NO", "");//商户用户号
		bodyMap.put("BUSINESS_TYPE", "");//业务类型  1:订阅
		

	}


	/**
	 * 签约查询
	 * 
	 */
	@Test
	public void Test001()throws Exception {
		initReqMap();
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO","201907031335550256341");//Y 商户订单编号
		bodyMap.put("MERC_ORDER_DATE","20190703");//Y 商户订单日期
		bodyMap.put("MERC_USER_NO", "116705995145281813215");//商户用户号
		bodyMap.put("BUSINESS_TYPE", "1");//业务类型  1:订阅



		requestMap = getRequestMap();

		respMap = http.sendHttpRequest(requestMap, url);
		logger.info("respMap:{}",respMap);
//		System.out.println(respMap.get(""));
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}	
	

}
