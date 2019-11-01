package com.http.mercgatewayweb.agreementpay;

import com.alibaba.fastjson.JSONObject;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Test merc-gateway-web interface 
 * @author zgp
 * @version 2.0
 * Description: 协议支付-直接支付
 */

public class TestAgreementpayDirectpay extends RequestMsgUtils{
	private static final Logger logger = LoggerFactory.getLogger(TestAgreementpayDirectpay.class);
	private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
//    private String url = "http://10.63.11.81:11110/merc-gateway-web/agreementpay/directpay";
    private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/directpay";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
		infoMap.put("TRX_CODE","100025");//Y 交易代码
		infoMap.put("VERSION","2.2");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
		bodyMap.put("PLACE_ORDER_IP", "127.0.0.1");
		bodyMap.put("NOTIFY_URL", notifyUrl);
		bodyMap.put("TRANS_AMT", "0.05");
		bodyMap.put("BUSINESS_CATEGORY", "110001");//业务种类,Y,110001水电煤缴费
		bodyMap.put("SIGN_AGREEMENT_NO", "");//签约协议号,N,与银行卡四要素不可同时为空
		bodyMap.put("CURRENCY", "CNY");
		bodyMap.put("ORDER_EXP_DATE", "22181012141034");//订单过期时间,N,
		bodyMap.put("SPLIT_FLAG", "2");// 分账标志,N,1：分账 2：不分账（默认）
		bodyMap.put("SPLIT_FORMULA","");
		bodyMap.put("ASSURE_FLAG", "2");// 担保标志,N,1：担保	2：不担保（默认）
		bodyMap.put("ORDER_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");
		bodyMap.put("BANK_ACCOUNT_NAME", "");//银行账户名称,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("BANK_MOBILE_NO", "");//银行预留手机号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("CARD_NO", "");//卡号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("ID_NO", "");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		bodyMap.put("ID_TYPE", "");//证件号,N,银行卡四要素如果其中一个有值，其余必须有值。
		
		bodyMap.put("MERC_USER_NO", "");//商户用户编号,Y
		bodyMap.put("VALID_MODE", "");//验证方式,Y,  1:短信校验 2:支付密码校验	3:短验弱验证   4:无需校验
		bodyMap.put("PAY_PASSWORD", "");//支付密码,N
		bodyMap.put("MERGE_FLAG", "");//合单标志，1合单，2非合单（默认）
		bodyMap.put("SIGN_MERC_ID", "");//签约商户号
		
		bodyMap.put("MBL_CAPTCHA", "");//短信验证码
		bodyMap.put("TRANS_JRN_NO", "");//请求TOKEN,Y,下单接口或获取短信接口返回的请求TOKEN
		bodyMap.put("TERMINAL_TYPE", "04");//终端类型
		bodyMap.put("TERMINALID", "TERMINALID");
		bodyMap.put("DEVICE_INFO", "ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");
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
		bodyMap.put("RISK_INFO", JSONObject.toJSONString(riskMsg));//风控大字段,N  
		bodyMap.put("EXTEND1", "备用域EXTEND1");
		bodyMap.put("EXTEND2", "备用域EXTEND2");
		bodyMap.put("EXTEND3", "备用域EXTEND3");
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
	 * 协议号-直接支付
	 * 借记卡
	 */
	@Test
	public void testAgreementpayDirectpay()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
//		orderNo = "ZGP20190520170918277";//
//		bodyMap.put("TRANS_JRN_NO", "56190520170937603100010000");//请求TOKEN,Y,下单接口或获取短信接口返回的请求TOKEN
//		bodyMap.put("MBL_CAPTCHA", "586178");//短信验证码  628402
//		bodyMap.put("MERC_ORDER_NO", orderNo);
		
		//联调环境数据
//		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
//		bodyMap.put("MERC_USER_NO", "ZGP20190102194507514");//商户用户编号,Y
//		bodyMap.put("SIGN_AGREEMENT_NO", "20190102194659646100010010");//签约协议号 ICBC
		//联调环境数据
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_USER_NO", "AT20181024212042494");//商户用户编号,Y
		bodyMap.put("SIGN_AGREEMENT_NO", "20181024212618947100010010");//签约协议号 ICBC
//		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
//		bodyMap.put("BANK_MOBILE_NO", "13552535506");//银行预留手机号
//		bodyMap.put("ID_NO", "321081198601042119");//证件号
//		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC
		
