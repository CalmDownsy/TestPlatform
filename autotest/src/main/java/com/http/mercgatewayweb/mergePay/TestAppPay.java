package com.http.mercgatewayweb.mergePay;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Test merchant-gateway interface 100014
 * Description: 微信小程序
 */

public class TestAppPay extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestAppPay.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/apppay/createorder";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"apppay/createorder";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
//	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/backNotify"
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();

	public void initReqMap() throws Exception { 
		infoMap.put("TRX_CODE","100043");//Y 交易代码
		infoMap.put("VERSION","2.2");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());
		bodyMap.put("MERC_USER_NO", orderNo);
		bodyMap.put("PLACE_ORDER_IP", "127.0.1.1");
        bodyMap.put("TRANS_AMT", "100");
        bodyMap.put("CURRENCY", "CNY");
        bodyMap.put("ORDER_EXP_DATE", "20970724101010");
        bodyMap.put("APP_ID", "12345");
        bodyMap.put("SUB_MERC_ID", "800010000020019");
        bodyMap.put("ORDER_SUBJECT", "whh测试APP");
        bodyMap.put("ORDER_DESC", "remarkssssssss");
        bodyMap.put("OPERATOR_ID", "whh测试APP");
        bodyMap.put("STORE_ID", "whh测试APP");
        bodyMap.put("TERMINAL_ID", "whh测试APP");
        bodyMap.put("TERMINAL_TYPE", "01");
        bodyMap.put("DEVICE_INFO", "01");
        bodyMap.put("LIMIT_PAY", "1");//目前仅当微信支付模式下可用 1-限定不能使用信用卡支付
        bodyMap.put("SPLIT_FLAG", "2");// 1：分账        2：不分账（默认）
//		String splitFormula = "2,800010000020019,关尔靖,1,分账说明1|1,140000000802,邹国平,1,分账说明2";
//      bodyMap.put("SPLIT_FORMULA, Base64.encodeBase64String(splitFormula.getBytes("utf-8")));// 分账公式
        bodyMap.put("ASSURE_FLAG", "2");// 1：担保        2：不担保（默认）
        bodyMap.put("PAY_CHANNEL", "1");//1-微信，目前只支持微信
		bodyMap.put("NOTIFY_URL", notifyUrl);
		bodyMap.put("RETURN_URL", notifyUrl);
        bodyMap.put("RISK_INFO", "");//风控扩展字段
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
        bodyMap.put("ORDER_DETAILS", "01");
	}
	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
		initReqMap();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}

	String merc_id="800010000020019";//800010000120001 800010000020019
	String order_no=orderNo;
	String order_date=randomUtils.getDate8();
	/**
	 * app支付 普通
	 * 有回调
	 */
//	@Test
	public void testAppPayCommonPay()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
        bodyMap.put("SUB_MERC_ID", "");
		logger.info("MERC_ORDER_NO= "+order_no);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
//		new PayBack().payback(merc_id,order_no,order_date);
	}

	/**
	 * APP合单支付
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
	@Test
	public void testAppPayMergePay()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		setUp();
        bodyMap.put("SPLIT_FLAG", "1");// 1：分账        2：不分账（默认） 本期只支持分账
        bodyMap.put("ASSURE_FLAG", "1");// 1：担保        2：不担保（默认） 本期只支持分账
        bodyMap.put("SUB_MERC_ID", merc_id);//二级商户号
        bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
		bodyMap.put("PLACE_ORDER_IP", "223.104.3.243");//手机上网ip
        bodyMap.put("TRANS_AMT", "300");//第5笔单

		Map<String,String> TRANS_DETAILContent4 = new LinkedHashMap<String, String>();
		Map<String,Object> TRANS_DETAIL4 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent4.put("SUB_MERC_ORDER_NO", "1"+order_no);//子订单编号
		TRANS_DETAILContent4.put("BELONG_MERC_ID", merc_id);//子订单编号
		TRANS_DETAILContent4.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent4.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent4.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
		String splitFormula4 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
		TRANS_DETAILContent4.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula4.getBytes("utf-8")));//子订单分账公式
		TRANS_DETAIL4.put("TRANS_DETAIL",TRANS_DETAILContent4);
		TRANS_DETAILS.add(TRANS_DETAIL4);

		Map<String,String> TRANS_DETAILContent5 = new LinkedHashMap<String, String>();
		Map<String,Object> TRANS_DETAIL5 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent5.put("SUB_MERC_ORDER_NO", "2"+order_no);//子订单编号
		TRANS_DETAILContent5.put("BELONG_MERC_ID", merc_id);//子订单编号
		TRANS_DETAILContent5.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent5.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent5.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        String splitFormula5 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
        TRANS_DETAILContent5.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula5.getBytes("utf-8")));//子订单分账公式
        TRANS_DETAIL5.put("TRANS_DETAIL",TRANS_DETAILContent5);
		TRANS_DETAILS.add(TRANS_DETAIL5);
		
		bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
		logger.info("MERC_ORDER_NO= "+order_no);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		//回调
//		new PayBack().payback(merc_id,order_no,order_date);
	}

	/**
	 * 800010000020019 关尔靖
	 * 个人 140000000802 邹国平
	 * 企业 700000000391402 企业商户1
	 * 个体 700000000391404 个体工商户1
	 * 小微 700000000391405 小微商户1
	 */

}
