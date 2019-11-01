package com.http.mercgatewayweb;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test  interface 
 * @author zyh
 * @version 2.0
 * Description: 余额发红包
 * 
 */
public class TestBalancePay extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestBalancePay.class);
	protected String URL = "http://10.63.11.31:11110/merc-gateway-web/balance/balancepay";
//	protected String URL = "http://10.63.11.81:11110/merc-gateway-web/balance/balancepay";

	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/balance/balancepay";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "balance/balancepay";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100067");//Y 交易代码
		infoMap.put("VERSION","2.2");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);//Y
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
		bodyMap.put("TRANS_AMT", "");
		bodyMap.put("PAY_USER_TYPE", "1");//1：个人	 2：商户（暂不支持）
		bodyMap.put("PAY_NO", "");
		bodyMap.put("BUSINESS_SCENE", "2");//1：消费（暂不支持） 2：红包发放
		bodyMap.put("SPLIT_FLAG", "2");//1：分账 2：不分账（默认）
		bodyMap.put("SPLIT_FORMULA", "");
		bodyMap.put("ASSURE_FLAG", "2");//1：担保  2：不担保（默认）
		bodyMap.put("NOTIFY_URL", "http://10.63.13.11:12790/simulator-web/mercservice/signAndPayNotify");

		bodyMap.put("TERMINAL_TYPE", "02");//
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
	//个人余额发红包
    @Test
    public void testBalancePay2RedPackage() throws Exception{
    	bodyMap.put("MERCHANT_ID", "800010000020019");
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("TRANS_AMT", "1");
		bodyMap.put("PAY_USER_TYPE", "1");//1：个人  2：商户（暂不支持）
		bodyMap.put("PAY_NO", "140000005411");//个人平台用户号  140000005411
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals( respMap.get("RET_CODE"), "0000" );
    }
	//商户余额发红包
	@Test
	public void testBalancePayB2CRedPackage() throws Exception{
		bodyMap.put("MERCHANT_ID", "800010000020019");
		bodyMap.put("MERC_ORDER_NO", "L"+randomUtils.getRandomTime(19));
		bodyMap.put("TRANS_AMT", "1");
		bodyMap.put("PAY_USER_TYPE", "2");//1：个人	 2：商户
		bodyMap.put("BUSINESS_SCENE", "3");//1：消费（暂不支持） 2：红包发放 3：B2C红包代付
		bodyMap.put("PAY_NO", "800010000020020");//个人平台用户号  140000005411
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals( respMap.get("RET_CODE"), "0000" );
	}
}

