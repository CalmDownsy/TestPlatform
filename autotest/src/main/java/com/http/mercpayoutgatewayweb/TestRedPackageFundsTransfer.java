package com.http.mercpayoutgatewayweb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
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
 * Description: 红包资金划拨  
 * 
 */
public class TestRedPackageFundsTransfer extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestRedPackageFundsTransfer.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11120/merc-payoutgateway-web/redpackage/fundstransfer";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web") + "redpackage/fundstransfer";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
		infoMap.put("TRX_CODE","100066");//Y交易代码
		infoMap.put("VERSION","2.0");//Y版本号
		infoMap.put("DATA_TYPE","0");//Y数据格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y请求流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y签名信息
		
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y商户订单日期
		bodyMap.put("MERC_ORDER_NO", orderNo);//Y商户订单编号
		bodyMap.put("BUSINESS_SCENE","");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","");//Y付款方类型 1-个人(暂不支持)2-商户  当业务种类为B2C红包直接发放(一步操作)和B2C发红包(二步操作)时不能为空。当业务种类为红包领取、红包退回时必须为空。
		bodyMap.put("PAY_NO","");//Y付款方编号  同PAY_USER_TYPE
		bodyMap.put("RECV_USER_TYPE","");//Y收款方类型 1：个人 2：商户	当业务场景是B2C发红包(二步操作）时可以为空，其他场景不能为空。
		bodyMap.put("RECV_NO","");//Y收款方编号  同RECV_NO
		bodyMap.put("TRANS_AMT","");//Y交易金额
		
		bodyMap.put("TERMINAL_TYPE", "02");//
		bodyMap.put("TERMINALID", "TERMINALID");
		bodyMap.put("DEVICE_INFO", "ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");
		bodyMap.put("REQUEST_IP","10.8.1.1");//Y用户请求IP
			Map<String, String> riskMsg = new HashMap<String, String>();
			riskMsg.put("subMercNo", "1000001");
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
	/**
	 * 接口支持能力：
	 * 1-红包领取   红包过渡户转账到用户现金余额 
	 * 2-B2C红包直接发放(一步操作) 商户现金户转账到用户现金余额 
	 * 3-红包退回   红包过渡户转账到用户/商户现金余额
	 * 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)  商户现金户转账到红包过渡户
	 **/

	 /**
     * 1-红包领取   红包过渡户转账到用户现金余额 
     * 同一个主体
     * @throws Exception
     */
	@Test
    public void testRedPackageGet2C() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("BUSINESS_SCENE","1");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","");//Y付款方类型  1：个人（暂不支持）2：商户 当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("PAY_NO","");//Y付款方编号  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("RECV_USER_TYPE","1");//Y收款方类型  1：个人  2：商户（暂不支持）
		bodyMap.put("RECV_NO","140000005411");//Y收款方编号  个人平台用户号 140000001507 140000005411
		bodyMap.put("TRANS_AMT","1");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }
	 /**
     * 3-红包退回   红包过渡户转账到用户/商户现金余额
     * 同一个主体
     * @throws Exception
     */
	@Test
    public void testRedPackageBack2C() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("BUSINESS_SCENE","3");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","");//Y付款方类型  1：个人（暂不支持）2：商户  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("PAY_NO","");//Y付款方编号  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("RECV_USER_TYPE","1");//Y收款方类型  1：个人 2：商户（暂不支持）
		bodyMap.put("RECV_NO","140000005411");//Y收款方编号  个人平台用户号  140000001507
		bodyMap.put("TRANS_AMT","1");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }
	/**
     * 3-红包退回   红包过渡户转账到用户/商户现金余额
     * 同一个主体
     * @throws Exception
     */
	@Test
    public void testRedPackageBack2B() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("BUSINESS_SCENE","3");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","");//Y付款方类型  1：个人（暂不支持）2：商户  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("PAY_NO","");//Y付款方编号  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("RECV_USER_TYPE","2");//Y收款方类型  1：个人 2：商户
		bodyMap.put("RECV_NO","800010000020019");//Y收款方编号  个人平台用户号
		bodyMap.put("TRANS_AMT","1");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }
	/**
     * 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)  商户现金户转账到红包过渡户
     * 同一个主体
     * @throws Exception
     */
	@Test
    public void testRedPackageMercSend() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("BUSINESS_SCENE","4");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","2");//Y付款方类型  1：个人（暂不支持）2：商户  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("PAY_NO","800010000020019");//Y付款方编号  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("RECV_USER_TYPE","");//Y收款方类型  1：个人 2：商户（暂不支持）
		bodyMap.put("RECV_NO","");//Y收款方编号  个人平台用户号 140000001507
		bodyMap.put("TRANS_AMT","1");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }
	/**
    * 2-B2C红包直接发放(一步操作) 商户现金户转账到用户现金余额 
    * 同一个主体
    * @throws Exception
    */
	@Test
   public void testRedPackageB2CGet() throws Exception{
       logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("BUSINESS_SCENE","2");//业务场景  1-红包领取 2-B2C红包直接发放(一步操作) 3-红包退回 4-B2C发红包(二步操作,和业务场景“红包领取”组合使用)
		bodyMap.put("PAY_USER_TYPE","2");//Y付款方类型  1：个人（暂不支持）2：商户  当业务种类为B2C红包领取时，必传，其他不传
		bodyMap.put("PAY_NO","800010000020019");//Y付款方编号 商户编号
		bodyMap.put("RECV_USER_TYPE","1");//Y收款方类型  1：个人 2：商户（暂不支持）
		bodyMap.put("RECV_NO","140000005411");//Y收款方编号  个人平台用户号 140000001507
		bodyMap.put("TRANS_AMT","1");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
   }
}
