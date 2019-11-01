package com.http.mercpayoutgatewayweb.query;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Test merchant-gateway interface 
 * @author zgp
 * @version 2.0
 * Description: 查询
 */

public class TestSinglepayQuery extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestSinglepayQuery.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11120/merc-payoutgateway-web/query/singlepay";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web")+"query/singlepay";

	public void initReqMap() throws Exception { 
		infoMap.put("TRX_CODE","200030");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", "");
		bodyMap.put("MERC_ORDER_DATE", "");
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
//	@Test
	public void testQueryPayOut()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", "AT20181023181006920");//成功
		bodyMap.put("MERC_ORDER_DATE", "20181206");
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("40069000", respMap.get("RET_CODE"));
		Assert.assertEquals("商户订单不存在", respMap.get("RET_MSG"));
	}

	public void queryPayOut(String mercId, String orderNo, String orderDate)throws Exception {
		setUp();
		bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);//成功
		bodyMap.put("MERC_ORDER_DATE", orderDate);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}

}