        bodyMap.put("TRANS_AMT", "10");
		bodyMap.put("VALID_MODE", "4");//验证方式,Y,1:短信校验  2:支付密码校验	3:短验弱验证   4:无需校验		
		bodyMap.put("MERGE_FLAG", "2");//1：合单         2：非合单（默认）
        bodyMap.put("ASSURE_FLAG", "2");// 1：担保       2：不担保（默认）
        bodyMap.put("SPLIT_FLAG",  "2");// 1：分账       2：不分账（默认）
        if(bodyMap.get("SPLIT_FLAG").toString()=="1"&&bodyMap.get("MERGE_FLAG").toString()!="1"){
        	String splitFormula = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
        	bodyMap.put("SPLIT_FORMULA", Base64.encodeBase64String(splitFormula.getBytes("utf-8")));//分账公式
	      	bodyMap.put("TRANS_AMT", "0.06");
        }
		if(bodyMap.get("MERGE_FLAG").toString()=="1"){
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
	  		Map<String,String> TRANS_DETAILContent5 = new LinkedHashMap<String, String>();
	  		Map<String,Object> TRANS_DETAIL5 = new LinkedHashMap<String, Object>();
	  		TRANS_DETAILContent5.put("SUB_MERC_ORDER_NO", "5"+orderNo);//子订单编号
	  		TRANS_DETAILContent5.put("BELONG_MERC_ID", "800010000240001");//分账方、主商户都可以
	  		TRANS_DETAILContent5.put("SUB_TRANS_AMT", "0.06");//子订单交易金额
	  		TRANS_DETAILContent5.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
	  		TRANS_DETAILContent5.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        	String splitFormula5 = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
        	TRANS_DETAILContent5.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula5.getBytes("utf-8")));//子订单分账公式
	        TRANS_DETAIL5.put("TRANS_DETAIL",TRANS_DETAILContent5);
	  		TRANS_DETAILS.add(TRANS_DETAIL5);
	  		bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
	      	bodyMap.put("TRANS_AMT", "0.12");
		}
        respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
	}

	/**
	 * 协议号-直接支付
	 * 借记卡红包发放
	 */
	@Test
	public void testAgreementpayDirectpay_RedPackage()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
//		orderNo = "ZGP20190520170918277";//
//		bodyMap.put("TRANS_JRN_NO", "56190520170937603100010000");//请求TOKEN,Y,下单接口或获取短信接口返回的请求TOKEN
//		bodyMap.put("MBL_CAPTCHA", "586178");//短信验证码  628402
//		bodyMap.put("MERC_ORDER_NO", orderNo);

		//联调环境数据
//		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
//		bodyMap.put("MERC_USER_NO", "ZGP20190102194507514");//商户用户编号,Y
//		bodyMap.put("SIGN_AGREEMENT_NO", "20190102194659646100010010");//签约协议号 ICBC
		//联调环境数据
		bodyMap.put("BUSINESS_SCENE", "2");//1：消费（默认）2：红包发放
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_USER_NO", "AT20181024212042494");//商户用户编号,Y
		bodyMap.put("SIGN_AGREEMENT_NO", "20181024212618947100010010");//签约协议号 ICBC
//		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
//		bodyMap.put("BANK_MOBILE_NO", "13552535506");//银行预留手机号
//		bodyMap.put("ID_NO", "321081198601042119");//证件号
//		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC

		bodyMap.put("TRANS_AMT", "1");
		bodyMap.put("VALID_MODE", "4");//验证方式,Y,1:短信校验  2:支付密码校验	3:短验弱验证   4:无需校验
		bodyMap.put("MERGE_FLAG", "2");//1：合单         2：非合单（默认）
		bodyMap.put("ASSURE_FLAG", "2");// 1：担保       2：不担保（默认）
		bodyMap.put("SPLIT_FLAG",  "2");// 1：分账       2：不分账（默认）
		if(bodyMap.get("SPLIT_FLAG").toString()=="1"&&bodyMap.get("MERGE_FLAG").toString()!="1"){
			String splitFormula = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
			bodyMap.put("SPLIT_FORMULA", Base64.encodeBase64String(splitFormula.getBytes("utf-8")));//分账公式
			bodyMap.put("TRANS_AMT", "0.06");
		}
		if(bodyMap.get("MERGE_FLAG").toString()=="1"){
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			Map<String,String> TRANS_DETAILContent5 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL5 = new LinkedHashMap<String, Object>();
			TRANS_DETAILContent5.put("SUB_MERC_ORDER_NO", "5"+orderNo);//子订单编号
			TRANS_DETAILContent5.put("BELONG_MERC_ID", "800010000240001");//分账方、主商户都可以
			TRANS_DETAILContent5.put("SUB_TRANS_AMT", "0.06");//子订单交易金额
			TRANS_DETAILContent5.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
			TRANS_DETAILContent5.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
			String splitFormula5 = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
			TRANS_DETAILContent5.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula5.getBytes("utf-8")));//子订单分账公式
			TRANS_DETAIL5.put("TRANS_DETAIL",TRANS_DETAILContent5);
			TRANS_DETAILS.add(TRANS_DETAIL5);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			bodyMap.put("TRANS_AMT", "0.12");
		}
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals(respMap.get("RET_CODE"),"0000" );
	}

	/**
	 * 协议号-直接支付
	 * 营销支付-不分账
	 */
	@Test
	public void testAgreementpayDirectpay_marketing()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");
