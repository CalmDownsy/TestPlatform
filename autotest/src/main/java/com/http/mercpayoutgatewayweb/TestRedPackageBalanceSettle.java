package com.http.mercpayoutgatewayweb;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.http.mercpayoutgatewayweb.query.TestSinglepayQuery;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


/**
 * Test  interface 
 * @author zyh
 * @version 2.0
 * Description: 红包资金划拨  
 * 
 */
public class TestRedPackageBalanceSettle extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestRedPackageBalanceSettle.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11120/merc-payoutgateway-web/redpackage/balancesettle";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web") + "redpackage/balancesettle";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
		infoMap.put("TRX_CODE","100068");//Y交易代码
		infoMap.put("VERSION","2.0");//Y版本号
		infoMap.put("DATA_TYPE","0");//Y数据格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y请求流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y签名信息
		
		bodyMap.put("MERCHANT_ID","800010000020019");
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("MARK_TYPE","1");//1：协议号 2：绑卡id
		bodyMap.put("MARK_ID","");
		bodyMap.put("USER_NO","");//个人平台用户号
		bodyMap.put("TRANS_AMT","");//Y交易金额
		bodyMap.put("NOTIFY_URL", "http://10.63.13.11:12790/simulator-web/mercservice/backNotify");
		bodyMap.put("REEX_NOTIFY_URL", "http://10.63.13.21:12790/simulator-web/mercservice/payOutBackNotify");
		
		bodyMap.put("TERMINAL_TYPE", "02");
		bodyMap.put("TERMINALID", "TERMINALID");
		bodyMap.put("DEVICE_INFO", "ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");
		bodyMap.put("REQUEST_IP","10.8.1.1");//Y用户请求IP
			Map<String, String> riskMsg = new HashMap<String, String>();
//			riskMsg.put("subMercNo", "1000001");
			riskMsg.put("subMercName", "小霸王");
//			riskMsg.put("subMercTradeCategory", "");
//			riskMsg.put("mercUserRegDate", "100");
//			riskMsg.put("mercUserActiveDays", "99");
//			riskMsg.put("mercUserActiveLevel", "1");
			riskMsg.put("mercUserRegMobile", "13800138000");
			riskMsg.put("mercUserRegEmail", "13800138000@139.com");
//			riskMsg.put("mercUserIdNo", "1234567890");
//			riskMsg.put("mercUserRegIp", "127.0.0.1");
			riskMsg.put("mercUserRegDeviceId", "1234567890");
//			riskMsg.put("mercUserRealNameFlag", "1");
//			riskMsg.put("orderRealFlag", "1");
//			riskMsg.put("orderRealIdNo", "1234567890");
//			riskMsg.put("rechargeMobile", "13800138000");
			riskMsg.put("receiveUserName", "小熊猫");
//			riskMsg.put("receiveUserMobile", "13800138000");
//			riskMsg.put("receiveAddrProv", "北京");
//			riskMsg.put("receiveAddrCity", "北京");
			riskMsg.put("receiveAddrArea", "海淀");
			riskMsg.put("receiveAddrDetail", "四季青桥");
			riskMsg.put("payFrontUrlReferrer", "www.baidu.com");
		bodyMap.put("RISK_INFO", JSONObject.toJSONString(riskMsg));//风控大字段,N,
		bodyMap.put("EXTEND1","");//N扩展字段
		bodyMap.put("EXTEND2","");//N扩展字段
		bodyMap.put("EXTEND3","");//N扩展字段
	}

	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
		initReqMap();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}

	TestSinglepayQuery query = new TestSinglepayQuery();
	 /**
     * 协议号
     * @throws Exception
     */
//	@Test
    public void testAgreementNo() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
        
        bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("MARK_TYPE","1");//1：协议号 2：绑卡id
		bodyMap.put("MARK_ID","48190114110729397100010010");
		bodyMap.put("USER_NO","140000005411");//Y收款方编号  个人平台用户号
		bodyMap.put("TRANS_AMT","1");//Y交易金额

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++){
			query.queryPayOut(bodyMap.get("MERCHANT_ID").toString(), bodyMap.get("MERC_ORDER_NO").toString() ,bodyMap.get("MERC_ORDER_DATE").toString() );
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState="+orderState);
			if(orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5*1000);
		}
		Assert.assertEquals( orderState,"S", "订单未成功");
    }
	
	 /**
     * 绑卡id
     * @throws Exception
     */
	@Test
    public void testBindId() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		
        bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("MARK_TYPE","2");//1：协议号 2：绑卡id
		bodyMap.put("MARK_ID","20190624143802248100010026");
		bodyMap.put("USER_NO","140000005411");//Y收款方编号  个人平台用户号
		bodyMap.put("TRANS_AMT","1");//Y交易金额

		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++){
			query.queryPayOut(bodyMap.get("MERCHANT_ID").toString(), bodyMap.get("MERC_ORDER_NO").toString() ,bodyMap.get("MERC_ORDER_DATE").toString() );
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState="+orderState);
			if(orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5*1000);
		}
		Assert.assertEquals( orderState,"S", "订单未成功");
    }
}