//		orderNo = "ZGP20190520170918277";//
//		bodyMap.put("TRANS_JRN_NO", "56190520170937603100010000");//请求TOKEN,Y,下单接口或获取短信接口返回的请求TOKEN
//		bodyMap.put("MBL_CAPTCHA", "586178");//短信验证码  628402
//		bodyMap.put("MERC_ORDER_NO", orderNo);

		//联调环境数据
//		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
//		bodyMap.put("MERC_USER_NO", "ZGP20190102194507514");//商户用户编号,Y
//		bodyMap.put("SIGN_AGREEMENT_NO", "20190102194659646100010010");//签约协议号 ICBC
		//联调环境数据
		bodyMap.put("BUSINESS_SCENE", "1");//1：消费（默认）2：红包发放
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_USER_NO", "AT20181024212042494");//商户用户编号,Y
		bodyMap.put("SIGN_AGREEMENT_NO", "20181024212618947100010010");//签约协议号 ICBC
//		bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//户名
//		bodyMap.put("BANK_MOBILE_NO", "13552535506");//银行预留手机号
//		bodyMap.put("ID_NO", "321081198601042119");//证件号
//		bodyMap.put("CARD_NO", "6233211234567892");//卡号 ICBC

		bodyMap.put("MARKETING_AMT", "3");//营销金额  ,未上送或上送金额为0时，即非营销交易，不校验营销公式
		String marketingFormula = "多营销户测试#800010000020020,1|800010000020019,2";
		bodyMap.put("MARKETING_FORMULA", Base64.encodeBase64String(marketingFormula.getBytes("utf-8")));//营销公式

		bodyMap.put("TRANS_AMT", "10");
		bodyMap.put("VALID_MODE", "4");//验证方式,Y,1:短信校验  2:支付密码校验	3:短验弱验证   4:无需校验
		bodyMap.put("MERGE_FLAG", "2");//1：合单         2：非合单（默认）
		bodyMap.put("ASSURE_FLAG", "2");// 1：担保       2：不担保（默认）
		bodyMap.put("SPLIT_FLAG",  "2");// 1：分账       2：不分账（默认）
		if(bodyMap.get("SPLIT_FLAG").toString()=="1"&&bodyMap.get("MERGE_FLAG").toString()!="1"){
			String splitFormula = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
			bodyMap.put("SPLIT_FORMULA", Base64.encodeBase64String(splitFormula.getBytes("utf-8")));//分账公式
			bodyMap.put("TRANS_AMT", "0.06");
		}
		if(bodyMap.get("MERGE_FLAG").toString()=="1"){
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			Map<String,String> TRANS_DETAILContent5 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL5 = new LinkedHashMap<String, Object>();
			TRANS_DETAILContent5.put("SUB_MERC_ORDER_NO", "5"+orderNo);//子订单编号
			TRANS_DETAILContent5.put("BELONG_MERC_ID", "800010000240001");//分账方、主商户都可以
			TRANS_DETAILContent5.put("SUB_TRANS_AMT", "0.06");//子订单交易金额
			TRANS_DETAILContent5.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
			TRANS_DETAILContent5.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
			String splitFormula5 = "2,800010000100010,金融类测试商户,0.02,分账说明|1,141029790680,邹国平,0.01,分账说明1|2,700000002795360,企业名称金融,0.01,分账说明2|3,700000002795358,个体名称,0.01,分账说明3|4,700000002795359,小微名称,0.01,分账说明4";
			TRANS_DETAILContent5.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula5.getBytes("utf-8")));//子订单分账公式
			TRANS_DETAIL5.put("TRANS_DETAIL",TRANS_DETAILContent5);
			TRANS_DETAILS.add(TRANS_DETAIL5);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			bodyMap.put("TRANS_AMT", "0.12");
		}
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals(respMap.get("RET_CODE"),"0000" );
	}
}
